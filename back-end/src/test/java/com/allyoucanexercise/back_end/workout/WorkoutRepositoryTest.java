package com.allyoucanexercise.back_end.workout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.simple.JdbcClient;

// import com.allyoucanexercise.back_end.helpers.TimeStampFormatter;

public class WorkoutRepositoryTest {

    @Mock
    private JdbcClient jdbcClient;

    @InjectMocks
    private WorkoutRepository workoutRepository;

    private static final Logger log = LoggerFactory.getLogger(WorkoutRepositoryTest.class);

    // essential JdbcClient Mocks we'll use for most tests
    private JdbcClient.StatementSpec statementSpec;
    private JdbcClient.MappedQuerySpec<Workout> mappedQuerySpec;
    private JdbcClient.ResultQuerySpec resultQuerySpec;
    private Workout workout;
    private Workout workout2;
    private final String fixedTimeString = "2025-04-02 10:30:00";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime time = LocalDateTime.parse(fixedTimeString, formatter);

    Workout setupCompletedWorkout(Integer userId, String title, LocalDateTime completedAt) {
        Workout temporaryWorkout = new Workout();
        temporaryWorkout.setUserId(userId);
        temporaryWorkout.setTitle(title);
        temporaryWorkout.setCompletedAt(completedAt);
        return temporaryWorkout;
    }

    @BeforeEach
    void setUp() {

        workout = setupCompletedWorkout(1, "Test Title1", time);
        workout2 = setupCompletedWorkout(2, "Test Title2", time);

        MockitoAnnotations.openMocks(this);
        statementSpec = mock(JdbcClient.StatementSpec.class);
        mappedQuerySpec = mock(JdbcClient.MappedQuerySpec.class);
        resultQuerySpec = mock(JdbcClient.ResultQuerySpec.class);
    }

    @Test
    void testFindAll() {
        log.debug("************************workout title is {}", workout.getTitle());

        when(jdbcClient.sql("select * from workout")).thenReturn(statementSpec);
        when(statementSpec.query(Workout.class)).thenReturn(mappedQuerySpec);
        when(mappedQuerySpec.list()).thenReturn(List.of(workout));

        List<Workout> workouts = workoutRepository.findAll();

        assertNotNull(workouts);
        assertEquals(1, workouts.size());
        assertEquals("Test Title1", workouts.get(0).getTitle());

        verify(jdbcClient).sql("select * from workout");
        verify(statementSpec).query(Workout.class);
        verify(mappedQuerySpec).list();
    }

    @Test
    void testFindById() {

        when(jdbcClient
                .sql("SELECT id, user_Id, title, created_at, completed_at FROM workout WHERE id = :id"))
                .thenReturn(statementSpec);
        when(statementSpec.param("id", 1)).thenReturn(statementSpec);
        when(statementSpec.query(Workout.class)).thenReturn(mappedQuerySpec);
        when(mappedQuerySpec.optional()).thenReturn(Optional.of(workout));

        Optional<Workout> result = workoutRepository.findById(1);

        assertEquals("Test Title1", result.get().getTitle());
        assertEquals(time, result.get().getCompletedAt());

        verify(jdbcClient)
                .sql("SELECT id, user_Id, title, created_at, completed_at FROM workout WHERE id = :id");
        verify(statementSpec).param("id", 1);
        verify(statementSpec).query(Workout.class);
        verify(mappedQuerySpec).optional();
    }

    @Test
    @DisplayName("test create - Happy Path")
    void testCreate() {
        when(jdbcClient
                .sql("INSERT INTO workout(user_id, title, completed_at) values(?,?,?)"))
                .thenReturn(statementSpec);
        when(statementSpec.params(List.of(workout.getUserId(), workout.getTitle(),
                workout.getCompletedAt())))
                .thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);

        workoutRepository.create(workout);

