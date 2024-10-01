package seedu.address.commons.util;

public class EnumUtil {
    public static <R extends Enum<R>> boolean inEnum(String test, Class<R> enumtype) {
        for (Enum<R> c : enumtype.getEnumConstants()) {
            if (c.name().equalsIgnoreCase(test)) {
                return true;
            }
        }

        return false;
    }
}
