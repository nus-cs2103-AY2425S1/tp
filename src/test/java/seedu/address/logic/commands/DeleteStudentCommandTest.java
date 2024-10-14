package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;


import org.junit.jupiter.api.Test;

public class DeleteStudentCommandTest {
    @Test
    public void constructor_nullStudentNumber_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteStudentCommand(null));
    }
}
