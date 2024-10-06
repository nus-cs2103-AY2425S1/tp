package spleetwaise.transaction.testutil;

import spleetwaise.transaction.logic.commands.AddCommand;

/**
 * A utility class for Transaction.
 */
public class TransactionUtil {

    /**
     * TODO: Pass a transaction object and stringify it
     * @return
     */
    public static String getAddCommand() {
        return AddCommand.COMMAND_WORD;
    }
}
