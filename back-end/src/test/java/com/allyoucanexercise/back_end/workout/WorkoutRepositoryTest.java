package com.allyoucanexercise.back_end.workout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.simple.JdbcClient;

import com.allyoucanexercise.back_end.helpers.TimeStampFormatter;

public class WorkoutRepositoryTest {

        @Mock
        private JdbcClient jdbcClient;

        @InjectMocks
        private WorkoutRepository workoutRepository;

        // essential JdbcClient Mocks we'll use for most tests
        private JdbcClient.StatementSpec statementSpec;
        private JdbcClient.MappedQuerySpec<Workout> mappedQuerySpec;
        private JdbcClient.ResultQuerySpec resultQuerySpec;
        private Workout workout;
        private Workout workout2;

        Workout setupCompletedWorkout(Integer userId, String title, Timestamp createdAt, Timestamp completedAt) {
                workout = new Workout();
                workout.setUserId(userId);
                workout.setTitle(title);
                workout.setCreatedAt(createdAt);
                workout.setCompletedAt(completedAt);
                return workout;
        }

        @BeforeEach
        void setUp() {

                final String fixedTimeString = "2025-04-02 10:30:00";
                final Timestamp time = TimeStampFormatter.stringToTimestamp(fixedTimeString);
                workout = setupCompletedWorkout(1, "Test Title", time, time);
                workout2 = setupCompletedWorkout(2, "Test Title2", time, time);

                MockitoAnnotations.openMocks(this);
                statementSpec = mock(JdbcClient.StatementSpec.class);
                mappedQuerySpec = mock(JdbcClient.MappedQuerySpec.class);
                resultQuerySpec = mock(JdbcClient.ResultQuerySpec.class);
        }

        @Test
        void testFindAll() {
                // Prepare mock data prior to the call

                when(jdbcClient.sql("select * from workout")).thenReturn(statementSpec);
                when(statementSpec.query(Workout.class)).thenReturn(mappedQuerySpec); // query() returns MappedQuerySpec
                when(mappedQuerySpec.list()).thenReturn(List.of(workout)); // list() returns the mock list of workouts

                // Call method under test
                List<Workout> workouts = workoutRepository.findAll();

                // Verify the result
                assertNotNull(workouts);
                assertEquals(1, workouts.size());
                assertEquals("Push-up", workouts.get(0).name());
                assertEquals(WorkoutType.UPPERBODY, workouts.get(0).workoutType());

                // Verify interactions with mock
                verify(jdbcClient).sql("select * from workout");
                verify(statementSpec).query(Workout.class); // Verify query() was called with Workout.class
                verify(mappedQuerySpec).list(); // Verify that list() was called on the MappedQuerySpec
        }

        @Test
        void testFindById() {

                when(jdbcClient
                                .sql("SELECT id,name,workout_group,workout_type,description,picture FROM workout WHERE id = :id"))
                                .thenReturn(statementSpec);
                when(statementSpec.param("id", 1)).thenReturn(statementSpec);
                when(statementSpec.query(Workout.class)).thenReturn(mappedQuerySpec);
                when(mappedQuerySpec.optional()).thenReturn(Optional.of(workout));

                Optional<Workout> result = workoutRepository.findById(1);

                assertEquals("Push-up", result.get().name());
                assertEquals(WorkoutGroup.CHEST, result.get().workoutGroup());

                verify(jdbcClient)
                                .sql("SELECT id,name,workout_group,workout_type,description,picture FROM workout WHERE id = :id");
                verify(statementSpec).param("id", 1);
                verify(statementSpec).query(Workout.class);
                verify(mappedQuerySpec).optional(); // Ensure optional was called
        }

        @Test
        @DisplayName("test create - Happy Path")
        void testCreate() {
                when(jdbcClient
                                .sql("INSERT INTO workout(name,workout_group,workout_type,description,picture) values(?,?,?,?,?)"))
                                .thenReturn(statementSpec);
                when(statementSpec.params(List.of(workout.name(), workout.workoutGroup().toString(),
                                workout.workoutType().toString(), workout.description(), workout.picture())))
                                .thenReturn(statementSpec);
                when(statementSpec.update()).thenReturn(1);

                workoutRepository.create(workout);

                verify(jdbcClient)
                                .sql("INSERT INTO workout(name,workout_group,workout_type,description,picture) values(?,?,?,?,?)");
                verify(statementSpec).params(List.of(workout.name(), workout.workoutGroup().toString(),
                                workout.workoutType().toString(), workout.description(), workout.picture()));
                verify(statementSpec).update();
        }

