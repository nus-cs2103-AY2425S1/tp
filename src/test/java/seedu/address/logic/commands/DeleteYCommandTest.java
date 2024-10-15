package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.StaticContext;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class DeleteYCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_confirmDeletion_success() throws CommandException {
        // Ensure the person to delete is part of the model's filtered person list
        Person personToDelete = model.getFilteredPersonList().get(0);
        StaticContext.setPersonToDelete(personToDelete);

        DeleteYCommand deleteYCommand = new DeleteYCommand(personToDelete);
        CommandResult commandResult = deleteYCommand.execute(model);

        assertCommandSuccess(deleteYCommand, model, String.format(DeleteYCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete), model);
    }
}