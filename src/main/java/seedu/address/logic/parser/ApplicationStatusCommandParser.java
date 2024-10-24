package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICATION_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ApplicationStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.company.ApplicationStatus;

/**
 * Parses input arguments and creates a new ApplicationStatusCommand object
 */
public class ApplicationStatusCommandParser implements Parser<ApplicationStatusCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ApplicationStatusCommand
     * and returns a ApplicationStatusCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ApplicationStatusCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_APPLICATION_STATUS);

        Index index;
        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (IllegalValueException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ApplicationStatusCommand.MESSAGE_USAGE), pe);
        }
        String status = argumentMultimap.getValue(PREFIX_APPLICATION_STATUS).orElse("");

        return new ApplicationStatusCommand(index, new ApplicationStatus(status));
    }

}
