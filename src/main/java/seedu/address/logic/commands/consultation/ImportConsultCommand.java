package seedu.address.logic.commands.consultation;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.consultation.Date;
import seedu.address.model.consultation.Time;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;

/**
 * Imports consultations from a CSV file into TAHub.
 */
public class ImportConsultCommand extends Command {

    public static final String COMMAND_WORD = "importconsult";
    public static final CommandType COMMAND_TYPE = CommandType.IMPORTCONSULT;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports consultations from a CSV file.\n"
            + "For files in parent directory: " + COMMAND_WORD + " filename.csv\n"
            + "For files in home directory: " + COMMAND_WORD + " ~/path/to/file.csv\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " consultations.csv\n"
            + "  " + COMMAND_WORD + " ~/documents/consultations.csv\n"
            + "  " + COMMAND_WORD + " ~/semester1/consultations.csv";

    public static final String MESSAGE_FILE_OUTSIDE_PROJECT =
            "The file must be in the parent directory or specified with a complete path from home directory (~)";

    public static final String MESSAGE_SUCCESS = "Imported %1$d consultations successfully. %2$d entries had errors.";
    public static final String MESSAGE_SUCCESS_WITH_ERRORS = MESSAGE_SUCCESS + " Failed entries written to: %3$s";
    public static final String MESSAGE_EMPTY_FILE = "The specified file is empty or contains no valid entries";
    public static final String MESSAGE_INVALID_FILE = "Could not read the specified file: %1$s";
    public static final String MESSAGE_INVALID_HEADER = "Invalid CSV header. Expected: Date,Time,Students";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student '%s' not found in the system";

    private static final Logger logger = LogsCenter.getLogger(ImportConsultCommand.class);
    private final Path filePath;
    private int successCount = 0;
    private int errorCount = 0;

    /**
     * Creates an ImportConsultCommand to import consultation data from the specified path
     */
    public ImportConsultCommand(String filepath) {
        requireAllNonNull(filepath);
        this.filePath = resolveFilePath(filepath);
    }

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

            if (!header.equalsIgnoreCase("Date,Time,Students")) {
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
                    Consultation consultation = parseConsultation(line, model);
                    if (!model.hasConsult(consultation)) {
                        model.addConsult(consultation);
                        successCount++;
                        logger.fine("Successfully imported consultation: " + consultation);
                    } else {
                        errorEntries.add(new String[]{line, "Duplicate consultation"});
                        errorCount++;
                        logger.fine("Duplicate consultation found: " + consultation);
                    }
                } catch (IllegalArgumentException e) {
                    errorEntries.add(new String[]{line, e.getMessage()});
                    errorCount++;
                    logger.fine("Error parsing consultation entry: " + e.getMessage());
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
        if (filepath.startsWith("~")) {
            return Paths.get(System.getProperty("user.home"))
                    .resolve(filepath.substring(2));
        }

        if (!Paths.get(filepath).isAbsolute()) {
            return Paths.get(System.getProperty("user.dir"))
                    .getParent()
                    .resolve(filepath);
        }

        return Paths.get(filepath);
    }

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
     * Parses a CSV line into a Consultation object.
     */
    protected Consultation parseConsultation(String line, Model model) throws IllegalArgumentException {
        String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        if (parts.length < 3) {
            throw new IllegalArgumentException("Incomplete consultation entry");
        }

        try {
            String date = parts[0].trim();
            String time = parts[1].trim();
            String studentsStr = unescapeSpecialCharacters(parts[2]);

            List<Student> students = new ArrayList<>();
            if (!studentsStr.isEmpty()) {
                for (String studentName : studentsStr.split(";")) {
                    String trimmedName = studentName.trim();
                    Optional<Student> student = model.findStudentByName(new Name(trimmedName));
                    if (student.isEmpty()) {
                        throw new IllegalArgumentException(String.format(MESSAGE_STUDENT_NOT_FOUND, trimmedName));
                    }
                    students.add(student.get());
                }
            }

            return new Consultation(new Date(date), new Time(time), students);
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

        if (!(other instanceof ImportConsultCommand)) {
            return false;
        }

        ImportConsultCommand otherCommand = (ImportConsultCommand) other;
        return filePath.equals(otherCommand.filePath);
    }
}
