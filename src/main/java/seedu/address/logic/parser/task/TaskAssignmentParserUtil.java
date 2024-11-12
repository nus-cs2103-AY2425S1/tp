package seedu.address.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.task.MarkTaskCommand;
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
            Index personIndex = Index.oneBasedNoConstraints(Integer.parseInt(splitArgs[0].trim()));
            Set<Index> secondaryIndexes = parseMultipleIndexes(splitArgs, 1);
            return new ParsedCommandData(personIndex, secondaryIndexes);
        } catch (ParseException | NumberFormatException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage), pe);
        }
    }

    /**
     * Parses multiple indexes from the given array of arguments, starting at the specified position.
     *
     * @param args The arguments array containing potential indexes.
     * @param startPosition The position in the array to start parsing indexes.
     * @return A set of parsed indexes.
     * @throws ParseException if any index is invalid.
     */
    public static Set<Index> parseMultipleIndexes(String[] args, int startPosition) throws ParseException {
        Set<Index> indexes = new HashSet<>();

        try {
            for (int i = startPosition; i < args.length; i++) {
                Index index = Index.oneBasedNoConstraints(Integer.parseInt(args[i]));
                indexes.add(index);
            }
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTaskCommand.MESSAGE_USAGE));
        }
        return indexes;
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
