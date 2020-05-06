package io.swingsnackbar.view;


import io.swingsnackbar.SnackBar;
import io.swingsnackbar.model.SnackBarContainer;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class BasicSnackBarUI extends BasicPanelUI {

    private static final Color DEFAULT_BACKGROUND = new ColorUIResource(55, 58, 60);
    private static final Color DEFAULT_FOREGROUND = new ColorUIResource(248, 249, 250);
    private static final Color DEFAULT_COLOR_ICON = new ColorUIResource(255, 117, 24);
    protected static final Border DEFAULT_CONTENT_BORDER = new BorderUIResource(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    private static final String PREFIX = "SnackBar";

    @SuppressWarnings({"MethodOverridesStaticMethodOfSuperclass", "UnusedDeclaration"})
    public static ComponentUI createUI(JComponent c) {
        return new BasicSnackBarUI();
    }

    protected Color background;
    protected Color foreground;
    protected Color colorIconOrText;
    protected Font fontIconOrText;
    protected Border borderContent;
    protected SnackBarContainer container;
    protected SnackBar snackBar;


    @Override
    public void installUI(JComponent c) {
        super.installUI(c);

        if (c instanceof SnackBarContainer) {
            this.container = (SnackBarContainer) c;
            this.snackBar = this.container.getSnackBar();
            JLabel icon = container.getSnackBarIcon();
            if (icon != null && icon.getIcon() == null) {
                icon.setForeground(this.colorIconOrText);
                icon.setFont(this.fontIconOrText);
            }
            if (container.getSnackBarText() != null) {
                container.getSnackBarText().setBackground(this.background);
                container.getSnackBarText().setForeground(this.foreground);
            }
        }
    }

    @Override
    protected void installDefaults(JPanel p) {
        super.installDefaults(p);

        //All propriety should be insert inside the UIManager
        if (UIManager.get(getPrefix() + ".background") != null &&
                UIManager.get(getPrefix() + ".foreground") != null &&
                UIManager.get(getPrefix() + ".border") != null) {
            LookAndFeel.installColors(p, getPrefix() + ".background", getPrefix() + ".foreground");
            LookAndFeel.installBorder(p, getPrefix() + ".border");
            background = null;
            foreground = null;
            borderContent = null;
        }
        background = (background == null) ? DEFAULT_BACKGROUND : background;
        foreground = (foreground == null) ? DEFAULT_FOREGROUND : foreground;
        borderContent = (borderContent == null) ? DEFAULT_CONTENT_BORDER : borderContent;
        colorIconOrText = (colorIconOrText == null) ? DEFAULT_COLOR_ICON : colorIconOrText;
        fontIconOrText = (fontIconOrText == null) ? new Font(Font.SERIF, Font.BOLD, 14) : fontIconOrText;
        p.setForeground(foreground);
        p.setBackground(background);
        p.setBorder(borderContent);
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
    }

    @Override
    protected void uninstallDefaults(JPanel p) {
        super.uninstallDefaults(p);
    }

    public String getPrefix() {
        return PREFIX;
    }
}