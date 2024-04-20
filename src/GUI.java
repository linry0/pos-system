import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private JFrame frame;
    private JPanel panel;
    private JButton button;
    private JList<String> menuList;
    private JList<String> orderList;

    public GUI() {
        frame = new JFrame("Food Ordering System");
        panel = new JPanel();
        button = new JButton("Add to order");
        menuList = new JList<>(new String[]{"Item 1", "Item 2", "Item 3"});
        orderList = new JList<>(new DefaultListModel<>());

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = menuList.getSelectedValue();
                ((DefaultListModel<String>) orderList.getModel()).addElement(selectedItem);
            }
        });

        panel.setLayout(new GridLayout(1, 3));
        panel.add(new JScrollPane(menuList));
        panel.add(button);
        panel.add(new JScrollPane(orderList));

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GUI();
    }
}