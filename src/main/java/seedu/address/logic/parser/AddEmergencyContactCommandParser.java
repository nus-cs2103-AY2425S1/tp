package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_RELATIONSHIP;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddEmergencyContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Relationship;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class AddEmergencyContactCommandParser implements Parser<AddEmergencyContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEmergencyContactCommand
     * and returns an AddEmergencyContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEmergencyContactCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMERGENCY_CONTACT_NAME, PREFIX_EMERGENCY_CONTACT_PHONE,
                        PREFIX_EMERGENCY_CONTACT_RELATIONSHIP);

        if (!AddCommandParser.arePrefixesPresent(argMultimap, PREFIX_EMERGENCY_CONTACT_NAME,
                PREFIX_EMERGENCY_CONTACT_PHONE, PREFIX_EMERGENCY_CONTACT_RELATIONSHIP)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEmergencyContactCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEmergencyContactCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EMERGENCY_CONTACT_NAME, PREFIX_EMERGENCY_CONTACT_PHONE,
                PREFIX_EMERGENCY_CONTACT_RELATIONSHIP);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_PHONE).get());
        Relationship relationship = ParserUtil.parseRelationship(
                argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_RELATIONSHIP).get());
        EmergencyContact emergencyContact = new EmergencyContact(name, phone, relationship);
        return new AddEmergencyContactCommand(index, emergencyContact);
    }

}
