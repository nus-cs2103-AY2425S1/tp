package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void getTagSet_success() {
        Tag[] tags = {new Tag("Admin"), new Tag("Vice President")};

        assertTrue(List.of(tags).containsAll(SampleDataUtil.getTagSet("Admin", "Vice President")));
        assertTrue(List.of(tags).containsAll(SampleDataUtil.getTagSet("Vice President", "Admin")));
        assertTrue(List.of(tags).containsAll(SampleDataUtil.getTagSet("Vice President", "Admin", "Admin")));
    }

}
