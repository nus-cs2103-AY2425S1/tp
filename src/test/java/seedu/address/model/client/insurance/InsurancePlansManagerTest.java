package seedu.address.model.client.insurance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.model.client.insurance.InsurancePlanFactory.createInsurancePlan;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.client.exceptions.InsurancePlanException;
import seedu.address.model.client.insurance.claim.Claim;
import seedu.address.model.client.insurance.claim.ClaimStub;

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
        } catch (InsurancePlanException e) {
            fail();
        }
    }

    @Test
    void checkIfPlanNotOwnedWhileAdding_ownedPlan_failure() {
        try {
            insurancePlansManager.addPlan(new BasicPlan());
            insurancePlansManager.checkIfPlanNotOwned(new BasicPlan());
            fail();
        } catch (InsurancePlanException e) {
            assertEquals(e.getMessage(), InsurancePlansManager.DUPLICATE_PLAN_DETECTED_MESSAGE);
        }
    }

    @Test
    void checkIfPlanOwnedWhileRemoving_ownedPlan_success() {
        try {
            insurancePlansManager.addPlan(new BasicPlan());
            insurancePlansManager.checkIfPlanOwned(new BasicPlan());
        } catch (InsurancePlanException e) {
            fail();
        }
    }

    @Test
    void checkIfPlanOwnedWhileRemoving_unownedPlan_failure() {
        try {
            insurancePlansManager.checkIfPlanOwned(new BasicPlan());
            fail();
        } catch (InsurancePlanException e) {
            assertEquals(e.getMessage(), InsurancePlansManager.PLAN_NOT_DETECTED_MESSAGE);
        }
    }

    @Test
    void checkIfAccessPlansWorks_emptyPlans_success() {
        String testNoPlansString = insurancePlansManager.accessClaims();
        assertEquals(testNoPlansString, InsurancePlansManager.NO_INSURANCE_PLANS_MESSAGE);
    }

    @Test
    void checkIfAccessPlansWorks_onePlanNoClaims_success() {
        InsurancePlan stubPlan = new BasicPlan();
        insurancePlansManager.addPlan(stubPlan);
        String expectedMessage = stubPlan.toString() + "\n" + InsurancePlansManager.NO_CLAIMS_MESSAGE + "\n";
        assertEquals(expectedMessage, insurancePlansManager.accessClaims());
    }

    @Test
    void checkIfAccessPlansWorks_onePlanOneClaim_success() {
        InsurancePlan stubPlan = new BasicPlan();
        Claim stubClaim = new ClaimStub();
        stubPlan.addClaim(stubClaim);
        insurancePlansManager.addPlan(stubPlan);
        String expectedMessage = stubPlan.toString() + "\n" + "1. " + stubClaim.toString() + "\n";
        assertEquals(expectedMessage, insurancePlansManager.accessClaims());
    }

    // The following 3 test cases were created using chatgpt with minor edits.
    @Test
    public void equals_identicalObjects_returnsTrue() {
        // Arrange
        InsurancePlansManager manager1 = new InsurancePlansManager();
        InsurancePlansManager manager2 = new InsurancePlansManager();

        // Act and Assert
        assertEquals(manager1, manager2);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        // Arrange
        InsurancePlansManager manager = new InsurancePlansManager();

        // Act and Assert
        assertEquals(manager, new InsurancePlansManager());
    }

    @Test
    public void equals_differentObjects_returnsFalse() throws InsurancePlanException {
        // Arrange
        InsurancePlansManager manager1 = new InsurancePlansManager();
        InsurancePlansManager manager2 = new InsurancePlansManager();

        // Adding different plans to each manager
        InsurancePlan plan1 = createInsurancePlan(0);
        InsurancePlan plan2 = createInsurancePlan(1);

        manager1.addPlan(plan1);
        manager2.addPlan(plan2);

        // Act and Assert
        assertNotEquals(manager1, manager2);
    }


    @Test
    void testToString() {
        insurancePlansManager.addPlan(new BasicPlan());
        assertEquals(insurancePlansManager.toString(), new BasicPlan().toString());
    }
}
