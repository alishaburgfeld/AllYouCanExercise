// package com.allyoucanexercise.back_end.workout;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.fail;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.sql.Timestamp;
// import java.util.List;
// import java.util.Map;
// import java.util.Optional;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.jdbc.core.simple.JdbcClient;

// import com.allyoucanexercise.back_end.helpers.TimeStampFormatter;

// public class WorkoutRepositoryTest {

// @Mock
// private JdbcClient jdbcClient;

// @InjectMocks
// private WorkoutRepository workoutRepository;

// private static final Logger log =
// LoggerFactory.getLogger(WorkoutRepositoryTest.class);

// // essential JdbcClient Mocks we'll use for most tests
// private JdbcClient.StatementSpec statementSpec;
// private JdbcClient.MappedQuerySpec<Workout> mappedQuerySpec;
// private JdbcClient.ResultQuerySpec resultQuerySpec;
// private Workout workout;
// private Workout workoutNoAddedDates;
// private final String fixedTimeString = "2025-04-02 10:30:00";
// private final Timestamp time =
// TimeStampFormatter.stringToTimestamp(fixedTimeString);

// Workout setupCompletedWorkout(Integer userId, String title, Timestamp
// createdAt, Timestamp completedAt) {
// Workout temporaryWorkout = new Workout();
// temporaryWorkout.setUserId(userId);
// temporaryWorkout.setTitle(title);
// temporaryWorkout.setCreatedAt(createdAt);
// temporaryWorkout.setCompletedAt(completedAt);
// return temporaryWorkout;
// }

// @BeforeEach
// void setUp() {

// workout = setupCompletedWorkout(1, "Test Title1", time, time);
// workoutNoAddedDates = setupCompletedWorkout(2, "Test Title2", null, null);

// MockitoAnnotations.openMocks(this);
// statementSpec = mock(JdbcClient.StatementSpec.class);
// mappedQuerySpec = mock(JdbcClient.MappedQuerySpec.class);
// resultQuerySpec = mock(JdbcClient.ResultQuerySpec.class);
// }

// @Test
// void testFindAll() {
// log.debug("************************workout title is {}", workout.getTitle());

// when(jdbcClient.sql("select * from workout")).thenReturn(statementSpec);
// when(statementSpec.query(Workout.class)).thenReturn(mappedQuerySpec);
// when(mappedQuerySpec.list()).thenReturn(List.of(workout));

// List<Workout> workouts = workoutRepository.findAll();

// assertNotNull(workouts);
// assertEquals(1, workouts.size());
// assertEquals("Test Title1", workouts.get(0).getTitle());

// verify(jdbcClient).sql("select * from workout");
// verify(statementSpec).query(Workout.class);
// verify(mappedQuerySpec).list();
// }

// @Test
// void testFindById() {

// when(jdbcClient
// .sql("SELECT id, userId, title, createdAt, completedAt FROM workout WHERE id
// = :id"))
// .thenReturn(statementSpec);
// when(statementSpec.param("id", 1)).thenReturn(statementSpec);
// when(statementSpec.query(Workout.class)).thenReturn(mappedQuerySpec);
// when(mappedQuerySpec.optional()).thenReturn(Optional.of(workout));

// Optional<Workout> result = workoutRepository.findById(1);

// assertEquals("Test Title1", result.get().getTitle());
// assertEquals(time, result.get().getCreatedAt());

// verify(jdbcClient)
// .sql("SELECT id, userId, title, createdAt, completedAt FROM workout WHERE id
// = :id");
// verify(statementSpec).param("id", 1);
// verify(statementSpec).query(Workout.class);
// verify(mappedQuerySpec).optional();
// }

// @Test
// @DisplayName("test create - Happy Path")
// void testCreate() {
// when(jdbcClient
// .sql("INSERT INTO workout(userId, title, createdAt, completedAt)
// values(?,?,?,?)"))
// .thenReturn(statementSpec);
// when(statementSpec.params(List.of(workout.getUserId(), workout.getTitle(),
// workout.getCreatedAt(),
// workout.getCompletedAt())))
// .thenReturn(statementSpec);
// when(statementSpec.update()).thenReturn(1);

