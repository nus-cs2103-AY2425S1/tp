package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.COMMAND_FORMAT_PREAMBLE;
import static seedu.address.logic.Messages.MESSAGE_HELP_PROMPT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_INDEX_OR_NAME;
import static seedu.address.logic.Messages.WHITESPACE;
import static seedu.address.logic.Messages.styleCommand;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NICKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM_HANDLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Nickname;
import seedu.address.model.contact.Role;
import seedu.address.model.contact.StudentStatus;
import seedu.address.model.contact.TelegramHandle;

/**
 * Edits the details of an existing contact in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_FUNCTION = COMMAND_WORD + ": Edits the details of the contact "
            + "identified by the full name or the numerical displayed index. "
            + "Existing values will be overwritten by the input values.";
    public static final String MESSAGE_COMMAND_FORMAT = styleCommand(COMMAND_WORD + WHITESPACE
            + "[INDEX OR FULL_NAME_OF_CONTACT_TO_EDIT]" + WHITESPACE + "[PREFIX] [new description]");
    public static final String MESSAGE_COMMAND_EXAMPLE = "Example One: " + COMMAND_WORD + " 1 "
            + PREFIX_TELEGRAM_HANDLE + "johnny_9876_haha "
            + PREFIX_EMAIL + "johnd@example.com\n"
            + "Example Two: " + COMMAND_WORD + " 1 " + PREFIX_NAME + "John Doe\n"
            + "Example Three: " + COMMAND_WORD + " 2 " + PREFIX_NAME + " John Doe" + PREFIX_NICKNAME + " "
            + "johnny";
    public static final String MESSAGE_COMMAND_FORMAT_AND_HELP_PROMPT =
            COMMAND_FORMAT_PREAMBLE + WHITESPACE
            + EditCommand.MESSAGE_COMMAND_FORMAT + "\n"
            + String.format(MESSAGE_HELP_PROMPT,
            HelpCommand.COMMAND_WORD + " " + EditCommand.COMMAND_WORD);

    public static final String MESSAGE_EDIT_CONTACT_SUCCESS = "Contact edited successfully! Edited "
            + "Contact: %1$s\nThe display index of the contact may be reassigned if the name or nickname "
            + "is edited. ";
    public static final String MESSAGE_MISSING_PREFIX =
            String.format(Messages.MESSAGE_MISSING_PREFIX, "editing");

    public static final String MESSAGE_DUPLICATE_CONTACT =
            String.format(Messages.MESSAGE_DUPLICATE_CONTACT, "This will result in a contact",
                    "that edits to make");
    public static final String MESSAGE_DUPLICATE_FIELDS_CONTACT =
            String.format(Messages.MESSAGE_DUPLICATE_FIELDS_CONTACT,
                    "If you are sure these are the changes to make, please edit the conflicting "
                            + "fields of the other contact(s) to something else first prior to prevent conflicts");

    public static final String MESSAGE_MISSING_INDEX_OR_FULL_NAME =
            String.format(MESSAGE_MISSING_INDEX_OR_NAME, MESSAGE_COMMAND_FORMAT_AND_HELP_PROMPT);


    private static final int invalidTargetIndex = -1;

    private Index targetIndex;
    private final Name targetName;
    private final EditContactDescriptor editContactDescriptor;

    /**
     * @param targetIndex of the contact in the filtered contact list to edit
     * @param editContactDescriptor details to edit the contact with
     */
    public EditCommand(Index targetIndex, EditContactDescriptor editContactDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(editContactDescriptor);

        this.targetIndex = targetIndex;
        this.targetName = null;
        this.editContactDescriptor = new EditContactDescriptor(editContactDescriptor);
    }

    /**
     * @param targetName of the contact in the filtered contact list to edit
     * @param editContactDescriptor details to edit the contact with
     */
    public EditCommand(Name targetName, EditContactDescriptor editContactDescriptor) {
        requireNonNull(targetName);
        requireNonNull(editContactDescriptor);

        this.targetIndex = null;
        this.targetName = targetName;
        this.editContactDescriptor = new EditContactDescriptor(editContactDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownFilteredList = model.getFilteredContactList();
        List<Contact> allContactList = model.getAllContactList();
        // System.out.println("attempting to execute EditCommand, targetIndex = " + targetIndex);

        if (targetIndex == null) {
            screenDuplicate(allContactList);
            setTargetIndex(lastShownFilteredList);
            // System.out.println("Have set Target Index: " + targetIndex);
        }
        assert(targetIndex != null);

        if (targetIndex.getZeroBased() >= lastShownFilteredList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToEdit = lastShownFilteredList.get(targetIndex.getZeroBased());
        Contact editedContact = createEditedContact(contactToEdit, editContactDescriptor);

        if (!contactToEdit.isSameContact(editedContact) && model.hasContact(editedContact)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }

        if (contactToEdit.isSameContact(editedContact)
                && model.hasDuplicateFieldsWithException(contactToEdit, editedContact)) {
            throw new CommandException(MESSAGE_DUPLICATE_FIELDS_CONTACT);
        }

        model.setContact(contactToEdit, editedContact);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        return new CommandResult(String.format(MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedContact)));
    }

    private void setTargetIndex(List<Contact> lastShownList) throws CommandException {
        int temp = lastShownList.stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(targetName))
                .map(lastShownList::indexOf)
                .reduce(invalidTargetIndex, (x, y) -> y);
        // System.out.println("temp = " + temp);
        if (temp == invalidTargetIndex) {
            throw new CommandException(String.format(Messages.MESSAGE_CONTACT_NOT_IN_ADDRESS_BOOK,
                    targetName));
        }
        this.targetIndex = Index.fromZeroBased(temp);
    }

    /**
     * Creates and returns a {@code Contact} with the details of {@code contactToEdit}
     * edited with {@code editContactDescriptor}.
     */
    private static Contact createEditedContact(Contact contactToEdit, EditContactDescriptor editContactDescriptor) {
        assert contactToEdit != null;

        Name updatedName = editContactDescriptor.getName().orElse(contactToEdit.getName());
        TelegramHandle updatedTelegramHandle = editContactDescriptor.getTelegramHandle()
                .orElse(contactToEdit.getTelegramHandle());
        Email updatedEmail = editContactDescriptor.getEmail().orElse(contactToEdit.getEmail());
        StudentStatus updatedStudentStatus =
                editContactDescriptor.getStudentStatus().orElse(contactToEdit.getStudentStatus());
        Set<Role> updatedRoles = editContactDescriptor.getRoles().orElse(contactToEdit.getRoles());
        Nickname updatedNickname = editContactDescriptor.getNickname().orElse(contactToEdit.getNickname());
        return new Contact(updatedName, updatedTelegramHandle, updatedEmail, updatedStudentStatus, updatedRoles,
                updatedNickname);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand otherEditCommand)) {
            return false;
        }

        if (targetIndex == null) {
            if (otherEditCommand.targetIndex == null) {
                if (targetName == null) {
                    // redundancy protection, should never reach here
                    // either targetName or targetIndex are null and not both
                    return otherEditCommand.targetName == null;
                }
                return targetName.equals(otherEditCommand.targetName);
            }
            return false;
        }

        return targetIndex.equals(otherEditCommand.targetIndex)
                && editContactDescriptor.equals(otherEditCommand.editContactDescriptor);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("editContactDescriptor", editContactDescriptor)
                .toString();
    }


    // DRY not adhered (compare to delete)
    private void screenDuplicate(List<Contact> lastShownList) throws CommandException {
        long nameCount = lastShownList.stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(targetName))
                .count();
        boolean hasDuplicate = nameCount > 1;
        if (hasDuplicate) {
            throw new CommandException(
                    String.format(
                            Messages.MESSAGE_DUPLICATE_NAME, targetName.fullName,
                            COMMAND_WORD));
        }
    }

    /**
     * Stores the details to edit the contact with. Each non-empty field value will replace the
     * corresponding field value of the contact.
     */
    public static class EditContactDescriptor {
        private Name name;
        private TelegramHandle telegramHandle;
        private Email email;
        private StudentStatus studentStatus;
        private Set<Role> roles;
        private Nickname nickname;

        public EditContactDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code roles} is used internally.
         */
        public EditContactDescriptor(EditContactDescriptor toCopy) {
            setName(toCopy.name);
            setTelegramHandle(toCopy.telegramHandle);
            setEmail(toCopy.email);
            setStudentStatus(toCopy.studentStatus);
            setAndSortRoles(toCopy.roles);
            setNickname(toCopy.nickname);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, telegramHandle, email, studentStatus, roles, nickname);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setTelegramHandle(TelegramHandle telegramHandle) {
            this.telegramHandle = telegramHandle;
        }

        public Optional<TelegramHandle> getTelegramHandle() {
            return Optional.ofNullable(telegramHandle);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setStudentStatus(StudentStatus studentStatus) {
            this.studentStatus = studentStatus;
        }

        public Optional<StudentStatus> getStudentStatus() {
            return Optional.ofNullable(studentStatus);
        }

        /**
         * Sets {@code roles} to this object's {@code roles}.
         * A defensive copy of {@code roles} is used internally.
         */
        public void setAndSortRoles(Set<Role> roles) {
            this.roles = (roles != null) ? new TreeSet<>(roles) : null;
        }

        /**
         * Returns an unmodifiable roles set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code roles} is null.
         */
        public Optional<Set<Role>> getRoles() {
            return (roles != null) ? Optional.of(Collections.unmodifiableSet(roles)) : Optional.empty();
        }

        public void setNickname(Nickname nickname) {
            this.nickname = nickname;
        }

        public Optional<Nickname> getNickname() {
            return Optional.ofNullable(nickname);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditContactDescriptor)) {
                return false;
            }

            EditContactDescriptor otherEditContactDescriptor = (EditContactDescriptor) other;
            return Objects.equals(name, otherEditContactDescriptor.name)
                    && Objects.equals(telegramHandle, otherEditContactDescriptor.telegramHandle)
                    && Objects.equals(email, otherEditContactDescriptor.email)
                    && Objects.equals(studentStatus, otherEditContactDescriptor.studentStatus)
                    && Objects.equals(roles, otherEditContactDescriptor.roles)
                    && Objects.equals(nickname, otherEditContactDescriptor.nickname);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("telegramHandle", telegramHandle)
                    .add("email", email)
                    .add("studentStatus", studentStatus)
                    .add("roles", roles)
                    .add("nickname", nickname)
                    .toString();
        }
    }
}
