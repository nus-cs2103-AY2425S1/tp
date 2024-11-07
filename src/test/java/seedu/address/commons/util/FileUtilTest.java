package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileUtilTest {
    @TempDir
    public Path temporaryFolder;


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
    public void createFile_fileDoesNotExist_createsFile() throws IOException {
        Path file = temporaryFolder.resolve("newFile.txt");
        FileUtil.createFile(file);
        assertTrue(Files.exists(file));
    }

    @Test
    public void createFile_fileExists_doesNotOverwrite() throws IOException {
        Path file = temporaryFolder.resolve("existingFile.txt");
        Files.createFile(file);
        FileUtil.createFile(file);
        assertTrue(Files.exists(file));
    }

    @Test
    public void isFileExists_fileExists_returnsTrue() throws IOException {
        Path file = temporaryFolder.resolve("testFile.txt");
        Files.createFile(file);
        assertTrue(FileUtil.isFileExists(file));
    }

    @Test
    public void isFileExists_fileDoesNotExist_returnsFalse() {
        Path file = temporaryFolder.resolve("nonExistentFile.txt");
        assertFalse(FileUtil.isFileExists(file));
    }

    @Test
    public void createParentDirsOfFile_parentDirsDoNotExist_createsParentDirs() throws IOException {
        Path file = temporaryFolder.resolve("parentDir/newFile.txt");
        FileUtil.createParentDirsOfFile(file);
        assertTrue(Files.exists(file.getParent()));
    }

    @Test
    public void createParentDirsOfFile_parentDirsExist_doesNotThrowException() throws IOException {
        Path file = temporaryFolder.resolve("parentDir/newFile.txt");
        Files.createDirectories(file.getParent());
        FileUtil.createParentDirsOfFile(file);
        assertTrue(Files.exists(file.getParent()));
    }
}
