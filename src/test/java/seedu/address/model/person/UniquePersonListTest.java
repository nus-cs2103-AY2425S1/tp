package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;


public class UniquePersonListTest {

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
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(uniquePersonList.contains(editedAlice));
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
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
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

    @Test
    public void findSameField_samePhone() {
        String phoneNumber = "98765432";

        Person person = new PersonBuilder().withName("Alan")
                .withAddress("George Street").withEmail("alan@example.com")
                .withPhone(phoneNumber)
                .withTelegramUsername("alanalan").withRoles("attendee").build();

        uniquePersonList.add(person);

        Person otherPerson = new PersonBuilder().withName("Glenn")
                .withAddress("King Town").withEmail("glenn@example.com")
                .withPhone(phoneNumber)
                .withTelegramUsername("glenn").withRoles("vendor").build();

        String[] expectedValue = new String[] {"phone", phoneNumber};

        // same keyword "phone"
        assertEquals(uniquePersonList.findSameField(otherPerson)[0], expectedValue[0]);

        // same phone number
        assertEquals(uniquePersonList.findSameField(otherPerson)[1], expectedValue[1]);

        assertEquals(uniquePersonList.findSameField(otherPerson).length, expectedValue.length);
    }

    @Test
    public void findSameField_sameEmail() {
        String email = "bigboss@example.com";

        Person person = new PersonBuilder().withName("Alan")
                .withAddress("George Street").withEmail(email)
                .withPhone("98765432")
                .withTelegramUsername("alanalan").withRoles("attendee").build();

        uniquePersonList.add(person);

        Person otherPerson = new PersonBuilder().withName("Glenn")
                .withAddress("King Town").withEmail(email)
                .withPhone("99888777")
                .withTelegramUsername("glenn").withRoles("vendor").build();

        String[] expectedValue = new String[] {"email", email};

        // same keyword "email"
        assertEquals(uniquePersonList.findSameField(otherPerson)[0], expectedValue[0]);

        // same email
        assertEquals(uniquePersonList.findSameField(otherPerson)[1], expectedValue[1]);

        assertEquals(uniquePersonList.findSameField(otherPerson).length, expectedValue.length);
    }

    @Test
    public void findSameField_sameTelegram() {
        String telegram = "physics_legend";

        Person person = new PersonBuilder().withName("Alan")
                .withAddress("George Street").withEmail("alan@example.com")
                .withPhone("98765432")
                .withTelegramUsername(telegram).withRoles("attendee").build();

        uniquePersonList.add(person);

        Person otherPerson = new PersonBuilder().withName("Glenn")
                .withAddress("King Town").withEmail("glenn@example.com")
                .withPhone("99888777")
                .withTelegramUsername(telegram).withRoles("vendor").build();

        String[] expectedValue = new String[] {"telegram", telegram};

        // same keyword "telegram"
        assertEquals(uniquePersonList.findSameField(otherPerson)[0], expectedValue[0]);

        // same telegram handle
        assertEquals(uniquePersonList.findSameField(otherPerson)[1], expectedValue[1]);

        assertEquals(uniquePersonList.findSameField(otherPerson).length, expectedValue.length);
    }

    @Test
    public void equals() {
        UniquePersonList uniquePersonList = new UniquePersonList();

        // same values -> returns true
        assertTrue(uniquePersonList.equals(new UniquePersonList()));

        // same object -> returns true
        assertTrue(uniquePersonList.equals(uniquePersonList));

        // null -> returns false
        assertFalse(uniquePersonList.equals(null));

        // different types -> returns false
        assertFalse(uniquePersonList.equals(5.0f));

        uniquePersonList.add(ALICE);

        // different values -> returns false
        assertFalse(uniquePersonList.equals(new UniquePersonList()));

    }

    @Test
    public void countSamePerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.countSamePerson(null));
    }

    @Test
    public void countSamePerson_noPersonsSame() {
        assertEquals(uniquePersonList.countSamePerson(ALICE), 0);

        uniquePersonList.add(BOB);

        assertEquals(uniquePersonList.countSamePerson(ALICE), 0);
    }

    @Test
    public void countSamePerson_onePersonSame() {
        uniquePersonList.add(ALICE);

        assertEquals(uniquePersonList.countSamePerson(ALICE), 1);

        uniquePersonList.add(BOB);

        assertEquals(uniquePersonList.countSamePerson(ALICE), 1);
    }

    @Test
    public void containsPhone_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.containsPhone(null));
    }

    @Test
    public void containsPhone_false() {
        assertEquals(uniquePersonList.containsPhone(ALICE), false);

        uniquePersonList.add(BOB);

        assertEquals(uniquePersonList.containsPhone(ALICE), false);
    }

    @Test
    public void containsPhone_true() {
        uniquePersonList.add(ALICE);

        assertEquals(uniquePersonList.containsPhone(ALICE), true);

        uniquePersonList.add(BOB);

        assertEquals(uniquePersonList.containsPhone(ALICE), true);
    }

    @Test
    public void containsEmail_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.containsEmail(null));
    }

    @Test
    public void containsEmail_false() {
        assertEquals(uniquePersonList.containsEmail(ALICE), false);

        uniquePersonList.add(BOB);

        assertEquals(uniquePersonList.containsEmail(ALICE), false);
    }

    @Test
    public void containsEmail_true() {
        uniquePersonList.add(ALICE);

        assertEquals(uniquePersonList.containsEmail(ALICE), true);

        uniquePersonList.add(BOB);

        assertEquals(uniquePersonList.containsEmail(ALICE), true);
    }
}
