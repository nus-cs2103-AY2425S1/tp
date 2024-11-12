package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CompanyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Company(null));
    }

    @Test
    public void constructor_invalidCompany_throwsIllegalArgumentException() {
        String invalidCompany = "";
        assertThrows(IllegalArgumentException.class, () -> new Company(invalidCompany));
    }

    @Test
    public void isValidCompany() {
        // null company
        assertThrows(NullPointerException.class, () -> Company.isValidCompany(null));

        // invalid Company
        assertFalse(Company.isValidCompany("")); // empty string
        assertFalse(Company.isValidCompany("   ")); // spaces only

        // valid Company
        assertTrue(Company.isValidCompany("^")); // only non-alphanumeric characters
        assertTrue(Company.isValidCompany("peter*")); // contains non-alphanumeric characters
        assertTrue(Company.isValidCompany("peter jack")); // alphabets only
        assertTrue(Company.isValidCompany("12345")); // numbers only
        assertTrue(Company.isValidCompany("peter the 2nd")); // alphanumeric characters
        assertTrue(Company.isValidCompany("Capital Tan")); // with capital letters
        assertTrue(Company.isValidCompany("David Roger Jackson Ray Jr 2nd")); // long Companys
    }

    @Test
    public void equals() {
        Company company = new Company("Valid Company");

        // same values -> returns true
        assertTrue(company.equals(new Company("Valid Company")));

        // same object -> returns true
        assertTrue(company.equals(company));

        // null -> returns false
        assertFalse(company.equals(null));

        // different types -> returns false
        assertFalse(company.equals(5.0f));

        // different values -> returns false
        assertFalse(company.equals(new Company("Other Valid Company")));
    }
}
