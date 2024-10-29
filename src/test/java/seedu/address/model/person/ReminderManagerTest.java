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

        // Set up persons with different deadlines (all in progress by default and active client status)
        personDueToday = new PersonBuilder().withName("John")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("active")
                .build();
        personDueInFuture = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().plusDays(2).format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("active")
                .build();
        personOverdue = new PersonBuilder().withName("Bob")
                .withDeadline(LocalDate.now().minusDays(1).format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("active")
                .build();

        reminderManager = new ReminderManager(personList);
    }

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
    public void getCurrentReminder_completedProject_notShownInReminder() {
        Person completedPerson = new PersonBuilder().withName("John")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("completed")
                .withClientStatus("active")
                .build();
        personList.add(completedPerson);

        assertEquals("No upcoming or overdue reminders.", reminderManager.currentReminderProperty().get());
    }

    @Test
    public void getCurrentReminder_inactiveClients_notShownInReminder() {
        Person oldPerson = new PersonBuilder().withName("John")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("old")
                .build();
        Person potentialPerson = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().plusDays(2).format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("potential")
                .build();
        Person unresponsivePerson = new PersonBuilder().withName("Bob")
                .withDeadline(LocalDate.now().minusDays(1).format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("unresponsive")
                .build();
        Person blacklistedPerson = new PersonBuilder().withName("Charlie")
                .withDeadline(LocalDate.now().plusDays(2).format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("blacklisted")
                .build();

        personList.addAll(oldPerson, potentialPerson, unresponsivePerson, blacklistedPerson);
        assertEquals("No upcoming or overdue reminders.", reminderManager.currentReminderProperty().get());
    }

    @Test
    public void getCurrentReminder_activePersonAndOtherStatuses_showsOnlyActive() {
        Person oldPerson = new PersonBuilder().withName("John")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("old")
                .build();
        Person potentialPerson = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().plusDays(2).format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("potential")
                .build();
        Person unresponsivePerson = new PersonBuilder().withName("Bob")
                .withDeadline(LocalDate.now().minusDays(1).format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("unresponsive")
                .build();
        Person blacklistedPerson = new PersonBuilder().withName("Charlie")
                .withDeadline(LocalDate.now().plusDays(2).format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("blacklisted")
                .build();
        Person activePerson = new PersonBuilder().withName("David")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("active")
                .build();

        personList.addAll(oldPerson, potentialPerson, unresponsivePerson, blacklistedPerson, activePerson);
        assertEquals("David has deadline due today.", reminderManager.currentReminderProperty().get());
    }

    @Test
    public void getCurrentReminder_twoActivePersons_showsBothNames() {
        Person person2 = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("active")
                .build();
        personList.addAll(personDueToday, person2);

        assertEquals("John and Alice have deadlines due today.", reminderManager.currentReminderProperty().get());
    }

    @Test
    public void getCurrentReminder_threeActivePersons_showsAllNames() {
        Person person2 = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("active")
                .build();
        Person person3 = new PersonBuilder().withName("Charlie")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("active")
                .build();
        personList.addAll(personDueToday, person2, person3);

        assertEquals("John, Alice and Charlie have deadlines due today.", reminderManager.currentReminderProperty().get());
    }

    @Test
    public void getCurrentReminder_fourActivePersons_showsThreeNamesAndMore() {
        Person person2 = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("active")
                .build();
        Person person3 = new PersonBuilder().withName("Charlie")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("active")
                .build();
        Person person4 = new PersonBuilder().withName("David")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("active")
                .build();
        personList.addAll(personDueToday, person2, person3, person4);

        assertEquals("John, Alice, Charlie and 1 more have deadlines due today.",
                reminderManager.currentReminderProperty().get());
    }

    @Test
    public void getCurrentReminder_fiveActivePersons_showsThreeNamesAndMore() {
        Person person2 = new PersonBuilder().withName("Alice")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("active")
                .build();
        Person person3 = new PersonBuilder().withName("Charlie")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("active")
                .build();
        Person person4 = new PersonBuilder().withName("David")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("active")
                .build();
        Person person5 = new PersonBuilder().withName("Eve")
                .withDeadline(LocalDate.now().format(DATE_FORMATTER))
                .withProjectStatus("in progress")
                .withClientStatus("active")
                .build();
        personList.addAll(personDueToday, person2, person3, person4, person5);

        assertEquals("John, Alice, Charlie and 2 more have deadlines due today.",
                reminderManager.currentReminderProperty().get());
    }
}
