package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCompanyAtIndex;
import static seedu.address.logic.commands.RemoveBookmarkCommand.MESSAGE_REMOVE_BOOKMARK_FAILURE;
import static seedu.address.testutil.TypicalBookmarkedCompanies.getTypicalBookmarkedAddressBook;
import static seedu.address.testutil.TypicalCompanies.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.company.Bookmark;
import seedu.address.model.company.Company;

public class RemoveBookmarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model bookmarkedModel = new ModelManager(getTypicalBookmarkedAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // Set up the company to remove bookmark
        Company companyToRemoveBookmark = bookmarkedModel.getFilteredCompanyList().get(INDEX_FIRST_COMPANY
                .getZeroBased());
        RemoveBookmarkCommand removeBookmarkCommand = new RemoveBookmarkCommand(INDEX_FIRST_COMPANY);

        String expectedMessage = String.format(RemoveBookmarkCommand.MESSAGE_REMOVE_BOOKMARK_SUCCESS,
                Messages.format(companyToRemoveBookmark));

        // Create a new company with the original company's isBookmark value set to false
        Company companyRemovedBookmark = new Company(companyToRemoveBookmark.getName(),
                companyToRemoveBookmark.getPhone(), companyToRemoveBookmark.getEmail(),
                companyToRemoveBookmark.getAddress(), companyToRemoveBookmark.getCareerPageUrl(),
                companyToRemoveBookmark.getTags(), new Bookmark(false));
        ModelManager expectedModel = new ModelManager(bookmarkedModel.getAddressBook(), new UserPrefs());

        expectedModel.setCompany(companyToRemoveBookmark, companyRemovedBookmark);

        // Assert that the command is successful
        assertCommandSuccess(removeBookmarkCommand, bookmarkedModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentBookmarkUnfilteredList_throwsCommandException() {
        RemoveBookmarkCommand removeBookmarkCommand = new RemoveBookmarkCommand(INDEX_FIRST_COMPANY);

        assertCommandSuccess(removeBookmarkCommand, model, MESSAGE_REMOVE_BOOKMARK_FAILURE, model);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(bookmarkedModel.getFilteredCompanyList().size() + 1);
        RemoveBookmarkCommand removeBookmarkCommand = new RemoveBookmarkCommand(outOfBoundIndex);

        assertCommandFailure(removeBookmarkCommand, bookmarkedModel, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // Filter the list
        showCompanyAtIndex(bookmarkedModel, INDEX_FIRST_COMPANY);

        // Set up the company to remove bookmark
        Company companyToRemoveBookmark = bookmarkedModel.getFilteredCompanyList().get(INDEX_FIRST_COMPANY
                .getZeroBased());
        RemoveBookmarkCommand removeBookmarkCommand = new RemoveBookmarkCommand(INDEX_FIRST_COMPANY);

        String expectedMessage = String.format(RemoveBookmarkCommand.MESSAGE_REMOVE_BOOKMARK_SUCCESS,
                Messages.format(companyToRemoveBookmark));

        // Create a new company with the original company's isBookmark field set to false
        Company companyRemovedBookmark = new Company(companyToRemoveBookmark.getName(),
                companyToRemoveBookmark.getPhone(), companyToRemoveBookmark.getEmail(),
                companyToRemoveBookmark.getAddress(), companyToRemoveBookmark.getCareerPageUrl(),
                companyToRemoveBookmark.getTags(), new Bookmark(false));
        ModelManager expectedModel = new ModelManager(bookmarkedModel.getAddressBook(), new UserPrefs());

        expectedModel.setCompany(companyToRemoveBookmark, companyRemovedBookmark);

        // Assert that the command works
        assertCommandSuccess(removeBookmarkCommand, bookmarkedModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentBookmarkFilteredList_throwsCommandException() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);
        RemoveBookmarkCommand removeBookmarkCommand = new RemoveBookmarkCommand(INDEX_FIRST_COMPANY);

        assertCommandSuccess(removeBookmarkCommand, model, MESSAGE_REMOVE_BOOKMARK_FAILURE, model);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCompanyAtIndex(bookmarkedModel, INDEX_FIRST_COMPANY);

        Index outOfBoundIndex = INDEX_SECOND_COMPANY;
        // Ensures that out of bound index is still within bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < bookmarkedModel.getAddressBook().getCompanyList().size());
        RemoveBookmarkCommand removeBookmarkCommand = new RemoveBookmarkCommand(outOfBoundIndex);

        assertCommandFailure(removeBookmarkCommand, bookmarkedModel, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }
}
