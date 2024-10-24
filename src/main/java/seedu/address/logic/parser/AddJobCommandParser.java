package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REQUIREMENTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddJobCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.Name;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobCompany;
import seedu.address.model.job.JobDescription;
import seedu.address.model.job.JobSalary;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddJobCommand object.
 */
public class AddJobCommandParser implements Parser<AddJobCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddJobCommand
     * and returns an AddJobCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddJobCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_COMPANY,
                            PREFIX_SALARY, PREFIX_REQUIREMENTS, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_COMPANY, PREFIX_SALARY, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddJobCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_COMPANY,
                    PREFIX_SALARY, PREFIX_DESCRIPTION);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        JobCompany company = ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY).get());
        JobSalary salary = ParserUtil.parseSalary(argMultimap.getValue(PREFIX_SALARY).get());
        JobDescription description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Set<Tag> requirements = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_REQUIREMENTS));

        Job job = new Job(name, company, salary, description, requirements, new HashSet<>());

        return new AddJobCommand(job);
    }
}
