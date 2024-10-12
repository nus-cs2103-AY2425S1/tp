package seedu.sellsavvy.logic.commands.ordercommands;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;

public class AddOrderCommandTest {

    //TODO: Remove this
    @Test
    public void execute_newOrder_success() { // not a legit test
        Model model = new ModelManager();
        assertThrows(NullPointerException.class, () -> new AddOrderCommand(null, null).execute(model));
    }
}
