package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalParams.PARAMS_ARRAY_ALL;
import static seedu.address.testutil.TypicalParams.PARAMS_ARRAY_FIRST;
import static seedu.address.testutil.TypicalParams.PARAMS_ARRAY_SECOND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook2;

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
        model = new ModelManager(getTypicalAddressBook2(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validParamsUnfilteredList_success() {
        GetCommand getCommand = new GetCommand(PARAMS_ARRAY_ALL);
        ObservableList<Person> personList = expectedModel.getAddressBook().getPersonList();
        String names = "";
        String phoneNumbers = "";
        String emails = "";
        String addresses = "";
        for (int i = 0; i < personList.size() - 1; i++) {
            names += personList.get(i).getName() + "," + "\n";
        }
        names += personList.get(personList.size() - 1).getName() + "\n\n";
        for (int i = 0; i < personList.size() - 1; i++) {
            phoneNumbers += personList.get(i).getPhone() + "," + "\n";
        }
        phoneNumbers += personList.get(personList.size() - 1).getPhone() + "\n\n";
        for (int i = 0; i < personList.size() - 1; i++) {
            emails += personList.get(i).getEmail() + "," + "\n";
        }
        emails += personList.get(personList.size() - 1).getEmail() + "\n\n";
        for (int i = 0; i < personList.size() - 1; i++) {
            addresses += personList.get(i).getAddress() + "," + "\n";
        }
        addresses += personList.get(personList.size() - 1).getAddress() + "\n\n";
        String expectedMessage = String.format(GetCommand.MESSAGE_GET_PARAMETER_SUCCESS,
                "NAME", names)
                + String.format(GetCommand.MESSAGE_GET_PARAMETER_SUCCESS, "PHONE NUMBER", phoneNumbers)
                + String.format(GetCommand.MESSAGE_GET_PARAMETER_SUCCESS, "EMAIL", emails)
                + String.format(GetCommand.MESSAGE_GET_PARAMETER_SUCCESS, "ADDRESS", addresses);

        assertCommandSuccess(getCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidParamsUnfilteredList_throwsCommandException() {
        String[] invalidParam = {"p/", "b/"};
        GetCommand getCommand = new GetCommand(invalidParam);

        assertCommandFailure(getCommand, model, Messages.MESSAGE_INVALID_PARAMETER);
    }

    @Test
    public void execute_validParamsFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        ObservableList<Person> curr = model.getFilteredPersonList();
        GetCommand getCommand = new GetCommand(PARAMS_ARRAY_SECOND);

        String expectedMessage = String.format(GetCommand.MESSAGE_GET_PARAMETER_SUCCESS,
                "NAME", curr.get(0).getName()) + "\n\n"
                + String.format(GetCommand.MESSAGE_GET_PARAMETER_SUCCESS,
                "ADDRESS", curr.get(0).getAddress()) + "\n\n";
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        assertCommandSuccess(getCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidParamsFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        String[] invalidParams = {"p/", "b/"};

        GetCommand getCommand = new GetCommand(invalidParams);

        assertCommandFailure(getCommand, model, Messages.MESSAGE_INVALID_PARAMETER);
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
