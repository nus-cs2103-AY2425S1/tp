package seedu.hireme.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_ROLE_BOFA;
import static seedu.hireme.testutil.Assert.assertThrows;
import static seedu.hireme.testutil.TypicalInternshipApplications.APPLE;
import static seedu.hireme.testutil.TypicalInternshipApplications.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.model.internshipapplication.exceptions.DuplicateInternshipException;
import seedu.hireme.testutil.InternshipApplicationBuilder;

public class AddressBookTest {

    private final AddressBook<InternshipApplication> addressBook = new AddressBook<>();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook<InternshipApplication> newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateApplications_throwsDuplicateApplicationsException() {
        // Two persons with the same identity fields
        InternshipApplication editedApple = new InternshipApplicationBuilder(APPLE).build();
        List<InternshipApplication> newApplications = Arrays.asList(APPLE, editedApple);
        AddressBookStub newData = new AddressBookStub(newApplications);

        assertThrows(DuplicateInternshipException.class, () -> addressBook.resetData(newData));
        assertThrows(DuplicateInternshipException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasItem(null));
    }

    @Test
    public void hasItem_itemNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasItem(APPLE));
    }

    @Test
    public void hasItem_itemInAddressBook_returnsTrue() {
        addressBook.addItem(APPLE);
        assertTrue(addressBook.hasItem(APPLE));
    }

    @Test
    public void hasItem_itemWithSameIdentityFieldsInAddressBook_returnsFalse() {
        addressBook.addItem(APPLE);
        InternshipApplication editedApple = new
                InternshipApplicationBuilder(APPLE).withRole(VALID_ROLE_BOFA).build();
        assertFalse(addressBook.hasItem(editedApple));
    }

    @Test
    public void getList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{items=" + addressBook.getList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook<InternshipApplication> {
        private final ObservableList<InternshipApplication> applications = FXCollections.observableArrayList();

        AddressBookStub(Collection<InternshipApplication> applications) {
            this.applications.setAll(applications);
        }

        @Override
        public ObservableList<InternshipApplication> getList() {
            return applications;
        }
    }

}
