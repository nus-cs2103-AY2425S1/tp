package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASKING_PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROPERTY_TYPE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperties.ALICE;
import static seedu.address.testutil.TypicalProperties.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PropertyBuilder;

public class PropertyTest {

    @Test
    public void isSameProperty() {
        // same object -> returns true
        assertTrue(ALICE.isSameProperty(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameProperty(null));

        // same name, all other attributes different -> returns true
        Property editedAlice = new PropertyBuilder().withLandlordName(ALICE).withPhone(VALID_PHONE_BOB)
                        .withAskingPrice(VALID_ASKING_PRICE_BOB).withLocation(VALID_LOCATION_BOB)
                .withPropertyType(VALID_PROPERTY_TYPE_BOB).build();
        assertTrue(ALICE.isSameProperty(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PropertyBuilder(ALICE).withLandlordName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameProperty(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Property editedBob = new PropertyBuilder(BOB).withLandlordName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameProperty(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PropertyBuilder(BOB).withLandlordName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameProperty(editedBob));
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

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Property editedAlice = new PropertyBuilder(ALICE).withLandlordName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PropertyBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different location -> returns false
        editedAlice = new PropertyBuilder(ALICE).withLocation(VALID_LOCATION_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different property type -> returns false
        editedAlice = new PropertyBuilder(ALICE).withPropertyType(VALID_PROPERTY_TYPE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different asking price -> returns false
        editedAlice = new PropertyBuilder(ALICE).withAskingPrice(VALID_ASKING_PRICE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Property.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", location=" + ALICE.getLocation() + ", askingPrice=" + ALICE.getAskingPrice()
                + ", propertyType=" + ALICE.getPropertyType() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
