package com.allyoucanexercise.back_end.exercise;

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

public class ExerciseRepositoryTest {

    @Mock
    private JdbcClient jdbcClient;

    @InjectMocks
    private ExerciseRepository exerciseRepository;

    // essential JdbcClient Mocks we'll use for most tests
    private JdbcClient.StatementSpec statementSpec;
    private JdbcClient.MappedQuerySpec<Exercise> mappedQuerySpec;
    private JdbcClient.ResultQuerySpec resultQuerySpec;
    private Exercise exercise;
    private Exercise exercise2;

    @BeforeEach
    void setUp() {
        exercise = new Exercise(1, "Push-up", ExerciseGroup.CHEST, ExerciseType.UPPERBODY, "Push-up description",
                "url1");
        exercise2 = new Exercise(2, "Squat", ExerciseGroup.QUADS, ExerciseType.LOWERBODY, "Squat description", "url2");
        // Initializes mocks
        MockitoAnnotations.openMocks(this);
        statementSpec = mock(JdbcClient.StatementSpec.class);
        mappedQuerySpec = mock(JdbcClient.MappedQuerySpec.class);
        resultQuerySpec = mock(JdbcClient.ResultQuerySpec.class); // Mock MappedQuerySpec

        // alternate method:
        // outside of this function have a private JdbcClient jdbcClientMock, private
        // ExerciseRepository exerciseRepository
        // inside this function: jdbcClientMock = mock(JdbcClient.class)
        // exerciseRepository = new ExerciseRepository(jdbcClientMock)
    }

    @Test
    void testFindAll() {
        // Prepare mock data prior to the call

        when(jdbcClient.sql("select * from exercise")).thenReturn(statementSpec);
        when(statementSpec.query(Exercise.class)).thenReturn(mappedQuerySpec); // query() returns MappedQuerySpec
        when(mappedQuerySpec.list()).thenReturn(List.of(exercise)); // list() returns the mock list of exercises

        // Call method under test
        List<Exercise> exercises = exerciseRepository.findAll();

        // Verify the result
        assertNotNull(exercises);
        assertEquals(1, exercises.size());
        assertEquals("Push-up", exercises.get(0).name());
        assertEquals(ExerciseType.UPPERBODY, exercises.get(0).exerciseType());

        // Verify interactions with mock
        verify(jdbcClient).sql("select * from exercise");
        verify(statementSpec).query(Exercise.class); // Verify query() was called with Exercise.class
        verify(mappedQuerySpec).list(); // Verify that list() was called on the MappedQuerySpec
    }

    @Test
    void testFindById() {

        when(jdbcClient
                .sql("SELECT id,name,exercise_group,exercise_type,description,picture FROM exercise WHERE id = :id"))
                .thenReturn(statementSpec);
        when(statementSpec.param("id", 1)).thenReturn(statementSpec);
        when(statementSpec.query(Exercise.class)).thenReturn(mappedQuerySpec);
        when(mappedQuerySpec.optional()).thenReturn(Optional.of(exercise));

        Optional<Exercise> result = exerciseRepository.findById(1);

        assertEquals("Push-up", result.get().name());
        assertEquals(ExerciseGroup.CHEST, result.get().exerciseGroup());

        verify(jdbcClient)
                .sql("SELECT id,name,exercise_group,exercise_type,description,picture FROM exercise WHERE id = :id");
        verify(statementSpec).param("id", 1);
        verify(statementSpec).query(Exercise.class);
        verify(mappedQuerySpec).optional(); // Ensure optional was called
    }

    @Test
    @DisplayName("test create - Happy Path")
    void testCreate() {
        when(jdbcClient
                .sql("INSERT INTO exercise(name,exercise_group,exercise_type,description,picture) values(?,?,?,?,?)"))
                .thenReturn(statementSpec);
        when(statementSpec.params(List.of(exercise.name(), exercise.exerciseGroup().toString(),
                exercise.exerciseType().toString(), exercise.description(), exercise.picture())))
                .thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);

        exerciseRepository.create(exercise);

