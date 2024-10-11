package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEHANDLE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TeleHandleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TelegramHandle;

/**
 * Parses input arguments and creates a new TeleHandleCommand object.
 */
public class TeleHandleCommandParser implements Parser<TeleHandleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TeleHandleCommand
     * and returns a TeleHandleCommand object for execution
     * @param args
     * @return new TeleHandleCommand
     * @throws ParseException is the user input does not conform to the expected format
     */
    public TeleHandleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TELEHANDLE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TeleHandleCommand.MESSAGE_USAGE), ive);
        }

        String teleHandle = argMultimap.getValue(PREFIX_TELEHANDLE).orElse("");

        return new TeleHandleCommand(index, new TelegramHandle(teleHandle));


    }
}
