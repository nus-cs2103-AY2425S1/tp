package seedu.internbuddy.model.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_EMAIL_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_NAME_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_PHONE_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_TAG_SOFTWARE;
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
    public void isSamecompany() {
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

        // name differs in case, all other attributes same -> returns false
        Company editedBob = new CompanyBuilder(MICROSOFT).withName(VALID_NAME_MICROSOFT.toLowerCase()).build();
        assertFalse(MICROSOFT.isSameCompany(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_MICROSOFT + " ";
        editedBob = new CompanyBuilder(MICROSOFT).withName(nameWithTrailingSpaces).build();
        assertFalse(MICROSOFT.isSameCompany(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Company aliceCopy = new CompanyBuilder(GOOGLE).build();
        assertTrue(GOOGLE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(GOOGLE.equals(GOOGLE));

        // null -> returns false
        assertFalse(GOOGLE.equals(null));

        // different type -> returns false
        assertFalse(GOOGLE.equals(5));

        // different company -> returns false
        assertFalse(GOOGLE.equals(MICROSOFT));

        // different name -> returns false
        Company editedAlice = new CompanyBuilder(GOOGLE).withName(VALID_NAME_MICROSOFT).build();
        assertFalse(GOOGLE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new CompanyBuilder(GOOGLE).withPhone(VALID_PHONE_MICROSOFT).build();
        assertFalse(GOOGLE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new CompanyBuilder(GOOGLE).withEmail(VALID_EMAIL_MICROSOFT).build();
        assertFalse(GOOGLE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new CompanyBuilder(GOOGLE).withAddress(VALID_ADDRESS_MICROSOFT).build();
        assertFalse(GOOGLE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new CompanyBuilder(GOOGLE).withTags(VALID_TAG_SOFTWARE).build();
        assertFalse(GOOGLE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Company.class.getCanonicalName() + "{name=" + GOOGLE.getName() + ", phone=" + GOOGLE.getPhone()
                + ", email=" + GOOGLE.getEmail() + ", address=" + GOOGLE.getAddress() + ", tags=" + GOOGLE.getTags()
                + ", status=" + GOOGLE.getStatus() + "}";
        assertEquals(expected, GOOGLE.toString());
    }
}
