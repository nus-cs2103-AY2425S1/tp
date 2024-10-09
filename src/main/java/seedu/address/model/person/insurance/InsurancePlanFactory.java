package seedu.address.model.person.insurance;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Factory class to create the different types of Insurance Plans based on their insuranceId.
 */
public class InsurancePlanFactory {

    /** Error message when a insuranceId passed in is invalid. */
    public static final String INVALID_PLAN_ID_MESSAGE = "This Insurance Plan ID is invalid!";

    /**
     * Creates InsurancePlan Object based on the given insurance id,
     * @param insurancePlanId an integer representing the insurance plan that the user wants to create.
     * @return InsurancePlan Object that the user wants to create.
     * @throws ParseException if the insuranceId is not a valid input (Insurance plan does not exist).
     */
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
