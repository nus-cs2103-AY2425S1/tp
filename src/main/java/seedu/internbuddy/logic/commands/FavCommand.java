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
 * Favourites a company identified using it's displayed index from the address book.
 */
public class FavCommand extends Command {
    public static final String COMMAND_WORD = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Favourites the company identified by the index number used in the displayed company list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FAV_COMPANY_SUCCESS = "Favourited company: %1$s";

    private final Index index;

    /**
     * @param index of the company in the filtered company list to favourite
     */
    public FavCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Company> lastShownList = model.getFilteredCompanyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        Company companyToFav = lastShownList.get(index.getZeroBased());
        Company editedCompany = new Company(companyToFav.getName(), companyToFav.getPhone(), companyToFav.getEmail(),
                companyToFav.getAddress(), companyToFav.getTags(),
                companyToFav.getStatus(), companyToFav.getApplications(),
                true);

        model.setCompany(companyToFav, editedCompany);
        model.updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES);

        return new CommandResult(String.format(MESSAGE_FAV_COMPANY_SUCCESS, Messages.format(companyToFav)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FavCommand)) {
            return false;
        }

        FavCommand otherFavCommand = (FavCommand) other;
        return index.equals(otherFavCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .toString();
    }
}
