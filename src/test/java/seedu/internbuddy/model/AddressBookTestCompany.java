package seedu.internbuddy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internbuddy.logic.commands.CommandTestUtilCompany.VALID_ADDRESS_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtilCompany.VALID_TAG_SOFTWARE;
import static seedu.internbuddy.testutil.Assert.assertThrows;
import static seedu.internbuddy.testutil.TypicalCompanies.GOOGLE;
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
public class AddressBookTestCompany {

    private final AddressBookCompany addressBook = new AddressBookCompany();

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
        AddressBookCompany newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    /**
     * Test for resetData method
     */
    @Test
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
    }

    /**
     * Test for toString method
     */
    @Test
    public void toStringMethod() {
        String expected = AddressBookCompany.class.getCanonicalName() + "{companies="
            + addressBook.getCompanyList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose companies list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBookCompany {
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
