package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;

import java.util.Arrays;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameMatchesKeywordPredicate;

/**
 * Parses input arguments and creates a new AssignCommand object
 */
public class AssignCommandParser implements Parser<AssignCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignCommand
     * and returns a AssignCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AssignCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Check for invalid format where prefix immediately follows number
        String trimmedArgs = args.trim();
        if (trimmedArgs.matches("\\d+" + PREFIX_ROLE.getPrefix() + ".*")
                || trimmedArgs.matches("\\d+" + PREFIX_WEDDING.getPrefix() + ".*")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ROLE, PREFIX_WEDDING);

        Index personIndex = null;
        NameMatchesKeywordPredicate predicate = null;

        try {
            String target = argMultimap.getPreamble();

            if (target.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
            }

            // Determine if the target is an personIndex or a name
            if (isNumeric(target)) {
                personIndex = ParserUtil.parseIndex(target);
            } else {
                String[] nameKeywords = target.split("\\s+");
                predicate = new NameMatchesKeywordPredicate(Arrays.asList(nameKeywords));
            }

        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ROLE);

        boolean isAssignRole = argMultimap.getValue(PREFIX_ROLE).isPresent();
        boolean isAssignWedding = !argMultimap.getAllValues(PREFIX_WEDDING).isEmpty();

        AssignCommand.PersonWithRoleDescriptor personWithRoleDescriptor = new AssignCommand.PersonWithRoleDescriptor();

        if (!isAssignRole && !isAssignWedding) {
            // no role and wedding to assign
            throw new ParseException(AssignCommand.MESSAGE_MISSING_FIELDS);

        } else if (isAssignRole & !isAssignWedding) {
            // assign role only
            String roleValue = argMultimap.getValue(PREFIX_ROLE).get();
            personWithRoleDescriptor.setRole(ParserUtil.parseRole(roleValue));

            return new AssignCommand(personIndex, predicate, personWithRoleDescriptor, null);
        } else if (isAssignWedding & !isAssignRole) {
            // assign to wedding only
            Set<Index> weddingIndices = ParserUtil.parseWeddingJobs(argMultimap.getAllValues(PREFIX_WEDDING));
            personWithRoleDescriptor.setRole(null);
            return new AssignCommand(personIndex, predicate, personWithRoleDescriptor, weddingIndices);
        } else {
            // assign role and wedding
            String roleValue = argMultimap.getValue(PREFIX_ROLE).get();
            personWithRoleDescriptor.setRole(ParserUtil.parseRole(roleValue));
            Set<Index> weddingIndices = ParserUtil.parseWeddingJobs(argMultimap.getAllValues(PREFIX_WEDDING));
            return new AssignCommand(personIndex, predicate, personWithRoleDescriptor, weddingIndices);
        }
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
