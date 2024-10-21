package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.BookmarkCommand.MESSAGE_BOOKMARK_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCompanyAtIndex;
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


public class BookmarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model bookmarkedModel = new ModelManager(getTypicalBookmarkedAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // Set up the company to bookmark
        Company companyToBookmark = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        BookmarkCommand bookmarkCommand = new BookmarkCommand(INDEX_FIRST_COMPANY);

        String expectedMessage = String.format(BookmarkCommand.MESSAGE_BOOKMARK_SUCCESS,
                Messages.format(companyToBookmark));

        // Create a new company with the original company's isBookmark field set to true
        Company companyBookmarked = new Company(companyToBookmark.getName(), companyToBookmark.getPhone(),
                companyToBookmark.getEmail(), companyToBookmark.getAddress(), companyToBookmark.getCareerPageUrl(),
                companyToBookmark.getTags(), new Bookmark(true));
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.setCompany(companyToBookmark, companyBookmarked);

        // Assert that the command is successful
        assertCommandSuccess(bookmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_existingBookmarkUnfilteredList_showsFailure() {
        // Set up the already bookmarked company
        BookmarkCommand bookmarkCommand = new BookmarkCommand(INDEX_FIRST_COMPANY);
        Company companyToBookmark = bookmarkedModel.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        String expectedMessage = String.format(MESSAGE_BOOKMARK_FAILURE, Messages.format(companyToBookmark));

        // Assert that the command is successful but returns a failure message because
        // the company is already a bookmarked company
        assertCommandSuccess(bookmarkCommand, bookmarkedModel, expectedMessage, bookmarkedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        BookmarkCommand bookmarkCommand = new BookmarkCommand(outOfBoundIndex);

        assertCommandFailure(bookmarkCommand, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // Filter the list
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        // Set up the company to be bookmarked
        Company companyToBookmark = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        BookmarkCommand bookmarkCommand = new BookmarkCommand(INDEX_FIRST_COMPANY);

        String expectedMessage = String.format(BookmarkCommand.MESSAGE_BOOKMARK_SUCCESS,
                Messages.format(companyToBookmark));

        // Create a new company with the original company's isBookmark field set to true
        Company companyBookmarked = new Company(companyToBookmark.getName(), companyToBookmark.getPhone(),
                companyToBookmark.getEmail(), companyToBookmark.getAddress(), companyToBookmark.getCareerPageUrl(),
                companyToBookmark.getTags(), new Bookmark(true));
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.setCompany(companyToBookmark, companyBookmarked);

        // Assert that the command is successful
        assertCommandSuccess(bookmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_existingBookmarkFilteredList_showsFailure() {
        showCompanyAtIndex(bookmarkedModel, INDEX_FIRST_COMPANY);
        BookmarkCommand bookmarkCommand = new BookmarkCommand(INDEX_FIRST_COMPANY);

        Company companyToBookmark = bookmarkedModel.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        String expectedMessage = String.format(MESSAGE_BOOKMARK_FAILURE, Messages.format(companyToBookmark));

        assertCommandSuccess(bookmarkCommand, bookmarkedModel, expectedMessage, bookmarkedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        Index outOfBoundIndex = INDEX_SECOND_COMPANY;
        // Ensures that out of bound index is still within bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getCompanyList().size());
        BookmarkCommand bookmarkCommand = new BookmarkCommand(outOfBoundIndex);

        assertCommandFailure(bookmarkCommand, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }
}
