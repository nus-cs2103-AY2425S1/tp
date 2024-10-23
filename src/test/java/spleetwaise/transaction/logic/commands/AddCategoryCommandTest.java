package spleetwaise.transaction.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import spleetwaise.address.model.person.Person;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.logic.commands.CommandResult;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Transaction;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class AddCategoryCommandTest {
    private static final Person testPerson = TypicalPersons.ALICE;
    private static final Amount testAmount = new Amount("1.23");
    private static final Description testDescription = new Description("description");
    private static final Date testDate = new Date("01012024");
    private static final Transaction testTxn = new Transaction(testPerson, testAmount, testDescription, testDate);
    private static final Category category = new Category("FOOD");
    private static final Transaction testTxn1 = TypicalTransactions.CARLBUYING;
    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new AddCategoryCommand(null, null));
    }

    @Test
    public void execute_validCategory_success() {
        AddCategoryCommand cmd = new AddCategoryCommand(testTxn1, category);
        CommandResult cmdRes = assertDoesNotThrow(cmd::execute);

        String expectedString = String.format("Category added to transaction: [%s] with [%s]",
                testTxn1.getId(), category);
        assertEquals(expectedString, cmdRes.getFeedbackToUser());
    }

    @Test
    public void equals_diffTransaction_returnsFalse() {
        AddCategoryCommand cmd1 = new AddCategoryCommand(testTxn1, category);
        Transaction testTxn2 = new Transaction(TypicalPersons.BOB, testAmount, testDescription, testDate);
        AddCategoryCommand cmd2 = new AddCategoryCommand(testTxn2, category);

        assertNotEquals(cmd1, cmd2);
    }

    @Test
    public void equals_null_returnsFalse() {
        AddCategoryCommand cmd1 = new AddCategoryCommand(testTxn1, category);

        assertNotEquals(null, cmd1);
    }
}
