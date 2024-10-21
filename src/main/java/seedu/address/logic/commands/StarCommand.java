package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.StarredStatus;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class StarCommand extends Command {
    public static final String COMMAND_WORD = "star";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Stars the person identified by the name or index in the address book.\n"
            + "Parameters: NAME or INDEX (must match exactly one person or be a valid index)\n"
            + "Example: " + COMMAND_WORD + " John Doe\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_STAR_PERSON_SUCCESS = "Starred Person: %1$s";
    public static final String MESSAGE_UNSTAR_PERSON_SUCCESS = "Unstarred Person: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "The person's name provided is invalid";
    public static final String MESSAGE_INVALID_INDEX = "The person index provided is invalid";

    private final Name targetName;
    private final Index targetIndex;
    private final StarredStatus toStar;

    /**
     * @param targetName of the person to be starred or unstarred in the list
     */
    public StarCommand(Name targetName, StarredStatus toStar) {
        this.targetName = targetName;
        this.targetIndex = null;
        this.toStar = toStar;
    }

    /**
     * @param targetIndex of the index of the person to be starred or unstarred in the list
     */
    public StarCommand(Index targetIndex, StarredStatus toStar) {
        this.targetIndex = targetIndex;
        this.targetName = null;
        this.toStar = toStar;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToStar;

        if (targetName != null) {
            Optional<Person> personOptional = lastShownList.stream()
                    .filter(person -> person.getName().equals(targetName))
                    .findFirst();

            if (personOptional.isEmpty()) {
                throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
            }
            personToStar = personOptional.get();
        } else {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_INDEX);
            }
            personToStar = lastShownList.get(targetIndex.getZeroBased());
        }

        model.deletePerson(personToStar);

        String result = toStar.value.equals("true")
                ? String.format(MESSAGE_STAR_PERSON_SUCCESS, personToStar.getName())
                : String.format(MESSAGE_UNSTAR_PERSON_SUCCESS, personToStar.getName());

        return new CommandResult(result);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StarCommand)) {
            return false;
        }

        StarCommand otherStarCommand = (StarCommand) other;


        return (targetName != null && targetName.equals(otherStarCommand.targetName))
                || (targetIndex != null && targetIndex.equals(otherStarCommand.targetIndex))
                && toStar.equals(otherStarCommand.toStar);
    }

    @Override
    public String toString() {
        if (targetName != null) {
            return String.format("StarCommand[targetName=%s, toStar=%s]", targetName, toStar);
        } else {
            return String.format("StarCommand[targetIndex=%d, toStar=%s]", targetIndex.getOneBased(), toStar);
        }
    }
}
