package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditScheduleCommand;
import seedu.address.logic.commands.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditScheduleCommand object
 */
public class EditScheduleCommandParser implements Parser<EditScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditScheduleCommand
     * and returns an EditScheduleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_TIME, PREFIX_CONTACT);

        Index index;

        // Parse the person index from the 'c/' prefix
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String
                    .format(MESSAGE_INVALID_COMMAND_FORMAT, EditScheduleCommand.MESSAGE_USAGE), pe);
        }

        // Verify no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DATE, PREFIX_TIME);

        EditScheduleDescriptor editScheduleDescriptor = new EditScheduleDescriptor();

        List<Index> contactIndexes = argMultimap.getAllValues(PREFIX_CONTACT).stream()
                .flatMap(value -> Stream.of(value.split("\\s+"))) // Split multiple indices by spaces
                .map(contactIndexStr -> {
                    try {
                        return ParserUtil.parseIndex(contactIndexStr);
                    } catch (ParseException e) {
                        throw new RuntimeException(e); // Wrap ParseException to unchecked exception
                    }
                }).toList();

        // Parse and set the new values if present
        if (argMultimap.getValue(PREFIX_CONTACT).isPresent()) {
            editScheduleDescriptor.setContactIndex(contactIndexes);
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editScheduleDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editScheduleDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            editScheduleDescriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get()));
        }

        // Check if any field has been edited
        if (!editScheduleDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditScheduleCommand.MESSAGE_NOT_EDITED);
        }

        return new EditScheduleCommand(index, editScheduleDescriptor);
    }
}
