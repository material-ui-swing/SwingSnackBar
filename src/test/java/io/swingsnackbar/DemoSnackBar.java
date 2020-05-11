/**
 * MIT License
 *
 * Copyright (c) 2020 Vincenzo Palazzo vincenzopalazzodev@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.swingsnackbar;

import io.swingsnackbar.action.AbstractSnackBarAction;
import io.swingsnackbar.view.BasicSnackBarUI;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;
import mdlaf.utils.MaterialColors;
import mdlaf.utils.MaterialImageFactory;
import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class DemoSnackBar extends JFrame {

    static {
        try {
            UIManager.put("SnackBarUI", BasicSnackBarUI.class.getCanonicalName());
            JDialog.setDefaultLookAndFeelDecorated(true);
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.put("SnackBar.arc", 0);
            UIManager.put("SnackBar.background", MaterialColors.COSMO_BLACK);
            UIManager.put("SnackBar.foreground", MaterialColors.WHITE);
            UIManager.put("SnackBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(5, 5, 5, 5)));
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
        add(new JToggleButton());
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
                snackBar = SnackBar.make(frame, "Do you like Swing Snackbar?", "OPEN")
                        .setDuration(SnackBar.LENGTH_SHORT)
                        .setAction(new AbstractSnackBarAction() {
                                       @Override
                                       public void mousePressed(MouseEvent e) {
                                           System.out.println("Hello SnackBar");
                                       }
                                   }
                        ).run();
            }
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
