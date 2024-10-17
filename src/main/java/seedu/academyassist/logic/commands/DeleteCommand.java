package seedu.academyassist.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.transformation.FilteredList;
import seedu.academyassist.commons.util.ToStringBuilder;
import seedu.academyassist.logic.Messages;
import seedu.academyassist.logic.commands.exceptions.CommandException;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.person.Ic;
import seedu.academyassist.model.person.IcMatchesPredicate;
import seedu.academyassist.model.person.Person;

/**
 * Deletes a student identified using it's NRIC from the management system.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by the NRIC used in the displayed student list.\n"
            + "Parameters: NRIC (should follow the format of Singaporean IC and FIN numbers)\n"
            + "Example: " + COMMAND_WORD + " S1234567A";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Student %1$s (%2$s) is successfully deleted.\n";

    private final Ic targetIc;

    public DeleteCommand(Ic targetIc) {
        this.targetIc = targetIc;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        FilteredList<Person> filteredPersonList = new FilteredList<>(model.getFilteredPersonList());
        filteredPersonList.setPredicate(new IcMatchesPredicate(targetIc));

        if (filteredPersonList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NRIC);
        }

        Person personToDelete = filteredPersonList.get(0);
        model.deletePerson(personToDelete);

        //model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.getName(),
                personToDelete.getIc()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIc.equals(otherDeleteCommand.targetIc);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIc", targetIc)
                .toString();
    }
}
