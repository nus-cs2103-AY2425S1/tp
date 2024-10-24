package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            String[] inputArr = args.split(",");
            int inputSize = inputArr.length;
            int[] intArr = new int[inputSize];

            for (int i = 0; i < inputSize; i++) {
                if (!DeleteCommandParser.isNumber(inputArr[i].trim())) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
                }
            }
            //convert string indexes to int indexes and sort int indexes in ascending order
            for (int i = 0; i < inputSize; i++) {
                intArr[i] = Integer.parseInt(inputArr[i].trim());
            }
            //extract only the unique int indexes
            int[] uniqueIntArr = Arrays.stream(intArr)
                    .distinct()
                    .toArray();
            int uniqueSize = uniqueIntArr.length;
            Index[] uniqueIndex = new Index[uniqueSize];

            for (int i = 0; i < uniqueSize; i++) {
                uniqueIndex[i] = ParserUtil.parseIndex(String.valueOf(uniqueIntArr[i]));
            }
            return new DeleteCommand(uniqueIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    public static boolean isNumber(String str) {
        return str.matches("-?\\d+");
    }

}
