package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CountCommandTest {

    @Test
    public void execute_countPerson_success() {
        Model model = new ModelManager();
        int expectedCount = model.getFilteredPersonList().size();
        CountCommand countCommand = new CountCommand();
        CommandResult result = countCommand.execute(model);
        assertEquals(String.format(CountCommand.MESSAGE_COUNT_PERSONS, expectedCount), result.getFeedbackToUser());
    }
}

