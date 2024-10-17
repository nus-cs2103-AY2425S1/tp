package seedu.address.model.person;

import seedu.address.commons.util.ToStringBuilder;

import java.util.function.Predicate;

public class RoomNumberPredicate implements Predicate<Person> {
    private final String roomNumber;

    public RoomNumberPredicate(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public boolean test(Person person) {
        return person.getRoomNumber().toString().equals(roomNumber);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof RoomNumberPredicate)) {
            return false;
        }

        RoomNumberPredicate otherRoomNumberPredicate = (RoomNumberPredicate) other;
        return roomNumber.equals(otherRoomNumberPredicate.roomNumber);
    }

    @Override
    public int hashCode() {
        return roomNumber.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("roomNumber", roomNumber).toString();
    }
}
