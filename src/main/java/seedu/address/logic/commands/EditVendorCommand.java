package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.EditVendorDescriptor;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Budget;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Vendor;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing vendor in the address book.
 */
public class EditVendorCommand extends Command {
    public static final String COMMAND_WORD = "edit_vendor";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the vendor identified "
            + "by the index number used in the displayed guest list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_COMPANY + "COMPANY] "
            + "[" + PREFIX_BUDGET + "BUDGET] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";
    public static final String MESSAGE_EDIT_VENDOR_SUCCESS = "Edited Vendor: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_VENDOR = "This vendor already exists in the address book.";
    private final Index index;
    private final EditVendorDescriptor editVendorDescriptor;

    /**
     * @param index of the vendor in the filtered vendor list to edit
     * @param editVendorDescriptor details to edit the vendor with
     */
    public EditVendorCommand(Index index, EditVendorDescriptor editVendorDescriptor) {
        requireNonNull(index);
        requireNonNull(editVendorDescriptor);
        this.index = index;
        this.editVendorDescriptor = new EditVendorDescriptor(editVendorDescriptor);
    }

    /**
     * Creates and returns a {@code Vendor} with the details of {@code vendorToEdit}
     * edited with {@code editVendorDescriptor}.
     */
    private static Vendor createEditedVendor(Vendor vendorToEdit, EditVendorDescriptor editVendorDescriptor) {
        assert vendorToEdit != null;
        Name updatedName = editVendorDescriptor.getName().orElse(vendorToEdit.getName());
        Phone updatedPhone = editVendorDescriptor.getPhone().orElse(vendorToEdit.getPhone());
        Email updatedEmail = editVendorDescriptor.getEmail().orElse(vendorToEdit.getEmail());
        Address updatedAddress = editVendorDescriptor.getAddress().orElse(vendorToEdit.getAddress());
        Set<Tag> updatedTags = editVendorDescriptor.getTags().orElse(vendorToEdit.getTags());
        Company updatedCompany = editVendorDescriptor.getCompany().orElse(vendorToEdit.getCompany());
        Budget updatedBudget = editVendorDescriptor.getBudget().orElse(vendorToEdit.getBudget());
        return new Vendor(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                updatedCompany, updatedBudget);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredVendorList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Vendor vendorToEdit = (Vendor) lastShownList.get(index.getZeroBased());
        Vendor editedVendor = createEditedVendor(vendorToEdit, editVendorDescriptor);
        if (!vendorToEdit.isSamePerson(editedVendor) && model.hasPerson(editedVendor)) {
            throw new CommandException(MESSAGE_DUPLICATE_VENDOR);
        }
        model.setPerson(vendorToEdit, editedVendor);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_VENDOR_SUCCESS, Messages.format(editedVendor)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof EditVendorCommand)) {
            return false;
        }
        EditVendorCommand otherEditCommand = (EditVendorCommand) other;
        return index.equals(otherEditCommand.index)
                && editVendorDescriptor.equals(otherEditCommand.editVendorDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editVendorDescriptor", editVendorDescriptor)
                .toString();
    }
}
