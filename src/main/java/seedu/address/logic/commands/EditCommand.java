package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT_PAYMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Policy;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_PARAMETERS_EXAMPLE = "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]\n"
            + "[" + PREFIX_POLICY + "POLICY_INDEX "
            + PREFIX_POLICY_NAME + "POLICY_NAME "
            + PREFIX_POLICY_START_DATE + "START_DATE "
            + PREFIX_POLICY_END_DATE + "END_DATE "
            + PREFIX_NEXT_PAYMENT_DATE + "PAY_DATE "
            + PREFIX_PAYMENT_AMOUNT + "AMOUNT]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Existing values will be overwritten by the input values.\n"
            + MESSAGE_PARAMETERS_EXAMPLE;

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.\n"
            + MESSAGE_PARAMETERS_EXAMPLE;
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

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

        model.setPerson(personToEdit, editedPerson);
        model.commitAddressBook();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor)
            throws CommandException {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        //placeholder
        Appointment appointment = editPersonDescriptor.getAppointment().orElse(personToEdit.getAppointment());
        Birthday birthday = editPersonDescriptor.getBirthday().orElse(personToEdit.getBirthday());
        List<Policy> policies = editPolicies(personToEdit,
                editPersonDescriptor.getPolicies().orElse(Collections.emptyMap()));

        Person newPerson = new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                appointment, birthday);

        newPerson.setPolicies(policies);

        return newPerson;
    }

    /**
     * Edits the policies of a given person based on a map of new policies and their corresponding indexes.
     *
     * @param personToEdit The person whose policies are to be edited.
     * @param newPolicies A map where the key is the {@code Index} of the policy to be replaced, and the value is the
     *                    new {@code Policy}.
     * @return A list of updated policies after applying the changes specified in the {@code newPolicies} map.
     * @throws CommandException If any provided index in the {@code newPolicies} map is out of bounds, indicating that
     *     the specified policy does not exist.
     */
    public static List<Policy> editPolicies(Person personToEdit, Map<Index, Policy> newPolicies)
            throws CommandException {
        List<Policy> oldPolicies = personToEdit.getPolicies();

        List<Policy> updatedPolicies = new ArrayList<Policy>(oldPolicies);
        int policiesSize = updatedPolicies.size();

        for (Map.Entry<Index, Policy> entry : newPolicies.entrySet()) {
            Index index = entry.getKey();
            Policy policy = entry.getValue();
            int zeroBasedIndex = index.getZeroBased();

            if (zeroBasedIndex >= policiesSize) {
                throw new CommandException(Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
            }

            int duplicatePolicyIndex = getDuplicatePolicyIndex(policy, updatedPolicies);
            boolean isPolicyToEdit = (duplicatePolicyIndex == zeroBasedIndex);
            boolean isNotDuplicatePolicy = (duplicatePolicyIndex == -1);

            if (!isNotDuplicatePolicy && !isPolicyToEdit) {
                throw new CommandException(Messages.MESSAGE_DUPLICATE_POLICY_NAME);
            }

            updatedPolicies.set(zeroBasedIndex, policy);
        }

        return updatedPolicies;
    }

    /**
     * Checks if a given policy already exists in the provided list of policies. If a policy with the same name is
     * found, the method returns the index of the matching policy in the list. If no matching policy is found, it
     * returns {@code -1}.
     *
     * @param policy      the {@link Policy} object to check for duplication
     * @param policyList  the list of {@link Policy} objects to compare against
     * @return the index of the duplicate policy in the list, or {@code -1} if
     *         no duplicate is found
     */
    public static Integer getDuplicatePolicyIndex(Policy policy, List<Policy> policyList) {
        String policyName = policy.getPolicyName();
        int size = policyList.size();

        for (int i = 0; i < size; i++) {
            if (policyName.equals(policyList.get(i).getPolicyName())) {
                return i;
            }
        }

        return -1;
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
        private Birthday birthday;
        private Appointment appointment;

        private Map<Index, Policy> policies;

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
            setAppointment(toCopy.appointment);
            setBirthday(toCopy.birthday);
            setPolicies(toCopy.policies);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags, birthday, appointment, policies);
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

        public void setBirthday(Birthday birthday) {
            this.birthday = birthday;
        }
        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }

        public void setAppointment(Appointment appointment) {
            this.appointment = appointment;
        }

        public Optional<Appointment> getAppointment() {
            return Optional.ofNullable(appointment);
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
         * Sets {@code policies} to this object's {@code policies}.
         * A defensive copy of {@code policies} is used internally.
         */
        public void setPolicies(Map<Index, Policy> policies) {
            this.policies = (policies != null) ? new HashMap<Index, Policy>(policies) : null;
        }

        /**
         * Returns an unmodifiable policy list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code policies} is null.
         */
        public Optional<Map<Index, Policy>> getPolicies() {
            return (policies != null) ? Optional.of(Collections.unmodifiableMap(policies)) : Optional.empty();
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
                    && Objects.equals(birthday, otherEditPersonDescriptor.birthday)
                    && Objects.equals(appointment, otherEditPersonDescriptor.appointment)
                    && Objects.equals(policies, otherEditPersonDescriptor.policies);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .add("birthday", birthday)
                    .add("appointment", appointment)
                    .add("policies", policies)
                    .toString();
        }
    }
}
