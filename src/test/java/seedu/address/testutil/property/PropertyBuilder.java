package seedu.address.testutil.property;

import seedu.address.model.property.AskingPrice;
import seedu.address.model.property.LandlordName;
import seedu.address.model.property.Location;
import seedu.address.model.property.Phone;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyType;

/**
 * A utility class to help with building Property objects.
 */
public class PropertyBuilder {

    public static final String DEFAULT_LANDLORD_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_LOCATION = "Bishan";
    public static final String DEFAULT_ASKING_PRICE = "600000";
    public static final String DEFAULT_PROPERTY_TYPE = "HDB";

    private LandlordName landlordName;
    private Phone phone;
    private Location location;
    private PropertyType propertyType;
    private AskingPrice askingPrice;

    /**
     * Creates a {@code PropertyBuilder} with the default details.
     */
    public PropertyBuilder() {
        landlordName = new LandlordName(DEFAULT_LANDLORD_NAME);
        phone = new Phone(DEFAULT_PHONE);
        location = new Location(DEFAULT_LOCATION);
        propertyType = new PropertyType(DEFAULT_PROPERTY_TYPE);
        askingPrice = new AskingPrice(DEFAULT_ASKING_PRICE);
    }

    /**
     * Initialises the PersonBuilder with the data of {@code propertyToCopy}.
     */
    public PropertyBuilder(Property propertyToCopy) {
        landlordName = propertyToCopy.getName();
        phone = propertyToCopy.getPhone();
        location = propertyToCopy.getLocation();
        askingPrice = propertyToCopy.getAskingPrice();
        propertyType = propertyToCopy.getPropertyType();
    }

    /**
     * Sets the {@code LandlordName} of the {@code Property} that we are building.
     */
    public PropertyBuilder withLandlordName(String landlordName) {
        this.landlordName = new LandlordName(landlordName);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Property} that we are building.
     */
    public PropertyBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Property} that we are building.
     */
    public PropertyBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code PropertyType} of the {@code Property} that we are building.
     */
    public PropertyBuilder withPropertyType(String propertyType) {
        this.propertyType = new PropertyType(propertyType);
        return this;
    }

    /**
     * Sets the {@code AskingPrice} of the {@code Property} that we are building.
     */
    public PropertyBuilder withAskingPrice(String askingPrice) {
        this.askingPrice = new AskingPrice(askingPrice);
        return this;
    }

    public Property build() {
        return new Property(landlordName, phone, location, askingPrice, propertyType);
    }
}
