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
            + "Example: " + COMMAND_WORD + " e/ p/";

    public static final String MESSAGE_GET_PARAMETER_SUCCESS = "Here are the %s details:\n%s";
    private final String[] parameters;


    /**
     * Takes in the array of indexes to be used for deletion.
     *
     * @param parameters the specified parameters.
     */
    public GetCommand(String[] parameters) {
        this.parameters = parameters;
    }

    /**
     * Executes the Get command with the specified parameters.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Message to user that extraction of the details were successful.
     * @throws CommandException If any of the params given are invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        int size = lastShownList.size();
        String resultString = "";
        boolean[] runBefore = new boolean[4];
        for (int i = 0; i < runBefore.length; i++) {
            runBefore[i] = false;
        }

        for (int i = 0; i < parameters.length; i++) {
            String s = "";
            switch (parameters[i]) {

            case "e/":
                if (runBefore[0]) {
                    break;
                }
                for (int j = 0; j < size - 1; j++) {
                    s += lastShownList.get(j).getEmail() + ",\n";
                }
                s += lastShownList.get(size - 1).getEmail() + "\n";
                resultString += String.format(MESSAGE_GET_PARAMETER_SUCCESS, "EMAIL", s) + "\n";
                runBefore[0] = true;
                break;

            case "p/":
                if (runBefore[1]) {
                    break;
                }
                for (int j = 0; j < size - 1; j++) {
                    s += lastShownList.get(j).getPhone() + ",\n";
                }
                s += lastShownList.get(size - 1).getPhone() + "\n";
                resultString += String.format(MESSAGE_GET_PARAMETER_SUCCESS, "PHONE NUMBER", s) + "\n";
                runBefore[1] = true;
                break;

            case "n/":
                if (runBefore[2]) {
                    break;
                }
                for (int j = 0; j < size - 1; j++) {
                    s += lastShownList.get(j).getName() + ",\n";
                }
                s += lastShownList.get(size - 1).getName() + "\n";
                resultString += String.format(MESSAGE_GET_PARAMETER_SUCCESS, "NAME", s) + "\n";
                runBefore[2] = true;
                break;

            case "a/":
                if (runBefore[3]) {
                    break;
                }
                for (int j = 0; j < size - 1; j++) {
                    s += lastShownList.get(j).getAddress() + ",\n";
                }
                s += lastShownList.get(size - 1).getAddress() + "\n";
                resultString += String.format(MESSAGE_GET_PARAMETER_SUCCESS, "ADDRESS", s) + "\n";
                runBefore[3] = true;
                break;

            default:
                throw new CommandException(Messages.MESSAGE_INVALID_PARAMETER);
            }
        }

        return new CommandResult(resultString);
    }

}
