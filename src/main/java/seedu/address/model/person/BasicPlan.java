package seedu.address.model.person;

/**
 * The {@code BasicPlan} class represents a basic type of insurance plan.
 * It extends the {@code InsurancePlan} abstract class and provides specific implementations
 * for certain behaviors, such as returning a string representation of the plan.
 */
public class BasicPlan extends InsurancePlan {

    /** Unique identifier for the basic insurance plan. Initialized to 0 by default. */
    private int insurancePlanId = 0;

    /**
     * Default constructor for the {@code BasicPlan} class.
     * Initializes a new instance of the basic insurance plan with default values.
     */
    public BasicPlan() {
    }

    /**
     * Returns a string representation of the basic insurance plan.
     *
     * @return a string that represents the basic insurance plan
     */
    @Override
    public String toString() {
        return "Basic Insurance Plan";
    }
}
