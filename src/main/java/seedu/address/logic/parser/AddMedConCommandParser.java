package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDCON;

import seedu.address.logic.commands.AddMedConCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;
import seedu.address.model.person.MedCon;

/**
 * Parses user input for the {@link AddMedConCommand} and creates a new instance of it.
 */
public class AddMedConCommandParser implements Parser<AddMedConCommand> {
    /**
     * Parses the given arguments string and creates a {@link AddMedConCommand} object.
     *
     * @param args the arguments string containing user input.
     * @return A {@link AddMedConCommand} object containing the parsed NRIC and medical conditon.
     * @throws ParseException if the user input does not conform to the expected format or
     *         if the NRIC or medical condition is not provided.
     */
    public AddMedConCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NRIC, PREFIX_MEDCON);
        System.out.println(trimmedArgs);
        if (!argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddMedConCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_MEDCON).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddMedConCommand.MESSAGE_USAGE));
        }

        String nricStr = argMultimap.getValue(PREFIX_NRIC).orElse("");
        Nric nric = ParserUtil.parseNric(nricStr);
        String medConStr = argMultimap.getValue(PREFIX_MEDCON).orElse("");
        MedCon medCon = ParserUtil.parseMedCon(medConStr);

        return new AddMedConCommand(nric, medCon);
    }
}
