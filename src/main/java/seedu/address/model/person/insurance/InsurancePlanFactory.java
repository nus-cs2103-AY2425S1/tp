package seedu.address.model.person.insurance;

import seedu.address.logic.parser.exceptions.ParseException;

public class InsurancePlanFactory {

    public static final String INVALID_PLAN_ID_MESSAGE = "This plan id is invalid!";

    public static InsurancePlan createInsurancePlan(int insurancePlanId) throws ParseException {
        if (insurancePlanId == 0) {
            return new BasicPlan();
        } else if (insurancePlanId == 1) {
            return new TravelPlan();
        } else {
            throw new ParseException(INVALID_PLAN_ID_MESSAGE);
        }
    }

}
