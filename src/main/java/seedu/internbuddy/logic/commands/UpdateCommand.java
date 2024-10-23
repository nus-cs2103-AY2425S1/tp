package seedu.internbuddy.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_APPSTATUS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.commons.util.ToStringBuilder;
import seedu.internbuddy.logic.Messages;
import seedu.internbuddy.logic.commands.exceptions.CommandException;
import seedu.internbuddy.model.Model;
import seedu.internbuddy.model.application.AppStatus;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.company.Company;

/**
 * Update the status of an application identified using it's displayed index from the address book.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the status of an application. "
            + "Parameters: COMPANY_INDEX (must be a positive integer) "
            + "APPLICATION_INDEX (must be a positive integer) "
            + PREFIX_APPSTATUS + "APP_STATUS\n"
            + "Example: " + COMMAND_WORD + " 1 1 "
            + PREFIX_APPSTATUS + "OA";

    public static final String MESSAGE_UPDATE_APPLICATION_SUCCESS = "Updated application: %1$s";

    private final Index companyIndex;
    private final Index applicationIndex;
    private final AppStatus appStatus;
    private Application applicationToEdit;

    /**
     * @param companyIndex of the company in the filtered company list to edit application from
     * @param applicationIndex of the application in the company to edit
     * @param appStatus of the new application status ('INTERVIEWED', 'OA', 'OFFERED', 'ACCEPTED' or 'REJECTED')
     */
    public UpdateCommand(Index companyIndex, Index applicationIndex, AppStatus appStatus) {
        requireNonNull(companyIndex);
        requireNonNull(applicationIndex);
        requireNonNull(appStatus);
        this.companyIndex = companyIndex;
        this.applicationIndex = applicationIndex;
        this.appStatus = appStatus;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Company> lastShownList = model.getFilteredCompanyList();

        if (companyIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        Company companyToEdit = lastShownList.get(companyIndex.getZeroBased());
        List<Application> applicationList = new ArrayList<>(companyToEdit.getApplications());

        if (applicationIndex.getZeroBased() >= applicationList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        applicationToEdit = applicationList.get(applicationIndex.getZeroBased());
        Application editedApplication = applicationToEdit.setAppStatus(appStatus);
        applicationList.set(applicationIndex.getZeroBased(), editedApplication);

        Company editedCompany = new Company(companyToEdit.getName(), companyToEdit.getPhone(), companyToEdit.getEmail(),
                companyToEdit.getAddress(), companyToEdit.getTags(),
                companyToEdit.getStatus(), applicationList, companyToEdit.getIsFavourite());
        model.setCompany(companyToEdit, editedCompany);

        return new CommandResult(String.format(MESSAGE_UPDATE_APPLICATION_SUCCESS, editedApplication));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateCommand otherUpdateCommand)) {
            return false;
        }

        return Objects.equals(companyIndex, otherUpdateCommand.companyIndex)
            && Objects.equals(applicationIndex, otherUpdateCommand.applicationIndex)
            && Objects.equals(appStatus, otherUpdateCommand.appStatus);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("applicationToEdit", applicationToEdit)
                .toString();
    }
}
