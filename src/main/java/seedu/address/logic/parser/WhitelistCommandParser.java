package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.WhitelistCommand;
import seedu.address.logic.commands.WhitelistListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ClientStatus;

/**
 * Parses input arguments and creates a new WhitelistCommand object
 */
public class WhitelistCommandParser implements Parser<WhitelistCommand> {

    /**
     * Checks if the new client status is valid.
     * The new client status must be 'old', 'active' or 'potential',
     * it cannot be 'blacklisted' (despite it being a valid client status).
     * {@code clientStatus} will already be validated.
     */
    public boolean isValidNewClientStatus(ClientStatus clientStatus) {
        return !clientStatus.isBlacklisted();
    }

    /**
     * Parses the given {@code String} of arguments in the context of the WhitelistCommand
     * and returns a WhitelistCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public WhitelistCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CLIENT_STATUS);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLIENT_STATUS);

        Index index;
        ClientStatus newClientStatus;
        String preamble = argMultimap.getPreamble();

        try {
            index = ParserUtil.parseIndex(preamble);
        } catch (ParseException pe) {
            if (preamble.equals("") && !argMultimap.getValue(PREFIX_CLIENT_STATUS).isPresent()) {
                return new WhitelistListCommand();
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, WhitelistCommand.MESSAGE_USAGE), pe);
            }
        }

        if (argMultimap.getValue(PREFIX_CLIENT_STATUS).isPresent()) {
            newClientStatus = ParserUtil.parseClientStatus(argMultimap.getValue(PREFIX_CLIENT_STATUS).get());

            if (isValidNewClientStatus(newClientStatus)) {
                return new WhitelistCommand(index, newClientStatus);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, WhitelistCommand.MESSAGE_USAGE));
            }
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, WhitelistCommand.MESSAGE_USAGE));
        }
    }
}
