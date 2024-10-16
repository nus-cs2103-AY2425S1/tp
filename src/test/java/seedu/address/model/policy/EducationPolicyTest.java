package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class EducationPolicyTest {
    private final EducationPolicy education = new EducationPolicy();

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        LocalDate expiryDate = LocalDate.now();
        Person insuree = new PersonBuilder().build();
        assertThrows(NullPointerException.class, () -> new LifePolicy(0, 0, null, insuree));
        assertThrows(NullPointerException.class, () -> new LifePolicy(0, 0, expiryDate, null));
    }

    @Test
    public void getType_returnsCorrectType() {
        assertEquals(PolicyType.EDUCATION, education.getType());
    }

    @Test
    public void toStringMethod() {
        String expectedPrefix = "Policy type: " + education.getType() + " | ";
        assertTrue(education.toString().startsWith(expectedPrefix));
    }

    @Test
    public void equalsMethod() {
        // same object -> returns true
        assertTrue(education.equals(education));

        // null -> returns false
        assertFalse(education.equals(null));

        // same values -> return true
        EducationPolicy policyWithSameValues = new EducationPolicy();
        assertTrue(education.equals(policyWithSameValues));

        // different type -> returns false
        Policy life = new LifePolicy();
        life.setPremiumAmount(education.getPremiumAmount());
        life.setCoverageAmount(education.getCoverageAmount());
        assertFalse(education.equals(life));
    }
}
