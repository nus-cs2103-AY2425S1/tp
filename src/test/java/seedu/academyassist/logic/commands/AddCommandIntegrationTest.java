package seedu.academyassist.logic.commands;

import static seedu.academyassist.logic.Messages.MESSAGE_DUPLICATE_IC;
import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.academyassist.testutil.TypicalPersons.getTypicalAcademyAssist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.academyassist.logic.Messages;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.ModelManager;
import seedu.academyassist.model.UserPrefs;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.SubjectEnum;
import seedu.academyassist.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAcademyAssist(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder("S00008")
                .withSubjects(SubjectEnum.MATH.getSubjectName(), SubjectEnum.ENGLISH.getSubjectName())
                .build();

        Model expectedModel = new ModelManager(model.getAcademyAssist(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedModel);
    }
    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAcademyAssist().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, MESSAGE_DUPLICATE_IC);
    }

}
