package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRIAGE;

import seedu.address.logic.commands.TriageCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Triage;

/**
 * Parses user input to create a TriageCommand.
 */
public class TriageCommandParser implements Parser<TriageCommand> {

    /**
     * Parses the given arguments to create a TriageCommand.
     *
     * @param args The arguments provided by the user.
     * @return A TriageCommand that contains the parsed NRIC and triage level.
     * @throws ParseException If the user input does not adhere to the expected format.
     */
    public TriageCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Tokenize the arguments using the defined PREFIX_TRIAGE
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TRIAGE);

        Nric nric;
        Triage triage;

        try {
            // Parse the NRIC from the preamble (the part before any option flags)
            nric = ParserUtil.parseNric(argMultimap.getPreamble());
            // Parse the triage level, defaulting to an empty string if not present
            triage = ParserUtil.parseTriage(argMultimap.getValue(PREFIX_TRIAGE).orElse(""));
        } catch (ParseException pe) {
            // If parsing fails, throw a ParseException with a detailed message
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TriageCommand.MESSAGE_USAGE), pe);
        }

        // Return a new TriageCommand with the parsed NRIC and triage level
        return new TriageCommand(nric, triage);
    }
}
