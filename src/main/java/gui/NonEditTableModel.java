package gui;

import javax.swing.table.DefaultTableModel;

public class NonEditTableModel extends DefaultTableModel {

    public NonEditTableModel() {
    }

    public NonEditTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public void setRowCount(int rowCount) {
        super.setRowCount(rowCount);
    }

    @Override
    public void removeRow(int row) {
        super.removeRow(row);
    }
}
