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
package org.material.component.swingsnackbar;

import mdlaf.themes.MaterialLiteTheme;
import mdlaf.utils.icons.MaterialIconFont;
import org.material.component.swingsnackbar.action.AbstractSnackBarAction;
import org.material.component.swingsnackbar.jmars.JMarsSnackBar;
import org.material.component.swingsnackbar.view.BasicSnackBarUI;
import mdlaf.MaterialLookAndFeel;
import mdlaf.utils.MaterialColors;
import mdlaf.utils.MaterialImageFactory;
import javax.swing.*;
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
            JFrame.setDefaultLookAndFeelDecorated(false);
            UIManager.put("SnackBar.arc", 8);
            UIManager.put("SnackBar.background", MaterialColors.COSMO_BLACK);
            UIManager.put("SnackBar.foreground", MaterialColors.COSMO_DARK_GRAY);
            UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialLiteTheme()));
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private JFrame frame = this;
    private JButton openSnackBar;
    private JButton openJMarsSnackBar;
    private SnackBar snackBar;

    private DemoSnackBar() { }

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
        setLayout(new GridLayout(2, 1));
        openJMarsSnackBar = new JButton("Open JMars SnackBar");
        openSnackBar = new JButton("Open SnackBar");
        add(openJMarsSnackBar);
        add(openSnackBar);

        Icon icon = MaterialImageFactory.getInstance().getImage(
                MaterialIconFont.DELETE,
                MaterialColors.PURPLE_500
        );
        setSize(new Dimension(450, 550));
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("@vincenzopalazzo SnackBar");
        snackBar = new JMarsSnackBar(frame, "Test SnackBar", new JLabel(icon));
    }

    protected void initActions() {
        openJMarsSnackBar.addActionListener(event -> {
            if (snackBar != null && !snackBar.isRunning()) {
                Icon closeIcon = MaterialImageFactory.getInstance().getImage(
                        MaterialIconFont.CLOSE,
                        20,
                        MaterialColors.COSMO_DARK_GRAY
                );
                snackBar = SnackBar.make(frame, "WoW! A new SnackBar for Java Swing", closeIcon)
                        .setDuration(SnackBar.LENGTH_INDEFINITE)
                        .setPosition(SnackBar.TOP)
                        .setAction(new AbstractSnackBarAction() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                snackBar.dismiss();
                            }
                        })
                        .run();
            }
        });

        openSnackBar.addActionListener(event -> {
            if (snackBar != null && !snackBar.isRunning()) {
                Icon closeIcon = MaterialImageFactory.getInstance().getImage(
                        MaterialIconFont.CLOSE,
                        20,
                        MaterialColors.COSMO_DARK_GRAY
                );
                Icon androidIcon = MaterialImageFactory.getInstance().getImage(
                        MaterialIconFont.ANDROID,
                        20,
                        MaterialColors.COSMO_DARK_GRAY
                );
                snackBar = JMarsSnackBar.make(frame, "Oh! It is the same of the Android Component", closeIcon)
                        .setLeftIcon(androidIcon) // This need to be here because the make return the correct instance type, this help to avoid the cast
                        .setDuration(SnackBar.LENGTH_INDEFINITE)
                        .setPosition(SnackBar.TOP)
                        .setAction(new AbstractSnackBarAction() {
                                       @Override
                                       public void mousePressed(MouseEvent e) {
                                          snackBar.dismiss();
                                       }
                                   })
                        .run();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DemoSnackBar demo = new DemoSnackBar();
            demo.initView();
        });
    }
}
