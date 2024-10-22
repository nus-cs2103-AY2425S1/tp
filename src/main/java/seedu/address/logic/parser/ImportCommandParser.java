package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Placeholder
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    public static final String OVERRIDE_FLAG = "--override";

    /**
     * Placeholder
     * @param args
     * @return
     * @throws ParseException
     */
    @Override
    public ImportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ImportCommand(false);
        } else if (trimmedArgs.equals(OVERRIDE_FLAG)) {
            return new ImportCommand(true);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.COMMAND_SUMMARY_FORMAT));
        }
    }
}
