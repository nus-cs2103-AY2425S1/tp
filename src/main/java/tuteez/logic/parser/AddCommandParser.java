package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.Messages.MESSAGE_MISSING_PERSON_NAME;
import static tuteez.logic.Messages.MESSAGE_MISSING_PHONE;
import static tuteez.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tuteez.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON;
import static tuteez.logic.parser.CliSyntax.PREFIX_NAME;
import static tuteez.logic.parser.CliSyntax.PREFIX_PHONE;
import static tuteez.logic.parser.CliSyntax.PREFIX_TAG;
import static tuteez.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import tuteez.logic.commands.AddCommand;
import tuteez.logic.parser.exceptions.ParseException;
import tuteez.model.person.Address;
import tuteez.model.person.Email;
import tuteez.model.person.Name;
import tuteez.model.person.Person;
import tuteez.model.person.Phone;
import tuteez.model.person.TelegramUsername;
import tuteez.model.person.lesson.Lesson;
import tuteez.model.tag.Tag;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TELEGRAM, PREFIX_TAG, PREFIX_LESSON);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TELEGRAM);
        Phone phone = parsePhoneFromArgMultiMap(argMultimap);
        Name name = parseNameFromArgMultiMap(argMultimap);
        verifyRequiredFields(name, phone);
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).orElse(null));
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse(null));
        TelegramUsername telegramUsername = ParserUtil.parseTelegramUsername(
                argMultimap.getValue(PREFIX_TELEGRAM).orElse(null));

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        List<Lesson> lessonList = ParserUtil.parseLessons(argMultimap.getAllValues(PREFIX_LESSON));

        Person person = new Person(name, phone, email, address, telegramUsername, tagList, lessonList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the phone number from the given ArgumentMultimap.
     *
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @return the parsed Phone object or null if the phone prefix is not present
     * @throws ParseException if the phone number is invalid
     */
    private Phone parsePhoneFromArgMultiMap(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_PHONE)) {
            return ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        } else {
            return null;
        }
    }

    /**
     * Parses the name from the given ArgumentMultimap.
     *
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @return the parsed Name object or null if the name prefix is not present
     * @throws ParseException if the name is invalid
     */
    private Name parseNameFromArgMultiMap(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            return ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        } else {
            return null;
        }
    }

    /**
     * Verifies that the required fields (name and phone) are present and valid.
     *
     * @param name the parsed Name object
     * @param phone the parsed Phone object
     * @throws ParseException if any of the required fields are missing
     */
    private void verifyRequiredFields(Name name, Phone phone) throws ParseException {
        if (name == null && phone == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        if (name == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_PERSON_NAME));
        }
        if (phone == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_PHONE));
        }
    }

}
