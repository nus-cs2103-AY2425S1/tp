package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.ArgumentPredicateToFail;
import seedu.address.model.person.ClientStatus;

/**
 * Lists out all whitelisted clients
 */
public class WhitelistListCommand extends WhitelistCommand {

    /**
     * Instantiates a {@code WhitelistListCommand} object.
     *
     * @param argPredToFail an argument predicate that should fail
     */
    public WhitelistListCommand() {
        // this is irrelevant but necessary
        super(Index.fromZeroBased(0), new ClientStatus("old"));

        // the main predicate
        // this.argPredToFail = argPredToFail;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredPersonList(ArgumentPredicateToFail.getNotBlacklistedPredicate());

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
