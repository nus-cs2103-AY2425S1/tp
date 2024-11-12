package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.appointment.Appointment;

public class FindAppointmentCommandTest {

    @Test
    public void equals() {
        // Create sample predicates for testing
        Predicate<Appointment> firstPredicate = appointment ->
                appointment.getPerson().getName().equals("Alice")
                        && appointment.getAppointmentDateTime().toLocalDate().equals(LocalDateTime.of(2024,
                        10, 20, 0, 0).toLocalDate());
        Predicate<Appointment> secondPredicate = appointment ->
                appointment.getPerson().getName().equals("Bob")
                        && appointment.getAppointmentDateTime().toLocalDate().equals(LocalDateTime.of(2024,
                        11, 20, 0, 0).toLocalDate());

        FindAppointmentCommand findFirstCommand = new FindAppointmentCommand(firstPredicate);
        FindAppointmentCommand findSecondCommand = new FindAppointmentCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindAppointmentCommand findFirstCommandCopy = new FindAppointmentCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different predicate -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

}
