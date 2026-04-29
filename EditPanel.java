import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class EditPanel extends JPanel {

    JTextField tf1; // id
    JTextField tf2; // марка
    JTextField tf3; // цена
    JTextField tf4; // год

    public EditPanel(){

        setLayout(new GridLayout(3,4,2,2));

        JButton but1 = new JButton("Добавить");
        JButton but2 = new JButton("Обновить");
        JButton but3 = new JButton("Удалить");
        JButton but4 = new JButton("Удалить > ср. цены");

        tf1 = new JTextField(""); // id
        tf2 = new JTextField(""); // марка
        tf3 = new JTextField(""); // цена
        tf4 = new JTextField(""); // год

        JLabel l1 = new JLabel("ID");
        JLabel l2 = new JLabel("Марка");
        JLabel l3 = new JLabel("Цена");
        JLabel l4 = new JLabel("Год");

        // --- первая строка: подписи ---
        add(l1); add(l2); add(l3); add(l4);

        // --- вторая строка: поля ввода ---
        add(tf1); add(tf2); add(tf3); add(tf4);

        // --- третья строка: кнопки ---
        add(but1); add(but2); add(but3); add(but4);

        // --- обработчики кнопок ---
        but1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insert();
            }
        });

        but2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });

        but3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });

        but4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteGroup();
            }
        });
    }

    private void insert(){
        String sId = tf1.getText();
        String sBrand = tf2.getText();
        String sPrice = tf3.getText();
        String sYear = tf4.getText();

        if (sId.equals("") || sBrand.equals("") || sPrice.equals("") || sYear.equals("")){
            MainFrame.MSG.setText("   Задайте значения всех полей");
            return;
        }

        int id, year;
        float price;
        try{
            id = Integer.parseInt(sId);
            price = Float.parseFloat(sPrice);
            year = Integer.parseInt(sYear);
        } catch (NumberFormatException e){
            MainFrame.MSG.setText("   Неверный формат числовых полей");
            return;
        }

        MainFrame.MSG.setText("   Запрос на добавление записи");
        Car car = new Car(id, sBrand, price, year);

        if (!Global.table.addCar(car))
            MainFrame.MSG.setText("   Запись не добавлена, возможно нарушена уникальность ключа");

        Global.updateJTable(Global.table.getCars());
        clearFields();
    }

    private void update(){
        String sId = tf1.getText();
        String sBrand = tf2.getText();
        String sPrice = tf3.getText();
        String sYear = tf4.getText();

        if (sId.equals("") || sBrand.equals("") || sPrice.equals("") || sYear.equals("")){
            MainFrame.MSG.setText("   Задайте значения всех полей");
            return;
        }

        int id, year;
        float price;
        try{
            id = Integer.parseInt(sId);
            price = Float.parseFloat(sPrice);
            year = Integer.parseInt(sYear);
        } catch (NumberFormatException e){
            MainFrame.MSG.setText("   Неверный формат числовых полей");
            return;
        }

        MainFrame.MSG.setText("   Запрос на обновление записи");
        Car car = new Car(id, sBrand, price, year);

        if (!Global.table.updateCarByKey(car))
            MainFrame.MSG.setText("   Запись не обновлена, возможно записи с таким ключом нет");

        Global.updateJTable(Global.table.getCars());
        clearFields();
    }

    private void delete(){
        String sId = tf1.getText();
        if (sId.equals("")){
            MainFrame.MSG.setText("   Задайте значение ключа (ID)");
            return;
        }

        int id;
        try{
            id = Integer.parseInt(sId);
        } catch (NumberFormatException e){
            MainFrame.MSG.setText("   Неверный формат ID");
            return;
        }

        MainFrame.MSG.setText("   Запрос на удаление записи по ключу");

        Car car = new Car(id, "Noname", 0, 0);

        if (!Global.table.delCar(car))
            MainFrame.MSG.setText("   Запись не удалена, возможно записи с таким ключом нет");

        Global.updateJTable(Global.table.getCars());
        clearFields();
    }

    private void deleteGroup(){
        if (!Global.table.deleteAvgPrice())
            MainFrame.MSG.setText("   Записи не удалены, возможно таких записей нет");
        else
            MainFrame.MSG.setText("   Удалены записи с ценой выше средней");

        Global.updateJTable(Global.table.getCars());
        clearFields();
    }

    private void clearFields(){
        tf1.setText("");
        tf2.setText("");
        tf3.setText("");
        tf4.setText("");
    }
}
