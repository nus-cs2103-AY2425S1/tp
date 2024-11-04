package seedu.address.logic.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleObjectProperty;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.util.SampleAssignmentsUtil;
import seedu.address.testutil.TypicalPersons;

class ViewCommandTest {
    private final Model model = new ModelManager(
            getTypicalAddressBook(),
            new UserPrefs(), SampleAssignmentsUtil.getSamplePredefinedAssignments());
    @Test
    public void equalsHandleCorrectly() {
        ViewCommand test = new ViewCommand(new Name("Amy"));
        assertEquals(test, new ViewCommand(new Name("Amy")));
        assertNotEquals(test, new ViewCommand(new Name("John")));
        assertFalse(test.equals(new Object()));
    }

    @Test
    public void equalsHandleNotSame() {
        ViewCommand test = new ViewCommand(new Name("Amy"));
        assertNotEquals(test, new ViewCommand(new Name("John")));
    }

    @Test
    public void equalsHandleNullPerson() {
        assertNotEquals(new ViewCommand(new Name("Amy")), ViewCommand.closeView());
    }

    @Test
    public void viewCommandExecutesCorrectly() throws CommandException {
        ViewCommand viewCommand = new ViewCommand(TypicalPersons.ALICE.getName());
        assertEquals(
                viewCommand.execute(model),
                new CommandResult(
                        ViewCommand.VIEW_ACKNOWLEDGMENT,
                        new SimpleObjectProperty<>(TypicalPersons.ALICE),
                        false));
    }

    @Test
    public void viewCommandHandlesNotFoundPerson() throws CommandException {
        ViewCommand viewCommand = new ViewCommand(new Name("KJohn"));
        assertThrows(CommandException.class, () -> viewCommand.execute(model));
    }

    @Test
    public void handleCloseTest() throws CommandException {
        assertEquals(ViewCommand.closeView().execute(model), ViewCommand.getCloseViewResult());
    }
}
