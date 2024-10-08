package seedu.address.model.person.insurance;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * The {@code InsurancePlan} abstract class represents a general blueprint for an insurance plan.
 * It defines common behaviors that can be shared across all types of insurance plans.
 * This class is meant to be extended by specific insurance plan implementations.
 */
public abstract class InsurancePlan {

    public static final String INVALID_PLAN_ID_MESSAGE = "The insurance plan id is not valid";

    /** Unique identifier for the insurance plan, to be determined in each class separately. */
    protected int insurancePlanId = -1;

    /**
     * Retrieves the insurance plan's unique identifier.
     *
     * @return the insurance plan's ID
     */
    public int getInsurancePlanId() {
        return insurancePlanId;
    }


    /**
     * Checks if the insurance plan id exists within the current plans.
     *
     * @param insurancePlanId the integer representing the id of the plan to be selected by the system.
     * @throws IllegalValueException thrown if the id does not exist within the correct range.
     */
    public static void checkValidPlan(int insurancePlanId) throws IllegalValueException {
        int validMinId = 0;
        int validMaxId = 1;
        if (insurancePlanId < validMinId || insurancePlanId > validMaxId) {
            throw new IllegalValueException(INVALID_PLAN_ID_MESSAGE);
        }
    }

    /**
     * Returns a string representation of the insurance plan.
     *
     * @return a string that represents the insurance plan
     */
    @Override
    public String toString() {
        return "Insurance Plan";
    }

    /**
     * Compares this insurance plan to another object for equality.
     * Two insurance plans are considered equal if they are the same object
     * or if they are both instances of {@code InsurancePlan} and share the same insurance plan ID.
     *
     * @param other the object to be compared for equality
     * @return {@code true} if the specified object is equal to this insurance plan, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InsurancePlan) // instanceof handles nulls
                && insurancePlanId == ((InsurancePlan) other).getInsurancePlanId(); // check plan id
    }
}
