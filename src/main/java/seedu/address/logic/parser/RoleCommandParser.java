package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Collection;

import seedu.address.logic.commands.RoleCommand;
import seedu.address.logic.commands.RoleCommand.PersonWithRoleDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.role.Role;

/**
 * Parses input arguments and creates a new RoleCommand object
 */
public class RoleCommandParser implements Parser<RoleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RoleCommand
     * and returns an RoleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RoleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ROLE);

        Name name;
        try {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).orElseThrow());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RoleCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ROLE);

        PersonWithRoleDescriptor personWithRoleDescriptor = new PersonWithRoleDescriptor();


        Role role = parseRoleToAssign(argMultimap.getValue(PREFIX_ROLE).get());
        personWithRoleDescriptor.setRole(role);

        return new RoleCommand(name, personWithRoleDescriptor);
    }

    /**
     * Parses {@code Collection<String> roles} into a {@code Set<Role>} if {@code roles} is non-empty.
     * If {@code roles} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Role>} containing zero roles.
     */
    private Role parseRoleToAssign(String role) throws ParseException {

        return ParserUtil.parseRole(role);
    }

}
