package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ETA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
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
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.delivery.Archive;
import seedu.address.model.delivery.Cost;
import seedu.address.model.delivery.Date;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.Eta;
import seedu.address.model.delivery.ItemName;
import seedu.address.model.delivery.Status;
import seedu.address.model.delivery.Time;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;
import seedu.address.ui.InspectWindow;

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
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String INSPECT_MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the delivery identified "
           + "by the index number used in the displayed delivery list. "
           + "Existing values will be overwritten by the input values.\n"
           + "Parameters: INDEX (must be a positive integer) "
           + "[" + PREFIX_ITEMS + "ITEM NAME] "
           + "[" + PREFIX_ADDRESS + "ADDRESS] "
           + "[" + PREFIX_COST + "COST] "
           + "[" + PREFIX_ETA + "ETA] "
           + "[" + PREFIX_STATUS + "STATUS]\n"
           + "Example: " + COMMAND_WORD + " 1 "
           + PREFIX_ITEMS + "TV "
           + PREFIX_ADDRESS + "Clementi Ave 3, Blk 462, S120311 "
           + PREFIX_COST + "$300"
           + PREFIX_STATUS + "not delivered ";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_EDIT_DELIVERY_SUCCESS = "Edited Delivery %d: %2$s";


    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;
    private final EditDeliveryDescriptor editDeliveryDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
        this.editDeliveryDescriptor = null;
    }

    /**
     * @param index of delivery in delivery list to edit
     * @param editDeliveryDescriptor details to edit delivery with
     */
    public EditCommand(Index index, EditDeliveryDescriptor editDeliveryDescriptor) {
        requireNonNull(index);
        requireNonNull(editDeliveryDescriptor);

        this.index = index;
        this.editDeliveryDescriptor = editDeliveryDescriptor;
        this.editPersonDescriptor = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        boolean isInspect = AddressBookParser.getInspect();
        if (!isInspect) {
            requireNonNull(editPersonDescriptor);
            return editPerson(model);
        } else {
            requireNonNull(editDeliveryDescriptor);
            return editDelivery();
        }
    }

    /**
     * Edits person according to descriptor and returns CommandResult
     */
    private CommandResult editPerson(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        assert editPersonDescriptor != null;
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(
            String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson))
        );
    }

    /**
     * Edits delivery according to descriptor and returns CommandResult
     */
    private CommandResult editDelivery() throws CommandException {
        Person inspectedPerson = InspectWindow.getInspectedPerson();

        //Currently no filtered list for delivery

        List<Delivery> deliveryList = inspectedPerson.getUnmodifiableDeliveryList();
        if (index.getZeroBased() >= deliveryList.size()
                || index.getZeroBased() >= inspectedPerson.getFirstArchivedIndex().getZeroBased()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
        }

        Delivery deliveryToEdit = deliveryList.get(index.getZeroBased());
        assert editDeliveryDescriptor != null;
        Delivery editedDelivery = createEditedDelivery(deliveryToEdit, editDeliveryDescriptor);
        inspectedPerson.setDelivery(deliveryToEdit, editedDelivery);
        return new CommandResult(
            String.format(MESSAGE_EDIT_DELIVERY_SUCCESS, index.getOneBased(), Messages.format(editedDelivery))
        );
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
        Role updatedRole = editPersonDescriptor.getRole().orElse(personToEdit.getRole());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedRole, updatedAddress, updatedTags);
    }

    /**
     * Creates and returns a {@code Delivery} with the details of {@code toEdit}
     * edited with {@code descriptor}.
     */
    private static Delivery createEditedDelivery(Delivery toEdit, EditDeliveryDescriptor descriptor) {
        assert toEdit != null;

        Archive archive = toEdit.getArchive();

        Set<ItemName> updatedItems = descriptor.getItems().orElse(toEdit.getItems());
        Address updatedAddress = descriptor.getAddress().orElse(toEdit.getAddress());
        Cost updatedCost = descriptor.getCost().orElse(toEdit.getCost());
        Eta updatedEta = descriptor.getEta().orElse(toEdit.getEta());
        Status updatedStatus = descriptor.getStatus().orElse(toEdit.getStatus());

        return new Delivery(updatedItems, updatedAddress, updatedCost, updatedEta, updatedStatus, archive);
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
        private Role role;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setRole(toCopy.role);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, role, address, tags);
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

        public void setRole(Role role) {
            this.role = role;
        }

        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
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
                    && Objects.equals(role, otherEditPersonDescriptor.role)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("role", role)
                    .add("address", address)
                    .add("tags", tags)
                    .toString();
        }
    }

    /**
     * Stores the details to edit the Delivery with
     */
    public static class EditDeliveryDescriptor {

        private Set<ItemName> items;
        private Address address;
        private Cost cost;
        private Date date;
        private Time time;
        private Eta eta;
        private Status status;

        public EditDeliveryDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditDeliveryDescriptor(EditDeliveryDescriptor toCopy) {
            setItems(toCopy.items);
            setAddress(toCopy.address);
            setCost(toCopy.cost);
            setDate(toCopy.date);
            setTime(toCopy.time);
            setEta(toCopy.eta);
            setStatus(toCopy.status);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(items, address, cost, date, time, eta);
        }

        public void setItems(Set<ItemName> items) {
            this.items = (items != null) ? new HashSet<>(items) : new HashSet<>();
        }

        public Optional<Set<ItemName>> getItems() {
            return (items != null) ? Optional.of(Collections.unmodifiableSet(items)) : Optional.empty();
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setCost(Cost cost) {
            this.cost = cost;
        }

        public Optional<Cost> getCost() {
            return Optional.ofNullable(cost);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public Optional<Time> getTime() {
            return Optional.ofNullable(time);
        }

        public void setEta(Eta eta) {
            this.eta = eta;
        }

        public Optional<Eta> getEta() {
            return Optional.ofNullable(eta);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditDeliveryDescriptor)) {
                return false;
            }

            EditDeliveryDescriptor otherEditDeliveryDescriptor = (EditDeliveryDescriptor) other;
            return Objects.equals(address, otherEditDeliveryDescriptor.address)
                    && Objects.equals(cost, otherEditDeliveryDescriptor.cost)
                    && Objects.equals(date, otherEditDeliveryDescriptor.date)
                    && Objects.equals(time, otherEditDeliveryDescriptor.time)
                    && Objects.equals(eta, otherEditDeliveryDescriptor.eta)
                    && Objects.equals(status, otherEditDeliveryDescriptor.status)
                    && Objects.equals(items, otherEditDeliveryDescriptor.items);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("items", items)
                    .add("address", address)
                    .add("cost", cost)
                    .add("date", date)
                    .add("time", time)
                    .add("eta", eta)
                    .add("status", status)
                    .toString();
        }
    }
}
