package seedu.address.model.consultation.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DuplicateConsultationExceptionTest {

    @Test
    public void testDuplicateConsultationException() {
        // Verifying that the exception is thrown with the expected message
        Exception exception = assertThrows(DuplicateConsultationException.class, () -> {
            throw new DuplicateConsultationException();
        });

        // Asserting that the message matches
        String expectedMessage = "Operation would result in duplicate consultations";
        String actualMessage = exception.getMessage();

        // Check if the actual message matches the expected message
        assert(expectedMessage.equals(actualMessage));
    }
}
