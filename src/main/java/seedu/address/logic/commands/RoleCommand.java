package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
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
import seedu.address.model.role.Role;

/**
 * Tags existing person in the address book.
 */
public class RoleCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": assigns a role to person identified "
            + "by full name. "
            + "Example: " + COMMAND_WORD + PREFIX_NAME
            + "Alex Yeoh " + PREFIX_ROLE + "food vendor";

    public static final String MESSAGE_ADD_PERSON_ROLE_SUCCESS = "Assigned role to: %1$s";
    public static final String MESSAGE_DUPLICATE_ROLE = "This role has already been assigned to %1$s.";
    // public static final String MESSAGE_UNSPECIFIED_NAME = "Please specific the full name of person to role.";

    private final Name name;
    private final PersonWithRoleDescriptor personWithRoleDescriptor;

    /**
     * @param name                of the person in the filtered person list to add role to
     * @param personWithRoleDescriptor details to add role to person with
     */
    public RoleCommand(Name name, PersonWithRoleDescriptor personWithRoleDescriptor) {
        requireNonNull(name);
        requireNonNull(personWithRoleDescriptor);

        this.name = name;
        this.personWithRoleDescriptor = new PersonWithRoleDescriptor(personWithRoleDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // checks if multiple persons contain same keyword in name
        // displays those persons
        /*
        String[] nameKeywords = new String[] {this.name.fullName}

        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords));
        model.updateFilteredPersonList(predicate);
        if (model.getFilteredPersonList().size() > 1) {
            return new CommandResult(MESSAGE_UNSPECIFIED_NAME);
        }
        */

        // finds person to add role to
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToAddRole = null;
        for (Person person : lastShownList) {
            if (person.getName().equals(this.name)) {
                personToAddRole = person;
                break;
            }
        }
        if (personToAddRole == null) {
            throw new CommandException(Name.MESSAGE_CONSTRAINTS);
        }

        Person personWithRole = createPersonWithRole(personToAddRole, personWithRoleDescriptor);
        model.setPerson(personToAddRole, personWithRole);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_PERSON_ROLE_SUCCESS, Messages.format(personWithRole)));
    }

    /**
     * Creates and returns a {@code Person} with the role added to {@code personToAddRole}
     *  with {@code personWithRoleDescriptor}.
     */
    private static Person createPersonWithRole(Person personToAddRole, PersonWithRoleDescriptor personWithRoleDescriptor) {
        assert personToAddRole != null;

        Name updatedName = personWithRoleDescriptor.getName().orElse(personToAddRole.getName());
        Phone updatedPhone = personWithRoleDescriptor.getPhone().orElse(personToAddRole.getPhone());
        Email updatedEmail = personWithRoleDescriptor.getEmail().orElse(personToAddRole.getEmail());
        Address updatedAddress = personWithRoleDescriptor.getAddress().orElse(personToAddRole.getAddress());
        Role updatedRole = personToAddRole.getRole();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedRole);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true; // Same object reference
        }

        if (!(other instanceof RoleCommand)) {
            return false; // Not the same class
        }

        RoleCommand otherRoleCommand = (RoleCommand) other;

        // Check equality of the fields
        return Objects.equals(name, otherRoleCommand.name)
                && Objects.equals(personWithRoleDescriptor, otherRoleCommand.personWithRoleDescriptor);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).add("name", name)
                .add("personWithRoleDescriptor", personWithRoleDescriptor).toString();
    }

    /**
     * Stores the details to add role to person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class PersonWithRoleDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Role role;

        public PersonWithRoleDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code role} is used internally.
         */
        public PersonWithRoleDescriptor(PersonWithRoleDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setRole(toCopy.role);
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
         * Sets {@code role} to this object's {@code role}.
         * A defensive copy of {@code role} is used internally.
         */
        public void setRole(Role role) {
            this.role = role;
        }

        /**
         * Returns an unmodifiable role set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code role} is null.
         */
        public Optional<Role> getRole() {
            return (role != null) ? Optional.of(role) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true; // same object
            }
            if (!(other instanceof PersonWithRoleDescriptor)) {
                return false; // different type
            }
            PersonWithRoleDescriptor otherDescriptor = (PersonWithRoleDescriptor) other;

            // Ensure all fields (including role) are compared correctly
            return Objects.equals(name, otherDescriptor.name)
                    && Objects.equals(role, otherDescriptor.role); // add other fields as necessary
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("role", role)
                    .toString();
        }
    }
}
