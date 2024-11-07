package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.wedding.Wedding;

/**
 * Represents a command to view the participants of a specified wedding.
 * This command is case-insensitive and expects the wedding names to be in the format of "NAME & NAME".
 */
public class ViewWeddingCommand extends Command {
    public static final String COMMAND_WORD = "view-wed";
    public static final String COMMAND_WORD_SHORT = "vw";
    public static final String COMMAND_FUNCTION = COMMAND_WORD_SHORT
            + ": Shows all persons involved in the weddings of the specified "
            + "keywords (case-insensitive).";

    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "Parameters: NAME & NAME\n"
            + "Example: " + COMMAND_WORD_SHORT + " Jonus & Izzat";
    public static final String MESSAGE_WEDDING_DOESNT_EXIST = "This wedding cannot be found.\n"
            + "Please make sure that the wedding is created and is in the format 'NAME & NAME'.\n"
            + "If you have not created a wedding yet, you can do so using the '"
            + AddWeddingCommand.COMMAND_WORD
            + "' command.";
    public static final String MESSAGE_NO_PARTICIPANTS_ADDED =
            String.format(Messages.MESSAGE_PARTICIPANTS_LISTED_OVERVIEW, 0)
            + "\n"
            + "You can add a participant to this wedding using the '"
            + TagAddCommand.COMMAND_WORD_SHORT
            + "' command.";

    private final TagContainsKeywordsPredicate predicate;

    public ViewWeddingCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Wedding> matchingWedding = model.getFilteredWeddingList()
                .stream()
                .filter(wedding -> wedding.getWeddingName()
                        .toString()
                        .equalsIgnoreCase(predicate.getWeddingKeywords()))
                .toList();

        if (matchingWedding.isEmpty()) {
            throw new CommandException(MESSAGE_WEDDING_DOESNT_EXIST);
        }

        model.updateFilteredPersonList(predicate);
        int numOfParticipants = model.getFilteredPersonList().size();

        if (numOfParticipants == 0) {
            throw new CommandException(MESSAGE_NO_PARTICIPANTS_ADDED);
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PARTICIPANTS_LISTED_OVERVIEW, numOfParticipants));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewWeddingCommand otherViewWeddingCommand)) {
            return false;
        }

        return predicate.equals(otherViewWeddingCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
