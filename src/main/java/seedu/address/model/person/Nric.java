package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Nric {

    public static final String MESSAGE_CONSTRAINTS = "NRIC should adhere to the following format and constraints:\n"
            + "1. The NRIC must be 9 characters long.\n"
            + "2. The first character must be one of the following letters: 'S', 'T', 'F', or 'G'.\n"
            + "   - 'S' and 'T' are for Singapore Citizens and Permanent Residents.\n"
            + "   - 'F' and 'G' are for Foreigners.\n"
            + "3. The next 7 characters must be digits.\n"
            + "4. The last character must be an uppercase letter, which serves as a checksum to validate the NRIC.\n"
            + "5. The checksum is computed based on a specific algorithm using the 7 digits of the NRIC.";

    public static final String VALIDATION_REGEX = "\\d{7}";

    public String nric;

    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_CONSTRAINTS);
    }

    public static boolean isValidNric(String nric) {
        // Check if the input is 9 characters long
        if (nric == null || nric.length() != 9) {
            return false;
        }

        // Check if the first character is 'S', 'T', 'F', or 'G'
        char firstChar = nric.charAt(0);
        if (firstChar != 'S' && firstChar != 'T' && firstChar != 'F' && firstChar != 'G') {
            return false;
        }

        // Check if the next 7 characters are digits
        String digits = nric.substring(1, 8);
        if (!digits.matches(VALIDATION_REGEX)) {
            return false;
        }

        // Get the checksum letter (the last character)
        char checksumLetter = nric.charAt(8);

        // Declare the weights array as per NRIC checksum calculation
        int[] weights = {2, 7, 6, 5, 4, 3, 2};

        // Calculate the sum of the weighted digits
        int sum = 0;
        for (int i = 0; i < 7; i++) {
            sum += (digits.charAt(i) - '0') * weights[i];
        }

        // Adjust the sum based on the type of NRIC (S/F or T/G)
        if (firstChar == 'T' || firstChar == 'G') {
            sum += 4;  // T/G NRICs have an additional offset of 4
        }

        // Calculate the remainder
        int remainder = sum % 11;

        // Define the checksum letters for S/F and T/G NRICs
        char[] checksumLettersForST = {'J', 'Z', 'I', 'H', 'G', 'F', 'E', 'D', 'C', 'B', 'A'};
        char[] checksumLettersForFG = {'X', 'W', 'U', 'T', 'R', 'Q', 'P', 'N', 'M', 'L', 'K'};

        // Get the correct checksum letter based on the NRIC type
        char correctChecksum;
        if (firstChar == 'S' || firstChar == 'T') {
            correctChecksum = checksumLettersForST[remainder];
        } else {
            correctChecksum = checksumLettersForFG[remainder];
        }

        // Compare the calculated checksum with the provided one
        return checksumLetter == correctChecksum;
    }
}
