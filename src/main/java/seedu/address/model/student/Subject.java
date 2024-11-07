package seedu.address.model.student;

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
            Subjects.ENGLISH, Subjects.A_MATH, Subjects.E_MATH, Subjects.MATH, Subjects.PHYSICS, Subjects.CHEMISTRY,
            Subjects.BIOLOGY, Subjects.COMBINED_SCIENCE, Subjects.GEOGRAPHY, Subjects.HISTORY, Subjects.LITERATURE,
            Subjects.ACCOUNTING, Subjects.SOCIAL_STUDIES, Subjects.CHINESE, Subjects.HIGHER_CHINESE, Subjects.MALAY,
            Subjects.HIGHER_MALAY, Subjects.TAMIL, Subjects.HIGHER_TAMIL, Subjects.HINDI, Subjects.MUSIC, Subjects.ART
    );
    static {
        validSubjectsByLevel.put(new Level("S1 EXPRESS"), validLowerSecondarySubjects);
        validSubjectsByLevel.put(new Level("S1 NA"), validLowerSecondarySubjects);
        validSubjectsByLevel.put(new Level("S1 NT"), validLowerSecondarySubjects);
        validSubjectsByLevel.put(new Level("S1 IP"), validLowerSecondarySubjects);

        validSubjectsByLevel.put(new Level("S2 EXPRESS"), validLowerSecondarySubjects);
        validSubjectsByLevel.put(new Level("S2 NA"), validLowerSecondarySubjects);
        validSubjectsByLevel.put(new Level("S2 NT"), validLowerSecondarySubjects);
        validSubjectsByLevel.put(new Level("S2 IP"), validLowerSecondarySubjects);

        validSubjectsByLevel.put(new Level("S3 EXPRESS"), validUpperSecondarySubjects);
        validSubjectsByLevel.put(new Level("S3 NA"), validUpperSecondarySubjects);
        validSubjectsByLevel.put(new Level("S3 NT"), validUpperSecondarySubjects);
        validSubjectsByLevel.put(new Level("S3 IP"), validUpperSecondarySubjects);

        validSubjectsByLevel.put(new Level("S4 EXPRESS"), validUpperSecondarySubjects);
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
     * @throws NullPointerException if subjectName is null.
     * @throws IllegalArgumentException if subjectName is invalid.
     */
    public Subject(String subjectName) {
        requireNonNull(subjectName);
        checkArgument(isValidSubjectName(subjectName), MESSAGE_CONSTRAINTS);
        this.subjectName = subjectName.toUpperCase();
    }

    /**
     * Checks if a subject name is valid for a given level.
     *
     * @param level The level to validate against.
     * @param subjectName The subject name to validate.
     * @return true if subjectName is valid for the specified level, false otherwise.
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

    /**
     * Retrieves the last generated validation message for valid subjects by level.
     *
     * @return The current validation message.
     */
    public static String getValidSubjectMessage() {
        return messageValidSubjectsByLevel;
    }

    /**
     * Retrieves the valid subject message for a given level.
     *
     * @param level The level for which to get valid subjects.
     * @return A message listing valid subjects for the level, or a message indicating a level is required.
     */
    public static String getValidSubjectMessage(Level level) {
        if (level == null || level.levelName.equals("NONE NONE")) {
            return Subject.MESSAGE_LEVEL_NEEDED;
        }

        EnumSet<Subjects> validSubjects = validSubjectsByLevel.get(level);
        messageValidSubjectsByLevel = String.format("%s %s: %s",
                MESSAGE_VALID_SUBJECTS_BASE, level, validSubjects);
        return messageValidSubjectsByLevel;
    }

    /**
     * Checks if a given string is a valid subject name.
     *
     * @param subjectName The subject name to validate.
     * @return true if subjectName is valid, false otherwise.
     */
    public static boolean isValidSubjectName(String subjectName) {
        requireNonNull(subjectName);
        return inEnum(subjectName, Subjects.class);
    }

    /**
     * Checks if all subjects in a set are valid for a given level.
     *
     * @param level The level to validate against.
     * @param subjects A set of subjects to validate.
     * @return true if all subjects are valid for the specified level, false otherwise.
     */
    public static boolean isValidSubjectsByLevel(Level level, Set<Subject> subjects) {
        if (level == null || level.levelName.equals("NONE NONE")) {
            messageValidSubjectsByLevel = Subject.MESSAGE_LEVEL_NEEDED;
            return false;
        }
        EnumSet<Subjects> validSubjects = validSubjectsByLevel.get(level);
        for (Subject s : subjects) {
            if (!validSubjects.contains(Subjects.valueOf(s.subjectName))) {
                messageValidSubjectsByLevel = String.format("%s %s: %s",
                        MESSAGE_VALID_SUBJECTS_BASE, level, validSubjects);
                return false;
            }
        }

        return true;
    }

    /**
     * Compares this subject to the specified object for equality.
     *
     * @param other The object to compare to.
     * @return true if the specified object is equal to this subject, false otherwise.
     */
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

    /**
     * Returns the hash code for this subject.
     *
     * @return The hash code value of this subject.
     */
    @Override
    public int hashCode() {
        return subjectName.hashCode();
    }

    /**
     * Formats this subject as a string for viewing.
     *
     * @return A string representation of this subject.
     */
    public String toString() {
        return '[' + subjectName + ']';
    }

}
