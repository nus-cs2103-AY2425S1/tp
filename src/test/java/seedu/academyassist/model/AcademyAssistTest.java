package seedu.academyassist.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.academyassist.testutil.Assert.assertThrows;
import static seedu.academyassist.testutil.TypicalPersons.ALICE;
import static seedu.academyassist.testutil.TypicalPersons.getTypicalAcademyAssist;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.exceptions.DuplicatePersonException;
import seedu.academyassist.testutil.PersonBuilder;

public class AcademyAssistTest {

    private final AcademyAssist academyAssist = new AcademyAssist();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), academyAssist.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> academyAssist.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAcademyAssist_replacesData() {
        AcademyAssist newData = getTypicalAcademyAssist();
        academyAssist.resetData(newData);
        assertEquals(newData, academyAssist);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AcademyAssistStub newData = new AcademyAssistStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> academyAssist.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> academyAssist.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAcademyAssist_returnsFalse() {
        assertFalse(academyAssist.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAcademyAssist_returnsTrue() {
        academyAssist.addPerson(ALICE);
        assertTrue(academyAssist.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAcademyAssist_returnsTrue() {
        academyAssist.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(academyAssist.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> academyAssist.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AcademyAssist.class.getCanonicalName() + "{persons=" + academyAssist.getPersonList() + "}";
        assertEquals(expected, academyAssist.toString());
    }

    /**
     * A stub ReadOnlyAcademyAssist whose persons list can violate interface constraints.
     */
    private static class AcademyAssistStub implements ReadOnlyAcademyAssist {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        AcademyAssistStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public int getIdGeneratedCount() {
            return 0;
        }
    }

}
