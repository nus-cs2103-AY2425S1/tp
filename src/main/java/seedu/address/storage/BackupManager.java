package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;

/**
 * Manages the creation, restoration, and deletion of backups for the AddressBook data files.
 * Provides methods to ensure backup storage is capped at a maximum of {@link #MAX_BACKUPS},
 * and supports restoring specific backups by index.
 */
public class BackupManager {

    private static final Logger logger = LogsCenter.getLogger(BackupManager.class);
    private static final DateTimeFormatter FILE_TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
    private static final DateTimeFormatter DISPLAY_TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
    private static final String BACKUP_FILE_REGEX =
            "(\\d+)_(.*?)_(\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}-\\d{2}-\\d{3})\\.json";
    private static final Pattern BACKUP_FILE_PATTERN =
            Pattern.compile(BACKUP_FILE_REGEX);

    private static final int MAX_BACKUPS = 10; // indexed from 0 to 9
    private static final int MAX_FILENAME_LENGTH = 250;
    private final Path backupDirectory;

    /**
     * Constructs a {@code BackupManager} with the specified backup directory.
     * Initializes the directory if it does not exist.
     *
     * @param backupDirectory The path to the backup directory. Must not be {@code null}.
     * @throws IOException If the backup directory cannot be created or accessed.
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
     * Retrieves the timestamp from the backup file name for sorting purposes.
     *
     * @param backupPath The path of the backup file.
     * @return LocalDateTime representing the timestamp, or LocalDateTime.MIN if parsing fails.
     */
    private LocalDateTime getFileTimestamp(Path backupPath) {
        String filename = backupPath.getFileName().toString();
        Matcher matcher = BACKUP_FILE_PATTERN.matcher(filename);
        if (matcher.matches()) {
            String timestampStr = matcher.group(3);
            try {
                return LocalDateTime.parse(timestampStr, FILE_TIMESTAMP_FORMATTER);
            } catch (Exception e) {
                logger.warning("Failed to parse timestamp from filename: " + filename + " - " + e.getMessage());
                return LocalDateTime.MIN;
            }
        } else {
            logger.warning("Invalid backup file format: " + filename);
            return LocalDateTime.MIN;
        }
    }

    /**
     * Creates a backup of the specified source file.
     * If the number of backups exceeds {@link #MAX_BACKUPS}, the oldest backup will be deleted.
     *
     * @param sourcePath        The path to the file to back up.
     * @param actionDescription A description of the action being backed up.
     * @return The index used for the newly created backup.
     * @throws IOException If an error occurs during the backup process.
     */
    public int createIndexedBackup(Path sourcePath, String actionDescription) throws IOException {
        String timestamp = LocalDateTime.now().format(FILE_TIMESTAMP_FORMATTER);

        int usedIndex;

        try (Stream<Path> backups = Files.list(backupDirectory)) {
            List<Path> backupFiles = backups
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());

            int numberOfBackups = backupFiles.size();

            if (numberOfBackups < MAX_BACKUPS) {
                // Find the highest existing index and increment
                int highestIndex = backupFiles.stream()
                        .mapToInt(this::extractIndex)
                        .max()
                        .orElse(-1);
                usedIndex = highestIndex + 1;
            } else {
                // Find the oldest backup
                Path oldestBackup = backupFiles.stream()
                        .min(Comparator.comparing(this::getFileTimestamp))
                        .orElseThrow(() -> new IOException("No backups found"));

                usedIndex = extractIndex(oldestBackup);
                // Delete the oldest backup
                Files.deleteIfExists(oldestBackup);
                logger.info("Deleted oldest backup at index " + usedIndex + ": " + oldestBackup);
            }
        }

        String backupFileName = String.format("%d_%s_%s.json", usedIndex, actionDescription, timestamp);

        // Check if the file name exceeds the limit
        if (backupFileName.length() > MAX_FILENAME_LENGTH) {
            throw new IOException("Backup file name exceeds the maximum length of "
                    + MAX_FILENAME_LENGTH
                    + " characters. Please shorten your description.");
        }

        Path backupPath = backupDirectory.resolve(backupFileName);

        // Copy source to the backup path
        Files.copy(sourcePath, backupPath, StandardCopyOption.REPLACE_EXISTING);
        logger.info("Backup created with index: " + usedIndex);

