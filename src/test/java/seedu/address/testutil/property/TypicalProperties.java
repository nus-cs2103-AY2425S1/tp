package seedu.address.testutil.property;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASKING_PRICE_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASKING_PRICE_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LANDLORD_NAME_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LANDLORD_NAME_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROPERTY_TYPE_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROPERTY_TYPE_BRENDA;

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
            .withAddress("Jurong West Blk 100 #05-01").withPhone("94351253").withAskingPrice("450000")
            .withPropertyType("HDB").build();
    public static final Property BENSON = new PropertyBuilder().withLandlordName("Benson Meier")
            .withAddress("Clementi Avenue Blk 102 #02-02").withPhone("98765432").withAskingPrice("1500000")
            .withPropertyType("Condominium").build();
    public static final Property CARL = new PropertyBuilder().withLandlordName("Carl Kurz").withPhone("95352563")
            .withAddress("Aljunied Crescent Blk 102 #02-02").withAskingPrice("1500000").withPropertyType("HDB").build();
    public static final Property DANIEL = new PropertyBuilder().withLandlordName("Daniel Meier").withPhone("87652533")
            .withAddress("Bukit Timah Rd #01-01").withAskingPrice("3500000").withPropertyType("Terrace").build();
    public static final Property ELLE = new PropertyBuilder().withLandlordName("Elle Meyer").withPhone("92822224")
            .withAddress("Marsiling Rd Blk 105 #03-02").withAskingPrice("6000000").withPropertyType("HDB").build();
    public static final Property FIONA = new PropertyBuilder().withLandlordName("Fiona Kunz").withPhone("94822272")
            .withAddress("Shibuya Rd #01-01").withAskingPrice("6000000").withPropertyType("Apartment").build();
    public static final Property GEORGE = new PropertyBuilder().withLandlordName("George Best").withPhone("98272923")
            .withAddress("Tampines Blk 10 #02-02").withAskingPrice("4500000").withPropertyType("HDB").build();

    // Manually added
    public static final Property HOON = new PropertyBuilder().withLandlordName("Hoon Meier").withPhone("84842727")
            .withAddress("Little India Blk 102 #02-02").withAskingPrice("6000000").withPropertyType("HDB").build();
    public static final Property IDA = new PropertyBuilder().withLandlordName("Ida Mueller").withPhone("92823747")
            .withAddress("Shinjuku Blk 102 #02-02").withAskingPrice("1200000").withPropertyType("Condominium").build();

    // Manually added - Property's details found in {@code CommandTestUtil}
    public static final Property AMY = new PropertyBuilder().withLandlordName(VALID_LANDLORD_NAME_ALAN)
            .withPhone(VALID_PHONE_ALAN)
            .withAskingPrice(VALID_ASKING_PRICE_ALAN).withPropertyType(VALID_PROPERTY_TYPE_ALAN)
            .withAddress(VALID_ADDRESS_ALAN).build();
    public static final Property BOB = new PropertyBuilder().withLandlordName(VALID_LANDLORD_NAME_BRENDA)
            .withPhone(VALID_PHONE_BRENDA)
            .withAskingPrice(VALID_ASKING_PRICE_BRENDA).withPropertyType(VALID_PROPERTY_TYPE_BRENDA)
            .withAddress(VALID_ADDRESS_BRENDA).build();

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
