package seedu.address.model.client.insurance;

import seedu.address.logic.Messages;
import seedu.address.model.client.exceptions.InsurancePlanException;

/**
 * Factory class to create the different types of Insurance Plans based on their insuranceId.
 */
public class InsurancePlanFactory {

    /**
     * Creates InsurancePlan Object based on the given insurance id.
     *
     * @param insurancePlanId an integer representing the insurance plan that the user wants to create.
     * @return InsurancePlan Object that the user wants to create.
     * @throws InsurancePlanException if the insuranceId is not a valid input (Insurance plan does not exist).
     */
    public static InsurancePlan createInsurancePlan(int insurancePlanId) throws InsurancePlanException {
        if (insurancePlanId == 0) {
            return new BasicPlan();
        } else if (insurancePlanId == 1) {
            return new TravelPlan();
        } else {
            throw new InsurancePlanException(Messages.MESSAGE_INVALID_INSURANCE_ID);
        }
    }

    /**
     * Creates InsurancePlan Object based on the given insurance plan name.
     *
     * @param insurancePlanName a string representing the insurance plan that the user wants to create.
     * @return InsurancePlan Object that the user wants to create.
     * @throws InsurancePlanException if the insuranceId is not a valid input (Insurance plan does not exist).
     */
    public static InsurancePlan createInsurancePlan(String insurancePlanName) throws InsurancePlanException {
        if (insurancePlanName.equals("Basic Insurance Plan")) {
            return new BasicPlan();
        } else if (insurancePlanName.equals("Travel Insurance Plan")) {
            return new TravelPlan();
        } else {
            throw new InsurancePlanException(Messages.MESSAGE_INVALID_INSURANCE_NAME);
        }
    }
}
