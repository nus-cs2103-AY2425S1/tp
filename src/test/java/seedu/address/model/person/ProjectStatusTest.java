package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_STATUS_COMPLETE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_STATUS_IN_PROGRESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Test class for the ProjectStatus field in Person class.
 */
public class ProjectStatusTest {

    @Test
    public void projectStatus_modifyProjectStatus_success() {
        // default project status
        Person person = new PersonBuilder(ALICE).build();
        assertEquals("in progress", person.getProjectStatus().toString());

        // change project status to "completed"
        Person editedPerson = new PersonBuilder(ALICE).withProjectStatus(VALID_PROJECT_STATUS_COMPLETE).build();
        assertEquals(VALID_PROJECT_STATUS_COMPLETE, editedPerson.getProjectStatus().toString());
    }

    @Test
    public void projectStatus_isSamePerson_differentStatus_returnsTrue() {
        // same name, different project status -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withProjectStatus(VALID_PROJECT_STATUS_IN_PROGRESS).build();
        assertTrue(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void projectStatus_equals() {
        // same values including project status -> returns true
        Person aliceWithStatus = new PersonBuilder(ALICE).withProjectStatus(VALID_PROJECT_STATUS_COMPLETE).build();
        Person copyOfAlice = new PersonBuilder(aliceWithStatus).build();
        assertTrue(aliceWithStatus.equals(copyOfAlice));

        // different project status -> returns false
        Person aliceWithDifferentStatus = new PersonBuilder(ALICE).withProjectStatus(VALID_PROJECT_STATUS_IN_PROGRESS).build();
        assertFalse(aliceWithStatus.equals(aliceWithDifferentStatus));
    }

    @Test
    public void projectStatus_toStringMethod() {
        Person personWithStatus = new PersonBuilder(ALICE).withProjectStatus(VALID_PROJECT_STATUS_COMPLETE).build();
        String expected = Person.class.getCanonicalName() + "{name=" + personWithStatus.getName() + ", phone="
                + personWithStatus.getPhone() + ", email=" + personWithStatus.getEmail() + ", address="
                + personWithStatus.getAddress() + ", tags=" + personWithStatus.getTags() + ", projectStatus="
                + personWithStatus.getProjectStatus() + "}";
        assertEquals(expected, personWithStatus.toString());
    }

    @Test
    public void projectStatus_nullValue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ProjectStatus(null));
    }

    @Test
    public void projectStatus_invalidValue_throwsIllegalArgumentException() {
        String invalidStatus = "";
        assertThrows(IllegalArgumentException.class, () -> new ProjectStatus(invalidStatus));
    }
}
