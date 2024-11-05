package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PaymentCommandTest {

    private static final String VALID_DATETIME = "2024-11-11 1400";
    private static final String INVALID_DATETIME = "2024-12-12 1400";
    private static final String VALID_NAME = "Alice";
    private static final String INVALID_NAME = "Unknown";

    private Model model = new ModelManager(new AddressBook(), new UserPrefs());

    @Test
    public void execute_markAsPaid_success() throws Exception {
        Person person = new PersonBuilder().withName(VALID_NAME)
                .withSchedule(new String[]{VALID_DATETIME}, new String[]{""}).build();
        model.addPerson(person);

        PaymentCommand paymentCommand = new PaymentCommand(new Name(VALID_NAME), VALID_DATETIME, true);
        CommandResult commandResult = paymentCommand.execute(model);

        assertEquals(String.format(PaymentCommand.MESSAGE_PAYMENT_SUCCESS, VALID_NAME, VALID_DATETIME, "Paid"),
                commandResult.getFeedbackToUser());

        // Verify payment status was updated
        Person updatedPerson = model.getFilteredPersonList().get(0);
        assertTrue(updatedPerson.getSchedules().iterator().next().getPaymentStatus());
    }

    @Test
    public void execute_markAsUnpaid_success() throws Exception {
        // Create person with a paid appointment
        Person person = new PersonBuilder().withName(VALID_NAME)
                .withSchedule(new String[]{VALID_DATETIME}, new String[]{""}).build();
        model.addPerson(person);

        // First mark as paid
        PaymentCommand markPaidCommand = new PaymentCommand(new Name(VALID_NAME), VALID_DATETIME, true);
        markPaidCommand.execute(model);

        // Then mark as unpaid
        PaymentCommand markUnpaidCommand = new PaymentCommand(new Name(VALID_NAME), VALID_DATETIME, false);
        CommandResult commandResult = markUnpaidCommand.execute(model);

        assertEquals(String.format(PaymentCommand.MESSAGE_PAYMENT_SUCCESS, VALID_NAME, VALID_DATETIME, "Unpaid"),
                commandResult.getFeedbackToUser());

        // Verify payment status was updated
        Person updatedPerson = model.getFilteredPersonList().get(0);
        assertFalse(updatedPerson.getSchedules().iterator().next().getPaymentStatus());
    }

    @Test
    public void execute_duplicatePaidPayment_throwsCommandException() throws Exception {
        Person person = new PersonBuilder().withName(VALID_NAME)
                .withSchedule(new String[]{VALID_DATETIME}, new String[]{""}).build();
        model.addPerson(person);

        // Mark as paid first time
        PaymentCommand firstPaymentCommand = new PaymentCommand(new Name(VALID_NAME), VALID_DATETIME, true);
        firstPaymentCommand.execute(model);

        // Attempt to mark as paid second time
        PaymentCommand secondPaymentCommand = new PaymentCommand(new Name(VALID_NAME), VALID_DATETIME, true);
        String expectedMessage = String.format(PaymentCommand.MESSAGE_DUPLICATE_PAYMENT, VALID_NAME, VALID_DATETIME);
        assertThrows(CommandException.class, expectedMessage, () -> secondPaymentCommand.execute(model));
    }

    @Test
    public void execute_duplicateUnpaidPayment_throwsCommandException() throws Exception {
        Person person = new PersonBuilder().withName(VALID_NAME)
                .withSchedule(new String[]{VALID_DATETIME}, new String[]{""}).build();
        model.addPerson(person);

        // Attempt to mark as unpaid when already unpaid
        PaymentCommand unpaidCommand = new PaymentCommand(new Name(VALID_NAME), VALID_DATETIME, false);
        String expectedMessage = String.format(PaymentCommand.MESSAGE_UNPAID_PAYMENT, VALID_NAME, VALID_DATETIME);
        assertThrows(CommandException.class, expectedMessage, () -> unpaidCommand.execute(model));
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        PaymentCommand paymentCommand = new PaymentCommand(new Name(INVALID_NAME), VALID_DATETIME, true);
        String expectedMessage = String.format(PaymentCommand.MESSAGE_PERSON_NOT_FOUND, INVALID_NAME);
        assertThrows(CommandException.class, expectedMessage, () -> paymentCommand.execute(model));
    }

    @Test
    public void execute_scheduleNotFound_throwsCommandException() {
        Person person = new PersonBuilder().withName(VALID_NAME)
                .withSchedule(new String[]{VALID_DATETIME}, new String[]{""}).build();
        model.addPerson(person);

        PaymentCommand paymentCommand = new PaymentCommand(new Name(VALID_NAME), INVALID_DATETIME, true);
        String expectedMessage = String.format(PaymentCommand.MESSAGE_SCHEDULE_NOT_FOUND, VALID_NAME, INVALID_DATETIME);
        assertThrows(CommandException.class, expectedMessage, () -> paymentCommand.execute(model));
    }

    @Test
    public void equals() {
        PaymentCommand command1 = new PaymentCommand(new Name("Alice"), VALID_DATETIME, true);
        PaymentCommand command2 = new PaymentCommand(new Name("Alice"), VALID_DATETIME, true);
        PaymentCommand command3 = new PaymentCommand(new Name("Bob"), VALID_DATETIME, true);
        PaymentCommand command4 = new PaymentCommand(new Name("Alice"), INVALID_DATETIME, true);
        PaymentCommand command5 = new PaymentCommand(new Name("Alice"), VALID_DATETIME, false);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        assertTrue(command1.equals(command2));

        // different person -> returns false
        assertFalse(command1.equals(command3));

        // different date -> returns false
        assertFalse(command1.equals(command4));

        // different payment status -> returns false
        assertFalse(command1.equals(command5));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different types -> returns false
        assertFalse(command1.equals("string"));
    }
}
