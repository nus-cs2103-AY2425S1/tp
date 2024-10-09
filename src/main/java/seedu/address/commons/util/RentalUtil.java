package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import seedu.address.model.rentalinformation.CustomerList;

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

        int day = Integer.parseInt(str.substring(0, 2));
        int month = Integer.parseInt(str.substring(3, 5));
        int year = Integer.parseInt(str.substring(6, 10));

        return LocalDate.of(year, month, day);
    }

    /**
     * Converts a {@link LocalDate} to a formatted string.
     *
     * @param date The {@link LocalDate} to be converted.
     * @param format The pattern to be applied, following the rules of {@link DateTimeFormatter}.
     * @return a formatted string representation of the {@link LocalDate}.
     */
    public static String convertLocalDateToStringWithFormat(LocalDate date, String format) {
        requireNonNull(date);
        requireNonNull(format);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        return date.format(formatter);
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

    /**
     * Compares two customer lists to determine if they contain the same set of customers.
     *
     * @param firstList  the first {@code CustomerList} to compare.
     * @param secondList the second {@code CustomerList} to compare.
     * @return {@code true} if both customer lists contain the same set of customers,
     *         {@code false} otherwise.
     */
    public static boolean isCustomerListSame(CustomerList firstList, CustomerList secondList) {
        requireNonNull(firstList);
        requireNonNull(secondList);

        HashSet<String> firstHashSet = new HashSet<>(firstList.getCustomerList());
        HashSet<String> secondHashSet = new HashSet<>(secondList.getCustomerList());

        return firstHashSet.equals(secondHashSet);
    }
}
