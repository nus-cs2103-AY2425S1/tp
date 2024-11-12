package tuteez.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tuteez.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.Model;
import tuteez.model.ModelManager;

public class LessonCommandTest {
    /**
     * A concrete implementation of LessonCommand for testing constructor behavior.
     */
    private class ConcreteLessonCommand extends LessonCommand {
        ConcreteLessonCommand(Index personIndex) {
            super(personIndex);
        }

        @Override
        public CommandResult execute(Model model) throws CommandException {
            return new CommandResult("Test command");
        }
    }

    @Test
    public void constructor_validIndex_success() {
        Index validIndex = Index.fromOneBased(1);
        LessonCommand command = new ConcreteLessonCommand(validIndex);
        assertEquals(validIndex, command.personIndex, "Constructor should properly set the person index");
    }

    @Test
    public void execute_mustBeImplementedBySubclass() throws Exception {
        LessonCommand lessonCommand = new LessonCommandImpl(Index.fromOneBased(1));
        Model model = new ModelManager();
        assertThrows(AssertionError.class, () -> lessonCommand.execute(model));
    }

    private class LessonCommandImpl extends LessonCommand {
        public LessonCommandImpl(Index personIndex) {
            super(personIndex);
        }

        @Override
        public CommandResult execute(Model model) throws CommandException {
            throw new AssertionError("This method should not be called.");
        }
    }
}
