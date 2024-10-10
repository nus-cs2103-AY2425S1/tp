package seedu.address.model.person.insurance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class InsurancePlansManagerTest {

    private final InsurancePlansManager insurancePlansManager = new InsurancePlansManager();

    @Test
    void getInsurancePlans_success() {
        insurancePlansManager.addPlan(new BasicPlan());
        insurancePlansManager.addPlan(new TravelPlan());

        ArrayList<InsurancePlan> correctPlans = new ArrayList<>();
        correctPlans.add(new BasicPlan());
        correctPlans.add(new TravelPlan());

        assertEquals(correctPlans, insurancePlansManager.getInsurancePlans());
    }

    @Test
    void addPlan_success() {
        insurancePlansManager.addPlan(new BasicPlan());
        assertEquals(insurancePlansManager.getInsurancePlans().get(0), new BasicPlan());
    }

    @Test
    void deletePlan() {
        insurancePlansManager.addPlan(new BasicPlan());
        insurancePlansManager.addPlan(new TravelPlan());
        insurancePlansManager.deletePlan(new BasicPlan());
        assertEquals(insurancePlansManager.getInsurancePlans().get(0), new TravelPlan());
    }

    @Test
    void checkIfPlanNotOwnedWhileAdding_unownedPlan_success() {
        try {
            insurancePlansManager.checkIfPlanNotOwned(new BasicPlan());
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    void checkIfPlanNotOwnedWhileAdding_ownedPlan_failure() {
        try {
            insurancePlansManager.addPlan(new BasicPlan());
            insurancePlansManager.checkIfPlanNotOwned(new BasicPlan());
            fail();
        } catch (ParseException e) {
            assertEquals(e.getMessage(), InsurancePlansManager.DUPLICATE_PLAN_DETECTED_MESSAGE);
        }
    }

    @Test
    void checkIfPlanOwnedWhileRemoving_ownedPlan_success() {
        try {
            insurancePlansManager.addPlan(new BasicPlan());
            insurancePlansManager.checkIfPlanOwned(new BasicPlan());
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    void checkIfPlanOwnedWhileRemoving_unownedPlan_failure() {
        try {
            insurancePlansManager.checkIfPlanOwned(new BasicPlan());
            fail();
        } catch (ParseException e) {
            assertEquals(e.getMessage(), InsurancePlansManager.PLAN_NOT_DETECTED_MESSAGE);
        }
    }

    @Test
    void testToString() {
        insurancePlansManager.addPlan(new BasicPlan());
        assertEquals(insurancePlansManager.toString(), new BasicPlan().toString());
    }
}
