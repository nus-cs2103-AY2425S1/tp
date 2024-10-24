package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.skill.Skill;

/**
 * Jackson-friendly version of {@link Skill}.
 */
class JsonAdaptedSkill {

    private final String skillName;

    /**
     * Constructs a {@code JsonAdaptedSkill} with the given {@code skillName}.
     */
    @JsonCreator
    public JsonAdaptedSkill(String skillName) {
        this.skillName = skillName;
    }

    /**
     * Converts a given {@code Skill} into this class for Jackson use.
     */
    public JsonAdaptedSkill(Skill source) {
        skillName = source.skillName;
    }

    @JsonValue
    public String getSkillName() {
        return skillName;
    }

    /**
     * Converts this Jackson-friendly adapted skill object into the model's {@code Skill} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted skill.
     */
    public Skill toModelType() throws IllegalValueException {
        if (!Skill.isValidSkillName(skillName)) {
            throw new IllegalValueException(Skill.MESSAGE_CONSTRAINTS);
        }
        return new Skill(skillName);
    }

}
