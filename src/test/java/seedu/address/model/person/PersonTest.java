package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_BTC_SUB_STRING;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_SOL_MAIN_STRING;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.addresses.PublicAddressFactory;
import seedu.address.testutil.PersonBuilder;

public class PersonTest {


    private Person person;
    private PublicAddress btcAddress1;
    private PublicAddress btcAddress2;

    @BeforeEach
    public void setUp() {
        btcAddress1 = PublicAddressFactory.createPublicAddress(
            Network.BTC, VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING, "btcLabel1");
        btcAddress2 = PublicAddressFactory.createPublicAddress(
            Network.BTC, VALID_PUBLIC_ADDRESS_BTC_SUB_STRING, "btcLabel2");

        person = new PersonBuilder(IDA).withPublicAddresses(
            btcAddress1, btcAddress2).build();
    }

    @Test
    public void withUpdatedPublicAddress_existingNetwork_updatesAddress() {
        PublicAddress newBtcAddress =
            PublicAddressFactory.createPublicAddress(
                Network.BTC, VALID_PUBLIC_ADDRESS_BTC_SUB_STRING, "btcLabel1");
        Person updatedPerson = person.withUpdatedPublicAddress(newBtcAddress);

        assertEquals(2, updatedPerson.getPublicAddressesComposition()
            .getPublicAddresses().get(Network.BTC).size());
        assertTrue(updatedPerson.getPublicAddressesComposition()
            .getPublicAddresses().get(Network.BTC).contains(newBtcAddress));
        assertFalse(updatedPerson.getPublicAddressesComposition()
            .getPublicAddresses().get(Network.BTC).contains(btcAddress1));
        assertTrue(updatedPerson.getPublicAddressesComposition()
            .getPublicAddresses().get(Network.BTC).contains(btcAddress2));
    }

    @Test
    public void withUpdatedPublicAddress_existingNetworkCapitaliseLabel_returnsDifferentPerson() {
        PublicAddress newBtcAddress =
            PublicAddressFactory.createPublicAddress(
                Network.BTC, VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING, "BTCLABEL1");
        Person updatedPerson = person.withUpdatedPublicAddress(newBtcAddress);

        assertNotEquals(person, updatedPerson);
    }

    @Test
    public void withUpdatedPublicAddress_existingNetworkNewLabel_returnsDifferentPerson() {
        PublicAddress newBtcAddress =
            PublicAddressFactory.createPublicAddress(
                Network.BTC, VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING, "newBtcLabel");
        Person updatedPerson = person.withAddedPublicAddress(newBtcAddress);

        assertNotEquals(person, updatedPerson);
    }

    @Test
    public void withUpdatedPublicAddress_newNetwork_returnsDifferentPerson() {
        PublicAddress newAddress = PublicAddressFactory.createPublicAddress(
            Network.SOL, VALID_PUBLIC_ADDRESS_SOL_MAIN_STRING, "solLabel");
        Person updatedPerson = person.withAddedPublicAddress(newAddress);

        assertNotEquals(person, updatedPerson);
    }

    @Test
    public void withUpdatedPublicAddress_unchangedNetwork_returnsEqualPerson() {
        Person updatedPerson = person.withUpdatedPublicAddress(btcAddress1);

        assertEquals(person, updatedPerson);
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () ->
            person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
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
        assertEquals(ALICE, aliceCopy);

        // same object -> returns true
        assertEquals(ALICE, ALICE);

        // null -> returns false
        assertNotEquals(null, ALICE);

        // different type -> returns false
        assertNotEquals(5, ALICE);

        // different person -> returns false
        assertNotEquals(ALICE, BOB);

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertNotEquals(ALICE, editedAlice);

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertNotEquals(ALICE, editedAlice);

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertNotEquals(ALICE, editedAlice);

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertNotEquals(ALICE, editedAlice);

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertNotEquals(ALICE, editedAlice);
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
            + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", publicAddresses="
            + ALICE.getPublicAddressesComposition() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
