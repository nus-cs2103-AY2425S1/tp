package spleetwaise.commons;

import java.util.UUID;

/**
 * Utility class for generating transaction UUIDs.
 */
public class IdUtil {

    public static final String TEST_ID = "test-uuid";
    private static boolean isDeterminate = false;

    /**
     * Sets whether the transaction ID generation is determinate or not. Used for testing purposes, this method allows
     * you to control the randomness of the generated IDs.
     *
     * @param isDeterminate if true, the transaction ID will be deterministic; otherwise, it will be random
     */
    public static void setDeterminate(boolean isDeterminate) {
        IdUtil.isDeterminate = isDeterminate;
    }

    /**
     * Generates an UUID.
     *
     * @return a random UUID.
     */
    public static String getId() {
        if (isDeterminate) {
            return TEST_ID;
        }
        return UUID.randomUUID().toString();
    }

}
