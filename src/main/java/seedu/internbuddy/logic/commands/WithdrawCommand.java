package seedu.internbuddy.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_APP_INDEX;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_COMPANY_INDEX;

import java.util.ArrayList;
import java.util.List;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.logic.Messages;
import seedu.internbuddy.logic.commands.exceptions.CommandException;
import seedu.internbuddy.model.Model;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.model.company.Status;
import seedu.internbuddy.model.company.StatusType;

/**
 * Withdraws an application identified using it's displayed index from the address book.
 */
public class WithdrawCommand extends Command {

    public static final String COMMAND_WORD = "withdraw";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Withdraws the application identified by the index number used in the displayed application list.\n"
            + "Parameters: " + PREFIX_COMPANY_INDEX + "COMPANY_INDEX(must be a positive integer) "
            + PREFIX_APP_INDEX + "APPLICATION_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " c/1 app/1";

    public static final String MESSAGE_WITHDRAW_APPLICATION_SUCCESS = "Withdrawn application for %2$s: %1$s";
    public static final String MESSAGE_NO_APPLICATION = "%s has no applications to update!";

    private final Index companyIndex;
    private final Index applicationIndex;

    /**
     * @param companyIndex of the company in the filtered company list to withdraw application from
     * @param applicationIndex of the application in the company to withdraw
     */
    public WithdrawCommand(Index companyIndex, Index applicationIndex) {
        requireNonNull(companyIndex);
        requireNonNull(applicationIndex);
        this.companyIndex = companyIndex;
        this.applicationIndex = applicationIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Company> lastShownList = model.getFilteredCompanyList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_COMPANY_LIST_EMPTY);
        }
        if (companyIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException("Company "
                    + String.format(Messages.MESSAGE_INDEX_EXCEEDS_SIZE, lastShownList.size()));
        }

        Company companyToEdit = lastShownList.get(companyIndex.getZeroBased());
        List<Application> applicationList = new ArrayList<>(companyToEdit.getApplications());

        if (applicationList.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_APPLICATION, companyToEdit.getName()));
        }
        if (applicationIndex.getZeroBased() >= applicationList.size()) {
            throw new CommandException("Application "
                    + String.format(Messages.MESSAGE_INDEX_EXCEEDS_SIZE, applicationList.size()));
        }

        Application applicationToWithdraw = applicationList.get(applicationIndex.getZeroBased());
        applicationList.remove(applicationToWithdraw);

        Status newStatus = getNewStatus(companyToEdit, applicationList);

        Company editedCompany = new Company(companyToEdit.getName(), companyToEdit.getPhone(), companyToEdit.getEmail(),
                companyToEdit.getAddress(), companyToEdit.getTags(),
                newStatus, applicationList, companyToEdit.getIsFavourite(), false);

        model.setCompany(companyToEdit, editedCompany);
        String successMessage = String.format(MESSAGE_WITHDRAW_APPLICATION_SUCCESS,
                Messages.format(applicationToWithdraw), companyToEdit.getName());
        if (applicationList.isEmpty()) {
            return new CommandResult(successMessage + "\nNo more applications for "
                    + companyToEdit.getName().fullName + ". Status has been set to CLOSED.");
        }
        return new CommandResult(successMessage);
    }

    public static Status getNewStatus(Company companyToEdit, List<Application> applicationList) {
        if (applicationList.isEmpty()) {
            return new Status(StatusType.CLOSED);
        } else {
            return companyToEdit.getStatus();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WithdrawCommand)) {
            return false;
        }

        WithdrawCommand otherWithdrawCommand = (WithdrawCommand) other;
        return companyIndex.equals(otherWithdrawCommand.companyIndex)
                && applicationIndex.equals(otherWithdrawCommand.applicationIndex);
    }
}
