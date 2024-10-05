package seedu.address.model.person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;

public class DuplicatePhoneTagger {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    private final HashMap<Phone, Integer> phoneMap = new HashMap<>();

    public void tagPersonsWithDuplicatePhone(List<Person> persons, Person editedPerson) {
        tagDuplicates(persons, editedPerson);
    }

    /**
     *
     */
    private void tagDuplicates(List<Person> persons, Person editedPerson) {
        boolean duplicatePhoneExists = false;
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            if (person.equals(editedPerson)) {
                continue;
            }
            Phone phone = person.getPhone();
            if (!phoneMap.containsKey(phone)) {
                phoneMap.put(phone, i);
                continue;
            }
            duplicatePhoneExists = true;
        }
        if (duplicatePhoneExists) {

        }
    }
}
