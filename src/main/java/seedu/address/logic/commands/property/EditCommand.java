package seedu.address.logic.commands.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASKING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.AskingPrice;
import seedu.address.model.property.LandlordName;
import seedu.address.model.property.Location;
import seedu.address.model.property.Phone;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyType;

/**
 * Edits the details of an existing property in the property list.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the property identified "
            + "by the index number used in the displayed property list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "LANDLORD NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_ASKING_PRICE + "ASKING PRICE] "
            + "[" + PREFIX_TYPE + "TYPE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_LOCATION + "San Francisco "
            + PREFIX_ASKING_PRICE + "5000000 "
            + PREFIX_TYPE + "Condominium";

    public static final String MESSAGE_EDIT_PROPERTY_SUCCESS = "Edited Property: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one property must be provided.";
    public static final String MESSAGE_DUPLICATE_PROPERTY = "This property already exists in the address book.";

    private final Index index;
    private final EditPropertyDescriptor editPropertyDescriptor;

    /**
     * Creates an EditCommand to edit the specified {@code Property} by its index
     */
    public EditCommand(Index targetIndex, EditPropertyDescriptor editPropertyDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(editPropertyDescriptor);
        this.index = targetIndex;
        this.editPropertyDescriptor = editPropertyDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Property> lastShownList = model.getFilteredPropertyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROPERTY_INDEX);
        }

        Property propertyToEdit = lastShownList.get(index.getZeroBased());
        Property editedProperty = createEditedProperty(propertyToEdit, editPropertyDescriptor);

        if (!propertyToEdit.isSameProperty(editedProperty) && model.hasProperty(editedProperty)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROPERTY);
        }

        model.setProperty(propertyToEdit, editedProperty);
        model.updateFilteredPropertyList(Model.PREDICATE_SHOW_ALL_PROPERTIES);
        return new CommandResult(String.format(MESSAGE_EDIT_PROPERTY_SUCCESS, Messages.format(editedProperty)));
    }

    private static Property createEditedProperty(Property propertyToEdit,
                                                 EditPropertyDescriptor editPropertyDescriptor) {
        assert propertyToEdit != null;

        LandlordName updatedLandlordName = editPropertyDescriptor.getLandlordName().orElse(propertyToEdit.getName());
        Phone updatedPhone = editPropertyDescriptor.getPhone().orElse(propertyToEdit.getPhone());
        Location updatedLocation = editPropertyDescriptor.getLocation().orElse(propertyToEdit.getLocation());
        AskingPrice updatedAskingPrice = editPropertyDescriptor.getAskingPrice()
                .orElse(propertyToEdit.getAskingPrice());
        PropertyType updatedPropertyType = editPropertyDescriptor.getType().orElse(propertyToEdit.getPropertyType());

        return new Property(updatedLandlordName, updatedPhone, updatedLocation,
                updatedAskingPrice, updatedPropertyType);
    }
    /**
     * Stores the details to edit the property with. Each non-empty field value will replace the
     * corresponding field value of the property.
     */
    public static class EditPropertyDescriptor {
        private LandlordName name;
        private Phone phone;
        private Location location;
        private AskingPrice askingPrice;
        private PropertyType type;

        public EditPropertyDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPropertyDescriptor(EditPropertyDescriptor toCopy) {
            setLandlordName(toCopy.name);
            setPhone(toCopy.phone);
            setLocation(toCopy.location);
            setAskingPrice(toCopy.askingPrice);
            setType(toCopy.type);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, location, askingPrice, type);
        }

        // This method should not be used yet.
        public void setLandlordName(LandlordName name) {
            this.name = name;
        }

        public Optional<LandlordName> getLandlordName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        public void setAskingPrice(AskingPrice askingPrice) {
            this.askingPrice = askingPrice;
        }

        public Optional<AskingPrice> getAskingPrice() {
            return Optional.ofNullable(askingPrice);
        }

        public void setType(PropertyType type) {
            this.type = type;
        }

        public Optional<PropertyType> getType() {
            return Optional.ofNullable(type);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPropertyDescriptor)) {
                return false;
            }

            EditPropertyDescriptor otherEditPropertyDescriptor =
                    (EditPropertyDescriptor) other;

            return Objects.equals(name, otherEditPropertyDescriptor.name)
                    && Objects.equals(phone, otherEditPropertyDescriptor.phone)
                    && Objects.equals(location, otherEditPropertyDescriptor.location)
                    && Objects.equals(askingPrice, otherEditPropertyDescriptor.askingPrice)
                    && Objects.equals(type, otherEditPropertyDescriptor.type);

        }
    }
}
