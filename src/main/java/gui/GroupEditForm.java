package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * окно для редактирования списка контактов в группе
 */
public class GroupEditForm extends JFrame {
    private static DefaultListModel listModel = new DefaultListModel();

    public GroupEditForm() throws HeadlessException {
        initGui();
    }

    private void initGui() {
        setSize(50, 100);
        setUndecorated(true);
        JList<String> list = new JList<>();
        list.setModel(listModel);
        add(list);
    }

    /**
     * обновление списка контактов в группе
     */
    public void refreshModel(List<String> list) {
        listModel.removeAllElements();
        for (String s : list) {
            listModel.addElement(s);
        }
        validate();
    }

    /**
     * показ окна
     */
    public void showWindow(Point pointObject, int width) {
        Point point = new Point(pointObject.x + width + 3, pointObject.y);
        setLocation(point);
        setVisible(true);
    }
}
