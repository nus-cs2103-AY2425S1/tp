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
    private static final int NUMBER_OF_DAYS_IN_WEEK = 7;
    private final HashMap<Day, TreeSet<Lesson>> dayLessonsMap = new HashMap<>(NUMBER_OF_DAYS_IN_WEEK);

    /**
     * Constructs a new {@code lessonManager} and initializes an empty schedule
     *
     * <p>A {@code TreeSet} of lessons is created for each day of the week, ensuring
     * that lessons are stored in natural order based on their start time. The comparator
     * used to order the lessons is {@code Lesson.LessonComparator}.</p>
     */
    public LessonManager() {
        for (Day day : Day.values()) {
            dayLessonsMap.put(day, new TreeSet<>(new Lesson.LessonComparator()));
        }
    }

    /**
     * Adds a lesson to the lesson schedule for the day on which the lesson occurs.
     *
     * <p>Note: Ensure that the lesson does not clash with any existing lessons before
     * calling this method. Clashing lessons should be detected and rejected beforehand.</p>
     *
     * @param lesson The {@code Lesson} to be added to the schedule.
     */
    public void addLesson(Lesson lesson) {
        dayLessonsMap.get(lesson.getLessonDay()).add(lesson);
    }

    /**
     * Deletes a lesson from the lesson schedule for the day on which the lesson occurs.
     *
     * @param lesson The {@code Lesson} to be removed from the schedule.
     */
    public void deleteLesson(Lesson lesson) {
        dayLessonsMap.get(lesson.getLessonDay()).remove(lesson);
    }

    /**
     * Checks if the given lesson clashes with any existing lesson on the same day.
     * A clash is determined if there is any overlap in lesson timings with another
     * lesson already scheduled on the same day.
     *
     * <p> Note: Lessons can be back to back i.e. timings 1900-2000 & 2000-2100 do not clash </p>
     *
     * @param lesson The lesson to check for potential time conflicts.
     * @return {@code true} if the lesson clashes with any existing lessons on the same day,
     *         {@code false} otherwise.
     */
    public boolean isClashingWithExistingLesson(Lesson lesson) {
        Day lessonDay = lesson.getLessonDay();
        TreeSet<Lesson> lessonsOnDay = dayLessonsMap.get(lessonDay);
        for (Lesson lessonOnDay : lessonsOnDay) {
            assert lessonOnDay != null;
            if (Lesson.isClashingWithOtherLesson(lessonOnDay, lesson)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the lessons from a stored {@code LessonManager} instance. Copies all lessons from
     * the provided {@code dataLessonManager} into this instance, grouping them by day.
     * Each day's lessons are stored in a {@code TreeSet} to maintain their natural order.
     *
     * @param dataLessonManager The {@code LessonManager} containing the lessons from storage
     *                          to be copied into this instance.
     */
    public void setLessonsFromStorage(LessonManager dataLessonManager) {
        for (Day day : Day.values()) {
            dayLessonsMap.put(day, new TreeSet<>(dataLessonManager.dayLessonsMap.get(day)));
        }
    }
}
