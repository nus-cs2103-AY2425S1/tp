package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Tags existing person in the address book.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": adds a tag to person identified "
            + "by full name. "
            + "New tags will be added to existing tags.\n"
            + "Example: " + COMMAND_WORD + PREFIX_NAME
            + "Alex Yeoh " + PREFIX_TAG + "food vendor";

    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Tagged Person: %1$s";
    public static final String MESSAGE_NO_NEW_TAG = "At least one tag must be provided.";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists for %1$s.";


    private final Name name;
    private final TagPersonDescriptor tagPersonDescriptor;

    /**
     * @param name                of the person in the filtered person list to tag
     * @param tagPersonDescriptor details to tag the person with
     */
    public TagCommand(Name name, TagPersonDescriptor tagPersonDescriptor) {
        requireNonNull(name);
        requireNonNull(tagPersonDescriptor);

        this.name = name;
        this.tagPersonDescriptor = new TagPersonDescriptor(tagPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // finds person to tag
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToTag = null;
        for (Person person : lastShownList) {
            if (person.getName().equals(this.name)) {
                personToTag = person;
                break;
            }
        }
        if (personToTag == null) {
            throw new CommandException(Name.MESSAGE_CONSTRAINTS);
        }

        // check for duplicate tags
        Set<Tag> currentTags = personToTag.getTags();
        for (Tag newTag : tagPersonDescriptor.getTags().orElse(Set.of())) {
            if (currentTags.contains(newTag)) {
                throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(MESSAGE_DUPLICATE_TAG, personToTag.getName())));
            }
        }

        Person taggedPerson = createEditedPerson(personToTag, tagPersonDescriptor);
        model.setPerson(personToTag, taggedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_TAG_PERSON_SUCCESS, Messages.format(taggedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToTag}
     * edited with {@code tagPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToTag, TagPersonDescriptor tagPersonDescriptor) {
        assert personToTag != null;

        Name updatedName = tagPersonDescriptor.getName().orElse(personToTag.getName());
        Phone updatedPhone = tagPersonDescriptor.getPhone().orElse(personToTag.getPhone());
        Email updatedEmail = tagPersonDescriptor.getEmail().orElse(personToTag.getEmail());
        Address updatedAddress = tagPersonDescriptor.getAddress().orElse(personToTag.getAddress());
        Set<Tag> updatedTags = new HashSet<>(personToTag.getTags());
        tagPersonDescriptor.getTags().ifPresent(updatedTags::addAll); // Add tags if present

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true; // Same object reference
        }

        if (!(other instanceof TagCommand)) {
            return false; // Not the same class
        }

        TagCommand otherTagCommand = (TagCommand) other;

        // Check equality of the fields
        return Objects.equals(name, otherTagCommand.name)
                && Objects.equals(tagPersonDescriptor, otherTagCommand.tagPersonDescriptor);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).add("name", name)
                .add("tagPersonDescriptor", tagPersonDescriptor).toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class TagPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public TagPersonDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public TagPersonDescriptor(TagPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
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

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true; // same object
            }
            if (!(other instanceof TagPersonDescriptor)) {
                return false; // different type
            }
            TagPersonDescriptor otherDescriptor = (TagPersonDescriptor) other;

            // Ensure all fields (including tags) are compared correctly
            return Objects.equals(name, otherDescriptor.name)
                    && Objects.equals(tags, otherDescriptor.tags); // add other fields as necessary
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .toString();
        }
    }
}
