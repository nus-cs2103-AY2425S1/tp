package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
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
            + "Example: " + COMMAND_WORD + " email phone";

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
        String resultString = "";

        for (int i = 0; i < parameters.length; i++) {
            String s = "";
            switch (parameters[i].toLowerCase()) {

            case "email":
                resultString += getEmailMessage(lastShownList);
                break;

            case "phone":
                resultString += getPhoneNumberMessage(lastShownList);
                break;

            case "name":
                resultString += getNameMessage(lastShownList);
                break;

            case "address":
                resultString += getAddressMessage(lastShownList);
                break;

            default:
                throw new CommandException(Messages.MESSAGE_INVALID_PARAMETER);
            }
        }

        return new CommandResult(resultString);
    }

    /**
     * Returns the emails of all the people in current list.
     *
     * @param currList current list.
     * @return string message of all the emails.
     */
    public static String getEmailMessage(List<Person> currList) {
        String message = currList.stream()
                .map(person -> person.getEmail().toString())
                .reduce((email1, email2) -> email1 + ",\n" + email2)
                .orElse("");
        return String.format(MESSAGE_GET_PARAMETER_SUCCESS, "EMAIL", message) + "\n\n";
    }

    /**
     * Returns the phone numbers of all the people in current list.
     *
     * @param currList current list.
     * @return string message of all the phone numbers.
     */
    public static String getPhoneNumberMessage(List<Person> currList) {
        String message = currList.stream()
                .map(person -> person.getPhone().toString())
                .reduce((phone1, phone2) -> phone1 + ",\n" + phone2)
                .orElse("");
        return String.format(MESSAGE_GET_PARAMETER_SUCCESS, "PHONE NUMBER", message) + "\n\n";
    }

    /**
     * Returns the name of all the people in current list.
     *
     * @param currList current list.
     * @return string message of all the names.
     */
    public static String getNameMessage(List<Person> currList) {
        String message = currList.stream()
                .map(person -> person.getName().toString())
                .reduce((name1, name2) -> name1 + ",\n" + name2)
                .orElse("");
        return String.format(MESSAGE_GET_PARAMETER_SUCCESS, "NAME", message) + "\n\n";
    }

    /**
     * Returns the addresses of all the people in current list.
     *
     * @param currList current list.
     * @return string message of all the addresses.
     */
    public static String getAddressMessage(List<Person> currList) {
        String message = currList.stream()
                .map(person -> person.getAddress().toString())
                .reduce((address1, address2) -> address1 + ",\n" + address2)
                .orElse("");
        return String.format(MESSAGE_GET_PARAMETER_SUCCESS, "ADDRESS", message) + "\n\n";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GetCommand)) {
            return false;
        }

        GetCommand otherGetCommand = (GetCommand) other;
        return Arrays.equals(parameters, otherGetCommand.parameters);
    }

}
