package seedu.address.logic.commands;


import javafx.collections.ObservableList;
import seedu.address.logic.Logic;
import seedu.address.model.person.Person;

/**
 * Contains utility methods use to retrieve client information
 */
public class ClientUtil {
    /**
     * Given a view command, returns the specified client Person object
     * @param viewCommand input view command
     * @param logic logic object
     * @return person object of the specified client
     */
    public static Person findViewPerson(String viewCommand, Logic logic) {
        String name = viewCommand.replace("view", "");
        String trimmedName = name.trim();
        ObservableList<Person> clientList = logic.getFilteredPersonList();
        Person matchingPerson = clientList.stream()
                .filter(person -> person.getName().toString().equals(trimmedName))
                .findFirst().orElse(null);
        return matchingPerson;
    }
}
