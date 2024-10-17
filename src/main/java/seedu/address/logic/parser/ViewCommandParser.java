package seedu.address.logic.parser;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class ViewCommandParser implements Parser<ViewCommand> {

    private static final Prefix PREFIX_NAME = new Prefix("n/");
    private static final Prefix PREFIX_CONTACT_NUMBER = new Prefix("p/");
    private static final Prefix PREFIX_ROOM_NUMBER = new Prefix("r/");


    /**
     * Parses the provided arguments to create a new ViewCommand object.
     * Validates and extracts name, contact number, and room number from the arguments.
     * Constructs a predicate for filtering persons based on these extracted values.
     * Throws ParseException if the input does not conform to the expected format.
     *
     * @param args The input arguments to parse.
     * @return ViewCommand A command object used for viewing persons based on specified criteria.
     * @throws ParseException If the input does not meet the required format.
     */
    @Override
    public ViewCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CONTACT_NUMBER, PREFIX_ROOM_NUMBER);

        // Check for unwanted extra text in the preamble (text before the first valid prefix)
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        // Check if any recognized prefixes have values and extract them
        Optional<String> name = parseName(argMultimap.getValue(PREFIX_NAME).orElse(null));
        Optional<String> contactNumber = parseContactNumber(argMultimap.getValue(PREFIX_CONTACT_NUMBER).orElse(null));
        Optional<String> roomNumber = parseRoomNumber(argMultimap.getValue(PREFIX_ROOM_NUMBER).orElse(null));

        Predicate<Person> combinedPredicate = p -> true; // default predicate that always returns true

        if (name.isPresent()) {
            combinedPredicate = combinedPredicate.and(new NameContainsKeywordsPredicate(Arrays.asList(name.get().split("\\s+"))));
        }
        if (contactNumber.isPresent()) {
            combinedPredicate = combinedPredicate.and(new PhonePredicate(contactNumber.get()));
        }
        if (roomNumber.isPresent()) {
            combinedPredicate = combinedPredicate.and(new RoomNumberPredicate(roomNumber.get()));
        }

        return new ViewCommand(combinedPredicate);
    }

    /**
     * Parses and validates the name extracted from the input arguments.
     * Returns an Optional containing the name if it is valid, otherwise throws ParseException.
     *
     * @param name The name to validate.
     * @return Optional<String> Containing the validated name, or empty if no name is provided.
     * @throws ParseException If the name does not meet the validation criteria.
     */

    private Optional<String> parseName(String name) throws ParseException {
        if (name != null && !name.trim().isEmpty()) {
            if (!Name.isValidName(name.trim())) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, Name.MESSAGE_CONSTRAINTS));
            }
            return Optional.of(name.trim());
        }
        return Optional.empty();
    }

    /**
     * Parses and validates the contact number from the input arguments.
     * Returns an Optional containing the contact number if it is valid, otherwise throws ParseException.
     *
     * @param contactNumber The contact number to validate.
     * @return Optional<String> Containing the validated contact number, or empty if no contact number is provided.
     * @throws ParseException If the contact number does not meet the validation criteria.
     */
  Optional<String> parseContactNumber(String contactNumber) throws ParseException {
        if (contactNumber != null && !contactNumber.trim().isEmpty()) {
            if (!Phone.isValidPhone(contactNumber.trim())) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, Phone.MESSAGE_CONSTRAINTS));
            }
            return Optional.of(contactNumber.trim());
        }
        return Optional.empty();
    }

    /**
     * Parses and validates the room number from the input arguments.
     * Returns an Optional containing the room number if it is valid, otherwise throws ParseException.
     *
     * @param roomNumber The room number to validate.
     * @return Optional<String> Containing the validated room number, or empty if no room number is provided.
     * @throws ParseException If the room number does not meet the validation criteria.
     */
    private Optional<String> parseRoomNumber(String roomNumber) throws ParseException {
        if (roomNumber != null && !roomNumber.trim().isEmpty()) {
            if (!RoomNumber.isValidRoomNumber(roomNumber.trim())) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, RoomNumber.MESSAGE_CONSTRAINTS));
            }
            return Optional.of(roomNumber.trim());
        }
        return Optional.empty();
    }
}
