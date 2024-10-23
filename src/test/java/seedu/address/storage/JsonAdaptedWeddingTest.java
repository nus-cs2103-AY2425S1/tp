package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedWedding.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingName;

public class JsonAdaptedWeddingTest {
    private static final String INVALID_WEDDING_NAME = "R@chel's Wedding";
    private static final String INVALID_WEDDING_DATE = "Tomorrow";
    private static final String INVALID_ASSIGNEES = "String";

    private static final String VALID_WEDDING_NAME = WEDDING_ONE.getWeddingName().toString();
    private static final String VALID_WEDDING_DATE = WEDDING_ONE.getWeddingDate().toString();
    private static final List<JsonAdaptedPersonId> VALID_ASSIGNEES = WEDDING_ONE.getAssignees().stream()
            .map(JsonAdaptedPersonId::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validWeddingDetails_returnsWedding() throws Exception {
        JsonAdaptedWedding person = new JsonAdaptedWedding(WEDDING_ONE);
        assertEquals(WEDDING_ONE, person.toModelType());
    }

    @Test
    public void toModelType_invalidWeddingName_throwsIllegalValueException() {
        JsonAdaptedWedding wedding =
                new JsonAdaptedWedding(INVALID_WEDDING_NAME, VALID_WEDDING_DATE, VALID_ASSIGNEES);
        String expectedMessage = WeddingName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, wedding::toModelType);
    }

    @Test
    public void toModelType_nullWeddingName_throwsIllegalValueException() {
        JsonAdaptedWedding wedding =
                new JsonAdaptedWedding(null, VALID_WEDDING_DATE, VALID_ASSIGNEES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, WeddingName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, wedding::toModelType);
    }

    @Test
    public void toModelType_invalidWeddingDate_throwsIllegalValueException() {
        JsonAdaptedWedding wedding =
                new JsonAdaptedWedding(VALID_WEDDING_NAME, INVALID_WEDDING_DATE, VALID_ASSIGNEES);
        String expectedMessage = WeddingDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, wedding::toModelType);
    }

    @Test
    public void toModelType_nullWeddingDate_throwsIllegalValueException() {
        JsonAdaptedWedding wedding =
                new JsonAdaptedWedding(VALID_WEDDING_NAME, null, VALID_ASSIGNEES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, WeddingDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, wedding::toModelType);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        List<JsonAdaptedPersonId> invalidIds = new ArrayList<>(VALID_ASSIGNEES);
        invalidIds.add(new JsonAdaptedPersonId(INVALID_ASSIGNEES));
        JsonAdaptedPersonId id =
                new JsonAdaptedPersonId(INVALID_ASSIGNEES);
        assertThrows(IllegalValueException.class, id::toModelType);
    }

}
