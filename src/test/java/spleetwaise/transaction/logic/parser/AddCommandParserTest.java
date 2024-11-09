package spleetwaise.transaction.logic.parser;

import static spleetwaise.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static spleetwaise.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DATE;
import static spleetwaise.transaction.logic.parser.CommandParserTestUtil.assertParseFailure;
import static spleetwaise.transaction.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static spleetwaise.transaction.model.transaction.Date.getNowDate;
import static spleetwaise.transaction.model.transaction.Status.NOT_DONE_STATUS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.model.CommonModelManager;
import spleetwaise.transaction.logic.Messages;
import spleetwaise.transaction.logic.commands.AddCommand;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.model.transaction.Transaction;

public class AddCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

    private static final Person testPerson = TypicalPersons.ALICE;
    private static final Amount testAmount = new Amount("1.23");
    private static final Description testDescription = new Description("description");
    private static final Date testDate = new Date(getNowDate());
    private static final Set<Category> testCategories = new HashSet<>(List.of(new Category("FOOD")));
    private static final Status testStatus = new Status(NOT_DONE_STATUS);
    private static final AddressBookModel abModel = new AddressBookModelManager();

    private final AddCommandParser parser = new AddCommandParser();

    @BeforeAll
    public static void setUp() {
        abModel.addPerson(testPerson);
        CommonModelManager.initialise(abModel, null);
    }

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput =
                " 1 amt/1.23 desc/description " + PREFIX_CATEGORY + "FOOD";
        Transaction txn = new Transaction(
                testPerson, testAmount, testDescription, testDate, testCategories, testStatus);
        assertParseSuccess(parser, userInput, new AddCommand(txn));
    }

    @Test
    public void parse_withOptionalDateField_success() {
        String userInput =
                " 1 amt/1.23 desc/description " + PREFIX_DATE + getNowDate() + " " + PREFIX_CATEGORY + "FOOD";
        Transaction txn = new Transaction(
                testPerson, testAmount, testDescription, testDate, testCategories, testStatus);
        assertParseSuccess(parser, userInput, new AddCommand(txn));
    }

    @Test
    public void parse_invalidIndex_exceptionThrown() {
        String userInput = " 0 amt/1.23 desc/description date/01012024";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        userInput = " -1 amt/1.23 desc/description date/01012024";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        userInput = " invalid amt/1.23 desc/description date/01012024";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        userInput = " " + (abModel.getFilteredPersonList().size() + 1) + " amt/1.23 desc/description date/01012024";
        assertParseFailure(
                parser, userInput,
                String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, abModel.getFilteredPersonList().size() + 1)
        );
    }

    @Test
    public void parse_invalidAmount_exceptionThrown() {
        String userInput = " 1 amt/1.234 desc/description date/01012024";
        assertParseFailure(parser, userInput, Amount.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidDescription_exceptionThrown() {
        String userInput = " 1 amt/1.23 desc/ date/01012024";
        assertParseFailure(parser, userInput, Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidDate_exceptionThrown() {
        String userInput = " 1 amt/1.23 desc/test date/2024";
        assertParseFailure(parser, userInput, Date.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidStatus_exceptionThrown() {
        String userInput = " 1 amt/1.23 desc/test status/invalid";
        assertParseFailure(parser, userInput, Status.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingParam_exceptionThrown() {
        String userInput = " 1 desc/test";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

