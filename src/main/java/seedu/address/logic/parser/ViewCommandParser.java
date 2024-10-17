package seedu.address.logic.parser;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhonePredicate;
import seedu.address.model.person.RoomNumberPredicate;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class ViewCommandParser implements Parser<ViewCommand> {

    private static final Prefix PREFIX_NAME = new Prefix("n/");
    private static final Prefix PREFIX_CONTACT_NUMBER = new Prefix("p/");
    private static final Prefix PREFIX_ROOM_NUMBER = new Prefix("r/");

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

    private Optional<String> parseName(String name) {
        return Optional.ofNullable(name).filter(s -> !s.trim().isEmpty());
    }

    private Optional<String> parseContactNumber(String contactNumber) {
        return Optional.ofNullable(contactNumber).filter(s -> !s.trim().isEmpty());
    }

    private Optional<String> parseRoomNumber(String roomNumber) {
        return Optional.ofNullable(roomNumber).filter(s -> !s.trim().isEmpty());
    }
}
