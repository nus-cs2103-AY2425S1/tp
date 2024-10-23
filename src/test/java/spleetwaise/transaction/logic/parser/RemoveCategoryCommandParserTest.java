package spleetwaise.transaction.logic.parser;

import static spleetwaise.transaction.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.model.CommonModel;
import spleetwaise.transaction.logic.commands.RemoveCategoryCommand;
import spleetwaise.transaction.model.TransactionBookModelManager;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Transaction;

public class RemoveCategoryCommandParserTest {
    private static final Person testPerson = TypicalPersons.ALICE;
    private static Amount testAmount = new Amount("1.23");
    private static Description testDescription = new Description("description");
    private static Category cat = new Category("FOOD");
    private RemoveCategoryCommandParser parser = new RemoveCategoryCommandParser();

    @BeforeEach
    void setup() {
        CommonModel.initialise(new AddressBookModelManager(), new TransactionBookModelManager());
    }

    @Test
    public void parse_allFieldsPresent_success() {
        CommonModel.getInstance().addPerson(testPerson);

        String userInput = " txn/1 cat/FOOD";
        Transaction txn = new Transaction(testPerson, testAmount, testDescription);
        CommonModel.getInstance().addTransaction(txn);
        assertParseSuccess(parser, userInput, new RemoveCategoryCommand(txn, cat));
    }
}
