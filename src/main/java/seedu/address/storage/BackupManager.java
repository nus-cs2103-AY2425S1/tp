package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;
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

    private final Path backupDirectory;

    /**
     * Initializes a {@code BackupManager} with the specified backup directory.
     *
     * @param backupDirectory The path to the backup directory. Must not be {@code null}.
     *
     * @throws IOException If the backup directory path is {@code null} or if there is
     *                     an error while creating the directories.
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
     * Saves a backup of the given file with a timestamp.
     *
     * @param filePath The original file to back up.
     * @throws IOException If an error occurs during the backup.
     */
    public void saveBackup(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            throw new IOException("The file to back up does not exist: " + filePath);
        }

        String backupFileName = BACKUP_PREFIX + System.currentTimeMillis() + BACKUP_EXTENSION;
        Path backupPath = backupDirectory.resolve(backupFileName);

        Files.copy(filePath, backupPath, StandardCopyOption.REPLACE_EXISTING);
        logger.info("Backup created: " + backupPath);
    }

    /**
     * Cleans up old backups, keeping only the latest `maxBackups` files.
     *
     * @param maxBackups The number of recent backups to retain.
     * @throws IOException If an error occurs during cleanup.
     */
    public void cleanOldBackups(int maxBackups) throws IOException {
        if (maxBackups < 1) {
            throw new IllegalArgumentException("maxBackups must be at least 1.");
        }

        try (Stream<Path> backups = Files.list(backupDirectory)) {
            backups.filter(Files::isRegularFile)
                    .sorted(Comparator.comparing(this::getFileCreationTime).reversed())
                    .skip(maxBackups)
                    .forEach(this::deleteBackup);
        }
    }

    /**
     * Restores the most recent backup.
     *
     * @return The path to the restored backup, if it exists.
     * @throws IOException If restoration fails.
     */
    public Optional<Path> restoreMostRecentBackup() throws IOException {
        try (Stream<Path> backups = Files.list(backupDirectory)) {
            return backups.filter(Files::isRegularFile)
                    .max(Comparator.comparing(this::getFileCreationTime));
        }
    }

    private FileTime getFileCreationTime(Path path) {
        try {
            return Files.getLastModifiedTime(path);
        } catch (IOException e) {
            logger.warning("Unable to retrieve creation time for " + path + ": " + e.getMessage());
            return FileTime.fromMillis(System.currentTimeMillis());
        }
    }

    private void deleteBackup(Path backupPath) {
        try {
            Files.deleteIfExists(backupPath);
            logger.info("Deleted old backup: " + backupPath);
        } catch (IOException e) {
            logger.warning("Failed to delete backup: " + backupPath + " - " + e.getMessage());
        }
    }
}