        verify(jdbcClient)
                .sql("INSERT INTO exercise(name,exercise_group,exercise_type,description,picture) values(?,?,?,?,?)");
        verify(statementSpec).params(List.of(exercise.name(), exercise.exerciseGroup().toString(),
                exercise.exerciseType().toString(), exercise.description(), exercise.picture()));
        verify(statementSpec).update();
    }

    @Test
    @DisplayName("test create - Unhappy Path")
    void testCreateUnhappy() {
        exercise = new Exercise(1, "", ExerciseGroup.CHEST, ExerciseType.UPPERBODY, "Push-up description",
                "pictureUrl"); // Missing name

        when(jdbcClient
                .sql("INSERT INTO exercise(name,exercise_group,exercise_type,description,picture) values(?,?,?,?,?)"))
                .thenReturn(statementSpec);
        // missing the name
        when(statementSpec.params(List.of(exercise.name(), exercise.exerciseGroup().toString(),
                exercise.exerciseType().toString(), exercise.description(), exercise.picture())))
                .thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(0);

        try {
            exerciseRepository.create(exercise);
            fail("Expected an exception to be thrown due to unsuccessful creation");
        } catch (IllegalStateException e) {
            // Verify that the exception message contains the expected failure reason
            assertTrue(e.getMessage().contains("Failed to create exercise"));
        }

        verify(jdbcClient)
                .sql("INSERT INTO exercise(name,exercise_group,exercise_type,description,picture) values(?,?,?,?,?)");
        verify(statementSpec).params(List.of(exercise.name(), exercise.exerciseGroup().toString(),
                exercise.exerciseType().toString(), exercise.description(), exercise.picture()));
        verify(statementSpec).update();
    }

    @Test
    @DisplayName("test update - Happy Path")
    void testUpdate() {
        exercise = new Exercise(1, "Push-up", ExerciseGroup.CHEST, ExerciseType.UPPERBODY,
                "New, Updated, Push-up description", "pictureUrl");

        int id = exercise.id();
        when(jdbcClient.sql(
                "update exercise set name = ?, exercise_group = ?, exercise_type = ?, description = ?, picture = ? where id = ?"))
                .thenReturn(statementSpec);
        when(statementSpec.params(List.of(exercise.name(), exercise.exerciseGroup().toString(),
                exercise.exerciseType().toString(), exercise.description(), exercise.picture(), id)))
                .thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);

        exerciseRepository.update(exercise, id);

        verify(jdbcClient).sql(
                "update exercise set name = ?, exercise_group = ?, exercise_type = ?, description = ?, picture = ? where id = ?");
        verify(statementSpec).params(List.of(exercise.name(), exercise.exerciseGroup().toString(),
                exercise.exerciseType().toString(), exercise.description(), exercise.picture(), id));
        verify(statementSpec).update();
    }

    @Test
    @DisplayName("test update - Unhappy Path")
    void testUpdateUnhappy() {
        exercise = new Exercise(1, "Push-up", ExerciseGroup.CHEST, ExerciseType.UPPERBODY,
                "New, Updated, Push-up description", "pictureUrl");
        int id = 2;

        when(jdbcClient.sql(
                "update exercise set name = ?, exercise_group = ?, exercise_type = ?, description = ?, picture = ? where id = ?"))
                .thenReturn(statementSpec);
        when(statementSpec.params(List.of(exercise.name(), exercise.exerciseGroup().toString(),
                exercise.exerciseType().toString(), exercise.description(), exercise.picture(), id)))
                .thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(0);

        try {
            exerciseRepository.update(exercise, id);
            fail("Expected an exception to be thrown due to unsuccessful creation");
        } catch (IllegalStateException e) {
            // Verify that the exception message contains the expected failure reason
            assertTrue(e.getMessage().contains("Failed to update exercise"));
        }

        verify(jdbcClient).sql(
                "update exercise set name = ?, exercise_group = ?, exercise_type = ?, description = ?, picture = ? where id = ?");
        verify(statementSpec).params(List.of(exercise.name(), exercise.exerciseGroup().toString(),
                exercise.exerciseType().toString(), exercise.description(), exercise.picture(), id));
        verify(statementSpec).update();
    }

    @Test
    @DisplayName("test delete - Happy Path")
    void testDelete() {
        int id = exercise.id();
        when(jdbcClient.sql("delete from exercise where id = :id")).thenReturn(statementSpec);
        when(statementSpec.param("id", id)).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);

        exerciseRepository.delete(id);

        verify(jdbcClient).sql("delete from exercise where id = :id");
        verify(statementSpec).param("id", id);
        verify(statementSpec).update();
    }

    @Test
    @DisplayName("test delete - Unhappy Path")
    void testDeleteUnhappy() {
        int id = 0;
        when(jdbcClient.sql("delete from exercise where id = :id")).thenReturn(statementSpec);
        when(statementSpec.param("id", id)).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(0);

        try {
            exerciseRepository.delete(id);
            fail("Expected an exception to be thrown due to unsuccessful creation");
        } catch (IllegalStateException e) {
            // Verify that the exception message contains the expected failure reason
            assertTrue(e.getMessage().contains("Failed to delete exercise"));
        }

        verify(jdbcClient).sql("delete from exercise where id = :id");
        verify(statementSpec).param("id", id);
        verify(statementSpec).update();
    }

    @Test
    void testCount() {

        when(jdbcClient.sql("select * from exercise")).thenReturn(statementSpec);
        when(statementSpec.query()).thenReturn(resultQuerySpec);
        when(resultQuerySpec.listOfRows()).thenReturn(List.of(
                Map.of("id", 1, "name", "Push-up", "exerciseGroup", "CHEST", "exerciseType", "UPPERBODY", "description",
                        "Push-up description", "picture", "pictureUrl"),
                Map.of("id", 2, "name", "Squat", "exerciseGroup", "QUADS", "exerciseType", "LOWERBODY", "description",
                        "Squat Description", "picture", "pictureUrl")));

        int count = exerciseRepository.count();
        assertEquals(count, 2);

    }

    @Test
    void testCreatAll() {

        List<Exercise> exercises = List.of(exercise, exercise2);

        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.params(anyList())).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);

        exerciseRepository.saveAll(exercises);

        verify(jdbcClient, times(2)).sql(anyString());
        verify(statementSpec, times(2)).params(anyList());
        verify(statementSpec, times(2)).update();
    }

    @Test
    void testFindByExerciseTypeUpperBody() {
        String exerciseType1 = "UPPERBODY";

        when(jdbcClient.sql("select * from exercise where exercise_type = :exercise_type")).thenReturn(statementSpec);
        when(statementSpec.param("exercise_type", exerciseType1)).thenReturn(statementSpec);
        when(statementSpec.query(Exercise.class)).thenReturn(mappedQuerySpec);
        when(mappedQuerySpec.list()).thenReturn(List.of(exercise));

        List<Exercise> exercises = exerciseRepository.findByExerciseType(exerciseType1);

        assertNotNull(exercises);
        assertEquals(1, exercises.size());
        assertEquals("Push-up", exercises.get(0).name());
        assertEquals(ExerciseType.UPPERBODY, exercises.get(0).exerciseType());

        verify(jdbcClient).sql("select * from exercise where exercise_type = :exercise_type");
        verify(statementSpec).query(Exercise.class);
        verify(mappedQuerySpec).list();
    }

    @Test
    void testFindByExerciseTypeLowerBody() {
        String exerciseType2 = "LOWERBODY";

        when(jdbcClient.sql("select * from exercise where exercise_type = :exercise_type")).thenReturn(statementSpec);
        when(statementSpec.param("exercise_type", exerciseType2)).thenReturn(statementSpec);
        when(statementSpec.query(Exercise.class)).thenReturn(mappedQuerySpec);
        when(mappedQuerySpec.list()).thenReturn(List.of(exercise2));

        List<Exercise> exercises = exerciseRepository.findByExerciseType(exerciseType2);

        assertNotNull(exercises);
        assertEquals(1, exercises.size());
        assertEquals("Squat", exercises.get(0).name());
        assertEquals(ExerciseType.LOWERBODY, exercises.get(0).exerciseType());

        verify(jdbcClient).sql("select * from exercise where exercise_type = :exercise_type");
        verify(statementSpec).query(Exercise.class);
        verify(mappedQuerySpec).list();
    }

}
