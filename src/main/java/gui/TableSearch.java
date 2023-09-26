package gui;

import main.Logs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;

public class TableSearch extends JTable {
    public void search(String text) {
        filter(text);
    }

    public TableSearch(TableModel dm) {
        super(dm);
    }

    private void filter(String text) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(getModel());

        List<RowFilter<TableModel, Object>> rowFilterList = new ArrayList<>();
        try {
            for (int i = 0; i < getColumnCount(); i++) {
                rowFilterList.add(RowFilter.regexFilter("(?iu)" + text, i));
            }
            sorter.setRowFilter(RowFilter.orFilter(rowFilterList));
            setRowSorter(sorter);
        } catch (Exception e) {
            Logs.writeLog(e.getMessage());
        }
    }

    public void clearFilter() {
        setRowSorter(new TableRowSorter<>(getModel()));
    }


}
