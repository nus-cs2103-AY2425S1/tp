package seedu.address.model.student;

import seedu.address.model.tut.TutDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PresentDates {
    private final ArrayList<TutDate> dates;

    public PresentDates(ArrayList<TutDate> dates) {
        this.dates = dates;
    }

    public void add(TutDate date) {
        dates.add(date);
    }

    public List<TutDate> getList() {
        return Collections.unmodifiableList(dates);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for (TutDate date : dates) {
            s.append(date.toString());
        }
        s.append(']');
        return s.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof PresentDates)) {
            return false;
        }
        PresentDates otherDates = (PresentDates) other;
        return dates.equals(otherDates.dates);
    }
}
