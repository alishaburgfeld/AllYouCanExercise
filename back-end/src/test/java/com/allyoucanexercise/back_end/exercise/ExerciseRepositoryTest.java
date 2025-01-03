package com.allyoucanexercise.back_end.exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        // Initializes mocks
        MockitoAnnotations.openMocks(this); 

        // alternate method:
        // outside of this function have a private JdbcClient jdbcClientMock, private ExerciseRepository exerciseRepository
        // inside this function: jdbcClientMock = mock(JdbcClient.class) exerciseRepository = new ExerciseRepository(jdbcClientMock)
    }

    @Test
    void testFindAll() {
        Exercise exercise = new Exercise(1, "Push-up", ExerciseType.UPPERBODY, "Push-up description");
        
        // Prepare mock data prior to the call
        // when(jdbcClient.sql("select * from exercise").query(Exercise.class).list())
        //         .thenReturn(List.of(exercise));
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
    JdbcClient.MappedQuerySpec<Exercise> mappedQuerySpec = mock(JdbcClient.MappedQuerySpec.class);  // Mock MappedQuerySpec
    when(jdbcClient.sql("select * from exercise")).thenReturn(statementSpec);
    when(statementSpec.query(Exercise.class)).thenReturn(mappedQuerySpec);  // query() returns MappedQuerySpec
    when(mappedQuerySpec.list()).thenReturn(List.of(exercise));  // list() returns the mock list of exercises


        // Call method under test
        List<Exercise> exercises = exerciseRepository.findAll();

        // Verify the result
        assertNotNull(exercises);
        assertEquals(1, exercises.size());
        assertEquals("Push-up", exercises.get(0).name());

        // Verify interactions with mock
        verify(jdbcClient).sql("select * from exercise");
    }
}
