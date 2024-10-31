package seedu.address.storage;

import com.opencsv.bean.CsvBindByName;

import seedu.address.model.person.Person;

/**
 * Comma-seperated value-friendly version of {@link Person}.
 */
public class CsvAdaptedPerson {

    @CsvBindByName(column = "Name")
    private String name;

    @CsvBindByName(column = "Phone Number")
    private String phoneNumber;

    @CsvBindByName(column = "Address")
    private String address;

    @CsvBindByName(column = "Email")
    private String email;

    @CsvBindByName(column = "Remark")
    private String remark;

    @CsvBindByName(column = "Tags")
    private String tags;

    @CsvBindByName(column = "Listings")
    private String listings;

    /**
     * Constructs a {@code CsvAdaptedPerson} with the given person details.
     */
    public CsvAdaptedPerson(String name, String phoneNumber, String address, String email, String remark, String tags,
                            String listings) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.remark = remark;
        this.tags = tags;
        this.listings = listings;
    }
}
