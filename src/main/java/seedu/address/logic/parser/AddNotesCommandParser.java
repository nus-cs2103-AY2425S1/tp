package seedu.address.logic.parser;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddNotesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddNotesCommandParser implements Parser<AddNotesCommand> {
    public AddNotesCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NOTES);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNotesCommand.MESSAGE_USAGE), ive);
        }
        String notes = argMultimap.getValue(PREFIX_NOTES).orElse("");
        return new AddNotesCommand(index, notes);
    }
}