// workoutRepository.create(workout);

// verify(jdbcClient)
// .sql("INSERT INTO workout(userId, title, createdAt, completedAt)
// values(?,?,?,?)");
// verify(statementSpec).params(List.of(workout.getUserId(), workout.getTitle(),
// workout.getCreatedAt(),
// workout.getCompletedAt()));
// verify(statementSpec).update();
// }

// @Test
// @DisplayName("test create works with no createdAt or completedAt provided")
// void testCreateWithoutDates() {
// when(jdbcClient
// .sql("INSERT INTO workout(userId, title, createdAt, completedAt)
// values(?,?,?,?)"))
// .thenReturn(statementSpec);
// when(statementSpec.params(List.of(workoutNoAddedDates.getUserId(),
// workoutNoAddedDates.getTitle(),
// workoutNoAddedDates.getCreatedAt(),
// workoutNoAddedDates.getCompletedAt())))
// .thenReturn(statementSpec);
// when(statementSpec.update()).thenReturn(1);

// workoutRepository.create(workoutNoAddedDates);

// verify(jdbcClient)
// .sql("INSERT INTO workout(userId, title, createdAt, completedAt)
// values(?,?,?,?)");
// verify(statementSpec).params(List.of(workoutNoAddedDates.getUserId(),
// workoutNoAddedDates.getTitle(),
// workoutNoAddedDates.getCreatedAt(),
// workoutNoAddedDates.getCompletedAt()));
// verify(statementSpec).update();
// }

// @Test
// @DisplayName("test create - Unhappy Path")
// void testCreateUnhappy() {

// Workout workoutMissingUserId = new Workout();
// workoutMissingUserId.setTitle("title");
// // Missing userId
// when(jdbcClient
// .sql("INSERT INTO workout(userId, title, createdAt, completedAt)
// values(?,?,?,?)"))
// .thenReturn(statementSpec);
// when(statementSpec.params(List.of(workoutMissingUserId.getUserId(),
// workoutMissingUserId.getTitle(),
// workoutMissingUserId.getCreatedAt(),
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
// .sql("INSERT INTO workout(userId, title, createdAt, completedAt)
// values(?,?,?,?)");
// verify(statementSpec).params(List.of(workoutMissingUserId.getUserId(),
// workoutMissingUserId.getTitle(),
// workoutMissingUserId.getCreatedAt(),
// workoutMissingUserId.getCompletedAt()));
// verify(statementSpec).update();
// }

// @Test
// @DisplayName("test update - Happy Path")
// void testUpdate() {
// Workout updatedWorkout = workout;
// updatedWorkout.setTitle("New Title");

// int id = workout.getId();
// when(jdbcClient.sql(
// "update workout set userId = ?, title = ?, createdAt = ?, completedAt = ?
// where id = ?"))
// .thenReturn(statementSpec);
// when(statementSpec.params(List.of(updatedWorkout.getUserId(),
// updatedWorkout.getTitle(),
// updatedWorkout.getCreatedAt(),
// updatedWorkout.getCompletedAt(), id)))
// .thenReturn(statementSpec);
// when(statementSpec.update()).thenReturn(1);

// workoutRepository.update(updatedWorkout, id);

// verify(jdbcClient).sql(
// "update workout set userId = ?, title = ?, createdAt = ?, completedAt = ?
// where id = ?");
// verify(statementSpec).params(List.of(updatedWorkout.getUserId(),
// updatedWorkout.getTitle(),
// updatedWorkout.getCreatedAt(),
// updatedWorkout.getCompletedAt(), id));
// verify(statementSpec).update();
// }

// @Test
// @DisplayName("test update - Unhappy Path")
// void testUpdateUnhappy() {
// Workout updatedWorkout = workout;
// updatedWorkout.setTitle("New Title");

// int id = workout.getId();

// when(jdbcClient.sql(
// "update workout set userId = ?, title = ?, createdAt = ?, completedAt = ?
// where id = ?"))
// .thenReturn(statementSpec);
// when(statementSpec.params(List.of(updatedWorkout.getUserId(),
// updatedWorkout.getTitle(),
// updatedWorkout.getCreatedAt(),
// updatedWorkout.getCompletedAt(), id)))
// .thenReturn(statementSpec);
// when(statementSpec.update()).thenReturn(0);

