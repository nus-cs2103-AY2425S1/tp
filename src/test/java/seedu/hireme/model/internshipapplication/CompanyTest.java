package seedu.hireme.model.internshipapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.testutil.Assert.assertThrows;
import static seedu.hireme.testutil.TypicalInternshipApplications.GOOGLE;
import static seedu.hireme.testutil.TypicalInternshipApplications.YAHOO;

import org.junit.jupiter.api.Test;

public class CompanyTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Company(null, null));
        assertThrows(NullPointerException.class, () -> new Company(new Email("valid@gmail.com"), null));
        assertThrows(NullPointerException.class, () -> new Company(null, new Name("Valid name")));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () ->
                new Company(new Email(invalidEmail), new Name("name")));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () ->
                new Company(new Email("example@example.com"), new Name(invalidName)));
    }

    @Test
    public void equals() {
        Company google = GOOGLE.getCompany();
        Company yahoo = YAHOO.getCompany();

        // same values -> returns true
        assertTrue(google.equals(google));

        // null -> returns false
        assertFalse(google.equals(null));

        // different type -> returns false
        assertFalse(google.equals(5));

        // different person -> returns false
        assertFalse(google.equals(yahoo));
    }

    @Test
    public void toStringMethod() {
        Company google = GOOGLE.getCompany();
        String expected = "Company: " + google.getName()
                + ", Email: " + google.getEmail();
        assertEquals(expected, google.toString());
    }

}
