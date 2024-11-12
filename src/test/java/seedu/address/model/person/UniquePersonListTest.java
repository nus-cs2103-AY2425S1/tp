package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.WeddingBuilder;

public class UniquePersonListTest {

    private static final Wedding WEDDING_A = new WeddingBuilder().withName("Wedding A").withDate("2020-02-20").build();
    private static final Wedding WEDDING_B = new WeddingBuilder().withName("Wedding B").build();
    private static final Wedding WEDDING_C = new WeddingBuilder().withName("Wedding C").build();

    private final UniquePersonList uniquePersonList = new UniquePersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        assertTrue(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsFalse() {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withRole(VALID_TAG_HUSBAND)
                .build();
        assertFalse(uniquePersonList.contains(editedAlice));
    }

    @Test
    public void containsPhone_personWithSamePhoneInList_returnsFalse() {
        uniquePersonList.add(AMY);
        Person editedBenson = new PersonBuilder(BENSON).withPhone(VALID_PHONE_AMY)
                .build();
        assertFalse(uniquePersonList.contains(editedBenson));
    }

    @Test
    public void containsPhone_personWithDifferentPhoneInList_returnsFalse() {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withRole(VALID_TAG_HUSBAND)
                .build();
        assertFalse(uniquePersonList.contains(editedAlice));
    }

    @Test
    public void containsEmail_personWithSameEmailInList_returnsTrue() {
        uniquePersonList.add(BOB);
        Person editedBob = new PersonBuilder(BOB).withEmail(VALID_EMAIL_BOB)
                .build();
        assertTrue(uniquePersonList.contains(editedBob));
    }

    @Test
    public void containsEmail_personWithDifferentEmailInList_returnsFalse() {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withRole(VALID_TAG_HUSBAND)
                .build();
        assertFalse(uniquePersonList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setPerson(ALICE, ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(ALICE);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withRole(VALID_TAG_HUSBAND)
                .build();
        uniquePersonList.setPerson(ALICE, editedAlice);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setPerson(ALICE, BOB);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPerson(ALICE, BOB));
    }

    @Test
    public void updatePersonInvolveInEditedWedding_targetWeddingNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList
                .updatePersonInvolveInEditedWedding(null, WEDDING_B));
    }

    @Test
    public void updatePersonInvolveInEditedWedding_editedWeddingNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList
                .updatePersonInvolveInEditedWedding(WEDDING_A, null));
    }

    @Test
    public void updatePersonInvolveInEditedWedding_personWithTargetWedding_updatesOwnWedding() {
        Person alice = new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(), ALICE.getAddress(),
                ALICE.getRole(), null);
        alice.setOwnWedding(WEDDING_A);
        uniquePersonList.add(alice);

        uniquePersonList.updatePersonInvolveInEditedWedding(WEDDING_A, WEDDING_B);

        Person updatedAlice = uniquePersonList.asUnmodifiableObservableList().get(0);
        assertEquals(WEDDING_B, updatedAlice.getOwnWedding());
    }

    @Test
    public void updatePersonInvolveInEditedWedding_personWithTargetWeddingInJobList_updatesJobList() {
        Person bob = new Person(BOB.getName(), BOB.getPhone(), BOB.getEmail(), BOB.getAddress(),
                BOB.getRole(), null);
        bob.getWeddingJobs().add(WEDDING_A);
        uniquePersonList.add(bob);

        uniquePersonList.updatePersonInvolveInEditedWedding(WEDDING_A, WEDDING_B);

        Person updatedBob = uniquePersonList.asUnmodifiableObservableList().get(0);
        assertEquals(1, updatedBob.getWeddingJobs().size());
        assertEquals(WEDDING_B, updatedBob.getWeddingJobs().iterator().next());
    }

    @Test
    public void updatePersonInvolveInEditedWedding_personNotInvolvedInWedding_noChange() {
        Person alice = new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(), ALICE.getAddress(),
                ALICE.getRole(), null);
        alice.setOwnWedding(WEDDING_C);
        uniquePersonList.add(alice);

        uniquePersonList.updatePersonInvolveInEditedWedding(WEDDING_A, WEDDING_B);

        Person updatedAlice = uniquePersonList.asUnmodifiableObservableList().get(0);
        assertEquals(WEDDING_C, updatedAlice.getOwnWedding());
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePersonList.add(ALICE);
        uniquePersonList.remove(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((UniquePersonList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePersonList.add(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        uniquePersonList.setPersons(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePersonList.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        uniquePersonList.setPersons(personList);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePersonList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniquePersonList.asUnmodifiableObservableList().toString(), uniquePersonList.toString());
    }
}
