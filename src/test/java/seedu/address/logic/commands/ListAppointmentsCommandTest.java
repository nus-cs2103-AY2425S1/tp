package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ListAppointmentsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    @Test
    public void execute_emptyAddressBook_success() {
        CommandResult result = new ListAppointmentsCommand(Optional.empty(),
                Optional.empty()).execute(model);
        assertEquals("Listed 0 upcoming appointments", result.getFeedbackToUser().trim());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_addressBookWithAppointments_success() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
        Person person1 = new PersonBuilder().withName("Alice")
                .withSchedule(new String[]{now.plusDays(1).format(inputFormatter)}, new String[]{""}).build();
        Person person2 = new PersonBuilder().withName("Bob")
                .withSchedule(new String[]{now.plusDays(2).format(inputFormatter)}, new String[]{""}).build();
        Person person3 = new PersonBuilder().withName("Charlie")
                .withSchedule(new String[]{now.minusDays(1).format(inputFormatter)}, new String[]{""}).build();
        model.addPerson(person1);
        model.addPerson(person2);
        model.addPerson(person3);
        expectedModel.addPerson(person1);
        expectedModel.addPerson(person2);
        expectedModel.addPerson(person3);

        CommandResult result = new ListAppointmentsCommand(Optional.empty(),
                Optional.empty()).execute(model);

        String expectedMessage = String.format("Listed 2 upcoming appointments\n%s: %s\n%s: %s",
                person1.getName(), now.plusDays(1).format(outputFormatter),
                person2.getName(), now.plusDays(2).format(outputFormatter));
        assertEquals(expectedMessage, result.getFeedbackToUser().trim());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_addressBookWithDateFilter_success() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
        Person person1 = new PersonBuilder().withName("Alice")
                .withSchedule(new String[]{now.plusDays(1).format(inputFormatter)}, new String[]{""}).build();
        Person person2 = new PersonBuilder().withName("Bob")
                .withSchedule(new String[]{now.plusDays(2).format(inputFormatter)}, new String[]{""}).build();
        model.addPerson(person1);
        model.addPerson(person2);
        expectedModel.addPerson(person1);
        expectedModel.addPerson(person2);

        CommandResult result = new ListAppointmentsCommand(Optional.of(now.plusDays(1).toLocalDate()),
                Optional.empty()).execute(model);

        String expectedMessage = String.format("Listed 1 upcoming appointments\n%s: %s",
                person1.getName(), now.plusDays(1).format(outputFormatter));
        assertEquals(expectedMessage, result.getFeedbackToUser().trim());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_addressBookWithTimeFilter_success() {
        LocalDate testDate = LocalDate.of(2024, 12, 10);
        LocalTime testTime = LocalTime.of(14, 0);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");

        // Create persons with appointments at different times
        Person person1 = new PersonBuilder().withName("Alice")
                .withSchedule(new String[]{testDate.atTime(testTime).format(inputFormatter)},
                        new String[]{""}).build();
        Person person2 = new PersonBuilder().withName("Bob")
                .withSchedule(new String[]{testDate.atTime(testTime.plusHours(1)).format(inputFormatter)},
                        new String[]{""}).build();
        Person person3 = new PersonBuilder().withName("Charlie")
                .withSchedule(new String[]{testDate.atTime(testTime.minusHours(1))
                        .format(inputFormatter)}, new String[]{""})
                .build();

        model.addPerson(person1);
        model.addPerson(person2);
        model.addPerson(person3);
        expectedModel.addPerson(person1);
        expectedModel.addPerson(person2);
        expectedModel.addPerson(person3);

        // Execute command with date and time filter
        ListAppointmentsCommand command = new ListAppointmentsCommand(Optional.of(testDate), Optional.of(testTime));
        CommandResult result = command.execute(model);

        // Only person1 should match the filter
        String expectedMessage = String.format("Listed 1 upcoming appointments\n%s: %s",
                person1.getName(), testDate.atTime(testTime).format(outputFormatter));

        assertEquals(expectedMessage, result.getFeedbackToUser().trim());
        assertEquals(expectedModel, model);
    }
}
