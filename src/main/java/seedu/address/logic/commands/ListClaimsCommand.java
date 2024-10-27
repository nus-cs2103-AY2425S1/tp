package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.claim.Claim;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyType;

/**
 * Lists all claims of a specified policy type for a person in the address book.
 * If there are no claims under the specified policy type, or if the person index is invalid,
 * an appropriate message will be shown.
 */
public class ListClaimsCommand extends Command {

    public static final String COMMAND_WORD = "list-claims";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all claims under the specified policy type "
            + "for the person identified by the index number used in the displayed person list. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_POLICY_TYPE + "POLICY_TYPE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_POLICY_TYPE + "health";

    public static final String MESSAGE_LIST_CLAIMS_SUCCESS = "Claims listed for policy type '%1$s' of person: "
            + "%2$s\n%3$s";
    public static final String MESSAGE_NO_CLAIMS = "No claims found for policy type '%1$s' of person: %2$s";
    public static final String MESSAGE_INVALID_PERSON_INDEX = "The person index provided is invalid.";
    public static final String MESSAGE_NO_POLICY_OF_TYPE = "No policy of type '%1$s' found for person: %2$s";

    private final Index personIndex;
    private final PolicyType policyType;

    /**
     * Creates a ListClaimsCommand to list claims for a specific policy type
     * associated with a specified person in the address book.
     *
     * @param personIndex The index of the person in the filtered person list.
     * @param policyType The type of the policy whose claims are to be listed.
     */
    public ListClaimsCommand(Index personIndex, PolicyType policyType) {
        this.personIndex = personIndex;
        this.policyType = policyType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_INDEX);
        }

        Person person = lastShownList.get(personIndex.getZeroBased());

        // find the policy of the specified type
        Optional<Policy> policyOptional = person.getPolicies().stream()
                .filter(policy -> policy.getType().equals(policyType))
                .findFirst();

        if (policyOptional.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_POLICY_OF_TYPE, policyType, person.getName()));
        }

        // get the claims from the policy
        List<Claim> claims = policyOptional.get().getClaimList().getList();

        if (claims.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_CLAIMS, policyType, person.getName()));
        }

        String claimsString = claims.stream()
                .map(Claim::toString)
                .collect(Collectors.joining("\n"));

        return new CommandResult(String.format(MESSAGE_LIST_CLAIMS_SUCCESS, policyType, person.getName(),
                claimsString));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ListClaimsCommand)) {
            return false;
        }
        ListClaimsCommand otherCommand = (ListClaimsCommand) other;
        return personIndex.equals(otherCommand.personIndex)
                && policyType.equals(otherCommand.policyType);
    }
}
