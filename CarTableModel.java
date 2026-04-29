import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CarTableModel extends AbstractTableModel {

    private List<Car> cars;

    private final String[] columnNames = {
            "ID",
            "Марка",
            "Цена",
            "Год"
    };

    public CarTableModel(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public int getRowCount() {
        return cars.size();
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
        Car c = cars.get(row);
        switch (col) {
            case 0: return c.getId();
            case 1: return c.getBrand();
            case 2: return c.getPrice();
            case 3: return c.getYear();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false; // редактирование только через EditPanel
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
        fireTableDataChanged();
    }
}
