package seedu.address.logic.parser.contact.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.parseRole;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.contact.commands.FindRoleCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicates.PersonIsRolePredicate;
import seedu.address.model.role.Role;


/**
 * Parses input arguments and creates a new FindRoleCommand object
 */
public class FindRoleCommandParser implements Parser<FindRoleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindRoleCommand
     * and returns a FindRoleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindRoleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindRoleCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        Set<Role> roles = new HashSet<>();

        for (String keyword : nameKeywords) {
            roles.add(parseRole(keyword));
        }

        return new FindRoleCommand(new PersonIsRolePredicate(new ArrayList<>(roles)));
    }

}
