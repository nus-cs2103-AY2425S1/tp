package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CUSTOMER_LIST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEPOSIT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MONTHLY_RENT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RENTAL_END_DATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RENTAL_START_DATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RENT_DUE_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CUSTOMER_LIST_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPOSIT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTHLY_RENT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENTAL_END_DATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENTAL_START_DATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENT_DUE_DATE_ONE;
import static seedu.address.storage.JsonAdaptedRentalInformation.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRentalInformation.RENTAL_ONE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.rentalinformation.Address;
import seedu.address.model.rentalinformation.CustomerList;
import seedu.address.model.rentalinformation.Deposit;
import seedu.address.model.rentalinformation.MonthlyRent;
import seedu.address.model.rentalinformation.RentDueDate;
import seedu.address.model.rentalinformation.RentalDate;

public class JsonAdaptedRentalInformationTest {
    @Test
    public void toModelType_validRentalInformationDetails_returnsPerson() throws Exception {
        JsonAdaptedRentalInformation rentalInformation = new JsonAdaptedRentalInformation(RENTAL_ONE);
        assertEquals(RENTAL_ONE, rentalInformation.toModelType());
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedRentalInformation rentalInformation =
                new JsonAdaptedRentalInformation(INVALID_ADDRESS, VALID_RENTAL_START_DATE_ONE,
                        VALID_RENTAL_END_DATE_ONE, VALID_RENT_DUE_DATE_ONE, VALID_MONTHLY_RENT_ONE, VALID_DEPOSIT_ONE,
                        VALID_CUSTOMER_LIST_ONE);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, rentalInformation::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedRentalInformation rentalInformation =
                new JsonAdaptedRentalInformation(null, VALID_RENTAL_START_DATE_ONE, VALID_RENTAL_END_DATE_ONE,
                        VALID_RENT_DUE_DATE_ONE, VALID_MONTHLY_RENT_ONE, VALID_DEPOSIT_ONE, VALID_CUSTOMER_LIST_ONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, rentalInformation::toModelType);
    }

