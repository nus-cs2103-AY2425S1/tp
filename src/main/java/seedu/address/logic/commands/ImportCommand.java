package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.course.Course;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;

/**
 * Imports students from a CSV file into TAHub.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final CommandType COMMAND_TYPE = CommandType.IMPORTSTUDENT;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports students from a CSV file.\n"
            + "For files in parent directory: " + COMMAND_WORD + " filename.csv\n"
            + "For files in home directory: " + COMMAND_WORD + " ~/path/to/file.csv\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " students.csv\n"
            + "  " + COMMAND_WORD + " ~/documents/students.csv\n"
            + "  " + COMMAND_WORD + " ~/semester1/class1/students.csv";

    public static final String MESSAGE_FILE_OUTSIDE_PROJECT =
            "The file must be in the parent directory or specified with a complete path from home directory (~)";

    public static final String MESSAGE_SUCCESS = "Imported %1$d students successfully. %2$d entries had errors.";
    public static final String MESSAGE_SUCCESS_WITH_ERRORS = MESSAGE_SUCCESS + " Failed entries written to: %3$s";
    public static final String MESSAGE_EMPTY_FILE = "The specified file is empty or contains no valid entries";
    public static final String MESSAGE_INVALID_FILE = "Could not read the specified file: %1$s";
    public static final String MESSAGE_INVALID_HEADER = "Invalid CSV header. Expected: Name,Phone,Email,Courses";

    private static final Logger logger = LogsCenter.getLogger(ImportCommand.class);
    private final Path filePath;
    private int successCount = 0;
    private int errorCount = 0;

    /**
     * Creates an ImportCommand to import data from the specified path into TAHub
     */
    public ImportCommand(String filepath) {
        requireAllNonNull(filepath);
        this.filePath = resolveFilePath(filepath);
    }

    /**
     * Returns Command Type IMPORTSTUDENT
     *
     * @return Command Type IMPORTSTUDENT
     */
    @Override
    public CommandType getCommandType() {
        return COMMAND_TYPE;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        List<String[]> errorEntries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String header = reader.readLine();
            if (header == null) {
                throw new CommandException(MESSAGE_EMPTY_FILE);
            }

            // Validate header
            if (!header.equalsIgnoreCase("Name,Phone,Email,Courses")) {
                throw new CommandException(MESSAGE_INVALID_HEADER);
            }

            String line;
            boolean hasValidEntries = false;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                hasValidEntries = true;
                try {
                    Student student = parseStudent(line);
                    if (!model.hasStudent(student)) {
                        model.addStudent(student);
                        successCount++;
                        logger.fine("Successfully imported student: " + student.getName());
                    } else {
                        errorEntries.add(new String[]{line, "Duplicate student"});
                        errorCount++;
                        logger.fine("Duplicate student found: " + student.getName());
                    }
                } catch (IllegalArgumentException e) {
                    errorEntries.add(new String[]{line, e.getMessage()});
                    errorCount++;
                    logger.fine("Error parsing student entry: " + e.getMessage());
                }
            }

            if (!hasValidEntries) {
                throw new CommandException(MESSAGE_EMPTY_FILE);
            }

            if (!errorEntries.isEmpty()) {
                Path errorPath = writeErrorFile(errorEntries);
                return new CommandResult(
                        String.format(MESSAGE_SUCCESS_WITH_ERRORS, successCount, errorCount, errorPath),
                        COMMAND_TYPE);
            }

            return new CommandResult(String.format(MESSAGE_SUCCESS, successCount, errorCount), COMMAND_TYPE);

        } catch (IOException e) {
            logger.warning("Error reading import file: " + e.getMessage());
            throw new CommandException(String.format(MESSAGE_INVALID_FILE, e.getMessage()));
        }
    }

    /**
     * Resolves the file path, handling both home directory paths (starting with ~) and parent directory paths.
     */
    protected Path resolveFilePath(String filepath) {
        // If path starts with ~, expand to user home directory
        if (filepath.startsWith("~")) {
            return Paths.get(System.getProperty("user.home"))
                    .resolve(filepath.substring(2)); // Remove ~/ from path
        }

        // For relative paths, check parent directory of project
        if (!Paths.get(filepath).isAbsolute()) {
            return Paths.get(System.getProperty("user.dir"))
                    .getParent() // Go up one level from project directory
                    .resolve(filepath);
        }

        // For absolute paths, use as is
        return Paths.get(filepath);
    }

    /**
     * Writes failed entries to an error file in the same directory as the input file.
     */
    private Path writeErrorFile(List<String[]> errorEntries) throws IOException {
        Path errorPath = filePath.resolveSibling("error.csv");
        try (FileWriter writer = new FileWriter(errorPath.toFile())) {
            writer.write("Original Entry,Error Message\n");
            for (String[] entry : errorEntries) {
                writer.write(String.format("%s,%s\n",
                        escapeSpecialCharacters(entry[0]),
                        escapeSpecialCharacters(entry[1])));
            }
        }
        return errorPath;
    }

    /**
     * Parses a CSV line into a Student object.
     */
    protected Student parseStudent(String line) throws IllegalArgumentException {
        String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        if (parts.length < 4) {
            throw new IllegalArgumentException("Incomplete student entry");
        }

        try {
            String name = unescapeSpecialCharacters(parts[0]);
            String phone = parts[1].trim();
            String email = parts[2].trim();
            String coursesStr = unescapeSpecialCharacters(parts[3]);

            Set<Course> courses = new HashSet<>();
            if (!coursesStr.isEmpty()) {
                for (String course : coursesStr.split(";")) {
                    courses.add(new Course(course.trim()));
                }
            }

            return new Student(
                    new Name(name),
                    new Phone(phone),
                    new Email(email),
                    courses
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid data format: " + e.getMessage());
        }
    }

    /**
     * Escapes special characters in CSV data.
     * Made public for testing.
     */
    public String escapeSpecialCharacters(String data) {
        if (data == null) {
            return "";
        }
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    /**
     * Unescapes special characters in CSV data.
     * Made public for testing.
     */
    public String unescapeSpecialCharacters(String data) {
        if (data == null) {
            return "";
        }
        data = data.trim();
        if (data.startsWith("\"") && data.endsWith("\"")) {
            data = data.substring(1, data.length() - 1);
            data = data.replace("\"\"", "\"");
        }
        return data;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ImportCommand)) {
            return false;
        }

        ImportCommand otherCommand = (ImportCommand) other;
        return filePath.equals(otherCommand.filePath);
    }
}
