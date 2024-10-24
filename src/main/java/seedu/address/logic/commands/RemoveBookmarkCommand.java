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
 * Remove bookmark from a company identified by its displayed index from the address book.
 */
public class RemoveBookmarkCommand extends Command {

    public static final String COMMAND_WORD = "removebm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": removes bookmarked company based on the index"
            + "in the list of contacts.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";
    public static final String MESSAGE_REMOVE_BOOKMARK_SUCCESS = "Removed bookmarked company: %1$s";

    public static final String MESSAGE_REMOVE_BOOKMARK_FAILURE = "Unable to remove, company is not bookmarked.";

    private final Index index;

    /**
     * Constructor for the RemoveBookmarkCommand
     *
     * @param index The index of the bookmarked company in the address book.
     */
    public RemoveBookmarkCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Company> lastShownList = model.getFilteredCompanyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        Company companyToRemoveBookmark = lastShownList.get(index.getZeroBased());

        // Check if company is even bookmarked in the first place
        if (!companyToRemoveBookmark.getIsBookmark().getIsBookmarkValue()) {
            return new CommandResult(String.format(MESSAGE_REMOVE_BOOKMARK_FAILURE,
                    Messages.format(companyToRemoveBookmark)));
        }

        Company companyRemovedBookmark = new Company(companyToRemoveBookmark.getName(),
                companyToRemoveBookmark.getPhone(), companyToRemoveBookmark.getEmail(),
                companyToRemoveBookmark.getAddress(), companyToRemoveBookmark.getCareerPageUrl(),
                companyToRemoveBookmark.getApplicationStatus(),
                companyToRemoveBookmark.getTags(), new Bookmark(false));

        model.setCompany(companyToRemoveBookmark, companyRemovedBookmark);
        model.updateFilteredCompanyList(Model.PREDICATE_SHOW_ALL_COMPANIES);

        return new CommandResult(String.format(MESSAGE_REMOVE_BOOKMARK_SUCCESS,
                Messages.format(companyToRemoveBookmark)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemoveBookmarkCommand)) {
            return false;
        }

        RemoveBookmarkCommand e = (RemoveBookmarkCommand) other;
        return index.equals(e.index);
    }
}
