package spleetwaise.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.UUID;

/**
 * Utility class for generating transaction UUIDs.
 */
public class IdUtil {

    public static final String TEST_ID = "123e4567-e89b-42d3-a456-556642440000";
    public static final String MESSAGE_CONSTRAINTS = "UUID is invalid, refer to "
            + "https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/UUID.html.";
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

    /**
     * Returns true if a given string is a valid remark (allows empty string "").
     */
    public static boolean isValidId(String id) {
        try {
            requireNonNull(id);
            String trimmedId = id.trim();
            UUID.fromString(trimmedId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
