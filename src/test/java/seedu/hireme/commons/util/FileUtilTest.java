package seedu.hireme.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.commons.util.FileUtil.CHARSET;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileUtilTest {
    @TempDir
    Path tempDir;

    private Path testFile;
    private Path nonExistentFile;

    @BeforeEach
    public void setUp() {
        testFile = tempDir.resolve("testFile.txt");
        nonExistentFile = tempDir.resolve("nonExistentFile.txt");
    }

    @Test
    public void isValidPath() {
        // valid path
        assertTrue(FileUtil.isValidPath("valid/file/path"));

        // invalid path
        assertFalse(FileUtil.isValidPath("a\0"));

        // null path -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));
    }

    /**
     * Tests that the file exists correctly when it does.
     * Positive test case.
     */
    @Test
    public void isFileExists_fileExists_returnsTrue() throws IOException {
        Files.createFile(testFile);
        assertTrue(FileUtil.isFileExists(testFile));
    }

    /**
     * Tests that the file does not exist correctly when it does not.
     * Negative test case.
     */
    @Test
    public void isFileExists_fileDoesNotExist_returnsFalse() {
        assertFalse(FileUtil.isFileExists(nonExistentFile));
    }

    /**
     * Tests that the method correctly identifies a valid path.
     * Positive test case.
     */
    @Test
    public void isValidPath_validPath_returnsTrue() {
        assertTrue(FileUtil.isValidPath(testFile.toString()));
    }

    /**
     * Tests that the method correctly identifies an invalid path.
     * Negative test case.
     */
    @Test
    public void isValidPath_invalidPath_returnsFalse() {
        assertFalse(FileUtil.isValidPath("invalid/\0|path.txt"));
    }

    /**
     * Tests creating a file that does not exist yet.
     * Positive test case.
     */
    @Test
    public void createIfMissing_fileDoesNotExist_createsFile() throws IOException {
        FileUtil.createIfMissing(nonExistentFile);
        assertTrue(Files.exists(nonExistentFile));
    }

    /**
     * Tests creating a file that already exists.
     * Negative test case.
     */
    @Test
    public void createIfMissing_fileAlreadyExists_doesNotThrow() throws IOException {
        Files.createFile(testFile);
        FileUtil.createIfMissing(testFile);
        assertTrue(Files.exists(testFile));
    }

    /**
     * Tests creating a file correctly.
     * Positive test case.
     */
    @Test
    public void createFile_fileDoesNotExist_createsFile() throws IOException {
        FileUtil.createFile(nonExistentFile);
        assertTrue(Files.exists(nonExistentFile));
    }

    /**
     * Tests that creating an existing file does not create a new one.
     * Negative test case.
     */
    @Test
    public void createFile_fileAlreadyExists_doesNotCreateNewFile() throws IOException {
        Files.createFile(testFile);
        FileUtil.createFile(testFile);
        long fileCount = Files.list(tempDir).count();
        assertEquals(1, fileCount);
    }

    /**
     * Tests that creating parent directories works correctly.
     * Positive test case.
     */
    @Test
    public void createParentDirsOfFile_missingParentDirs_createsDirectories() throws IOException {
        Path fileWithParent = tempDir.resolve("subdir/testFile.txt");
        FileUtil.createParentDirsOfFile(fileWithParent);
        assertTrue(Files.exists(fileWithParent.getParent()));
    }

    /**
     * Tests that calling createParentDirsOfFile on an existing parent directory does nothing.
     * Negative test case.
     */
    @Test
    public void createParentDirsOfFile_existingParentDirs_doesNotThrow() throws IOException {
        Path fileWithParent = tempDir.resolve("subdir/testFile.txt");
        Files.createDirectories(fileWithParent.getParent());
        FileUtil.createParentDirsOfFile(fileWithParent);
        assertTrue(Files.exists(fileWithParent.getParent()));
    }

    /**
     * Tests reading from a file correctly.
     * Positive test case.
     */
    @Test
    public void readFromFile_fileExists_returnsContent() throws IOException {
        String content = "Hello, World!";
        Files.write(testFile, content.getBytes(CHARSET), StandardOpenOption.CREATE);
        assertEquals(content, FileUtil.readFromFile(testFile));
    }

    /**
     * Tests that reading from a non-existent file throws an IOException.
     * Negative test case.
     */
    @Test
    public void readFromFile_fileDoesNotExist_throwsIoException() {
        assertThrows(IOException.class, () -> FileUtil.readFromFile(nonExistentFile));
    }

    /**
     * Tests writing to a file correctly.
     * Positive test case.
     */
    @Test
    public void writeToFile_newFile_createsFileWithContent() throws IOException {
        String content = "Hello, World!";
        FileUtil.writeToFile(nonExistentFile, content);
        assertEquals(content, new String(Files.readAllBytes(nonExistentFile), CHARSET));
    }

    /**
     * Tests that writing to an existing file updates its content.
     * Positive test case.
     */
    @Test
    public void writeToFile_existingFile_updatesContent() throws IOException {
        String initialContent = "Hello, World!";
        Files.write(testFile, initialContent.getBytes(CHARSET), StandardOpenOption.CREATE);
        String newContent = "Updated Content!";
        FileUtil.writeToFile(testFile, newContent);
        assertEquals(newContent, new String(Files.readAllBytes(testFile), CHARSET));
    }

}
