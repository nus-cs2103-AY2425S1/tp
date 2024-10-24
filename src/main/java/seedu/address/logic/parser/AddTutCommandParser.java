package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUT_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddTutCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.TutorialId;
import seedu.address.model.tut.TutName;
import seedu.address.model.tut.Tutorial;

/**
 * Parses input arguments and creates a new AddTutCommand object.
 */
public class AddTutCommandParser implements Parser<AddTutCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTutCommand
     * and returns an AddTutCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddTutCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TUT_NAME, PREFIX_TUT_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_TUT_NAME, PREFIX_TUT_ID) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTutCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUT_NAME, PREFIX_TUT_ID);
        TutName tutName = ParserUtil.parseTutName(argMultimap.getValue(PREFIX_TUT_NAME).get());
        TutorialId id = ParserUtil.parseTutorialId(argMultimap.getValue(PREFIX_TUT_ID).get());

        Tutorial tutorial = Tutorial.of(tutName, id);


        return new AddTutCommand(tutorial);
    }

    /**
     * Returns true if none of the prefixes contain empty {@code Optional} values in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
