package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

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
    public CommandResult execute(Model model) {
        requireNonNull(model);

        try {
            // make the find command do the hard work
            return (new FindCommandParser().parse("find cs/blacklisted")).execute(model);
        } catch (ParseException pe) {
            return null; // this will never happen
        }
    }
}
