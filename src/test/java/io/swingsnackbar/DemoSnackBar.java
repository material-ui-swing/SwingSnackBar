package io.swingsnackbar;

import io.swingsnackbar.action.AbstractSnackBarAction;
import io.swingsnackbar.view.BasicSnackBarUI;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;
import mdlaf.utils.MaterialColors;
import mdlaf.utils.MaterialFontFactory;
import mdlaf.utils.MaterialImageFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class DemoSnackBar extends JFrame {

    static {
        try {
            UIManager.put("SnackBarUI", BasicSnackBarUI.class.getCanonicalName());
            JDialog.setDefaultLookAndFeelDecorated(true);
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.put("SnackBar.arc", 5);
            UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialLiteTheme()));
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private JFrame frame = this;
    private JButton openSnackBar;
    private SnackBar snackBar;

    private DemoSnackBar() {
    }

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
            if (snackBar != null && !snackBar.isRunning()) {
                snackBar = SnackBar.make(frame, "Do you want open another Snackbar?", "OPEN")
                        .setMarginBottom(0)
                        .setGap(50)
                        .setPosition(SnackBar.TOP_LEFT)
                        .setIconTextColor(MaterialColors.COSMO_STRONG_BLUE)
                        .setSnackBarBackground(MaterialColors.COSMO_DARK_GRAY)
                        .setSnackBarForeground(MaterialColors.COSMO_BLACK)
                        .setIconTextStyle(MaterialFontFactory.getInstance().getFont(MaterialFontFactory.BOLD))
                        .setDuration(SnackBar.LENGTH_SHORT)
                        .setAction(new AbstractSnackBarAction() {
                                       @Override
                                       public void mousePressed(MouseEvent e) {
                                           Icon icon = MaterialImageFactory.getInstance().getImage(
                                                   GoogleMaterialDesignIcons.DONE,
                                                   MaterialColors.COSMO_GREEN);
                                           SnackBar.make(frame, "Second snackbar opened", icon)
                                                   .setDuration(SnackBar.LENGTH_SHORT)
                                                   .run(SnackBar.BOTTOM);
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
