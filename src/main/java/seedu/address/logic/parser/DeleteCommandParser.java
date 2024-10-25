package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Module;
import seedu.address.model.person.StudentId;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String trimmedArg = args.trim();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE);
        argMultimap.verifyNoDuplicateStudentId(args);

        if (trimmedArg.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        String studentIdString = argMultimap.getPreamble(); // Assuming preamble is used for ID
        StudentId studentId = ParserUtil.parseStudentId(studentIdString);

        if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
            String moduleName = argMultimap.getValue(PREFIX_MODULE).get();
            Module module = ParserUtil.parseModule(moduleName);
            return new DeleteCommand(studentId, module);
        }
        return new DeleteCommand(studentId);
    }
}
