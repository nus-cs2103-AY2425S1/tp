package tuteez.model.person.lesson;

import static tuteez.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class LessonTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(null));
    }
}
