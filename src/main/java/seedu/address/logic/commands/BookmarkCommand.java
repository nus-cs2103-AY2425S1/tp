package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.company.Bookmark;
import seedu.address.model.company.Company;

/**
 * Bookmarks a company identified using its displayed index from the address book.
 */
public class BookmarkCommand extends Command {

    public static final String COMMAND_WORD = "bookmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": bookmarks the company based on the index"
            + "in the list of contacts.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_BOOKMARK_SUCCESS = "Bookmarked company: %1$s";

    public static final String MESSAGE_BOOKMARK_FAILURE = "Company is already bookmarked: %1$s";

    private final Index index;

    /**
     * Constructor for the BookmarkCommand
     *
     * @param index The index of the company to be bookmarked in the address book.
     */
    public BookmarkCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Company> lastShownList = model.getFilteredCompanyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        Company companyToBookmark = lastShownList.get(index.getZeroBased());

        // Check if company is not bookmarked in the first place
        if (companyToBookmark.getIsBookmark().getIsBookmarkValue()) {
            return new CommandResult(String.format(MESSAGE_BOOKMARK_FAILURE,
                    Messages.format(companyToBookmark)));
        }

        Company companyBookmarked = new Company(companyToBookmark.getName(), companyToBookmark.getPhone(),
                companyToBookmark.getEmail(), companyToBookmark.getAddress(), companyToBookmark.getCareerPageUrl(),
                companyToBookmark.getApplicationStatus(),
                companyToBookmark.getTags(), new Bookmark(true));

        model.setCompany(companyToBookmark, companyBookmarked);
        model.updateFilteredCompanyList(Model.PREDICATE_SHOW_ALL_COMPANIES);

        return new CommandResult(String.format(MESSAGE_BOOKMARK_SUCCESS,
                Messages.format(companyToBookmark)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof BookmarkCommand)) {
            return false;
        }

        BookmarkCommand e = (BookmarkCommand) other;
        return index.equals(e.index);
    }
}
