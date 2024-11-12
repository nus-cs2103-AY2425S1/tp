package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.shortcut.Alias;
import seedu.address.model.shortcut.FullTagName;
import seedu.address.model.shortcut.ShortCut;

class JsonAdaptedShortCutTest {

    private static final String VALID_ALIAS = "v";
    private static final String VALID_FULL_TAG_NAME = "Vegan";
    private static final String INVALID_ALIAS = "v@";
    private static final String INVALID_FULL_TAG_NAME = ""; // Invalid because empty

    @Test
    void toModelType_validShortCut_returnsShortCut() throws Exception {
        ShortCut validShortCut = new ShortCut(new Alias(VALID_ALIAS), new FullTagName(VALID_FULL_TAG_NAME));
        JsonAdaptedShortCut adaptedShortCut = new JsonAdaptedShortCut(validShortCut);

        // Check that the adapted shortcut correctly converts back to the model shortcut
        assertEquals(validShortCut, adaptedShortCut.toModelType());
    }

    @Test
    void toModelType_invalidAlias_throwsIllegalValueException() {
        // Create a JsonAdaptedShortCut with an invalid alias
        JsonAdaptedShortCut adaptedShortCut = new JsonAdaptedShortCut(INVALID_ALIAS, VALID_FULL_TAG_NAME);

        // Expect an IllegalValueException due to invalid alias
        assertThrows(IllegalValueException.class, Alias.MESSAGE_CONSTRAINTS, adaptedShortCut::toModelType);
    }

    @Test
    void toModelType_invalidFullTagName_throwsIllegalValueException() {
        // Create a JsonAdaptedShortCut with an invalid full tag name
        JsonAdaptedShortCut adaptedShortCut = new JsonAdaptedShortCut(VALID_ALIAS, INVALID_FULL_TAG_NAME);

        // Expect an IllegalValueException due to invalid full tag name
        assertThrows(IllegalValueException.class, FullTagName.MESSAGE_CONSTRAINTS, adaptedShortCut::toModelType);
    }

    @Test
    void toModelType_nullAlias_throwsIllegalValueException() {
        // Create a JsonAdaptedShortCut with a null alias
        JsonAdaptedShortCut adaptedShortCut = new JsonAdaptedShortCut(null, VALID_FULL_TAG_NAME);

        // Expect an IllegalValueException due to missing alias
        assertThrows(IllegalValueException.class, String.format(JsonAdaptedShortCut.MISSING_FIELD_MESSAGE_FORMAT,
                Alias.class.getSimpleName()), adaptedShortCut::toModelType);
    }

    @Test
    void toModelType_nullFullTagName_throwsIllegalValueException() {
        // Create a JsonAdaptedShortCut with a null full tag name
        JsonAdaptedShortCut adaptedShortCut = new JsonAdaptedShortCut(VALID_ALIAS, null);

        // Expect an IllegalValueException due to missing full tag name
        assertThrows(IllegalValueException.class, String.format(JsonAdaptedShortCut.MISSING_FIELD_MESSAGE_FORMAT,
                FullTagName.class.getSimpleName()), adaptedShortCut::toModelType);
    }
}
