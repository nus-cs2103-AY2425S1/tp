package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.project.Id;
import seedu.address.model.project.Name;
import seedu.address.testutil.TypicalProjects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedProject.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProjects.BETA;

public class JsonAdaptedProjectTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_ID = "+651234";

    private static final String VALID_NAME = BETA.getName().toString();
    private static final String VALID_ID = BETA.getId().toString();

    @Test
    public void toModelType_validProjectDetails_returnsProject() throws Exception {
        JsonAdaptedProject project = new JsonAdaptedProject(BETA);
        assertEquals(BETA, project.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedProject project =
                new JsonAdaptedProject(INVALID_NAME, VALID_ID);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedProject project = new JsonAdaptedProject(null, VALID_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedProject project =
                new JsonAdaptedProject(VALID_NAME, INVALID_ID);
        String expectedMessage = Id.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedProject project = new JsonAdaptedProject(VALID_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Id.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }
}
