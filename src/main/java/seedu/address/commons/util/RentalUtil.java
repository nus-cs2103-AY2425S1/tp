package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class RentalUtil {
    public static LocalDate convertStringToLocalDate(String str) {
        requireNonNull(str);

        int year = Integer.parseInt(str.substring(0, 4));
        int month = Integer.parseInt(str.substring(5, 7));
        int day = Integer.parseInt(str.substring(8, 10));

        return LocalDate.of(year, month, day);
    }

    public static ArrayList<String> convertStringToCustomerArrayList(String str) {
        requireNonNull(str);

        String[] customerList = str.split(";");

        return new ArrayList<>(Arrays.asList(customerList));
    }
}
