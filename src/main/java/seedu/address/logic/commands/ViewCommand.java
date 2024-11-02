package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * View all information of specified contact
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "Viewing contact now...";

    public static final String MESSAGE_NO_SUCH_TELEGRAM =
            "There is no one in your address book with the telegram handle: @";

    public static final String MESSAGE_TOO_SHORT_TELEGRAM =
            "The telegram handle entered is too short (min 5 characters).";

    public static final String MESSAGE_ILLEGAL_CHARACTERS_IN_TELEGRAM =
            "The telegram handle entered contains illegal characters.\n"
            + "Telegram handles should only contain letters, numbers and underscores.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds one contact whose telegram handle "
            + "matches the specified telegram handle (case-insensitive)\n"
            + "and displays contains all the contact's information.\n"
            + "Parameters: t/TELEGRAM_HANDLE \n"
            + "Example: " + COMMAND_WORD + " t/TELEGRAM_HANDLE";

    private final String tele;

    public ViewCommand(String tele) {
        this.tele = tele.toLowerCase();
    }

    /**
     * Checks if char array given contains any illegal characters (not letters, numbers or underscore)
     * @param arr char array
     * @return boolean value
     */
    public boolean containsIllegalCharacters(char[] arr) {
        char[] legalCharacters = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '_'};
        int illegalCount = 0;
        for (Character c : arr) {
            boolean isLegal = false;
            for (Character lc : legalCharacters) {
                if (!isLegal) {
                    isLegal = c.equals(lc);
                }
            }
            illegalCount = !isLegal ? illegalCount + 1 : illegalCount;
        }
        return illegalCount > 0;
    }

    /**
     * Checks if telegram handle for this ViewCommand instance is at least 5
     * characters long and that it contains no illegal characters. Otherwise
     * throws relevant exception
     * @throws CommandException
     */
    public void isValidHandle() throws CommandException {
        char[] arr = this.tele.toCharArray();
        if (arr.length < 5) {
            throw new CommandException(MESSAGE_TOO_SHORT_TELEGRAM);
        } else if (containsIllegalCharacters(arr)) {
            throw new CommandException(MESSAGE_ILLEGAL_CHARACTERS_IN_TELEGRAM);
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        isValidHandle();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> p = lastShownList.stream().filter(person ->
                person.getTelegram().value.toLowerCase().equals(this.tele)).toList();
        if (p.isEmpty()) {
            throw new CommandException(MESSAGE_NO_SUCH_TELEGRAM + this.tele);
        }
        Person person = p.get(0);
        return new CommandResult(MESSAGE_SUCCESS, true, person);
    }

}
