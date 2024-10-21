package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CountCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;
import seedu.address.model.UserPrefs;

public class CountCommandTest {

    @Test
    public void execute_noFilter_success() {
        Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

        CountCommand command = new CountCommand(Optional.empty(), Optional.empty());
        String expectedMessage = String.format(CountCommand.MESSAGE_COUNT_PERSONS, model.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_namePrefixFilter_success() {
        Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

        Optional<String> namePrefix = Optional.of("Al");
        CountCommand command = new CountCommand(namePrefix, Optional.empty());

        expectedModel.updateFilteredPersonList(person ->
                person.getName().fullName.toLowerCase().startsWith(namePrefix.get().toLowerCase()));
        String expectedMessage = String.format(CountCommand.MESSAGE_COUNT_PERSONS, expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagFilter_success() {
        Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

        Optional<String> tag = Optional.of("friend");
        CountCommand command = new CountCommand(Optional.empty(), tag);

        expectedModel.updateFilteredPersonList(person ->
                person.getTags().stream().anyMatch(t -> t.tagName.equalsIgnoreCase(tag.get())));
        String expectedMessage = String.format(CountCommand.MESSAGE_COUNT_PERSONS, expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
}



