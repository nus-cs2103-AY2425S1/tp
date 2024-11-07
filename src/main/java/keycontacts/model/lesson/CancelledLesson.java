package keycontacts.model.lesson;

import static keycontacts.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import keycontacts.commons.util.ToStringBuilder;

/**
 *  Class representing a Student's cancelled lesson in the student directory.
 *  Guarantees: immutable
 */
public class CancelledLesson {
    private final Date lessonDate;

    /**
     * Constructs a {@code CancelledLesson} object.
     *
     * @param lessonDate The lesson date of the cancelled lesson
     */
    public CancelledLesson(Date lessonDate) {
        requireAllNonNull(lessonDate);
        this.lessonDate = lessonDate;
    }

    public Date getLessonDate() {
        return lessonDate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CancelledLesson)) {
            return false;
        }

        CancelledLesson otherLesson = (CancelledLesson) other;
        return lessonDate.equals(otherLesson.lessonDate);
    }

    @Override
    public int hashCode() {
        return lessonDate.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("lessonDate", lessonDate)
                .toString();
    }

    /**
     * Returns a cancelled lesson set containing the list of strings given.
     */
    public static Set<CancelledLesson> getCancelledLessonSet(String... strings) {
        return Arrays.stream(strings)
                .map(string -> new CancelledLesson(new Date(string)))
                .collect(Collectors.toSet());
    }

}
