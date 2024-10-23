package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.stream.Stream;

import seedu.address.logic.commands.ModuleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Module;
import seedu.address.model.person.StudentId;

/**
 * Parses input arguments and creates a new ModuleCommand object
 */
public class ModuleCommandParser implements Parser<ModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ModuleCommand
     * and returns a ModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE);
        String preamble = argMultimap.getPreamble();

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MODULE);
        argMultimap.verifyNoDuplicateStudentId(args);
        StudentId studentId = ParserUtil.parseStudentId(preamble);
        Module module = ParserUtil.parseModule(argMultimap.getValue(PREFIX_MODULE).get());

        return new ModuleCommand(studentId, module);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
