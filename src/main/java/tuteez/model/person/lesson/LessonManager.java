package tuteez.model.person.lesson;

import java.util.HashMap;
import java.util.TreeSet;

/**
 * A container for all Lesson instances
 * Provides:
 *  1. Methods to add and delete
 *  2. Check for clashing lessons
 */
public class LessonManager {
    private final HashMap<Day, TreeSet<Lesson>> dayLessonsMap = new HashMap<>(7);

    public LessonManager() {
       for (Day day : Day.values()) {
           dayLessonsMap.put(day, new TreeSet<>(new Lesson.LessonComparator()));
       }
    }

    /**
     * Note: Should always check for a clashing lesson before calling this method
     * @param lesson
     */
    public void addLesson(Lesson lesson) {
        dayLessonsMap.get(lesson.getLessonDay()).add(lesson);
    }

    public void deleteLesson(Lesson lesson) {
        dayLessonsMap.get(lesson.getLessonDay()).remove(lesson);
    }

    public boolean isClashingWithExistingLesson(Lesson lesson) {
        Day lessonDay = lesson.getLessonDay();
        TreeSet<Lesson> lessonsOnDay = dayLessonsMap.get(lessonDay);
        for (Lesson lessonOnDay : lessonsOnDay) {
            if (Lesson.isClashingWithOtherLesson(lessonOnDay, lesson)) {
                return true;
            }
        }
        return false;
    }

    public void setLessonsFromStorage(LessonManager dataLessonManager) {
        for (Day day : Day.values()) {
            dayLessonsMap.put(day, new TreeSet<>(dataLessonManager.dayLessonsMap.get(day)));
        }
    }
}
