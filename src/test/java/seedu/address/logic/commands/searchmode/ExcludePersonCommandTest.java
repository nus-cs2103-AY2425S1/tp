package seedu.address.logic.commands.searchmode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ExcludePersonCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();


    }

    @Test
    public void execute_invalidIndices_throwsCommandException() {
        // invalid index
        Set<Index> invalidIndex = new HashSet<>();
        invalidIndex.add(Index.fromOneBased(7));
        assertThrows(CommandException.class, () -> new ExcludePersonCommand(invalidIndex).execute(model,
                model.getEventManager()));
    }

    @Test
    public void execute_validIndices_success() throws CommandException {
        // valid index
        Set<Index> validIndex = new HashSet<>();
        validIndex.add(Index.fromOneBased(1));
        model.addPerson(ALICE);
        CommandResult expectedCommandResult = new CommandResult(ExcludePersonCommand.MESSAGE_SUCCESS);
        CommandResult actualCommandResult = new ExcludePersonCommand(validIndex).execute(model,
                model.getEventManager());
        assertEquals(expectedCommandResult, actualCommandResult);
    }

    @Test
    public void getRemovedPersonIndices_validIndices_success() {
        Set<Index> validIndex = new HashSet<>();
        validIndex.add(Index.fromOneBased(1));
        ExcludePersonCommand excludePersonCommand = new ExcludePersonCommand(validIndex);
        assertEquals(validIndex, excludePersonCommand.getExcludedPersonsIndices());
    }
}
