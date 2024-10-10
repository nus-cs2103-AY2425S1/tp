package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.skill.Skill;

/**
 * Jackson-friendly version of {@link Skill}.
 */
class JsonAdaptedSkill {

    private final String skill;

    /**
     * Constructs a {@code JsonAdaptedSkill} with the given {@code skill}.
     */
    @JsonCreator
    public JsonAdaptedSkill(String skill) {
        this.skill = skill;
    }

    /**
     * Converts a given {@code Skill} into this class for Jackson use.
     */
    public JsonAdaptedSkill(Skill source) {
        skill = source.skill;
    }

    @JsonValue
    public String getSkill() {
        return skill;
    }

    /**
     * Converts this Jackson-friendly adapted skill object into the model's {@code Skill} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted skill.
     */
    public Skill toModelType() throws IllegalValueException {
        if (!Skill.isValidSkill(skill)) {
            throw new IllegalValueException(Skill.MESSAGE_CONSTRAINTS);
        }
        return new Skill(skill);
    }

}
