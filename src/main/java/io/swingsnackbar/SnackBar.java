package io.swingsnackbar;

import io.swingsnackbar.ui.BasicSnackBarUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 * I can add an panel with personal UI?? What do you think?
 *
 * Reference implementation style https://material.io/components/snackbars#
 *
 * @author https://github.com/vincenzopalazzo
 */
public class SnackBar extends JDialog {

    /**
     * This is lacal
     */
    private static final String PREFIX_UI_MANAGER = "SnackBar.";

    protected static final int LONG_TIME = 45;
    protected static final int SHORT_TIME = 10;

    //TODO I can move this element inside the other component with an personal UI
    //and use the dialog only how content.
    private JPanel content;
    private JLabel textLabel;

    public SnackBar(Frame owner, String textLabel, Icon icon) {
        super(owner);
        this.content = new JPanel();
        this.content.setUI(new BasicSnackBarUI(this));
        this.content.setLayout(new BorderLayout());
        if (textLabel != null && !textLabel.isEmpty()) {
            this.textLabel = new JLabel(textLabel);
            if (icon != null) {
                this.textLabel.setHorizontalTextPosition(SwingConstants.LEFT);
                this.setIcon(icon);
            }
            this.content.add(this.textLabel);

        }
        setContentPane(content);
        setModal(false);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        initStyle();
    }

    public SnackBar(Frame owner, String textLabel) {
        this(owner, textLabel, null);
    }

    /**
     * With this method you can set the style inside the component.
     * I'm thinking to move JPanel component inside an personal method and use the dialog only how container
     */
    protected void initStyle() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new Rectangle2D.Float(0f, 0f, getWidth(), getHeight()));
            }
        });
        Dimension dimension = new Dimension(150, 50);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setSize(dimension);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setFocusableWindowState(false);
        setModalityType(ModalityType.MODELESS);
        //setBackground(new ColorUIResource(55, 58, 60));
        //getRootPane().setBackground(new ColorUIResource(55, 58, 60));
    }

    //Component API
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

    public void setBorder(Border border){
        if(border == null){
            throw new IllegalArgumentException("Border null");
        }
        this.content.setBorder(border);
    }
}
