package spleetwaise.transaction.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import spleetwaise.address.logic.commands.CommandResult;
import spleetwaise.address.logic.commands.exceptions.CommandException;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.testutil.Assert;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.transaction.model.ModelManager;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Transaction;

public class AddCommandTest {

    private static Person testPerson = TypicalPersons.ALICE;
    private static Amount testAmount = new Amount("+1.23");
    private static Description testDescription = new Description("description");
    private static Date testDate = new Date("01012024");

    private static Transaction testTxn = new Transaction(testPerson, testAmount, testDescription, testDate);

    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_validPerson_success() {
        ModelManager modelManager = new ModelManager();

        AddCommand cmd = new AddCommand(testTxn);
        CommandResult cmdRes = assertDoesNotThrow(() -> cmd.execute(modelManager));

        String expectedString = String.format("[%s] Alice Pauline(94351253): description on 01/01/2024 for $+1.23",
                testTxn.getId());
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, expectedString),
                cmdRes.getFeedbackToUser());
        assertTrue(modelManager.hasTransaction(testTxn));
    }

    @Test
    public void execute_duplicatePerson_exceptionThrown() {
        ModelManager modelManager = new ModelManager();
        modelManager.addTransaction(testTxn);

        AddCommand cmd = new AddCommand(testTxn);
        Assert.assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_TXN, () ->
                cmd.execute(modelManager));
    }

    @Test
    public void equals_sameTransaction_returnsTrue() {
        AddCommand cmd1 = new AddCommand(testTxn);
        AddCommand cmd2 = new AddCommand(testTxn);

        assertTrue(cmd1.equals(cmd1));
        assertTrue(cmd1.equals(cmd2));
    }

    @Test
    public void equals_diffTransaction_returnsFalse() {
        AddCommand cmd1 = new AddCommand(testTxn);
        Transaction testTxn2 = new Transaction(TypicalPersons.BOB, testAmount, testDescription, testDate);
        AddCommand cmd2 = new AddCommand(testTxn2);

        assertFalse(cmd1.equals(cmd2));
    }

    @Test
    public void equals_null_returnsFalse() {
        AddCommand cmd1 = new AddCommand(testTxn);

        assertFalse(cmd1.equals(null));
    }

}
