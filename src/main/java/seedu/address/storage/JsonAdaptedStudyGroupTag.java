package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.StudyGroupTag;

/**
 * Jackson-friendly version of {@link StudyGroupTag}.
 */
class JsonAdaptedStudyGroupTag {

    private final String studyGroupName;

    /**
     * Constructs a {@code JsonAdaptedStudyGroupTag}
     * with the given {@code studyGroupName}.
     */
    @JsonCreator
    public JsonAdaptedStudyGroupTag(String studyGroupName) {
        this.studyGroupName = studyGroupName;
    }

    /**
     * Converts a given {@code StudyGroupTag}
     * into this class for Jackson use.
     */
    public JsonAdaptedStudyGroupTag(StudyGroupTag source) {
        studyGroupName = source.studyGroupName;
    }

    @JsonValue
    public String getStudyGroupName() {
        return studyGroupName;
    }

    /**
     * Converts this Jackson-friendly
     * adapted tag object into the model's
     * {@code StudyGroupTag} object.
     *
     * @throws IllegalValueException if there were
     *              any data constraints violated in
     *              the adapted study group tag.
     */
    public StudyGroupTag toModelType() throws IllegalValueException {
        if (!StudyGroupTag.isValidStudyGroupName(studyGroupName)) {
            throw new IllegalValueException(StudyGroupTag.MESSAGE_CONSTRAINTS);
        }
        return new StudyGroupTag(studyGroupName);
    }

}
