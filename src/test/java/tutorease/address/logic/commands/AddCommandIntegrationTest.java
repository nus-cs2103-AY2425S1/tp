package tutorease.address.logic.commands;

import static tutorease.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutorease.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorease.address.testutil.TypicalLessons.getTypicalLessons;
import static tutorease.address.testutil.TypicalStudents.getTypicalTutorEase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tutorease.address.logic.Messages;
import tutorease.address.model.Model;
import tutorease.address.model.ModelManager;
import tutorease.address.model.UserPrefs;
import tutorease.address.model.person.Person;
import tutorease.address.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddContactCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTutorEase(), new UserPrefs(), getTypicalLessons());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new StudentBuilder().build();

        Model expectedModel = new ModelManager(model.getTutorEase(), new UserPrefs(), model.getLessonSchedule());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddContactCommand(validPerson), model,
                String.format(AddContactCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getTutorEase().getPersonList().get(0);
        assertCommandFailure(new AddContactCommand(personInList), model,
                AddContactCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
