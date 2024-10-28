package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASKING_PRICE_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LANDLORD_NAME_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROPERTY_TYPE_BRENDA;
import static seedu.address.testutil.property.TypicalProperties.ALICE;
import static seedu.address.testutil.property.TypicalProperties.BRENDA;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.property.PropertyBuilder;

public class PropertyTest {

    @Test
    public void isSameProperty() {

        // same object -> returns true
        assertTrue(ALICE.isSameProperty(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameProperty(null));

        // same address, all other attributes different -> returns true
        Property editedAlice = new PropertyBuilder(ALICE).withLandlordName(VALID_LANDLORD_NAME_ALAN)
                .withPhone(VALID_PHONE_ALAN).withAskingPrice(VALID_ASKING_PRICE_ALAN)
                .withPropertyType(VALID_PROPERTY_TYPE_BRENDA).build();
        assertTrue(ALICE.isSameProperty(editedAlice));

        // different address, all other attributes same -> return false
        editedAlice = new PropertyBuilder(ALICE).withAddress(VALID_ADDRESS_ALAN).build();
        assertFalse(ALICE.isSameProperty(editedAlice));

        // address differs in case, all other attributes same -> returns false
        Property editedBrenda = new PropertyBuilder(BRENDA).withAddress(VALID_ADDRESS_BRENDA.toLowerCase()).build();
        assertFalse(BRENDA.isSameProperty(editedBrenda));

        // address has trailing spaces, all other attributes same -> returns false
        String addressWithTrailingSpaces = VALID_ADDRESS_BRENDA + " ";
        editedBrenda = new PropertyBuilder(BRENDA).withAddress(addressWithTrailingSpaces).build();
        assertFalse(BRENDA.isSameProperty(editedBrenda));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Property aliceCopy = new PropertyBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different property -> returns false
        assertFalse(ALICE.equals(BRENDA));

        // different name -> returns false
        Property editedAlice = new PropertyBuilder(ALICE).withLandlordName(VALID_LANDLORD_NAME_ALAN).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PropertyBuilder(ALICE).withPhone(VALID_PHONE_ALAN).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PropertyBuilder(ALICE).withAddress(VALID_ADDRESS_ALAN).build();
        assertFalse(ALICE.equals(editedAlice));

        // different asking price
        editedAlice = new PropertyBuilder(ALICE).withAskingPrice(VALID_ASKING_PRICE_ALAN).build();
        assertFalse(ALICE.equals(editedAlice));

        // different property type
        editedAlice = new PropertyBuilder(ALICE).withPropertyType(VALID_PROPERTY_TYPE_BRENDA).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Property.class.getCanonicalName() + "{name=" + ALICE.getLandlordName()
                + ", phone=" + ALICE.getPhone() + ", address=" + ALICE.getAddress()
                + ", askingPrice=" + ALICE.getAskingPrice() + ", propertyType=" + ALICE.getPropertyType() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
