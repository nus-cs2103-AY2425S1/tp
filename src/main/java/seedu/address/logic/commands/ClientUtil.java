package seedu.address.logic.commands;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.logic.Logic;
import seedu.address.model.person.Person;

public class ClientUtil {
    public static Person findViewPerson(String viewCommand, Logic logic) {
        String name = viewCommand.replace("view","");
        String trimmedName = name.trim();
        ObservableList<Person> clientList = logic.getFilteredPersonList();
        Person matchingPerson = clientList.stream()
                .filter(person -> person.getName().toString().equals(trimmedName))
                .findFirst().orElse(null);
        return matchingPerson;
    }
}
