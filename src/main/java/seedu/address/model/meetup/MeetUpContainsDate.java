package seedu.address.model.meetup;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.function.Predicate;

/**
 * Tests that a {@code Meetup}'s {@code Date} range contains the given date.
 */
public class MeetUpContainsDate implements Predicate<MeetUp> {
    private final Date date;

    /**
     * Date must be present and not null.
     */
    public MeetUpContainsDate(Date date) {
        requireNonNull(date);
        this.date = date;
    }

    @Override
    public boolean test(MeetUp meetUp) {
        return false;
        // TODO
        //return !meetUp.getFrom().before(this.date) && !meetUp.getTo().after(this.date);
    }
}
