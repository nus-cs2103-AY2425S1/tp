package seedu.address.testutil;

import seedu.address.model.addresses.BtcAddress;
import seedu.address.model.addresses.EthAddress;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.addresses.SolAddress;


/**
 * A utility class containing a list of {@code PublicAddress} objects to be used in tests.
 */
public class TypicalPublicAddresses {
    public static final String VALID_PUBLIC_ADDRESS_ETH_MAIN_STRING =
        "0x0B1C9E1Fb5E13c797c7f0134641810E9A7ca14d2"; //this is a real ETH public address
    public static final String VALID_PUBLIC_ADDRESS_ETH_SUB_STRING =
        "0xE859Ed4267af9f247aAB2703CebcA466C71887b2"; //this is a real ETH public address
    public static final String VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING =
        "bc1qak5yzzvn7va9qmkp7g5uykg2msc5kt0z0uhv2k"; //this is a real BTC public address
    public static final String VALID_PUBLIC_ADDRESS_BTC_SUB_STRING =
        "bc1qrcpwxgwmy7yp73eq9xmnr9t43ncl7ms8jly0tt"; //this is a real BTC public address
    public static final String VALID_PUBLIC_ADDRESS_BTC_COLD_STRING =
        "bc1phkt4pgl42lad3mm2srne73y8a7zgam3cumrzmc";
    public static final String VALID_PUBLIC_ADDRESS_BTC_NOT_IN_ADDRESS_BOOK_BTC_STRING =
        "bc1q5y5960gr9vnjlmwfst232z07surun7rey5svu9"; //this is a real BTC public address
    public static final String VALID_PUBLIC_ADDRESS_SOL_MAIN_STRING =
        "2rB4kogqBNwCxmDXwRCNRPijV94g5udCb7Bp435fvfBA"; //this is a real SOL public address
    public static final String VALID_PUBLIC_ADDRESS_SOL_SUB_STRING =
        "44wP1ZSKZX4PDADKU2i14EyU8TsXdjZahxGNMryvv3Ty"; //this is a real SOL public address
    //ETH PA length 42
    //SOL PA length 32-44
    //BTC PA length 26-35
    //testing purposes
    public static final String INVALID_PUBLIC_ADDRESS_INVALID_CHAR_STRING = "0x!@#$%^&*()";
    public static final String INVALID_PUBLIC_ADDRESS_TOO_LONG_STRING =
        "0x0B1C9E1Fb5Ec797c7f0134641810E9A7ca14d2wjrenlkewrngilsdfjdshfsdkfewrkhgnilwerkngiwrengiwrhngirwengiwrengiwer";
    public static final String VALID_PUBLIC_ADDRESS_ONLY_NUMBERS_STRING = "0123456789012345678901234567890123456789";
    public static final String VALID_PUBLIC_ADDRESS_ONLY_CHARS_STRING = "0123456789abcdef0123456789abcdef01234567";

    public static final PublicAddress VALID_PUBLIC_ADDRESS_ETH_MAIN =
        new EthAddress(VALID_PUBLIC_ADDRESS_ETH_MAIN_STRING, "main");
    public static final PublicAddress VALID_PUBLIC_ADDRESS_ETH_SUB =
        new EthAddress(VALID_PUBLIC_ADDRESS_ETH_SUB_STRING, "sub");
    public static final PublicAddress VALID_PUBLIC_ADDRESS_BTC_MAIN =
        new BtcAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING, "main");
    public static final PublicAddress VALID_PUBLIC_ADDRESS_BTC_SUB =
        new BtcAddress(VALID_PUBLIC_ADDRESS_BTC_SUB_STRING, "sub");
    public static final PublicAddress VALID_PUBLIC_ADDRESS_BTC_COLD =
        new BtcAddress(VALID_PUBLIC_ADDRESS_BTC_COLD_STRING, "cold");
    public static final PublicAddress VALID_PUBLIC_ADDRESS_BTC_NOT_IN_ADDRESS_BOOK =
        new BtcAddress(VALID_PUBLIC_ADDRESS_BTC_NOT_IN_ADDRESS_BOOK_BTC_STRING, "not_in_address_book");
    public static final PublicAddress VALID_PUBLIC_ADDRESS_SOL_MAIN =
        new SolAddress(VALID_PUBLIC_ADDRESS_SOL_MAIN_STRING, "main");
    public static final PublicAddress VALID_PUBLIC_ADDRESS_SOL_SUB =
        new SolAddress(VALID_PUBLIC_ADDRESS_SOL_SUB_STRING, "sub");

}
