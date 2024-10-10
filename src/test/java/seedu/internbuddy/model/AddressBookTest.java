package seedu.internbuddy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internbuddy.logic.commands.CommandTestUtilCompany.VALID_ADDRESS_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtilCompany.VALID_TAG_SOFTWARE;
import static seedu.internbuddy.testutil.Assert.assertThrows;
<<<<<<< HEAD
import static seedu.internbuddy.testutil.TypicalCompanies.GOOGLE;
=======
import static seedu.internbuddy.testutil.TypicalCompanies.ALICE;
>>>>>>> 398707caf839baca66fada2b3d5612969e0eb79e
import static seedu.internbuddy.testutil.TypicalCompanies.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.model.company.exceptions.DuplicateCompanyException;
import seedu.internbuddy.testutil.CompanyBuilder;

/**
 * Test class for AddressBookCompany
 */
public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getCompanyList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }


    /**
     * Test for resetData method
     */
    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    /**
     * Test for resetData method
     */
    @Test
<<<<<<< HEAD
    public void resetData_withDuplicateCompanies_throwsDuplicateCompanyException() {
        // Two companies with the same identity fields
        Company editedGoogle = new CompanyBuilder(GOOGLE).withAddress(VALID_ADDRESS_MICROSOFT)
                .withTags(VALID_TAG_SOFTWARE).build();
        List<Company> newCompanies = Arrays.asList(GOOGLE, editedGoogle);
        AddressBookStub newData = new AddressBookStub(newCompanies);

        assertThrows(DuplicateCompanyException.class, () -> addressBook.resetData(newData));
    }

    /**
     * Test for hasCompany method
     */
    @Test
    public void hasCompany_nullCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasCompany(null));
    }

    @Test
    public void hasCompany_companyNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasCompany(GOOGLE));
    }

    /**
     * Test for hasCompany method
     */
    @Test
    public void hasCompany_companyInAddressBook_returnsTrue() {
        addressBook.addCompany(GOOGLE);
        assertTrue(addressBook.hasCompany(GOOGLE));
    }

    /**
     * Test for hasCompany method
     */
    @Test
    public void hasCompany_companyWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addCompany(GOOGLE);
        Company editedGoogle = new CompanyBuilder(GOOGLE).withAddress(VALID_ADDRESS_MICROSOFT)
            .withTags(VALID_TAG_SOFTWARE).build();
        assertTrue(addressBook.hasCompany(editedGoogle));
    }

    @Test
    public void getCompanyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getCompanyList()
            .remove(0));
=======
    public void resetData_withDuplicatecompanies_throwsDuplicateCompanyException() {
        // Two companies with the same identity fields
        Company editedAlice = new CompanyBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Company> newCompanies = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newCompanies);

        assertThrows(DuplicateCompanyException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasCompany_nullCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasCompany(null));
    }

    @Test
    public void hasCompany_companyNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasCompany(ALICE));
    }

    @Test
    public void hasCompany_companyInAddressBook_returnsTrue() {
        addressBook.addCompany(ALICE);
        assertTrue(addressBook.hasCompany(ALICE));
    }

    @Test
    public void hasCompany_companyWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addCompany(ALICE);
        Company editedAlice = new CompanyBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasCompany(editedAlice));
    }

    @Test
    public void getCompanyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getCompanyList().remove(0));
>>>>>>> 398707caf839baca66fada2b3d5612969e0eb79e
    }

    /**
     * Test for toString method
     */
    @Test
    public void toStringMethod() {
<<<<<<< HEAD
        String expected = AddressBook.class.getCanonicalName() + "{companies="
            + addressBook.getCompanyList() + "}";
=======
        String expected = AddressBook.class.getCanonicalName() + "{companies=" + addressBook.getCompanyList() + "}";
>>>>>>> 398707caf839baca66fada2b3d5612969e0eb79e
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose companies list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Company> companies = FXCollections.observableArrayList();

        AddressBookStub(Collection<Company> companies) {
            this.companies.setAll(companies);
        }

        @Override
        public ObservableList<Company> getCompanyList() {
            return companies;
        }
    }

}
