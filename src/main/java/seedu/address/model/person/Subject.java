package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.EnumUtil.inEnum;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents a Subject in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidSubjectName(String)}
 */
public class Subject {
    enum Subjects {
        MATH, A_MATH, E_MATH,
        SCIENCE, PHYSICS, CHEMISTRY, BIOLOGY, COMBINED_SCIENCE, ACCOUNTING,
        LITERATURE, HISTORY, GEOGRAPHY, SOCIAL_STUDIES,
        MUSIC, ART,
        ENGLISH, CHINESE, HIGHER_CHINESE, MALAY, HIGHER_MALAY, TAMIL, HIGHER_TAMIL, HINDI
    }

    public static final String MESSAGE_CONSTRAINTS = "Subjects should be in list: "
            + Arrays.toString(Subjects.values());

    public static final String MESSAGE_LEVEL_NEEDED = "Tag a student with a level first or in the same command";

    private static final Map<Level, EnumSet<Subjects>> validSubjectsByLevel = new HashMap<>();
    private static final EnumSet<Subjects> validLowerSecondarySubjects = EnumSet.of(
            Subjects.ENGLISH, Subjects.MATH, Subjects.SCIENCE, Subjects.PHYSICS, Subjects.CHEMISTRY,
            Subjects.BIOLOGY, Subjects.GEOGRAPHY, Subjects.HISTORY, Subjects.LITERATURE, Subjects.SOCIAL_STUDIES,
            Subjects.CHINESE, Subjects.HIGHER_CHINESE, Subjects.MALAY, Subjects.HIGHER_MALAY, Subjects.TAMIL,
            Subjects.HIGHER_TAMIL, Subjects.HINDI
    );

    private static final EnumSet<Subjects> validUpperSecondarySubjects = EnumSet.of(
            Subjects.ENGLISH, Subjects.A_MATH, Subjects.E_MATH, Subjects.PHYSICS, Subjects.CHEMISTRY, Subjects.BIOLOGY,
            Subjects.COMBINED_SCIENCE, Subjects.GEOGRAPHY, Subjects.HISTORY, Subjects.LITERATURE, Subjects.ACCOUNTING,
            Subjects.SOCIAL_STUDIES, Subjects.CHINESE, Subjects.HIGHER_CHINESE, Subjects.MALAY, Subjects.HIGHER_MALAY,
            Subjects.TAMIL, Subjects.HIGHER_TAMIL, Subjects.HINDI, Subjects.MUSIC, Subjects.ART
    );
    static {
        validSubjectsByLevel.put(new Level("S1 Express"), validLowerSecondarySubjects);
        validSubjectsByLevel.put(new Level("S1 NA"), validLowerSecondarySubjects);
        validSubjectsByLevel.put(new Level("S1 NT"), validLowerSecondarySubjects);
        validSubjectsByLevel.put(new Level("S1 IP"), validLowerSecondarySubjects);

        validSubjectsByLevel.put(new Level("S2 Express"), validLowerSecondarySubjects);
        validSubjectsByLevel.put(new Level("S2 NA"), validLowerSecondarySubjects);
        validSubjectsByLevel.put(new Level("S2 NT"), validLowerSecondarySubjects);
        validSubjectsByLevel.put(new Level("S2 IP"), validLowerSecondarySubjects);

        validSubjectsByLevel.put(new Level("S3 Express"), validUpperSecondarySubjects);
        validSubjectsByLevel.put(new Level("S3 NA"), validUpperSecondarySubjects);
        validSubjectsByLevel.put(new Level("S3 NT"), validUpperSecondarySubjects);
        validSubjectsByLevel.put(new Level("S3 IP"), validUpperSecondarySubjects);

        validSubjectsByLevel.put(new Level("S4 Express"), validUpperSecondarySubjects);
        validSubjectsByLevel.put(new Level("S4 NA"), validUpperSecondarySubjects);
        validSubjectsByLevel.put(new Level("S4 NT"), validUpperSecondarySubjects);
        validSubjectsByLevel.put(new Level("S4 IP"), validUpperSecondarySubjects);
    }

    private static final String MESSAGE_VALID_SUBJECTS_BASE = "Subject is not valid for given level. "
            + "Valid subjects for";
    private static String messageValidSubjectsByLevel = "";
    public final String subjectName;

    /**
     * Constructs a {@code Subject}.
     *
     * @param subjectName A valid subject name.
     */
    public Subject(String subjectName) {
        requireNonNull(subjectName);
        checkArgument(isValidSubjectName(subjectName), MESSAGE_CONSTRAINTS);
        this.subjectName = subjectName;
    }

    /**
     * Returns true if a given string is a valid subject name and correspondences to the correct level.
     */
    public static boolean isValidSubjectNameByLevel(Level level, String subjectName) {
        requireNonNull(subjectName);
        if (level.levelName.equals("NONE NONE")) {
            return false;
        }
        return validSubjectsByLevel
                .get(level)
                .contains(Subjects.valueOf(subjectName.toUpperCase()));
    }

    public static String getValidSubjectMessage() {
        return messageValidSubjectsByLevel;
    }

    /**
     * Returns true if a given string is a valid subject name.
     */
    public static boolean isValidSubjectName(String subjectName) {
        requireNonNull(subjectName);
        return inEnum(subjectName.toUpperCase(), Subjects.class);
    }

    /**
     * Returns true if all subjects are valid according to the given level
     * */
    public static boolean isValidSubjectsByLevel(Level level, Set<Subject> subjects) {
        if (level == null || level.levelName.equals("NONE NONE")) {
            messageValidSubjectsByLevel = Subject.MESSAGE_LEVEL_NEEDED;
            return false;
        }
        EnumSet<Subjects> validSubjects = validSubjectsByLevel.get(level);
        for (Subject s: subjects) {
            if (!validSubjects.contains(Subjects.valueOf(s.subjectName.toUpperCase()))) {
                messageValidSubjectsByLevel = String.format("%s %s: %s",
                        MESSAGE_VALID_SUBJECTS_BASE, level, validSubjects);
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Subject)) {
            return false;
        }

        Subject otherSubject = (Subject) other;
        return subjectName.equals(otherSubject.subjectName);
    }

    @Override
    public int hashCode() {
        return subjectName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + subjectName + ']';
    }

}
