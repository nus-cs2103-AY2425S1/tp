package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.GenderMatchesKeywordsPredicate;

public class ConfirmCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ClearCommand.setIsClear(true);

        assertCommandSuccess(new ConfirmCommand(), model, ConfirmCommand.MESSAGE_SUCCESS_FULL_CLEAR, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());
        ClearCommand.setIsClear(true);

        assertCommandSuccess(new ConfirmCommand(), model, ConfirmCommand.MESSAGE_SUCCESS_FULL_CLEAR, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBookFilteredList_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearCommand.setIsClear(true);

        model.updateFilteredPersonList(new GenderMatchesKeywordsPredicate(Arrays.asList("m")));
        expectedModel.updateFilteredPersonList(new GenderMatchesKeywordsPredicate(Arrays.asList("m")));
        while (!expectedModel.getFilteredPersonList().isEmpty()) {
            Person personToDelete = expectedModel.getFilteredPersonList().get(0);
            expectedModel.deletePerson(personToDelete);
        }

        assertCommandSuccess(new ConfirmCommand(), model, ConfirmCommand.MESSAGE_SUCCESS_FILTERED_CLEAR, expectedModel);
    }

    @Test
    public void execute_notIsClear_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearCommand.setIsClear(false);
        ConfirmCommand confirmCommand = new ConfirmCommand();

        assertCommandFailure(confirmCommand, model,
                Messages.MESSAGE_UNKNOWN_COMMAND);
    }
}
