package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_EMPTY_LIST_ERROR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameMatchesKeywordPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.role.Role;
import seedu.address.model.wedding.Wedding;

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

    public static final String MESSAGE_DUPLICATE_HANDLING =
            "Please specify the index of the contact you want to assign.\n"
                    + "Find the index from the list below and type edit INDEX ...\n"
                    + "Example: " + COMMAND_WORD + " 1 ...";
    private final Index index;
    private final NameMatchesKeywordPredicate predicate;
    private final PersonWithRoleDescriptor personWithRoleDescriptor;

    /**
     * @param index of the person in the filtered person list to assign role
     * @param personWithRoleDescriptor details of the person to assign role
     */
    public RoleCommand(Index index, NameMatchesKeywordPredicate predicate,
                       PersonWithRoleDescriptor personWithRoleDescriptor) {
        requireNonNull(personWithRoleDescriptor);

        this.index = index;
        this.predicate = predicate;
        this.personWithRoleDescriptor = new PersonWithRoleDescriptor(personWithRoleDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        requireNonNull(model);
        Person personToAssign;

        if (this.index != null) {
            personToAssign = assignWithIndex(model);
        } else {
            personToAssign = assignWithKeyword(model);
        }

        Person assignedPerson = createPersonWithRole(personToAssign, personWithRoleDescriptor);

        if (personToAssign.getRole().equals(assignedPerson.getRole())) {
            throw new CommandException(MESSAGE_DUPLICATE_ROLE);
        }

        model.setPerson(personToAssign, assignedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_PERSON_ROLE_SUCCESS, Messages.format(assignedPerson)));
    }

    /**
     * Performs edit command logic when the input is an index.
     *
     * @param model {@code Model} which the command should operate on
     * @return the person to be edited
     * @throws CommandException if the list is empty or if the index is invalid
     */
    public Person assignWithIndex(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_EDIT_EMPTY_LIST_ERROR);
        }

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                    lastShownList.size()));
        }

        return lastShownList.get(index.getZeroBased());
    }

    /**
     * Performs edit command logic when the input is a {@code String}.
     *
     * @param model {@code Model} which the command should operate on
     * @return the person to be edited
     * @throws CommandException if the filtered list using {@code predicate} is empty or contains more than 1 element
     */
    public Person assignWithKeyword(Model model) throws CommandException {
        model.updateFilteredPersonList(predicate);
        List<Person> filteredList = model.getFilteredPersonList();

        if (filteredList.isEmpty()) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(MESSAGE_EDIT_EMPTY_LIST_ERROR);
        } else if (filteredList.size() == 1) {
            return filteredList.get(0);
        } else {
            throw new CommandException(MESSAGE_DUPLICATE_HANDLING);
        }
    }

    /**
     * Creates and returns a {@code Person} with the role added to {@code personToAddRole}
     *  with {@code personWithRoleDescriptor}.
     */
    private static Person createPersonWithRole(Person personToAddRole,
                                               PersonWithRoleDescriptor personWithRoleDescriptor) {
        assert personToAddRole != null;

        Name updatedName = personWithRoleDescriptor.getName().orElse(personToAddRole.getName());
        Phone updatedPhone = personWithRoleDescriptor.getPhone().orElse(personToAddRole.getPhone());
        Email updatedEmail = personWithRoleDescriptor.getEmail().orElse(personToAddRole.getEmail());
        Address updatedAddress = personWithRoleDescriptor.getAddress().orElse(personToAddRole.getAddress());
        Optional<Role> updatedRole = personWithRoleDescriptor.getRole();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedRole, null);
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
        return index.equals(otherRoleCommand.index)
                && Objects.equals(personWithRoleDescriptor, otherRoleCommand.personWithRoleDescriptor);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).add("index", index)
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
        private Optional<Role> role;
        private Wedding ownWedding;
        private Set<Wedding> weddingJobs = new HashSet<>();

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
        public void setRole(Optional<Role> role) {
            this.role = role;
        }

        /**
         * Returns an unmodifiable role set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code role} is null.
         */
        public Optional<Role> getRole() {
            return this.role;
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
