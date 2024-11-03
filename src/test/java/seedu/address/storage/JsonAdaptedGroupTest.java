package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


public class JsonAdaptedGroupTest {

    private static final String VALID_GROUP_NAME = "StudyGroup1";
    private static final String INVALID_GROUP_NAME = null;

    private static final Person VALID_PERSON_1 = new PersonBuilder().withName("Alice").build();
    private static final Person VALID_PERSON_2 = new PersonBuilder().withName("Bob").build();

    private static final JsonAdaptedPerson VALID_JSON_PERSON_1 = new JsonAdaptedPerson(VALID_PERSON_1);
    private static final JsonAdaptedPerson VALID_JSON_PERSON_2 = new JsonAdaptedPerson(VALID_PERSON_2);

    private static final List<JsonAdaptedPerson> VALID_MEMBERS =
            Arrays.asList(VALID_JSON_PERSON_1, VALID_JSON_PERSON_2);

    @Test
    public void toModelType_validGroupDetails_returnsGroup() throws Exception {
        JsonAdaptedGroup jsonAdaptedGroup = new JsonAdaptedGroup(VALID_GROUP_NAME, VALID_MEMBERS);
        Group group = jsonAdaptedGroup.toModelType();
        assertEquals(VALID_GROUP_NAME, group.getGroupName().toString());
        assertEquals(Arrays.asList(VALID_PERSON_1, VALID_PERSON_2), group.getMembers());
    }

    @Test
    public void toModelType_nullGroupName_throwsIllegalValueException() {
        JsonAdaptedGroup jsonAdaptedGroup = new JsonAdaptedGroup(INVALID_GROUP_NAME, VALID_MEMBERS);
        String expectedMessage = String.format(JsonAdaptedGroup.MISSING_FIELD_MESSAGE_FORMAT, "GroupName");
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedGroup::toModelType);
    }

    @Test
    public void toModelType_nullMembers_returnsGroupWithEmptyMembers() throws Exception {
        JsonAdaptedGroup jsonAdaptedGroup = new JsonAdaptedGroup(VALID_GROUP_NAME, null);
        Group group = jsonAdaptedGroup.toModelType();
        assertEquals(VALID_GROUP_NAME, group.getGroupName().toString());
        assertEquals(Collections.emptyList(), group.getMembers());
    }

    @Test
    public void toModelType_invalidMember_throwsIllegalValueException() {
        JsonAdaptedPerson invalidJsonPerson = new JsonAdaptedPerson(
                null, "A123", "98765432", Collections.emptyList());
        List<JsonAdaptedPerson> invalidMembers = Collections.singletonList(invalidJsonPerson);
        JsonAdaptedGroup jsonAdaptedGroup = new JsonAdaptedGroup(VALID_GROUP_NAME, invalidMembers);
        assertThrows(IllegalValueException.class, jsonAdaptedGroup::toModelType);
    }
}
