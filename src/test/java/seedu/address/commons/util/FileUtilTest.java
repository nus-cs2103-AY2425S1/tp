package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class FileUtilTest {

    @Test
    public void isValidPath() {
        // valid path
        assertTrue(FileUtil.isValidPath("valid/file/path"));

        // invalid path
        assertFalse(FileUtil.isValidPath("a\0"));

        // null path -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));
    }

    @Test
    public void createFile_createPrentDirsOfFile_isFileExists() throws IOException {
        FileUtil ff = new FileUtil();
        Path path = Path.of("/tmp/" + new Random().nextInt() + "/"
            + new Random().nextInt() + ".txt");

        // file does not exist 00
        assertFalse(FileUtil.isFileExists(path));

        // file exist not regular 10
        assertFalse(FileUtil.isFileExists(Path.of("/dev")));

        // create file does not exist
        FileUtil.createFile(path);

        // file exists and regular 11
        assertTrue(FileUtil.isFileExists(path));

        // parent directory null
        FileUtil.createParentDirsOfFile(Path.of("/"));

        //create file already exists
        FileUtil.createFile(path);

        path.toFile().delete();
    }
}
