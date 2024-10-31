package tuteez.model.person.lesson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import tuteez.model.person.Person;
import tuteez.model.person.UniquePersonList;

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
     * Checks if a given lesson already exists in the schedule
     *
     * @param lesson The lesson to check for existence in the schedule.
     * @return {@code true} if the lesson exists, {@code false} otherwise.
     */
    public boolean isExistingLesson(Lesson lesson) {
        Day lessonDay = lesson.getLessonDay();
        return dayLessonsMap.get(lessonDay).contains(lesson);
    }

    /**
     * Checks for any lessons in the given student list that clash with the specified lesson.
     * A clash occurs if an existing lesson on the same day has overlapping timings with the given lesson.
     *
     * <p>Note: Consecutive lessons (e.g., timings 1900-2000 & 2000-2100) do not clash
     * as they do not overlap in time.</p>
     *
     * @param studentList The list of students whose lessons are to be checked for potential clashes.
     * @param lesson The lesson to check for time conflicts.
     * @return A map where each entry consists of a {@code Person} whose lessons clash with the specified lesson,
     *         and an {@code ArrayList} of the clashing lessons for that student.
     */
    public Map<Person, ArrayList<Lesson>> getClashingLessons(UniquePersonList studentList, Lesson lesson) {
        Map<Person, ArrayList<Lesson>> clashingLessonMap = new HashMap<>();
        for (Person studentToCheck : studentList) {
            ArrayList<Lesson> clashedLessons = studentToCheck.getLessonsThatClash(lesson);
            if (!clashedLessons.isEmpty()) {
                clashingLessonMap.put(studentToCheck, clashedLessons);
            }
        }
        return clashingLessonMap;
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
