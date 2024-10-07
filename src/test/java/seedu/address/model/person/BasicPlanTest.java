package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BasicPlanTest {

    @Test
    void testToString_success() {
        BasicPlan testPlan = new BasicPlan();
        assertEquals("Basic Insurance Plan", testPlan.toString());
    }

    @Test
    void testID_success() {
        BasicPlan testPlan = new BasicPlan();
        assertEquals(testPlan.getInsurancePlanId(), 0);
    }
}
