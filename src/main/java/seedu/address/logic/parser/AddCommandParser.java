package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddCompanyCommand;
import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.AddJobCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand<?>> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns a subtype of AddCommand for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand<?> parse(String args) throws ParseException {

        String entity = args.trim().split(" ")[0];
        String addArgs = args.replace(" " + entity, "");

        switch (entity) {
        case AddContactCommand.ENTITY_WORD:
            return new AddContactCommandParser().parse(addArgs);
        case AddJobCommand.ENTITY_WORD:
            return new AddJobCommandParser().parse(addArgs);
        case AddCompanyCommand.ENTITY_WORD:
            return new AddCompanyCommandParser().parse(addArgs);
        default:
            throw new ParseException(AddCommand.MESSAGE_USAGE);
        }

    }

}
