package seedu.internbuddy.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internbuddy.testutil.TypicalApplications.DS_APPLICATION;
import static seedu.internbuddy.testutil.TypicalApplications.PM_APPLICATION;
import static seedu.internbuddy.testutil.TypicalApplications.SWE_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.testutil.ApplicationBuilder;


public class ApplicationTest {

    @Test
    public void isSameApplication() {
        // same object -> returns true
        assertTrue(SWE_APPLICATION.isSameApplication(SWE_APPLICATION));

        // null -> returns false
        assertFalse(SWE_APPLICATION.isSameApplication(null));

        // same name, all other attributes different -> returns true
        Application editedDS = new ApplicationBuilder(DS_APPLICATION).withAppStatus("APPLIED").build();
        assertTrue(DS_APPLICATION.isSameApplication(editedDS));

        // different name, all other attributes same -> returns false
        Application editedPM = new ApplicationBuilder(PM_APPLICATION)
            .withDescription("Product Manager").build();
        assertFalse(PM_APPLICATION.isSameApplication(editedPM));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Application sweCopy = new ApplicationBuilder(SWE_APPLICATION).build();
        assertTrue(SWE_APPLICATION.equals(sweCopy));

        // same object -> returns true
        assertTrue(SWE_APPLICATION.equals(SWE_APPLICATION));

        // null -> returns false
        assertFalse(SWE_APPLICATION.equals(null));

        // different type -> returns false
        assertFalse(SWE_APPLICATION.equals(5));

        // different application -> returns false
        assertFalse(SWE_APPLICATION.equals(DS_APPLICATION));

        // different name -> returns false
        Application editedSwe = new ApplicationBuilder(SWE_APPLICATION).withDescription("Software Engineer").build();
        assertFalse(SWE_APPLICATION.equals(editedSwe));

        // different status -> returns True
        editedSwe = new ApplicationBuilder(SWE_APPLICATION).withAppStatus("APPLIED").build();
        assertTrue(SWE_APPLICATION.equals(editedSwe));
    }

    @Test
    public void testNewStatus() {
        Application sweCopy = new ApplicationBuilder(SWE_APPLICATION).build();
        Application sweCopy2 = sweCopy.setAppStatus(new AppStatus("REJECTED"));
        assertTrue(sweCopy.isSameApplication(sweCopy2));
    }

    @Test
    public void testToString() {
        Application sweCopy = new ApplicationBuilder(SWE_APPLICATION).build();
        String expected = Application.class.getCanonicalName() + "{name=" + sweCopy.getName()
                + ", description=" + sweCopy.getDescription() + ", appStatus=" + sweCopy.getAppStatus()
                + "}";
        assertEquals(expected, sweCopy.toString());
    }
}
