package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.JobCode;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_JOBCODE,
                        PREFIX_TAG, PREFIX_REMARK);

        // Check for missing prefixes
        List<String> missingFields = new ArrayList<>();

        if (!argMultimap.getValue(PREFIX_NAME).isPresent()) {
            missingFields.add("Name");
        }
        if (!argMultimap.getValue(PREFIX_JOBCODE).isPresent()) {
            missingFields.add("Job Code");
        }
        if (!argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            missingFields.add("Phone");
        }
        if (!argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            missingFields.add("Email");
        }
        if (!argMultimap.getValue(PREFIX_TAG).isPresent()) {
            missingFields.add("Tag");
        }

        // Check if there is any additional content in the preamble
        if (!missingFields.isEmpty()) {
            throw new ParseException(String.format("Invalid command format! Missing fields: %s\n%s",
                    String.join(", ", missingFields),
                    String.format(AddCommand.MESSAGE_USAGE)));
        }

        String preamble = argMultimap.getPreamble();
        if (!preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_JOBCODE,
                PREFIX_TAG, PREFIX_REMARK);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        JobCode jobCode = ParserUtil.parseJobCode(argMultimap.getValue(PREFIX_JOBCODE).get());
        Tag tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
        Remark remark;
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            try {
                remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get());
            } catch (ParseException e) {
                // Handle the exception (log it or rethrow with a message)
                throw new ParseException(Remark.MESSAGE_CONSTRAINTS, e);
            }
        } else {
            remark = new Remark(""); // Default value if remark is not provided
        }

        Person person = new Person(name, phone, email, jobCode, tag, remark);

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
