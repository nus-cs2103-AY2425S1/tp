package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.ArrayList;
import java.util.List;

public class SelectCommandParser implements Parser<SelectCommand> {

    public SelectCommand parse(String args) throws ParseException {
        String indexesString = args.trim();
        // defensive programming may be used here WIP for the split
        String[] indexStrings = indexesString.split(" ");
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
