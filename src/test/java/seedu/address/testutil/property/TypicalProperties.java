package seedu.address.testutil.property;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ASKING_PRICE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASKING_PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROPERTY_TYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROPERTY_TYPE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.PropertyList;
import seedu.address.model.property.Property;

/**
 * A utility class containing a list of {@code Property} objects to be used in testing.
 */
public class TypicalProperties {
    public static final Property ALICE = new PropertyBuilder().withLandlordName("Alice Pauline")
            .withLocation("Jurong West").withPhone("94351253").withAskingPrice("450000")
            .withPropertyType("HDB").build();
    public static final Property BENSON = new PropertyBuilder().withLandlordName("Benson Meier")
            .withLocation("Clementi").withPhone("98765432").withAskingPrice("1500000")
            .withPropertyType("Condominium").build();
    public static final Property CARL = new PropertyBuilder().withLandlordName("Carl Kurz").withPhone("95352563")
            .withLocation("Aljunied").withAskingPrice("1500000").withPropertyType("HDB").build();
    public static final Property DANIEL = new PropertyBuilder().withLandlordName("Daniel Meier").withPhone("87652533")
            .withLocation("Bukit Timah").withAskingPrice("3500000").withPropertyType("Terrace").build();
    public static final Property ELLE = new PropertyBuilder().withLandlordName("Elle Meyer").withPhone("92822224")
            .withLocation("Marsiling").withAskingPrice("6000000").withPropertyType("HDB").build();
    public static final Property FIONA = new PropertyBuilder().withLandlordName("Fiona Kunz").withPhone("94822272")
            .withLocation("Shibuya").withAskingPrice("6000000").withPropertyType("Apartment").build();
    public static final Property GEORGE = new PropertyBuilder().withLandlordName("George Best").withPhone("98272923")
            .withLocation("Tampines").withAskingPrice("4500000").withPropertyType("HDB").build();

    // Manually added
    public static final Property HOON = new PropertyBuilder().withLandlordName("Hoon Meier").withPhone("84842727")
            .withLocation("Little India").withAskingPrice("6000000").withPropertyType("HDB").build();
    public static final Property IDA = new PropertyBuilder().withLandlordName("Ida Mueller").withPhone("92823747")
            .withLocation("Shinjuku").withAskingPrice("1200000").withPropertyType("Condominium").build();

    // Manually added - Property's details found in {@code CommandTestUtil}
    public static final Property AMY = new PropertyBuilder().withLandlordName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withAskingPrice(VALID_ASKING_PRICE_AMY).withPropertyType(VALID_PROPERTY_TYPE_AMY)
            .withLocation(VALID_LOCATION_AMY).build();
    public static final Property BOB = new PropertyBuilder().withLandlordName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withAskingPrice(VALID_ASKING_PRICE_BOB).withPropertyType(VALID_PROPERTY_TYPE_BOB)
            .withLocation(VALID_LOCATION_BOB).build();

    private TypicalProperties() {} // prevents instantiation

    public static PropertyList getTypicalPropertyList() {
        PropertyList ab = new PropertyList();
        for (Property property : getTypicalProperties()) {
            ab.addProperty(property);
        }
        return ab;
    }

    public static List<Property> getTypicalProperties() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
