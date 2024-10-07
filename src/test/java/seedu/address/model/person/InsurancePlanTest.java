package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
        assertFalse(testPlan.equals(new Object()));
    }

    @Test
    void testEquals_fail_anotherInsurancePlan() {
        InsurancePlan testPlan = new TravelPlanStub();
        InsurancePlan testPlan2 = new BasicPlanStub();
        assertFalse(testPlan.equals(testPlan2));
    }
}