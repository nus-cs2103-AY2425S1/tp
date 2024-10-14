package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class AddGradeCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullAssignmentFormat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddGradeCommand(null, 0f, null));
    }


    @Test
    public void constructor_validAddGradeCommandFormat_success() {
        AddGradeCommand command = new AddGradeCommand("John Doe", 9.0f, "Ex09");
        assertNotNull(command);
    }

    @Test
    public void execute_validPersonGrade_success() throws Exception {
        Person testPerson = TypicalPersons.ALICE;
        AddGradeCommand command = new AddGradeCommand(
                testPerson.getName().toString(),
                9.0f,
                "Ex09");
        command.execute(model);
        assertEquals(model
                .getAddressBook()
                .getPersonList()
                .stream().filter(person -> person
                        .getName()
                        .equals(testPerson.getName())).toList().get(0).getAssignment().getScore(), 9.0f);

    }
}
