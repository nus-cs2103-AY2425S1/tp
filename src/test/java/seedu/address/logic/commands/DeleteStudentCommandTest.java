package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;


import org.junit.jupiter.api.Test;

public class DeleteStudentCommandTest {
    @Test
    public void constructor_nullStudentNumber_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteStudentCommand(null));
    }
}
