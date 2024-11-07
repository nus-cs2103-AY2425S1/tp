package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.ModelClearObserver;

public class ClearCommandTest {

    private static class ModelClearObserverStub implements ModelClearObserver {
        private boolean isCleared = false;

        @Override
        public void uddersCleared() {
            isCleared = true;
        }

        public boolean isCleared() {
            return isCleared;
        }
    }

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        // create and add observerstub to the model.
        ModelClearObserverStub observer = new ModelClearObserverStub();
        model.addObserver(observer);
        expectedModel.addObserver(observer);

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(observer.isCleared());
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        ModelClearObserverStub observer = new ModelClearObserverStub();
        model.addObserver(observer);
        expectedModel.addObserver(observer);

        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(observer.isCleared());
    }

}
