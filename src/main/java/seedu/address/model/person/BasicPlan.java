package seedu.address.model.person;

public class BasicPlan extends InsurancePlan{
    private int insurancePlanId = 0;

    public BasicPlan() {
    }

    @Override
    public String toString() {
        return "Basic Insurance Plan";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InsurancePlan) // instanceof handles nulls
                && insurancePlanId == ((InsurancePlan) other).getInsurancePlanId(); // check plan id
    }
}
