package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Gets the specified parameter from all the people in the filtered list.
 */
public class GetCommand extends Command {
    public static final String COMMAND_WORD = "get";


    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Get the specified parameter of all the people in the filtered persons list.\n"
            + "Parameters: PARAMETER \n"
            + "Example: " + COMMAND_WORD + " e";

    public static final String MESSAGE_GET_PARAMETER_SUCCESS = "Here are the details:\n%s";
    private final String parameter;


    /**
     * Takes in the array of indexes to be used for deletion.
     *
     * @param parameter the specified parameter.
     */
    public GetCommand(String parameter) {
        this.parameter = parameter;
    }

    /**
     * Executes the Get command with the specified parameter.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Message to user that deletions were successful.
     * @throws CommandException If any of the param given are invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        int size = lastShownList.size();
        String s = "";

        switch (parameter) {

        case "e":
            for (int i = 0; i < size - 1; i++) {
                s += lastShownList.get(i).getEmail() + ",\n";
            }
            s += lastShownList.get(size - 1).getEmail();
            break;


        case "p":
            for (int i = 0; i < size - 1; i++) {
                s += lastShownList.get(i).getPhone() + ",\n";
            }
            s += lastShownList.get(size - 1).getPhone();
            break;

        case "n":
            for (int i = 0; i < size - 1; i++) {
                s += lastShownList.get(i).getName() + ",\n";
            }
            s += lastShownList.get(size - 1).getName();
            break;

        case "a":
            for (int i = 0; i < size - 1; i++) {
                s += lastShownList.get(i).getAddress() + ",\n";
            }
            s += lastShownList.get(size - 1).getAddress();
            break;

        default:
            throw new CommandException(Messages.MESSAGE_INVALID_PARAMETER);
        }

        return new CommandResult(String.format(MESSAGE_GET_PARAMETER_SUCCESS, s));
    }

}
