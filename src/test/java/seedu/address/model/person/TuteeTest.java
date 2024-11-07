package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_DEACON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_DEACON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOURS_DEACON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEACON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_DEACON;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.CLARA;
import static seedu.address.testutil.TypicalPersons.DEACON;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TuteeBuilder;

public class TuteeTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Tutee tutee = new TuteeBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> tutee.getSubjects().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(CLARA.isSamePerson(CLARA));

        // null -> returns false
        assertFalse(CLARA.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Tutee editedClara = new TuteeBuilder(CLARA).withPhone(VALID_PHONE_DEACON).withEmail(VALID_EMAIL_DEACON)
                .withAddress(VALID_ADDRESS_DEACON).build();
        assertFalse(CLARA.isSamePerson(editedClara));


        // different name, all other attributes same -> returns false
        editedClara = new TuteeBuilder(CLARA).withName(VALID_NAME_DEACON).build();
        assertFalse(CLARA.isSamePerson(editedClara));

        // name differs in case, all other attributes same -> returns false
        Tutee editedBob = new TuteeBuilder(DEACON).withName(VALID_NAME_DEACON.toLowerCase()).build();
        assertTrue(DEACON.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_DEACON + " ";
        editedBob = new TuteeBuilder(DEACON).withName(nameWithTrailingSpaces).build();
        assertFalse(DEACON.isSamePerson(editedBob));
    }


    @Test
    public void equals() {
        // same values -> returns true
        Tutee claraCopy = new TuteeBuilder(CLARA).build();
        assertTrue(CLARA.equals(claraCopy));

        // same object -> returns true
        assertTrue(CLARA.equals(CLARA));

        // null -> returns false
        assertFalse(CLARA.equals(null));

        // different type -> returns false
        assertFalse(CLARA.equals(5));

        // different person -> returns false
        assertFalse(CLARA.equals(DEACON));

        // different name -> returns false
        Tutee editedClara = new TuteeBuilder(CLARA).withName(VALID_NAME_DEACON).build();
        assertFalse(CLARA.equals(editedClara));

        // different phone -> returns false
        editedClara = new TuteeBuilder(CLARA).withPhone(VALID_PHONE_DEACON).build();
        assertFalse(CLARA.equals(editedClara));

        // different email -> returns false
        editedClara = new TuteeBuilder(CLARA).withEmail(VALID_EMAIL_DEACON).build();
        assertFalse(CLARA.equals(editedClara));

        // different address -> returns false
        editedClara = new TuteeBuilder(CLARA).withAddress(VALID_ADDRESS_DEACON).build();
        assertFalse(CLARA.equals(editedClara));

        // different hours -> returns false
        editedClara = new TuteeBuilder(CLARA).withHours(VALID_HOURS_DEACON).build();
        assertFalse(CLARA.equals(editedClara));
    }

    @Test
    public void toStringMethod() {
        String expected = Tutee.class.getCanonicalName()
                + "{name=" + CLARA.getName() + ", phone=" + CLARA.getPhone()
                + ", email=" + CLARA.getEmail() + ", address=" + CLARA.getAddress() + ", hours=" + CLARA.getHours()
                + ", subjects=" + CLARA.getSubjects() + "}";
        assertEquals(expected, CLARA.toString());
    }
}
