package keycontacts.model;

import static keycontacts.testutil.TypicalStudents.getTypicalStudentDirectory;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class VersionedStudentDirectoryTest {

    private final VersionedStudentDirectory versionedStudentDirectory =
            new VersionedStudentDirectory(getTypicalStudentDirectory());

    @Test
    public void constructor() {
        assertEquals(getTypicalStudentDirectory(), versionedStudentDirectory);
    }

    @Test
    public void test() throws Exception {
        versionedStudentDirectory.setStudents(List.of());
        versionedStudentDirectory.commit();

        // test undo
        versionedStudentDirectory.undo();
        assertEquals(getTypicalStudentDirectory(), versionedStudentDirectory);

        // test redo
        versionedStudentDirectory.redo();
        assertEquals(new StudentDirectory(), versionedStudentDirectory);
    }
}
