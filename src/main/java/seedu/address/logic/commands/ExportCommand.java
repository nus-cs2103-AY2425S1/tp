package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Exports contact list to a csv file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the whole contact list "
            + "to a specfied file type.\n"
            + "Parameters: " + PREFIX_FILE + "FILETYPE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_FILE + "csv";

    public static final String MESSAGE_SUCCESS = "Contact list successfully exported to a %1$s file";
    public static final String MESSAGE_FAILURE = "Unable to export contact list to a %1$s file";

    public final String fileTypeCsv = "CSV";

    private final String csvHeaders = "Name,Phone No,Email,Address,Tags,Notes\n";

    private final Path exportCsvPath = Paths.get("exports" , "bizbook.csv");

    private final String fileType;

    /**
     * Creates an ExportCsvCommand.
     *
     * @param fileType to export the data into.
     */
    public ExportCommand(String fileType) {
        requireNonNull(fileType);
        this.fileType = fileType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            if (fileType.equals(fileTypeCsv)) {
                exportToCsv(model);
            }
        } catch (IOException io) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, fileType));
    }

    /**
     * Exports the contact list into a csv file.
     * Iterate through every person, convert them to a csv format and
     * write into the csv file.
     *
     * @throws IOException if an error occurs when creating the csv file.
     */
    private void exportToCsv(Model model) throws IOException {
        if (!Files.exists(exportCsvPath.getParent())) {
            Files.createDirectories(exportCsvPath.getName(0));
        }
        // Create a csv file to save the tasks
        File dataFile = new File(exportCsvPath.toString());
        FileWriter fw = new FileWriter(dataFile);

        fw.write(this.csvHeaders);

        ObservableList<Person> personList = model.getFilteredPersonList();
        for (Person person : personList) {
            String personData = this.toCsvString(person);
            fw.write(personData + "\n");
        }
        fw.close();
    }

    /**
     * Converts a {@code Person} object into a csv format.
     *
     * @param person to be encoded into a csv format.
     * @return A csv representation of the {@Code Person} object.
     */
    public String toCsvString(Person person) {
        StringJoiner sj = new StringJoiner(",");

        sj.add(person.getName().fullName);
        sj.add(person.getPhone().value);
        sj.add(person.getEmail().value);

        // Prevent excel from sepreating entries due to commas
        String address = "\"" + person.getAddress().value + "\"";
        sj.add(address);

        List<String> tags = new ArrayList<>();
        List<String> notes = new ArrayList<>();

        tags.addAll(person.getTags().stream()
            .map((tag) -> tag.toString())
            .toList());

        notes.addAll(person.getNotes().stream()
            .map((note) -> note.toString())
            .toList());

        sj.add("\"" + tags.toString().replaceAll("[\\[\\]]", "") + "\"");
        sj.add("\"" + notes.toString().replaceAll("[\\[\\]]", "") + "\"");

        return sj.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ExportCommand)) {
            return false;
        }

        ExportCommand otherExportCommand = (ExportCommand) other;
        return fileType.equals(otherExportCommand.fileType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("fileType", fileType)
                .toString();
    }
}
