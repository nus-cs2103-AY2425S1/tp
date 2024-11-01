package seedu.address.model.client.insurance;

/**
 * Represents an instance of an invalid insurance plan for testing.
 */
public class InvalidInsurancePlan extends InsurancePlan {

    /**
     * Constructs an Insurance Plan with an invalid id.
     */
    public InvalidInsurancePlan() {
        super();
        this.insurancePlanId = -1;
    }
}
