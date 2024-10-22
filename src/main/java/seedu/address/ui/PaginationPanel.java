package seedu.address.ui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import seedu.address.model.person.Person;

/**
 * An UI component that displays a list of {@code Person} with {@code Pagination}.
 */
public class PaginationPanel extends Pagination {
    public static final int ROWS_PER_PAGE = 10;
    private static int currentPageIndex = 0;
    private final ObservableList<Person> personList;

    /**
     * Creates a panel that contains a list of {@code Person} with {@code Pagination}.
     * @param personList A list of persons to display.
     */
    public PaginationPanel(ObservableList<Person> personList) {
        super();
        this.personList = personList;
        this.personList.addListener(this::onListItemsChanged);
        this.initPagination();
    }

    /**
     * Converts the index of the item of the current page to the index in {@code personList}.
     * @param itemIndex The index of the item of the current page.
     * @return The index of the item in {@code personList}.
     */
    public static int convertItemIndexToDisplayIndex(int itemIndex) {
        return (currentPageIndex) * ROWS_PER_PAGE + itemIndex + 1;
    }

    private void initPagination() {
        final int minPageCount = 1;
        int pageCount = Math.max((int) Math.ceil((double) personList.size() / ROWS_PER_PAGE), minPageCount);
        this.setPageCount(pageCount);
        this.setStyle("-fx-page-information-alignment: right;");
        this.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex) {
        PaginationPanel.currentPageIndex = pageIndex;
        int fromIndex = pageIndex * PaginationPanel.ROWS_PER_PAGE;
        int endIndex = Math.min(fromIndex + PaginationPanel.ROWS_PER_PAGE, personList.size());
        List<Person> subList = personList
                .stream()
                .toList()
                .subList(fromIndex, endIndex);
        PersonListPanel personListPanel = new PersonListPanel(FXCollections.observableList(subList));
        return personListPanel.getRoot();
    }

    private void onListItemsChanged(ListChangeListener.Change<? extends Person> unused) {
        this.initPagination();
    }
}
