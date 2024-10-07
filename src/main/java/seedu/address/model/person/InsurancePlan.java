package seedu.address.model.person;

public abstract class InsurancePlan {
    private int insurancePlanId;
    private int monthlyPremium;

    public int getInsurancePlanId() { return insurancePlanId; }

    public int getMonthlyPremium() { return monthlyPremium; }

}
