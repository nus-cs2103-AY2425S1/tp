package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Name;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonAdaptedListing {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Listing's %s field is missing.";

    private final String listingName;
    private final String price;
    private final String area;
    private final String region;
    private final String address;
    private final JsonAdaptedPerson seller;
    private final List<JsonAdaptedPerson> buyers = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedListing} with the given listing details.
     */
    @JsonCreator
    public JsonAdaptedListing(@JsonProperty("name") String listingName, @JsonProperty("price") String price,
                             @JsonProperty("area") String area, @JsonProperty("region") String region,
                             @JsonProperty("address") String address, @JsonProperty("seller") JsonAdaptedPerson seller,
                              @JsonProperty("buyers") List<JsonAdaptedPerson> buyers) {
        this.listingName = listingName;
        this.price = price;
        this.area = area;
        this.region = region;
        this.address = address;
        this.seller = seller;
        if (buyers != null) {
            this.buyers.addAll(buyers);
        }
    }

    /**
     * Converts a given {@code Listing} into this class for Jackson use.
     */
    public JsonAdaptedListing(Listing source) {
        listingName = source.getName().fullName;
        price = source.getPrice().getFormattedValue();
        area = source.getArea().toString();
        region = source.getRegion().name();
        address = source.getAddress().value;
        seller = new JsonAdaptedPerson(source.getSeller());
        buyers.addAll(source.getBuyers().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted listing object into the model's {@code Listing} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Listing toModelType() throws IllegalValueException {
        final List<Person> listingBuyers = new ArrayList<>();
        for (JsonAdaptedPerson tag : buyers) {
            listingBuyers.add(tag.toModelType());
        }

        if (listingName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(listingName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelListingName = new Name(listingName);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        // Can add check for price
//        if (!Phone.isValidPhone(phone)) {
//            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
//        }
        final Price modelPrice = new Price(price, new BigDecimal(price));

        if (area == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Area.class.getSimpleName()));
        }
        // Can implement check for area
//        if (!Email.isValidEmail(area)) {
//            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
//        }
        final Area modelArea = new Area(Integer.parseInt(area));

        if (region == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Region.class.getSimpleName()));
        }

        final Region modelRegion = Region.fromString(region);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }

        final Address modelAddress = new Address(address);

        if (seller == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }

        final Person modelSeller = seller.toModelType();

        final Set<Person> modelBuyers = new HashSet<>(listingBuyers);

        return new Listing(modelListingName, modelAddress, modelPrice, modelArea, modelRegion,
                modelSeller, modelBuyers);
    }
}
