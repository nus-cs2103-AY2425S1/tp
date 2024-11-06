package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.Date;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    public static final String MESSAGE_MISSING_PARAMETERS = "Following parameters are missing : ";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_ALLERGY);

        List<String> missingParams = new ArrayList<>();

        if (!argMultimap.getValue(PREFIX_NAME).isPresent()) {
            missingParams.add("n/");
        }
        if (!argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            missingParams.add("p/");
        }
        if (!argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            missingParams.add("e/");
        }
        if (!argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            missingParams.add("a/");
        }
        if (!argMultimap.getValue(PREFIX_TAG).isPresent()) {
            missingParams.add("t/");
        }
        if (!argMultimap.getValue(PREFIX_ALLERGY).isPresent()) {
            missingParams.add("m/");
        }

        // If there are any missing parameters, throw a ParseException with a more informative message
        if (!missingParams.isEmpty()) {
            StringBuilder missingParamsMessage = new StringBuilder();

            // Use a for loop to append each missing parameter to the message
            for (int i = 0; i < missingParams.size(); i++) {
                missingParamsMessage.append(missingParams.get(i));
                // Add a comma if it's not the last element
                if (i < missingParams.size() - 1) {
                    missingParamsMessage.append(", ");
                }
            }

            throw new ParseException(MESSAGE_MISSING_PARAMETERS + missingParamsMessage + "\n"
                    + AddCommand.MESSAGE_USAGE);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Tag tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
        Set<Allergy> allergyList = ParserUtil.parseAllergies(argMultimap.getAllValues(PREFIX_ALLERGY));
        Date date = new Date(LocalDateTime.MIN); // add command does not allow adding an appointment date straight away
        Person person = new Person(name, phone, email, address, tag, allergyList, date);
        return new AddCommand(person);
    }

}
