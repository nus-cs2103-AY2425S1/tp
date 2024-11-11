package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalCampusConnect;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.exceptions.RedoException;
import seedu.address.model.exceptions.UndoException;

public class VersionedCampusConnectTest {
    @Test
    public void execute_extractUndoneDataEmptyStack_failure() {
        VersionedCampusConnect vcc = new VersionedCampusConnect();
        assertThrows(RedoException.class, vcc::extractUndoneData);
    }

    @Test
    public void execute_extractOldDataEmptyStack_failure() {
        VersionedCampusConnect vcc = new VersionedCampusConnect();
        assertThrows(UndoException.class, vcc::extractOldData);
    }

    @Test
    public void execute_extractOldData_success() throws CommandException {
        ReadOnlyCampusConnect data = getTypicalCampusConnect();
        VersionedCampusConnect vcc = new VersionedCampusConnect();
        vcc.saveOldData(data);

        ReadOnlyCampusConnect newData = vcc.extractOldData();
        assertEquals(newData, data);
    }

    @Test
    public void execute_extractUndoneData_success() throws CommandException {
        ReadOnlyCampusConnect data = getTypicalCampusConnect();
        VersionedCampusConnect vcc = new VersionedCampusConnect();
        vcc.saveCurrentData(data);

        ReadOnlyCampusConnect newData = vcc.extractUndoneData();
        assertEquals(newData, data);
    }
}
