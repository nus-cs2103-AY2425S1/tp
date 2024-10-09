package seedu.address.model;

import seedu.address.model.person.UniquePersonList;

public class Group extends UniquePersonList {
    private String name;

    public Group(String name) {
        super();
        this.name = name;
    }

    public void rename(String newName) {
        name = newName;
    }
}