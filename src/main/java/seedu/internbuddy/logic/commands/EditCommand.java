package seedu.internbuddy.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.internbuddy.model.Model.PREDICATE_SHOW_ALL_COMPANIES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.commons.util.CollectionUtil;
import seedu.internbuddy.commons.util.ToStringBuilder;
import seedu.internbuddy.logic.Messages;
import seedu.internbuddy.logic.commands.exceptions.CommandException;
import seedu.internbuddy.model.Model;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.company.Address;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.model.company.Email;
import seedu.internbuddy.model.company.Phone;
import seedu.internbuddy.model.company.Status;
import seedu.internbuddy.model.company.StatusType;
import seedu.internbuddy.model.name.Name;
import seedu.internbuddy.model.tag.Tag;

/**
 * Edits the details of an existing company in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the company identified "
            + "by the index number used in the displayed company list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_COMPANY_SUCCESS = "Edited company: %1$s\n%2$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_COMPANY = "This company already exists in the address book.";

    private final Index index;
    private final EditCompanyDescriptor editCompanyDescriptor;

    /**
     * @param index of the company in the filtered company list to edit
     * @param editCompanyDescriptor details to edit the company with
     */
    public EditCommand(Index index, EditCompanyDescriptor editCompanyDescriptor) {
        requireNonNull(index);
        requireNonNull(editCompanyDescriptor);

        this.index = index;
        this.editCompanyDescriptor = new EditCompanyDescriptor(editCompanyDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Company> lastShownList = model.getFilteredCompanyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        Company companyToEdit = lastShownList.get(index.getZeroBased());
        Company editedCompany = createEditedCompany(companyToEdit, editCompanyDescriptor);
        String name = companyToEdit.getName().fullName;

        if (!companyToEdit.isSameCompany(editedCompany) && model.hasCompany(editedCompany)) {
            throw new CommandException(MESSAGE_DUPLICATE_COMPANY);
        }
        model.setCompany(companyToEdit, editedCompany);
        model.updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES);

        String edits = getEdits(companyToEdit, editedCompany);
        return new CommandResult(String.format(MESSAGE_EDIT_COMPANY_SUCCESS, name, edits));
    }

    /**
     * Creates and returns a {@code company} with the details of {@code companyToEdit}
     * edited with {@code editCompanyDescriptor}.
     */
    private static Company createEditedCompany(Company companyToEdit, EditCompanyDescriptor editCompanyDescriptor) {
        assert companyToEdit != null;

        Name updatedName = editCompanyDescriptor.getName().orElse(companyToEdit.getName());
        Phone updatedPhone = editCompanyDescriptor.getPhone().orElse(companyToEdit.getPhone());
        Email updatedEmail = editCompanyDescriptor.getEmail().orElse(companyToEdit.getEmail());
        Address updatedAddress = editCompanyDescriptor.getAddress().orElse(companyToEdit.getAddress());
        Set<Tag> updatedTags = editCompanyDescriptor.getTags().orElse(companyToEdit.getTags());
        Status updatedStatus = companyToEdit.getStatus();
        List<Application> updatedApplications = companyToEdit.getApplications();
        Boolean updatedIsFavourite = companyToEdit.getIsFavourite();

        return new Company(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                updatedStatus, updatedApplications, updatedIsFavourite);
    }

    /**
     * Creates a formatted {@code String} containing the changes made to the company.
     */
    public static String getEdits(Company companyToEdit, Company editedCompany) {
        assert companyToEdit != null;
        assert editedCompany != null;

        StringBuilder sb = new StringBuilder();

        if (!Objects.equals(companyToEdit.getName().fullName, editedCompany.getName().fullName)) {
            sb.append(getEditString("name",
                    companyToEdit.getName().fullName, editedCompany.getName().fullName));
        }
        if (!companyToEdit.getEmail().equals(editedCompany.getEmail())) {
            sb.append(getEditString("email",
                    companyToEdit.getEmail().value, editedCompany.getEmail().value));
        }
        if (!companyToEdit.getAddress().equals(editedCompany.getAddress())) {
            sb.append(getEditString("address",
                    companyToEdit.getAddress().getValue(), editedCompany.getAddress().getValue()));
        }
        if (!companyToEdit.getPhone().equals(editedCompany.getPhone())) {
            sb.append(getEditString("phone",
                    companyToEdit.getPhone().getValue(), editedCompany.getPhone().getValue()));
        }
        if (!companyToEdit.getTags().equals(editedCompany.getTags())) {
            sb.append(getEditString("tags",
                    companyToEdit.getTagsListString(), editedCompany.getTagsListString()));
        }

        return sb.toString();
    }

    private static String getEditString(String field, String oldValue, String newOldValue) {
        return String.format("%s: %s -> %s\n", field, oldValue, newOldValue);
    }

    public static Company setStatusApplied(Company companyToEdit) {
        Name updatedName = companyToEdit.getName();
        Phone updatedPhone = companyToEdit.getPhone();
        Email updatedEmail = companyToEdit.getEmail();
        Address updatedAddress = companyToEdit.getAddress();
        Set<Tag> updatedTags = companyToEdit.getTags();
        Status updatedStatus = new Status(StatusType.APPLIED);
        List<Application> applications = companyToEdit.getApplications();
        Boolean updatedIsFavourite = companyToEdit.getIsFavourite();

        return new Company(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                updatedStatus, applications, updatedIsFavourite);
    }

    public static Company setStatusClosed(Company companyToEdit) {
        Name updatedName = companyToEdit.getName();
        Phone updatedPhone = companyToEdit.getPhone();
        Email updatedEmail = companyToEdit.getEmail();
        Address updatedAddress = companyToEdit.getAddress();
        Set<Tag> updatedTags = companyToEdit.getTags();
        Status updatedStatus = new Status(StatusType.CLOSED);
        List<Application> updatedApplications = companyToEdit.getApplications();
        Boolean updatedIsFavourite = companyToEdit.getIsFavourite();

        return new Company(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                updatedStatus, updatedApplications, updatedIsFavourite);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editCompanyDescriptor.equals(otherEditCommand.editCompanyDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editCompanyDescriptor", editCompanyDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the company with. Each non-empty field value will replace the
     * corresponding field value of the company.
     */
    public static class EditCompanyDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Status status;
        private List<Application> applications;
        private Boolean isFavourite;

        public EditCompanyDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCompanyDescriptor(EditCompanyDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setStatus(toCopy.status);
            setApplications(toCopy.applications);
            setIsFavourite(toCopy.isFavourite);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setIsFavourite(Boolean isFavourite) {
            this.isFavourite = isFavourite;
        }

        public Optional<Boolean> getIsFavourite() {
            return Optional.ofNullable(isFavourite);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setApplications(List<Application> applications) {
            this.applications = applications;
        }

        public Optional<List<Application>> getApplications() {
            return Optional.ofNullable(applications);
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCompanyDescriptor)) {
                return false;
            }

            EditCompanyDescriptor otherEditCompanyDescriptor = (EditCompanyDescriptor) other;
            return Objects.equals(name, otherEditCompanyDescriptor.name)
                    && Objects.equals(phone, otherEditCompanyDescriptor.phone)
                    && Objects.equals(email, otherEditCompanyDescriptor.email)
                    && Objects.equals(address, otherEditCompanyDescriptor.address)
                    && Objects.equals(tags, otherEditCompanyDescriptor.tags)
                    && Objects.equals(status, otherEditCompanyDescriptor.status);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .add("status", status)
                    .toString();
        }
    }
}
