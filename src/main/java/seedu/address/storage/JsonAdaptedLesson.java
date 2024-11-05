package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.datetime.Date;
import seedu.address.model.datetime.Time;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.StudentLessonInfo;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
class JsonAdaptedLesson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson's %s field is missing!";
    public static final String STUDENT_NOT_FOUND_MESSAGE = "Student %s"
            + " does not exist in the address book, or details do not match!";

    private final String date;
    private final String time;
    private final List<JsonAdaptedStudentLessonInfo> studentLessonInfoList;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("date") String date,
            @JsonProperty("time") String time,
            @JsonProperty("studentLessonInfoList") List<JsonAdaptedStudentLessonInfo> studentLessonInfoList) {
        this.date = date;
        this.time = time;
        this.studentLessonInfoList = new ArrayList<>();
        // require null check because JSON can use null if it is not present in file
        if (studentLessonInfoList != null) {
            List<JsonAdaptedStudentLessonInfo> nonNullStudentInfos = studentLessonInfoList.stream()
                    .filter(JsonAdaptedStudentLessonInfo::isNonNull)
                    .toList();
            this.studentLessonInfoList.addAll(nonNullStudentInfos);
        }
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        date = source.getDate().toString();
        time = source.getTime().toString();
        studentLessonInfoList = new ArrayList<>();
        source.getStudentLessonInfoList().forEach(studentLessonInfo ->
                studentLessonInfoList.add(new JsonAdaptedStudentLessonInfo(studentLessonInfo)));
    }

    /**
     * Converts this Jackson-friendly adapted lesson object into the model's
     * {@code Lesson} object.
     *
     * @param addressBook AddressBook instance to verify that the student(s) exist
     *                    in.
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted lesson.
     */
    public Lesson toModelType(AddressBook addressBook) throws IllegalValueException {
        checkValidity(date, time);
        final Date modelDate = new Date(date);
        final Time modelTime = new Time(time);
        final List<StudentLessonInfo> modelStudentLessonInfoList = new ArrayList<>();

        for (JsonAdaptedStudentLessonInfo jsonInfo : studentLessonInfoList) {
            // checking that student exists in TAHub is already done in StudentLessonInfo::toModelType
            StudentLessonInfo studentLessonInfo = jsonInfo.toModelType(addressBook);
            modelStudentLessonInfoList.add(studentLessonInfo);
        }

        return new Lesson(modelDate, modelTime, modelStudentLessonInfoList);
    }

    private void checkValidity(String date, String time) throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Date"));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Time"));
        }
        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof JsonAdaptedLesson)) {
            return false;
        }

        JsonAdaptedLesson otherLesson = (JsonAdaptedLesson) other;
        return date.equals(otherLesson.date)
                && time.equals(otherLesson.time)
                && studentLessonInfoList.equals(otherLesson.studentLessonInfoList);
    }
}
