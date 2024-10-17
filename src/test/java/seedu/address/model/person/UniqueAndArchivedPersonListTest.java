package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.PersonNotFoundException;

public class UniqueAndArchivedPersonListTest {

    private UniquePersonList uniquePersonList;
    private ArchivedPersonList archivedPersonList;

    @BeforeEach
    public void setUp() {
        uniquePersonList = new UniquePersonList();
        archivedPersonList = new ArchivedPersonList();
    }

    @Test
    public void archivePerson_validPerson_archivesSuccessfully() {
        uniquePersonList.add(ALICE);  // Add ALICE to the UniquePersonList
        uniquePersonList.archivePerson(ALICE, archivedPersonList);  // Archive ALICE

        // Ensure ALICE is removed from UniquePersonList and added to ArchivedPersonList
        assertFalse(uniquePersonList.contains(ALICE));
        assertTrue(archivedPersonList.contains(ALICE));
    }

    @Test
    public void archivePerson_personNotInUniqueList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.archivePerson(ALICE, archivedPersonList));
    }

    @Test
    public void unarchivePerson_validPerson_unarchivesSuccessfully() {
        archivedPersonList.addArchivedPerson(BOB);  // Add BOB to the ArchivedPersonList
        uniquePersonList.unarchivePerson(BOB, archivedPersonList);  // Unarchive BOB

        // Ensure BOB is removed from ArchivedPersonList and added to UniquePersonList
        assertFalse(archivedPersonList.contains(BOB));
        assertTrue(uniquePersonList.contains(BOB));
    }

    @Test
    public void unarchivePerson_personNotInArchivedList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.unarchivePerson(ALICE, archivedPersonList));
    }

    @Test
    public void archiveAndUnarchivePerson_personSuccessfullyMovesBetweenLists() {
        uniquePersonList.add(ALICE);  // Add ALICE to the UniquePersonList
        uniquePersonList.archivePerson(ALICE, archivedPersonList);  // Archive ALICE

        // ALICE should now be in ArchivedPersonList, not UniquePersonList
        assertFalse(uniquePersonList.contains(ALICE));
        assertTrue(archivedPersonList.contains(ALICE));

        // Unarchive ALICE
        uniquePersonList.unarchivePerson(ALICE, archivedPersonList);

        // ALICE should now be back in UniquePersonList, not in ArchivedPersonList
        assertTrue(uniquePersonList.contains(ALICE));
        assertFalse(archivedPersonList.contains(ALICE));
    }

    @Test
    public void archivePerson_personAlreadyArchived_throwsPersonNotFoundException() {
        archivedPersonList.addArchivedPerson(ALICE);  // ALICE is already in the archived list
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.archivePerson(ALICE, archivedPersonList));
    }

    @Test
    public void unarchivePerson_personAlreadyInUniqueList_throwsPersonNotFoundException() {
        uniquePersonList.add(BOB);  // BOB is already in the unique person list
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.unarchivePerson(BOB, archivedPersonList));
    }

    @Test
    public void removingPersonInUniqueList_doesNotAffectArchivedPersonList() {
        uniquePersonList.add(ALICE);
        archivedPersonList.addArchivedPerson(BOB);

        // Remove ALICE from UniquePersonList
        uniquePersonList.remove(ALICE);

        // Ensure ALICE is only removed from UniquePersonList, and BOB remains in ArchivedPersonList
        assertFalse(uniquePersonList.contains(ALICE));
        assertTrue(archivedPersonList.contains(BOB));
    }

    @Test
    public void removingPersonInArchivedList_doesNotAffectUniquePersonList() {
        uniquePersonList.add(ALICE);
        archivedPersonList.addArchivedPerson(BOB);

        // Remove BOB from ArchivedPersonList
        archivedPersonList.removeArchivedPerson(BOB);

        // Ensure BOB is removed from ArchivedPersonList, and ALICE remains in UniquePersonList
        assertTrue(uniquePersonList.contains(ALICE));
        assertFalse(archivedPersonList.contains(BOB));
    }
}
