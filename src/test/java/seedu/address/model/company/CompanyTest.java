package seedu.address.model.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BIGTECH;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCompanies.GOOGLE;
import static seedu.address.testutil.TypicalCompanies.MICROSOFT;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CompanyBuilder;

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
                .withEmail(VALID_EMAIL_MICROSOFT)
                .withAddress(VALID_ADDRESS_MICROSOFT).withTags(VALID_TAG_BIGTECH).build();
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
        editedGoogle = new CompanyBuilder(GOOGLE).withTags(VALID_TAG_BIGTECH).build();
        assertFalse(GOOGLE.equals(editedGoogle));
    }

    @Test
    public void toStringMethod() {
        String expected = Company.class.getCanonicalName() + "{name=" + GOOGLE.getName() + ", phone="
                + GOOGLE.getPhone()
                + ", email=" + GOOGLE.getEmail() + ", address=" + GOOGLE.getAddress() + ", url="
                + GOOGLE.getCareerPageUrl() + ", application status=" + GOOGLE.getApplicationStatus()
                + ", tags=" + GOOGLE.getTags() + ", bookmark=" + GOOGLE.getIsBookmark()
                + "}";
        assertEquals(expected, GOOGLE.toString());
    }
}
