package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Random;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Randomly selects a person from the current list of persons shown to the user
 */
public class RandomCommand extends Command {

    public static final String COMMAND_WORD = "random";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Randomly selects a student from the current "
            + "displayed list."
            + "Any parameters specified are ignored."
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Success. Displaying randomly selected student.";
    public static final String MESSAGE_RANDOM_INSUFFICIENT_STUDENTS = "The current list is too small for a student to"
            + " be randomly selected."
            + "\n"
            + "Use 'list' to display students.";

    private final Random random;
    private String message;

    /**
     * Creates a RandomCommand .
     */
    public RandomCommand() {
        this.random = new Random();
        this.message = "";
    }

    /**
     * Creates a RandomCommand with an additional warning message to display to the user.
     *
     * @param message Warning message to be displayed to the user
     */
    public RandomCommand(String message) {
        this();
        this.message = message;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        int sizeOfCurrentList = model.getFilteredPersonList().size();

        if (sizeOfCurrentList < 2) {
            throw new CommandException(MESSAGE_RANDOM_INSUFFICIENT_STUDENTS);
        }

        int randomIndex = random.nextInt(sizeOfCurrentList);
        Person randomPerson = model.getFilteredPersonList().get(randomIndex);

        model.updateFilteredPersonList((person) -> person.equals(randomPerson));

        return new CommandResult(MESSAGE_SUCCESS + "\n" + message);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RandomCommand)) {
            return false;
        }

        RandomCommand randomCommand = (RandomCommand) other;
        return random.equals(randomCommand.random);
    }

    @Override
    public int hashCode() {
        return random.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("randomGen", random)
                .toString();
    }
}
