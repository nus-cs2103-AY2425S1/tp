package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.listcommands.ListProjectMembersCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.AssignmentProjectPredicate;
import seedu.address.model.project.ProjectName;


/**
 * Parses input arguments and creates a new ListProjectMembersCommand object.
 */
public class ListProjectMembersCommandParser implements Parser<ListProjectMembersCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListProjectMembersCommand
     * and returns a ListProjectMembersCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ListProjectMembersCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PROJECT_NAME);
        if (!arePrefixesPresent(argMultimap, PREFIX_PROJECT_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                    ListProjectMembersCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PROJECT_NAME);
        // Create the predicate to filter assignments by the project name
        ProjectName projectName = ParserUtil.parseProjectName(argMultimap.getValue(PREFIX_PROJECT_NAME).get());

        AssignmentProjectPredicate predicate = new AssignmentProjectPredicate(projectName);

        return new ListProjectMembersCommand(predicate);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
