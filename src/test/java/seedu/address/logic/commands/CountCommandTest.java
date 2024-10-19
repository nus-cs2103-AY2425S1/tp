package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CountCommandTest {

    @Test
    public void execute_countPerson_success_noFilter() throws CommandException {
        Model model = new ModelManager();
        int expectedCount = model.getFilteredPersonList().size();

        // Create CountCommand with no filters
        CountCommand countCommand = new CountCommand(Optional.empty(), Optional.empty());
        CommandResult result = countCommand.execute(model);

        assertEquals(String.format(CountCommand.MESSAGE_COUNT_PERSONS, expectedCount), result.getFeedbackToUser());
    }

    @Test
    public void execute_countPerson_success_withNamePrefix() throws CommandException {
        Model model = new ModelManager();
        String namePrefix = "Al";

        // Manually filter list for expected count
        int expectedCount = (int) model.getFilteredPersonList().stream()
                .filter(person -> person.getName().fullName.startsWith(namePrefix))
                .count();

        // Create CountCommand with name prefix filter
        CountCommand countCommand = new CountCommand(Optional.of(namePrefix), Optional.empty());
        CommandResult result = countCommand.execute(model);

        assertEquals(String.format(CountCommand.MESSAGE_COUNT_PERSONS, expectedCount), result.getFeedbackToUser());
    }

    @Test
    public void execute_countPerson_success_withTag() throws CommandException {
        Model model = new ModelManager();
        String tag = "friend";

        // Manually filter list for expected count
        int expectedCount = (int) model.getFilteredPersonList().stream()
                .filter(person -> person.getTags().stream()
                        .anyMatch(t -> t.tagName.equals(tag)))
                .count();

        // Create CountCommand with tag filter
        CountCommand countCommand = new CountCommand(Optional.empty(), Optional.of(tag));
        CommandResult result = countCommand.execute(model);

        assertEquals(String.format(CountCommand.MESSAGE_COUNT_PERSONS, expectedCount), result.getFeedbackToUser());
    }

    @Test
    public void execute_countPerson_noMatches() throws CommandException {
        Model model = new ModelManager();
        String namePrefix = "Zzz"; // unlikely to match

        // Create CountCommand with a prefix that yields no matches
        CountCommand countCommand = new CountCommand(Optional.of(namePrefix), Optional.empty());
        CommandResult result = countCommand.execute(model);

        assertEquals(CountCommand.MESSAGE_NO_MATCHES, result.getFeedbackToUser());
    }
}

