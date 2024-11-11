package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddAllergyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new AddAllergyCommand object
 */
public class AddAllergyCommandParser implements Parser<AddAllergyCommand> {
    private static final Logger logger = LogsCenter.getLogger(AddCommandParser.class);
    /**
     * Parses the given arguments string and creates an {@link AddAllergyCommand} object.
     *
     * @param args the arguments string containing user input.
     * @return An {@Code AddAllergyCommand} object containing the parsed NRIC and set of allergies.
     * @throws ParseException if the user input does not conform to the expected format or
     *         if the NRIC is not provided.
     */
    public AddAllergyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_ALLERGY);

        // Check if NRIC and allergy is provided
        if (!argMultimap.arePrefixesPresent(PREFIX_NRIC, PREFIX_ALLERGY) || !argMultimap.getPreamble().isEmpty()) {
            logger.warning("Unexpected preamble detected.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAllergyCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NRIC);

        // Parse NRIC
        String nricStr = argMultimap.getValue(PREFIX_NRIC).get();
        Nric nric = ParserUtil.parseNric(nricStr);

        // Parse all Allergy values and add them to a set
        Set<Allergy> allergies = new HashSet<>();
        for (String allergyStr : argMultimap.getAllValues(PREFIX_ALLERGY)) {
            Allergy allergy = ParserUtil.parseAllergy(allergyStr);
            allergies.add(allergy);
        }

        return new AddAllergyCommand(nric, allergies);
    }
}
