package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import java.nio.file.Path;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
/**
 * Archives the address book.
 */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_PATH + "FileName\n"
            + "The file name should be a path of an json file "
            + "and not contain any slash `/` \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PATH + "mybook.json";
    public static final String MESSAGE_SUCCESS = "Archive Path changed to: %1$s";

    private Path archivePath;

    public ArchiveCommand(Path archivePath) {
        this.archivePath = archivePath;
    }

    public Path getArchivePath() {
        return archivePath;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(String.format(MESSAGE_SUCCESS, archivePath));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ArchiveCommand)) {
            return false;
        }

        ArchiveCommand otherLoadCommand = (ArchiveCommand) other;
        return archivePath.equals(otherLoadCommand.archivePath);
    }

}

