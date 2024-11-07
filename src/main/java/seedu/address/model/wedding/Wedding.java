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
    private final WeddingName weddingName;
    private Person partner1;
    private Person partner2;
    private ArrayList<Person> guestList = new ArrayList<>();
    private Address address;
    private String date;

    /**
     * Constructs a {@code Wedding} with the specified {@code weddingName}
     * @param weddingName A valid {@code WeddingName}
     */
    public Wedding(WeddingName weddingName) {
        requireNonNull(weddingName);
        this.weddingName = weddingName;
    }

    /**
     * Constructs a {@code Wedding} with the specified {@code weddingName}
     * @param weddingName A valid {@code WeddingName}
     */
    public Wedding(WeddingName weddingName, Person partner1, Person partner2,
                   ArrayList<Person> guestList, Address address, String date) {
        requireNonNull(weddingName);
        this.weddingName = weddingName;
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
        return checkName.matches(WeddingName.VALIDATION_REGEX);
    }

    /**
     * Returns wedding name associated with wedding
     * @return A {@code WeddingName} object representing the name of the wedding
     */
    public WeddingName getWeddingName() {
        return this.weddingName;
    }

    /**
     * Returns partner1 associated with wedding
     * @return A {@code Person} object of the partner1 of the wedding
     */
    public Person getPartner1() {
        return this.partner1;
    }

    /**
     * Sets partner1 in this wedding
     * @param partner1 A {@code Person} object to be set as {@code partner1}
     */
    public void setPartner1(Person partner1) {
        this.partner1 = partner1;
    }

    /**
     * Returns partner2 associated with wedding
     * @return A {@code Person} object of the partner2 of the wedding
     */
    public Person getPartner2() {
        return this.partner2;
    }

    /**
     * Sets partner2 in this wedding
     * @param partner2 A {@code Person} object to be set as {@code partner2}
     */
    public void setPartner2(Person partner2) {
        this.partner2 = partner2;
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
     * Adds {@code Person} to guest list in this wedding
     *
     * @param person An {@code Person} object of the guest list of the wedding
     */
    public void addToGuestList(Person person) {
        this.guestList.add(person);
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
        return (partner1 == null ? 0 : 1)
                + (partner2 == null ? 0 : 1)
                + guestList.size();
    }

    /**
     * Returns true if the wedding can be deleted,
     * wedding can be deleted if {@code peopleCount} is 0.
     */
    public boolean canBeDeleted() {
        return (getNumPersonsForWedding() == 0);
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

    @Override
    public Wedding clone() {
        try {
            return (Wedding) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Wedding(weddingName, partner1, partner2, guestList, address, date);
        }
    }
}
