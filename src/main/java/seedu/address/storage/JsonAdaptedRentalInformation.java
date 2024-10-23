package seedu.address.storage;

import java.util.AbstractCollection;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.RentalUtil;
import seedu.address.model.rentalinformation.Address;
import seedu.address.model.rentalinformation.CustomerList;
import seedu.address.model.rentalinformation.Deposit;
import seedu.address.model.rentalinformation.MonthlyRent;
import seedu.address.model.rentalinformation.RentDueDate;
import seedu.address.model.rentalinformation.RentalDate;
import seedu.address.model.rentalinformation.RentalInformation;

/**
 * Jackson-friendly version of {@link RentalInformation}.
 */
class JsonAdaptedRentalInformation {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Rental Information's %s field is missing!";
    private final String address;
    private final String rentalStartDate;
    private final String rentalEndDate;
    private final String rentDueDate;
    private final String monthlyRent;
    private final String deposit;
    private final String customerList;

    /**
     * Constructs a {@code JsonAdaptedRentalInformation} with the given {@code address}, {@code rentalStartDate},
     * {@code rentalEndDate}, {@code rentDueDate}, {@code monthlyRent}, {@code deposit}, {@code customerList}.
     */
    @JsonCreator
    public JsonAdaptedRentalInformation(@JsonProperty("address") String address,
                                        @JsonProperty("rentalStartDate") String rentalStartDate,
                                        @JsonProperty("rentalEndDate") String rentalEndDate,
                                        @JsonProperty("rentDueDate") String rentDueDate,
                                        @JsonProperty("monthlyRent") String monthlyRent,
                                        @JsonProperty("deposit") String deposit,
                                        @JsonProperty("customerList") String customerList) {
        this.address = address;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
        this.rentDueDate = rentDueDate;
        this.monthlyRent = monthlyRent;
        this.deposit = deposit;
        this.customerList = customerList;
    }

    /**
     * Converts a given {@code RentalInformation} into this class for Jackson use.
     */
    public JsonAdaptedRentalInformation(RentalInformation source) {
        this.address = source.getAddress().value;
        this.rentalStartDate = Optional.ofNullable(source.getRentalStartDate().rentalDate)
                .map(rsd -> RentalUtil.convertLocalDateToStringWithFormat(rsd, "dd/MM/yyyy"))
                .orElse(null);
        this.rentalEndDate = Optional.ofNullable(source.getRentalEndDate().rentalDate)
                .map(red -> RentalUtil.convertLocalDateToStringWithFormat(red, "dd/MM/yyyy"))
                .orElse(null);
        this.rentDueDate = source.getRentDueDate().rentDueDate;
        this.monthlyRent = Optional.ofNullable(source.getMonthlyRent().monthlyRent)
                .map(mr -> String.format("%.2f", mr))
                .orElse(null);
        this.deposit = Optional.ofNullable(source.getDeposit().deposit)
                .map(d -> String.format("%.2f", d))
                .orElse(null);
        this.customerList = Optional.ofNullable(source.getCustomerList().customerList)
                .map(AbstractCollection::toString)
                .orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted RentalInformation object into the model's {@code RentalInformation}
     * object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted RentalInformation.
     */
    public RentalInformation toModelType() throws IllegalValueException {
        final Address modelAddress = modelAddressFunction();
        final RentalDate modelRentalStartDate = modelRentalStartDateFunction();
        final RentalDate modelRentalEndDate = modelRentalEndDateFunction();
        final RentDueDate modelRentDueDate = modelRentDueDateFunction();
        final MonthlyRent modelMonthlyRent = modelMonthlyRentFunction();
        final Deposit modelDeposit = modelDepositFunction();
        final CustomerList modelCustomerList = modelCustomerListFunction();

        return new RentalInformation(modelAddress, modelRentalStartDate, modelRentalEndDate, modelRentDueDate,
                modelMonthlyRent, modelDeposit, modelCustomerList);
    }

    private Address modelAddressFunction() throws IllegalValueException {
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }

        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }

        return new Address(address);
    }

    private RentalDate modelRentalStartDateFunction() throws IllegalValueException {
        if (rentalStartDate == null) {
            return new RentalDate();
        }

        if (!RentalDate.isValidRentalDate(rentalStartDate)) {
            throw new IllegalValueException(RentalDate.MESSAGE_CONSTRAINTS);
        }

        return new RentalDate(rentalStartDate);
    }

    private RentalDate modelRentalEndDateFunction() throws IllegalValueException {
        if (rentalEndDate == null) {
            return new RentalDate();
        }

        if (!RentalDate.isValidRentalDate(rentalEndDate)) {
            throw new IllegalValueException(RentalDate.MESSAGE_CONSTRAINTS);
        }

        return new RentalDate(rentalEndDate);
    }

    private RentDueDate modelRentDueDateFunction() throws IllegalValueException {
        if (rentDueDate == null) {
            return new RentDueDate();
        }

        if (!RentDueDate.isValidDueDate(rentDueDate)) {
            throw new IllegalValueException(RentDueDate.MESSAGE_CONSTRAINTS);
        }

        return new RentDueDate(rentDueDate);
    }

    private MonthlyRent modelMonthlyRentFunction() throws IllegalValueException {
        if (monthlyRent == null) {
            return new MonthlyRent();
        }

        if (!MonthlyRent.isValidMonthlyRent(monthlyRent)) {
            throw new IllegalValueException(MonthlyRent.MESSAGE_CONSTRAINTS);
        }

        return new MonthlyRent(monthlyRent);
    }

    private Deposit modelDepositFunction() throws IllegalValueException {
        if (deposit == null) {
            return new Deposit();
        }

        if (!Deposit.isValidDeposit(deposit)) {
            throw new IllegalValueException(Deposit.MESSAGE_CONSTRAINTS);
        }

        return new Deposit(deposit);
    }

    private CustomerList modelCustomerListFunction() throws IllegalValueException {
        if (customerList == null) {
            return new CustomerList();
        }

        if (!CustomerList.isValidCustomerList(customerList)) {
            throw new IllegalValueException(CustomerList.MESSAGE_CONSTRAINTS);
        }

        return new CustomerList(customerList);
    }
}
