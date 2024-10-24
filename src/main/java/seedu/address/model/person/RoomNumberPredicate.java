package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;



/**
 * A predicate class that tests whether a Person's room number matches a given room number.
 */
public class RoomNumberPredicate implements Predicate<Person> {
    private final RoomNumber roomNumber;



    /**
     * Constructs a new RoomNumberPredicate with a specified room number.
     *
     * @param roomNumber The room number to compare against Person objects.
     */
    public RoomNumberPredicate(String roomNumber) {
        this.roomNumber = new RoomNumber(roomNumber);
    }


    /**
     * Evaluates this predicate on the given Person instance.
     * Tests if the Person's room number matches the room number provided during instantiation.
     *
     * @param person The Person to be tested against this predicate.
     * @return true if the Person's room number matches the predicate's room number, otherwise false.
     */
    @Override
    public boolean test(Person person) {
        // This checks if the person's room number is present and matches the one in the predicate.
        return person.getRoomNumber()
                .map(roomNum -> roomNum.equals(this.roomNumber))
                .orElse(false);
    }



    /**
     * Compares this predicate with another object for equality.
     * Two RoomNumberPredicate objects are considered equal if their room numbers are equal.
     *
     * @param other The object to compare with this predicate.
     * @return true if the other object is an instance of RoomNumberPredicate and their room numbers are equal.
     */
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


    /**
     * Returns a hash code value for the room number.
     * Ensures consistent behavior with equals when storing in hash-based collections.
     *
     * @return the hash code value of the room number.
     */
    @Override
    public int hashCode() {
        return roomNumber.hashCode();
    }

    /**
     * Provides a string representation of this RoomNumberPredicate.
     * Includes the room number in the string output to aid in debugging and logging.
     *
     * @return a string representation of this predicate, including the room number.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("roomNumber", roomNumber).toString();
    }
}
