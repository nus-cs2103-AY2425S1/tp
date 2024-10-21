package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnmarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Tutorial;

/**
 * Parses input arguments and creates a new MarkCommand object
 */
public class UnmarkCommandParser implements Parser<UnmarkCommand> {

    @Override
    public UnmarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_TUTORIAL);

        Index index;
        Tutorial tutorial;
        try {
            String indexStr = argMultimap.getPreamble();
            tutorial = new Tutorial(argMultimap.getValue(PREFIX_TUTORIAL).orElse(""));
            if (indexStr.equals(ParserUtil.WILDCARD)) {
                return new UnmarkCommand(true, tutorial);
            } else {
                index = ParserUtil.parseIndex(indexStr);
            }
        } catch (ParseException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnmarkCommand.MESSAGE_USAGE), ive);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }


        return new UnmarkCommand(index, tutorial);
    }
}
