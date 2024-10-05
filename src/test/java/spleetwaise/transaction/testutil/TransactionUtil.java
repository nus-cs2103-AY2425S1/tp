package spleetwaise.transaction.testutil;

import spleetwaise.transaction.logic.commands.AddCommand;
import spleetwaise.transaction.logic.parser.SpleetWaiseParser;

/**
 * A utility class for Transaction.
 */
public class TransactionUtil {

    /**
     * TODO: Pass a transaction object and stringify it
     * @return
     */
    public static String getAddCommand() {
        return SpleetWaiseParser.SPLEETWAISE_COMMAND_PREFIX + " " + AddCommand.COMMAND_WORD;
    }
}
