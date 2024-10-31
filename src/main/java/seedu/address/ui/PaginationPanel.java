package seedu.address.ui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import seedu.address.model.contact.Contact;

/**
 * An UI component that displays a list of {@code Contact} with {@code Pagination}.
 */
public class PaginationPanel extends Pagination {
    public static final int ROWS_PER_PAGE = 10;
    private static int currentPageIndex = 0;
    private final ObservableList<Contact> contactList;

    /**
     * Creates a panel that contains a list of {@code Contact} with {@code Pagination}.
     * @param contactList A list of contacts to display.
     */
    public PaginationPanel(ObservableList<Contact> contactList) {
        super();
        this.contactList = contactList;
        this.contactList.addListener(this::onListItemsChanged);
        this.initPagination();
    }

    /**
     * Converts the index of the item of the current page to the index in {@code contactList}.
     * @param itemIndex The index of the item of the current page.
     * @return The index of the item in {@code contactList}.
     */
    public static int convertItemIndexToDisplayIndex(int itemIndex) {
        return (currentPageIndex) * ROWS_PER_PAGE + itemIndex + 1;
    }

    private void initPagination() {
        final int minPageCount = 1;
        int pageCount = Math.max((int) Math.ceil((double) contactList.size() / ROWS_PER_PAGE), minPageCount);
        this.setPageCount(pageCount);
        this.setStyle("-fx-page-information-alignment: right;");
        this.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex) {
        PaginationPanel.currentPageIndex = pageIndex;
        int fromIndex = pageIndex * PaginationPanel.ROWS_PER_PAGE;
        int endIndex = Math.min(fromIndex + PaginationPanel.ROWS_PER_PAGE, contactList.size());
        List<Contact> subList = contactList
                .stream()
                .toList()
                .subList(fromIndex, endIndex);
        ContactListPanel contactListPanel = new ContactListPanel(FXCollections.observableList(subList));
        return contactListPanel.getRoot();
    }

    private void onListItemsChanged(ListChangeListener.Change<? extends Contact> unused) {
        this.initPagination();
    }
}
