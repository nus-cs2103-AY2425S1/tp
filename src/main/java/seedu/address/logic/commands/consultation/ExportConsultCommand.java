package seedu.address.logic.commands.consultation;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.consultation.Consultation;

/**
 * Exports the currently displayed list of consultations to a CSV file.
 */
public class ExportConsultCommand extends Command {

    public static final String COMMAND_WORD = "exportconsult";
    public static final String FORCE_FLAG = "-f";
    public static final CommandType COMMAND_TYPE = CommandType.EXPORTCONSULT;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the current list of consultations to a CSV file. "
            + "Parameters: FILENAME " + "[" + FORCE_FLAG + "] "
            + "\nExample: " + COMMAND_WORD + " consultations"
            + "\nExample with force flag: " + COMMAND_WORD + " " + FORCE_FLAG + " consultations";

    public static final String MESSAGE_SUCCESS = "Exported %1$d consultations to %2$s";
    public static final String MESSAGE_FAILURE = "Failed to export consultations: %1$s";
    public static final String MESSAGE_FILE_EXISTS = "File %1$s already exists. Use -f flag to overwrite.";
    public static final String MESSAGE_HOME_FILE_EXISTS =
            "File %1$s already exists in home directory. Use -f flag to overwrite.";
    public static final String MESSAGE_SUCCESS_WITH_COPY = "Exported %1$d consultations to %2$s and %3$s";

    private static final Logger logger = LogsCenter.getLogger(ExportConsultCommand.class);

    private final String filename;
    private final boolean isForceExport;
    private final Path baseDir;

    public ExportConsultCommand(String filename, boolean isForceExport) {
        this(filename, isForceExport, Paths.get("data"));
    }

    /**
     * Creates an ExportConsultCommand to export data to the specified filename in the default directory
     */
    public ExportConsultCommand(String filename, boolean isForceExport, Path baseDir) {
        this.filename = filename;
        this.isForceExport = isForceExport;
        this.baseDir = baseDir;
    }

    @Override
    public CommandType getCommandType() {
        return COMMAND_TYPE;
    }

    protected Path getHomeFilePath(String filename) {
        return Paths.get(System.getProperty("user.home"), filename + ".csv");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Consultation> consultList = model.getFilteredConsultationList();
        logger.info("Starting export for " + consultList.size() + " consultations");

        Path dataFilePath = baseDir.resolve(filename + ".csv");
        Path homeFilePath = getHomeFilePath(filename);

        try {
            Files.createDirectories(baseDir);
        } catch (IOException e) {
            logger.warning("Failed to create directory: " + baseDir);
            throw new CommandException(String.format(MESSAGE_FAILURE, "Could not create directory: " + e.getMessage()));
        }

        if (!isForceExport) {
            if (Files.exists(dataFilePath)) {
                throw new CommandException(String.format(MESSAGE_FILE_EXISTS, dataFilePath));
            }
            if (Files.exists(homeFilePath)) {
                throw new CommandException(String.format(MESSAGE_HOME_FILE_EXISTS, homeFilePath));
            }
        }

        try {
            writeCsvFile(dataFilePath, consultList);

            try {
                if (isForceExport) {
                    Files.copy(dataFilePath, homeFilePath, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    Files.copy(dataFilePath, homeFilePath);
                }
                return new CommandResult(String.format(MESSAGE_SUCCESS_WITH_COPY,
                        consultList.size(), dataFilePath, homeFilePath),
                        COMMAND_TYPE);
            } catch (IOException e) {
                return new CommandResult(String.format(MESSAGE_SUCCESS, consultList.size(), dataFilePath),
                        COMMAND_TYPE);
            }
        } catch (IOException e) {
            try {
                Files.deleteIfExists(dataFilePath);
            } catch (IOException ignored) {
                // Ignore cleanup errors
            }
            throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()));
        }
    }

    String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    private void writeCsvFile(Path filePath, List<Consultation> consultList) throws IOException {
        try (FileWriter csvWriter = new FileWriter(filePath.toFile())) {
            // Write CSV header
            csvWriter.append("Date,Time,Students\n");

            // Write consultation data
            for (Consultation consult : consultList) {
                List<String> studentNames = consult.getStudents().stream()
                        .map(student -> student.getName().fullName)
                        .collect(Collectors.toList());
                String studentsString = String.join(";", studentNames);

                csvWriter.append(String.format("%s,%s,%s\n",
                        consult.getDate().getValue(),
                        consult.getTime().getValue(),
                        escapeSpecialCharacters(studentsString)));
            }
            csvWriter.flush();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ExportConsultCommand)) {
            return false;
        }
        ExportConsultCommand otherCommand = (ExportConsultCommand) other;
        return filename.equals(otherCommand.filename)
                && isForceExport == otherCommand.isForceExport;
    }
}
