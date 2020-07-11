/**
 * MIT License
 *
 * Copyright (c) 2019-2020 Vincenzo Palazzo vincenzopalazzo1996@gmail.com
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
package org.material.component.swingsnackbar.view;

import org.material.component.swingsnackbar.SnackBar;
import org.material.component.swingsnackbar.model.SnackBarContainer;

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
            }else if (icon != null && icon.getIcon() != null){
                icon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
            background = UIManager.getColor(getPrefix() + ".background");
            foreground = UIManager.getColor(getPrefix() + ".foreground");
            borderContent = UIManager.getBorder(getPrefix() + ".border");
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
