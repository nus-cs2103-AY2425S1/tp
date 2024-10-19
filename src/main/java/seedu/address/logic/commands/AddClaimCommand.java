package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.claim.Claim;
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
    private final Index index;
    private final Claim claim;
    private PolicyType policyType;

    /**
     * Constructs an {@code AddClaimCommand} to add the specified {@code Claim} to a person.
     *
     * @param index      The index of the person in the filtered person list to whom the claim will be added.
     * @param claim      The claim to add to the person.
     * @param policyType
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
        throw new CommandException("still implementing");
        //        if (index.getZeroBased() >= model.getFilteredPersonList().size()) {
        //            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        //        }

        //return new CommandResult(String.format(MESSAGE_ADD_CLAIM_SUCCESS, claim));
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
