package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOwners.ALICE;
import static seedu.address.testutil.TypicalOwners.getTypicalPawPatrol;
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
import seedu.address.model.owner.exceptions.DuplicateOwnerException;
import seedu.address.model.pet.Pet;
import seedu.address.testutil.OwnerBuilder;
import seedu.address.testutil.TypicalOwners;

public class PawPatrolTest {

    private final PawPatrol pawPatrol = new PawPatrol();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), pawPatrol.getOwnerList());
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
        Owner editedAlice = new OwnerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        List<Owner> newPersons = Arrays.asList(ALICE, editedAlice);
        PawPatrolStub newData = new PawPatrolStub(newPersons);

        assertThrows(DuplicateOwnerException.class, () -> pawPatrol.resetData(newData));
    }

    @Test
    public void hasOwner_nullOwner_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> pawPatrol.hasOwner(null));
    }

    @Test
    public void hasOwner_ownerNotInPawPatrol_returnsFalse() {
        assertFalse(pawPatrol.hasOwner(ALICE));
    }

    @Test
    public void hasOwner_ownerInPawPatrol_returnsTrue() {
        pawPatrol.addOwner(ALICE);
        assertTrue(pawPatrol.hasOwner(ALICE));
    }

    @Test
    public void hasPerson_ownerWithSameIdentityFieldsInPawPatrol_returnsTrue() {
        pawPatrol.addOwner(ALICE);
        Owner editedAlice = new OwnerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(pawPatrol.hasOwner(editedAlice));
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
    public void getOwnerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> pawPatrol.getOwnerList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = PawPatrol.class.getCanonicalName() + "{owners=" + pawPatrol.getOwnerList()
            + ", pets=" + pawPatrol.getPetList() + ", links=" + pawPatrol.getLinkList() + "}";
        assertEquals(expected, pawPatrol.toString());
    }

    /**
     * A stub ReadOnlyPawPatrol whose persons list can violate interface
     * constraints.
     */
    private static class PawPatrolStub implements ReadOnlyPawPatrol {
        private final ObservableList<Owner> owners = FXCollections.observableArrayList();
        private final ObservableList<Pet> pets = FXCollections.observableArrayList();
        private final ObservableList<Link> links = FXCollections.observableArrayList();

        PawPatrolStub(Collection<Owner> persons) {
            this.owners.setAll(persons);
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
