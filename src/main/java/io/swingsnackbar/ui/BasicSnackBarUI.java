package io.swingsnackbar.ui;


import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class BasicSnackBarUI extends BasicPanelUI {

    private static Color primary = new ColorUIResource(55, 58, 60);

    @SuppressWarnings({"MethodOverridesStaticMethodOfSuperclass", "UnusedDeclaration"})
    public static ComponentUI createUI(JComponent c) {
        return new BasicSnackBarUI();
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.setBackground(primary);
        c.setBorder(new RoundedCornerBorder(primary, 7));
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
    }

    @Override
    protected void installDefaults(JPanel p) {
        super.installDefaults(p);
    }

    @Override
    protected void uninstallDefaults(JPanel p) {
        super.uninstallDefaults(p);
    }
}
