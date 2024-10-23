package keycontacts.model.lesson;

/**
 * A class encapsulating lesson clash result and clashing lesson.
 */
public class ClashResult {
    private final boolean hasClash;
    private final Lesson clashingLesson;

    /**
     * Constructs a {@Code ClashResult} object.
     * @param hasClash True if there is a clash present.
     * @param clashingLesson The preexisting lesson that clashes.
     */
    public ClashResult(boolean hasClash, Lesson clashingLesson) {
        this.hasClash = hasClash;
        this.clashingLesson = clashingLesson;
    }

    public boolean hasClash() {
        return hasClash;
    }

    public Lesson getClashingLesson() {
        return clashingLesson;
    }
}
