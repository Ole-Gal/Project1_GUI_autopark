import javax.swing.table.TableModel;
import java.util.List;

public class Global {

    public static CarFleet table;           // внутренняя таблица (модель предметной области)
    public static java.util.List<Car> cars; // список для отображения в JTable
    public static CarTableModel tableModel; // модель JTable

    public static void updateJTable(List<Car> list) {
        cars.clear();
        cars.addAll(list);
        tableModel.fireTableDataChanged();
    }
}
