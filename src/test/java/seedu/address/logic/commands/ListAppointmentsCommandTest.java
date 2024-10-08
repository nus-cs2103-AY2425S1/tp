package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ListAppointmentsCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_emptyAddressBook_success() {
        CommandResult result = new ListAppointmentsCommand().execute(model);
        assertEquals("Listed 0 upcoming appointments", result.getFeedbackToUser().trim());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_addressBookWithUpcomingAppointments_success() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        Person personWithUpcomingAppointment = new PersonBuilder().withName("Alice").withSchedule(now.plusDays(1).format(formatter)).build();
        Person personWithPastAppointment = new PersonBuilder().withName("Bob").withSchedule(now.minusDays(1).format(formatter)).build();
        model.addPerson(personWithUpcomingAppointment);
        model.addPerson(personWithPastAppointment);
        expectedModel.addPerson(personWithUpcomingAppointment);
        expectedModel.addPerson(personWithPastAppointment);

        CommandResult result = new ListAppointmentsCommand().execute(model);

        String expectedMessage = String.format("Listed 1 upcoming appointments\n%s: %s",
                personWithUpcomingAppointment.getName(), personWithUpcomingAppointment.getSchedule());
        assertEquals(expectedMessage, result.getFeedbackToUser().trim());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_addressBookWithMultipleUpcomingAppointments_success() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        Person person1 = new PersonBuilder().withName("Alice").withSchedule(now.plusDays(1).format(formatter)).build();
        Person person2 = new PersonBuilder().withName("Bob").withSchedule(now.plusDays(2).format(formatter)).build();
        Person person3 = new PersonBuilder().withName("Charlie").withSchedule(now.minusDays(1).format(formatter)).build();
        model.addPerson(person1);
        model.addPerson(person2);
        model.addPerson(person3);
        expectedModel.addPerson(person1);
        expectedModel.addPerson(person2);
        expectedModel.addPerson(person3);

        CommandResult result = new ListAppointmentsCommand().execute(model);

        String expectedMessage = String.format("Listed 2 upcoming appointments\n%s: %s\n%s: %s",
                person1.getName(), person1.getSchedule(),
                person2.getName(), person2.getSchedule());
        assertEquals(expectedMessage, result.getFeedbackToUser().trim());
        assertEquals(expectedModel, model);
    }
}