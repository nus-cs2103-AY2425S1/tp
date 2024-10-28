package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_3A;
import static seedu.address.logic.commands.CommandTestUtil.DESC_3B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDY_GROUP_TAG_1A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNUSED_STUDY_GROUP_TAG_3A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNUSED_STUDY_GROUP_TAG_3B;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonWithStudyGroupTag;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AssignCommand.AssignStudyGroupTagDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.StudyGroupTag;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AssignCommand.
 */
public class AssignCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model controlModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_twoStudyGroupSpecifiedUnfilteredList_success() {
        AssignStudyGroupTagDescriptor descriptor3A = new AssignStudyGroupTagDescriptor();
        descriptor3A.setStudyGroupTag(new StudyGroupTag(VALID_UNUSED_STUDY_GROUP_TAG_3A));
        AssignStudyGroupTagDescriptor descriptor3B = new AssignStudyGroupTagDescriptor();
        descriptor3B.setStudyGroupTag(new StudyGroupTag(VALID_UNUSED_STUDY_GROUP_TAG_3B));

        List<AssignStudyGroupTagDescriptor> descriptors = Arrays.asList(descriptor3A, descriptor3B);
        AssignCommand assignCommand = new AssignCommand(descriptors);

        String expectedMessage = AssignCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(assignCommand, model, expectedMessage, model);

        for (int i = 0; i < model.getFilteredPersonList().size(); i++) {
            assertNotEquals(model.getFilteredPersonList().get(i), controlModel.getFilteredPersonList().get(i));

        }
    }

    @Test
    public void execute_twoStudyGroupSpecifiedFilteredList_success() {
        showPersonWithStudyGroupTag(model, VALID_STUDY_GROUP_TAG_1A);

        AssignStudyGroupTagDescriptor descriptor3A = new AssignStudyGroupTagDescriptor();
        descriptor3A.setStudyGroupTag(new StudyGroupTag(VALID_UNUSED_STUDY_GROUP_TAG_3A));
        AssignStudyGroupTagDescriptor descriptor3B = new AssignStudyGroupTagDescriptor();
        descriptor3B.setStudyGroupTag(new StudyGroupTag(VALID_UNUSED_STUDY_GROUP_TAG_3B));

        List<AssignStudyGroupTagDescriptor> descriptors = Arrays.asList(descriptor3A, descriptor3B);
        AssignCommand assignCommand = new AssignCommand(descriptors);

        String expectedMessage = AssignCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(assignCommand, model, expectedMessage, model);

        for (int i = 0; i < model.getFilteredPersonList().size(); i++) {
            assertNotEquals(model.getFilteredPersonList().get(i), controlModel.getFilteredPersonList().get(i));

        }
    }

    @Test
    public void execute_oneStudyGroupSpecifiedUnfilteredList_success() {
        AssignStudyGroupTagDescriptor descriptor3A = new AssignStudyGroupTagDescriptor();
        descriptor3A.setStudyGroupTag(new StudyGroupTag(VALID_UNUSED_STUDY_GROUP_TAG_3A));

        List<AssignStudyGroupTagDescriptor> descriptors = Collections.singletonList(descriptor3A);
        AssignCommand assignCommand = new AssignCommand(descriptors);

        String expectedMessage = AssignCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        for (Person p : expectedModel.getFilteredPersonList()) {
            PersonBuilder personInList = new PersonBuilder(p);
            Person editedPerson = personInList.withAppendStudyGroupTags(VALID_UNUSED_STUDY_GROUP_TAG_3A).build();
            expectedModel.setPerson(p, editedPerson);
        }

        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_oneStudyGroupSpecifiedFilteredList_success() {
        showPersonWithStudyGroupTag(model, VALID_STUDY_GROUP_TAG_1A);

        AssignStudyGroupTagDescriptor descriptor3A = new AssignStudyGroupTagDescriptor();
        descriptor3A.setStudyGroupTag(new StudyGroupTag(VALID_UNUSED_STUDY_GROUP_TAG_3A));

        List<AssignStudyGroupTagDescriptor> descriptors = Collections.singletonList(descriptor3A);
        AssignCommand assignCommand = new AssignCommand(descriptors);

        String expectedMessage = AssignCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        showPersonWithStudyGroupTag(expectedModel, VALID_STUDY_GROUP_TAG_1A);

        for (Person p : expectedModel.getFilteredPersonList()) {
            PersonBuilder personInList = new PersonBuilder(p);
            Person editedPerson = personInList.withAppendStudyGroupTags(VALID_UNUSED_STUDY_GROUP_TAG_3A).build();
            expectedModel.setPerson(p, editedPerson);
        }

        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_existingStudyGroupUnfilteredList_failure() {
        AssignStudyGroupTagDescriptor descriptor1A = new AssignStudyGroupTagDescriptor();
        descriptor1A.setStudyGroupTag(new StudyGroupTag(VALID_STUDY_GROUP_TAG_1A));

        List<AssignStudyGroupTagDescriptor> descriptors = Collections.singletonList(descriptor1A);
        AssignCommand assignCommand = new AssignCommand(descriptors);

        assertCommandFailure(assignCommand, model, AssignCommand.MESSAGE_EXISTING_STUDYGROUP);
    }

    @Test
    public void execute_existingStudyGroupFilteredList_failure() {
        showPersonWithStudyGroupTag(model, VALID_STUDY_GROUP_TAG_1A);

        AssignStudyGroupTagDescriptor descriptor1A = new AssignStudyGroupTagDescriptor();
        descriptor1A.setStudyGroupTag(new StudyGroupTag(VALID_STUDY_GROUP_TAG_1A));

        List<AssignStudyGroupTagDescriptor> descriptors = Collections.singletonList(descriptor1A);
        AssignCommand assignCommand = new AssignCommand(descriptors);

        assertCommandFailure(assignCommand, model, AssignCommand.MESSAGE_EXISTING_STUDYGROUP);
    }

    @Test
    public void equals() {
        final AssignCommand standardCommand = new AssignCommand(Collections.singletonList(DESC_3A));

        // same values -> returns true
        AssignStudyGroupTagDescriptor copyDescriptor = new AssignStudyGroupTagDescriptor(DESC_3A);
        AssignCommand commandWithSameValues = new AssignCommand(Collections.singletonList(copyDescriptor));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new AssignCommand(Collections.singletonList(DESC_3B))));
    }

    @Test
    public void toStringMethod() {
        AssignStudyGroupTagDescriptor assignStudyGroupTagDescriptor = new AssignStudyGroupTagDescriptor(DESC_3A);
        AssignCommand assignCommand = new AssignCommand(Collections.singletonList(assignStudyGroupTagDescriptor));
        String expected = AssignCommand.class.getCanonicalName() + "{possible assignments=[" + DESC_3A + "]}";
        assertEquals(expected, assignCommand.toString());
    }
}
