package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Remark;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

public class RemarkCommandTest {
    private Model model = new ModelManager();

    @Test
    public void execute() {
        final Remark remark = new Remark("Some remark");

        //assertCommandSuccess(new RemarkCommand(INDEX_FIRST_PERSON, remark), model,
        //        String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), remark));
    }


}
