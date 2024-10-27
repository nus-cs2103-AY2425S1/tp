package seedu.address.model.person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.testutil.PersonBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReminderManagerTest {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private ObservableList<Person> personList;
    private ReminderManager reminderManager;

    private Person person1;
    private Person person2;
    private Person person3;

    @BeforeEach
    public void setUp() {
        personList = FXCollections.observableArrayList();

        // Set up persons with different deadlines
        person1 = new PersonBuilder().withName("John")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER)) // Due today
                .build();
        person2 = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().plusDays(2).format(DATE_FORMATTER)) // Due in 2 days
                .build();
        person3 = new PersonBuilder().withName("Bob")
                .withDeadline(LocalDate.now().minusDays(1).format(DATE_FORMATTER)) // Overdue
                .build();

        // Add persons to the list
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);

        // Initialize ReminderManager with the person list
        reminderManager = new ReminderManager(personList);
    }

    @Test
    public void getLatestReminders_dueToday() {
        List<String> reminders = reminderManager.getLatestReminders();
        assertEquals(1, reminders.size());
        assertEquals("John's deadline is due today.", reminders.get(0));  // John is due today
    }

    @Test
    public void getLatestReminders_dueInFuture() {
        personList.remove(person1);  // Remove John to test future deadlines

        List<String> reminders = reminderManager.getLatestReminders();
        assertEquals(1, reminders.size());
        assertEquals("Alice's deadline is in 2 days.", reminders.get(0));  // Alice is due in 2 days
    }

    @Test
    public void getNextReminder_sameDeadline_rotation() {
        // Add another person with the same deadline as John
        Person person4 = new PersonBuilder().withName("Chris")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER)) // Also due today
                .build();
        personList.add(person4);

        // First reminder should be for John, who is due today
        assertEquals("John's deadline is due today.", reminderManager.getNextReminder());

        // Second reminder should be for Chris, who shares the same deadline
        assertEquals("Chris's deadline is due today.", reminderManager.getNextReminder());

        // Should rotate back to John
        assertEquals("John's deadline is due today.", reminderManager.getNextReminder());
    }

    @Test
    public void getNextReminder_noReminders() {
        personList.clear();  // Clear the list to simulate no upcoming reminders

        String reminder = reminderManager.getNextReminder();
        assertEquals("No upcoming reminders.", reminder);  // No reminders available
    }

    @Test
    public void getNextReminder_futureDeadline_doesNotRotate() {
        // Future deadlines should not rotate until they are the latest
        personList.remove(person1);  // Remove John to test future deadlines
        assertEquals("Alice's deadline is in 2 days.", reminderManager.getNextReminder());
        assertEquals("Alice's deadline is in 2 days.", reminderManager.getNextReminder());
    }
}
