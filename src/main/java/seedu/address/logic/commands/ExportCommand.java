package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

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
    public static final String MESSAGE_FAILURE = "Unable to export contact list to a %1$s file"
            + ", please ensure that the file is closed before exporting";
    public static final String MESSAGE_CONSTRAINTS = "This file type is not supported, we only support CSV and VCF";

    private final String csvHeaders = "Name,Phone No,Email,Address,Tags,Notes";

    private final Path exportCsvPath = Paths.get("exports" , "bizbook.csv");

    private final FileType fileType;

    /**
     * Enum of supported file types to export.
     */
    public static enum FileType {
        CSV,
        VCF
    }

    /**
     * Creates an ExportCsvCommand.
     *
     * @param fileType to export the data into.
     */
    public ExportCommand(FileType fileType) {
        requireNonNull(fileType);
        this.fileType = fileType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            switch (fileType) {
            case CSV:
                this.exportToCsv(model);
                break;
            default:
                throw new CommandException(String.format(MESSAGE_CONSTRAINTS));
            }
        } catch (IOException io) {
            throw new CommandException(String.format(MESSAGE_FAILURE, fileType));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, fileType));
    }

    /**
     * Exports the contact list into a csv file.
     * Iterate through every person, convert them to a csv format and
     * write into the csv file.
     *
     * @throws IOException if an error occurs when creating the csv file.
     * @throws CommandException if an error occurs during command execution.
     */
    private void exportToCsv(Model model) throws CommandException, IOException {

        FileUtil.createIfMissing(exportCsvPath);

        StringJoiner sj = new StringJoiner("\n");
        sj.add(csvHeaders);

        ObservableList<Person> personList = model.getFilteredPersonList();
        for (Person person : personList) {
            String personData = this.toCsvString(person);
            sj.add(personData);
        }

        FileUtil.writeToFile(exportCsvPath, sj.toString());
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

        // Prevent excel from separating entries due to commas
        String address = "\"" + person.getAddress().value + "\"";
        sj.add(address);

        String tags = person.getTags().stream()
            .map(Tag::toString).collect(Collectors.joining(","));

        String notes = person.getNotes().stream()
            .map(Note::getNote).collect(Collectors.joining(","));

        sj.add("\"" + tags + "\"");
        sj.add("\"" + notes + "\"");

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
