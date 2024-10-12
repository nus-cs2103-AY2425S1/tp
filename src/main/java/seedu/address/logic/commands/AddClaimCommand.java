package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE_ID;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a claim to an existing person with existing Insurance Plan in the address book.
 */
public class AddClaimCommand extends Command {
    public static final String COMMAND_WORD = "addClaim";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an open claim to the insurance plan of the client identified "
            + "by their client id. \n"
            + "Parameters: INDEX (must be a positive integer), "
            + " INSURANCE_PLAN_ID (must be a valid ID), "
            + " Claim_ID (must be a valid ID), "
            + " Claim amount (must be a valid integer, convert from dollars and cents to just cents) \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_INSURANCE_ID + " 0 "
            + PREFIX_CLAIM_ID + " B1234 "
            + PREFIX_CLAIM_AMOUNT + " 10000";

    public static final String MESSAGE_SUCCESS =
            "New claim added to Client: %1$s, under Insurance plan %2$s, with Claim ID: %3$s, Claim Amount: %4$d";

    public final Index index;
    private final int insuranceId;
    private final String claimID;
    private final int claimAmount;

    /**
     * Constructs an AddClaimCommand object with the values passed in by the user.
     *
     * @param index       of the person in the filtered person list to add the insurance plan to.
     * @param insuranceId of insurance plan the claim is to be added to.
     * @param claimID     the claimID received when a claim is created through official channels.
     * @param claimAmount the amount that is being claimed through this claim in cents.
     */
    public AddClaimCommand(Index index, int insuranceId, final String claimID, final int claimAmount) {
        this.index = index;
        this.insuranceId = insuranceId;
        this.claimID = claimID;
        this.claimAmount = claimAmount;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        throw new CommandException(String.format(MESSAGE_SUCCESS, Messages.format(personToEdit),
                this.insuranceId, this.claimID, this.claimAmount));
    }
}
