package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_POLICY_NAME;
import static seedu.address.logic.Messages.MESSAGE_SUCCESS_SEARCH_POLICY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEARCH_POLICY;

import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Searches for clients who have a specified policy.
 */
public class SearchPolicyCommand extends Command {
    public static final String COMMAND_WORD = "search " + PREFIX_SEARCH_POLICY;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Searches for clients who have the specified policy.\n"
            + "Parameters: POLICY_NAME\n"
            + "Example: " + COMMAND_WORD + " Health Insurance";

    private final String policyName;

    /**
     * Creates a {@code SearchPolicyCommand} to search for clients with the specified policy name.
     *
     * @param policyName The policy name used to search for clients.
     * @throws CommandException if the {@code policyName} is invalid.
     */
    public SearchPolicyCommand(String policyName) throws CommandException {
        requireNonNull(policyName);
        if (!isValidPolicyName(policyName)) {
            throw new CommandException(MESSAGE_INVALID_POLICY_NAME);
        }
        this.policyName = policyName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Predicate<Person> predicate = person -> personHasPolicy(person, policyName);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS_SEARCH_POLICY, policyName));
    }

    /**
     * Normalizes a string by removing all whitespace and converting it to lowercase.
     *
     * @param input The string to normalize.
     * @return The normalized string.
     */

    private String normalizeString(String input) {
        return input.replaceAll("\\s+", "").toLowerCase();
    }

    /**
     * Checks if the given person has the specified policy based on the policy name.
     *
     * @param person The person to check.
     * @param policyName The name of the policy.
     * @return True if the person has the policy, false otherwise.
     */
    private boolean personHasPolicy(Person person, String policyName) {
        String normalizedPolicyName = normalizeString(policyName);
        return person.getPolicies().stream()
                .anyMatch(policy -> normalizeString(policy.getPolicyName()).equalsIgnoreCase(normalizedPolicyName));
    }

    /**
     * Validates the policy name.
     *
     * @param policyName The policy name to validate.
     * @return True if the policy name is valid, false otherwise.
     */
    private boolean isValidPolicyName(String policyName) {
        if (policyName == null || policyName.trim().isEmpty()) {
            return false;
        }

        String regex = "^[a-zA-Z0-9 ]+$";
        return policyName.matches(regex);
    }

    @Override
    public boolean equals(Object other) {
        // This method made use of ChatGPT to ensure its correctness when comparing the Command object

        if (other == this) {
            return true;
        }

        if (!(other instanceof SearchPolicyCommand)) {
            return false;
        }

        SearchPolicyCommand otherCommand = (SearchPolicyCommand) other;
        return this.policyName.equalsIgnoreCase(otherCommand.policyName);
    }
}
