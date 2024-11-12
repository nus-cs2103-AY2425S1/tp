package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Person;


/**
 * Adds a grade to a person identified by the index in the filtered person list.
 */
public class AddGradeCommand extends Command {
    public static final String COMMAND_WORD = "addGrade";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a grade to the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing grades will be updated by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) n/EXAM_NAME s/EXAM_SCORE w/EXAM_WEIGHTAGE\n"
            + "Example: " + COMMAND_WORD + " 1 n/Midterm s/85 w/30";
    public static final String MESSAGE_ADD_GRADE_SUCCESS = "Added grade for '%1$s' to %2$s";
    public static final String MESSAGE_EXCEED_WEIGHTAGE = "The total weightage of grades cannot exceed 100%";

    private final Grade toAdd;
    private final Index index;

    /**
     * Constructs a {@code AddGradeCommand} to add the specified {@code Grade} to the person at the given {@code Index}.
     *
     * @param index Index of the person in the filtered person list.
     * @param grade Grade to be added to the person.
     */
    public AddGradeCommand(Index index, Grade grade) {
        requireNonNull(index);
        requireNonNull(grade);
        this.index = index;
        toAdd = grade;
    }

    /**
     * Executes the command to add the grade to the person in the model's list.
     *
     * @param model The {@code Model} which contains the list of persons.
     * @return CommandResult indicating the result of the operation.
     * @throws CommandException if the index is invalid or the person cannot be found.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        try {
            Person updatedPerson = personToEdit.addGrade(this.toAdd);
            model.setPerson(personToEdit, updatedPerson);
            return new CommandResult(
                    String.format(MESSAGE_ADD_GRADE_SUCCESS, toAdd.getTestName(), personToEdit.getName()));
        } catch (IllegalStateException e) {
            throw new CommandException("The total weightage of grades cannot exceed 100%");
        }

    }

    /**
     * Checks if this AddGradeCommand is equal to another object.
     *
     * @param other The other object to compare with.
     * @return true if both AddGradeCommand objects have the same index and grade to add.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddGradeCommand)) {
            return false;
        }

        AddGradeCommand otherAddGradeCommand = (AddGradeCommand) other;
        return index.equals(otherAddGradeCommand.index)
                && toAdd.equals(otherAddGradeCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("toAdd", toAdd)
                .toString();
    }
}
