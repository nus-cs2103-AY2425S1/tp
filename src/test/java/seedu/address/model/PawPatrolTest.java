package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalPawPatrol;
import static seedu.address.testutil.TypicalPets.BELLA;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.link.Link;
import seedu.address.model.owner.Owner;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.pet.Pet;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalOwners;

public class PawPatrolTest {

    private final PawPatrol pawPatrol = new PawPatrol();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), pawPatrol.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> pawPatrol.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyPawPatrol_replacesData() {
        PawPatrol newData = getTypicalPawPatrol();
        pawPatrol.resetData(newData);
        assertEquals(newData, pawPatrol);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
            .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        PawPatrolStub newData = new PawPatrolStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> pawPatrol.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> pawPatrol.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInPawPatrol_returnsFalse() {
        assertFalse(pawPatrol.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInPawPatrol_returnsTrue() {
        pawPatrol.addPerson(ALICE);
        assertTrue(pawPatrol.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInPawPatrol_returnsTrue() {
        pawPatrol.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
            .build();
        assertTrue(pawPatrol.hasPerson(editedAlice));
    }

    @Test
    public void hasLink_nullLink_throwsNullPointerException() {
        Owner owner = TypicalOwners.ALICE;
        Pet pet = BELLA;

        assertThrows(NullPointerException.class, () -> new Link(owner, null));
        assertThrows(NullPointerException.class, () -> new Link(null, pet));
        assertThrows(NullPointerException.class, () -> new Link(null, null));
    }

    @Test
    public void hasLink_linkNotInPawPatrol_returnsFalse() {
        Owner owner = TypicalOwners.ALICE;
        Pet pet = BELLA;
        Link link = new Link(owner, pet);

        assertFalse(pawPatrol.hasLink(link));
    }

    @Test
    public void hasLink_linkInPawPatrol_returnsTrue() {
        Owner owner = TypicalOwners.ALICE;
        Pet pet = BELLA;
        Link link = new Link(owner, pet);

        pawPatrol.addLink(link);
        assertTrue(pawPatrol.hasLink(link));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> pawPatrol.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = PawPatrol.class.getCanonicalName() + "{persons=" + pawPatrol.getPersonList()
            + ", owners=" + pawPatrol.getOwnerList() + ", pets=" + pawPatrol.getPetList()
            + ", links=" + pawPatrol.getLinkList() + "}";
        assertEquals(expected, pawPatrol.toString());
    }

    /**
     * A stub ReadOnlyPawPatrol whose persons list can violate interface
     * constraints.
     */
    private static class PawPatrolStub implements ReadOnlyPawPatrol {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Owner> owners = FXCollections.observableArrayList();
        private final ObservableList<Pet> pets = FXCollections.observableArrayList();
        private final ObservableList<Link> links = FXCollections.observableArrayList();

        PawPatrolStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Owner> getOwnerList() {
            return owners;
        }

        @Override
        public ObservableList<Pet> getPetList() {
            return pets;
        }

        @Override
        public ObservableList<Link> getLinkList() {
            return links;
        }
    }
}
