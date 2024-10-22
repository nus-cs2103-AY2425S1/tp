package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_CONTACT;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;

import java.util.List;
import java.util.Set;


public class AssignContactToWeddingCommand extends Command {

    public static final String ASSIGN_COMMAND = "assign";

    public static final String MESSAGE_USAGE = ASSIGN_COMMAND + ": Assigns contacts to a specific wedding "
            + "where the wedding and contacts are identified by their index number. \n"
            + "Parameters: assign WeddingIndex (must be a positive integer) "
            + PREFIX_ASSIGN_CONTACT + "(specify at least 1 person index to assign)... \n"
            + "Example: " + ASSIGN_COMMAND + " 1 "
            + PREFIX_ASSIGN_CONTACT + "1 2 3";

    public static final String MESSAGE_ASSIGN_TO_WEDDING_SUCCESS = "Assigned the following persons to %1$s's wedding: %2$s";

    private final Index targetWeddingIndex;
    private final Set<Index> assignedPersonIndexList;

    public AssignContactToWeddingCommand(Index targetWeddingIndex, Set<Index> assignedPersonIndexList) {
        this.targetWeddingIndex = targetWeddingIndex;
        this.assignedPersonIndexList = assignedPersonIndexList;
    }



    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Wedding> lastShownWeddingList = model.getFilteredWeddingList();

        if (targetWeddingIndex.getZeroBased() >= lastShownWeddingList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX);
        }

        Wedding weddingToModify = lastShownWeddingList.get(targetWeddingIndex.getZeroBased());


        return null;
    }
}
