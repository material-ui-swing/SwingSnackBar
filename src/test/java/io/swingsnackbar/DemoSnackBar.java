package io.swingsnackbar;

import mdlaf.MaterialLookAndFeel;

import javax.swing.*;
import java.awt.*;

public class DemoSnackBar extends JFrame {

    static {
        try {
            JDialog.setDefaultLookAndFeelDecorated(true);
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private JFrame frame = this;

    private JButton openSnackBar;

    private DemoSnackBar(){}

    public void initView(){
        initComponents();
        initActions();

        setSize(new Dimension(450, 550));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    protected void initComponents(){
        setLayout(new BorderLayout());

        openSnackBar = new JButton("Open SnackBar");
        add(openSnackBar, BorderLayout.SOUTH);
    }

    protected void initActions(){
        openSnackBar.addActionListener(event -> {
            SnackBar dialog = new SnackBar(frame, "Test SnackBar");
            //dialog.setContentPane(new JPanel());
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DemoSnackBar demo = new DemoSnackBar();
                demo.initView();
            }
        });
    }

}
