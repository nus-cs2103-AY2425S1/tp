package seedu.address.model.lesson.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LessonNotFoundExceptionTest {

    @Test
    public void testLessonNotFoundException() {
        // Verifying that the exception is thrown with the expected message
        Exception exception = assertThrows(LessonNotFoundException.class, () -> {
            throw new LessonNotFoundException();
        });

        // Asserting that the message matches
        String expectedMessage = "Lesson not found in the list";
        String actualMessage = exception.getMessage();

        // Check if the actual message matches the expected message
        assert(expectedMessage.equals(actualMessage));
    }
}
