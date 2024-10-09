package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class MarkCommandParser implements Parser<MarkCommand> {

    @Override
    public MarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_TUTORIAL);

        Index index;
        int tutorial;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            tutorial = Integer.parseInt(argMultimap.getValue(PREFIX_TUTORIAL).orElse(""));
        } catch (ParseException | NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkCommand.MESSAGE_USAGE), e);
        }

        return new MarkCommand(index, tutorial);
    }
}
