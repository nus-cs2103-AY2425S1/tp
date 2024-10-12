package seedu.sellsavvy.logic.commands.ordercommands;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;

public class AddOrderCommandTest {

    //TODO: Remove this
    @Test
    public void execute_newOrder_success() { // not a legit test
        Model model = new ModelManager();
        try {
            assertNull(new AddOrderCommand().execute(model));
        } catch (Exception e) {
            fail();
        }
    }
}