        @Test
        @DisplayName("test create - Unhappy Path")
        void testCreateUnhappy() {
                workout = new Workout(1, "", WorkoutGroup.CHEST, WorkoutType.UPPERBODY, "Push-up description",
                                "pictureUrl"); // Missing name

                when(jdbcClient
                                .sql("INSERT INTO workout(name,workout_group,workout_type,description,picture) values(?,?,?,?,?)"))
                                .thenReturn(statementSpec);
                // missing the name
                when(statementSpec.params(List.of(workout.name(), workout.workoutGroup().toString(),
                                workout.workoutType().toString(), workout.description(), workout.picture())))
                                .thenReturn(statementSpec);
                when(statementSpec.update()).thenReturn(0);

                try {
                        workoutRepository.create(workout);
                        fail("Expected an exception to be thrown due to unsuccessful creation");
                } catch (IllegalStateException e) {
                        // Verify that the exception message contains the expected failure reason
                        assertTrue(e.getMessage().contains("Failed to create workout"));
                }

                verify(jdbcClient)
                                .sql("INSERT INTO workout(name,workout_group,workout_type,description,picture) values(?,?,?,?,?)");
                verify(statementSpec).params(List.of(workout.name(), workout.workoutGroup().toString(),
                                workout.workoutType().toString(), workout.description(), workout.picture()));
                verify(statementSpec).update();
        }

        @Test
        @DisplayName("test update - Happy Path")
        void testUpdate() {
                workout = new Workout(1, "Push-up", WorkoutGroup.CHEST, WorkoutType.UPPERBODY,
                                "New, Updated, Push-up description", "pictureUrl");

                int id = workout.id();
                when(jdbcClient.sql(
                                "update workout set name = ?, workout_group = ?, workout_type = ?, description = ?, picture = ? where id = ?"))
                                .thenReturn(statementSpec);
                when(statementSpec.params(List.of(workout.name(), workout.workoutGroup().toString(),
                                workout.workoutType().toString(), workout.description(), workout.picture(), id)))
                                .thenReturn(statementSpec);
                when(statementSpec.update()).thenReturn(1);

                workoutRepository.update(workout, id);

                verify(jdbcClient).sql(
                                "update workout set name = ?, workout_group = ?, workout_type = ?, description = ?, picture = ? where id = ?");
                verify(statementSpec).params(List.of(workout.name(), workout.workoutGroup().toString(),
                                workout.workoutType().toString(), workout.description(), workout.picture(), id));
                verify(statementSpec).update();
        }

        @Test
        @DisplayName("test update - Unhappy Path")
        void testUpdateUnhappy() {
                workout = new Workout(1, "Push-up", WorkoutGroup.CHEST, WorkoutType.UPPERBODY,
                                "New, Updated, Push-up description", "pictureUrl");
                int id = 2;

                when(jdbcClient.sql(
                                "update workout set name = ?, workout_group = ?, workout_type = ?, description = ?, picture = ? where id = ?"))
                                .thenReturn(statementSpec);
                when(statementSpec.params(List.of(workout.name(), workout.workoutGroup().toString(),
                                workout.workoutType().toString(), workout.description(), workout.picture(), id)))
                                .thenReturn(statementSpec);
                when(statementSpec.update()).thenReturn(0);

                try {
                        workoutRepository.update(workout, id);
                        fail("Expected an exception to be thrown due to unsuccessful creation");
                } catch (IllegalStateException e) {
                        // Verify that the exception message contains the expected failure reason
                        assertTrue(e.getMessage().contains("Failed to update workout"));
                }

                verify(jdbcClient).sql(
                                "update workout set name = ?, workout_group = ?, workout_type = ?, description = ?, picture = ? where id = ?");
                verify(statementSpec).params(List.of(workout.name(), workout.workoutGroup().toString(),
                                workout.workoutType().toString(), workout.description(), workout.picture(), id));
                verify(statementSpec).update();
        }

