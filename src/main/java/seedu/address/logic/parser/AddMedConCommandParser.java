package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDCON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.AddMedConCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MedCon;
import seedu.address.model.person.Nric;

/**
 * Parses user input for the {@link AddMedConCommand} and creates a new instance of it.
 */
public class AddMedConCommandParser implements Parser<AddMedConCommand> {
    /**
     * Parses the given arguments string and creates a {@link AddMedConCommand} object.
     *
     * @param args the arguments string containing user input.
     * @return A {@link AddMedConCommand} object containing the parsed NRIC and set of medical conditions.
     * @throws ParseException if the user input does not conform to the expected format or
     *         if the NRIC is not provided.
     */
    public AddMedConCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_MEDCON);

        // Check if NRIC is provided
        if (!argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedConCommand.MESSAGE_USAGE));
        }

        // Parse NRIC
        String nricStr = argMultimap.getValue(PREFIX_NRIC).get();
        Nric nric = ParserUtil.parseNric(nricStr);

        // Parse all MedCon values and add them to a set
        Set<MedCon> medCons = new HashSet<>();
        for (String medConStr : argMultimap.getAllValues(PREFIX_MEDCON)) {
            if (medConStr.length() > 45) {
                throw new ParseException("Length of medical condition exceeds 45 characters.");
            }
            medCons.add(new MedCon(medConStr));
        }

        return new AddMedConCommand(nric, medCons);

    }
}
