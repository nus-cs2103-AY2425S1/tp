package seedu.address.logic.parser;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddCompanyCommand;
import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.AddJobCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {

        String entity = args.trim().split(" ")[0];
        String addArgs = args.replace(" " + entity, "");

        switch (entity) {
        case AddContactCommand.COMMAND_ENTITY:
            return new AddContactCommandParser().parse(addArgs);
        case AddJobCommand.COMMAND_ENTITY:
            return new AddJobCommandParser().parse(addArgs);
        case AddCompanyCommand.COMMAND_ENTITY:
            return new AddCompanyCommandParser().parse(addArgs);
        default:
            throw new ParseException(AddCommand.MESSAGE_USAGE);
        }

    }

}
