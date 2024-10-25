package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HIGH_RISK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOW_RISK;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTag(VALID_TAG_HIGH_RISK).withAllergy(VALID_ALLERGY_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name, phone number same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        //different people -> returns false
        assertFalse(ALICE.isSamePerson(BOB));

        // name differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        //same name, same phone number, all other attributes different -> returns true
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTag(VALID_TAG_HIGH_RISK).withAllergy(VALID_ALLERGY_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

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
        editedAlice = new PersonBuilder(ALICE).withTag(VALID_TAG_LOW_RISK).build();
        assertFalse(ALICE.equals(editedAlice));

        // different allergies -> returns false
        editedAlice = new PersonBuilder(ALICE).withAllergy(VALID_ALLERGY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }
    @Test
    public void hashCode_samePerson_sameHashCode() {
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());
    }

    @Test
    public void hashCode_differentPersons_differentHashCode() {
        assertFalse(ALICE.hashCode() == BOB.hashCode());
    }

    @Test
    public void constructor_allFieldsPresent_success() {
        Person person = new PersonBuilder().withName("Charlie")
                .withPhone("98765432")
                .withEmail("charlie@example.com")
                .withAddress("123, Charlies Street")
                .withTag("Medium Risk")
                .withAllergy("Lactose").build();

        assertEquals("Charlie", person.getName().toString());
        assertEquals("98765432", person.getPhone().toString());
        assertEquals("charlie@example.com", person.getEmail().toString());
        assertEquals("123, Charlies Street", person.getAddress().toString());
        assertEquals("Medium Risk", person.getTag().toString());
        assertEquals("Lactose", person.getAllergy().toString());
    }

    @Test
    public void getters_allFieldsCorrectlyRetrieved() {
        assertEquals(ALICE.getName(), ALICE.getName());
        assertEquals(ALICE.getPhone(), ALICE.getPhone());
        assertEquals(ALICE.getEmail(), ALICE.getEmail());
        assertEquals(ALICE.getAddress(), ALICE.getAddress());
        assertEquals(ALICE.getTag(), ALICE.getTag());
        assertEquals(ALICE.getAllergy(), ALICE.getAllergy());
    }


    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", tag=" + ALICE.getTag() + ", allergy=" + ALICE.getAllergy()
                + ", date=" + ALICE.getDate() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
