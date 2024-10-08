package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Helper functions for handling rental information related operations.
 */
public class RentalUtil {
    /**
     * Converts a string in the format "dd/mm/yyyy" to a {@link LocalDate} object.
     *
     * @param str The string representing the date in the format "dd/mm/yyyy".
     * @return A {@link LocalDate} representing the date specified by the input string.
     */
    public static LocalDate convertStringToLocalDate(String str) {
        requireNonNull(str);

        int year = Integer.parseInt(str.substring(0, 4));
        int month = Integer.parseInt(str.substring(5, 7));
        int day = Integer.parseInt(str.substring(8, 10));

        return LocalDate.of(year, month, day);
    }

    /**
     * Converts a string (delimits by ";") to an {@link ArrayList}.
     *
     * @param str The string representing the customer list.
     * @return An {@link ArrayList} consisting all the customers' names specified by the input string.
     */
    public static ArrayList<String> convertStringToCustomerArrayList(String str) {
        requireNonNull(str);

        String[] customerList = str.split(";");

        return new ArrayList<>(Arrays.asList(customerList));
    }
}
