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

    private Model model = new ModelManager(new AddressBook(), new UserPrefs());

    @Test
    public void execute_validPaymentDetails_success() throws Exception {
        String dateTime = "2024-11-11 1400";
        Person person = new PersonBuilder().withName("Alice")
                .withSchedule(new String[]{dateTime}, new String[]{""}).build();
        model.addPerson(person);

        PaymentCommand paymentCommand = new PaymentCommand(new Name("Alice"), dateTime, true);
        CommandResult commandResult = paymentCommand.execute(model);

        assertEquals(String.format(PaymentCommand.MESSAGE_PAYMENT_SUCCESS, "Alice", dateTime, "Paid"),
                commandResult.getFeedbackToUser());

        // Verify that the payment status was updated
        Person updatedPerson = model.getFilteredPersonList().get(0);
        assertTrue(updatedPerson.getSchedules().iterator().next().getPaymentStatus());
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        PaymentCommand paymentCommand = new PaymentCommand(new Name("Unknown"), "2024-11-11 1400", true);
        assertThrows(CommandException.class, () -> paymentCommand.execute(model));
    }

    @Test
    public void execute_scheduleNotFound_throwsCommandException() {
        Person person = new PersonBuilder().withName("Bob")
                .withSchedule(new String[]{"2024-11-11 1400"}, new String[]{""}).build();
        model.addPerson(person);

        PaymentCommand paymentCommand = new PaymentCommand(new Name("Bob"), "2024-12-12 1400", true);
        assertThrows(CommandException.class, () -> paymentCommand.execute(model));
    }

    @Test
    public void equals() {
        PaymentCommand command1 = new PaymentCommand(new Name("Alice"), "2024-11-11 1400", true);
        PaymentCommand command2 = new PaymentCommand(new Name("Alice"), "2024-11-11 1400", true);
        PaymentCommand command3 = new PaymentCommand(new Name("Bob"), "2024-11-11 1400", true);
        PaymentCommand command4 = new PaymentCommand(new Name("Alice"), "2024-12-12 1400", true);
        PaymentCommand command5 = new PaymentCommand(new Name("Alice"), "2024-11-11 1400", false);

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
    }
}
