package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ArgumentPredicate;

/**
 * Lists out all blacklisted clients
 */
public class BlacklistListCommand extends BlacklistCommand {

    /**
     * Instantiates a {@code BlacklistListCommand} object.
     */
    public BlacklistListCommand() {
        super(Index.fromZeroBased(0)); // this is irrelevant but necessary
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        boolean isArchivedList = model.getIsArchivedList();

        // Allow user to use only if currently viewing the main list
        if (isArchivedList) {
            throw new CommandException(Messages.MESSAGE_NOT_IN_MAIN_LIST);
        }
        // here, the predicate dictates that the client status should be blacklisted
        model.updateFilteredPersonList(ArgumentPredicate.PREDICATE_BLACKLISTED);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
