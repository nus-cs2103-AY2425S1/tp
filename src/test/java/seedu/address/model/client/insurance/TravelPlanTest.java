package seedu.address.model.client.insurance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TravelPlanTest {

    @Test
    void testToString_success() {
        TravelPlan travelPlan = new TravelPlan();
        assertEquals("Travel Insurance Plan", travelPlan.toString());
    }

    @Test
    void testID_success() {
        TravelPlan travelPlan = new TravelPlan();
        assertEquals(1, travelPlan.getInsurancePlanId());
    }
}
