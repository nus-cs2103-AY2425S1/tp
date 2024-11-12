package seedu.internbuddy.model.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_EMAIL_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_NAME_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_PHONE_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_STATUS_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_TAG_SOFTWARE;
import static seedu.internbuddy.testutil.Assert.assertThrows;
import static seedu.internbuddy.testutil.TypicalApplications.PM_APPLICATION;
import static seedu.internbuddy.testutil.TypicalCompanies.GOOGLE;
import static seedu.internbuddy.testutil.TypicalCompanies.MICROSOFT;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.testutil.CompanyBuilder;

public class CompanyTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Company company = new CompanyBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> company.getTags().remove(0));
    }

    @Test
    public void isSameCompany() {
        // same object -> returns true
        assertTrue(GOOGLE.isSameCompany(GOOGLE));

        // null -> returns false
        assertFalse(GOOGLE.isSameCompany(null));

        // same name, all other attributes different -> returns true
        Company editedAlice = new CompanyBuilder(GOOGLE).withPhone(VALID_PHONE_MICROSOFT)
                .withEmail(VALID_EMAIL_MICROSOFT).withAddress(VALID_ADDRESS_MICROSOFT)
                .withTags(VALID_TAG_SOFTWARE).build();
        assertTrue(GOOGLE.isSameCompany(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new CompanyBuilder(GOOGLE).withName(VALID_NAME_MICROSOFT).build();
        assertFalse(GOOGLE.isSameCompany(editedAlice));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_MICROSOFT + " ";
        Company editedBob = new CompanyBuilder(MICROSOFT).withName(nameWithTrailingSpaces).build();
        assertFalse(MICROSOFT.isSameCompany(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Company googleCopy = new CompanyBuilder(GOOGLE).build();
        assertTrue(GOOGLE.equals(googleCopy));

        // same object -> returns true
        assertTrue(GOOGLE.equals(GOOGLE));

        // null -> returns false
        assertFalse(GOOGLE.equals(null));

        // different type -> returns false
        assertFalse(GOOGLE.equals(5));

        // different company -> returns false
        assertFalse(GOOGLE.equals(MICROSOFT));

        // different name -> returns false
        Company editedGoogle = new CompanyBuilder(GOOGLE).withName(VALID_NAME_MICROSOFT).build();
        assertFalse(GOOGLE.equals(editedGoogle));

        // different phone -> returns false
        editedGoogle = new CompanyBuilder(GOOGLE).withPhone(VALID_PHONE_MICROSOFT).build();
        assertFalse(GOOGLE.equals(editedGoogle));

        // different email -> returns false
        editedGoogle = new CompanyBuilder(GOOGLE).withEmail(VALID_EMAIL_MICROSOFT).build();
        assertFalse(GOOGLE.equals(editedGoogle));

        // different address -> returns false
        editedGoogle = new CompanyBuilder(GOOGLE).withAddress(VALID_ADDRESS_MICROSOFT).build();
        assertFalse(GOOGLE.equals(editedGoogle));

        // different tags -> returns false
        editedGoogle = new CompanyBuilder(GOOGLE).withTags(VALID_TAG_SOFTWARE).build();
        assertFalse(GOOGLE.equals(editedGoogle));

        // different status -> returns false
        editedGoogle = new CompanyBuilder(GOOGLE).withStatus(VALID_STATUS_MICROSOFT).build();
        assertFalse(GOOGLE.equals(editedGoogle));

        // different applications -> return false
        editedGoogle = new CompanyBuilder(GOOGLE).withApplications(PM_APPLICATION).build();
        assertFalse(GOOGLE.equals(editedGoogle));
    }

    @Test
    public void toStringMethod() {
        String expected = Company.class.getCanonicalName() + "{name=" + GOOGLE.getName()
                + ", phone=" + GOOGLE.getPhone() + ", email=" + GOOGLE.getEmail() + ", address=" + GOOGLE.getAddress()
                + ", status=" + GOOGLE.getStatus() + ", tags=" + GOOGLE.getTags()
                + ", applications=" + GOOGLE.getApplications() + "}";
        assertEquals(expected, GOOGLE.toString());
    }
}
