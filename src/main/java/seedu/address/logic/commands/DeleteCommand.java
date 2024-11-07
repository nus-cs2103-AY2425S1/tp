package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_UNDO_DELETE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * Deletes the people identified using the displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                + ": Deletes the people identified by the index numbers used in the displayed person list.\n"
                + "Parameters: INDEXES (must be positive integers)\n"
                + "Example: " + COMMAND_WORD + " 1, 2";

    public static final String MESSAGE_DELETE_PEOPLE_SUCCESS = "Deleted people:\n%s";

    private final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

    private final Index[] targetIndexes;
    private final List<Person> personsToDelete = new ArrayList<>();
    private final List<Appointment> deletedAppointments = new ArrayList<>();

    /**
     * Takes in the array of indexes to be used for deletion.
     *
     * @param targetIndexes array of Index.
     */
    public DeleteCommand(Index[] targetIndexes) {
        this.targetIndexes = targetIndexes;

    }

    /**
     * Executes the Delete command with multiple >= 1 index.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Message to user that deletions were successful.
     * @throws CommandException If any of the index given falls out of range.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEXES);
            }
            personsToDelete.add(lastShownList.get(targetIndex.getZeroBased()));
        }

        logger.info("----------------[PEOPLE TO BE DELETED]["
                + Arrays.stream(targetIndexes)
                .map(index -> String.valueOf(index.getOneBased())) // Convert to String
                .collect(Collectors.joining(", ")) + "]");

        StringBuilder sb = new StringBuilder();
        for (Person person : personsToDelete) {
            deletedAppointments.addAll(model.deleteAppointments(person.getName()));
            model.deletePerson(person);
            sb.append(person.getName()).append("\n");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PEOPLE_SUCCESS, sb.toString().trim()));
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public String undo(Model model, CommandHistory pastCommands) {
        Map<Person, Integer> personToIndexMap = new HashMap<>();

        for (int i = 0; i < personsToDelete.size(); i++) {
            personToIndexMap.put(personsToDelete.get(i), targetIndexes[i].getZeroBased());
        }
        List<Map.Entry<Person, Integer>> entryList = new ArrayList<>(personToIndexMap.entrySet());
        entryList.sort(Map.Entry.comparingByValue());

        for (Map.Entry<Person, Integer> entry : entryList) {
            model.addPerson(entry.getKey(), entry.getValue());
        }
        for (Appointment appointment: deletedAppointments) {
            model.addAppointment(appointment);
        }
        String namesAddedBack = personsToDelete.stream()
                .map(person -> person.getName().toString())
                .collect(Collectors.joining(", "));
        pastCommands.remove();
        return String.format(MESSAGE_UNDO_DELETE, namesAddedBack);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand otherDeleteCommand)) {
            return false;
        }

        return Arrays.equals(targetIndexes, otherDeleteCommand.targetIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndexes", Arrays.toString(targetIndexes))
                .toString();
    }
}
