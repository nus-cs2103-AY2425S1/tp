package seedu.address.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Utility class for parsing task assignment-related commands.
 * This class provides methods to extract a person index and task indexes from command arguments.
 */
public class TaskAssignmentParserUtil {
    /**
     * Parses the given {@code String} of arguments and returns a person index and set of task indexes.
     *
     * @throws ParseException if the input does not conform to the expected format.
     */
    public static ParsedCommandData parseTaskCommand(String args, String messageUsage) throws ParseException {
        requireNonNull(args);
        String[] splitArgs = args.trim().split("\\s+");

        if (splitArgs.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage));
        }

        try {
            Index personIndex = ParserUtil.parseIndex(splitArgs[0]);
            Set<Index> taskIndexes = new HashSet<>();
            for (int i = 1; i < splitArgs.length; i++) {
                taskIndexes.add(ParserUtil.parseIndex(splitArgs[i]));
            }
            return new ParsedCommandData(personIndex, taskIndexes);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage), pe);
        }
    }

    /**
     * Data structure to hold the parsed person index and set of task indexes.
     */
    public static class ParsedCommandData {
        public final Index personIndex;
        public final Set<Index> taskIndexes;

        /**
         * Constructs a {@code ParsedCommandData} object.
         *
         * @param personIndex the index of the person in the command.
         * @param taskIndexes the set of task indexes parsed from the command.
         */
        public ParsedCommandData(Index personIndex, Set<Index> taskIndexes) {
            this.personIndex = personIndex;
            this.taskIndexes = taskIndexes;
        }
    }
}
