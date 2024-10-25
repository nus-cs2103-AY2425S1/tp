package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.address.logic.commands.UnmarkPresentAllCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.TutorialGroup;

/**
 * Parses input arguments and creates a new UnmarkPresentAllCommand object.
 */
public class UnmarkPresentAllCommandParser implements Parser<UnmarkPresentAllCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkPresentAllCommand
     * and returns a UnmarkPresentAllCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmarkPresentAllCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL_GROUP, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_TUTORIAL_GROUP, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnmarkPresentAllCommand.MESSAGE_USAGE));
        }

        TutorialGroup tutorialGroup = ParserUtil.parseTutorialGroup(argMultimap.getValue(PREFIX_TUTORIAL_GROUP).get());
        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

        return new UnmarkPresentAllCommand(tutorialGroup, date);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
