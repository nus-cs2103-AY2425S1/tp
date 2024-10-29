package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        // Set up persons with different deadlines (all in progress by default)
        personDueToday = new PersonBuilder().withName("John")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .build();
        personDueInFuture = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().plusDays(2).format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .build();
        personOverdue = new PersonBuilder().withName("Bob")
                .withDeadline(LocalDate.now().minusDays(1).format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .build();

        reminderManager = new ReminderManager(personList);
    }

    @Test
    public void getCurrentReminder_completedProject_notShownInReminder() {
        Person completedPerson = new PersonBuilder().withName("John")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("completed")
                .build();
        personList.add(completedPerson);

        assertEquals("No upcoming or overdue reminders.", reminderManager.currentReminderProperty().get());
    }

    @Test
    public void getCurrentReminder_mixedCompletionStatus_showsOnlyIncomplete() {
        Person completedPerson = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("completed")
                .build();
        personList.addAll(personDueToday, completedPerson);

        assertEquals("John has deadline due today.", reminderManager.currentReminderProperty().get());
    }

    @Test
    public void getCurrentReminder_allCompleted_noReminders() {
        Person completedPerson1 = new PersonBuilder().withName("John")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("completed")
                .build();
        Person completedPerson2 = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("completed")
                .build();
        personList.addAll(completedPerson1, completedPerson2);

        assertEquals("No upcoming or overdue reminders.", reminderManager.currentReminderProperty().get());
    }

    @Test
    public void getCurrentReminder_threePersonsOneCompleted_showsTwoNames() {
        Person person2 = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .build();
        Person completedPerson = new PersonBuilder().withName("Charlie")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("completed")
                .build();
        personList.addAll(personDueToday, person2, completedPerson);

        assertEquals("John and Alice have deadlines due today.", reminderManager.currentReminderProperty().get());
    }

    @Test
    public void getCurrentReminder_fourPersonsTwoCompleted_showsCorrectCount() {
        Person person2 = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .build();
        Person completedPerson1 = new PersonBuilder().withName("Charlie")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("completed")
                .build();
        Person person4 = new PersonBuilder().withName("David")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .build();
        personList.addAll(personDueToday, person2, completedPerson1, person4);

        assertEquals("John, Alice and David have deadlines due today.", reminderManager
                .currentReminderProperty().get());
    }

    @Test
    public void propertyBinding_projectStatusChanges() {
        StringProperty reminderProperty = reminderManager.currentReminderProperty();

        // Add two people
        Person person2 = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .build();
        personList.addAll(personDueToday, person2);
        assertEquals("John and Alice have deadlines due today.", reminderProperty.get());

        // Update one person to completed
        Person completedPerson = new PersonBuilder(personDueToday)
                .withProjectStatus("completed")
                .build();
        int index = personList.indexOf(personDueToday);
        personList.set(index, completedPerson);
        assertEquals("Alice has deadline due today.", reminderProperty.get());

        // Update the other person to completed
        Person completedPerson2 = new PersonBuilder(person2)
                .withProjectStatus("completed")
                .build();
        index = personList.indexOf(person2);
        personList.set(index, completedPerson2);
        assertEquals("No upcoming or overdue reminders.", reminderProperty.get());
    }

    @Test
    public void getCurrentReminder_overdueWithCompletedProjects() {
        Person overduePerson2 = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().minusDays(1).format(DATE_FORMATTER))
                .withProjectStatus("completed")
                .build();
        personList.addAll(personOverdue, overduePerson2);

        assertEquals("Bob has overdue deadline by 1 day.", reminderManager.currentReminderProperty().get());
    }

    // Existing tests remain but need to add project status...
    @Test
    public void getCurrentReminder_noPersons_returnsNoReminders() {
        assertEquals("No upcoming or overdue reminders.", reminderManager.currentReminderProperty().get());
    }

    @Test
    public void getCurrentReminder_singleIncompletePerson_showsCorrectFormat() {
        personList.add(personDueToday);
        assertEquals("John has deadline due today.", reminderManager.currentReminderProperty().get());
    }

    @Test
    public void getCurrentReminder_multipleIncompleteFutureDeadlines() {
        Person person2 = new PersonBuilder().withName("Charlie")
                .withDeadline(LocalDate.now().plusDays(2).format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .build();
        Person person3 = new PersonBuilder().withName("David")
                .withDeadline(LocalDate.now().plusDays(2).format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .build();
        personList.addAll(personDueInFuture, person2, person3);

        assertEquals("Alice, Charlie and David have deadlines in 2 days.", reminderManager
                .currentReminderProperty().get());
    }
}
