package spleetwaise.commons.testutil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

/**
 * A utility class that provides static assertion methods for testing purposes. This class includes methods for
 * asserting exceptions, validating person card displays, and matching person lists in the application.
 */
public class Assert {

    /**
     * Asserts that the given executable throws an exception to the expected type.
     *
     * @param expectedType The class of the expected exception.
     * @param executable   The executable that is expected to throw the exception.
     */
    public static void assertThrows(Class<? extends Throwable> expectedType, Executable executable) {
        Assertions.assertThrows(expectedType, executable);
    }

    /**
     * Asserts that the given executable throws an exception to the expected type with the expected message.
     *
     * @param expectedType    The class of the expected exception.
     * @param expectedMessage The expected message of the thrown exception.
     * @param executable      The executable that is expected to throw the exception.
     */
    public static void assertThrows(
            Class<? extends Throwable> expectedType, String expectedMessage,
            Executable executable
    ) {
        Throwable thrownException = Assertions.assertThrows(expectedType, executable);
        Assertions.assertEquals(expectedMessage, thrownException.getMessage());
    }
}
