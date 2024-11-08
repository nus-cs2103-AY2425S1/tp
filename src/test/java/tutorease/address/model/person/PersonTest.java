package tutorease.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.testutil.TypicalStudents.ALICE;
import static tutorease.address.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;

public class PersonTest {
    @Test
    public void isSamePerson_sameName_returnsTrue() {
        assertTrue(ALICE.isSamePerson(ALICE));
    }

    @Test
    public void isSamePerson_differentName_returnsFalse() {
        assertFalse(ALICE.isSamePerson(BOB));
    }

    @Test
    public void hasSameEmail_sameEmail_returnsTrue() {
        assertTrue(ALICE.hasSameEmail(ALICE));
    }

    @Test
    public void hasSameEmail_differentEmail_returnsFalse() {
        assertFalse(ALICE.hasSameEmail(BOB));
    }

    @Test
    public void hasSamePhone_samePhone_returnsTrue() {
        assertTrue(ALICE.hasSamePhone(ALICE));
    }

    @Test
    public void hasSamePhone_differentPhone_returnsFalse() {
        assertFalse(ALICE.hasSamePhone(BOB));
    }

    @Test
    public void equals_identicalPersons_returnsTrue() {
        assertEquals(ALICE, ALICE);
    }

    @Test
    public void hashCode_identicalPersons_sameHashCode() {
        assertEquals(ALICE.hashCode(), ALICE.hashCode());
    }
}

