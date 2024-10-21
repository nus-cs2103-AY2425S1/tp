package seedu.address.logic.commands;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
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
    public static final CommandType COMMAND_TYPE = CommandType.EXPORTSTUDENT;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the current list of students to a CSV file. "
            + "Parameters: FILEPATH "
            + "Example: " + COMMAND_WORD + " data/export.csv";

    public static final String MESSAGE_SUCCESS = "Exported %1$d students to %2$s";
    public static final String MESSAGE_FAILURE = "Failed to export students: %1$s";

    private final Path filePath;

    public ExportCommand(Path filePath) {
        this.filePath = filePath;
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
            return new CommandResult(String.format(MESSAGE_SUCCESS, studentList.size(), filePath));
        } catch (IOException e) {
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
        return filePath.equals(otherCommand.filePath);
    }
}
