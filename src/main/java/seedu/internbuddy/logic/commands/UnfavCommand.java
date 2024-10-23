package seedu.internbuddy.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.internbuddy.model.Model.PREDICATE_SHOW_ALL_COMPANIES;

import java.util.List;

import seedu.internbuddy.commons.core.index.Index;
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

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        Company companyToUnfav = lastShownList.get(index.getZeroBased());
        Company editedCompany = new Company(companyToUnfav.getName(),
                companyToUnfav.getPhone(), companyToUnfav.getEmail(),
                companyToUnfav.getAddress(), companyToUnfav.getTags(),
                companyToUnfav.getStatus(), companyToUnfav.getApplications(),
                false);

        model.setCompany(companyToUnfav, editedCompany);
        model.updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES);

        return new CommandResult(String.format(MESSAGE_UNFAV_COMPANY_SUCCESS, Messages.format(companyToUnfav)));
    }




}
