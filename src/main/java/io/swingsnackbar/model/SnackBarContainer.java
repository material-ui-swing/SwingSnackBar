package io.swingsnackbar.model;

import io.swingsnackbar.ui.BasicSnackBarUI;
import sun.swing.SwingUtilities2;

import javax.swing.*;
import java.awt.*;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class SnackBarContainer extends JPanel {

    /**
     * With this code you can load the personal UI with Swing system call
     */
    private static final String uiClassID = "SnackBarUI";

    protected JLabel snackBarText;
    protected JLabel snackBarIcon;

    private SnackBarContainer() {
        super(new BorderLayout());
    }

    public SnackBarContainer(JLabel snackBarText, JLabel snackBarIcon) {
        this();
        if (snackBarText == null || snackBarIcon == null) {
            throw new IllegalArgumentException("TODO complex message, for the moment the function arguments are null");
        }
        this.snackBarText = snackBarText;
        this.snackBarIcon = snackBarIcon;
    }

    public SnackBarContainer(JLabel snackBarText, Icon snackBarIcon) {
        this();
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
    }

    /**
     * This was call only inside the component SnackBar, to set the style inside the component
     * param owner
     */
    public void inflateContent() {
        super.setUI(new BasicSnackBarUI()); // TODO make the possibility to set this inside the UIManager
        super.add(snackBarText, BorderLayout.WEST);
        super.add(snackBarIcon, BorderLayout.EAST);
        setVisible(true);
    }


    /**
     * TODO make a java wrapper for the swingUtilites2
     */
    public Dimension getDimension() {
        int width = 0;
        if (snackBarIcon.getIcon() != null) {
            width = SwingUtilities2.stringWidth(snackBarText,
                    this.snackBarText.getFontMetrics(this.snackBarText.getFont()),
                    this.snackBarText.getText()
            ) + this.snackBarIcon.getIcon().getIconWidth();
        } else {
            this.snackBarText.setHorizontalTextPosition(SwingConstants.CENTER);
            this.snackBarIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
            width = SwingUtilities2.stringWidth(snackBarText,
                    this.snackBarText.getFontMetrics(this.snackBarText.getFont()),
                    this.snackBarText.getText()
            ) + SwingUtilities2.stringWidth(snackBarIcon,
                    this.snackBarIcon.getFontMetrics(this.snackBarIcon.getFont()),
                    this.snackBarIcon.getText()
            );
        }
        width += 50; //gap
        return new Dimension(width, 50);
    }
}
