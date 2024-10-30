package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_INDEX;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimList;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.claim.EditClaimDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;

public class EditClaimCommand extends Command {

    public static final String COMMAND_WORD = "edit-claim";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits a claim from the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_POLICY_TYPE + "POLICY_TYPE "
            + PREFIX_CLAIM_INDEX + "CLAIM_INDEX (must be a positive integer) "
            + PREFIX_CLAIM_STATUS + "NEW_STATUS "
            + PREFIX_CLAIM_DESC + "NEW_DESCRIPTION\n"
            + "Note: Use the 'list-claims' command to find the appropriate claim index "
            + "for the specified policy type.\n\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_POLICY_TYPE + "health "
            + PREFIX_CLAIM_INDEX + "1 "
            + PREFIX_CLAIM_STATUS + "approved "
            + PREFIX_CLAIM_DESC + "Updated claim details.\n";
    public static final String MESSAGE_NOT_EDITED = "At least one field needs to be updated";
    public static final String MESSAGE_EDIT_CLAIM_SUCCESS = "Claim edited for policy type '%1$s' of person: %2$s.\n\n"
            + "Updated Claim Details:\nStatus: %3$s | Description: %4$s.";
    public static final String MESSAGE_NO_CLAIM_FOUND = "No claim found at the specified index to edit.";
    public static final String MESSAGE_INVALID_PERSON_INDEX = "The person index provided is invalid.";
    public static final String MESSAGE_NO_POLICY_OF_TYPE = "No policy of type '%1$s' found for person: %2$s";
    public static final String MESSAGE_DUPLICATE_CLAIM = "This claim already exists in the policy.";
    public static final String MESSAGE_NOT_CHANGED = "This claim has not been changed";

    private final Index personIndex;
    private final PolicyType policyType;
    private final Index claimIndex;
    private final EditClaimDescriptor editClaimDescriptor;

    public EditClaimCommand(Index personIndex, PolicyType policyType, Index claimIndex,
                            EditClaimDescriptor editClaimDescriptor) {
        requireAllNonNull(personIndex, policyType, claimIndex, editClaimDescriptor);
        this.personIndex = personIndex;
        this.policyType = policyType;
        this.claimIndex = claimIndex;
        this.editClaimDescriptor = editClaimDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToEdit = getPersonFromList(model, personIndex);
        Set<Policy> policySet = personToEdit.getPolicies();

        Policy policy = getPolicyFromPerson(personToEdit, policyType);

        Claim oldClaim = getOldClaim(policy, claimIndex);
        Claim editedClaim = createEditedClaim(oldClaim, editClaimDescriptor);

        PolicySet updatedPolicySet = new PolicySet();
        updatedPolicySet.addAll(policySet);
        updatedPolicySet.remove(policy.getType());

        Policy editedPolicy = createEditedPolicy(policy, oldClaim, editedClaim);
        updatedPolicySet.add(editedPolicy);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), updatedPolicySet);

        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_EDIT_CLAIM_SUCCESS, policyType, personToEdit.getName(),
                editedClaim.getStatus(), editedClaim.getClaimDescription()));
    }

    private Person getPersonFromList(Model model, Index personIndex) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_INDEX);
        }
        return lastShownList.get(personIndex.getZeroBased());
    }

    private Policy getPolicyFromPerson(Person person, PolicyType policyType) throws CommandException {
        return person.getPolicies().stream()
                .filter(x -> x.getType().equals(policyType))
                .findFirst()
                .orElseThrow(() -> new CommandException(
                        String.format(MESSAGE_NO_POLICY_OF_TYPE, policyType, person.getName())));
    }

    public Claim getOldClaim(Policy policy, Index claimIndex) throws CommandException {
        try {
            return policy.getClaimList().getList().get(claimIndex.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_NO_CLAIM_FOUND);
        }
    }

    public Policy createEditedPolicy(Policy policy, Claim oldClaim, Claim newClaim) throws CommandException {
        ClaimList updatedClaims = new ClaimList();
        updatedClaims.addAll(policy.getClaimList());

        if (newClaim.equals(oldClaim)) {
            throw new CommandException(MESSAGE_NOT_CHANGED);
        }

        for (Claim claim : updatedClaims) {
            if (claim.equals(newClaim) && !claim.equals(oldClaim)) {
                throw new CommandException(MESSAGE_DUPLICATE_CLAIM);
            }
        }

        int oldClaimIndex = updatedClaims.indexOf(oldClaim);
        if (oldClaimIndex != -1) {
            // Replace the old claim with the new claim
            updatedClaims.set(oldClaimIndex, newClaim);
        } else {
            throw new CommandException(MESSAGE_NO_CLAIM_FOUND);
        }

        return Policy.makePolicy(
                policy.getType(), policy.getPremiumAmount(), policy.getCoverageAmount(), policy.getExpiryDate(), updatedClaims);
    }

    public Claim createEditedClaim(Claim claim, EditClaimDescriptor descriptor) {
        ClaimStatus updatedStatus = descriptor.getStatus().orElse(claim.getStatus());
        String updatedDesc = descriptor.getDescription().orElse(claim.getClaimDescription());

        return new Claim(updatedStatus, updatedDesc);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof EditClaimCommand
                && personIndex.equals(((EditClaimCommand) other).personIndex)
                && policyType.equals(((EditClaimCommand) other).policyType)
                && claimIndex.equals(((EditClaimCommand) other).claimIndex)
                && editClaimDescriptor.equals(((EditClaimCommand) other).editClaimDescriptor));
    }
}
