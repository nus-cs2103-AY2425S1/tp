package spleetwaise.transaction.logic.parser;

import static spleetwaise.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static spleetwaise.transaction.logic.commands.FilterCommand.MESSAGE_USAGE;
import static spleetwaise.transaction.logic.parser.CommandParserTestUtil.assertParseFailure;
import static spleetwaise.transaction.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import spleetwaise.address.logic.Messages;
import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.model.CommonModel;
import spleetwaise.transaction.logic.commands.FilterCommand;
import spleetwaise.transaction.model.FilterCommandPredicate;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.TransactionBookModelManager;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class FilterCommandParserTest {

    private static final Person testPerson = TypicalTransactions.SEANOWESME.getPerson();
    private static final Amount testAmount = TypicalTransactions.SEANOWESME.getAmount();
    private static final Description testDescription = TypicalTransactions.SEANOWESME.getDescription();
    private static final Date testDate = TypicalTransactions.SEANOWESME.getDate();
    private static final Status testStatus = TypicalTransactions.SEANOWESME.getStatus();
    private static final AddressBookModel abModel = new AddressBookModelManager();
    private static final TransactionBookModel txnModel = new TransactionBookModelManager();

    private final FilterCommandParser parser = new FilterCommandParser();

    @BeforeAll
    public static void setUp() {
        abModel.setAddressBook(TypicalPersons.getTypicalAddressBook());
        txnModel.setTransactionBook(TypicalTransactions.getTypicalTransactionBook());
        CommonModel.initialise(abModel, txnModel);
    }

    @Test
    public void parse_personField_success() {
        String userInput = " 1";
        FilterCommandPredicate expectedPred = new FilterCommandPredicate(testPerson, null, null, null, null);

        assertParseSuccess(parser, userInput, new FilterCommand(expectedPred));
    }

    @Test
    public void parse_amountField_success() {
        String userInput = " amt/9999999999.99";
        FilterCommandPredicate expectedPred = new FilterCommandPredicate(null, testAmount, null, null, null);

        assertParseSuccess(parser, userInput, new FilterCommand(expectedPred));
    }

    @Test
    public void parse_descriptionField_success() {
        String userInput = " desc/Sean owes me a lot for a landed property in Sentosa";
        FilterCommandPredicate expectedPred = new FilterCommandPredicate(null, null, testDescription, null, null);

        assertParseSuccess(parser, userInput, new FilterCommand(expectedPred));
    }

    @Test
    public void parse_dateField_success() {
        String userInput = " date/10102024";
        FilterCommandPredicate expectedPred = new FilterCommandPredicate(null, null, null, testDate, null);

        assertParseSuccess(parser, userInput, new FilterCommand(expectedPred));
    }

    @Test
    public void parse_statusField_success() {
        String userInput = " status/" + Status.NOT_DONE_STATUS;
        FilterCommandPredicate expectedPred = new FilterCommandPredicate(null, null, null, null, testStatus);

        assertParseSuccess(parser, userInput, new FilterCommand(expectedPred));
    }

    @Test
    public void parse_allFields_success() {
        String userInput = " 1 amt/9999999999.99  "
                + "desc/Sean owes me a lot for a landed property in Sentosa date/10102024 status/"
                + Status.NOT_DONE_STATUS;
        FilterCommandPredicate expectedPred = new FilterCommandPredicate(testPerson, testAmount,
                testDescription, testDate, testStatus
        );

        assertParseSuccess(parser, userInput, new FilterCommand(expectedPred));
    }

    @Test
    public void parse_noField_failure() {
        String userInput = " ";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndexField_failure() {
        String userInput = " 0";
        assertParseFailure(parser, userInput, ParserUtil.MESSAGE_INVALID_INDEX);

        userInput = " invalid";
        assertParseFailure(parser, userInput, ParserUtil.MESSAGE_INVALID_INDEX);

        userInput = " " + abModel.getFilteredPersonList().size() + 1;
        assertParseFailure(parser, userInput, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void parse_invalidAmountField_failure() {
        String userInput = " amt/9.999";
        assertParseFailure(parser, userInput, Amount.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidDescriptionField_failure() {
        String userInput = " desc/";
        assertParseFailure(parser, userInput, Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidDateField_failure() {
        String userInput = " date/1010202";
        assertParseFailure(parser, userInput, Date.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidStatusField_failure() {
        String userInput = " status/invalid";
        assertParseFailure(parser, userInput, Status.MESSAGE_CONSTRAINTS);
    }
}
