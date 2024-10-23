package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Add new tag(s) for an existing person
 */
public class AddTagCommand extends Command {
    public static final String COMMAND_WORD = "addTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Add tag(s) for the person identified "
            + "by the index number used in the displayed person list "
            + "Parameters: INDEX (must be a positive interger) "
            + "[" + PREFIX_TAG + "TAG]...\n";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Tag added: %1$s";
    public static final String MESSAGE_NOT_ADD = "At least one tag to be provided.";
    private final Index index;
    private final AddTagCommand.AddTagDescriptor addTagDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param addTagDescriptor details to edit the person with
     */
    public AddTagCommand(Index index, AddTagCommand.AddTagDescriptor addTagDescriptor) {
        requireNonNull(index);
        requireNonNull(addTagDescriptor);

        this.index = index;
        this.addTagDescriptor = new AddTagCommand.AddTagDescriptor(addTagDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, addTagDescriptor);


        model.deletePerson(personToEdit);

        model.insertPerson(editedPerson, index.getZeroBased());
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, AddTagCommand.AddTagDescriptor addTagDescriptor) {
        assert personToEdit != null;

        Name updatedName = addTagDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = addTagDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = addTagDescriptor.getEmail().orElse(personToEdit.getEmail());
        Set<Tag> updatedTags = addTagDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        AddTagCommand otherAddTagCommand = (AddTagCommand) other;
        return index.equals(otherAddTagCommand.index)
                && addTagDescriptor.equals(otherAddTagCommand.addTagDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", addTagDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class AddTagDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Set<Tag> tags;

        public AddTagDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public AddTagDescriptor(AddTagCommand.AddTagDescriptor toAdd) {
            setName(toAdd.name);
            setPhone(toAdd.phone);
            setEmail(toAdd.email);
            addTags(toAdd.tags);
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

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void addTags(Set<Tag> tags) {
            if (tags != null) {
                if (this.tags == null) {
                    this.tags = tags;
                    return;
                }
                for (Tag tag : tags) {
                    this.tags.add(tag);
                }
            }

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
            if (!(other instanceof AddTagCommand.AddTagDescriptor)) {
                return false;
            }

            AddTagCommand.AddTagDescriptor otherAddTagDescriptor = (AddTagCommand.AddTagDescriptor) other;
            return Objects.equals(tags, otherAddTagDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("tags", tags)
                    .toString();
        }
    }
}
