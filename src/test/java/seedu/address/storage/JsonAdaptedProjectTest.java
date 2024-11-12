package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedProject.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProjects.BETA;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.project.ProjectId;
import seedu.address.model.project.ProjectName;

public class JsonAdaptedProjectTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_ID = "+651234";
    private static final String VALID_NAME = BETA.getName().toString();
    private static final String VALID_ID = BETA.getId().toString();
    private static final List<JsonAdaptedSkill> VALID_SKILLS = BETA.getSkills().stream()
            .map(JsonAdaptedSkill::new)
            .collect(Collectors.toList());


    @Test
    public void toModelType_validProjectDetails_returnsProject() throws Exception {
        JsonAdaptedProject project = new JsonAdaptedProject(BETA);
        assertEquals(BETA, project.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedProject project =
                new JsonAdaptedProject(INVALID_NAME, VALID_ID, VALID_SKILLS);
        String expectedMessage = ProjectName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedProject project = new JsonAdaptedProject(null, VALID_ID, VALID_SKILLS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ProjectName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedProject project =
                new JsonAdaptedProject(VALID_NAME, INVALID_ID, VALID_SKILLS);
        String expectedMessage = ProjectId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedProject project = new JsonAdaptedProject(VALID_NAME, null, VALID_SKILLS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ProjectId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }
}
