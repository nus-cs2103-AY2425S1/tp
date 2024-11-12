package seedu.hiredfiredpro.logic.parser;

import static seedu.hiredfiredpro.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_INTERVIEW_SCORE;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_SKILLS;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.hiredfiredpro.logic.commands.AddCommand;
import seedu.hiredfiredpro.logic.parser.exceptions.ParseException;
import seedu.hiredfiredpro.model.person.Email;
import seedu.hiredfiredpro.model.person.InterviewScore;
import seedu.hiredfiredpro.model.person.Job;
import seedu.hiredfiredpro.model.person.Name;
import seedu.hiredfiredpro.model.person.Person;
import seedu.hiredfiredpro.model.person.Phone;
import seedu.hiredfiredpro.model.skill.Skill;
import seedu.hiredfiredpro.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {
    public static final Tag DEFAULT_TAG_PENDING = new Tag("pending");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_JOB, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_SKILLS, PREFIX_INTERVIEW_SCORE, PREFIX_TAG);
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_JOB, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_INTERVIEW_SCORE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_JOB, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_INTERVIEW_SCORE);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Job job = ParserUtil.parseJob(argMultimap.getValue(PREFIX_JOB).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Set<Skill> skillsList = ParserUtil.parseSkills(argMultimap.getAllValues(PREFIX_SKILLS));
        InterviewScore interviewScore = ParserUtil
                .parseInterviewScore(argMultimap.getValue(PREFIX_INTERVIEW_SCORE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        tagList.add(DEFAULT_TAG_PENDING);

        Person person = new Person(name, job, phone, email, skillsList, interviewScore, tagList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
