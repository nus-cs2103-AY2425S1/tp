package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WEDDINGS;

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
 * Assigns, reassigns or removes the role of an existing person in the address book.
 * Assigns wedding(s) to an existing person in the address book.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": assigns a role and/or wedding(s) to person identified "
            + "by index or name. Weddings are identified by index.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) or KEYWORD (the name of contact) "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_WEDDING + "WEDDING_INDEX]...\n"
            + "Example: \n" + COMMAND_WORD
            + " Alex Yeoh " + PREFIX_ROLE + "food vendor\n"
            + COMMAND_WORD + " Alex Yeoh " + PREFIX_WEDDING + "1\n"
            + COMMAND_WORD + " 1 " + PREFIX_ROLE + "florist " + PREFIX_WEDDING + "1";

    public static final String MESSAGE_ASSIGN_PERSON_ROLE_SUCCESS = "Assigned role to: %1$s";

    public static final String MESSAGE_ASSIGN_PERSON_TO_WEDDING_SUCCESS = "Assigned %1$s to wedding(s):\n%2$s";

    public static final String MESSAGE_DUPLICATE_ROLE = "This role has already been assigned to %1$s.";

    public static final String MESSAGE_DUPLICATE_WEDDING = "Person has already been assigned to wedding(s).";

    public static final String MESSAGE_ASSIGN_EMPTY_PERSON_LIST_ERROR = "There is no person to assign.";

    public static final String MESSAGE_CLIENT_ASSIGN_ERROR = "Person is already a client of the wedding";

    public static final String MESSAGE_ASSIGN_EMPTY_WEDDING_LIST_ERROR =
            "There is no wedding to assign as the wedding list is empty.\n"
            + "Please refresh the list with a command (e.g. list, vieww).";

    public static final String MESSAGE_MISSING_FIELDS = "There is nothing to assign. \n"
            + "Please specify the role or wedding to assign with r/ROLE or w/WEDDING";

    public static final String MESSAGE_DUPLICATE_HANDLING =
            "Please specify the index of the contact you want to assign.\n"
                    + "Find the index from the list below and type: assign INDEX " + "[" + PREFIX_ROLE + "ROLE] "
                    + "[" + PREFIX_WEDDING + "WEDDING_INDEX]...\n"
                    + "Example: " + COMMAND_WORD + " 1 " + PREFIX_ROLE + "florist " + PREFIX_WEDDING + "1";
    private final Index index;
    private final NameMatchesKeywordPredicate predicate;
    private final PersonWithRoleDescriptor personWithRoleDescriptor;
    private final Set<Index> weddingIndices;

    /**
     * Creates a new {@code AssignCommand} to assign a role or wedding(s) to a specified person.
     * Either index or predicate must be provided to identify the person to be assigned, but not both.
     *
     * @param index {@code Index} of the person in the filtered person list to assign role or wedding(s).
     * @param predicate {@code NameMatchesKeywordPredicate} used to filter the person list to find the target person.
     * @param personWithRoleDescriptor details of the person, including the new role to be assigned.
     * @param weddingIndices set of indices representing the weddings to assign to the person.
     */
    public AssignCommand(Index index, NameMatchesKeywordPredicate predicate,
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
        Person personToAssign;

        if (this.index != null) {
            personToAssign = assignWithIndex(model);
        } else {
            personToAssign = assignWithKeyword(model);
        }

        Person assignedPerson;
        if (weddingIndices != null) {
            checkValidWeddingIndices(model);
            checkIsClientOfWedding(model, personToAssign);
            checkIsAssignedWeddings(model, personToAssign);
            assignWeddingJobs(model);
            assignedPerson = createPersonWithRole(personToAssign, personWithRoleDescriptor);
            model.setPerson(personToAssign, assignedPerson);
            model.updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(
                    MESSAGE_ASSIGN_PERSON_TO_WEDDING_SUCCESS,
                    Messages.format(assignedPerson),
                    Messages.format(assignedPerson.getWeddingJobs()))
            );
        } else {
            assignedPerson = createPersonWithRole(personToAssign, personWithRoleDescriptor);
            if (personToAssign.getRole().equals(assignedPerson.getRole())) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_ROLE, Messages.format(assignedPerson)));
            }
            model.setPerson(personToAssign, assignedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_ASSIGN_PERSON_ROLE_SUCCESS,
                    Messages.format(assignedPerson)));
        }
    }

    /**
     * Performs {@code AssignCommand} logic when the input is an index.
     *
     * @param model {@code Model} which the command should operate on.
     * @return the person to be assigned.
     * @throws CommandException if the list is empty or if the index is invalid.
     */
    public Person assignWithIndex(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_ASSIGN_EMPTY_PERSON_LIST_ERROR);
        }

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                            index.getOneBased(), lastShownList.size()));
        }

        return lastShownList.get(index.getZeroBased());
    }

    /**
     * Performs {@code AssignCommand} logic when the input is a {@code String}.
     *
     * @param model {@code Model} which the command should operate on.
     * @return the person to be assigned.
     * @throws CommandException if the filtered list using {@code predicate} is empty or contains more than 1 element.
     */
    public Person assignWithKeyword(Model model) throws CommandException {
        model.updateFilteredPersonList(predicate);
        List<Person> filteredList = model.getFilteredPersonList();

        if (filteredList.isEmpty()) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(MESSAGE_ASSIGN_EMPTY_PERSON_LIST_ERROR);
        } else if (filteredList.size() == 1) {
            return filteredList.get(0);
        } else {
            throw new CommandException(MESSAGE_DUPLICATE_HANDLING);
        }
    }

    /**
     * Returns a {@code Person} with updated role and wedding job list.
     *
     * @param personToAddRole the original person from the contact list to be assigned.
     * @param personWithRoleDescriptor details of the person, including the new role to be assigned.
     * @return the updated {@code Person} with updated role and wedding(s).
     * @throws CommandException if the wedding to be assigned is already assigned.
     */
    private static Person createPersonWithRole(Person personToAddRole,
                                               PersonWithRoleDescriptor personWithRoleDescriptor)
            throws CommandException {
        assert personToAddRole != null;

        Name updatedName = personWithRoleDescriptor.getName().orElse(personToAddRole.getName());
        Phone updatedPhone = personWithRoleDescriptor.getPhone().orElse(personToAddRole.getPhone());
        Email updatedEmail = personWithRoleDescriptor.getEmail().orElse(personToAddRole.getEmail());
        Address updatedAddress = personWithRoleDescriptor.getAddress().orElse(personToAddRole.getAddress());

        // if the role is null -> r/ prefix was not specified, retain original role
        // if role is Optional -> update role
        Optional<Role> updatedRole = personWithRoleDescriptor.getRole() == null
                ? personToAddRole.getRole()
                : personWithRoleDescriptor.getRole();
        Wedding ownWedding = personToAddRole.getOwnWedding();

        Person person = new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedRole,
                ownWedding);
        person.setWeddingJobs(personToAddRole.getWeddingJobs());

        for (Wedding wedding : personWithRoleDescriptor.getWeddingJobs()) {
            if (person.isAssignedToWedding(wedding)) {
                throw new CommandException(MESSAGE_DUPLICATE_WEDDING);
            }
        }

        person.setWeddingJobs(personWithRoleDescriptor.getWeddingJobs());
        return person;
    }

    /**
     * Checks if the wedding indices inputs are valid.
     *
     * @param model {@code Model} which the command should operate on.
     * @throws CommandException if the list is empty or if the index is invalid.
     */
    public void checkValidWeddingIndices(Model model) throws CommandException {
        List<Wedding> lastShownList = model.getFilteredWeddingList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_ASSIGN_EMPTY_WEDDING_LIST_ERROR);
        }

        for (Index index : weddingIndices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(String.format(
                        Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX, index.getOneBased(), lastShownList.size()));
            }
        }
    }

    /**
     * Checks if person is client of the wedding to be assigned.
     *
     * @param model {@code Model} which the command should operate on.
     * @param personToAssign the target person to check.
     * @throws CommandException if the {@code personToAssign} is the client of the wedding to be assigned.
     */
    public void checkIsClientOfWedding(Model model, Person personToAssign) throws CommandException {
        List<Wedding> weddings = model.getFilteredWeddingList();
        for (Index index : weddingIndices) {
            Wedding wedding = weddings.get(index.getZeroBased());
            if (personToAssign.getOwnWedding() != null && personToAssign.getOwnWedding().isSameWedding(wedding)) {
                throw new CommandException(MESSAGE_CLIENT_ASSIGN_ERROR);
            }
        }
    }

    /**
     * Checks if any of the weddings to be assigned are already assigned to the person.
     *
     * @param model {@code Model} which the command should operate on.
     * @param personToAssign the target person to check.
     * @throws CommandException if any of the weddings to be assigned are already assigned to {@code personToAssign}.
     */
    public void checkIsAssignedWeddings(Model model, Person personToAssign) throws CommandException {
        List<Wedding> weddings = model.getFilteredWeddingList();
        for (Index index : weddingIndices) {
            Wedding wedding = weddings.get(index.getZeroBased());
            if (personToAssign.isAssignedToWeddingNonClient(wedding)) {
                throw new CommandException(MESSAGE_DUPLICATE_WEDDING);
            }
        }
    }

    /**
     * Assigns the person with wedding jobs based on the provided indices.
     *
     * @param model {@code Model} which the command should operate on, which contains the list of weddings.
     */
    public void assignWeddingJobs(Model model) {
        List<Wedding> weddingList = model.getFilteredWeddingList();

        for (Index index : weddingIndices) {
            personWithRoleDescriptor.addWeddingJob(weddingList.get(index.getZeroBased()));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof AssignCommand)) {
            return false;
        }

        AssignCommand otherCommand = (AssignCommand) other;

        boolean indexEqual = Objects.equals(this.index, otherCommand.index);
        boolean predicateEqual = Objects.equals(this.predicate, otherCommand.predicate);
        boolean descriptorEqual = Objects.equals(this.personWithRoleDescriptor, otherCommand.personWithRoleDescriptor);
        boolean weddingIndicesEqual = Objects.equals(this.weddingIndices, otherCommand.weddingIndices);

        return indexEqual && predicateEqual && descriptorEqual && weddingIndicesEqual;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("index", index)
                .add("personWithRoleDescriptor", personWithRoleDescriptor).toString();
    }

    /**
     * Stores details about a person for the purpose of assigning or removing a role.
     * Each non-empty field value will replace the corresponding field value of the person.
     */
    public static class PersonWithRoleDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Optional<Role> role = Optional.empty();
        private Wedding ownWedding;
        private Set<Wedding> weddingJobs = new HashSet<>();

        /**
         * Default constructor for {@code PersonWithRoleDescriptor}.
         * Initializes an empty descriptor with no values set.
         */
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
