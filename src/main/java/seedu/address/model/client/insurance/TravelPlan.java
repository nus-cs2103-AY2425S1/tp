package seedu.address.model.client.insurance;

/**
 * The {@code TravelPlan} class represents a specific type of insurance plan tailored for travel purposes.
 * It extends the {@code InsurancePlan} abstract class and provides custom behavior for travel-related insurance.
 */
public class TravelPlan extends InsurancePlan {

    /**
     * Default constructor for the {@code TravelPlan} class.
     * Initializes a new instance of the travel insurance plan with default values.
     */
    public TravelPlan() {
        this.insurancePlanId = 1;
    }

    /**
     * Returns a string representation of the travel insurance plan.
     *
     * @return a string that represents the travel insurance plan
     */
    @Override
    public String toString() {
        return "Travel Insurance Plan";
    }
}

