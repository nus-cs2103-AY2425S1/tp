package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.policy.EducationPolicy;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.LifePolicy;
import seedu.address.model.policy.Policy;

class JsonAdaptedPolicyTest {


    @Test
    public void testToModelType_validLifePolicy() throws Exception {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy(300.0, 3000.0, "LIFE");
        Policy modelPolicy = policy.toModelType();
        assertEquals(LifePolicy.class, modelPolicy.getClass());
        assertEquals(300.0, modelPolicy.getPremiumAmount());
        assertEquals(3000.0, modelPolicy.getCoverageAmount());
    }
    @Test
    public void testToModelType_validHealthPolicy() throws Exception {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy(400.0, 4000.0, "HEALTH");
        Policy modelPolicy = policy.toModelType();
        assertEquals(HealthPolicy.class, modelPolicy.getClass());
        assertEquals(400.0, modelPolicy.getPremiumAmount());
        assertEquals(4000.0, modelPolicy.getCoverageAmount());
    }

    @Test
    public void testToModelType_validEducationPolicy() throws Exception {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy(
                new EducationPolicy(500.0, 5000.0));
        Policy modelPolicy = policy.toModelType();
        assertEquals(EducationPolicy.class, modelPolicy.getClass());
        assertEquals(500.0, modelPolicy.getPremiumAmount());
        assertEquals(5000.0, modelPolicy.getCoverageAmount());
    }
    @Test
    public void testToModelType_invalidPolicy_throwsIllegalValueException() {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy(400.0, 4000.0, "FOO");
        assertThrows(IllegalValueException.class, policy::toModelType);
    }

}

