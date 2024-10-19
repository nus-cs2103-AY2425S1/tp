package seedu.address.model;

import seedu.address.model.person.Person;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Participation {
    private Person student;
    private Tutorial tutorial;

    public Participation(Person student, Tutorial tutorial) {
        requireAllNonNull(student, tutorial);
        this.student = student;
        this.tutorial = tutorial;
    }

    public Person getStudent() {
        return student;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }
}