// try {
// workoutRepository.update(updatedWorkout, id);
// fail("Expected an exception to be thrown due to unsuccessful creation");
// } catch (IllegalStateException e) {
// // Verify that the exception message contains the expected failure reason
// assertTrue(e.getMessage().contains("Failed to update workout"));
// }

// verify(jdbcClient).sql(
// "update workout set userId = ?, title = ?, createdAt = ?, completedAt = ?
// where id = ?");
// verify(statementSpec).params(List.of(updatedWorkout.getUserId(),
// updatedWorkout.getTitle(),
// updatedWorkout.getCreatedAt(),
// updatedWorkout.getCompletedAt(), id));
// verify(statementSpec).update();
// }

// @Test
// @DisplayName("test delete - Happy Path")
// void testDelete() {
// int id = workout.getId();
// when(jdbcClient.sql("delete from workout where id =
// :id")).thenReturn(statementSpec);
// when(statementSpec.param("id", id)).thenReturn(statementSpec);
// when(statementSpec.update()).thenReturn(1);

// workoutRepository.delete(id);

// verify(jdbcClient).sql("delete from workout where id = :id");
// verify(statementSpec).param("id", id);
// verify(statementSpec).update();
// }

// @Test
// @DisplayName("test delete - Unhappy Path")
// void testDeleteUnhappy() {
// int id = 0;
// when(jdbcClient.sql("delete from workout where id =
// :id")).thenReturn(statementSpec);
// when(statementSpec.param("id", id)).thenReturn(statementSpec);
// when(statementSpec.update()).thenReturn(0);

// try {
// workoutRepository.delete(id);
// fail("Expected an exception to be thrown due to unsuccessful creation");
// } catch (IllegalStateException e) {
// // Verify that the exception message contains the expected failure reason
// assertTrue(e.getMessage().contains("Failed to delete workout"));
// }

// verify(jdbcClient).sql("delete from workout where id = :id");
// verify(statementSpec).param("id", id);
// verify(statementSpec).update();
// }

// @Test
// void testFindByUserId() {
// Workout additionaWorkout = setupCompletedWorkout(2, "AdditionalTitle", time,
// time);
// Integer userId = 2;

// log.debug("************ addiontional workout {}", additionaWorkout);
// log.debug("************ workoutNoAddedDates workout {}",
// workoutNoAddedDates);

// when(jdbcClient.sql("select * from workout where user_id = :user_id"))
// .thenReturn(statementSpec);
// when(statementSpec.query()).thenReturn(resultQuerySpec);
// when(statementSpec.query(Workout.class)).thenReturn(mappedQuerySpec);
// when(resultQuerySpec.listOfRows()).thenReturn(List.of(
// Map.of("id", workoutNoAddedDates.getId(), "userId",
// workoutNoAddedDates.getUserId(),
// "title", workoutNoAddedDates.getTitle(), "createdAt",
// workoutNoAddedDates.getCreatedAt() == null ? null
// : workoutNoAddedDates.getCreatedAt(),
// "completedAt",
// workoutNoAddedDates.getCompletedAt() == null ? null
// : workoutNoAddedDates.getCompletedAt()),
// Map.of("id", additionaWorkout.getId(), "userId",
// additionaWorkout.getUserId(), "title",
// additionaWorkout.getTitle(), "createdAt",
// workoutNoAddedDates.getCreatedAt() == null ? null
// : workoutNoAddedDates.getCreatedAt(),
// "completedAt", workoutNoAddedDates.getCompletedAt() == null ? null
// : workoutNoAddedDates.getCompletedAt())));

// List<Workout> workouts = workoutRepository.findByUserId(userId);

// assertNotNull(workouts);
// assertEquals(2, workouts.size());
// assertEquals("Test Title2", workouts.get(0).getTitle());

// verify(jdbcClient).sql("select * from workout where user_id = :user_id");
// verify(statementSpec).query(Workout.class);
// verify(resultQuerySpec.listOfRows());
// }
// }
