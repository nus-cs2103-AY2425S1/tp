package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Policy;
import seedu.address.model.tag.Tag;



/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedPolicies {

    private final String policyDescription;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @com.fasterxml.jackson.annotation.JsonCreator
    public JsonAdaptedPolicies(String policyDescription) {
        this.policyDescription = policyDescription;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedPolicies(Policy source) {
        policyDescription = source.toString();
    }

    @com.fasterxml.jackson.annotation.JsonValue
    public String getPolicyDescription() {
        return policyDescription;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Policy toModelType() throws IllegalValueException {
        if (!Policy.isValidPolicy(policyDescription)) {
            throw new IllegalValueException(Policy.MESSAGE_CONSTRAINTS);
        }
        return new Policy(policyDescription);
    }

}
