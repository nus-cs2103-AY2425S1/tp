package seedu.internbuddy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.commons.util.ToStringBuilder;
import seedu.internbuddy.logic.Messages;
import seedu.internbuddy.logic.commands.exceptions.CommandException;
import seedu.internbuddy.model.Model;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.model.company.Status;
import seedu.internbuddy.model.company.StatusType;

/**
 * Reopens a company identified using it's displayed index from the address book.
 */
public class ReopenCommand extends Command {

    public static final String COMMAND_WORD = "reopen";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the status of company identified by the index number "
            + "used in the displayed company list from CLOSED to INTERESTED.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REOPEN_COMPANY_SUCCESS = "Reopened company: %1$s";
    public static final String MESSAGE_COMPANY_NOT_CLOSED =
        "You can only reopen a company that is closed.";

    private final Index targetIndex;

    /**
     * @param targetIndex of the company in the filtered company list to reopen
     */
    public ReopenCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Reopens a company identified using it's displayed index from the address book.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Company> lastShownList = model.getFilteredCompanyList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_COMPANY_LIST_EMPTY);
        }
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INDEX_EXCEEDS_SIZE,
                model.getFilteredCompanyList().size()));
        }

        Company companyToReopen = lastShownList.get(targetIndex.getZeroBased());
        if (companyToReopen.getStatus().value != StatusType.CLOSED) {
            throw new CommandException(MESSAGE_COMPANY_NOT_CLOSED);
        }
        Company reopenedCompany = new Company(companyToReopen.getName(), companyToReopen.getPhone(),
                companyToReopen.getEmail(), companyToReopen.getAddress(), companyToReopen.getTags(),
                new Status(StatusType.INTERESTED), companyToReopen.getApplications(),
                    companyToReopen.getIsFavourite(), false);
        model.setCompany(companyToReopen, reopenedCompany);
        return new CommandResult(String.format(MESSAGE_REOPEN_COMPANY_SUCCESS, reopenedCompany.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReopenCommand // instanceof handles nulls
                && targetIndex.equals(((ReopenCommand) other).targetIndex)); // state check
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

}

