package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.*;

import seedu.address.commons.core.*;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.*;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Archives an existing person in the address book.
 */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD_ARCHIVE = "archive";

    public static final String COMMAND_WORD_UNARCHIVE = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD_ARCHIVE + "/" + COMMAND_WORD_UNARCHIVE
            + ": Archives/unarchives the person identified "
            + "by the index number used in the displayed person list. "
            + "Parameters: INDEX (must be a positive integer)";
    public static final String MESSAGE_ARCHIVE_PERSON_SUCCESS = "Archived Person: %1$s";

    public static final String MESSAGE_PERSON_IS_ALREADY_ARCHIVED =
            "This person is already archived in the address book.";

    public static final String MESSAGE_UNARCHIVE_PERSON_SUCCESS = "Unarchived Person: %1$s";

    public static final String MESSAGE_PERSON_IS_ALREADY_UNARCHIVED =
            "This person is already unarchived in the address book.";

    private final Index index;
    private final boolean isArchive;

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private Person personToModify;

    /**
     * @param index of the person in the filtered person list to archive/unarchive
     */
    public ArchiveCommand(Index index, boolean isArchive) {
        requireNonNull(index);

        this.index = index;
        this.isArchive = isArchive;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToModify = lastShownList.get(index.getZeroBased());

        if (isArchive == personToModify.isArchived()) {
            throw new CommandException(isArchive
                    ? MESSAGE_PERSON_IS_ALREADY_ARCHIVED
                    : MESSAGE_PERSON_IS_ALREADY_UNARCHIVED);
        }

        Person modifiedPerson = modifyPerson(personToModify);
        model.setPerson(personToModify, modifiedPerson);
        logArchiveInfo(modifiedPerson);
        return generateCommandResult(modifiedPerson);
    }

    private CommandResult generateCommandResult(Person modifiedPerson) {
        return new CommandResult(String.format(
                isArchive ? MESSAGE_ARCHIVE_PERSON_SUCCESS : MESSAGE_UNARCHIVE_PERSON_SUCCESS,
                Messages.format(modifiedPerson)));
    }

    private void logArchiveInfo(Person modifiedPerson) {
        logger.info(String.format(
                "%s %s",
                isArchive ? "Archived" : "Unarchived",
                Messages.format(modifiedPerson)
        ));
    }

    private Person modifyPerson(Person personToModify) {
        return new Person(
                personToModify.getName(),
                personToModify.getPhone(),
                personToModify.getEmail(),
                personToModify.getAddress(),
                personToModify.getPriority(),
                personToModify.getRemark(),
                personToModify.getDateOfBirth(),
                personToModify.getIncome(),
                personToModify.getFamilySize(),
                personToModify.getTags(),
                personToModify.getUpdatedAt(),
                this.isArchive
        );
    }

    /**
     * @return the {@link Person} whose isArchived is to be modified if it exists,
     *      otherwise {@code null} if person does not exist or command has not yet been executed.
     */
    public Person getPersonToModify() {
        return personToModify;
    }

    @Override
    public String getCommandWord() {
        return isArchive ? COMMAND_WORD_ARCHIVE : COMMAND_WORD_UNARCHIVE;
    }
}
