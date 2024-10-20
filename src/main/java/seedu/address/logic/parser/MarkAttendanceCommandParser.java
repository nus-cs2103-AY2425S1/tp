package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkAttendanceCommand object
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAttendanceCommand
     * and returns a MarkAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public MarkAttendanceCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_EVENT, PREFIX_INDEX);

        if (!argMultimap.getValue(PREFIX_EVENT).isPresent() || !argMultimap.getValue(PREFIX_INDEX).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkAttendanceCommand.MESSAGE_USAGE));
        }

        String eventName = argMultimap.getValue(PREFIX_EVENT).get().trim();

        if (eventName.isEmpty()) {
            throw new ParseException("Event name cannot be empty.");
        }

        List<String> indexStrings = argMultimap.getAllValues(PREFIX_INDEX);
        //        List<Index> indices;
        //        try {
        //            indices = indexStrings.stream()
        //                    .map(String::trim)
        //                    .map(ParserUtil::parseIndex)
        //                    .collect(Collectors.toList());
        //        } catch (ParseException pe) {
        //            throw new ParseException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        //        }
        List<Index> indices = new ArrayList<>();
        try {
            for (String indexString : indexStrings) {
                String trimmedIndex = indexString.trim();
                Index index = ParserUtil.parseIndex(trimmedIndex);
                indices.add(index);
            }
        } catch (ParseException pe) {
            throw new ParseException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return new MarkAttendanceCommand(eventName, indices);
    }
    //    @Override
    //    public MarkAttendanceCommand parse(String userInput) throws ParseException {
    //        String[] parts = userInput.trim().split("\\s+");
    //        if (parts.length != 2) {
    //            throw new ParseException("Invalid command format. " + MarkAttendanceCommand.MESSAGE_USAGE);
    //        }
    //
    //        String eventName = parts[0];
    //        Index index;
    //        try {
    //            index = ParserUtil.parseIndex(parts[1]);
    //        } catch (ParseException pe) {
    //            throw new ParseException("Invalid index provided.");
    //        }
    //
    //        return new MarkAttendanceCommand(eventName, index);
    //    }
}
