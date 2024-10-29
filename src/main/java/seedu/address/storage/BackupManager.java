package seedu.address.storage;

import java.io.IOException;
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

    private static final String BACKUP_PREFIX = "clinicbuddy-backup-";
    private static final String BACKUP_EXTENSION = ".json";

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
    private static final int MAX_BACKUPS = 10;
    private static final long MIN_BACKUP_INTERVAL_MS = 3000; // 3 seconds debounce to prevent multiple backups

    private final Path backupDirectory;
    private long lastBackupTime = 0; // Stores the last backup time to avoid multiple backups
    private final Object backupLock = new Object(); // Lock object to synchronize backup operations

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
     * Triggers a backup with a specified action description.
     * This captures the pre-modification state of the address book.
     *
     * @param filePath    The file path of the address book.
     * @param description A description for the backup file (e.g., "delete John Doe").
     * @throws IOException if an error occurs during backup creation.
     */
    public void triggerBackup(Path filePath, String description) throws IOException {
        synchronized (backupLock) {
            if (!Files.exists(filePath)) {
                throw new IOException("The file to back up does not exist: " + filePath);
            }

            String timestamp = LocalDateTime.now().format(FORMATTER);
            String backupFileName = String.format("%s_%s.json", description, timestamp);
            Path backupPath = backupDirectory.resolve(backupFileName);

            Files.copy(filePath, backupPath);
            logger.info("Backup created: " + backupPath);

            cleanOldBackups(MAX_BACKUPS);
        }
    }

    /**
     * Deletes old backups, keeping only the latest `maxBackups` files.
     *
     * @param maxBackups The maximum number of backups to retain.
     * @throws IOException If there is an error accessing the backup directory.
     */
    public void cleanOldBackups(int maxBackups) throws IOException {
        if (maxBackups < 1) {
            throw new IllegalArgumentException("maxBackups must be at least 1.");
        }

        try (Stream<Path> backups = Files.list(backupDirectory)) {
            List<Path> backupFiles = backups.filter(Files::isRegularFile)
                    .sorted(Comparator.comparing(this::getFileCreationTime).reversed()) // Newest first
                    .toList();

            // Delete only the oldest files if we exceed the maxBackups limit
            if (backupFiles.size() > maxBackups) {
                for (int i = maxBackups; i < backupFiles.size(); i++) {
                    deleteBackup(backupFiles.get(i));
                }
            }
        }
    }

    /**
     * Restores the second-most recent backup from the backup directory, if available.
     *
     * @return An {@code Optional<Path>} containing the path to the second-most recent backup, if it exists.
     * @throws IOException If an I/O error occurs while listing files in the backup directory.
     */
    public Optional<Path> restoreMostRecentBackup() throws IOException {
        try (Stream<Path> backups = Files.list(backupDirectory)) {
            List<Path> backupFiles = backups.filter(Files::isRegularFile)
                    .sorted(Comparator.comparing(this::getFileCreationTime).reversed()) // Newest first
                    .toList();

            if (!backupFiles.isEmpty()) {
                return Optional.of(backupFiles.get(0)); // Return the most recent backup
            } else {
                return Optional.empty(); // No backups available
            }
        }
    }


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
