package seedu.internbuddy.model.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internbuddy.logic.commands.CommandTestUtilCompany.VALID_ADDRESS_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtilCompany.VALID_EMAIL_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtilCompany.VALID_NAME_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtilCompany.VALID_PHONE_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtilCompany.VALID_TAG_SOFTWARE;
import static seedu.internbuddy.logic.commands.CommandTestUtilCompany.VALID_TAG_TECH;
import static seedu.internbuddy.testutil.Assert.assertThrows;
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
        Company editedGoogle = new CompanyBuilder(GOOGLE)
                .withPhone(VALID_PHONE_MICROSOFT).withEmail(VALID_EMAIL_MICROSOFT)
                .withAddress(VALID_ADDRESS_MICROSOFT).withTags(VALID_TAG_TECH).build();
        assertTrue(GOOGLE.isSameCompany(editedGoogle));

        // different name, all other attributes same -> returns false
        editedGoogle = new CompanyBuilder(GOOGLE).withName(VALID_NAME_MICROSOFT).build();
        assertFalse(GOOGLE.isSameCompany(editedGoogle));

        // name differs in case, all other attributes same -> returns false
        Company editedMicrosoft = new CompanyBuilder(MICROSOFT).withName(VALID_NAME_MICROSOFT.toLowerCase()).build();
        assertFalse(MICROSOFT.isSameCompany(editedMicrosoft));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_MICROSOFT + " ";
        editedMicrosoft = new CompanyBuilder(MICROSOFT).withName(nameWithTrailingSpaces).build();
        assertFalse(MICROSOFT.isSameCompany(editedMicrosoft));
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
    }

    @Test
    public void toStringMethod() {
        String expected = Company.class.getCanonicalName()
                + "{name=" + GOOGLE.getName() + ", phone=" + GOOGLE.getPhone()
                + ", email=" + GOOGLE.getEmail() + ", address=" + GOOGLE.getAddress()
                + ", tags=" + GOOGLE.getTags() + ", status=" + GOOGLE.getStatus() + "}";
        assertEquals(expected, GOOGLE.toString());
    }
}
