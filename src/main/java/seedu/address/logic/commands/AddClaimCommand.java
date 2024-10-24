package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimSet;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;

/**
 * Adds a claim to a person in the address book.
 */
public class AddClaimCommand extends Command {

    public static final String COMMAND_WORD = "add-claim";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a claim to the person identified "
            + "by the index number used in the displayed person list. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_POLICY_TYPE + "POLICY_TYPE " + PREFIX_CLAIM_STATUS + "CLAIM_STATUS "
            + PREFIX_CLAIM_DESC + "CLAIM_DESCRIPTION\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_POLICY_TYPE + "health "
            + PREFIX_CLAIM_STATUS + "PENDING "
            + PREFIX_CLAIM_DESC + "stomach surgery";

    public static final String MESSAGE_ADD_CLAIM_SUCCESS = "Added Claim to Person: %1$s";
    public static final String MESSAGE_POLICY_NOT_FOUND = "The policy for the specified type was not found.";
    public static final String MESSAGE_CLAIM_EXISTS = "A similar claim already exists in the policy.";

    private final Index index;
    private final Claim claim;
    private final PolicyType policyType;

    /**
     * Constructs an {@code AddClaimCommand} to add the specified {@code Claim} to a person.
     *
     * @param index      The index of the person in the filtered person list to whom the claim will be added.
     * @param claim      The claim to add to the person.
     * @param policyType The type of the policy to which the claim will be added.
     */
    public AddClaimCommand(Index index, Claim claim, PolicyType policyType) {
        requireNonNull(index);
        requireNonNull(claim);
        requireNonNull(policyType);
        this.index = index;
        this.claim = claim;
        this.policyType = policyType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Set<Policy> policySet = personToEdit.getPolicies();

        // find the policy based on the policyType
        Policy policy = policySet.stream()
                .filter(p -> p.getType().equals(policyType))
                .findFirst()
                .orElseThrow(() -> new CommandException(MESSAGE_POLICY_NOT_FOUND));

        ClaimSet claimSet = policy.getClaimSet();
        if (!claimSet.add(claim)) {
            throw new CommandException(MESSAGE_CLAIM_EXISTS);
        }

        // create new policy set with the updated policy (to preserve immutability)
        PolicySet updatedPolicySet = new PolicySet();
        updatedPolicySet.addAll(new HashSet<>(policySet));
        updatedPolicySet.remove(policy.getType());
        updatedPolicySet.add(policy);

        // create a new person with the updated policy set
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getTags(), updatedPolicySet);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_ADD_CLAIM_SUCCESS, claim));

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddClaimCommand)) {
            return false;
        }

        AddClaimCommand otherCommand = (AddClaimCommand) other;
        return index.equals(otherCommand.index)
                && policyType.equals(otherCommand.policyType)
                && claim.equals(otherCommand.claim);
    }
}
