package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCompanyAtIndex;
import static seedu.address.testutil.TypicalCompanies.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.company.Company;

public class RemoveBookmarkCommandTest {

//    private Model model = new ModelManager(getTypicalBookmarkedAddressBook(), new UserPrefs());
//
//    @Test
//    public void execute_validIndexUnfilteredList_success() {
//        Company companyToRemoveBookmark = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
//        RemoveBookmarkCommand removeBookmarkCommand = new RemoveBookmarkCommand(INDEX_FIRST_COMPANY);
//
//        String expectedMessage = String.format(RemoveBookmarkCommand.MESSAGE_REMOVE_BOOKMARK_SUCCESS,
//                Messages.format(companyToRemoveBookmark));
//
//        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
//
//        assertCommandSuccess(removeBookmarkCommand, model, expectedMessage, expectedModel);
//    }
}
