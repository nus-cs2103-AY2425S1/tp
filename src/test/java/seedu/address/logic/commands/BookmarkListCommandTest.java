package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBookmarkedCompanies.getTypicalBookmarkedAddressBook;
import static seedu.address.testutil.TypicalCompanies.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.company.Bookmark;
import seedu.address.model.company.Company;

public class BookmarkListCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model bookmarkedModel = new ModelManager(getTypicalBookmarkedAddressBook(), new UserPrefs());

    @Test
    public void execute_listWithBookmarkedCompanies_showsBookmarkedCompanies() {
        // Remove bookmark from one company so that list has companies that are not bookmarked
        Company companyToRemoveBookmark = bookmarkedModel.getFilteredCompanyList().get(INDEX_FIRST_COMPANY
                .getZeroBased());
        Company companyRemovedBookmark = new Company(companyToRemoveBookmark.getName(),
                companyToRemoveBookmark.getPhone(), companyToRemoveBookmark.getEmail(),
                companyToRemoveBookmark.getAddress(), companyToRemoveBookmark.getCareerPageUrl(),
                companyToRemoveBookmark.getApplicationStatus(), companyToRemoveBookmark.getTags(),
                new Bookmark(false), companyToRemoveBookmark.getRemark());
        bookmarkedModel.setCompany(companyToRemoveBookmark, companyRemovedBookmark);

        // Assert that the command is successful
        assertCommandSuccess(new BookmarkListCommand(), bookmarkedModel, BookmarkListCommand.MESSAGE_SUCCESS,
                bookmarkedModel);
    }
    @Test
    public void execute_noBookmarkedCompanies_showsNothing() {
        assertCommandSuccess(new BookmarkListCommand(), model, BookmarkListCommand.MESSAGE_NO_BOOKMARKED_COMPANIES,
                model);
    }

}
