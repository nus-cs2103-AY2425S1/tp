package seedu.address.logic.commands;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.course.Course;
import seedu.address.model.student.Student;

/**
 * Exports the currently displayed list of students to a CSV file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String FORCE_FLAG = "-f";
    public static final CommandType COMMAND_TYPE = CommandType.EXPORTSTUDENT;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the current list of students to a CSV file. "
            + "Parameters: FILENAME " + "[" + FORCE_FLAG + "] "
            + "\nExample: " + COMMAND_WORD + " students"
            + "\nExample with force flag: " + COMMAND_WORD + " " + FORCE_FLAG + " students";

    public static final String MESSAGE_SUCCESS = "Exported %1$d students to %2$s";
    public static final String MESSAGE_FAILURE = "Failed to export students: %1$s";
    public static final String MESSAGE_FILE_EXISTS = "File %1$s already exists. Use -f flag to overwrite.";
    public static final String MESSAGE_HOME_FILE_EXISTS =
            "File %1$s already exists in home directory. Use -f flag to overwrite.";
    public static final String MESSAGE_SUCCESS_WITH_COPY = "Exported %1$d students to %2$s and %3$s";
    private static final Logger logger = LogsCenter.getLogger(ExportCommand.class);

    private final String filename;
    private final boolean isForceExport;
    private final Path baseDir;

    /**
     * Creates an ExportCommand to export data to the specified filename in the default directory
     */
    public ExportCommand(String filename, boolean isForceExport) {
        this(filename, isForceExport, Paths.get("data"));
    }

    /**
     * Creates an ExportCommand to export data to the specified filename in the specified directory
     * This constructor is primarily for testing purposes
     */
    public ExportCommand(String filename, boolean isForceExport, Path baseDir) {
        this.filename = filename;
        this.isForceExport = isForceExport;
        this.baseDir = baseDir;
    }

    /**
     * Returns Command Type EXPORTSTUDENT
     *
     * @return Command Type EXPORTSTUDENT
     */
    @Override
    public CommandType getCommandType() {
        return COMMAND_TYPE;
    }

    /**
     * Gets the home file path. Protected for testing purposes.
     * @param filename The name of the file (without extension)
     * @return The full path to the file in the home directory
     */
    protected Path getHomeFilePath(String filename) {
        return Paths.get(System.getProperty("user.home"), filename + ".csv");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert model != null : "Model cannot be null";
        List<Student> studentList = model.getFilteredStudentList();
        logger.info("Starting export for " + studentList.size() + " students");

        Path dataFilePath = baseDir.resolve(filename + ".csv");
        Path homeFilePath = getHomeFilePath(filename);
        logger.fine("Export paths - Data: " + dataFilePath + ", Home: " + homeFilePath);

        // Create directories if they don't exist
        try {
            Files.createDirectories(baseDir);
            logger.fine("Created directory: " + baseDir);
        } catch (IOException e) {
            logger.warning("Failed to create directory: " + baseDir);
            throw new CommandException(String.format(MESSAGE_FAILURE, "Could not create directory: " + e.getMessage()));
        }

        // Check both locations for existing files when force flag is not set
        if (!isForceExport) {
            if (Files.exists(dataFilePath)) {
                logger.info("Data file already exists: " + dataFilePath);
                throw new CommandException(String.format(MESSAGE_FILE_EXISTS, dataFilePath));
            }
            if (Files.exists(homeFilePath)) {
                logger.info("Home file already exists: " + homeFilePath);
                throw new CommandException(String.format(MESSAGE_HOME_FILE_EXISTS, homeFilePath));
            }
        }

        try {
            // Write to data directory
            writeCsvFile(dataFilePath, studentList);
            logger.info("Successfully wrote data to: " + dataFilePath);

            // Copy to home directory
            try {
                if (isForceExport) {
                    Files.copy(dataFilePath, homeFilePath, StandardCopyOption.REPLACE_EXISTING);
                    logger.info("Force-copied file to home directory: " + homeFilePath);
                } else {
                    Files.copy(dataFilePath, homeFilePath);
                    logger.info("Copied file to home directory: " + homeFilePath);
                }
                return new CommandResult(String.format(MESSAGE_SUCCESS_WITH_COPY,
                        studentList.size(), dataFilePath, homeFilePath),
                        COMMAND_TYPE);
            } catch (FileAlreadyExistsException e) {
                // If file exists in home directory and force flag is not set
                // Delete the data directory file and throw exception
                Files.deleteIfExists(dataFilePath);
                throw new CommandException(String.format(MESSAGE_HOME_FILE_EXISTS, homeFilePath));
            } catch (IOException e) {
                // If home directory copy fails for other reasons, return success with data file only
                return new CommandResult(String.format(MESSAGE_SUCCESS, studentList.size(), dataFilePath),
                        COMMAND_TYPE);
            }
        } catch (IOException e) {
            logger.warning("Failed to write CSV file: " + e.getMessage());
            // Clean up any partially created files
            try {
                Files.deleteIfExists(dataFilePath);
            } catch (IOException ignored) {
                // Ignore cleanup errors
            }
            throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()));
        }
    }

    String escapeSpecialCharacters(String data) {
        assert data != null : "Input string cannot be null";
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        assert escapedData != null : "Escaped data cannot be null";
        return escapedData;
    }

    private void writeCsvFile(Path filePath, List<Student> studentList) throws IOException {
        assert filePath != null : "File path cannot be null";
        assert studentList != null : "Student list cannot be null";
        logger.fine("Writing CSV file to: " + filePath);
        try (FileWriter csvWriter = new FileWriter(filePath.toFile())) {
            // Write CSV header
            csvWriter.append("Name,Phone,Email,Courses\n");
            logger.finest("Wrote CSV header");

            // Write student data
            for (Student student : studentList) {
                assert student != null : "Student cannot be null";
                csvWriter.append(String.format("%s,%s,%s,%s\n",
                        escapeSpecialCharacters(student.getName().fullName),
                        student.getPhone().value,
                        student.getEmail().value,
                        escapeSpecialCharacters(coursesToString(student.getCourses()))));
            }
            csvWriter.flush();
            logger.finest("Wrote " + studentList.size() + " student records");
        }
    }

    private String coursesToString(Set<Course> courses) {
        assert courses != null : "Courses set cannot be null";
        return courses.stream()
                .map(Course::toString)
                .collect(Collectors.joining(";"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ExportCommand)) {
            return false;
        }
        ExportCommand otherCommand = (ExportCommand) other;
        return filename.equals(otherCommand.filename)
                && isForceExport == otherCommand.isForceExport;
    }
}
