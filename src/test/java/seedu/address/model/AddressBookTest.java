package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOwners.AARON;
import static seedu.address.testutil.TypicalOwners.BENSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPets.*;

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

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
            .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
            .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void sortOwners_ownersGetArrangedAlphabetically() {
        addressBook.addOwner(BENSON);
        addressBook.addOwner(AARON);
        assertTrue(addressBook.getOwnerList().get(0) == BENSON);
        addressBook.sortOwners();
        assertTrue(addressBook.getOwnerList().get(0) == AARON);
    }

    @Test
    public void sortPets_petsGetArrangedAlphabetically() {
        addressBook.addPet(BISON);
        addressBook.addPet(AARFUL);
        addressBook.sortPets();
        assertTrue(addressBook.getPetList().get(0) == AARFUL);
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
    public void hasLink_linkNotInAddressBook_returnsFalse() {
        Owner owner = TypicalOwners.ALICE;
        Pet pet = BELLA;
        Link link = new Link(owner, pet);

        assertFalse(addressBook.hasLink(link));
    }

    @Test
    public void hasLink_linkInAddressBook_returnsTrue() {
        Owner owner = TypicalOwners.ALICE;
        Pet pet = BELLA;
        Link link = new Link(owner, pet);

        addressBook.addLink(link);
        assertTrue(addressBook.hasLink(link));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList()
            + ", owners=" + addressBook.getOwnerList() + ", pets=" + addressBook.getPetList()
            + ", links=" + addressBook.getLinkList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface
     * constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Owner> owners = FXCollections.observableArrayList();
        private final ObservableList<Pet> pets = FXCollections.observableArrayList();
        private final ObservableList<Link> links = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
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
