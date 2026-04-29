import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TotalTableModel extends AbstractTableModel {

    private List<TotalRecord> totals;

    private final String[] columnNames = {
            "Год",
            "Число авто",
            "Средняя цена"
    };

    public TotalTableModel(List<TotalRecord> totals) {
        this.totals = totals;
    }

    @Override
    public int getRowCount() {
        return totals == null ? 0 : totals.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        TotalRecord tr = totals.get(row);
        switch (col) {
            case 0: return tr.getYear();
            case 1: return tr.getCount();
            case 2: return tr.getAvgPrice();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false; // Итоги не редактируются
    }
}
