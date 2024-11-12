package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
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
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Examples: " + COMMAND_WORD_ARCHIVE + " 1, " + COMMAND_WORD_UNARCHIVE + " 1";
    public static final String MESSAGE_ARCHIVE_PERSON_SUCCESS = "Archived Person: %1$s";

    public static final String MESSAGE_PERSON_IS_CURRENTLY_ARCHIVED =
            "This person is currently archived in the address book.";

    public static final String MESSAGE_UNARCHIVE_PERSON_SUCCESS = "Unarchived Person: %1$s";

    public static final String MESSAGE_PERSON_IS_CURRENTLY_NOT_ARCHIVED =
            "This person is currently not archived in the address book.";

    private final Index index;
    private final boolean shouldArchive;

    private final Logger logger = LogsCenter.getLogger(ArchiveCommand.class);

    private Person personToModify;
    private Person modifiedPerson;

    /**
     * @param index of the person in the filtered person list to archive/unarchive
     * @param shouldArchive depending on whether the person should be archived or not
     */
    public ArchiveCommand(Index index, boolean shouldArchive) {
        requireNonNull(index);

        this.index = index;
        this.shouldArchive = shouldArchive;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToModify = lastShownList.get(index.getZeroBased());

        if (shouldArchive == personToModify.isArchived()) {
            throw new CommandException(shouldArchive
                    ? MESSAGE_PERSON_IS_CURRENTLY_ARCHIVED
                    : MESSAGE_PERSON_IS_CURRENTLY_NOT_ARCHIVED);
        }

        modifiedPerson = modifyPerson(personToModify);
        model.setPerson(personToModify, modifiedPerson);
        logArchiveInfo(modifiedPerson);
        return generateCommandResult(modifiedPerson);
    }

    private CommandResult generateCommandResult(Person modifiedPerson) {
        return new CommandResult(String.format(
                shouldArchive ? MESSAGE_ARCHIVE_PERSON_SUCCESS : MESSAGE_UNARCHIVE_PERSON_SUCCESS,
                Messages.format(modifiedPerson)));
    }

    private void logArchiveInfo(Person modifiedPerson) {
        logger.info(String.format(
                "%s %s",
                shouldArchive ? "Archived" : "Unarchived",
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
                personToModify.getDateOfBirth(),
                personToModify.getIncome(),
                personToModify.getFamilySize(),
                personToModify.getTags(),
                personToModify.getSchemes(),
                personToModify.getUpdatedAt(),
                shouldArchive
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
        return shouldArchive ? COMMAND_WORD_ARCHIVE : COMMAND_WORD_UNARCHIVE;
    }

    @Override
    public String undo(Model model, CommandHistory pastCommands) {
        model.setPerson(modifiedPerson, personToModify);
        pastCommands.remove();
        return String.format(
                shouldArchive ? MESSAGE_UNARCHIVE_PERSON_SUCCESS : MESSAGE_ARCHIVE_PERSON_SUCCESS,
                Messages.format(personToModify));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // handles null
        if (!(other instanceof ArchiveCommand otherArchiveCommand)) {
            return false;
        }

        return this.index.equals(otherArchiveCommand.index)
                && this.shouldArchive == otherArchiveCommand.shouldArchive;
    }
}
