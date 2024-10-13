package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyExpiryDatePredicate;
import seedu.address.model.policy.PolicyMap;

/**
 * Lists all policies in the address book that are nearing expiry within the next 30 days.
 */
public class ListExpiringPoliciesCommand extends Command {

    /**
     * Command word to trigger this command in the application's UI.
     */
    public static final String COMMAND_WORD = "listExpiringPolicies";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all policies nearing expiry within the next 30 days.\n"
            + "Example: " + COMMAND_WORD;

    private static final String MESSAGE_SUCCESS = "The following policies are near expiry:";
    private static final String MESSAGE_NO_EXPIRING_POLICY = "No policies expiring within the next 30 days!";
    private static final String MESSAGE_FAILURE = "Failed to retrieve expiring policies. Please try again.";
    private static final String MESSAGE_POLICY_LISTED_DETAILS = "Insuree phone: %1$s, Policy Type: %2$s, "
            + "Premium Amount: %3$s, Coverage Amount: %4$s, Expiry Date: %5$s";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            LocalDate currentDate = LocalDate.now();

            // Create a predicate to filter policies expiring within the next 30 days
            PolicyExpiryDatePredicate predicate = new PolicyExpiryDatePredicate(currentDate);

            // Retrieve all persons from the model
            List<Person> persons = model.getFilteredPersonList();

            // StringBuilder to hold the result message
            StringBuilder resultMessage = new StringBuilder(MESSAGE_SUCCESS);

            boolean hasExpiringPolicies = false;

            // Iterate over all persons and their policies
            for (Person person : persons) {
                PolicyMap policyMap = person.getPolicyMap();

                // Filter the policies based on expiry date predicate
                for (Policy policy : policyMap.getAllPolicies()) {
                    if (predicate.test(policy)) {
                        // At least one expiring policy exists
                        hasExpiringPolicies = true;

                        // Append the policy details to the result message
                        resultMessage.append(String.format(
                                MESSAGE_POLICY_LISTED_DETAILS,
                                person.getPhone().toString(),
                                policy.getType().toString(),
                                policy.getPremiumAmount(),
                                policy.getCoverageAmount(),
                                policy.getExpiryDate().toLocalDate()
                        ));
                    }
                }
            }

            // If no expiring policies were found
            if (!hasExpiringPolicies) {
                return new CommandResult(MESSAGE_NO_EXPIRING_POLICY);
            }

            // Return the result message if expiring policies were found
            return new CommandResult(resultMessage.toString());

        } catch (Exception e) {
            // Handle any unexpected errors and return a failure message
            return new CommandResult(MESSAGE_FAILURE);
        }
    }




    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListExpiringPoliciesCommand)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}

