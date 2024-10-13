package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Grade;
import seedu.address.model.person.GradeList;
import seedu.address.model.person.Person;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_PERSON_SUCCESS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Adds a grade to a person identified by the index in the filtered person list.
 */
public class GradeCommand extends Command {
    public static final String COMMAND_WORD = "grade";
    public static final String MESSAGE_USAGE = "";
    private final Grade toAdd;
    private final Index index;

    /**
     * Constructs a {@code GradeCommand} to add the specified {@code Grade} to the person at the given {@code Index}.
     *
     * @param index Index of the person in the filtered person list.
     * @param grade Grade to be added to the person.
     */
    public GradeCommand(Index index, Grade grade) {
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
        GradeList gradeList = personToEdit.getGradeList();
        gradeList.addGrade(this.toAdd);
        // need help here: this doesn't obey Law of Demeter
        // How to resolve it?
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(personToEdit)));

    }

    /**
     * Checks if this GradeCommand is equal to another object.
     *
     * @param other The other object to compare with.
     * @return true if both GradeCommand objects have the same index and grade to add.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GradeCommand)) {
            return false;
        }

        GradeCommand otherGradeCommand = (GradeCommand) other;
        return index.equals(otherGradeCommand.index)
                && toAdd.equals(otherGradeCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("toAdd", toAdd)
                .toString();
    }
}
