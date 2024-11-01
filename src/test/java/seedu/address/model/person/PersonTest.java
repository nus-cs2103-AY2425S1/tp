package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
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
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
    @Test
    public void hashCodeMethod() {
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());
    }
    @Test
    public void getPhoneMethod() {
        assertEquals(ALICE.getPhone(), ALICE.getPhone());
    }
    @Test
    public void getEmailMethod() {
        assertEquals(ALICE.getEmail(), ALICE.getEmail());
    }
    @Test
    public void getAddressMethod() {
        assertEquals(ALICE.getAddress(), ALICE.getAddress());
    }
    @Test
    public void getTagsMethod() {
        assertEquals(ALICE.getTags(), ALICE.getTags());
    }
    @Test
    public void getListOfSellingPropertiesMethod() {
        assertEquals(ALICE.getListOfSellingProperties(), ALICE.getListOfSellingProperties());
    }
    @Test
    public void getListOfBuyingPropertiesMethod() {
        assertEquals(ALICE.getListOfBuyingProperties(), ALICE.getListOfBuyingProperties());
    }
    @Test
    public void containsBuyPropertyMethod() {
        Apartment property = new Apartment(new PostalCode("123456"), new UnitNumber("10-65"),
                new Price("1500000"), ALICE.getTags());
        assertFalse(ALICE.containsBuyProperty(property));
    }
    @Test
    public void isSamePersonMethod() {
        assertTrue(ALICE.isSamePerson(ALICE));
    }
    @Test
    public void equalsMethod() {
        assertTrue(ALICE.equals(ALICE));
    }
    @Test
    public void toStringMethod2() {
        assertEquals(ALICE.toString(), ALICE.toString());
    }
    @Test
    public void hashCodeMethod2() {
        assertEquals(ALICE.hashCode(), ALICE.hashCode());
    }
    @Test
    public void containSellPropertyTestWithProperties() {
        assertFalse(ALICE.containsSellProperty(new Apartment(new PostalCode("123456"), new UnitNumber("10-65"),
                new Price("1500000"), ALICE.getTags()), new ArrayList<>()));
    }
    @Test
    public void containSellPropertyTestWithoutProperties() {
        List<Person> properties = new ArrayList<>();
        properties.add(ALICE);
        assertFalse(ALICE.containsSellProperty(new Apartment(new PostalCode("123456"), new UnitNumber("10-65"),
                new Price("1500000"), ALICE.getTags()),
                properties));
    }
}
