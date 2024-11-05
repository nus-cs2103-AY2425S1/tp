package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Archives a person
 */
public class ArchiveCommand extends Command {
    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Archives the details of the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";
    public static final String MESSAGE_ARCHIVE_PERSON_SUCCESS = "Archived Person: %1$s";
    private final Index index;
    /**
     * Creates an ArchiveCommand to add the Person at the specified {@code Index}
     */
    public ArchiveCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        boolean isArchivedList = model.getIsArchivedList();

        // Allow user to use only if currently viewing the main list
        if (isArchivedList) {
            throw new CommandException(Messages.MESSAGE_NOT_IN_MAIN_LIST);
        }
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToArchive = lastShownList.get(index.getZeroBased());
        model.addArchivedPerson(personToArchive); // Add person to archived list
        model.deletePerson(personToArchive);
        return new CommandResult(String.format(MESSAGE_ARCHIVE_PERSON_SUCCESS, Messages.format(personToArchive)));
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

        ArchiveCommand archiveCommand = (ArchiveCommand) other;
        return index.equals(archiveCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
