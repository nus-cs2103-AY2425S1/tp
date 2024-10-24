package careconnect.logic.commands;

import static careconnect.commons.util.CollectionUtil.requireAllNonNull;
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
import careconnect.model.person.AppointmentDate;
import careconnect.model.person.Email;
import careconnect.model.person.Name;
import careconnect.model.person.Person;
import careconnect.model.person.Phone;
import careconnect.model.tag.Tag;

/**
 * Adds a person to the address book.
 */
public class AddLogCommand extends Command {

    public static final String COMMAND_WORD = "addlog";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a log to the person "
            + "identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + CliSyntax.PREFIX_REMARK + "REMARK "
            + "[" + CliSyntax.PREFIX_DATE + "DATE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + CliSyntax.PREFIX_REMARK + "First meetup. "
            + CliSyntax.PREFIX_DATE + "2019-10-15 14:00";

    public static final String MESSAGE_SUCCESS = "New log: %1$s added to person %2$s";

    private final Index targetIndex;
    private final Log log;

    /**
     * Creates an AddLogCommand to add the specified {@code Log}
     */
    public AddLogCommand(Index index, Log log) {
        requireAllNonNull(index, log);
        this.targetIndex = index;
        this.log = log;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToAddLog}
     */
    private static Person createPersonWithNewLog(Person personToAddLog, Log newLog) {
        requireNonNull(personToAddLog);

        Name name = personToAddLog.getName();
        Phone phone = personToAddLog.getPhone();
        Email email = personToAddLog.getEmail();
        Address address = personToAddLog.getAddress();
        Set<Tag> tags = personToAddLog.getTags();
        ArrayList<Log> updatedLogs = new ArrayList<>(personToAddLog.getLogs());
        AppointmentDate appointmentDate = personToAddLog.getAppointmentDate();
        updatedLogs.add(newLog);

        return new Person(name, phone, email, address, tags, updatedLogs, appointmentDate);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddLog = lastShownList.get(targetIndex.getZeroBased());
        Person personWithLogAdded = createPersonWithNewLog(personToAddLog, this.log);

        model.setPerson(personToAddLog, personWithLogAdded);
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.log.getRemark(),
                personToAddLog.getName()),
                false, false, targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddLogCommand)) {
            return false;
        }
        AddLogCommand otherAddLogCommand = (AddLogCommand) other;

        return targetIndex.equals(otherAddLogCommand.targetIndex)
                && log.equals(otherAddLogCommand.log);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("log", log)
                .toString();
    }

}
