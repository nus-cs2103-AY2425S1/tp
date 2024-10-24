package spleetwaise.transaction.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import spleetwaise.address.model.person.Person;
import spleetwaise.address.testutil.Assert;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.logic.commands.CommandResult;
import spleetwaise.commons.logic.commands.exceptions.CommandException;
import spleetwaise.commons.model.CommonModel;
import spleetwaise.transaction.model.TransactionBookModelManager;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Transaction;

public class AddCommandTest {

    private static final Person testPerson = TypicalPersons.ALICE;
    private static final Amount testAmount = new Amount("1.23");
    private static final Description testDescription = new Description("description");
    private static final Date testDate = new Date("01012024");
    private static final Set<Category> testCategories = new HashSet<>(List.of(new Category("FOOD")));
    private static final Transaction testTxn = new Transaction(
            testPerson, testAmount, testDescription, testDate, testCategories);

    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_validTransaction_success() {
        CommonModel.initialise(null, new TransactionBookModelManager());
        AddCommand cmd = new AddCommand(testTxn);
        CommandResult cmdRes = assertDoesNotThrow(cmd::execute);

        String expectedString = String.format(
                "[%s] Alice Pauline(94351253): description on 01/01/2024 for $1.23 with categories: [FOOD]",
                testTxn.getId()
        );
        assertEquals(
                String.format(AddCommand.MESSAGE_SUCCESS, expectedString),
                cmdRes.getFeedbackToUser()
        );
        assertTrue(CommonModel.getInstance().hasTransaction(testTxn));
    }

    @Test
    public void execute_duplicateTransaction_exceptionThrown() {
        CommonModel.initialise(null, new TransactionBookModelManager());
        CommonModel.getInstance().addTransaction(testTxn);

        AddCommand cmd = new AddCommand(testTxn);
        Assert.assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_TXN, cmd::execute);
    }

    @Test
    public void equals_sameTransaction_returnsTrue() {
        AddCommand cmd1 = new AddCommand(testTxn);
        AddCommand cmd2 = new AddCommand(testTxn);

        assertEquals(cmd1, cmd1);
        assertEquals(cmd1, cmd2);
    }

    @Test
    public void equals_diffTransaction_returnsFalse() {
        AddCommand cmd1 = new AddCommand(testTxn);
        Transaction testTxn2 = new Transaction(
                TypicalPersons.BOB, testAmount, testDescription, testDate, testCategories);
        AddCommand cmd2 = new AddCommand(testTxn2);
        Transaction testTxn3 = new Transaction(
                TypicalPersons.BOB, testAmount, testDescription, testDate,
                new HashSet<>(List.of(new Category("EXTRA"))));
        AddCommand cmd3 = new AddCommand(testTxn3);

        assertNotEquals(cmd1, cmd2);
        assertNotEquals(cmd1, cmd3);
        assertNotEquals(cmd2, cmd3);
    }

    @Test
    public void equals_null_returnsFalse() {
        AddCommand cmd1 = new AddCommand(testTxn);

        assertNotEquals(null, cmd1);
    }

}
