package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.company.ApplicationStatus;
import seedu.address.model.company.Company;


/**
 * Changes the application status of an existing company in the address book
 */
public class ApplicationStatusCommand extends Command {
    public static final String COMMAND_WORD = "status";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the application status of the company identified "
            + "by the index number used in the last company listing. "
            + "Existing application status will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "as/ [STATUS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "status/ Ongoing";

    public static final String MESSAGE_ADD_STATUS_SUCCESS = "Changed application to Company: %1$s";

    private final Index index;
    private final ApplicationStatus status;

    /**
     * @param index of the company in the filtered company list to edit the remark
     * @param status of the company to be updated to
     */
    public ApplicationStatusCommand(Index index, ApplicationStatus status) {
        requireAllNonNull(index, status);

        this.index = index;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Company> lastShownList = model.getFilteredCompanyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        Company companyToEdit = lastShownList.get(index.getZeroBased());
        Company editedPerson = new Company(
                companyToEdit.getName(), companyToEdit.getPhone(), companyToEdit.getEmail(),
                companyToEdit.getAddress(), companyToEdit.getCareerPageUrl(),
                companyToEdit.getApplicationStatus(), companyToEdit.getTags(), companyToEdit.getIsBookmark());

        model.setCompany(companyToEdit, editedPerson);
        model.updateFilteredCompanyList(Model.PREDICATE_SHOW_ALL_COMPANIES);

        return new CommandResult(String.format(MESSAGE_ADD_STATUS_SUCCESS, Messages.format(companyToEdit)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof ApplicationStatusCommand)) {
            return false;
        }
        // state check
        ApplicationStatusCommand e = (ApplicationStatusCommand) other;
        return index.equals(e.index)
                && status.equals(e.status);
    }
}
