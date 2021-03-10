/**
 * MIT License
 * <p>
 * Copyright (c) 2020 Vincenzo Palazzo vincenzopalazzodev@gmail.com
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.material.component.swingsnackbar.model;

import org.material.component.swingsnackbar.action.AbstractSnackBarAction;
import org.material.component.swingsnackbar.SnackBar;
import org.material.component.swingsnackbar.view.BasicSnackBarUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.PanelUI;
import java.awt.*;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class SnackBarContainer extends JPanel implements ISnackBarContainer {

    /**
     * With this code you can load the personal UI with Swing system call
     */
    private static final String uiClassID = "SnackBarUI";

    protected JLabel snackBarText;
    protected JLabel snackBarIcon;
    protected SnackBar snackBar;
    protected int gap = 30;

    public SnackBarContainer(SnackBar snackBar) {
        super(new BorderLayout());
        this.snackBar = snackBar;
    }

    public SnackBarContainer(SnackBar snackBar, JLabel snackBarText, JLabel snackBarIcon) {
        this(snackBar);
        if (snackBarText == null || snackBarIcon == null) {
            throw new IllegalArgumentException("TODO complex message, for the moment the function arguments are null");
        }
        this.snackBarText = snackBarText;
        this.snackBarIcon = snackBarIcon;
        this.updateUI();
    }

    public SnackBarContainer(SnackBar snackBar, JLabel snackBarText, Icon snackBarIcon) {
        this(snackBar);
        if (snackBarText == null) {
            throw new IllegalArgumentException("TODO complex message, for the moment the function arguments are null");
        }
        this.snackBarIcon.setHorizontalTextPosition(SwingConstants.LEFT);
        this.snackBarText = snackBarText;
        if (snackBarIcon != null) {
            this.snackBarIcon = new JLabel();
            this.snackBarIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
            this.snackBarIcon.setIcon(snackBarIcon);
        }
        this.updateUI();
    }

    public SnackBarContainer(SnackBar snackBar, JLabel snackBarText) {
        this(snackBar);
        if (snackBarText == null) {
            throw new IllegalArgumentException("TODO complex message, for the moment the function arguments are null");
        }
        this.snackBarText = snackBarText;
        this.updateUI();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        super.paint(graphics2D);
    }

    @Override
    public void updateUI() {
        if (snackBarText == null) return;
        if (UIManager.get(getUIClassID()) != null) {
            PanelUI ui = (PanelUI) UIManager.getUI(this);
            setUI(ui);
        } else {
            PanelUI ui = new BasicSnackBarUI();
            setUI(ui);
        }
    }

    /**
     * This was call only inside the component SnackBar, to set the style inside the component
     * param owner
     */
    public void inflateContent() {
        super.add(snackBarText, BorderLayout.CENTER);
        if (snackBarIcon != null) {
            super.add(snackBarIcon, BorderLayout.EAST);
        }
        setVisible(true);
    }

    protected int calculateMinimumDimension() {
        int width;
        if (this.snackBarIcon != null && snackBarIcon.getIcon() != null) {
            width = this.snackBarText.getFontMetrics(
                    this.snackBarText.getFont()).stringWidth(this.snackBarText.getText())
                    + this.snackBarIcon.getIcon().getIconWidth();
        } else {
            this.snackBarText.setHorizontalTextPosition(SwingConstants.CENTER);
            width = this.snackBarText.getFontMetrics(
                    this.snackBarText.getFont()).stringWidth(this.snackBarText.getText());
            if (this.snackBarIcon != null) {
                this.snackBarIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
                width = this.snackBarText.getFontMetrics(
                        this.snackBarText.getFont()).stringWidth(this.snackBarText.getText());
                width += (this.snackBarIcon.getText() != null
                        ? this.snackBarIcon.getText().length() * 8
                        : 10);
            }
        }
        return width + (gap * 2);
    }

    public Dimension getDimension() {
        int width = this.calculateMinimumDimension();
        return new Dimension(width, 50);
    }

    public void setAction(AbstractSnackBarAction action) {
        if (this.snackBarIcon == null) {
            this.snackBarIcon = new JLabel();
            super.add(snackBarIcon, BorderLayout.EAST);
            this.snackBar.initStyle();
            this.updateUI();
        }
        this.snackBarIcon.addMouseListener(action);
    }

    public void setIcon(Icon icon) {
        if (this.snackBarIcon == null) {
            this.snackBarIcon = new JLabel();
            super.add(snackBarIcon, BorderLayout.EAST);
            this.snackBar.initStyle();
            this.snackBarIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
            this.updateUI();
        }
        this.snackBarIcon.setIcon(icon);
    }

    public void setGap(int gap) {
        this.gap = gap;
        this.snackBarIcon.setBorder(new EmptyBorder(5, gap / 4, 5, gap / 4));
        this.snackBar.revalidate();
    }

    public void setText(String text) {
        this.snackBarText.setText(text);
    }

    public void setIconTextColor(Color color) {
        if (snackBarIcon.getIcon() == null) {
            this.snackBarIcon.setForeground(color);
        }
    }

    public void setIconTextStyle(Font font) {
        if (snackBarIcon.getIcon() == null) {
            this.snackBarIcon.setFont(font);
        }
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
    }

    @Override
    public void setBorder(Border border) {
        super.setBorder(border);
    }

    //getter and setter
    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    public JLabel getSnackBarText() {
        return snackBarText;
    }

    public JLabel getSnackBarIcon() {
        return snackBarIcon;
    }

    public SnackBar getSnackBar() {
        return snackBar;
    }
}
