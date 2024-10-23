package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NICKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM_HANDLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentStatus;
import seedu.address.model.person.TelegramHandle;
import seedu.address.model.tag.Nickname;
import seedu.address.model.tag.Role;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TELEGRAM_HANDLE + "telegramHandle] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_STUDENT_STATUS + "STUDENT_STATUS] "
            + "[" + PREFIX_ROLE + "ROLE]"
            + "[" + PREFIX_NICKNAME + "NICKNAME]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TELEGRAM_HANDLE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "One of the fields you want to add conflict with another person.";

    private static final int invalidTargetIndex = -1;

    private Index targetIndex;
    private final Name targetName;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param targetIndex of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index targetIndex, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(editPersonDescriptor);

        this.targetIndex = targetIndex;
        this.targetName = null;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    /**
     * @param targetName of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Name targetName, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(targetName);
        requireNonNull(editPersonDescriptor);

        this.targetIndex = null;
        this.targetName = targetName;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        System.out.println("attempting to execute EditCommand, targetIndex = " + targetIndex);

        if (targetIndex == null) {
            setTargetIndex(lastShownList);
            System.out.println("Have set Target Index: " + targetIndex);
        }
        assert(targetIndex != null);

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (!personToEdit.isSamePerson(editedPerson)
                && model.hasDuplicateFieldsWithException(personToEdit, editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_FIELDS);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    private void setTargetIndex(List<Person> lastShownList) throws CommandException {
        int temp = lastShownList.stream()
                .filter(person -> person.getName().equalsIgnoreCase(targetName))
                .map(lastShownList::indexOf)
                .reduce(invalidTargetIndex, (x, y) -> y);
        System.out.println("temp = " + temp);
        if (temp == invalidTargetIndex) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_IN_ADDRESS_BOOK);
        }
        this.targetIndex = Index.fromZeroBased(temp);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        TelegramHandle updatedTelegramHandle = editPersonDescriptor.getTelegramHandle()
                .orElse(personToEdit.getTelegramHandle());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        StudentStatus updatedStudentStatus =
                editPersonDescriptor.getStudentStatus().orElse(personToEdit.getStudentStatus());
        Set<Role> updatedRoles = editPersonDescriptor.getRoles().orElse(personToEdit.getRoles());
        Nickname updatedNickname = editPersonDescriptor.getNickname().orElse(personToEdit.getNickname());
        return new Person(updatedName, updatedTelegramHandle, updatedEmail, updatedStudentStatus, updatedRoles,
                updatedNickname);
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
        return targetIndex.equals(otherEditCommand.targetIndex)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private TelegramHandle telegramHandle;
        private Email email;
        private StudentStatus studentStatus;
        private Set<Role> roles;
        private Nickname nickname;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code roles} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setTelegramHandle(toCopy.telegramHandle);
            setEmail(toCopy.email);
            setStudentStatus(toCopy.studentStatus);
            setRoles(toCopy.roles);
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
        public void setRoles(Set<Role> roles) {
            this.roles = (roles != null) ? new HashSet<>(roles) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
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
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(telegramHandle, otherEditPersonDescriptor.telegramHandle)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(studentStatus, otherEditPersonDescriptor.studentStatus)
                    && Objects.equals(roles, otherEditPersonDescriptor.roles)
                    && Objects.equals(nickname, otherEditPersonDescriptor.nickname);
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
