package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WORKEXP;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.WorkExp;

/**
 * Adds a work experience to an existing person in the address book.
 */
public class AddWorkExperienceCommand extends Command {

    public static final String COMMAND_WORD = "addw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a work experience to the specified person. "
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_WORKEXP + "WORK EXPERIENCE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_WORKEXP + "Intern,Google,2024";

    public static final String MESSAGE_SUCCESS = "New work experience added to %1$s: %2$s";
    public static final String MESSAGE_INVALID_INDEX = "The person index provided is invalid.";

    private final Index index;
    private final WorkExp workExp;

    /**
     * Creates an AddWorkExperience to add the specified {@code WorkExp} to a person at the specified {@code Index}.
     */
    public AddWorkExperienceCommand(Index index, WorkExp workExp) {
        requireNonNull(index);
        requireNonNull(workExp);
        this.index = index;
        this.workExp = workExp;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), workExp,
                personToEdit.getTags(), personToEdit.getUniversity(), personToEdit.getMajor(),
                personToEdit.getInterests());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, personToEdit.getName(), workExp));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddWorkExperienceCommand)) {
            return false;
        }

        AddWorkExperienceCommand otherCommand = (AddWorkExperienceCommand) other;
        return index.equals(otherCommand.index)
                && workExp.equals(otherCommand.workExp);
    }
}
