package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_EMPTY_LIST_ERROR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WEDDINGS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": assigns a role and/or wedding(s) to person identified "
            + "by index or name. Weddings are identified by index.\n"
            + "For example: \n" + COMMAND_WORD
            + " Alex Yeoh " + PREFIX_ROLE + "food vendor\n"
            + COMMAND_WORD + " Alex Yeoh " + PREFIX_WEDDING + "1\n"
            + COMMAND_WORD + " 1 " + PREFIX_ROLE + "florist " + PREFIX_WEDDING + "1";

    public static final String MESSAGE_ASSIGN_PERSON_ROLE_SUCCESS = "Assigned role to: %1$s";

    public static final String MESSAGE_ASSIGN_PERSON_TO_WEDDING_SUCCESS = "Assigned %1$s to wedding(s):\n %2$s";

    public static final String MESSAGE_DUPLICATE_ROLE = "This role has already been assigned to %1$s.";
    public static final String MESSAGE_DUPLICATE_HANDLING =
            "Please specify the index of the contact you want to assign.\n"
                    + "Find the index from the list below and type edit INDEX ...\n"
                    + "Example: " + COMMAND_WORD + " 1 ...";
    private final Index index;
    private final NameMatchesKeywordPredicate predicate;
    private final PersonWithRoleDescriptor personWithRoleDescriptor;
    private final Set<Index> weddingIndices;

    /**
     * @param index of the person in the filtered person list to assign role
     * @param personWithRoleDescriptor details of the person to assign role
     */
    public RoleCommand(Index index, NameMatchesKeywordPredicate predicate,
                       PersonWithRoleDescriptor personWithRoleDescriptor, Set<Index> weddingIndices) {
        requireNonNull(personWithRoleDescriptor);

        this.index = index;
        this.predicate = predicate;
        this.personWithRoleDescriptor = new PersonWithRoleDescriptor(personWithRoleDescriptor);
        this.weddingIndices = weddingIndices;
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

        Person assignedPerson;
        if (weddingIndices != null) {
            generateWeddingJobs(model);
            assignedPerson = createPersonWithRole(personToAssign, personWithRoleDescriptor);
            model.setPerson(personToAssign, assignedPerson);
            model.updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);
            return new CommandResult(String.format(
                    MESSAGE_ASSIGN_PERSON_TO_WEDDING_SUCCESS,
                    Messages.format(assignedPerson),
                    assignedPerson.getWeddingJobs().stream()
                            .map(Messages::format)
                            .collect(Collectors.joining(", "))
            ));

        } else {
            assignedPerson = createPersonWithRole(personToAssign, personWithRoleDescriptor);
            if (personToAssign.getRole().equals(assignedPerson.getRole())) {
                throw new CommandException(MESSAGE_DUPLICATE_ROLE);

            }
            model.setPerson(personToAssign, assignedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_ASSIGN_PERSON_ROLE_SUCCESS,
                    Messages.format(assignedPerson)));
        }
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
        Optional<Role> updatedRole = personWithRoleDescriptor.getRole().or(personToAddRole::getRole);
        Person person = new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedRole, null);
        person.setWeddingJobs(personToAddRole.getWeddingJobs());
        person.setWeddingJobs(personWithRoleDescriptor.getWeddingJobs());
        return person;
    }

    /**
     * Associates the person with wedding jobs based on the provided indices.
     *
     * @param model The model containing the list of weddings.
     */
    public void generateWeddingJobs(Model model) {
        List<Wedding> weddingList = model.getFilteredWeddingList();

        for (Index index : weddingIndices) {
            personWithRoleDescriptor.addWeddingJob(weddingList.get(index.getZeroBased()));
            System.out.println(personWithRoleDescriptor.weddingJobs);

        }
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
        private Optional<Role> role = Optional.empty();
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

        public void setRole(Optional<Role> role) {
            this.role = role;
        }

        public Optional<Role> getRole() {
            return this.role;
        }

        public Set<Wedding> getWeddingJobs() {
            return weddingJobs;
        }

        /**
         * Adds a wedding to the list of wedding jobs.
         *
         * @param wedding {@code Wedding} to be added to the list of wedding jobs
         */
        public void addWeddingJob(Wedding wedding) {
            if (ownWedding == null || !ownWedding.isSameWedding(wedding)) {
                weddingJobs.add(wedding);
            } else {
                throw new IllegalArgumentException("Cannot add own wedding as a job.");
            }
        }

        /**
         * Adds a list of wedding jobs to the pre-existing list.
         *
         * @param weddingJobs {@code Set<Wedding>} to be added to the list of wedding jobs
         */
        public void setWeddingJobs(Set<Wedding> weddingJobs) {
            for (Wedding wedding : weddingJobs) {
                this.addWeddingJob(wedding);
            }
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
