package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_CELEBRITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Time;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an parsePersonCommand or parseEventCommand call based on prefixes
     * present
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userInput,
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                PREFIX_EVENT_NAME, PREFIX_EVENT_TIME, PREFIX_EVENT_VENUE, PREFIX_EVENT_CELEBRITY);

        if (isEventCommand(argumentMultimap)) {
            return parseEventCommand(argumentMultimap);
        } else if (isPersonCommand(argumentMultimap)) {
            return parsePersonCommand(argumentMultimap);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given argument multimap in the context of the AddContactCommand
     * and returns an AddContactCommand object
     * @param argMultimap
     * @return AddContactCommand object
     * @throws ParseException
     */
    public AddCommand parsePersonCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, phone, email, address, tagList);

        return new AddContactCommand(person);
    }

    /**
     * Parses the given argument multimap in the context of the AddEventCommand
     * and returns an AddEventCommand object
     * @param argMultimap
     * @return AddEventCommand object
     * @throws ParseException
     */
    public AddEventCommand parseEventCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_NAME, PREFIX_EVENT_TIME, PREFIX_EVENT_VENUE,
                PREFIX_EVENT_CELEBRITY) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_NAME, PREFIX_EVENT_TIME,
                                                    PREFIX_EVENT_VENUE, PREFIX_EVENT_CELEBRITY);
        EventName name = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME).get());
        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_EVENT_TIME).get());
        Venue venue = ParserUtil.parseVenue((argMultimap.getValue(PREFIX_EVENT_VENUE)).get());
        Person celebrity = parseCelebrity(argMultimap.getValue(PREFIX_EVENT_CELEBRITY).get());

        return new AddEventCommand(new Event(name, time, venue, celebrity));
    }

    /**
     * Parses the given argument to create a Person object
     * @param details
     * @return Person object
     * @throws ParseException
     */
    public Person parseCelebrity(String details) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(details,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        return new Person(name, phone, email, address, tagList);
    }

    /**
     * Returns true if ArgumentMultimap contains the prefixes needed for a new AddContactCommand
     * object
     * @param argMultimap
     * @return boolean
     */
    private static boolean isPersonCommand(ArgumentMultimap argMultimap) {
        return arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
    }

    /**
     * Returns true if ArgumentMultimap contains the prefixes needed for a new AddEventCommand
     * object
     * @param argMultimap
     * @return boolean
     */
    private static boolean isEventCommand(ArgumentMultimap argMultimap) {
        return arePrefixesPresent(argMultimap, PREFIX_EVENT_NAME, PREFIX_EVENT_TIME,
                                                PREFIX_EVENT_VENUE, PREFIX_EVENT_CELEBRITY);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
