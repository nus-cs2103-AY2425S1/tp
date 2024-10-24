package tuteez.logic.commands;

import static java.util.Objects.requireNonNull;
import static tuteez.logic.commands.AddCommand.MESSAGE_DUPLICATE_LESSON;
import static tuteez.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tuteez.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON;
import static tuteez.logic.parser.CliSyntax.PREFIX_NAME;
import static tuteez.logic.parser.CliSyntax.PREFIX_PHONE;
import static tuteez.logic.parser.CliSyntax.PREFIX_TAG;
import static tuteez.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static tuteez.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import tuteez.commons.core.LogsCenter;
import tuteez.commons.core.index.Index;
import tuteez.commons.util.CollectionUtil;
import tuteez.commons.util.ToStringBuilder;
import tuteez.logic.Messages;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.Model;
import tuteez.model.person.Address;
import tuteez.model.person.Email;
import tuteez.model.person.Name;
import tuteez.model.person.Person;
import tuteez.model.person.Phone;
import tuteez.model.person.TelegramUsername;
import tuteez.model.person.lesson.Lesson;
import tuteez.model.tag.Tag;

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
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TELEGRAM + "TELEGRAM USERNAME] "
            + "[" + PREFIX_TAG + "TAG]..."
            + "[" + PREFIX_LESSON + "LESSON]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johnd@gmail.com\n"
            + "Note: You can leave the optional parameters specified in 'add' blank "
            + "in order to delete the existing values.";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;
    private final Logger logger = LogsCenter.getLogger(EditCommand.class);

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        Optional<Set<Lesson>> lessons = editPersonDescriptor.getLessons();
        if (lessons.isPresent()) {
            for (Lesson newLesson : lessons.get()) {
                if (personToEdit.isLessonScheduled(newLesson)) {
                    continue;
                }

                if (model.isClashingWithExistingLesson(newLesson)) {
                    String logMessage = String.format("Student: %s | Lessons: %s "
                            + "| Conflict: Clashes with another student's lesson",
                            editedPerson.getName(), editedPerson.getLessons().toString());
                    logger.info(logMessage);
                    throw new CommandException(MESSAGE_DUPLICATE_LESSON);
                }
            }
        }

        model.setPerson(personToEdit, editedPerson);
        if (personToEdit.equals(model.getLastViewedPerson().get().get())) {
            model.updateLastViewedPerson(editedPerson);
            String logMessageForPerson =
                    String.format("Student on display is edited, After Edit - Student: %s", editedPerson);
            logger.info(logMessageForPerson);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        String logMessage = String.format("Before Edit - Student: %s%nAfter Edit - Student: %s",
                personToEdit, editedPerson);
        logger.info(logMessage);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        TelegramUsername updatedTelegramUser = editPersonDescriptor.getTelegramUsername()
                .orElse(personToEdit.getTelegramUsername());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Set<Lesson> updatedLessons = editPersonDescriptor.getLessons().orElse(personToEdit.getLessons());


        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTelegramUser, updatedTags,
                updatedLessons);
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
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private TelegramUsername telegramUsername;
        private Set<Lesson> lessons;


        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setTelegramUsername(toCopy.telegramUsername);
            setLessons(toCopy.lessons);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address,
                    telegramUsername, tags, lessons);
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

        public void setTelegramUsername(TelegramUsername username) {
            this.telegramUsername = username;
        }

        public Optional<TelegramUsername> getTelegramUsername() {
            return Optional.ofNullable(telegramUsername);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code lessons} to this object's {@code lessons}.
         * A defensive copy of {@code lessons} is used internally.
         */
        public void setLessons(Set<Lesson> lessons) {
            this.lessons = (lessons != null) ? new HashSet<>(lessons) : null;
        }

        /**
         * Returns an unmodifiable lesson set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code lessons} is null.
         */
        public Optional<Set<Lesson>> getLessons() {
            return (lessons != null) ? Optional.of(Collections.unmodifiableSet(lessons)) : Optional.empty();
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
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags)
                    && Objects.equals(telegramUsername, otherEditPersonDescriptor.telegramUsername)
                    && Objects.equals(lessons, otherEditPersonDescriptor.lessons);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("telegramUsername", telegramUsername)
                    .add("tags", tags)
                    .add("lessons", lessons)
                    .toString();
        }
    }
}
