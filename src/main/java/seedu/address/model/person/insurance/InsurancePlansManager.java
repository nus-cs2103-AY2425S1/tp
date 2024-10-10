package seedu.address.model.person.insurance;

import static seedu.address.model.person.insurance.InsurancePlanFactory.createInsurancePlan;

import java.util.ArrayList;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The {@code InsurancePlansManager} class represents a list manager of InsurancePlans that a client has purchased.
 * This class provides methods to add and remove insurance plans.
 */
public class InsurancePlansManager {

    public static final String DUPLICATE_PLAN_DETECTED_MESSAGE = "This plan with id: %1$s, "
            + "has already been added to this client: %2$s";
    public static final String PLAN_NOT_DETECTED_MESSAGE = "This plan with id: %1$s, "
            + "has not been added to this client: %2$s";

    private final ArrayList<InsurancePlan> insurancePlans;

    /**
     * Constructs an empty InsurancePlans list.
     */
    public InsurancePlansManager() {
        this.insurancePlans = new ArrayList<>();
    }

    /**
     * Constructs an {@code InsurancePlansManager} by initializing it with a string representing saved insurance plans.
     *
     * @param insurancePlansString the string representing saved insurance plans. If no insurance plans have been
     *                             added, it should be "No added plans".
     * @throws ParseException if the string cannot be parsed into valid insurance plans.
     * @throws AssertionError if the insurancePlansString is an empty string or contains only whitespace.
     */
    public InsurancePlansManager(String insurancePlansString) throws ParseException{
        this();
        assert !insurancePlansString.trim().isEmpty() :
                "Saved insurance plans string must not be an empty string. "
                        + "If no insurance plans have been added, it will be \"No added plans\" ";

        if (!insurancePlansString.equals("No added plans")) {
            String[] planNames = insurancePlansString.split(", ");
            for (String planName : planNames) {
                InsurancePlan planToBeAdded = createInsurancePlan(planName);
                addPlan(planToBeAdded);
            }
        }
    }

    /**
     * Returns a list of insurance plans currently owned by the client.
     */
    public ArrayList<InsurancePlan> getInsurancePlans() {
        return this.insurancePlans;
    }

    /**
     * Adds a new insurance plan to the client's list of insurance plans.
     *
     * @param plan The insurance plan to add.
     */
    public void addPlan(InsurancePlan plan) {
        insurancePlans.add(plan);
    }

    /**
     * Removes an insurance plan from the client's list of insurance plans.
     *
     * @param plan The insurance plan to remove.
     */
    public void deletePlan(InsurancePlan plan) {
        insurancePlans.remove(plan);
    }

    /**
     * Checks if the plan being queried is already owned by the client.
     *
     * @param plan Insurance plan to be checked.
     * @throws ParseException if the plan is not owned by the client.
     */
    public void checkIfPlanOwned(InsurancePlan plan) throws ParseException {
        for (InsurancePlan p : insurancePlans) {
            if (p.equals(plan)) {
                return;
            }
        }
        throw new ParseException(PLAN_NOT_DETECTED_MESSAGE);
    }

    /**
     * Checks if the plan being queried is not owned yet by the client.
     *
     * @param plan Insurance plan to be checked.
     * @throws ParseException if the plan is owned by the client.
     */
    public void checkIfPlanNotOwned(InsurancePlan plan) throws ParseException {
        for (InsurancePlan p : insurancePlans) {
            if (p.equals(plan)) {
                throw new ParseException(DUPLICATE_PLAN_DETECTED_MESSAGE);
            }
        }
    }

    /**
     * Returns a string representation of the insurance plans.
     * The string will contain the names of all insurance plans in the list.
     * If no plans exist, it returns "Insurance Plans: None".
     *
     * @return A string listing all the insurance plans or "None" if the list is empty.
     */
    @Override
    public String toString() {
        StringBuilder plans = new StringBuilder();
        for (InsurancePlan plan : insurancePlans) {
            plans.append(plan.toString()).append(", ");
        }

        if (insurancePlans.isEmpty()) {
            plans.append("No added plans");
        } else {
            plans.setLength(plans.length() - 2); // Remove trailing ", "
        }

        return plans.toString();
    }
}
