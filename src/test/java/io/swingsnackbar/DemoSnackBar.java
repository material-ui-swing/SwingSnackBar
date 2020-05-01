package io.swingsnackbar;

import javafx.scene.paint.Material;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;
import mdlaf.utils.MaterialBorders;
import mdlaf.utils.MaterialColors;
import mdlaf.utils.MaterialFontFactory;
import mdlaf.utils.MaterialImageFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DemoSnackBar extends JFrame {

    static {
        try {
            JDialog.setDefaultLookAndFeelDecorated(true);
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel(new MaterialLookAndFeel(new JMarsDarkTheme()));
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private JFrame frame = this;
    private JButton openSnackBar;
    private SnackBar snackBar;

    private DemoSnackBar() {}

    public void initView() {
        initComponents();
        initActions();

        setSize(new Dimension(450, 550));
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("@vincenzopalazzo SnackBar");
    }

    protected void initComponents() {
        setLayout(new BorderLayout());

        openSnackBar = new JButton("Open SnackBar");
        add(openSnackBar, BorderLayout.SOUTH);

        Icon icon = MaterialImageFactory.getInstance().getImage(
                GoogleMaterialDesignIcons.DELETE,
                MaterialColors.PURPLE_500
        );
        setSize(new Dimension(450, 550));
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("@vincenzopalazzo SnackBar");
        snackBar = new SnackBar(frame, "Test SnackBar", icon);
    }

    protected void initActions() {
        openSnackBar.addActionListener(event -> {
            //dialog.setContentPane(new JPanel());
          /*  JLabel retry = new JLabel("RETRY");
            retry.setFont(MaterialFontFactory.getInstance().getFont(MaterialFontFactory.BOLD));
            retry.setForeground(MaterialColors.DEEP_PURPLE_300);
            SnackBar.doShowToast(frame,
                    "TEST with static method and Snackbar Container",
                    retry,
                    SnackBar.SnackBarPosition.TOP
            );*/
            if(snackBar != null && !snackBar.isRunning()){
                snackBar = SnackBar.make(frame, "Android API", SnackBar.LENGTH_LONG)
                        .setAction("DELETE", new MouseListener(){

                                    @Override
                                    public void mouseClicked(MouseEvent e) {

                                    }

                                    @Override
                                    public void mousePressed(MouseEvent e) {
                                        System.out.println("CLICK on ICON");
                                    }

                                    @Override
                                    public void mouseReleased(MouseEvent e) {

                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                    }
                                }
                        ).run();
            }
            //snackBar.run();
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
