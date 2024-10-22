package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Tutorial;

/**
 * Parses input arguments and creates a new MarkCommand object
 */
public class MarkCommandParser implements Parser<MarkCommand> {

    @Override
    public MarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_TUTORIAL);

        if (!argMultimap.arePrefixesPresent(PREFIX_TUTORIAL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIAL);
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        List<Tutorial> tutorials = ParserUtil.parseTutorials(argMultimap.getValue(PREFIX_TUTORIAL).get());

        return new MarkCommand(index, tutorials);
    }
}
