package tutorease.address.storage;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import tutorease.address.commons.exceptions.IllegalValueException;
import tutorease.address.model.LessonSchedule;
import tutorease.address.model.ReadOnlyLessonSchedule;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.lesson.Lesson;

/**
 * An Immutable LessonSchedule that is serializable to JSON format.
 */
@JsonRootName(value = "lessonschedule")
public class JsonSerializableLessonSchedule {
    public static final String MESSAGE_DUPLICATE_LESSON = "Lessons list contains duplicate lesson(s).";
    private final List<JsonAdaptedLesson> lessons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableLessonSchedule} with the given lessons.
     */
    @JsonCreator
    public JsonSerializableLessonSchedule(@JsonProperty("lessons") List<JsonAdaptedLesson> lessons) {
        this.lessons.addAll(lessons);
    }

    /**
     * Converts a given {@code LessonSchedule} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableLessonSchedule}.
     */
    public JsonSerializableLessonSchedule(ReadOnlyLessonSchedule source) {
        this.lessons.addAll(source.getLessonList().stream().map(JsonAdaptedLesson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this lesson schedule into the model's {@code LessonSchedule} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public LessonSchedule toModelType(ReadOnlyTutorEase tutorEase) throws IllegalValueException {
        requireNonNull(tutorEase);
        LessonSchedule lessonSchedule = new LessonSchedule();
        for (JsonAdaptedLesson jsonAdaptedLesson : lessons) {
            Lesson lesson = jsonAdaptedLesson.toModelType(tutorEase);
            if (lessonSchedule.hasLesson(lesson)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LESSON);
            }
            lessonSchedule.addLesson(lesson);
        }
        return lessonSchedule;
    }
}
