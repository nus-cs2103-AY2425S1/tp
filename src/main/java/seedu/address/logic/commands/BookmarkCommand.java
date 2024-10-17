package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
    public static final String MESSAGE_REMOVE_BOOKMARK_SUCCESS = "Removed bookmarked company: %1$s";

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
        companyToBookmark.setBookmark(!companyToBookmark.getIsBookmark());

        model.setCompany(companyToBookmark, companyToBookmark);
        model.updateFilteredCompanyList(Model.PREDICATE_SHOW_ALL_COMPANIES);

        return new CommandResult(generateSuccessMessage(companyToBookmark));
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

    private String generateSuccessMessage(Company companyToBookmark) {
        String message = companyToBookmark.getIsBookmark() ? MESSAGE_BOOKMARK_SUCCESS : MESSAGE_REMOVE_BOOKMARK_SUCCESS;
        return String.format(message, Messages.format(companyToBookmark));
    }
}
