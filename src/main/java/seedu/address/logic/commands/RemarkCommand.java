package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.company.Address;
import seedu.address.model.company.ApplicationStatus;
import seedu.address.model.company.Bookmark;
import seedu.address.model.company.CareerPageUrl;
import seedu.address.model.company.Company;
import seedu.address.model.company.Email;
import seedu.address.model.company.Name;
import seedu.address.model.company.Phone;
import seedu.address.model.company.Remark;
import seedu.address.model.tag.Tag;

/**
 * Changes the remark of an existing company in the address book.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the remark of the company identified "
            + "by the index number used in the displayed company list. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "r/REMARK\n"
            + "Example: " + COMMAND_WORD + " 1 r/Best company ever";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Company: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Company: %1$s";

    private final Index index;
    private final Remark remark;

    /**
     * @param index of the company in the filtered company list to edit the remark
     * @param remark of the company to be updated to
     */
    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Company> lastShownList = model.getFilteredCompanyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        Company companyToEdit = lastShownList.get(index.getZeroBased());
        Company editedCompany = createEditedCompany(companyToEdit, remark);

        model.setCompany(companyToEdit, editedCompany);

        return new CommandResult(generateSuccessMessage(editedCompany));
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code companyToEdit}.
     */
    private String generateSuccessMessage(Company companyToEdit) {
        String message = !remark.value.isEmpty() ? MESSAGE_ADD_REMARK_SUCCESS : MESSAGE_DELETE_REMARK_SUCCESS;
        return String.format(message, companyToEdit);
    }

    /**
     * Creates and returns a {@code Company} with the details of {@code companyToEdit}
     * edited with {@code remark}.
     */
    private static Company createEditedCompany(Company companyToEdit, Remark remark) {
        assert companyToEdit != null;

        Name updatedName = companyToEdit.getName();
        Phone updatedPhone = companyToEdit.getPhone();
        Email updatedEmail = companyToEdit.getEmail();
        Address updatedAddress = companyToEdit.getAddress();
        CareerPageUrl updatedCareerPageUrl = companyToEdit.getCareerPageUrl();
        ApplicationStatus updatedApplicationStatus = companyToEdit.getApplicationStatus();
        Bookmark updatedBookmark = companyToEdit.getIsBookmark();
        Set<Tag> updatedTags = companyToEdit.getTags();

        return new Company(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedCareerPageUrl, updatedApplicationStatus, updatedTags, updatedBookmark, remark);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }
}
