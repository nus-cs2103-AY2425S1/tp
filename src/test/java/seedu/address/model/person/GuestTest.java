package seedu.address.model.person;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RELATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RSVP_PENDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalGuests.AMY;
import static seedu.address.testutil.TypicalGuests.BOB;

import java.util.Collections;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.GuestBuilder;

public class GuestTest {

    @Test
    void constructor_withAllFieldsSpecified() {
        Guest guest = new Guest(new Name(VALID_NAME_BOB), new Phone(VALID_PHONE_BOB), new Email(VALID_EMAIL_BOB),
                new Address(VALID_ADDRESS_BOB), new HashSet<>(Collections.singletonList(new Tag(VALID_TAG_HUSBAND))),
                new Rsvp(VALID_RSVP_PENDING), new Relation(VALID_RELATION_BOB));

        assertEquals(new Name(VALID_NAME_BOB), guest.getName());
        assertEquals(new Phone(VALID_PHONE_BOB), guest.getPhone());
        assertEquals(new Email(VALID_EMAIL_BOB), guest.getEmail());
        assertEquals(new Address(VALID_ADDRESS_BOB), guest.getAddress());
        assertEquals(new HashSet<>(Collections.singletonList(new Tag(VALID_TAG_HUSBAND))), guest.getTags());
        assertEquals(new Rsvp(VALID_RSVP_PENDING), guest.getRsvp());
        assertEquals(new Relation(VALID_RELATION_BOB), guest.getRelation());
    }

    @Test
    void constructor_withoutRelation_defaultsToUnknownRelation() {
        Guest guest = new Guest(new Name(VALID_NAME_BOB), new Phone(VALID_PHONE_BOB), new Email(VALID_EMAIL_BOB),
                new Address(VALID_ADDRESS_BOB), new HashSet<>(Collections.singletonList(new Tag(VALID_TAG_HUSBAND))),
                new Rsvp(VALID_RSVP_PENDING));

        assertEquals(new Name(VALID_NAME_BOB), guest.getName());
        assertEquals(new Phone(VALID_PHONE_BOB), guest.getPhone());
        assertEquals(new Email(VALID_EMAIL_BOB), guest.getEmail());
        assertEquals(new Address(VALID_ADDRESS_BOB), guest.getAddress());
        assertEquals(new HashSet<>(Collections.singletonList(new Tag(VALID_TAG_HUSBAND))), guest.getTags());
        assertEquals(new Rsvp(VALID_RSVP_PENDING), guest.getRsvp());
        assertEquals(new Relation("U"), guest.getRelation()); // Check default relation
    }

    @Test
    void constructor_withoutRsvp_defaultsToPendingRsvp() {
        Guest guest = new Guest(new Name(VALID_NAME_BOB), new Phone(VALID_PHONE_BOB), new Email(VALID_EMAIL_BOB),
                new Address(VALID_ADDRESS_BOB), new HashSet<>(Collections.singletonList(new Tag(VALID_TAG_HUSBAND))),
                new Relation(VALID_RELATION_BOB));
        assertEquals(new Name(VALID_NAME_BOB), guest.getName());
        assertEquals(new Phone(VALID_PHONE_BOB), guest.getPhone());
        assertEquals(new Email(VALID_EMAIL_BOB), guest.getEmail());
        assertEquals(new Address(VALID_ADDRESS_BOB), guest.getAddress());
        assertEquals(new HashSet<>(Collections.singletonList(new Tag(VALID_TAG_HUSBAND))), guest.getTags());
        assertEquals(new Rsvp("PENDING"), guest.getRsvp()); // Check default RSVP
        assertEquals(new Relation(VALID_RELATION_BOB), guest.getRelation());
    }

