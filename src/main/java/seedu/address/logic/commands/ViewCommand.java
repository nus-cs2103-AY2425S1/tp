package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Telegram;
import seedu.address.model.tag.Tag;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * View all information of specified contact
 */
public class ViewCommand extends Command {
    private final String tele;

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "Contact Info:\n";

    public static final String MESSAGE_NO_SUCH_TELEGRAM = "There is no one in your address book with the telegram handle: @";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds one contact whose telegram handle "
            + "matches the specified telegram handle (case-insensitive) and displays a new page which "
            + "contains all the contact's information.\n"
            + "Parameters: TELEGRAM_HANDLE \n"
            + "Example: " + COMMAND_WORD + " t/TELEGRAM_HANDLE";

    public ViewCommand(String tele) {
        this.tele = tele.toLowerCase();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> p = lastShownList.stream().filter(person ->
                person.getTelegram().value.toLowerCase().equals(this.tele)).toList();
        if (p.isEmpty()) {
            throw new CommandException(MESSAGE_NO_SUCH_TELEGRAM + this.tele);
        }
        Person person = p.get(0);
        return new CommandResult(MESSAGE_SUCCESS + generateContactInformation(person));
    }

    /**
     * Formulates a message that displays all information of the specified contact
     * @param p Person to which all their contact information is to be displayed
     * @return String message which contains all the information of the specified contact
     */
    public String generateContactInformation(Person p) {
        Field[] fields = Person.class.getDeclaredFields();
        StringBuilder contactInfo = new StringBuilder("");
        Arrays.stream(fields).forEach(field -> contactInfo.append(field.getName().toUpperCase()
                        + ": " + p.getString(field.getType()) + "\n"));
        return contactInfo.toString();
    }
}
