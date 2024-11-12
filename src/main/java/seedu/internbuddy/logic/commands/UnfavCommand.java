package seedu.internbuddy.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.internbuddy.model.Model.PREDICATE_SHOW_ALL_COMPANIES;

import java.util.List;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.commons.util.ToStringBuilder;
import seedu.internbuddy.logic.Messages;
import seedu.internbuddy.logic.commands.exceptions.CommandException;
import seedu.internbuddy.model.Model;
import seedu.internbuddy.model.company.Company;

/**
 * Unfavourites a company identified using it's displayed index from the address book.
 */
public class UnfavCommand extends Command {
    public static final String COMMAND_WORD = "unfav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unfavourites the company identified by the index number used in the displayed company list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNFAV_COMPANY_SUCCESS = "Unfavourited company: %1$s";

    public static final String MESSAGE_COMPANY_ALREADY_UNFAV = "%1$s is not currently favourited!";

    private final Index index;

    /**
     * @param index of the company in the filtered company list to Unfavourite
     */
    public UnfavCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Company> lastShownList = model.getFilteredCompanyList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_COMPANY_LIST_EMPTY);
        }
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_INDEX_EXCEEDS_SIZE, lastShownList.size()));
        }

        Company companyToUnfav = lastShownList.get(index.getZeroBased());

        if (!companyToUnfav.getIsFavourite()) {
            throw new CommandException(String.format(MESSAGE_COMPANY_ALREADY_UNFAV, companyToUnfav.getName()));
        }

        Company editedCompany = new Company(companyToUnfav.getName(),
                companyToUnfav.getPhone(), companyToUnfav.getEmail(),
                companyToUnfav.getAddress(), companyToUnfav.getTags(),
                companyToUnfav.getStatus(), companyToUnfav.getApplications(),
                false, false);

        model.setCompany(companyToUnfav, editedCompany);
        model.updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES);

        return new CommandResult(String.format(MESSAGE_UNFAV_COMPANY_SUCCESS, companyToUnfav.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UnfavCommand)) {
            return false;
        }
        UnfavCommand otherUnfavCommand = (UnfavCommand) other;
        return index.equals(otherUnfavCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .toString();
    }
}