    @Test
    void constructor_withoutRsvpAndRelation_defaultsToPendingAndUnknown() {
        Guest guest = new Guest(new Name(VALID_NAME_BOB), new Phone(VALID_PHONE_BOB), new Email(VALID_EMAIL_BOB),
                new Address(VALID_ADDRESS_BOB), new HashSet<>(Collections.singletonList(new Tag(VALID_TAG_HUSBAND))));

        assertEquals(new Name(VALID_NAME_BOB), guest.getName());
        assertEquals(new Phone(VALID_PHONE_BOB), guest.getPhone());
        assertEquals(new Email(VALID_EMAIL_BOB), guest.getEmail());
        assertEquals(new Address(VALID_ADDRESS_BOB), guest.getAddress());
        assertEquals(new HashSet<>(Collections.singletonList(new Tag(VALID_TAG_HUSBAND))), guest.getTags());
        assertEquals(new Rsvp("PENDING"), guest.getRsvp()); // Check default RSVP
        assertEquals(new Relation("U"), guest.getRelation()); // Check default relation
    }

    @Test
    public void isSameGuest() {
        // same object -> returns true
        assertTrue(AMY.isSamePerson(AMY));

        // null -> returns false
        assertFalse(AMY.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Guest editedAmy = new GuestBuilder(AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(AMY.isSamePerson(editedAmy));

        // different name, all other attributes same -> returns false
        editedAmy = new GuestBuilder(AMY).withName(VALID_NAME_BOB).build();
        assertFalse(AMY.isSamePerson(editedAmy));

        // name differs in case, all other attributes same -> returns true
        Guest editedBob = new GuestBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new GuestBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Guest amyCopy = new GuestBuilder(AMY).build();
        assertTrue(AMY.equals(amyCopy));

        // same object -> returns true
        assertTrue(AMY.equals(AMY));

        // null -> returns false
        assertFalse(AMY.equals(null));

        // different type -> returns false
        assertFalse(AMY.equals(5));

        // different guest -> returns false
        assertFalse(AMY.equals(BOB));

        // different name -> returns false
        Guest editedAmy = new GuestBuilder(AMY).withName(VALID_NAME_BOB).build();
        assertFalse(AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new GuestBuilder(AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new GuestBuilder(AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new GuestBuilder(AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(AMY.equals(editedAmy));

        // different rsvp -> returns false
        editedAmy = new GuestBuilder(AMY).withRsvp(VALID_RSVP_PENDING).build();
        assertFalse(AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new GuestBuilder(AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        String expected = Guest.class.getCanonicalName() + "{name=" + AMY.getName() + ", phone=" + AMY.getPhone()
                + ", email=" + AMY.getEmail() + ", address=" + AMY.getAddress() + ", tags=" + AMY.getTags() + ", RSVP="
                + AMY.getRsvp() + ", Relation=" + AMY.getRelation() + "}";
        assertEquals(expected, AMY.toString());
    }

    @Test
    public void hashCodeMethod() {

        // Same Guests
        Guest guest = new Guest(AMY.getName(), AMY.getPhone(), AMY.getEmail(), AMY.getAddress(),
                AMY.getTags(), AMY.getRsvp(), AMY.getRelation());
        assertEquals(guest.hashCode(), AMY.hashCode());

        // Different Guests
        assertNotEquals(AMY.hashCode(), BOB.hashCode());

        // Same default fields
        Guest guest1 = new Guest(AMY.getName(), AMY.getPhone(), AMY.getEmail(), AMY.getAddress(), AMY.getTags());
        Guest guest2 = new Guest(AMY.getName(), AMY.getPhone(), AMY.getEmail(), AMY.getAddress(), AMY.getTags());
        assertEquals(guest1.hashCode(), guest2.hashCode());

        // Different optional fields
        Guest guest3 = new Guest(AMY.getName(), AMY.getPhone(), AMY.getEmail(), AMY.getAddress(), AMY.getTags(),
                BOB.getRsvp(), BOB.getRelation());
        assertNotEquals(AMY.hashCode(), guest3.hashCode());
    }

}
