package seedu.address.ui;

import seedu.address.model.person.Person;

public interface SelectionListener {
    void onPersonSelected(Person person, int index);
}