        return usedIndex;
    }

    /**
     * Extracts the index from the filename of a backup file.
     *
     * @param backupPath The path of the backup file.
     * @return The index as an integer, or -1 if the filename format is invalid.
     */
    protected int extractIndex(Path backupPath) {
        String filename = backupPath.getFileName().toString();
        Matcher matcher = BACKUP_FILE_PATTERN.matcher(filename);
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            logger.warning("Invalid backup file format: " + filename);
            return -1;
        }
    }

    /**
     * Extracts the action description from the filename of a backup file.
     *
     * @param backupPath The path of the backup file.
     * @return The action description, or "Unknown" if the filename format is invalid.
     */
    protected String extractActionDescription(Path backupPath) {
        String filename = backupPath.getFileName().toString();
        Matcher matcher = BACKUP_FILE_PATTERN.matcher(filename);
        if (matcher.matches()) {
            return matcher.group(2);
        } else {
            logger.warning("Invalid backup file format: " + filename);
            return "Unknown";
        }
    }

    /**
     * Restores a backup by its index.
     *
     * @param index The index of the backup to restore.
     * @return The path to the restored backup file.
     * @throws IOException If the backup file is not found or cannot be accessed.
     */
    public Path restoreBackupByIndex(int index) throws IOException {
        try (Stream<Path> backups = Files.list(backupDirectory)) {
            Optional<Path> backupToRestore = backups
                    .filter(path -> extractIndex(path) == index)
                    .findFirst();

            if (backupToRestore.isPresent()) {
                return backupToRestore.get();
            } else {
                throw new IOException("Backup with index " + index + " not found.");
            }
        }
    }

    /**
     * Lists all backup files in the directory, formatted for user readability.
     * Files are sorted by creation time in descending order.
     *
     * @return A formatted string listing all backups, or an empty string if no backups exist.
     * @throws IOException If an error occurs while accessing the backup directory.
     */
    public String getFormattedBackupList() throws IOException {
        if (!Files.exists(backupDirectory)) {
            return "";
        }

        try (Stream<Path> stream = Files.list(backupDirectory)) {
            List<Path> backupFiles = stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .sorted(Comparator.comparing(this::getFileTimestamp).reversed())
                    .collect(Collectors.toList());

            if (backupFiles.isEmpty()) {
                return "";
            }

            StringBuilder sb = new StringBuilder();
            for (Path path : backupFiles) {
                String filename = path.getFileName().toString();
                Matcher matcher = BACKUP_FILE_PATTERN.matcher(filename);
                if (matcher.matches()) {
                    int index = Integer.parseInt(matcher.group(1));
                    String description = matcher.group(2);
                    String timestampStr = matcher.group(3);
                    LocalDateTime timestamp = LocalDateTime.parse(timestampStr, FILE_TIMESTAMP_FORMATTER);

                    String formattedTimestamp = timestamp.format(DISPLAY_TIMESTAMP_FORMATTER);
                    sb.append(String.format("%d [%s] Created on: %s\n",
                            index,
                            description,
                            formattedTimestamp));
                } else {
                    logger.warning("Invalid backup file format: " + filename);
                }
            }

            return sb.toString().trim();
        }
    }

    /**
     * Deletes all but the most recent {@code maxBackups}.
     *
     * @param maxBackups The number of backups to retain.
     * @throws IOException If an error occurs during deletion.
     */
    public void cleanOldBackups(int maxBackups) throws IOException {
        if (maxBackups < 1) {
            throw new IllegalArgumentException("maxBackups must be at least 1.");
        }

        try (Stream<Path> backups = Files.list(backupDirectory)) {
            List<Path> backupFiles = backups.filter(Files::isRegularFile)
                    .sorted(Comparator.comparing(this::getFileCreationTime).reversed())
                    .collect(Collectors.toList());

            for (int i = maxBackups; i < backupFiles.size(); i++) {
                Files.deleteIfExists(backupFiles.get(i));
                logger.info("Deleted old backup: " + backupFiles.get(i));
            }
        }
    }

    /**
     * Retrieves the file creation time of a given backup file.
     *
     * @param path The path to the backup file.
     * @return The file's creation time.
     */
    private FileTime getFileCreationTime(Path path) {
        try {
            return Files.getLastModifiedTime(path);
        } catch (IOException e) {
            logger.warning("Failed to get file creation time for " + path + ": " + e.getMessage());
            return FileTime.fromMillis(0);
        }
    }

    /**
     * Checks if a backup file exists at the specified index.
     *
     * @param index The index of the backup to check.
     * @return {@code true} if the backup exists, {@code false} otherwise.
     * @throws IOException If an error occurs while accessing the backup directory.
     */
    public boolean isBackupAvailable(int index) {
        try (Stream<Path> backups = Files.list(backupDirectory)) {
            return backups.anyMatch(path -> extractIndex(path) == index);
        } catch (IOException e) {
            logger.warning("Failed to check backup availability: " + e.getMessage());
            return false;
        }
    }

}
