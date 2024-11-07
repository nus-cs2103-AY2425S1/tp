package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.person.Subject.MESSAGE_CONSTRAINTS;
import static seedu.address.model.person.Subject.isValidSubject;

import seedu.address.logic.commands.FindSubjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonHaveSubjectPredicate;

/**
 * Parses input arguments and creates a new FindSubjectCommand object
 */
public class FindSubjectCommandParser implements Parser<FindSubjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindSubjectCommand
     * and returns a FindSubjectCommand object for execution.
     * @throws seedu.address.logic.parser.exceptions.ParseException if the user input is not a valid subject
     */
    @Override
    public FindSubjectCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSubjectCommand.MESSAGE_USAGE)
            );
        }
        if (!isValidSubject(trimmedArgs)) {
            throw new ParseException(MESSAGE_CONSTRAINTS);
        }
        String subjectToFind = trimmedArgs;
        return new FindSubjectCommand(new PersonHaveSubjectPredicate(subjectToFind));
    }
}
