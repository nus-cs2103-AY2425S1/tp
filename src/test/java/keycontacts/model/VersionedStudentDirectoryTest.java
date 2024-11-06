package keycontacts.model;

import org.junit.jupiter.api.Test;

import static keycontacts.testutil.TypicalStudents.getTypicalStudentDirectory;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VersionedStudentDirectoryTest {

    private final VersionedStudentDirectory versionedStudentDirectory =
            new VersionedStudentDirectory(getTypicalStudentDirectory());

    @Test
    public void constructor() {
        assertEquals(getTypicalStudentDirectory(), versionedStudentDirectory);
    }

    @Test
    public void commit() {
        versionedStudentDirectory.commit();

    }
}
