package tuteez.model.person.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LessonManagerTest {

    @Test
    public void addLesson() {
        LessonManager lessonManager = new LessonManager();
        Lesson l1 = new Lesson("friday 1200-1400");
        lessonManager.addLesson(l1);
        assertTrue(lessonManager.isClashingWithExistingLesson(l1));
    }

    @Test
    public void deleteLesson() {
        LessonManager lessonManager = new LessonManager();
        Lesson l1 = new Lesson("friday 1200-1400");
        lessonManager.addLesson(l1);
        assertTrue(lessonManager.isClashingWithExistingLesson(l1));
        lessonManager.deleteLesson(l1);
        assertFalse(lessonManager.isClashingWithExistingLesson(l1));
    }

    @Test
    public void isClashingWithExistingLesson_clashOccurs() {
        LessonManager lessonManager = new LessonManager();
        Lesson l1 = new Lesson("friday 1200-1400");
        Lesson l2 = new Lesson("friday 1230-1430");
        lessonManager.addLesson(l1);
        assertTrue(lessonManager.isClashingWithExistingLesson(l2));
    }

    @Test
    public void isClashingWithExistingLesson_noClash() {
        LessonManager lessonManager = new LessonManager();
        Lesson l1 = new Lesson("friday 1200-1400");
        Lesson l2 = new Lesson("friday 1400-1500");
        lessonManager.addLesson(l1);
        assertFalse(lessonManager.isClashingWithExistingLesson(l2));
    }
}
