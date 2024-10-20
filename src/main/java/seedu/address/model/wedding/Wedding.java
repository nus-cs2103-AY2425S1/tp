package seedu.address.model.wedding;

import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a wedding event and tracks the date and people related to the event, immutable
 */
public class Wedding {
    private final String name;
    private final String date;
    private final List<PersonId> assignees; //Stores a list of UserIDs

    public Wedding(String name, String date) {
        this.name = name;
        this.date = date;
        this.assignees = new ArrayList<>();
    }

    public Wedding(String name, String date, List<PersonId> assignees) {
        this.name = name;
        this.date = date;
        this.assignees = assignees;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public List<PersonId> getAssignees() {
        return this.assignees;
    }

    /**
     * Checks if this Wedding is the same as another Wedding.
     * Weddings are the same if they have the same name.
     * @param other Wedding to compare to
     */
    public boolean isSameWedding(Wedding other) {
        if (this == other) {
            return true;
        }

        return this.name.equals(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Wedding)) {
            return false;
        }

        Wedding other = (Wedding) o;
        return this.name.equals(other.name)
                && this.date.equals(other.date)
                && this.assignees.equals(other.assignees);
    }
}
