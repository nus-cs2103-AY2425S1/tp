package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashMap;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.AbsentDate;
import seedu.address.model.person.AbsentReason;
import seedu.address.model.person.Person;

/**
 * Adds absent date and reason to an existing person in the address book.
 */
public class AddAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "addAttendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the date where student "
            + "identified by the index is absent and the reason. To delete date, leave the reason empty.\n"
            + "Parameters: [INDEX] aa/DATE ar/REASON\n"
            + "Example to add absent date: " + COMMAND_WORD + " 1 aa/24-09-2024 ar/MC\n"
            + "Example to delete absent date: " + COMMAND_WORD + " 1 aa/24-09-2024 ar/\n";

    public static final String MESSAGE_ADD_ATTENDANCE_SUCCESS = "Added attendance for Person: %1$s";
    public static final String MESSAGE_DELETE_ATTENDANCE_SUCCESS = "Removed attendance from Person: %1$s";

    private final Index index;
    private final AbsentDate absentDate;
    private final AbsentReason absentReason;

    /**
     * @param index of the person in the filtered person list to include why the person is absent
     * @param absentDate date where the person to be updated is absent
     * @param absentReason reason why the person to be updated is absent
     */
    public AddAttendanceCommand(Index index, AbsentDate absentDate, AbsentReason absentReason) {
        requireAllNonNull(index, absentDate, absentReason);

        this.index = index;
        this.absentDate = absentDate;
        this.absentReason = absentReason;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        HashMap<AbsentDate, AbsentReason> newAttendances = new HashMap<>(personToEdit.getAttendances());
        if (absentReason.absentReason.isEmpty()) {
            newAttendances.remove(this.absentDate);
        } else {
            newAttendances.put(this.absentDate, this.absentReason);
        }

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRegisterNumber(), personToEdit.getSex(),
                personToEdit.getStudentClass(), personToEdit.getEcName(), personToEdit.getEcNumber(),
                personToEdit.getExams(), personToEdit.getTags(), newAttendances, personToEdit.getSubmissions());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    private String generateSuccessMessage(Person personToEdit) {
        String message = !absentReason.absentReason.isEmpty()
                ? MESSAGE_ADD_ATTENDANCE_SUCCESS : MESSAGE_DELETE_ATTENDANCE_SUCCESS;
        return String.format(message, Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddAttendanceCommand)) {
            return false;
        }

        AddAttendanceCommand e = (AddAttendanceCommand) other;
        return index.equals(e.index)
                && absentDate.equals(e.absentDate)
                && absentReason.equals(e.absentReason);
    }

}
