package careconnect.logic.parser;

import java.util.ArrayList;
import java.util.Set;

import careconnect.logic.Messages;
import careconnect.logic.commands.AddCommand;
import careconnect.logic.parser.exceptions.ParseException;
import careconnect.model.log.Log;
import careconnect.model.person.Address;
import careconnect.model.person.AppointmentDate;
import careconnect.model.person.Email;
import careconnect.model.person.Name;
import careconnect.model.person.Person;
import careconnect.model.person.Phone;
import careconnect.model.tag.Tag;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_PHONE,
                        CliSyntax.PREFIX_EMAIL,
                        CliSyntax.PREFIX_ADDRESS, CliSyntax.PREFIX_TAG);

        if (!ParserUtil.arePrefixesPresent(argMultimap, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_ADDRESS,
                CliSyntax.PREFIX_PHONE,
                CliSyntax.PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_PHONE,
                CliSyntax.PREFIX_EMAIL,
                CliSyntax.PREFIX_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(CliSyntax.PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(CliSyntax.PREFIX_EMAIL).get());
        Address address =
                ParserUtil.parseAddress(argMultimap.getValue(CliSyntax.PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(CliSyntax.PREFIX_TAG));
        // Initialises logs to an empty list when person is first added
        ArrayList<Log> logs = new ArrayList<>();

        Person person = new Person(name, phone, email, address, tagList, logs, new AppointmentDate());

        return new AddCommand(person);
    }

}
