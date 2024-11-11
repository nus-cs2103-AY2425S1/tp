package spleetwaise.transaction.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static spleetwaise.transaction.model.transaction.Status.NOT_DONE_STATUS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.logic.commands.CommandResult;
import spleetwaise.commons.logic.commands.exceptions.CommandException;
import spleetwaise.commons.model.CommonModelManager;
import spleetwaise.commons.testutil.Assert;
import spleetwaise.transaction.model.TransactionBookModelManager;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.model.transaction.Transaction;

public class AddCommandTest {

    private static final Person testPerson = TypicalPersons.ALICE;
    private static final Amount testAmount = new Amount("1.23");
    private static final Description testDescription = new Description("description");
    private static final Date testDate = new Date("01012024");
    private static final Status testStatus = new Status(NOT_DONE_STATUS);
    private static final Set<Category> testCategories = new HashSet<>(List.of(new Category("FOOD")));
    private static final Transaction testTxn = new Transaction(
            testPerson, testAmount, testDescription, testDate, testCategories, testStatus);

    @BeforeEach
    void setup() {
        CommonModelManager.initialise(
                new AddressBookModelManager(TypicalPersons.getTypicalAddressBook()),
                new TransactionBookModelManager()
        );
    }

    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_validTransaction_success() {
        AddCommand cmd = new AddCommand(testTxn);
        CommandResult cmdRes = assertDoesNotThrow(cmd::execute);

        String expectedString = String.format(
                "%s [%s] (%s): %s on %s for $%s with categories: %s",
                testPerson.getName(),
                testStatus,
                testPerson.getPhone(),
                testDescription, testDate, testAmount, testCategories
        );
        assertEquals(
                String.format(AddCommand.MESSAGE_SUCCESS, expectedString),
                cmdRes.getFeedbackToUser()
        );
        assertTrue(CommonModelManager.getInstance().hasTransaction(testTxn));
    }

    @Test
    public void equals_sameTransaction_returnsTrue() {
        AddCommand cmd1 = new AddCommand(testTxn);
        AddCommand cmd2 = new AddCommand(testTxn);

        assertEquals(cmd1, cmd1);
        assertEquals(cmd1, cmd2);
        assertEquals(cmd1.toString(), cmd2.toString());
        assertEquals(cmd2.toString(), cmd2.toString());
    }

    @Test
    public void equals_diffTransaction_returnsFalse() {
        AddCommand cmd1 = new AddCommand(testTxn);
        Transaction testTxn2 = new Transaction(
                TypicalPersons.BOB, testAmount, testDescription, testDate, testCategories, testStatus);
        AddCommand cmd2 = new AddCommand(testTxn2);

        assertNotEquals(cmd1, cmd2);
    }

    @Test
    public void equals_null_returnsFalse() {
        AddCommand cmd1 = new AddCommand(testTxn);
        ClearCommand cmd2 = new ClearCommand();

        assertNotEquals(null, cmd1);
        assertNotEquals(cmd2, cmd1);
    }

    @Test
    public void execute_duplicateTransaction_exceptionThrown() {
        CommonModelManager.getInstance().addTransaction(testTxn);

        AddCommand cmd = new AddCommand(testTxn);
        Assert.assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_TXN, cmd::execute);
    }
}
