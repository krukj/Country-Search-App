package org.example.gui_swing;

import javax.swing.*;

public class Gui extends JFrame {
    private JPanel panelMain;
    private JButton btnClick;
    private JComboBox comboBox1;

    public Gui() {

    }

    public static void main(String[] args) {
        Gui gui = new Gui();
        gui.setContentPane(gui.panelMain);
        gui.setTitle("Country Search Application");
        gui.setSize(400, 400);
        gui.setVisible(true);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
