package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalCampusConnect;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class CampusConnectTest {

    private final CampusConnect campusConnect = new CampusConnect();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), campusConnect.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> campusConnect.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyCampusConnect_replacesData() {
        CampusConnect newData = getTypicalCampusConnect();
        campusConnect.resetData(newData);
        assertEquals(newData, campusConnect);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        CampusConnectStub newData = new CampusConnectStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> campusConnect.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> campusConnect.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInCampusConnect_returnsFalse() {
        assertFalse(campusConnect.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInCampusConnect_returnsTrue() {
        campusConnect.addPerson(ALICE);
        assertTrue(campusConnect.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInCampusConnect_returnsTrue() {
        campusConnect.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(campusConnect.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> campusConnect.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = CampusConnect.class.getCanonicalName() + "{persons=" + campusConnect.getPersonList() + "}";
        assertEquals(expected, campusConnect.toString());
    }

    @Test
    public void insertPersonTest() {
        int ind = campusConnect.getPersonList().size();
        CampusConnect expected = new CampusConnect();
        expected.resetData(campusConnect);

        campusConnect.addPerson(ALICE, ind);
        expected.addPerson(ALICE);
        assertEquals(expected.toString(), campusConnect.toString());
    }


    /**
     * A stub ReadOnlyCampusConnect whose persons list can violate interface constraints.
     */
    private static class CampusConnectStub implements ReadOnlyCampusConnect {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        CampusConnectStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }
}
