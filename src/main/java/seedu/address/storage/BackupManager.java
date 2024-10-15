package seedu.address.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;

/**
 * Handles the creation, cleanup, and restoration of backups for the AddressBook data files.
 */
public class BackupManager {

    private static final Logger logger = LogsCenter.getLogger(BackupManager.class);

    private static final String BACKUP_PREFIX = "addressbook-backup-";
    private static final String BACKUP_EXTENSION = ".json";

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
    private static final int MAX_BACKUPS = 10;
    private final Path backupDirectory;

    /**
     * Constructs a {@code BackupManager} with the specified backup directory.
     *
     * @param backupDirectory The path to the backup directory. Must not be {@code null}.
     * @throws IOException If the backup directory path is {@code null} or if an error occurs
     *                     while creating the directory.
     */
    public BackupManager(Path backupDirectory) throws IOException {
        if (backupDirectory == null) {
            throw new IOException("Backup directory path cannot be null.");
        }

        this.backupDirectory = backupDirectory;
        if (!Files.exists(backupDirectory)) {
            Files.createDirectories(backupDirectory);
            logger.info("Created backup directory at: " + backupDirectory);
        }
    }

    /**
     * Saves a backup of the provided file to the backup directory.
     */
    public void saveBackup(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            throw new IOException("The file to back up does not exist: " + filePath);
        }

        String timestamp = LocalDateTime.now().format(FORMATTER);
        String backupFileName = String.format("%s%s%s", BACKUP_PREFIX, timestamp, BACKUP_EXTENSION);
        Path backupPath = backupDirectory.resolve(backupFileName);

        String content = Files.readString(filePath, StandardCharsets.UTF_8);
        if (!content.endsWith(System.lineSeparator())) {
            content += System.lineSeparator();
        }

        Files.writeString(backupPath, content, StandardCharsets.UTF_8);
        logger.info("Backup created: " + backupPath);

        cleanOldBackups(MAX_BACKUPS);
    }

    /**
     * Deletes old backups, keeping only the latest `maxBackups` files.
     *
     * @param maxBackups The maximum number of backups to retain. Must be at least 1.
     * @throws IllegalArgumentException if `maxBackups` is less than 1.
     * @throws IOException If an I/O error occurs while accessing the backup directory.
     */
    public void cleanOldBackups(int maxBackups) throws IOException {
        if (maxBackups < 1) {
            throw new IllegalArgumentException("maxBackups must be at least 1.");
        }

        try (Stream<Path> backups = Files.list(backupDirectory)) {
            List<Path> backupFiles = backups.filter(Files::isRegularFile)
                    .sorted(Comparator.comparing(this::getFileCreationTime).reversed()) // Newest first
                    .toList();

            // If the number of backups exceeds the limit, delete only the oldest ones
            if (backupFiles.size() > maxBackups) {
                for (int i = maxBackups; i < backupFiles.size(); i++) {
                    deleteBackup(backupFiles.get(i));
                }
            }
        }
    }

    /**
     * Restores the most recent backup from the backup directory, if available.
     *
     * @return An {@code Optional<Path>} containing the path to the most recent backup, if it exists.
     * @throws IOException If an I/O error occurs while listing files in the backup directory.
     */
    public Optional<Path> restoreMostRecentBackup() throws IOException {
        try (Stream<Path> backups = Files.list(backupDirectory)) {
            return backups.filter(Files::isRegularFile)
                    .max(Comparator.comparing(this::getFileCreationTime));
        }
    }

    /**
     * Retrieves the last modified time of a given file path.
     *
     * @param path The path to the file whose last modified time is to be retrieved.
     * @return A {@code FileTime} object representing the last modified time of the file.
     *         If the file's last modified time cannot be retrieved, returns the current system time.
     */
    protected FileTime getFileCreationTime(Path path) {
        try {
            return Files.getLastModifiedTime(path);
        } catch (IOException e) {
            logger.warning("Unable to retrieve creation time for " + path + ": " + e.getMessage());
            return FileTime.fromMillis(System.currentTimeMillis());
        }
    }

    /**
     * Deletes the specified backup file if it exists.
     *
     * @param backupPath The path to the backup file to be deleted.
     */
    protected void deleteBackup(Path backupPath) {
        try {
            Files.deleteIfExists(backupPath);
            logger.info("Deleted old backup: " + backupPath);
        } catch (IOException e) {
            logger.warning("Failed to delete backup: " + backupPath + " - " + e.getMessage());
        }
    }
}
