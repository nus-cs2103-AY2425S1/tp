package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyExpiryDatePredicate;

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

    private static final String MESSAGE_SUCCESS = "The following policies are near expiry:\n\n";
    private static final String MESSAGE_NO_EXPIRING_POLICY = "No policies expiring within the next 30 days!";
    private static final String MESSAGE_FAILURE = "Failed to retrieve expiring policies. Please try again.";
    private static final String MESSAGE_POLICY_LISTED_DETAILS = "Insuree name: %1$s   |   Insuree phone: %2$s\n"
            + "Policy Type: %3$s   |   Premium Amount: %4$.2f\nCoverage Amount: %5$.2f   |   Expiry Date: %6$s\n\n";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            LocalDate currentDate = LocalDate.now();

            PolicyExpiryDatePredicate predicate = new PolicyExpiryDatePredicate(currentDate);

            List<Person> persons = model.getFilteredPersonList();

            StringBuilder resultMessage = new StringBuilder(MESSAGE_SUCCESS);

            boolean hasExpiringPolicies = false;

            for (Person person : persons) {
                Set<Policy> policies = person.getPolicies();

                // Filter the policies based on expiry date predicate
                for (Policy policy : policies) {
                    if (predicate.test(policy)) {
                        hasExpiringPolicies = true;

                        resultMessage.append(String.format(
                                MESSAGE_POLICY_LISTED_DETAILS,
                                person.getName().toString(),
                                person.getPhone().toString(),
                                policy.getType().toString(),
                                policy.getPremiumAmount(),
                                policy.getCoverageAmount(),
                                policy.getExpiryDate()
                        ));
                    }
                }
            }

            // no expiring policies were found
            if (!hasExpiringPolicies) {
                return new CommandResult(MESSAGE_NO_EXPIRING_POLICY);
            }

            // expiring policies found
            return new CommandResult(resultMessage.toString());

        } catch (Exception e) {
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

