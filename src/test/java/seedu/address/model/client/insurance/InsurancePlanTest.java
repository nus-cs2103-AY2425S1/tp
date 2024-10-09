package seedu.address.model.client.insurance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

class InsurancePlanTest {

    @Test
    void testEquals_success() {
        InsurancePlan testPlan = new TravelPlanStub();
        InsurancePlan testPlan2 = new TravelPlanStub();
        assertEquals(testPlan, testPlan2);
    }

    @Test
    void testEquals_fail_object() {
        InsurancePlan testPlan = new TravelPlanStub();
        assertNotEquals(testPlan, new Object());
    }

    @Test
    void testEquals_fail_anotherInsurancePlan() {
        InsurancePlan testPlan = new TravelPlanStub();
        InsurancePlan testPlan2 = new BasicPlanStub();
        assertNotEquals(testPlan, testPlan2);
    }

    @Test
    void testCheckValidPlan_success() {
        try {
            int validId = 0;
            InsurancePlan.checkValidPlan(validId);
        } catch (IllegalValueException e) {
            fail();
        }
    }

    @Test
    void testCheckValidPlan_fail() {
        try {
            int validId = -1;
            InsurancePlan.checkValidPlan(validId);
            fail();
        } catch (IllegalValueException e) {
            assertEquals(e.getMessage(), InsurancePlan.INVALID_PLAN_ID_MESSAGE);
        }
    }
}
