package seedu.address.model.client.insurance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class InsurancePlanTest {

    @Test
    void testEquals_success() {
        InsurancePlan testPlan = new TravelPlanStub();
        InsurancePlan testPlan2 = new TravelPlanStub();
        assertEquals(testPlan, testPlan2);
    }

    @Test
    void testEquals_sameObject_success() {
        InsurancePlan testPlan = new TravelPlanStub();
        testPlan.equals(testPlan);
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
    void checkToString() {
        InsurancePlan testPlan = new BasicPlanStub();
        assertEquals("Basic Insurance Plan", testPlan.toString());
    }
}
