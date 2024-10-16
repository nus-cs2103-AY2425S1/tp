package tuteez.model.tag;

import org.junit.jupiter.api.Test;

import static tuteez.testutil.Assert.assertThrows;

public class LessonTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(null));
    }
}
