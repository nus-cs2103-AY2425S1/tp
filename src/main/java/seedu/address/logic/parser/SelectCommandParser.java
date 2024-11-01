package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {

    /**
     * Parses the given {@code Integer} of arguments in the context of the SelectCommand
     * and returns a SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        String indexesString = args.trim();
        String[] indexStrings = indexesString.split("\\s+");
        List<Index> indexes = new ArrayList<>();
        for (String indexString : indexStrings) {
            try {
                Index index = ParserUtil.parseIndex(indexString);
                indexes.add(index);
            } catch (ParseException e) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE), e);
            }
        }

        return new SelectCommand(indexes);

    }
}
