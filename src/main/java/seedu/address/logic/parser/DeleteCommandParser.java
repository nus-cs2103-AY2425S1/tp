package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Arrays;

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
            String[] arr = args.split(",");
            int size = arr.length;
            for (int i = 0; i < size; i++) {
                if (!DeleteCommandParser.isNumber(arr[i].trim())) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
                }
            }
            Index[] indexes = new Index[size];
            int[] intArr = new int[size];

            //convert string indexes to int indexes and sort int indexes in ascending order
            for (int i = 0; i < size; i++) {
                intArr[i] = Integer.parseInt(arr[i].trim());
            }
            Arrays.sort(intArr);
            //convert sorted int arr back into str arr to be parsed
            for (int i = 0; i < size; i++) {
                arr[i] = String.valueOf(intArr[i]);
            }
            for (int i = 0; i < size; i++) {
                indexes[i] = ParserUtil.parseIndex(arr[i]);
            }
            return new DeleteCommand(indexes);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    public static boolean isNumber(String str) {
        return str.matches("-?\\d+");
    }

}
