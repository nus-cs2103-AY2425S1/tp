package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_HELP_PROMPT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_INDEX_OR_NAME;
import static seedu.address.logic.Messages.MESSAGE_MULTIPLE_WAYS_FORBIDDEN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Name;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    public final static String MESSAGE_END_PART = "Command format:\n"
            + DeleteCommand.MESSAGE_COMMAND_FORMAT
            + String.format(MESSAGE_HELP_PROMPT,
            HelpCommand.COMMAND_WORD + " " + DeleteCommand.COMMAND_WORD);

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String preamble = argMultimap.getPreamble().trim();
            if (!preamble.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_MULTIPLE_WAYS_FORBIDDEN,
                        DeleteCommand.COMMAND_WORD));
            }
            String str = argMultimap.getValue(PREFIX_NAME).get();

            try {
                Name name = ParserUtil.parseName(str);
                return new DeleteCommand(name);
            } catch (Exception ex) {
                throw ex; // why it became successfull
                //throw new ParseException(MESSAGE_CONTACT_NOT_IN_ADDRESS_BOOK,  ex);
                // did not use the 2 parameter exception
            }
        }

        assert argMultimap.getValue(PREFIX_NAME).isEmpty();

        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) { // String.format()
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    "Missing index or Full name. " + MESSAGE_END_PART)); // to check if
            // message correct
            // format
        }

        if (trimmedArgs.matches("^[0-9]+$")) {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);
        }

        try {
            Name name = ParserUtil.parseName(trimmedArgs);
            return new DeleteCommand(name);
        } catch (Exception exp) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_INVALID_INDEX_OR_NAME
                            + String.format(MESSAGE_MULTIPLE_WAYS_FORBIDDEN, DeleteCommand.COMMAND_WORD)));
                // did not use the 2 parameter exception
        }
    }
}
