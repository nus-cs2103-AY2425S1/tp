package tahub.contacts.logic.commands.person;

import static tahub.contacts.logic.commands.CommandTestUtil.assertCommandFailure;
import static tahub.contacts.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tahub.contacts.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tahub.contacts.logic.Messages;
import tahub.contacts.model.Model;
import tahub.contacts.model.ModelManager;
import tahub.contacts.model.UserPrefs;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;
import tahub.contacts.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code PersonAddCommand}.
 */
public class PersonAddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UniqueCourseList(),
                new StudentCourseAssociationList());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getCourseList(),
                model.getScaList());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new PersonAddCommand(validPerson), model,
                String.format(PersonAddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new PersonAddCommand(personInList), model,
                PersonAddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
