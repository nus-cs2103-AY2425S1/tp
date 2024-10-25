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

        // Check for duplicate tag
        Tag currentTag = personToTag.getRole(); // Use `getRole()` instead of `getTags()`
        Tag newTag = tagPersonDescriptor.getTag().orElse(null);
        if (newTag != null && newTag.equals(currentTag)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_TAG, personToTag.getName()));
        }

        // When creating an edited person
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
        Tag updatedTag = tagPersonDescriptor.getTag().orElse(personToTag.getRole());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTag, null);
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
        private Tag tag;

        public TagPersonDescriptor() {}

        public TagPersonDescriptor(TagPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTag(toCopy.tag);
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

        // Set and get methods for a single tag instead of a set of tags
        public void setTag(Tag tag) {
            this.tag = tag;
        }

        public Optional<Tag> getTag() {
            return Optional.ofNullable(tag);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof TagPersonDescriptor)) {
                return false;
            }
            TagPersonDescriptor otherDescriptor = (TagPersonDescriptor) other;
            return Objects.equals(name, otherDescriptor.name)
                    && Objects.equals(phone, otherDescriptor.phone)
                    && Objects.equals(email, otherDescriptor.email)
                    && Objects.equals(address, otherDescriptor.address)
                    && Objects.equals(tag, otherDescriptor.tag);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tag", tag)
                    .toString();
        }
    }
}
