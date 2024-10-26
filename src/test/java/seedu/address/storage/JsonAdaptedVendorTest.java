package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalVendors.BORIS;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class JsonAdaptedVendorTest {

    private static final String INVALID_TASK = "buy flowers";
    private static final String VALID_NAME = BORIS.getName().toString();
    private static final String VALID_PHONE = BORIS.getPhone().toString();
    private static final String VALID_EMAIL = BORIS.getEmail().toString();
    private static final String VALID_ADDRESS = BORIS.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BORIS.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedWedding> VALID_WEDDINGS = BORIS.getWeddings().stream()
            .map(JsonAdaptedWedding::new)
            .collect(Collectors.toList());



    @Test
    public void toModelType_validVendorDetails_returnsVendor() throws Exception {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(BORIS);
        assertEquals(BORIS, vendor.toModelType());
    }

    // to add, test cases for invalid Tasks once vendors are integrated with tasks

}
