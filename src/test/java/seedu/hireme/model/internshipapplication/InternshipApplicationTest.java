package seedu.hireme.model.internshipapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_COMPANY_EMAIL_APPLE;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_COMPANY_EMAIL_BOFA;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_APPLE;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_BOFA;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_DATE_BOFA;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_ROLE_APPLE;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_ROLE_BOFA;
import static seedu.hireme.testutil.Assert.assertThrows;
import static seedu.hireme.testutil.TypicalInternshipApplications.APPLE;
import static seedu.hireme.testutil.TypicalInternshipApplications.BOFA;

import org.junit.jupiter.api.Test;

import seedu.hireme.testutil.InternshipApplicationBuilder;


public class InternshipApplicationTest {

    @Test
    public void constructorNoStatus_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new InternshipApplication(null,
                new Date("01/01/24"), new Role("SWE")));
        assertThrows(NullPointerException.class, () -> new InternshipApplication(APPLE.getCompany(),
                null, new Role("SWE")));
        assertThrows(NullPointerException.class, () -> new InternshipApplication(APPLE.getCompany(),
                new Date("01/01/24"), null));
        assertThrows(NullPointerException.class, () -> new InternshipApplication(null,
                null, null));
    }

    @Test
    public void constructorWithStatus_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new InternshipApplication(null,
                new Date("01/01/24"), new Role("SWE"), Status.PENDING));
        assertThrows(NullPointerException.class, () -> new InternshipApplication(APPLE.getCompany(),
                null, new Role("SWE"), Status.PENDING));
        assertThrows(NullPointerException.class, () -> new InternshipApplication(APPLE.getCompany(),
                new Date("01/01/24"), null, Status.PENDING));
        assertThrows(NullPointerException.class, () -> new InternshipApplication(APPLE.getCompany(),
                new Date("01/01/24"), new Role("SWE"), null));
        assertThrows(NullPointerException.class, () -> new InternshipApplication(null,
                null, null, null));
    }

    @Test
    public void getCompany() {
        Company expectedCompany = new Company(new Email(VALID_COMPANY_EMAIL_APPLE), new Name(VALID_COMPANY_NAME_APPLE));
        assertEquals(expectedCompany, APPLE.getCompany());
    }

    @Test
    public void getCompanyName() {
        Name expectedCompanyName = new Name(VALID_COMPANY_NAME_APPLE);
        assertEquals(expectedCompanyName, APPLE.getCompanyName());
    }

    @Test
    public void getCompanyNameValue() {
        assertEquals(VALID_COMPANY_NAME_APPLE, APPLE.getCompanyNameValue());
    }

    @Test
    public void getDate() {
        Date expectedDate = new Date("01/01/09");
        assertEquals(expectedDate, APPLE.getDateOfApplication());
    }

    @Test
    public void getRole() {
        Role expectedRole = new Role(VALID_ROLE_APPLE);
        assertEquals(expectedRole, APPLE.getRole());
    }

    @Test
    public void getStatus() {
        assertEquals(Status.PENDING, APPLE.getStatus());
    }

    @Test
    public void setStatus_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> APPLE.setStatus(null));
    }

    @Test
    public void setStatus_validStatus_success() {
        APPLE.setStatus(Status.REJECTED);
        assertEquals(Status.REJECTED, APPLE.getStatus());
        // Reset status
        APPLE.setStatus(Status.PENDING);
    }

    @Test
    public void deepCopy() {
        InternshipApplication deepCopy = APPLE.deepCopy();
        assertEquals(APPLE, deepCopy);
    }

    @Test
    public void isSame() {
        // same object -> returns true
        assertTrue(APPLE.isSame(APPLE));

        // null -> returns false
        assertFalse(APPLE.isSame(null));

        // different internship application -> returns false
        assertFalse(APPLE.isSame(BOFA));

        // same name, all other attributes different -> returns false
        InternshipApplication editedApple = new InternshipApplicationBuilder(APPLE).withRole(VALID_ROLE_BOFA)
                .withDate(VALID_DATE_BOFA).build();
        assertFalse(APPLE.isSame(editedApple));

        // same name, role, all other attributes different -> returns false
        editedApple = new InternshipApplicationBuilder(APPLE).withDate(VALID_DATE_BOFA).build();
        assertFalse(APPLE.isSame(editedApple));

        // same name, role, date but different status -> returns true
        editedApple = new InternshipApplicationBuilder(APPLE).withStatus(Status.REJECTED).build();
        assertTrue(APPLE.isSame(editedApple));

        // same attributes -> returns true
        editedApple = new InternshipApplicationBuilder(APPLE).build();
        assertTrue(APPLE.isSame(editedApple));
    }

    @Test
    public void equals() {
        // same values -> returns true
        InternshipApplication appleCopy = new InternshipApplicationBuilder(APPLE).build();
        assertTrue(APPLE.equals(appleCopy));

        // same object -> returns true
        assertTrue(APPLE.equals(APPLE));

        // null -> returns false
        assertFalse(APPLE.equals(null));

        // different type -> returns false
        assertFalse(APPLE.equals(5));

        // different person -> returns false
        assertFalse(APPLE.equals(BOFA));

        // different company name -> returns false
        InternshipApplication editedApple =
                new InternshipApplicationBuilder(APPLE).withName(VALID_COMPANY_NAME_BOFA).build();
        assertFalse(APPLE.equals(editedApple));

        // different role -> returns false
        editedApple = new InternshipApplicationBuilder(APPLE).withRole(VALID_ROLE_BOFA).build();
        assertFalse(APPLE.equals(editedApple));

        // different email -> returns false
        editedApple = new InternshipApplicationBuilder(APPLE).withEmail(VALID_COMPANY_EMAIL_BOFA).build();
        assertFalse(APPLE.equals(editedApple));

        // different date -> returns false
        editedApple = new InternshipApplicationBuilder(APPLE).withDate("02/01/24").build();
        assertFalse(APPLE.equals(editedApple));

        // different status -> returns false
        editedApple = new InternshipApplicationBuilder(APPLE).withStatus(Status.REJECTED).build();
        assertFalse(APPLE.equals(editedApple));
    }

    @Test
    public void toStringMethod() {
        String expected = InternshipApplication.class.getCanonicalName() + "{Company="
                + APPLE.getCompany() + ", Date of Application="
                + APPLE.getDateOfApplication() + ", Role="
                + APPLE.getRole() + ", Status=" + APPLE.getStatus() + "}";
        assertEquals(expected, APPLE.toString());
    }
}
