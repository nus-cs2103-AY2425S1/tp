package seedu.address.model.client.insurance;

public class InvalidInsurancePlan extends InsurancePlan {
    public InvalidInsurancePlan() {
        super();
        this.insurancePlanId = - 1;
    }
}
