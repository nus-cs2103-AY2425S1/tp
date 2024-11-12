package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.GradeCommand.MESSAGE_MISMATCH_MODULE_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * Parses input arguments and creates a new GradeCommand object
 */
public class GradeCommandParser implements Parser<GradeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the GradeCommand
     * and returns an GradeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GradeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_GRADE);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradeCommand.MESSAGE_USAGE), pe);
        }

        List<String> moduleNames = argMultimap.getAllValues(PREFIX_MODULE);
        List<String> gradeStrings = argMultimap.getAllValues(PREFIX_GRADE);

        if (moduleNames.isEmpty() || gradeStrings.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradeCommand.MESSAGE_USAGE));
        }

        if (moduleNames.size() != gradeStrings.size()) {
            throw new ParseException(String.format(MESSAGE_MISMATCH_MODULE_GRADE));
        }

        Map<String, Integer> moduleGrades = new LinkedHashMap<>();
        for (int i = 0; i < moduleNames.size(); i++) {
            String moduleName = moduleNames.get(i);
            int grade = ParserUtil.parseGrade(gradeStrings.get(i));
            moduleGrades.put(moduleName, grade);
        }

        return new GradeCommand(index, moduleGrades);
    }
}
