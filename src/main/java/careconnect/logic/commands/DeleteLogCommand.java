package careconnect.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import careconnect.commons.core.index.Index;
import careconnect.commons.util.ToStringBuilder;
import careconnect.logic.Messages;
import careconnect.logic.commands.exceptions.CommandException;
import careconnect.logic.parser.CliSyntax;
import careconnect.model.Model;
import careconnect.model.log.Log;
import careconnect.model.person.Address;
import careconnect.model.person.Email;
import careconnect.model.person.Name;
import careconnect.model.person.Person;
import careconnect.model.person.Phone;
import careconnect.model.tag.Tag;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteLogCommand extends Command {

    public static final String COMMAND_WORD = "deletelog";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the log for the person"
            + " identified by the index number\n"
            + "Parameters: PERSON_INDEX (must be a positive integer)\n"
            + CliSyntax.PREFIX_LOG_INDEX
            + "LOG_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + CliSyntax.PREFIX_LOG_INDEX + "2";

    public static final String MESSAGE_DELETE_LOG_SUCCESS = "Deleted log %1$s for person %2$s";

    private final Index personIndex;
    private final Index logIndex;
    private Log deletedLog;

    /**
     * Creates a DeleteLogCommand to delete the specified log
     *     with {@code logIndex} for the person with {@code personIndex}
     */
    public DeleteLogCommand(Index personIndex, Index logIndex) {
        this.personIndex = personIndex;
        this.logIndex = logIndex;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToDeleteLog}
     */
    private Person createPersonWithoutLog(Person personToDeleteLog, Index deletedLogIndex) {
        assert personToDeleteLog != null;

        Name name = personToDeleteLog.getName();
        Phone phone = personToDeleteLog.getPhone();
        Email email = personToDeleteLog.getEmail();
        Address address = personToDeleteLog.getAddress();
        Set<Tag> tags = personToDeleteLog.getTags();
        ArrayList<Log> updatedLogs = new ArrayList<>(personToDeleteLog.getLogs());

        this.deletedLog = updatedLogs.get(deletedLogIndex.getZeroBased());
        updatedLogs.remove(deletedLogIndex.getZeroBased());

        return new Person(name, phone, email, address, tags, updatedLogs);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        if (logIndex.getZeroBased() >= lastShownList.get(personIndex.getZeroBased()).getLogs().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOG_INDEX);
        }

        Person personToDeleteLog = lastShownList.get(personIndex.getZeroBased());
        Person personWithoutLog = createPersonWithoutLog(personToDeleteLog, logIndex);
        model.setPerson(personToDeleteLog, personWithoutLog);

        return new CommandResult(String.format(MESSAGE_DELETE_LOG_SUCCESS,
                personToDeleteLog.getLogs().get(logIndex.getZeroBased()).getRemark(),
                personToDeleteLog.getName()),
                false, false, personIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteLogCommand otherDeleteLogCommand)) {
            return false;
        }

        return personIndex.equals(otherDeleteLogCommand.personIndex)
                && deletedLog.equals(otherDeleteLogCommand.deletedLog);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personIndex", personIndex)
                .add("logIndex", logIndex)
                .toString();
    }
}