        verify(jdbcClient)
                .sql("INSERT INTO workout(user_id, title, completed_at) values(?,?,?)");
        verify(statementSpec).params(List.of(workout.getUserId(), workout.getTitle(),
                workout.getCompletedAt()));
        verify(statementSpec).update();
    }

    // @Test
    // @DisplayName("test create - Unhappy Path")
    // void testCreateUnhappy() {
    // can't figure out how to do this one because List will not accept nulls

    // Workout workoutMissingUserId = new Workout();
    // workoutMissingUserId.setTitle("title");
    // workoutMissingUserId.setCompletedAt(time);
    // // Missing userId
    // when(jdbcClient
    // .sql("INSERT INTO workout(userId, title, completedAt) values(?,?,?)"))
    // .thenReturn(statementSpec);
    // when(statementSpec.params(List.of(null,
    // workoutMissingUserId.getTitle(),
    // workoutMissingUserId.getCompletedAt())))
    // .thenReturn(statementSpec);
    // when(statementSpec.update()).thenReturn(0);

    // try {
    // workoutRepository.create(workoutMissingUserId);
    // fail("Expected an exception to be thrown due to unsuccessful creation");
    // } catch (IllegalStateException e) {
    // // Verify that the exception message contains the expected failure reason
    // assertTrue(e.getMessage().contains("Failed to create workout"));
    // }

    // verify(jdbcClient)
    // .sql("INSERT INTO workout(userId, title, completedAt) values(?,?,?)");
    // verify(statementSpec).params(List.of(null,
    // workoutMissingUserId.getTitle(),
    // workoutMissingUserId.getCompletedAt()));
    // verify(statementSpec).update();
    // }

    @Test
    @DisplayName("test create exception - Missing userId")
    void testCreateUnhappyMissingUserId() {
        // Create workout with missing userId
        Workout workoutMissingUserId = new Workout();
        workoutMissingUserId.setTitle("Test Title");
        workoutMissingUserId.setCompletedAt(time);

        when(jdbcClient
                .sql("INSERT INTO workout(user_id, title, completed_at) values(?,?,?)"))
                .thenReturn(statementSpec);

        try {
            workoutRepository.create(workoutMissingUserId);
            fail("Expected an exception to be thrown due to missing userId");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("userId must not be null"));
        }

        verify(jdbcClient, never()).sql(anyString()); // Ensure the SQL isn't executed with invalid data
    }

    @Test
    @DisplayName("test update - Happy Path")
    void testUpdate() {
        Workout updatedWorkout = workout;
        updatedWorkout.setTitle("New Title");

        int id = workout.getId();
        when(jdbcClient.sql(
                "update workout set user_id = ?, title = ?, completed_at = ? where id = ?"))
                .thenReturn(statementSpec);
        when(statementSpec.params(List.of(updatedWorkout.getUserId(),
                updatedWorkout.getTitle(),
                updatedWorkout.getCompletedAt(), id)))
                .thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);

        workoutRepository.update(updatedWorkout, id);

        verify(jdbcClient).sql(
                "update workout set user_id = ?, title = ?, completed_at = ? where id = ?");
        verify(statementSpec).params(List.of(updatedWorkout.getUserId(),
                updatedWorkout.getTitle(),
                updatedWorkout.getCompletedAt(), id));
        verify(statementSpec).update();
    }

    @Test
    @DisplayName("test update - Unhappy Path")
    void testUpdateUnhappy() {
        Workout updatedWorkout = workout;
        updatedWorkout.setTitle("New Title");

        int id = workout.getId();

        when(jdbcClient.sql(
                "update workout set user_id = ?, title = ?, completed_at = ? where id = ?"))
                .thenReturn(statementSpec);
        when(statementSpec.params(List.of(updatedWorkout.getUserId(),
                updatedWorkout.getTitle(),
                updatedWorkout.getCompletedAt(), id)))
                .thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(0);

        try {
            workoutRepository.update(updatedWorkout, id);
            fail("Expected an exception to be thrown due to unsuccessful creation");
        } catch (IllegalStateException e) {
            // Verify that the exception message contains the expected failure reason
            assertTrue(e.getMessage().contains("Failed to update workout"));
        }

        verify(jdbcClient).sql(
                "update workout set user_id = ?, title = ?, completed_at = ? where id = ?");
        verify(statementSpec).params(List.of(updatedWorkout.getUserId(),
                updatedWorkout.getTitle(),
                updatedWorkout.getCompletedAt(), id));
        verify(statementSpec).update();
    }

    @Test
    @DisplayName("test delete - Happy Path")
    void testDelete() {
        int id = workout.getId();
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

    // @Test
    // void testFindByUserId() {
    // Workout additionalWorkout = setupCompletedWorkout(2, "AdditionalTitle",
    // time);
    // Integer userId = 2;

    // Map<String, Object> workoutMap = new HashMap<>();
    // workoutMap.put("id", workout2.getId());
    // workoutMap.put("userId", workout2.getUserId());
    // workoutMap.put("title", workout2.getTitle());
    // workoutMap.put("createdAt", workout2.getCreatedAt());
    // workoutMap.put("completedAt", workout2.getCompletedAt());

    // Map<String, Object> additionalWorkoutMap = new HashMap<>();
    // additionalWorkoutMap.put("id", additionalWorkout.getId());
    // additionalWorkoutMap.put("userId", additionalWorkout.getUserId());
    // additionalWorkoutMap.put("title", additionalWorkout.getTitle());
    // additionalWorkoutMap.put("createdAt", additionalWorkout.getCreatedAt());
    // additionalWorkoutMap.put("completedAt", additionalWorkout.getCompletedAt());

    // log.error("additional2 is {}", workout2);
    // log.error("workoutMap is {}", workoutMap);
    // when(jdbcClient.sql("select * from workout where user_id = :user_id"))
    // .thenReturn(statementSpec);
    // when(statementSpec.param("user_id", userId)).thenReturn(statementSpec);
    // when(statementSpec.query()).thenReturn(resultQuerySpec);
    // when(statementSpec.query(Workout.class)).thenReturn(mappedQuerySpec);
    // when(resultQuerySpec.listOfRows()).thenReturn(List.of(workoutMap,
    // additionalWorkoutMap));

    // List<Workout> workouts = workoutRepository.findByUserId(userId);

    // log.error("******workouts is {}", workouts);

    // assertNotNull(workouts);
    // assertEquals(2, workouts.size());
    // assertEquals("Test Title2", workouts.get(0).getTitle());

    // verify(jdbcClient).sql("select * from workout where user_id = :user_id");
    // verify(statementSpec).query(Workout.class);
    // verify(resultQuerySpec.listOfRows());
    // }
}
