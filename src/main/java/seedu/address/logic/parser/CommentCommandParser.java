package seedu.address.logic.parser;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Comment;

/**
 * Parses input arguments and creates a new {@code CommentCommand} object
 */
public class CommentCommandParser implements Parser<CommentCommand> {

    /**
     * Parses the given input arguments and creates a {@code CommentCommand} object.
     * The input is expected to contain a valid index and a comment prefixed by {@code PREFIX_COMMENT}.
     *
     * @param args The user input string containing the index and comment.
     * @return A {@code CommentCommand} object with the specified index and comment.
     * @throws ParseException If the input format is incorrect or required prefixes are missing.
     */
    public CommentCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COMMENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_COMMENT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CommentCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COMMENT);

        Index index;
        index = ParserUtil.parseIndex(argMultimap.getPreamble());
        String comment = argMultimap.getValue(PREFIX_COMMENT).orElse("");
        return new CommentCommand(index, new Comment(comment));
    }

    /**
     * Checks if all the specified prefixes are present in the given {@code ArgumentMultimap}.
     *
     * @param argumentMultimap The {@code ArgumentMultimap} containing the parsed arguments.
     * @param prefixes The prefixes to check for presence.
     * @return {@code true} if all specified prefixes are present; {@code false} otherwise.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
