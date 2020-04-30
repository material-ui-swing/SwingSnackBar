package io.swingsnackbar;

import io.swingsnackbar.model.SnackBarContainer;
import io.swingsnackbar.ui.BasicSnackBarUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;

/**
 * I can add an panel with personal UI?? What do you think?
 * <p>
 * Reference implementation style https://material.io/components/snackbars#
 *
 * @author https://github.com/vincenzopalazzo
 */
public class SnackBar extends JDialog {

    /**
     * This variable use the personal prefix, it isn't inherited from JDialog
     */
    private static final String PREFIX_UI_MANAGER = "SnackBar.";
    private static SnackBar instance;

    /**
     * Only test
     * param withFrame
     * param withMessage
     */
    public static void doShowToast(JFrame withFrame, String withMessage, Icon withIcon, SnackBarPosition to) {
        if (withFrame == null || (withMessage == null || withMessage.isEmpty())) {
            throw new IllegalArgumentException("TODO complex message, for the moment the function arguments are null");
        }
        if (instance == null) {
            instance = new SnackBar(withFrame, withMessage, withIcon);
        }
        instance.run(to);
    }

    public static void doShowToast(JFrame withFrame, String withMessage, JLabel withIcon, SnackBarPosition to) {
        if (withFrame == null || (withMessage == null || withMessage.isEmpty())) {
            throw new IllegalArgumentException("TODO complex message, for the moment the function arguments are null");
        }
        if (instance == null) {
            instance = new SnackBar(withFrame, withMessage, withIcon);
        }
        instance.run(to);
    }

    //TODO I can move this element inside the other component with an personal UI
    //and use the dialog only how content.
    private SnackBarContainer snackBarContainer;

    protected JPanel content;
    protected JLabel textLabel;
    protected Timer timerVisibleSnack;
    protected boolean running = false;

    public SnackBar(JFrame frame, String message, Icon icon) {
        this(frame, message, new JLabel(icon));
    }

    public SnackBar(JFrame frame, String message, JLabel icon) {
        super(frame);
        this.snackBarContainer = new SnackBarContainer(new JLabel(message), icon);
        this.snackBarContainer.inflateContent();
        super.setContentPane(snackBarContainer);
        setModal(false);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        initStyle();
    }

    public SnackBar(Frame owner, String textLabel, Icon icon) {
        super(owner);
        this.content = new JPanel();
        this.content.setUI(new BasicSnackBarUI());
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
        Dimension dimension = this.snackBarContainer.getDimension();
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

    protected void setPosition(SnackBarPosition position) {
        int x;
        int y;
        switch(position){
            case TOP:
                x = (getOwner().getX() + ((getOwner().getWidth() - this.getWidth()) / 2));
                y = (getOwner().getY() + 30);
                break;
            default:
                x = (getOwner().getX() + ((getOwner().getWidth() - this.getWidth()) / 2));
                y = (getOwner().getY() + getOwner().getHeight() - 100);
        }
        Point point = new Point(x, y);
        super.setLocation(point);
    }

    //Component API
    public void setText(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Invalid text");
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

    public void setBorder(Border border) {
        if (border == null) {
            throw new IllegalArgumentException("Border null");
        }
        this.content.setBorder(border);
    }

    public void run() {
        this.run(SnackBarPosition.BOTTOM);
    }

    public void run(SnackBarPosition position) {
        if (!running) {
            running = true;
            setPosition(position);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    setVisible(true);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    setVisible(false);
                    running = false;
                }
            }).start();
        }
    }

    /**
     * Default SnackBar position.
     */
    public enum SnackBarPosition {
        TOP(),
        BOTTOM(),
        TOP_LEFT(),
        TOP_RIGHT(),
        BOTTOM_RIGHT(),
        BOTTOM_LEFT()
    }
}
