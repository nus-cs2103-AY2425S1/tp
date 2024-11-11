package seedu.address.model.client.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DuplicateClientExceptionTest {

    @Test
    public void duplicateClientException_isThrown() {
        // Test that DuplicateClientException is thrown
        assertThrows(DuplicateClientException.class, () -> {
            throw new DuplicateClientException();
        });
    }

    @Test
    public void duplicateClientException_hasCorrectMessage() {
        // Test that the message in DuplicateClientException is as expected
        Exception exception = assertThrows(DuplicateClientException.class, () -> {
            throw new DuplicateClientException();
        });
        assertEquals("Operation would result in duplicate persons", exception.getMessage());
    }
}
