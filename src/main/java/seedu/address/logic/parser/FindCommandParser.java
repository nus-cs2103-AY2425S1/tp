package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PhonePredicate;
import seedu.address.model.person.RoomNumber;
import seedu.address.model.person.RoomNumberPredicate;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Parser for FindCommand.
 */
public class FindCommandParser implements Parser<FindCommand> {



    /**
     * @param args The input arguments to parse.
     * @return A command object used for Finding persons based on specified criteria.
     * @throws ParseException If the input does not meet the required format.
     */
    @Override
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_ROOM_NUMBER, PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        Optional<String> name = parseName(argMultimap.getValue(PREFIX_NAME).orElse(null));
        Optional<String> contactNumber = parseContactNumber(argMultimap.getValue(PREFIX_PHONE).orElse(null));
        Optional<String> roomNumber = parseRoomNumber(argMultimap.getValue(PREFIX_ROOM_NUMBER).orElse(null));
        Optional<List<String>> tagList = parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Predicate<Person> combinedPredicate = p -> true;

        if (name.isPresent()) {
            combinedPredicate = combinedPredicate
                    .and(new NameContainsKeywordsPredicate(Arrays.asList(name.get().split("\\s+"))));
        }
        if (contactNumber.isPresent()) {
            combinedPredicate = combinedPredicate.and(new PhonePredicate(contactNumber.get()));
        }
        if (roomNumber.isPresent()) {
            combinedPredicate = combinedPredicate.and(new RoomNumberPredicate(roomNumber.get()));
        }
        if (tagList.isPresent() && !tagList.get().isEmpty()) {
            combinedPredicate = combinedPredicate.and(new TagContainsKeywordsPredicate(tagList.get()));
        }

        return new FindCommand(combinedPredicate);
    }

    /**
     * Parses and validates the name extracted from the input arguments.
     * Returns an Optional containing the name if it is valid, otherwise throws ParseException.
     *
     * @param name The name to validate.
     * @return Optional Containing the validated name, or empty if no name is provided.
     * @throws ParseException If the name does not meet the validation criteria.
     */
    private Optional<String> parseName(String name) throws ParseException {
        if (name != null && !name.trim().isEmpty()) {
            if (!Name.isValidName(name.trim())) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, Name.CHAR_MESSAGE_CONSTRAINTS));
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
     * @return Optional Containing the validated contact number, or empty if no contact number is provided.
     * @throws ParseException If the contact number does not meet the validation criteria.
     */
    private Optional<String> parseContactNumber(String contactNumber) throws ParseException {
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
     * @return Optional Containing the validated room number, or empty if no room number is provided.
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


    /**
     * Parses and validates the list of tags from the input arguments.
     * Returns an Optional containing the list of tags if it is valid, otherwise throws ParseException.
     *
     * @param tags The list of tags to validate.
     * @return Optional Containing the validated list of tags, or empty if no list of tags is provided.
     * @throws ParseException If the list of tags does not meet the validation criteria.
     */
    private Optional<List<String>> parseTags(List<String> tags) throws ParseException {
        if (!tags.isEmpty()) {
            List<String> tagList = tags.stream()
                    .map(String::trim) // Trim each tag
                    .filter(tag -> !tag.isEmpty()) // Filter out empty strings after trim
                    .collect(Collectors.toList());

            if (tagList.isEmpty()) {
                return Optional.empty(); // Return empty if no valid tags are found
            }

            return Optional.of(tagList);
        }
        return Optional.empty();
    }

}
