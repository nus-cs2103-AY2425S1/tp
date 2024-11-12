package seedu.internbuddy.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.internbuddy.commons.core.LogsCenter;
import seedu.internbuddy.commons.exceptions.IllegalValueException;
import seedu.internbuddy.model.AddressBook;
import seedu.internbuddy.model.ReadOnlyAddressBook;
import seedu.internbuddy.model.company.Company;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_COMPANY = "Companies list contains duplicate company(s).";
    private static final Logger logger = LogsCenter.getLogger(JsonSerializableAddressBook.class);

    private final List<JsonAdaptedCompany> companies = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given companies.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("companies") List<JsonAdaptedCompany> companies) {
        this.companies.addAll(companies);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        companies.addAll(source.getCompanyList().stream().map(JsonAdaptedCompany::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        logger.info("Attempting to convert " + JsonSerializableAddressBook.class + " to "
                + AddressBook.class + "...");
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedCompany jsonAdaptedCompany : companies) {
            Company company = jsonAdaptedCompany.toModelType();
            if (addressBook.hasCompany(company)) {
                logger.info("Duplicate companies detected.");
                throw new IllegalValueException(MESSAGE_DUPLICATE_COMPANY);
            }
            addressBook.addCompany(company);
        }
        return addressBook;
    }

}
