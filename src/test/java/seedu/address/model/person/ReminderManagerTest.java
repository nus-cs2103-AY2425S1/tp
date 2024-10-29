package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.testutil.PersonBuilder;

public class ReminderManagerTest {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private ObservableList<Person> personList;
    private ReminderManager reminderManager;

    private Person personDueToday;
    private Person personDueInFuture;
    private Person personOverdue;

    @BeforeEach
    public void setUp() {
        personList = FXCollections.observableArrayList();

        // Set up persons with different deadlines
        personDueToday = new PersonBuilder().withName("John")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .build();
        personDueInFuture = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().plusDays(2).format(DATE_FORMATTER))
                .build();
        personOverdue = new PersonBuilder().withName("Bob")
                .withDeadline(LocalDate.now().minusDays(1).format(DATE_FORMATTER))
                .build();

        reminderManager = new ReminderManager(personList);
    }

    @Test
    public void getCurrentReminder_twoPersonsSameDeadline_showsBothNames() {
        Person person2 = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .build();
        personList.addAll(personDueToday, person2);

        String reminder = reminderManager.currentReminderProperty().get();
        assertEquals("John and Alice have deadlines due today.", reminder);
    }

    @Test
    public void getCurrentReminder_threePersonsSameDeadline_showsAllNames() {
        // Add three people with same deadline
        Person person2 = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .build();
        Person person3 = new PersonBuilder().withName("Charlie")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .build();
        personList.addAll(personDueToday, person2, person3);

        String reminder = reminderManager.currentReminderProperty().get();
        assertEquals("John, Alice and Charlie have deadlines due today.", reminder);
    }

    @Test
    public void getCurrentReminder_fourPersonsSameDeadline_showsThreeNamesAndMore() {
        // Add four people with same deadline
        Person person2 = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .build();
        Person person3 = new PersonBuilder().withName("Charlie")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .build();
        Person person4 = new PersonBuilder().withName("David")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .build();
        personList.addAll(personDueToday, person2, person3, person4);

        String reminder = reminderManager.currentReminderProperty().get();
        assertEquals("John, Alice and 2 more have deadlines due today.", reminder);
    }

    @Test
    public void getCurrentReminder_fivePersonsSameDeadline_showsThreeNamesAndMore() {
        // Add five people with same deadline
        Person person2 = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .build();
        Person person3 = new PersonBuilder().withName("Charlie")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .build();
        Person person4 = new PersonBuilder().withName("David")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .build();
        Person person5 = new PersonBuilder().withName("Eve")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .build();
        personList.addAll(personDueToday, person2, person3, person4, person5);

        String reminder = reminderManager.currentReminderProperty().get();
        assertEquals("John, Alice and 3 more have deadlines due today.", reminder);
    }

    @Test
    public void getCurrentReminder_fourOverduePersons_showsThreeNamesAndMore() {
        // Add four people with same overdue deadline
        Person person2 = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().minusDays(1).format(DATE_FORMATTER))
                .build();
        Person person3 = new PersonBuilder().withName("Charlie")
                .withDeadline(LocalDate.now().minusDays(1).format(DATE_FORMATTER))
                .build();
        Person person4 = new PersonBuilder().withName("David")
                .withDeadline(LocalDate.now().minusDays(1).format(DATE_FORMATTER))
                .build();
        personList.addAll(personOverdue, person2, person3, person4);

        String reminder = reminderManager.currentReminderProperty().get();
        assertEquals("Bob, Alice and 2 more have overdue deadlines by 1 day.", reminder);
    }

    @Test
    public void getCurrentReminder_fourFutureDeadlines_showsThreeNamesAndMore() {
        // Add four people with same future deadline
        Person person2 = new PersonBuilder().withName("Charlie")
                .withDeadline(LocalDate.now().plusDays(2).format(DATE_FORMATTER))
                .build();
        Person person3 = new PersonBuilder().withName("David")
                .withDeadline(LocalDate.now().plusDays(2).format(DATE_FORMATTER))
                .build();
        Person person4 = new PersonBuilder().withName("Eve")
                .withDeadline(LocalDate.now().plusDays(2).format(DATE_FORMATTER))
                .build();
        personList.addAll(personDueInFuture, person2, person3, person4);

        String reminder = reminderManager.currentReminderProperty().get();
        assertEquals("Alice, Charlie and 2 more have deadlines in 2 days.", reminder);
    }

    // Original test cases remain the same...
    @Test
    public void getCurrentReminder_noPersons_returnsNoReminders() {
        assertEquals("No upcoming or overdue reminders.", reminderManager.currentReminderProperty().get());
    }

    @Test
    public void getCurrentReminder_singlePerson_showsCorrectFormat() {
        personList.add(personDueToday);
        assertEquals("John has deadline due today.", reminderManager.currentReminderProperty().get());
    }

    @Test
    public void propertyBinding_updatesWhenListChanges() {
        StringProperty reminderProperty = reminderManager.currentReminderProperty();

        // Initial state
        assertEquals("No upcoming or overdue reminders.", reminderProperty.get());

        // Add multiple persons
        Person person2 = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .build();
        Person person3 = new PersonBuilder().withName("Charlie")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .build();
        Person person4 = new PersonBuilder().withName("David")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .build();

        personList.add(personDueToday);
        assertEquals("John has deadline due today.", reminderProperty.get());

        personList.add(person2);
        assertEquals("John and Alice have deadlines due today.", reminderProperty.get());

        personList.addAll(person3, person4);
        assertEquals("John, Alice and 2 more have deadlines due today.", reminderProperty.get());

        // Remove persons
        personList.clear();
        assertEquals("No upcoming or overdue reminders.", reminderProperty.get());
    }
}