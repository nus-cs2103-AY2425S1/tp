package seedu.hiredfiredpro.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.hiredfiredpro.model.util.SampleDataUtil.getSampleHiredFiredPro;
import static seedu.hiredfiredpro.testutil.Assert.assertThrows;
import static seedu.hiredfiredpro.testutil.TypicalPersons.ALICE;
import static seedu.hiredfiredpro.testutil.TypicalPersons.getTypicalHiredFiredPro;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.hiredfiredpro.model.person.Person;
import seedu.hiredfiredpro.model.person.exceptions.DuplicatePersonException;
import seedu.hiredfiredpro.testutil.PersonBuilder;

public class HiredFiredProTest {

    private final HiredFiredPro hiredFiredPro = new HiredFiredPro();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), hiredFiredPro.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> hiredFiredPro.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyHiredFiredPro_replacesData() {
        HiredFiredPro newData = getTypicalHiredFiredPro();
        hiredFiredPro.resetData(newData);
        assertEquals(newData, hiredFiredPro);

        ReadOnlyHiredFiredPro sampleData = getSampleHiredFiredPro();
        hiredFiredPro.resetData(sampleData);
        assertEquals(sampleData, hiredFiredPro);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        HiredFiredProStub newData = new HiredFiredProStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> hiredFiredPro.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> hiredFiredPro.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInHiredFiredPro_returnsFalse() {
        assertFalse(hiredFiredPro.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInHiredFiredPro_returnsTrue() {
        hiredFiredPro.addPerson(ALICE);
        assertTrue(hiredFiredPro.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInHiredFiredPro_returnsTrue() {
        hiredFiredPro.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(hiredFiredPro.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> hiredFiredPro.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = HiredFiredPro.class.getCanonicalName() + "{persons=" + hiredFiredPro.getPersonList() + "}";
        assertEquals(expected, hiredFiredPro.toString());
    }

    /**
     * A stub ReadOnlyHiredFiredPro whose persons list can violate interface constraints.
     */
    private static class HiredFiredProStub implements ReadOnlyHiredFiredPro {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        HiredFiredProStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
