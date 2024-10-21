package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddAllergyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.allergy.Allergy;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new AddAllergyCommand object
 */
public class AddAllergyCommandParser implements Parser<AddAllergyCommand> {
    private static final Logger logger = LogsCenter.getLogger(AddCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the AddAllergyCommand
     * and returns an AddAllergyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddAllergyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_ALLERGY);

        if (!arePrefixesPresent(argMultimap, PREFIX_NRIC, PREFIX_ALLERGY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAllergyCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NRIC);

        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
        Set<Allergy> allergies = ParserUtil.parseAllergies(argMultimap.getAllValues(PREFIX_ALLERGY));

        logger.info("Successfully parsed nric for AddAllergyCommand: " + nric);
        logger.info("Successfully parsed allergies for AddAllergyCommand: " + allergies);

        return new AddAllergyCommand(nric, allergies);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