    @Test
    public void toModelType_invalidRentalStartDate_throwsIllegalValueException() {
        JsonAdaptedRentalInformation rentalInformation =
                new JsonAdaptedRentalInformation(VALID_ADDRESS_ONE, INVALID_RENTAL_START_DATE,
                        VALID_RENTAL_END_DATE_ONE, VALID_RENT_DUE_DATE_ONE, VALID_MONTHLY_RENT_ONE, VALID_DEPOSIT_ONE,
                        VALID_CUSTOMER_LIST_ONE);
        String expectedMessage = RentalDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, rentalInformation::toModelType);
    }

    @Test
    public void toModelType_nullRentalStartDate_success() {
        JsonAdaptedRentalInformation rentalInformation =
                new JsonAdaptedRentalInformation(VALID_ADDRESS_ONE, null, VALID_RENTAL_END_DATE_ONE,
                        VALID_RENT_DUE_DATE_ONE, VALID_MONTHLY_RENT_ONE, VALID_DEPOSIT_ONE, VALID_CUSTOMER_LIST_ONE);
        assertDoesNotThrow(() -> {
            rentalInformation.toModelType();
        });
    }

    @Test
    public void toModelType_invalidRentalEndDate_throwsIllegalValueException() {
        JsonAdaptedRentalInformation rentalInformation =
                new JsonAdaptedRentalInformation(VALID_ADDRESS_ONE, VALID_RENTAL_START_DATE_ONE,
                        INVALID_RENTAL_END_DATE, VALID_RENT_DUE_DATE_ONE, VALID_MONTHLY_RENT_ONE, VALID_DEPOSIT_ONE,
                        VALID_CUSTOMER_LIST_ONE);
        String expectedMessage = RentalDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, rentalInformation::toModelType);
    }

    @Test
    public void toModelType_nullRentalEndDate_success() {
        JsonAdaptedRentalInformation rentalInformation =
                new JsonAdaptedRentalInformation(VALID_ADDRESS_ONE, VALID_RENTAL_START_DATE_ONE, null,
                        VALID_RENT_DUE_DATE_ONE, VALID_MONTHLY_RENT_ONE, VALID_DEPOSIT_ONE, VALID_CUSTOMER_LIST_ONE);
        assertDoesNotThrow(() -> {
            rentalInformation.toModelType();
        });
    }

    @Test
    public void toModelType_invalidRentDueDate_throwsIllegalValueException() {
        JsonAdaptedRentalInformation rentalInformation =
                new JsonAdaptedRentalInformation(VALID_ADDRESS_ONE, VALID_RENTAL_START_DATE_ONE,
                        VALID_RENTAL_END_DATE_ONE, INVALID_RENT_DUE_DATE, VALID_MONTHLY_RENT_ONE, VALID_DEPOSIT_ONE,
                        VALID_CUSTOMER_LIST_ONE);
        String expectedMessage = RentDueDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, rentalInformation::toModelType);
    }

    @Test
    public void toModelType_nullRentDueDate_success() {
        JsonAdaptedRentalInformation rentalInformation =
                new JsonAdaptedRentalInformation(VALID_ADDRESS_ONE, VALID_RENTAL_START_DATE_ONE,
                        VALID_RENTAL_END_DATE_ONE, null, VALID_MONTHLY_RENT_ONE, VALID_DEPOSIT_ONE,
                        VALID_CUSTOMER_LIST_ONE);
        assertDoesNotThrow(() -> {
            rentalInformation.toModelType();
        });
    }

    @Test
    public void toModelType_invalidMonthlyRent_throwsIllegalValueException() {
        JsonAdaptedRentalInformation rentalInformation =
                new JsonAdaptedRentalInformation(VALID_ADDRESS_ONE, VALID_RENTAL_START_DATE_ONE,
                        VALID_RENTAL_END_DATE_ONE, VALID_RENT_DUE_DATE_ONE, INVALID_MONTHLY_RENT, VALID_DEPOSIT_ONE,
                        VALID_CUSTOMER_LIST_ONE);
        String expectedMessage = MonthlyRent.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, rentalInformation::toModelType);
    }

    @Test
    public void toModelType_nullMonthlyRent_success() {
        JsonAdaptedRentalInformation rentalInformation =
                new JsonAdaptedRentalInformation(VALID_ADDRESS_ONE, VALID_RENTAL_START_DATE_ONE,
                        VALID_RENTAL_END_DATE_ONE, VALID_RENT_DUE_DATE_ONE, null, VALID_DEPOSIT_ONE,
                        VALID_CUSTOMER_LIST_ONE);
        assertDoesNotThrow(() -> {
            rentalInformation.toModelType();
        });
    }

    @Test
    public void toModelType_invalidDeposit_throwsIllegalValueException() {
        JsonAdaptedRentalInformation rentalInformation =
                new JsonAdaptedRentalInformation(VALID_ADDRESS_ONE, VALID_RENTAL_START_DATE_ONE,
                        VALID_RENTAL_END_DATE_ONE, VALID_RENT_DUE_DATE_ONE, VALID_MONTHLY_RENT_ONE, INVALID_DEPOSIT,
                        VALID_CUSTOMER_LIST_ONE);
        String expectedMessage = Deposit.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, rentalInformation::toModelType);
    }

    @Test
    public void toModelType_nullDeposit_success() {
        JsonAdaptedRentalInformation rentalInformation =
                new JsonAdaptedRentalInformation(VALID_ADDRESS_ONE, VALID_RENTAL_START_DATE_ONE,
                        VALID_RENTAL_END_DATE_ONE, VALID_RENT_DUE_DATE_ONE, VALID_MONTHLY_RENT_ONE, null,
                        VALID_CUSTOMER_LIST_ONE);
        assertDoesNotThrow(() -> {
            rentalInformation.toModelType();
        });
    }

    @Test
    public void toModelType_invalidCustomerList_throwsIllegalValueException() {
        JsonAdaptedRentalInformation rentalInformation =
                new JsonAdaptedRentalInformation(VALID_ADDRESS_ONE, VALID_RENTAL_START_DATE_ONE,
                        VALID_RENTAL_END_DATE_ONE, VALID_RENT_DUE_DATE_ONE, VALID_MONTHLY_RENT_ONE, VALID_DEPOSIT_ONE,
                        INVALID_CUSTOMER_LIST);
        String expectedMessage = CustomerList.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, rentalInformation::toModelType);
    }

    @Test
    public void toModelType_nullCustomerList_success() {
        JsonAdaptedRentalInformation rentalInformation =
                new JsonAdaptedRentalInformation(VALID_ADDRESS_ONE, VALID_RENTAL_START_DATE_ONE,
                        VALID_RENTAL_END_DATE_ONE, VALID_RENT_DUE_DATE_ONE, VALID_MONTHLY_RENT_ONE, VALID_DEPOSIT_ONE,
                        null);
        assertDoesNotThrow(() -> {
            rentalInformation.toModelType();
        });
    }
}
