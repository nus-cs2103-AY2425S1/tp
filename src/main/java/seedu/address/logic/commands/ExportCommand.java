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
import java.util.stream.Collectors;

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

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Student> studentList = model.getFilteredStudentList();

        // Get project data directory path and home directory path
        Path dataFilePath = baseDir.resolve(filename + ".csv");
        Path homeFilePath = Paths.get(System.getProperty("user.home"), filename + ".csv");

        // Create directories if they don't exist
        try {
            Files.createDirectories(baseDir);
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, "Could not create directory: " + e.getMessage()));
        }

        // Check both locations for existing files when force flag is not set
        if (!isForceExport) {
            if (Files.exists(dataFilePath)) {
                throw new CommandException(String.format(MESSAGE_FILE_EXISTS, dataFilePath));
            }
            if (Files.exists(homeFilePath)) {
                throw new CommandException(String.format(MESSAGE_HOME_FILE_EXISTS, homeFilePath));
            }
        }

        try {
            // Write to data directory
            writeCsvFile(dataFilePath, studentList);

            // Copy to home directory
            try {
                if (isForceExport) {
                    Files.copy(dataFilePath, homeFilePath, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    Files.copy(dataFilePath, homeFilePath);
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
            // Clean up any partially created files
            try {
                Files.deleteIfExists(dataFilePath);
            } catch (IOException ignored) {
                // Ignore cleanup errors
            }
            throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()));
        }
    }

    private String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    private void writeCsvFile(Path filePath, List<Student> studentList) throws IOException {
        try (FileWriter csvWriter = new FileWriter(filePath.toFile())) {
            // Write CSV header
            csvWriter.append("Name,Phone,Email,Courses\n");

            // Write student data
            for (Student student : studentList) {
                csvWriter.append(String.format("%s,%s,%s,%s\n",
                        escapeSpecialCharacters(student.getName().fullName),
                        student.getPhone().value,
                        student.getEmail().value,
                        escapeSpecialCharacters(coursesToString(student.getCourses()))));
            }

            csvWriter.flush();
        }
    }

    private String coursesToString(Set<Course> courses) {
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
