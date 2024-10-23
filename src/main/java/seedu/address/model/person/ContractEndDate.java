package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a Person's Contract End Date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public abstract class ContractEndDate implements Comparable<ContractEndDate> {

    public static final String MESSAGE_CONSTRAINTS =
            "Contract End Date should only contain numeric characters and dashes in the format 'YYYY-MM-DD', and it"
                    + " should not be blank";
    private static final ContractEndDate EMPTY_CONTRACT_END_DATE = new EmptyContractEndDate();

    public static ContractEndDate empty() {
        return EMPTY_CONTRACT_END_DATE;
    }

    public static ContractEndDate of(String date) {
        return new FilledContractEndDate(date);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static LocalDate convertStringToDate(String date) {
        return LocalDate.parse(date);
    }

    /**
     * Gets the original value of contract end date;
     */
    public String getValue() {
        return "";
    }


    static class EmptyContractEndDate extends ContractEndDate {

        @Override
        public boolean equals(Object t) {
            return this == t;
        }

        @Override
        public String toString() {
            return "";
        }

        @Override
        public int compareTo(ContractEndDate t) {
            if (t instanceof EmptyContractEndDate) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    static class FilledContractEndDate extends ContractEndDate {
        public final LocalDate contractEndDate;
        private final String value;

        /**
         * Constructs a {@code ContractEndDate}.
         *
         * @param date A valid date.
         */
        public FilledContractEndDate(String date) {
            requireNonNull(date);
            checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
            this.contractEndDate = convertStringToDate(date);
            this.value = date;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return contractEndDate.toString();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof FilledContractEndDate)) {
                return false;
            }

            FilledContractEndDate otherContractEndDate = (FilledContractEndDate) other;
            return contractEndDate.equals(otherContractEndDate.contractEndDate);
        }

        @Override
        public int hashCode() {
            return contractEndDate.hashCode();
        }

        @Override
        public int compareTo(ContractEndDate t) {
            if (t instanceof EmptyContractEndDate) {
                return -1;
            } else {
                int comparisonResult = contractEndDate.compareTo(((FilledContractEndDate) t).contractEndDate);
                return Integer.signum(comparisonResult);
            }
        }
    }
}
