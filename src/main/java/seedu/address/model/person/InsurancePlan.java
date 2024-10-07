package seedu.address.model.person;

public abstract class InsurancePlan {
    private int insurancePlanId;

    public int getInsurancePlanId() { return insurancePlanId; }

    public String toString() {
        return "Insurance Plan";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InsurancePlan) // instanceof handles nulls
                && insurancePlanId == ((InsurancePlan) other).getInsurancePlanId(); // check plan id
    }
}
