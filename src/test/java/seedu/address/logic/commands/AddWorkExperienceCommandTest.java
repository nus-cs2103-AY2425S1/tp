package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.WorkExp;
import seedu.address.testutil.PersonBuilder;

public class AddWorkExperienceCommandTest {

    private static final WorkExp VALID_WORK_EXP = new WorkExp("Intern,Google,2024");
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_addWorkExperience_successful() throws Exception {
        Person personToEdit = model.getFilteredPersonList().get(0);
        Index index = Index.fromZeroBased(0);
        AddWorkExperienceCommand command = new AddWorkExperienceCommand(index, VALID_WORK_EXP);

        // Create the expected edited person
        Person editedPerson = new PersonBuilder(personToEdit).withWorkExp(VALID_WORK_EXP.toString()).build();

        // Execute command and verify output
        CommandResult result = command.execute(model);
        assertEquals(String.format(AddWorkExperienceCommand.MESSAGE_SUCCESS, personToEdit.getName(), VALID_WORK_EXP),
                result.getFeedbackToUser());

        // Verify that the person in the model has been updated
        assertEquals(editedPerson, model.getFilteredPersonList().get(0));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddWorkExperienceCommand command = new AddWorkExperienceCommand(outOfBoundIndex, VALID_WORK_EXP);

        assertThrows(CommandException.class, AddWorkExperienceCommand.MESSAGE_INVALID_INDEX, () ->
                command.execute(model));
    }

    @Test
    public void equals() {
        WorkExp workExp1 = new WorkExp("Intern,Google,2024");
        WorkExp workExp2 = new WorkExp("Engineer,Amazon,2023");
        AddWorkExperienceCommand command1 = new AddWorkExperienceCommand(Index.fromOneBased(1), workExp1);
        AddWorkExperienceCommand command2 = new AddWorkExperienceCommand(Index.fromOneBased(1), workExp2);
        AddWorkExperienceCommand command3 = new AddWorkExperienceCommand(Index.fromOneBased(2), workExp1);

        // Same object -> returns true
        assertTrue(command1.equals(command1));

        // Same values -> returns true
        AddWorkExperienceCommand command1Copy = new AddWorkExperienceCommand(Index.fromOneBased(1), workExp1);
        assertTrue(command1.equals(command1Copy));

        // Different types -> returns false
        assertFalse(command1.equals(1));

        // Null -> returns false
        assertFalse(command1.equals(null));

        // Different work experience -> returns false
        assertFalse(command1.equals(command2));

        // Different index -> returns false
        assertFalse(command1.equals(command3));
    }
}
