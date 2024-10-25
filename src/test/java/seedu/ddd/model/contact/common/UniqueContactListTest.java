package seedu.ddd.model.contact.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_1;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_ADDRESS;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_CLIENT;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_VENDOR;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.contact.exceptions.ContactNotFoundException;
import seedu.ddd.model.contact.exceptions.DuplicateContactException;
import seedu.ddd.testutil.contact.ClientBuilder;

public class UniqueContactListTest {

    private final UniqueContactList uniqueContactList = new UniqueContactList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueContactList.contains(VALID_CLIENT));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueContactList.add(VALID_CLIENT);
        assertTrue(uniqueContactList.contains(VALID_CLIENT));
    }

    @Test
    public void contains_contactWithSameIdentityFieldsInList_returnsTrue() {
        uniqueContactList.add(VALID_CLIENT);
        Contact editedAlice = new ClientBuilder(VALID_CLIENT)
                .withAddress(VALID_VENDOR_ADDRESS)
                .withTags(VALID_TAG_1)
                .build();
        assertTrue(uniqueContactList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.add(null));
    }

    @Test
    public void add_duplicateContact_throwsDuplicateContactException() {
        uniqueContactList.add(VALID_CLIENT);
        assertThrows(DuplicateContactException.class, () -> uniqueContactList.add(VALID_CLIENT));
    }

    @Test
    public void setContact_nullTargetContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.setContact(null, VALID_CLIENT));
    }

    @Test
    public void setContact_nullEditedContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.setContact(VALID_CLIENT, null));
    }

    @Test
    public void setContact_targetContactNotInList_throwsContactNotFoundException() {
        assertThrows(ContactNotFoundException.class, () -> uniqueContactList.setContact(VALID_CLIENT, VALID_CLIENT));
    }

    @Test
    public void setContact_editedContactIsSameContact_success() {
        uniqueContactList.add(VALID_CLIENT);
        uniqueContactList.setContact(VALID_CLIENT, VALID_CLIENT);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        expectedUniqueContactList.add(VALID_CLIENT);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContact_editedContactHasSameIdentity_success() {
        uniqueContactList.add(VALID_CLIENT);
        Contact editedAlice = new ClientBuilder(VALID_CLIENT)
                .withAddress(VALID_VENDOR_ADDRESS)
                .withTags(VALID_TAG_1)
                .build();
        uniqueContactList.setContact(VALID_CLIENT, editedAlice);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        expectedUniqueContactList.add(editedAlice);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContact_editedContactHasDifferentIdentity_success() {
        uniqueContactList.add(VALID_CLIENT);
        uniqueContactList.setContact(VALID_CLIENT, VALID_VENDOR);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        expectedUniqueContactList.add(VALID_VENDOR);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContact_editedContactHasNonUniqueIdentity_throwsDuplicateContactException() {
        uniqueContactList.add(VALID_CLIENT);
        uniqueContactList.add(VALID_VENDOR);
        assertThrows(DuplicateContactException.class, () -> uniqueContactList.setContact(VALID_CLIENT, VALID_VENDOR));
    }

    @Test
    public void remove_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.remove(null));
    }

    @Test
    public void remove_contactDoesNotExist_throwsContactNotFoundException() {
        assertThrows(ContactNotFoundException.class, () -> uniqueContactList.remove(VALID_CLIENT));
    }

    @Test
    public void remove_existingContact_removesContact() {
        uniqueContactList.add(VALID_CLIENT);
        uniqueContactList.remove(VALID_CLIENT);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContacts_nullUniqueContactList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.setContacts((UniqueContactList) null));
    }

    @Test
    public void setContacts_uniqueContactList_replacesOwnListWithProvidedUniqueContactList() {
        uniqueContactList.add(VALID_CLIENT);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        expectedUniqueContactList.add(VALID_VENDOR);
        uniqueContactList.setContacts(expectedUniqueContactList);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContacts_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.setContacts((List<Contact>) null));
    }

    @Test
    public void setContacts_list_replacesOwnListWithProvidedList() {
        uniqueContactList.add(VALID_CLIENT);
        List<Contact> contactList = Collections.singletonList(VALID_VENDOR);
        uniqueContactList.setContacts(contactList);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        expectedUniqueContactList.add(VALID_VENDOR);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContacts_listWithDuplicateContacts_throwsDuplicateContactException() {
        List<Contact> listWithDuplicateContacts = Arrays.asList(VALID_CLIENT, VALID_CLIENT);
        assertThrows(DuplicateContactException.class, () -> uniqueContactList.setContacts(listWithDuplicateContacts));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueContactList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueContactList.asUnmodifiableObservableList().toString(), uniqueContactList.toString());
    }
}
