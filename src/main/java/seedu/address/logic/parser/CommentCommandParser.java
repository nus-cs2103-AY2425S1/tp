package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Comment;

/**
 * Parses input arguments and creates a new CommentCommand object.
 */
public class CommentCommandParser implements Parser<CommentCommand> {

    @Override
    public CommentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_COMMENT);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CommentCommand.MESSAGE_USAGE), ive);
        }
        String comment = argMultimap.getValue(PREFIX_COMMENT).orElse("");

        return new CommentCommand(index, new Comment(comment));
    }
}
