package spleetwaise.transaction.logic.parser;

import static spleetwaise.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static spleetwaise.transaction.logic.commands.FilterCommand.MESSAGE_USAGE;
import static spleetwaise.transaction.logic.parser.CommandParserTestUtil.assertParseFailure;
import static spleetwaise.transaction.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static spleetwaise.transaction.testutil.TransactionBuilder.DEFAULT_CATEGORY;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import spleetwaise.address.logic.Messages;
import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.model.CommonModelManager;
import spleetwaise.transaction.logic.commands.FilterCommand;
import spleetwaise.transaction.model.FilterCommandPredicate;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.TransactionBookModelManager;
import spleetwaise.transaction.model.filterpredicate.AmountFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.AmountSignFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.CategoryFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.DateFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.DescriptionFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.PersonFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.StatusFilterPredicate;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.model.transaction.Transaction;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class FilterCommandParserTest {

    private static final Person testPerson = TypicalTransactions.SEANOWESME.getPerson();
    private static final Amount testAmount = TypicalTransactions.SEANOWESME.getAmount();
    private static final Description testDescription = TypicalTransactions.SEANOWESME.getDescription();
    private static final Date testDate = TypicalTransactions.SEANOWESME.getDate();
    private static final Status testStatus = TypicalTransactions.SEANOWESME.getStatus();
    private static final Category testCategory = DEFAULT_CATEGORY;
    private static final AddressBookModel abModel = new AddressBookModelManager();
    private static final TransactionBookModel txnModel = new TransactionBookModelManager();
    private static final Predicate<Transaction> testPersonPred = new PersonFilterPredicate(testPerson);
    private static final Predicate<Transaction> testAmountPred = new AmountFilterPredicate(testAmount);
    private static final Predicate<Transaction> testDescriptionPred = new DescriptionFilterPredicate(testDescription);
    private static final Predicate<Transaction> testDatePred = new DateFilterPredicate(testDate);
    private static final Predicate<Transaction> testStatusPred = new StatusFilterPredicate(testStatus);
    private static final Predicate<Transaction> testAmountSignPred =
            new AmountSignFilterPredicate(AmountSignFilterPredicate.POSITIVE_SIGN);
    private static final Predicate<Transaction> testCategoryPred = new CategoryFilterPredicate(testCategory);

    private final FilterCommandParser parser = new FilterCommandParser();

    @BeforeAll
    public static void setUp() {
        abModel.setAddressBook(TypicalPersons.getTypicalAddressBook());
        txnModel.setTransactionBook(TypicalTransactions.getTypicalTransactionBook());
        CommonModelManager.initialise(abModel, txnModel);
    }

    @Test
    public void parse_personField_success() {
        String userInput = " 1";
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testPersonPred);
        FilterCommandPredicate expectedPred = new FilterCommandPredicate(subPredicates);

        assertParseSuccess(parser, userInput, new FilterCommand(expectedPred));
    }

    @Test
    public void parse_amountField_success() {
        String userInput = " amt/9999999999.99";
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testAmountPred);
        FilterCommandPredicate expectedPred = new FilterCommandPredicate(subPredicates);

        assertParseSuccess(parser, userInput, new FilterCommand(expectedPred));
    }

    @Test
    public void parse_descriptionField_success() {
        String userInput = " desc/Sean owes me a lot for a landed property in Sentosa";
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testDescriptionPred);
        FilterCommandPredicate expectedPred = new FilterCommandPredicate(subPredicates);

        assertParseSuccess(parser, userInput, new FilterCommand(expectedPred));
    }

    @Test
    public void parse_dateField_success() {
        String userInput = " date/10102024";
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testDatePred);
        FilterCommandPredicate expectedPred = new FilterCommandPredicate(subPredicates);

        assertParseSuccess(parser, userInput, new FilterCommand(expectedPred));
    }

    @Test
    public void parse_statusField_success() {
        String userInput = " status/" + Status.NOT_DONE_STATUS;
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testStatusPred);
        FilterCommandPredicate expectedPred = new FilterCommandPredicate(subPredicates);

        assertParseSuccess(parser, userInput, new FilterCommand(expectedPred));
    }

    @Test
    public void parse_categoryField_success() {
        String userInput = " cat/food";
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testCategoryPred);
        FilterCommandPredicate expectedPred = new FilterCommandPredicate(subPredicates);

        assertParseSuccess(parser, userInput, new FilterCommand(expectedPred));
    }

    @Test
    public void parse_allFields_success() {
        String userInput = " 1 amt/9999999999.99  "
                + "desc/Sean owes me a lot for a landed property in Sentosa date/10102024 status/"
                + Status.NOT_DONE_STATUS + " cat/food";
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testPersonPred);
        subPredicates.add(testAmountPred);
        subPredicates.add(testDescriptionPred);
        subPredicates.add(testDatePred);
        subPredicates.add(testStatusPred);
        subPredicates.add(testCategoryPred);
        FilterCommandPredicate expectedPred = new FilterCommandPredicate(subPredicates);

        assertParseSuccess(parser, userInput, new FilterCommand(expectedPred));
    }

    @Test
    public void parse_amtSignField_success() {
        String userInput = " amtsign/" + AmountSignFilterPredicate.POSITIVE_SIGN;
        ArrayList<Predicate<Transaction>> subPredicates = new ArrayList<>();
        subPredicates.add(testAmountSignPred);
        FilterCommandPredicate expectedPred = new FilterCommandPredicate(subPredicates);

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

        Integer outOfBoundsIndexInt = abModel.getFilteredPersonList().size() + 1;
        userInput = " " + outOfBoundsIndexInt;
        assertParseFailure(
                parser, userInput, String.format(
                        Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                        outOfBoundsIndexInt
                ));
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

    @Test
    public void parse_invalidCategoryField_failure() {
        String userInput = " cat/ ";
        assertParseFailure(parser, userInput, Category.MESSAGE_CONSTRAINTS);
    }
}
