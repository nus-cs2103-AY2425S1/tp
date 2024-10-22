package seedu.address.model.consultation.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ConsultationNotFoundExceptionTest {

    @Test
    public void testConsultationNotFoundException() {
        // Verifying that the exception is thrown with the expected message
        Exception exception = assertThrows(ConsultationNotFoundException.class, () -> {
            throw new ConsultationNotFoundException();
        });

        // Asserting that the message matches
        String expectedMessage = "Consultation not found in the list";
        String actualMessage = exception.getMessage();

        // Check if the actual message matches the expected message
        assert(expectedMessage.equals(actualMessage));
    }
}
