package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {
    private static final String WHITESPACE_REGEX = "\\s+";

    private static final String LOG_EXECUTING_SELECT_COMMAND_PARSER = "Executing SelectCommandParser with input: %s";
    private static final String LOG_SELECT_COMMAND_PARSER_SUCCESS = "SelectCommandParser "
            + "executed successfully with indexes: %s";

    private static final Logger logger = LogsCenter.getLogger(SelectCommandParser.class.getName());

    /**
     * Parses the given {@code Integer} of arguments in the context of the SelectCommand
     * and returns a SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        String indexesString = args.trim();

        logger.info(String.format(LOG_EXECUTING_SELECT_COMMAND_PARSER, indexesString));

        String[] indexStrings = indexesString.split(WHITESPACE_REGEX);
        List<Index> indexes = parseIndexes(indexStrings);

        logger.info(String.format(LOG_SELECT_COMMAND_PARSER_SUCCESS, String.join(", ", indexesString)));

        return new SelectCommand(indexes);

    }

    private List<Index> parseIndexes(String[] indexStrings) throws ParseException {
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
        return indexes;
    }
}
