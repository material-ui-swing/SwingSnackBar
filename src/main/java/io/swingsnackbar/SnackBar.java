package io.swingsnackbar;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

/**
 * I can add an panel with personal UI?? What do you think?
 */
public class SnackBar extends JDialog {

    /**
     * This is lacal
     */
    private static final String PREFIX_UI_MANAGER = "SnackBar.";

    protected static final int LONG_TIME = 45;
    protected static final int SHORT_TIME = 10;

    private JLabel textLabel;

    public SnackBar(Frame owner, String textLabel, Icon icon) {
        super(owner);
        if (textLabel != null && textLabel.isEmpty()) {
            this.textLabel = new JLabel(textLabel);
            if (icon != null) {
                this.setIcon(icon);
            }
        }
        setModal(false);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        initStyle();
    }

    public SnackBar(Frame owner, String textLabel) {
        this(owner, textLabel, null);
    }

    //Style method
    protected void initStyle() {

        Graphics graphics = getGraphics();
        if(graphics != null){
            graphics = UtilUI.getAliasedGraphics(graphics);
        }
        setPreferredSize(new Dimension(50, 30));
        setMinimumSize(new Dimension(50, 30));
        setMaximumSize(new Dimension(50, 30));
        setSize(new Dimension(50, 30));
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setFocusableWindowState(false);
        setModalityType(ModalityType.MODELESS);

        setBackground( new ColorUIResource(55, 58, 60));
        getContentPane().setBackground(new ColorUIResource(55, 58, 60));
        getRootPane().setBackground(new ColorUIResource(55, 58, 60));
    }



    public void setText(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Invalid test");
        }
        textLabel.setText(text);
    }

    public void setIcon(Icon icon) {
        if (icon == null) {
            throw new IllegalArgumentException("Invalid icon");
        }
        textLabel.setIconTextGap(15);
        textLabel.setIcon(icon);
    }
}
