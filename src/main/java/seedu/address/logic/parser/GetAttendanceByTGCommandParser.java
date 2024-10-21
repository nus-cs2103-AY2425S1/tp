package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import seedu.address.logic.commands.GetAttendanceByTGCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.TutorialGroup;

import java.util.stream.Stream;

/**
 * Parses input arguments and creates a new GetAttendanceByTGCommand object
 */
public class GetAttendanceByTGCommandParser implements Parser<GetAttendanceByTGCommand> {

    @Override
    public GetAttendanceByTGCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL_GROUP);

        if (!arePrefixesPresent(argMultimap, PREFIX_TUTORIAL_GROUP) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(GetAttendanceByTGCommand.MESSAGE_USAGE));
        }

        TutorialGroup tutorialGroup = ParserUtil.parseTutorialGroup(argMultimap.getValue(PREFIX_TUTORIAL_GROUP).get());

        return new GetAttendanceByTGCommand(tutorialGroup);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
