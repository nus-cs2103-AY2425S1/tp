package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.issue.Issue;

/**
 * Jackson-friendly version of {@link Issue}.
 */
class JsonAdaptedIssue {

    private final String issueName;

    /**
     * Constructs a {@code JsonAdaptedIssue} with the given {@code issueName}.
     */
    @JsonCreator
    public JsonAdaptedIssue(String issueName) {
        this.issueName = issueName;
    }

    /**
     * Converts a given {@code Issue} into this class for Jackson use.
     */
    public JsonAdaptedIssue(Issue source) {
        issueName = source.issueName;
    }

    @JsonValue
    public String getIssueName() {
        return issueName;
    }

    /**
     * Converts this Jackson-friendly adapted issue object into the model's {@code Issue} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted issue.
     */
    public Issue toModelType() throws IllegalValueException {
        if (!Issue.isValidIssueName(issueName)) {
            throw new IllegalValueException(Issue.MESSAGE_CONSTRAINTS);
        }
        return new Issue(issueName);
    }

}