        @Test
        @DisplayName("test delete - Happy Path")
        void testDelete() {
                int id = workout.id();
                when(jdbcClient.sql("delete from workout where id = :id")).thenReturn(statementSpec);
                when(statementSpec.param("id", id)).thenReturn(statementSpec);
                when(statementSpec.update()).thenReturn(1);

                workoutRepository.delete(id);

                verify(jdbcClient).sql("delete from workout where id = :id");
                verify(statementSpec).param("id", id);
                verify(statementSpec).update();
        }

        @Test
        @DisplayName("test delete - Unhappy Path")
        void testDeleteUnhappy() {
                int id = 0;
                when(jdbcClient.sql("delete from workout where id = :id")).thenReturn(statementSpec);
                when(statementSpec.param("id", id)).thenReturn(statementSpec);
                when(statementSpec.update()).thenReturn(0);

                try {
                        workoutRepository.delete(id);
                        fail("Expected an exception to be thrown due to unsuccessful creation");
                } catch (IllegalStateException e) {
                        // Verify that the exception message contains the expected failure reason
                        assertTrue(e.getMessage().contains("Failed to delete workout"));
                }

                verify(jdbcClient).sql("delete from workout where id = :id");
                verify(statementSpec).param("id", id);
                verify(statementSpec).update();
        }

        @Test
        void testCount() {

                when(jdbcClient.sql("select * from workout")).thenReturn(statementSpec);
                when(statementSpec.query()).thenReturn(resultQuerySpec);
                when(resultQuerySpec.listOfRows()).thenReturn(List.of(
                                Map.of("id", 1, "name", "Push-up", "workoutGroup", "CHEST", "workoutType", "UPPERBODY",
                                                "description",
                                                "Push-up description", "picture", "pictureUrl"),
                                Map.of("id", 2, "name", "Squat", "workoutGroup", "QUADS", "workoutType", "LOWERBODY",
                                                "description",
                                                "Squat Description", "picture", "pictureUrl")));

                int count = workoutRepository.count();
                assertEquals(count, 2);

        }

        @Test
        void testCreatAll() {

                List<Workout> workouts = List.of(workout, workout2);

                when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
                when(statementSpec.params(anyList())).thenReturn(statementSpec);
                when(statementSpec.update()).thenReturn(1);

                workoutRepository.saveAll(workouts);

                verify(jdbcClient, times(2)).sql(anyString());
                verify(statementSpec, times(2)).params(anyList());
                verify(statementSpec, times(2)).update();
        }

        @Test
        void testFindByWorkoutTypeUpperBody() {
                String workoutType1 = "UPPERBODY";

                when(jdbcClient.sql("select * from workout where workout_type = :workout_type"))
                                .thenReturn(statementSpec);
                when(statementSpec.param("workout_type", workoutType1)).thenReturn(statementSpec);
                when(statementSpec.query(Workout.class)).thenReturn(mappedQuerySpec);
                when(mappedQuerySpec.list()).thenReturn(List.of(workout));

                List<Workout> workouts = workoutRepository.findByWorkoutType(workoutType1);

                assertNotNull(workouts);
                assertEquals(1, workouts.size());
                assertEquals("Push-up", workouts.get(0).name());
                assertEquals(WorkoutType.UPPERBODY, workouts.get(0).workoutType());

                verify(jdbcClient).sql("select * from workout where workout_type = :workout_type");
                verify(statementSpec).query(Workout.class);
                verify(mappedQuerySpec).list();
        }

        @Test
        void testFindByWorkoutTypeLowerBody() {
                String workoutType2 = "LOWERBODY";

                when(jdbcClient.sql("select * from workout where workout_type = :workout_type"))
                                .thenReturn(statementSpec);
                when(statementSpec.param("workout_type", workoutType2)).thenReturn(statementSpec);
                when(statementSpec.query(Workout.class)).thenReturn(mappedQuerySpec);
                when(mappedQuerySpec.list()).thenReturn(List.of(workout2));

                List<Workout> workouts = workoutRepository.findByWorkoutType(workoutType2);

                assertNotNull(workouts);
                assertEquals(1, workouts.size());
                assertEquals("Squat", workouts.get(0).name());
                assertEquals(WorkoutType.LOWERBODY, workouts.get(0).workoutType());

                verify(jdbcClient).sql("select * from workout where workout_type = :workout_type");
                verify(statementSpec).query(Workout.class);
                verify(mappedQuerySpec).list();
        }

}
