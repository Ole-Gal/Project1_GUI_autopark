import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewPanel extends JPanel {

    JTextField tfMinPrice;
    JTextField tfMaxPrice;
    JTextField tfBrandFilter;

    JButton butRange;
    JButton butFilter;
    JButton butShowAll;
    JButton butAboveAvg;
    JButton butSortById;
    JButton butSortByPrice;
    JButton butDeleteAboveAvg;
    JButton butTotalsByYear;

    public ViewPanel() {
        setLayout(new GridLayout(3, 1, 5, 5));

        // --- Первая строка: диапазон цены ---
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1.add(new JLabel("Укажите диапазон цены:"));
        tfMinPrice = new JTextField(8);
        tfMaxPrice = new JTextField(8);
        p1.add(tfMinPrice);
        p1.add(new JLabel(" - "));
        p1.add(tfMaxPrice);

        butRange = new JButton("Цена в диапазоне");
        p1.add(butRange);

        // --- Вторая строка: фильтр по марке ---
        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(new JLabel("Введите фильтр для марки:"));
        tfBrandFilter = new JTextField(12);
        p2.add(tfBrandFilter);

        butFilter = new JButton("Фильтр по марке");
        p2.add(butFilter);

        // --- Третья строка: кнопки запросов/сортировок ---
        JPanel p3 = new JPanel(new GridLayout(2, 3, 5, 5));

        butShowAll       = new JButton("Вывести все");
        butAboveAvg      = new JButton("Цена выше средней");
        butSortById      = new JButton("Сортировать по id");
        butSortByPrice   = new JButton("Сортировать по цене");
        butDeleteAboveAvg= new JButton("Удалить авто с ценой выше средней");
        butTotalsByYear  = new JButton("Итоги по годам");

        p3.add(butShowAll);
        p3.add(butAboveAvg);
        p3.add(butSortById);
        p3.add(butSortByPrice);
        p3.add(butDeleteAboveAvg);
        p3.add(butTotalsByYear);

        add(p1);
        add(p2);
        add(p3);

        // ---------------- ЛОГИКА КНОПОК ----------------

        // 1. Вывести все
        butShowAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Global.updateJTable(Global.table.getCars());
                MainFrame.MSG.setText("   Показаны все записи");
            }
        });

        // 2. Цена выше средней (ПРОСМОТР)
        butAboveAvg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CarFleet fleet = Global.table.aboveAvgPrice();
                Global.updateJTable(fleet.getCars());
                MainFrame.MSG.setText("   Автомобили с ценой выше средней");
            }
        });

        // 3. Сортировать по id
        butSortById.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Global.table.sortInPlaceById();
                Global.updateJTable(Global.table.getCars());
                MainFrame.MSG.setText("   Таблица отсортирована по ID");
            }
        });

        // 4. Сортировать по цене
        butSortByPrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Global.table.sortInPlaceByPrice();
                Global.updateJTable(Global.table.getCars());
                MainFrame.MSG.setText("   Таблица отсортирована по цене");
            }
        });

        // 5. Удалить авто с ценой выше средней (ИЗ ТАБЛИЦЫ)
        butDeleteAboveAvg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Global.table.deleteAvgPrice()) {
                    MainFrame.MSG.setText("   Записи не удалены, возможно таких записей нет");
                } else {
                    MainFrame.MSG.setText("   Удалены авто с ценой выше средней");
                }
                Global.updateJTable(Global.table.getCars());
            }
        });

        // 6. Цена в диапазоне (из полей сверху)
        butRange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s1 = tfMinPrice.getText().trim();
                String s2 = tfMaxPrice.getText().trim();
                try {
                    float p1 = Float.parseFloat(s1);
                    float p2 = Float.parseFloat(s2);
                    CarFleet fleet = Global.table.betweenPrice(p1, p2);
                    Global.updateJTable(fleet.getCars());
                    MainFrame.MSG.setText("   Авто с ценой в диапазоне " + p1 + " - " + p2);
                } catch (Exception ex) {
                    MainFrame.MSG.setText("   Неверные значения диапазона");
                }
            }
        });

        // 7. Фильтр по марке
        butFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filter = tfBrandFilter.getText();
                CarFleet fleet = Global.table.filterOfName(filter);
                Global.updateJTable(fleet.getCars());
                MainFrame.MSG.setText("   Фильтр по марке: " + filter);
            }
        });

        // 8. Итоги по годам
        butTotalsByYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<TotalRecord> totals = Global.table.totalCountSumYear();
                if (totals == null || totals.isEmpty()) {
                    MainFrame.MSG.setText("   Нет данных для итогов по годам");
                    return;
                }

                StringBuilder sb = new StringBuilder();
                sb.append("Год    Кол-во    Средняя цена\n");
                for (TotalRecord tr : totals) {
                    sb.append(String.format("%4d    %6d    %10.2f\n",
                            tr.getYear(), tr.getCount(), tr.getAvgPrice()));
                }

                JTextArea ta = new JTextArea(sb.toString());
                ta.setEditable(false);
                ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
                JScrollPane sp = new JScrollPane(ta);

                JDialog dlg = new JDialog(MainFrame.frame, "Итоги по годам", true);
                dlg.getContentPane().add(sp);
                dlg.setSize(400, 300);
                dlg.setLocationRelativeTo(MainFrame.frame);
                dlg.setVisible(true);

                MainFrame.MSG.setText("   Итоги по годам рассчитаны");
            }
        });
    }
}
