package tahub.contacts.logic.parser.person;

import static java.util.Objects.requireNonNull;
import static tahub.contacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_MATRICULATION_NUMBER;

import java.util.stream.Stream;

import tahub.contacts.logic.commands.person.PersonDeleteCommand;
import tahub.contacts.logic.parser.ArgumentMultimap;
import tahub.contacts.logic.parser.ArgumentTokenizer;
import tahub.contacts.logic.parser.Parser;
import tahub.contacts.logic.parser.ParserUtil;
import tahub.contacts.logic.parser.Prefix;
import tahub.contacts.logic.parser.exceptions.ParseException;
import tahub.contacts.model.person.MatriculationNumber;

/**
 * Parses input arguments and creates a new PersonDeleteCommand object
 */
public class PersonDeleteCommandParser implements Parser<PersonDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PersonDeleteCommand
     * and returns a PersonDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PersonDeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MATRICULATION_NUMBER);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MATRICULATION_NUMBER);

        if (!arePrefixesPresent(argMultimap, PREFIX_MATRICULATION_NUMBER) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PersonDeleteCommand.MESSAGE_USAGE));
        }

        assert argMultimap.getValue(PREFIX_MATRICULATION_NUMBER).isPresent();

        MatriculationNumber personMatricNumberToDelete = ParserUtil
                .parseMatriculationNumber(argMultimap.getValue(PREFIX_MATRICULATION_NUMBER).get());
        return new PersonDeleteCommand(personMatricNumberToDelete);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
