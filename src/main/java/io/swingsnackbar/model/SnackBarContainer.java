package io.swingsnackbar.model;

import io.swingsnackbar.SnackBar;
import io.swingsnackbar.ui.BasicSnackBarUI;
import sun.swing.SwingUtilities2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

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
    protected SnackBar snackBar;

    private SnackBarContainer(SnackBar snackBar) {
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
    }

    public SnackBarContainer(SnackBar snackBar, JLabel snackBarText) {
        this(snackBar);
        if (snackBarText == null) {
            throw new IllegalArgumentException("TODO complex message, for the moment the function arguments are null");
        }
        this.snackBarText = snackBarText;
    }

    /**
     * This was call only inside the component SnackBar, to set the style inside the component
     * param owner
     */
    public void inflateContent() {
        super.setUI(new BasicSnackBarUI()); // TODO make the possibility to set this inside the UIManager
        super.add(snackBarText, BorderLayout.WEST);
        if(snackBarIcon != null){
            super.add(snackBarIcon, BorderLayout.EAST);
        }
        setVisible(true);
    }


    /**
     * TODO make a java wrapper for the swingUtilites2
     */
    public Dimension getDimension() {
        int width;
        if (this.snackBarIcon != null && snackBarIcon.getIcon() != null) {
            width = SwingUtilities2.stringWidth(snackBarText,
                    this.snackBarText.getFontMetrics(this.snackBarText.getFont()),
                    this.snackBarText.getText()
            ) + this.snackBarIcon.getIcon().getIconWidth();
        } else {
            this.snackBarText.setHorizontalTextPosition(SwingConstants.CENTER);
            width = SwingUtilities2.stringWidth(snackBarText,
                    this.snackBarText.getFontMetrics(this.snackBarText.getFont()),
                    this.snackBarText.getText()
            );
            if(this.snackBarIcon != null){
                this.snackBarIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
                width += SwingUtilities2.stringWidth(snackBarIcon,
                        this.snackBarIcon.getFontMetrics(this.snackBarIcon.getFont()),
                        this.snackBarIcon.getText()
                );
            }
        }
        width += 150; //gap
        return new Dimension(width, 50);
    }

    public void setAction(String text, MouseListener action) {
        if(this.snackBarIcon == null){
            this.snackBarIcon = new JLabel();
            super.add(snackBarIcon, BorderLayout.EAST);
            this.snackBar.initStyle();
            super.updateUI();
        }
        this.snackBarIcon.setText(text);
        this.snackBarIcon.addMouseListener(action);
    }

    public void setAction(String text, Color color, MouseListener action){
        if(this.snackBarIcon == null){
            this.snackBarIcon = new JLabel();
            super.add(snackBarIcon, BorderLayout.EAST);
            this.snackBar.initStyle();
        }
        this.snackBarIcon.setText(text);
        this.snackBarIcon.setForeground(color);
        this.snackBarIcon.addMouseListener(action);
    }

    public void setAction(Icon icon, MouseListener action) {
        if(this.snackBarIcon == null){
            this.snackBarIcon = new JLabel();
            super.add(snackBarIcon, BorderLayout.EAST);
            this.snackBar.initStyle();
            this.snackBarIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
            super.updateUI();
        }
        this.snackBarIcon.setIcon(icon);
        this.snackBarIcon.addMouseListener(action);
    }

    public void setIcon(Icon icon) {
        if(this.snackBarIcon == null){
            this.snackBarIcon = new JLabel();
            super.add(snackBarIcon, BorderLayout.EAST);
            this.snackBar.initStyle();
            this.snackBarIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
            super.updateUI();
        }
        this.snackBarIcon.setIcon(icon);
    }

    public void setText(String text) {
        this.snackBarText.setText(text);
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
