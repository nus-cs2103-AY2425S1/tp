package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.GetCommand.getAddressMessage;
import static seedu.address.logic.commands.GetCommand.getEmailMessage;
import static seedu.address.logic.commands.GetCommand.getNameMessage;
import static seedu.address.logic.commands.GetCommand.getPhoneNumberMessage;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalParams.PARAMS_ARRAY_ALL;
import static seedu.address.testutil.TypicalParams.PARAMS_ARRAY_FIRST;
import static seedu.address.testutil.TypicalParams.PARAMS_ARRAY_SECOND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook2;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code GetCommand}.
 */
public class GetCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook2(), new ArrayList<>(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new ArrayList<>(), new UserPrefs());
    }

    @Test
    public void execute_validParamsUnfilteredList_success() {
        GetCommand getCommand = new GetCommand(PARAMS_ARRAY_ALL);
        ObservableList<Person> personList = expectedModel.getAddressBook().getPersonList();
        String names = getNameMessage(personList);
        String phoneNumbers = getPhoneNumberMessage(personList);
        String emails = getEmailMessage(personList);
        String addresses = getAddressMessage(personList);

        String expectedMessage = names + phoneNumbers + emails + addresses;
        assertCommandSuccess(getCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidParamsUnfilteredList_throwsCommandException() {
        String[] invalidParam = {"p/", "b/"};
        GetCommand getCommand = new GetCommand(invalidParam);

        assertCommandFailure(getCommand, model, Messages.MESSAGE_INVALID_PARAMETERS);
    }

    @Test
    public void execute_validParamsFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        ObservableList<Person> currList = model.getFilteredPersonList();
        GetCommand getCommand = new GetCommand(PARAMS_ARRAY_SECOND);

        String names = getNameMessage(currList);
        String addresses = getAddressMessage(currList);
        String expectedMessage = names + addresses;
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        assertCommandSuccess(getCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidParamsFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        String[] invalidParams = {"p/", "b/"};

        GetCommand getCommand = new GetCommand(invalidParams);

        assertCommandFailure(getCommand, model, Messages.MESSAGE_INVALID_PARAMETERS);
    }

    @Test
    public void equals() {
        GetCommand getFirstCommand = new GetCommand(PARAMS_ARRAY_FIRST);
        GetCommand getSecondCommand = new GetCommand(PARAMS_ARRAY_SECOND);

        // same object -> returns true
        assertTrue(getFirstCommand.equals(getFirstCommand));

        // different types -> returns false
        assertFalse(getFirstCommand.equals(1));

        // null -> returns false
        assertFalse(getFirstCommand.equals(null));

        // different parameter list -> returns false
        assertFalse(getFirstCommand.equals(getSecondCommand));
    }
}
