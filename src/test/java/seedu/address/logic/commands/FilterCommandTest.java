package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.getTypicalClinicConnectSystem;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalClinicConnectSystem(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalClinicConnectSystem(), new UserPrefs());

    @Test
    public void constructor_nullDateFilter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FilterCommand(null));
    }

    @Test
    public void constructor_nullStartDate_throwsNullPointerException() {
        int a = 1;
    }
}
