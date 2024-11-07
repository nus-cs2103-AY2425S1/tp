package tuteez.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import tuteez.commons.core.index.Index;
import tuteez.commons.util.ToStringBuilder;
import tuteez.logic.Messages;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.Model;
import tuteez.model.person.Name;
import tuteez.model.person.Person;

/**
 * Displays a person identified using it's displayed index from tuteez.
 */
public class DisplayCommand extends Command {

    public static final String COMMAND_WORD = "display";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the details of the student identified "
            + "by the index number used in the displayed student list or by name.\n"
            + "Parameters: INDEX (must be a positive integer) or NAME (must be a valid name in tuteez)\n"
            + "Example: " + COMMAND_WORD + " 1" + " or " + COMMAND_WORD + " John Doe";

    public static final String MESSAGE_DISPLAY_PERSON_SUCCESS = "Displayed Person: %1$s";

    private final Index targetIndex;
    private final Name targetName;

    /**
     * Creates a DisplayCommand to display the person at the specified {@code targetIndex} of the displayed list.
     * @param targetIndex the index of the person in the displayed list to display
     */
    public DisplayCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    /**
     * Creates a DisplayCommand to display the person with the specified {@code targetName}.
     * @param targetName the name of the person to display
     */
    public DisplayCommand(Name targetName) {
        this.targetIndex = null;
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToDisplay;
        if (targetIndex != null) {
            personToDisplay = getPersonToDisplayByIndex(model, targetIndex);
        } else {
            personToDisplay = getPersonToDisplayByName(model, targetName);
        }

        model.displayPerson(personToDisplay);
        return new CommandResult(String.format(MESSAGE_DISPLAY_PERSON_SUCCESS,
                Messages.formatPersonName(personToDisplay)));
    }

    private Person getPersonToDisplayByIndex(Model model, Index index) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return lastShownList.get(index.getZeroBased());
    }

    private Person getPersonToDisplayByName(Model model, Name name) throws CommandException {
        Person personToDisplay = model.findPersonByName(name);
        if (personToDisplay == null) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME, name));
        }
        return personToDisplay;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DisplayCommand)) {
            return false;
        }

        DisplayCommand otherDisplayCommand = (DisplayCommand) other;
        boolean isIndexValid = targetIndex != null && targetIndex.equals(otherDisplayCommand.targetIndex);
        boolean isNameValid = targetName != null && targetName.equals(otherDisplayCommand.targetName);
        return isIndexValid || isNameValid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("targetName", targetName)
                .toString();
    }
}
