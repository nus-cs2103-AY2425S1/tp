package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Property;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class DeletePropertyToBuyCommand extends Command {

    public static final String COMMAND_WORD = "delBuy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the property of the specified person "
            + "using the index number used in the displayed person list and "
            + "the index number used int he displayed property-to-buy list.\n"
            + "Existing properties, other than the one being deleted, will remain.\n"
            + "Parameters: PERSON_INDEX (must be a positive integer) PROPERTY_TO_BUY_INDEX (must be a positive integer)"
            + "Example: " + COMMAND_WORD + " 1 " + "2\n"
            + "This means that we want to delete the 2nd property the 1st person wants to buy.";

    public static final String MESSAGE_PERSON_PROPERTY_SUCCESS = "Deleted Buying Property: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";



    private final Index personIndex;
    private final Index propertyIndex;
    private EditPersonPropertyToBuyDescriptor editPersonPropertyDescriptor;

    /**
     * @param personIndex of the person in the filtered person list to edit.
     * @param propertyIndex of the property belonging to the person in the filtered person list to edit.
     * @param editPersonPropertyDescriptor details to edit the person and his/her property with
     */
    public DeletePropertyToBuyCommand(Index personIndex, Index propertyIndex,
                                      EditPersonPropertyToBuyDescriptor editPersonPropertyDescriptor) {
        requireNonNull(personIndex);
        requireNonNull(propertyIndex);
        requireNonNull(editPersonPropertyDescriptor);

        this.personIndex = personIndex;
        this.propertyIndex = propertyIndex;
        this.editPersonPropertyDescriptor = new EditPersonPropertyToBuyDescriptor(editPersonPropertyDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());

        editPersonPropertyDescriptor.setBuyingProperties(personToEdit.getListOfBuyingProperties());

        if (editPersonPropertyDescriptor.getBuyingProperties().get() == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
        }

        if (personToEdit.getListOfBuyingProperties().isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_PROPERTIES_TO_DELETE);
        }

        if (propertyIndex.getZeroBased() >= personToEdit.getListOfBuyingProperties().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
        }

        editPersonPropertyDescriptor.deleteBuyingProperties(propertyIndex);

        Person editedPerson = createEditedPerson(personToEdit, editPersonPropertyDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_PERSON_PROPERTY_SUCCESS,
                Messages.formatProperties(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit,
                                             EditPersonPropertyToBuyDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        ObservableList<Property> updatedListOfSellingProperties = (ObservableList<Property>)
                editPersonDescriptor.getSellingProperties()
                .orElse(personToEdit.getListOfSellingProperties());
        ObservableList<Property> updatedListOfBuyingProperties = (ObservableList<Property>)
                editPersonDescriptor.getBuyingProperties()
                .orElse(personToEdit.getListOfBuyingProperties());
        ObservableList<Property> updatedListOfPropertiesSold = (ObservableList<Property>)
                editPersonDescriptor.getPropertiesSold()
                        .orElse(personToEdit.getListOfPropertiesSold());
        ObservableList<Property> updatedListOfPropertiesBought = (ObservableList<Property>)
                editPersonDescriptor.getPropertiesBought()
                        .orElse(personToEdit.getListOfPropertiesBought());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                updatedListOfSellingProperties, updatedListOfBuyingProperties,
                updatedListOfPropertiesSold, updatedListOfPropertiesBought);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletePropertyToBuyCommand)) {
            return false;
        }

        DeletePropertyToBuyCommand otherEditCommand = (DeletePropertyToBuyCommand) other;
        return personIndex.equals(otherEditCommand.personIndex)
                && propertyIndex.equals(otherEditCommand.propertyIndex)
                && editPersonPropertyDescriptor.equals(otherEditCommand.editPersonPropertyDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personIndex", personIndex)
                .add("propertyIndex", propertyIndex)
                .add("editPersonDescriptor", editPersonPropertyDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonPropertyToBuyDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private ObservableList<Property> sellingProperties;
        private ObservableList<Property> buyingProperties;
        private ObservableList<Property> propertiesSold;
        private ObservableList<Property> propertiesBought;

        public EditPersonPropertyToBuyDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonPropertyToBuyDescriptor(EditPersonPropertyToBuyDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setSellingProperties(toCopy.sellingProperties);
            setBuyingProperties(toCopy.buyingProperties);
            setPropertiesSold(toCopy.propertiesSold);
            setPropertiesBought(toCopy.propertiesBought);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
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

        public void setSellingProperties(List<Property> sellingProperties) {
            this.sellingProperties = (sellingProperties != null) ? FXCollections.observableArrayList(sellingProperties)
                    : null;
        }

        public Optional<List<Property>> getSellingProperties() {
            return Optional.ofNullable(sellingProperties);
        }

        public void setBuyingProperties(List<Property> buyingProperties) {
            this.buyingProperties = (buyingProperties != null) ? FXCollections.observableArrayList(buyingProperties)
                    : null;
        }

        public Optional<List<Property>> getBuyingProperties() {
            return Optional.ofNullable(buyingProperties);
        }

        /**
         * Removes the property specified by the propertyIndex
         */
        public void deleteBuyingProperties(Index propertyIndex) {
            if (buyingProperties.size() > 0) {
                buyingProperties.remove(propertyIndex.getZeroBased());
            }
        }

        public void setPropertiesBought(List<Property> propertiesBought) {
            this.propertiesBought = (propertiesBought != null) ? FXCollections.observableArrayList(propertiesBought)
                    : null;
        }

        public Optional<List<Property>> getPropertiesBought() {
            return Optional.ofNullable(propertiesBought);
        }


        public void setPropertiesSold(List<Property> propertiesSold) {
            this.propertiesSold = (propertiesSold != null) ? FXCollections.observableArrayList(propertiesSold)
                    : null;
        }

        public Optional<List<Property>> getPropertiesSold() {
            return Optional.ofNullable(propertiesSold);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonPropertyToBuyDescriptor)) {
                return false;
            }

            EditPersonPropertyToBuyDescriptor otherEditPersonDescriptor = (EditPersonPropertyToBuyDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags)
                    && Objects.deepEquals(sellingProperties, otherEditPersonDescriptor.sellingProperties)
                    && Objects.deepEquals(buyingProperties, otherEditPersonDescriptor.buyingProperties)
                    && Objects.deepEquals(propertiesSold, otherEditPersonDescriptor.propertiesSold)
                    && Objects.deepEquals(propertiesBought, otherEditPersonDescriptor.propertiesBought);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .add("properties-to-sell", sellingProperties)
                    .add("properties-to-buy", buyingProperties)
                    .add("properties-sold", propertiesSold)
                    .add("properties-bought", propertiesBought)
                    .toString();
        }
    }
}

