package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;

public class DeleteGradeCommandTest {
    private static final Index VALID_INDEX = Index.fromOneBased(16);
    private static final String VALID_TEST_NAME = "Midterm";

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteGradeCommand(null, VALID_TEST_NAME));
    }

    @Test
    public void constructor_nullTestIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteGradeCommand(VALID_INDEX, null));
    }

    @Test
    public void constructor_validIndexAndTestName_success() {
        new DeleteGradeCommand(VALID_INDEX, VALID_TEST_NAME);
    }
}
