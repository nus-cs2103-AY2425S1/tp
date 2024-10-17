package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Grade;

public class AddGradeCommandTest {
    private static final Index VALID_INDEX = Index.fromOneBased(16);
    private static final Grade VALID_GRADE = new Grade("Midterm", 95F, 30F);

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddGradeCommand(null, VALID_GRADE));
    }

    @Test
    public void constructor_nullGrade_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddGradeCommand(VALID_INDEX, null));
    }
}
