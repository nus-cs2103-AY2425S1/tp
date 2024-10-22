package seedu.address.model.wedding;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.model.person.Address;
import seedu.address.model.person.Person;


/**
 * Represents a Wedding in the system.
 * Guarantees: immutable; name is valid as declared in {@link #isValidWeddingName(String)}
 */
public class Wedding {
    public static final String MESSAGE_CONSTRAINTS =
            "Wedding names should only contain alphanumeric characters, spaces or apostrophes, "
                    + "and they should not be blank. Wedding names are case sensitive.";

    /**
     * Validation regex checks that first character of the wedding name must not be a whitespace,
     * so that " " (a blank string) is not a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}'][\\p{Alnum} ']*";
    private final WeddingName weddingName;
    private int peopleCount;
    private Person partner1;
    private Person partner2;
    private ArrayList<Person> guestList;
    private Address address;
    private String date;

    /**
     * Constructs a {@code Wedding} with the specified {@code weddingName}
     * @param weddingName A valid {@code WeddingName}
     */
    public Wedding(WeddingName weddingName) {
        requireNonNull(weddingName);
        this.weddingName = weddingName;
        this.peopleCount = 0;
    }

    /**
     * Constructs a {@code Wedding} with the specified {@code weddingName}
     * @param weddingName A valid {@code WeddingName}
     */
    public Wedding(WeddingName weddingName, int peopleCount, Person partner1, Person partner2,
                   ArrayList<Person> guestList, Address address, String date) {
        requireNonNull(weddingName);
        this.weddingName = weddingName;
        this.peopleCount = peopleCount;
        this.partner1 = partner1;
        this.partner2 = partner2;
        this.guestList = guestList;
        this.address = address;
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid wedding name.
     */
    public static boolean isValidWeddingName(String checkName) {
        return checkName.matches(VALIDATION_REGEX);
    }

    /**
     * Returns wedding name associated with wedding
     * @return A {@code WeddingName} object representing the name of the wedding
     */
    public WeddingName getWeddingName() {
        return this.weddingName;
    }

    /**
     * Returns people count associated with wedding
     * @return peopleCount ({@code int}) of the wedding
     */
    public int getPeopleCount() {
        return this.peopleCount;
    }

    /**
     * Returns partner1 associated with wedding
     * @return A {@code Person} object of the partner1 of the wedding
     */
    public Person getPartner1() {
        return this.partner1;
    }

    /**
     * Returns partner2 associated with wedding
     * @return A {@code Person} object of the partner2 of the wedding
     */
    public Person getPartner2() {
        return this.partner2;
    }

    /**
     * Returns guest list associated with wedding
     *
     * @return An {@code ArrayList<Person>} object of the guest list of the wedding
     */
    public ArrayList<Person> getGuestList() {
        return this.guestList;
    }

    /**
     * Returns wedding address associated with wedding
     * @return A {@code Address} object of the address of the wedding
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Returns date associated with wedding
     * @return A {@code String} object of the date of the wedding
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Returns the number of people assigned to this wedding
     * @return An {@code int} with the number of people assigned to the wedding
     */
    public int getNumPersonsForWedding() {
        return this.peopleCount;
    }


    public void increasePeopleCount() {
        peopleCount++;
    }

    public void decreasePeopleCount() {
        peopleCount--;
    }

    /**
     * Returns true if the wedding can be deleted,
     * wedding can be deleted if {@code peopleCount} is 0.
     */
    public boolean canBeDeleted() {
        return (peopleCount == 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Wedding otherWedding)) {
            return true;
        }

        return weddingName.equals(otherWedding.weddingName);
    }

    /**
     * Returns true if another wedding has the same WeddingName as this wedding.
     * @param otherWedding A {@code Wedding} to compare with.
     */
    public boolean isSameWedding(Wedding otherWedding) {
        if (otherWedding == this) {
            return true;
        }

        // Wedding#equals(Object) covers the null case
        return otherWedding.getWeddingName().equals(getWeddingName());
    }

    @Override
    public int hashCode() {
        return weddingName.hashCode();
    }

    @Override
    public String toString() {
        return '[' + weddingName.toString() + ']';
    }
}
