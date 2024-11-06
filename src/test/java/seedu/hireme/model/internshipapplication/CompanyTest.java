package seedu.hireme.model.internshipapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.testutil.Assert.assertThrows;
import static seedu.hireme.testutil.TypicalInternshipApplications.GOOGLE;
import static seedu.hireme.testutil.TypicalInternshipApplications.YAHOO;

import org.junit.jupiter.api.Test;

public class CompanyTest {

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        // Null email and name
        assertThrows(NullPointerException.class, () -> new Company(null, null));

        // Null email only
        assertThrows(NullPointerException.class, () -> new Company(null, new Name("Valid Name")));

        // Null name only
        assertThrows(NullPointerException.class, () -> new Company(new Email("valid@example.com"), null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        // Empty email
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () ->
                new Company(new Email(invalidEmail), new Name("Valid Name")));

        // Invalid email format
        String invalidEmailFormat = "invalid-email";
        assertThrows(IllegalArgumentException.class, () ->
                new Company(new Email(invalidEmailFormat), new Name("Valid Name")));

        // Edge case for just "@" symbol
        assertThrows(IllegalArgumentException.class, () ->
                new Company(new Email("@"), new Name("Valid Name")));

        // Edge case for domain without TLD
        assertThrows(IllegalArgumentException.class, () ->
                new Company(new Email("user@domain"), new Name("Valid Name")));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        // Empty name
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () ->
                new Company(new Email("example@example.com"), new Name(invalidName)));

    }

    @Test
    public void validConstructor_noExceptions() {
        // Valid email and name
        Company company = new Company(new Email("valid@example.com"), new Name("Valid Name"));
        assertEquals("valid@example.com", company.getEmail().toString());
        assertEquals("Valid Name", company.getName().toString());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Company google = GOOGLE.getCompany();

        // Same object reference
        assertTrue(google.equals(google));
    }

    @Test
    public void equals_differentObjectWithSameAttributes_returnsTrue() {
        Company google1 = new Company(new Email("google@example.com"), new Name("Google"));
        Company google2 = new Company(new Email("google@example.com"), new Name("Google"));

        // Different object, same values
        assertTrue(google1.equals(google2));
        assertTrue(google2.equals(google1));
    }

    @Test
    public void equals_nullAndDifferentType_returnsFalse() {
        Company google = GOOGLE.getCompany();

        // Null check
        assertFalse(google.equals(null));

        // Different type
        assertFalse(google.equals("Not a Company"));
    }

    @Test
    public void equals_differentAttributes_returnsFalse() {
        Company google = GOOGLE.getCompany();
        Company yahoo = YAHOO.getCompany();

        // Different email and name
        assertFalse(google.equals(yahoo));

        // Different email, same name
        Company differentEmail = new Company(new Email("different@example.com"), google.getName());
        assertFalse(google.equals(differentEmail));

        // Same email, different name
        Company differentName = new Company(google.getEmail(), new Name("Different Name"));
        assertFalse(google.equals(differentName));
    }

    @Test
    public void hashCode_sameAttributes_equalHashCodes() {
        Company google1 = new Company(new Email("google@example.com"), new Name("Google"));
        Company google2 = new Company(new Email("google@example.com"), new Name("Google"));

        // Objects with same attributes should have the same hash code
        assertEquals(google1.hashCode(), google2.hashCode());
    }

    @Test
    public void hashCode_differentAttributes_differentHashCodes() {
        Company google = new Company(new Email("google@example.com"), new Name("Google"));
        Company yahoo = new Company(new Email("yahoo@example.com"), new Name("Yahoo"));

        // Objects with different attributes should have different hash codes
        assertFalse(google.hashCode() == yahoo.hashCode());
    }

    @Test
    public void toString_validCompany_correctFormat() {
        Company google = GOOGLE.getCompany();
        String expected = String.format("Company: %s, Email: %s", google.getName(), google.getEmail());

        // Correct toString format
        assertEquals(expected, google.toString());
    }

    @Test
    public void getNameValue_returnsNameAsString() {
        Company company = new Company(new Email("test@example.com"), new Name("Test Company"));

        // getNameValue should return the string representation of the name
        assertEquals("Test Company", company.getNameValue());
    }

    @Test
    public void getEmail_returnsEmailAsString() {
        Company company = new Company(new Email("test@example.com"), new Name("Test Company"));

        // getEmail should return the email object which contains the correct string
        assertEquals("test@example.com", company.getEmail().toString());
    }

    @Test
    public void largeVolumeOfCompanies_differentAttributes_doesNotConflict() {
        // Test for a large number of Company instances with slight variations
        for (int i = 0; i < 1000; i++) {
            Company company = new Company(new Email("company" + i + "@example.com"), new Name("Company" + i));
            Company sameCompany = new Company(new Email("company" + i + "@example.com"), new Name("Company" + i));
            Company differentCompany = new Company(new Email("company" + i + "@example.com"),
                    new Name("Different Company" + i));

            // Check equality for identical companies
            assertEquals(company, sameCompany);

            // Check inequality for slightly different companies
            assertNotEquals(company, differentCompany);
        }
    }
}
