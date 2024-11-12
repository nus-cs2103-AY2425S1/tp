package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWeddings.getTypicalWeddingBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearWeddingBookCommandTest {

    @Test
    public void execute_emptyWeddingBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearWeddingBookCommand(), model, ClearWeddingBookCommand.CONFIRMATION_PROMPT,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyWeddingBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalWeddingBook());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalWeddingBook());

        assertCommandSuccess(new ClearWeddingBookCommand(), model, ClearWeddingBookCommand.CONFIRMATION_PROMPT,
                expectedModel);
    }
}
