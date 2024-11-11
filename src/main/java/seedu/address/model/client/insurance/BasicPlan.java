package seedu.address.model.client.insurance;

/**
 * The {@code BasicPlan} class represents a basic type of insurance plan.
 * It extends the {@code InsurancePlan} abstract class and provides specific implementations
 * for certain behaviors, such as returning a string representation of the plan.
 */
public class BasicPlan extends InsurancePlan {

    /**
     * Default constructor for the {@code BasicPlan} class.
     * Initializes a new instance of the basic insurance plan with default values.
     */
    public BasicPlan() {
        this.insurancePlanId = 0;
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
