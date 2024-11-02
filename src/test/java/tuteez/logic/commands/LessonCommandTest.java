package tuteez.logic.commands;

import static tuteez.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.Model;
import tuteez.model.ModelManager;

public class LessonCommandTest {

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
