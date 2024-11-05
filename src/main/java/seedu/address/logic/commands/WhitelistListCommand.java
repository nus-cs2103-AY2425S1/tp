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
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // here, the predicate dictates that the client status should be blacklisted;
        // hence if the predicate fails (ensured by the ArgumentPredicateToFail object),
        // the client is not blacklisted => they are whitelisted
        model.updateFilteredPersonList(ArgumentPredicateToFail.PREDICATE_NOT_BLACKLISTED);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
