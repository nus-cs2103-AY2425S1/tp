package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIREDROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPERIENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DesiredRole;
import seedu.address.model.person.Email;
import seedu.address.model.person.Experience;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Skills;
import seedu.address.model.person.Status;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object.
 */
public class AddCommandParser implements Parser<AddCommand> {

    // Logger instance for this class
    private static final Logger logger = Logger.getLogger(AddCommandParser.class.getName());

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AddCommand parse(String args) throws ParseException {
        logger.log(Level.INFO, "Starting to parse AddCommand with args: {0}", args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
            args,
            PREFIX_NAME,
            PREFIX_PHONE,
            PREFIX_EMAIL,
            PREFIX_ADDRESS,
            PREFIX_DESIREDROLE,
            PREFIX_SKILLS,
            PREFIX_EXPERIENCE,
            PREFIX_STATUS,
            PREFIX_NOTE,
            PREFIX_TAG
        );

        logger.log(Level.FINE, "Tokenization complete: {0}", argMultimap);

        // Check for required prefixes (note is optional)
        if (!arePrefixesPresent(
            argMultimap,
            PREFIX_NAME,
            PREFIX_PHONE,
            PREFIX_EMAIL,
            PREFIX_ADDRESS,
            PREFIX_DESIREDROLE,
            PREFIX_SKILLS,
            PREFIX_EXPERIENCE,
            PREFIX_STATUS
        ) || !argMultimap.getPreamble().isEmpty()) {
            logger.log(Level.WARNING, "Missing required prefixes or preamble is not empty");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // Verify no duplicate prefixes
        try {
            argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME,
                PREFIX_PHONE,
                PREFIX_EMAIL,
                PREFIX_ADDRESS,
                PREFIX_DESIREDROLE,
                PREFIX_SKILLS,
                PREFIX_EXPERIENCE,
                PREFIX_STATUS,
                PREFIX_NOTE
            );
            logger.log(Level.FINE, "No duplicate prefixes found");
        } catch (ParseException pe) {
            logger.log(Level.WARNING, "Duplicate prefixes found: {0}", pe.getMessage());
            throw pe;
        }

        try {
            // Parse each field
            logger.log(Level.FINE, "Parsing individual fields");

            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            assert name != null : "Name parsing failed!";
            logger.log(Level.FINER, "Parsed name: {0}", name);

            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            assert phone != null : "Phone parsing failed!";
            logger.log(Level.FINER, "Parsed phone: {0}", phone);

            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
            assert email != null : "Email parsing failed!";
            logger.log(Level.FINER, "Parsed email: {0}", email);

            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
            assert address != null : "Address parsing failed!";
            logger.log(Level.FINER, "Parsed address: {0}", address);

            DesiredRole desiredRole = ParserUtil.parseDesiredRole(argMultimap.getValue(PREFIX_DESIREDROLE).get());
            assert desiredRole != null : "DesiredRole parsing failed!";
            logger.log(Level.FINER, "Parsed desiredRole: {0}", desiredRole);

            Skills skills = ParserUtil.parseSkills(argMultimap.getValue(PREFIX_SKILLS).get());
            assert skills != null : "Skills parsing failed!";
            logger.log(Level.FINER, "Parsed skills: {0}", skills);

            Experience experience = ParserUtil.parseExperience(argMultimap.getValue(PREFIX_EXPERIENCE).get());
            assert experience != null : "Experience parsing failed!";
            logger.log(Level.FINER, "Parsed experience: {0}", experience);

            Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
            assert status != null : "Status parsing failed!";
            logger.log(Level.FINER, "Parsed status: {0}", status);

            // Parse optional note
            Note note;
            if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
                note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
                assert note != null : "Note parsing failed!";
                logger.log(Level.FINER, "Parsed note: {0}", note);
            } else {
                note = new Note(""); // Default to empty note if not provided
                logger.log(Level.FINER, "No note provided; using empty note");
            }

            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            assert tagList != null : "Tag parsing failed!";
            logger.log(Level.FINER, "Parsed tags: {0}", tagList);

            // Create a new Person object with all fields
            Person person = new Person(
                name,
                phone,
                email,
                address,
                desiredRole,
                skills,
                experience,
                status,
                note,
                tagList
            );

            logger.log(Level.INFO, "Successfully parsed AddCommand: {0}", person);

            return new AddCommand(person);
        } catch (ParseException pe) {
            logger.log(Level.WARNING, "Error parsing fields: {0}", pe.getMessage());
            throw pe;
        }
    }

    /**
     * Returns true if all of the specified prefixes contain non-empty values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        boolean result = Stream.of(prefixes)
            .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
        return result;
    }
}
