import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TotalDialog extends JDialog {

    public TotalDialog(Frame owner, String title, List<TotalRecord> totals) {
        super(owner, title, true); // true = модальное окно

        setLayout(new BorderLayout(5, 5));

        // Таблица итогов
        JTable table = new JTable(new TotalTableModel(totals));
        JScrollPane scroll = new JScrollPane(table);

        // Кнопка закрытия
        JButton close = new JButton("Закрыть");
        close.addActionListener(e -> dispose());

        JPanel p = new JPanel();
        p.add(close);

        add(scroll, BorderLayout.CENTER);
        add(p, BorderLayout.SOUTH);

        setSize(400, 300);
        setLocationRelativeTo(owner);
    }
}
