package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;

/**
 * Deletes a claim from an existing policy in the address book.
 * This command allows the user to remove a specified claim from a policy type of a person identified by their index.
 * If no matching person, policy type, or claim is found, an appropriate error message is returned.
 */
public class DeleteClaimsCommand extends Command {

    public static final String COMMAND_WORD = "delete-claim";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a claim from the person identified by the index number used in the displayed person list. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_POLICY_TYPE + "POLICY_TYPE " + PREFIX_CLAIM_STATUS + "CLAIM_STATUS "
            + PREFIX_CLAIM_DESC + "CLAIM_DESCRIPTION\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_POLICY_TYPE + "health "
            + PREFIX_CLAIM_STATUS + "PENDING "
            + PREFIX_CLAIM_DESC + "Hospitalization";

    public static final String MESSAGE_DELETE_CLAIM_SUCCESS = "Claim deleted for policy type '%1$s' of person: %2$s\n\n"
            + "Deleted Claim Details:\nStatus: %3$s | Description: %4$s";
    public static final String MESSAGE_NO_CLAIM_FOUND = "No claim matching the specified status "
            + "and description was found.";
    public static final String MESSAGE_INVALID_PERSON_INDEX = "The person index provided is invalid.";
    public static final String MESSAGE_NO_POLICY_OF_TYPE = "No policy of type '%1$s' found for person: %2$s";

    private final Index personIndex;
    private final PolicyType policyType;
    private final ClaimStatus claimStatus;
    private final String claimDescription;

    /**
     * Creates a DeleteClaimsCommand to delete the specified claim.
     *
     * @param personIndex The index of the person in the filtered person list.
     * @param policyType The type of the policy whose claim is to be deleted.
     * @param claimStatus The status of the claim.
     * @param claimDescription The description of the claim.
     */
    public DeleteClaimsCommand(Index personIndex, PolicyType policyType, ClaimStatus claimStatus,
                               String claimDescription) {
        requireAllNonNull(personIndex, policyType, claimStatus, claimDescription);
        this.personIndex = personIndex;
        this.policyType = policyType;
        this.claimStatus = claimStatus;
        this.claimDescription = claimDescription;
    }

    /**
     * Executes the delete claim command by removing the specified claim from a person's policy.
     * Updates the model if successful, or throws a CommandException if the person, policy, or claim is invalid.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A CommandResult indicating the outcome of the command execution.
     * @throws CommandException If the index is invalid, the policy type is not found, or no matching claim found.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person person = getPersonFromModel(model);
        Policy policy = findPolicyByType(person, policyType);

        deleteClaimFromPolicy(policy);

        Person updatedPerson = createUpdatedPerson(person);
        updateModel(model, person, updatedPerson);

        String successMessage = formatSuccessMessage(person);
        return new CommandResult(successMessage);
    }

    /**
     * Retrieves the person from the model based on the provided index.
     *
     * @param model The model containing the list of persons.
     * @return The person at the specified index.
     * @throws CommandException If the person index is invalid.
     */
    private Person getPersonFromModel(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_INDEX);
        }
        return lastShownList.get(personIndex.getZeroBased());
    }

    /**
     * Deletes the specified claim from the given policy.
     *
     * @param policy The policy from which the claim is to be removed.
     * @throws CommandException If no matching claim is found.
     */
    private void deleteClaimFromPolicy(Policy policy) throws CommandException {
        Claim claimToDelete = new Claim(claimStatus, claimDescription);
        boolean claimRemoved = policy.removeClaim(claimToDelete);
        if (!claimRemoved) {
            throw new CommandException(MESSAGE_NO_CLAIM_FOUND);
        }
    }

    private Person createUpdatedPerson(Person person) {
        PolicySet updatedPolicySet = new PolicySet();
        updatedPolicySet.addAll(person.getPolicies());
        return new Person(person.getName(), person.getPhone(), person.getEmail(),
                person.getAddress(), person.getTags(), updatedPolicySet);
    }

    private void updateModel(Model model, Person originalPerson, Person updatedPerson) {
        model.setPerson(originalPerson, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Formats the success message after deleting a claim.
     *
     * @param person The person whose claim was deleted.
     * @return The formatted success message.
     */
    private String formatSuccessMessage(Person person) {
        return String.format(MESSAGE_DELETE_CLAIM_SUCCESS, policyType, person.getName(), claimStatus, claimDescription);
    }


    /**
     * Finds the policy of the specified type for the given person.
     *
     * @param person     The person whose policies are to be searched.
     * @param policyType The type of policy to find.
     * @return The policy of the specified type.
     * @throws CommandException If no policy of the specified type is found.
     */
    private Policy findPolicyByType(Person person, PolicyType policyType) throws CommandException {
        Optional<Policy> policyOptional = person.getPolicies().stream()
                .filter(policy -> policy.getType().equals(policyType))
                .findFirst();

        if (policyOptional.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_POLICY_OF_TYPE, policyType, person.getName()));
        }
        return policyOptional.get();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeleteClaimsCommand)) {
            return false;
        }
        DeleteClaimsCommand otherCommand = (DeleteClaimsCommand) other;
        return personIndex.equals(otherCommand.personIndex)
                && policyType.equals(otherCommand.policyType)
                && claimStatus.equals(otherCommand.claimStatus)
                && claimDescription.equalsIgnoreCase(otherCommand.claimDescription);
    }
}
