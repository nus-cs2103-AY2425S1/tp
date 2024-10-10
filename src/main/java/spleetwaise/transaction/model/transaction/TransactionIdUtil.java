package spleetwaise.transaction.model.transaction;

import java.util.UUID;

/**
 * Utilities class for generating transaction UUIDs.
 */
public class TransactionIdUtil {

    private static boolean isDeterminate = false;

    public static void setDeterminate(boolean state) {
        isDeterminate = state;
    }

    /**
     * Generates an UUID.
     *
     * @return a random UUID.
     */
    public static String getUuid() {
        if (isDeterminate) {
            return "test-uuid";
        }
        return UUID.randomUUID().toString();
    }
}
