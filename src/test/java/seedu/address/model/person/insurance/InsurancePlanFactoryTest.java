package seedu.address.model.person.insurance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class InsurancePlanFactoryTest {

    @Test
    void createInsurancePlanById_success() {
        try {
            InsurancePlan insurancePlan = InsurancePlanFactory.createInsurancePlan(0);
            BasicPlan basicPlan = new BasicPlan();
            assertEquals(insurancePlan, basicPlan);
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    void createInsurancePlanById_invalidId() {
        try {
            InsurancePlan insurancePlan = InsurancePlanFactory.createInsurancePlan(-1);
            fail();
        } catch (ParseException e) {
            assertEquals(e.getMessage(), InsurancePlanFactory.INVALID_PLAN_ID_MESSAGE);
        }
    }

    @Test
    void createInsurancePlanByString_success() {
        try {
            InsurancePlan insurancePlan = InsurancePlanFactory.createInsurancePlan("Basic Insurance Plan");
            BasicPlan basicPlan = new BasicPlan();
            assertEquals(insurancePlan, basicPlan);
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    void createInsurancePlanByString_invalidString() {
        try {
            InsurancePlan insurancePlan = InsurancePlanFactory.createInsurancePlan("Invalid String");
            fail();
        } catch (ParseException e) {
            assertEquals(e.getMessage(), InsurancePlanFactory.INVALID_PLAN_NAME_MESSAGE);
        }
    }

    @Test
    void createInsurancePlanByString_emptyString() {
        try {
            InsurancePlan insurancePlan = InsurancePlanFactory.createInsurancePlan("");
            fail();
        } catch (ParseException e) {
            assertEquals(e.getMessage(), InsurancePlanFactory.INVALID_PLAN_NAME_MESSAGE);
        }
    }

    // the following test case was created based on an idea suggested by ChatGPT.
    @Test
    void createInsurancePlanByString_nullString() {
        assertThrows(NullPointerException.class, () -> {
            InsurancePlanFactory.createInsurancePlan(null);
        });
    }
}
