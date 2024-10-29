package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RoleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameMatchesKeywordPredicate;

/**
 * Parses input arguments and creates a new RoleCommand object
 */
public class RoleCommandParser implements Parser<RoleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RoleCommand
     * and returns a RoleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public RoleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ROLE);

        Index index = null;
        NameMatchesKeywordPredicate predicate = null;

        try {
            String target = argMultimap.getPreamble();

            if (target.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RoleCommand.MESSAGE_USAGE));
            }

            // Determine if the target is an index or a name
            if (isNumeric(target)) {
                index = ParserUtil.parseIndex(target);
            } else {
                String[] nameKeywords = target.split("\\s+");
                predicate = new NameMatchesKeywordPredicate(Arrays.asList(nameKeywords));
            }

        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RoleCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ROLE);

        RoleCommand.PersonWithRoleDescriptor personWithRoleDescriptor = new RoleCommand.PersonWithRoleDescriptor();

        // Check if role is provided and is non-empty, otherwise set it to Optional.empty()
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            String roleValue = argMultimap.getValue(PREFIX_ROLE).get();
            if (roleValue.isEmpty()) {
                personWithRoleDescriptor.setRole(Optional.empty());
            } else {
                personWithRoleDescriptor.setRole(ParserUtil.parseRole(roleValue));
            }
        } else {
            personWithRoleDescriptor.setRole(Optional.empty());
        }

        return new RoleCommand(index, predicate, personWithRoleDescriptor);
    }

    /**
     * Parses {@code Collection<String> roles} into a {@code Set<Role>} if {@code roles} is non-empty.
     * If {@code roles} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Role>} containing zero roles.
     */
    private boolean isNumeric(String str) {
        return str != null && str.matches("-?\\d+");
    }
}
