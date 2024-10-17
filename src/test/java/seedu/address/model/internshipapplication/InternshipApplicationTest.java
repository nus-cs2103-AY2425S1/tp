package seedu.address.model.internshipapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_EMAIL_BOFA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_BOFA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOFA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOFA;
import static seedu.address.testutil.TypicalInternshipApplications.APPLE;
import static seedu.address.testutil.TypicalInternshipApplications.BOFA;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.InternshipApplicationBuilder;


public class InternshipApplicationTest {

    @Test
    public void isSame() {
        // same object -> returns true
        assertTrue(APPLE.isSame(APPLE));

        // null -> returns false
        assertFalse(APPLE.isSame(null));

        // same name, all other attributes different -> returns false
        InternshipApplication editedApple = new InternshipApplicationBuilder(APPLE).withRole(VALID_ROLE_BOFA)
                 .withDate(VALID_DATE_BOFA).build();
        assertFalse(APPLE.isSame(editedApple));

        // different name, all other attributes same -> returns false
        editedApple = new InternshipApplicationBuilder(APPLE).withName(VALID_COMPANY_NAME_BOFA).build();
        assertFalse(APPLE.isSame(editedApple));

        // name differs in case, all other attributes same -> returns true
        InternshipApplication editedBofa = new InternshipApplicationBuilder(BOFA).withName(VALID_COMPANY_NAME_BOFA
                    .toLowerCase()).build();
        assertTrue(BOFA.isSame(editedBofa));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_COMPANY_NAME_BOFA + " ";
        editedBofa = new InternshipApplicationBuilder(BOFA).withName(nameWithTrailingSpaces).build();
        assertTrue(BOFA.isSame(editedBofa));
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
    }

    @Test
    public void toStringMethod() {
        String expected = InternshipApplication.class.getCanonicalName() + "{Company="
                + APPLE.getCompany() + ", Date of Application="
                + APPLE.getDateOfApplication() + ", Role=" + APPLE.getRole() + "}";
        assertEquals(expected, APPLE.toString());
    }
}
