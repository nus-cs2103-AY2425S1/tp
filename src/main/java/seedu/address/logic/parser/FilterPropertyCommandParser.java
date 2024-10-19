package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.logic.commands.FilterPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.PropertyType;
import seedu.address.model.property.Type;

/**
 * Parses input arguments and creates a new {@code FilterPropertyCommand} object
 */
public class FilterPropertyCommandParser implements Parser<FilterPropertyCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code FilterPropertyCommand}
     * and returns a {@code FilterPropertyCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterPropertyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TYPE);
        String type = argMultimap.getValue(PREFIX_TYPE).orElse("");
        if (type.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE));
        }
        if (!PropertyType.isValidEnumValue(type)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, Type.MESSAGE_CONSTRAINTS));
        }
        return new FilterPropertyCommand(new Type(type));
    }
}
