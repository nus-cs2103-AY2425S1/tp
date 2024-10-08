package seedu.address.model.person.insurance;

import java.util.ArrayList;
import java.util.List;

public class InsurancePlans {

    private final List<InsurancePlan> insurancePlans;

    /**
     * Constructs an empty InsurancePlans list.
     */
    public InsurancePlans() {
        this.insurancePlans = new ArrayList<>();
    }

    /**
     * Adds a new insurance plan to the list.
     * @param plan The insurance plan to add.
     */
    public void addPlan(InsurancePlan plan) {
        insurancePlans.add(plan);
    }

    /**
     * Removes an insurance plan from the list.
     * @param plan The insurance plan to remove.
     */
    public void deletePlan(InsurancePlan plan) {
        insurancePlans.remove(plan);
    }

    @Override
    public String toString() {
        StringBuilder plans = new StringBuilder("Insurance Plans: ");
        for (InsurancePlan plan : insurancePlans) {
            plans.append(plan.toString());
        }

        if (!insurancePlans.isEmpty()) {
            plans.append("None");
        } else {
            plans.setLength(plans.length() - 2);  // Remove trailing ", "
        }

        return plans.toString();
    }
}
