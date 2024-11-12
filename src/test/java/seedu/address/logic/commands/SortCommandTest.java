package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.comparator.AddressCompare;
import seedu.address.model.person.comparator.EcNameCompare;
import seedu.address.model.person.comparator.EcNumberCompare;
import seedu.address.model.person.comparator.EmailCompare;
import seedu.address.model.person.comparator.NameCompare;
import seedu.address.model.person.comparator.PhoneCompare;
import seedu.address.model.person.comparator.RegisterNumberCompare;
import seedu.address.model.person.comparator.SexCompare;
import seedu.address.model.person.comparator.StudentClassCompare;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validSortAttribute_success() {
        Comparator<Person> comparator;
        SortCommand command;
        Model expectedModel;
        String expectedMessage;
        ParserUtil.SortAttribute sortAttribute;

        // EP: NAME
        sortAttribute = ParserUtil.SortAttribute.NAME;
        command = new SortCommand(sortAttribute);
        comparator = new NameCompare();
        expectedMessage = String.format(SortCommand.MESSAGE_SORTED_SUCCESS, sortAttribute);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sortFilteredPersonList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // EP: PHONE
        sortAttribute = ParserUtil.SortAttribute.PHONE;
        command = new SortCommand(sortAttribute);
        comparator = new PhoneCompare();
        expectedMessage = String.format(SortCommand.MESSAGE_SORTED_SUCCESS, sortAttribute);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sortFilteredPersonList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // EP: ADDRESS
        sortAttribute = ParserUtil.SortAttribute.ADDRESS;
        command = new SortCommand(sortAttribute);
        comparator = new AddressCompare();
        expectedMessage = String.format(SortCommand.MESSAGE_SORTED_SUCCESS, sortAttribute);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sortFilteredPersonList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // EP: EMERGENCYCONTACTNAME
        sortAttribute = ParserUtil.SortAttribute.EMERGENCYCONTACTNAME;
        command = new SortCommand(sortAttribute);
        comparator = new EcNameCompare();
        expectedMessage = String.format(SortCommand.MESSAGE_SORTED_SUCCESS, sortAttribute);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sortFilteredPersonList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // EP: EMERGENCYCONTACTNUMBER
        sortAttribute = ParserUtil.SortAttribute.EMERGENCYCONTACTNUMBER;
        command = new SortCommand(sortAttribute);
        comparator = new EcNumberCompare();
        expectedMessage = String.format(SortCommand.MESSAGE_SORTED_SUCCESS, sortAttribute);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sortFilteredPersonList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // EP: EMAIL
        sortAttribute = ParserUtil.SortAttribute.EMAIL;
        command = new SortCommand(sortAttribute);
        comparator = new EmailCompare();
        expectedMessage = String.format(SortCommand.MESSAGE_SORTED_SUCCESS, sortAttribute);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sortFilteredPersonList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // EP: REGISTERNUMBER
        sortAttribute = ParserUtil.SortAttribute.REGISTERNUMBER;
        command = new SortCommand(sortAttribute);
        comparator = new RegisterNumberCompare();
        expectedMessage = String.format(SortCommand.MESSAGE_SORTED_SUCCESS, sortAttribute);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sortFilteredPersonList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // EP: SEX
        sortAttribute = ParserUtil.SortAttribute.SEX;
        command = new SortCommand(sortAttribute);
        comparator = new SexCompare();
        expectedMessage = String.format(SortCommand.MESSAGE_SORTED_SUCCESS, sortAttribute);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sortFilteredPersonList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // EP: STUDENTCLASS
        sortAttribute = ParserUtil.SortAttribute.STUDENTCLASS;
        command = new SortCommand(sortAttribute);
        comparator = new StudentClassCompare();
        expectedMessage = String.format(SortCommand.MESSAGE_SORTED_SUCCESS, sortAttribute);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sortFilteredPersonList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // EP: NONE
        sortAttribute = ParserUtil.SortAttribute.NONE;
        command = new SortCommand(sortAttribute);
        comparator = null;
        expectedMessage = SortCommand.MESSAGE_UNSORTED_SUCCESS;
        expectedModel.sortFilteredPersonList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }


    @Test
    public void equals() {
        final SortCommand standardCommand = new SortCommand(ParserUtil.SortAttribute.NAME);

        // EP: same values -> returns true
        SortCommand commandWithSameValues = new SortCommand(ParserUtil.SortAttribute.NAME);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // EP: same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // EP: null -> returns false
        assertFalse(standardCommand.equals(null));

        // EP: different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // EP: different SortAttribute -> returns false
        assertFalse(standardCommand.equals(new SortCommand(ParserUtil.SortAttribute.PHONE)));
    }
}
