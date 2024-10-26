package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Archives an existing person in the address book.
 */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Archives the person identified "
            + "by the index number used in the displayed person list. "
            + "Parameters: INDEX (must be a positive integer)";
    public static final String MESSAGE_ARCHIVE_PERSON_SUCCESS = "Archived Person: %1$s";

    public static final String MESSAGE_PERSON_IS_ALREADY_ARCHIVED =
            "This person is already archived in the address book.";

    private final Index index;

    private Person personToArchive;

    /**
     * @param index of the person in the filtered person list to archive
     */
    public ArchiveCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToArchive = lastShownList.get(index.getZeroBased());

        if (personToArchive.isArchived()) {
            throw new CommandException(MESSAGE_PERSON_IS_ALREADY_ARCHIVED);
        }

        Person archivedPerson = archivePerson(personToArchive);
        model.setPerson(personToArchive, archivedPerson);
        return new CommandResult(String.format(MESSAGE_ARCHIVE_PERSON_SUCCESS, archivedPerson));
    }

    private static Person archivePerson(Person personToArchive) {
        return new Person(
                personToArchive.getName(),
                personToArchive.getPhone(),
                personToArchive.getEmail(),
                personToArchive.getAddress(),
                personToArchive.getPriority(),
                personToArchive.getRemark(),
                personToArchive.getDateOfBirth(),
                personToArchive.getIncome(),
                personToArchive.getFamilySize(),
                personToArchive.getTags(),
                personToArchive.getUpdatedAt(),
                true
        );
    }

    /**
     * @return the {@link Person} to be archived if it exists,
     *      otherwise {@code null} if person does not exist or command has not yet been executed.
     */
    public Person getPersonToArchive() {
        return personToArchive;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
