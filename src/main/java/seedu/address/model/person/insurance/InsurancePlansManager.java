package seedu.address.model.person.insurance;

import static seedu.address.model.person.insurance.InsurancePlanFactory.createInsurancePlan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.insurance.claim.Claim;

/**
 * The {@code InsurancePlansManager} class represents a list manager of InsurancePlans that a client has purchased.
 * This class provides methods to add and remove insurance plans.
 */
public class InsurancePlansManager {

    public static final String DUPLICATE_PLAN_DETECTED_MESSAGE = "This plan with id: %1$s, "
            + "has already been added to this client: %2$s";
    public static final String PLAN_NOT_DETECTED_MESSAGE = "This plan with id: %1$s, "
            + "has not been added to this client: %2$s";
    public static final String DUPLICATE_CLAIM_ID_MESSAGE = "This claim with id: %1$s "
            + "has already been added to this client: %2$s";

    private final ArrayList<InsurancePlan> insurancePlans;
    private final HashSet<String> claimIds;

    /**
     * Constructs an empty InsurancePlans list.
     */
    public InsurancePlansManager() {
        this.insurancePlans = new ArrayList<>();
        this.claimIds = new HashSet<>();
    }

    /**
     * Constructs an {@code InsurancePlansManager} by initializing it with a string representing saved insurance plans.
     *
     * @param insurancePlansString the string representing saved insurance plans. If no insurance plans have been
     *                             added, it should be "No added plans".
     * @throws ParseException if the string cannot be parsed into valid insurance plans.
     * @throws AssertionError if the insurancePlansString is an empty string or contains only whitespace.
     */
    public InsurancePlansManager(String insurancePlansString) throws ParseException {
        this();
        assert !insurancePlansString.trim().isEmpty() : "Saved insurance plans string must not be an empty string. "
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
        if (insurancePlans.isEmpty()) {
            return;
        }
        for (InsurancePlan p : insurancePlans) {
            if (p.equals(plan)) {
                throw new ParseException(DUPLICATE_PLAN_DETECTED_MESSAGE);
            }
        }
    }

    /**
     * Adds a claim to the insurance plan of the client.
     *
     * @param insurancePlan The insurance plan the claim is to be added to.
     * @param claim         The claim that is to be added to the insurance plan.
     * @throws ParseException if the claimId already exists.
     */
    public void addClaimToInsurancePlan(InsurancePlan insurancePlan, Claim claim) throws ParseException {
        if (this.claimIds.contains(claim.getClaimId())) {
            throw new ParseException(DUPLICATE_CLAIM_ID_MESSAGE);
        }
        for (InsurancePlan p : insurancePlans) {
            if (p.equals(insurancePlan)) {
                p.claims.add(claim);
                this.claimIds.add(claim.getClaimId());
            }
        }
    }

    /**
     * Converts all claims into a string to be saved in JSON file.
     */
    public String convertClaimsToJson() {
        StringBuilder claimsStringBuilder = new StringBuilder();
        for (InsurancePlan p : this.insurancePlans) {
            for (Claim c : p.claims) {
                claimsStringBuilder.append(p.toString())
                        .append("|")
                        .append(c.getClaimId())
                        .append("|")
                        .append(c.getOpen())
                        .append("|")
                        .append(c.getClaimAmount())
                        .append(",");
            }
        }

        if (!claimsStringBuilder.isEmpty()) {
            claimsStringBuilder.deleteCharAt(claimsStringBuilder.length() - 1);
        } else {
            claimsStringBuilder.append("No claims yet.");
        }

        return claimsStringBuilder.toString();
    }

    /**
     * Adds all the claims string from JSON file into their respective insurance plans.
     *
     * @param claimsJson String obtained from JSON file of all the claims.
     * @throws ParseException if there is an error parsing the data from the JSON file.
     */
    public void addAllClaimsFromJson(String claimsJson) throws ParseException {
        String[] claimsString = claimsJson.split(",");

        if (Objects.equals(claimsString[0], "No claims yet.")) {
            return;
        }

        for (String claim : claimsString) {
            String[] claimAttributes = claim.split("\\|");

            InsurancePlan insurancePlan = createInsurancePlan(claimAttributes[0]);
            checkIfPlanOwned(insurancePlan);

            String claimId = claimAttributes[1];
            boolean isOpen = Boolean.parseBoolean(claimAttributes[2]);
            int claimAmount = Integer.parseInt(claimAttributes[3]);

            Claim claimToBeAdded = new Claim(claimId, isOpen, claimAmount);

            this.addClaimToInsurancePlan(insurancePlan, claimToBeAdded);
        }
    }

    /**
     * Returns the number of open claims for all the insurance plans owned by the client.
     */
    public int getNumberOfOpenClaims() {
        int count = 0;
        for (InsurancePlan p : this.insurancePlans) {
            for (Claim c : p.claims) {
                if (c.getOpen()) {
                    count++;
                }
            }
        }
        return count;
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
