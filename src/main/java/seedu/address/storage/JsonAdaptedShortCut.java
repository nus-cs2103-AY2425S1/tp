package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.shortcut.Alias;
import seedu.address.model.shortcut.FullTagName;
import seedu.address.model.shortcut.ShortCut;

/**
 * Jackson-friendly version of {@link ShortCut}.
 */
class JsonAdaptedShortCut {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Shortcut's %s field is missing!";

    private final String alias;
    private final String fullTagName;

    /**
     * Constructs a {@code JsonAdaptedShortCut} with the given shortcut details.
     */
    @JsonCreator
    public JsonAdaptedShortCut(@JsonProperty("alias") String alias,
                               @JsonProperty("fullTagName") String fullTagName) {
        this.alias = alias;
        this.fullTagName = fullTagName;
    }

    /**
     * Converts a given {@code ShortCut} into this class for Jackson use.
     */
    public JsonAdaptedShortCut(ShortCut source) {
        alias = source.getAlias().toString();
        fullTagName = source.getFullTagName().toString();
    }

    /**
     * Converts this Jackson-friendly adapted shortcut object into the model's {@code ShortCut} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted shortcut.
     */
    public ShortCut toModelType() throws IllegalValueException {

        if (alias == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Alias.class.getSimpleName()));
        }
        if (!Alias.isValidAlias(alias)) {
            throw new IllegalValueException(Alias.MESSAGE_CONSTRAINTS);
        }
        final Alias modelAlias = new Alias(alias);

        if (fullTagName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    FullTagName.class.getSimpleName()));
        }
        if (!FullTagName.isValidTagName(fullTagName)) {
            throw new IllegalValueException(FullTagName.MESSAGE_CONSTRAINTS);
        }
        final FullTagName modelFullTagName = new FullTagName(fullTagName);

        return new ShortCut(modelAlias, modelFullTagName);
    }
}
