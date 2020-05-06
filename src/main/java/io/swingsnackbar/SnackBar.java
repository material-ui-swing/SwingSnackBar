package io.swingsnackbar;

import io.swingsnackbar.action.AbstractSnackBarAction;
import io.swingsnackbar.model.SnackBarContainer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Reference implementation style https://material.io/components/snackbars#
 * <p>
 * The api is the same to https://material.io/develop/android/components/snackbar/
 *
 * This component try to reproduce the google material SnackBar component.
 *
 * @author https://github.com/vincenzopalazzo
 */
public class SnackBar extends JDialog {

    public static final int LENGTH_LONG = 8000;
    public static final int LENGTH_SHORT = 5000;
    public static final int LENGTH_INDEFINITE = -1;
    public static final SnackBarPosition TOP = SnackBarPosition.TOP;
    public static final SnackBarPosition TOP_LEFT = SnackBarPosition.TOP_LEFT;
    public static final SnackBarPosition TOP_RIGHT = SnackBarPosition.TOP_RIGHT;
    public static final SnackBarPosition BOTTOM = SnackBarPosition.BOTTOM;
    public static final SnackBarPosition BOTTOM_LEFT = SnackBarPosition.BOTTOM_LEFT;
    public static final SnackBarPosition BOTTOM_RIGHT = SnackBarPosition.BOTTOM_RIGHT;

    protected static int WINDOW_RADIUS = UIManager.getInt("SnackBar.arc");

    public static SnackBar make(Window contextView, String withMessage, String iconText) {
        if(contextView == null || (withMessage == null || withMessage.isEmpty())){
            String message = "\n";
            if(contextView == null){
                message = "- Context View null, this propriety should be an instance of Window swing component\n";
            }
            if((withMessage == null || withMessage.isEmpty())){
                if(withMessage == null){
                    message = "- The message is null, this propriety should be contains any " +
                            "text because is the text content inside the Snackbar\n";
                }else{
                    message = "- The message is empty, this propriety should be contains any " +
                            "text because is the text content inside the Snackbar\n";
                }
            }
            throw new IllegalArgumentException(message);
        }
        return new SnackBar(contextView, withMessage, iconText);
    }

    public static SnackBar make(Window contextView, String withMessage, Icon icon) {
        if(contextView == null || (withMessage == null || withMessage.isEmpty())){
            String message = "\n";
            if(contextView == null){
                message = "- Context View null, this propriety should be an instance of Window swing component\n";
            }
            if((withMessage == null || withMessage.isEmpty())){
                if(withMessage == null){
                    message = "- The message is null, this propriety should be contains any " +
                            "text because is the text content inside the Snackbar\n";
                }else{
                    message = "- The message is empty, this propriety should be contains any " +
                            "text because is the text content inside the Snackbar\n";
                }
            }
            throw new IllegalArgumentException(message);
        }
        return new SnackBar(contextView, withMessage, icon);
    }

    private SnackBarContainer snackBarContainer;
    protected boolean running = false;
    protected int duration = LENGTH_INDEFINITE; //DEFAULT value
    protected SnackBarPosition position = SnackBarPosition.BOTTOM; //Default value
    protected int marginTop = 25;
    protected int marginBottom = 25;
    protected int marginLeft = 25;
    protected int marginRight = 25;

    public SnackBar(Window frame, String message, Icon icon) {
        this(frame, message, new JLabel(icon));
    }

    public SnackBar(Window frame, String message, String iconText) {
        this(frame, message, new JLabel(iconText));
    }

    public SnackBar(Window frame, String message, JLabel icon) {
        super(frame);
        this.snackBarContainer = new SnackBarContainer(this, new JLabel(message), icon);
        this.snackBarContainer.inflateContent();
        super.setContentPane(snackBarContainer);
        setModal(false);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        initStyle();
        super.getOwner().addWindowStateListener(new WindowEventDimensionChanged());
    }

    @Override
    public void revalidate() {
        super.revalidate();
        this.setPersonalDimension();
    }

    /**
     * With this method you can set the style inside the component.
     * I'm thinking to move JPanel component inside an personal method and use the dialog only how container
     */
    public void initStyle() {
        this.setPersonalDimension();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), WINDOW_RADIUS, WINDOW_RADIUS));
            }
        });
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setFocusableWindowState(false);
        setModalityType(ModalityType.MODELESS);
        Color background = UIManager.getColor("SnackBar.background") == null
                ? new ColorUIResource(55, 58, 60)
                : UIManager.getColor("SnackBar.background");
        setBackground(background);
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        getRootPane().setBackground(background);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    }

    protected void setPersonalDimension(){
        Dimension dimension = this.snackBarContainer.getDimension();
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setSize(dimension);
    }

    protected void doCalculatePosition() {
        int x;
        int y;
        switch (this.position) {
            case TOP:
                x = (getOwner().getX() + ((getOwner().getWidth() - this.getWidth()) / 2));
                y = (getOwner().getY() + this.getHeight() + marginTop);
                break;
            case TOP_RIGHT:
                if (isPossibleWith()) {
                    x = (getOwner().getX() + getOwner().getWidth() - getWidth() - marginRight);
                    y = (getOwner().getY() + this.getHeight() + marginTop);
                } else {
                    x = (getOwner().getX() + ((getOwner().getWidth() - this.getWidth()) / 2));
                    y = (getOwner().getY() + this.getHeight() + marginTop);
                }
                break;
            case TOP_LEFT:
                if (isPossibleWith()) {
                    x = (getOwner().getX() + marginLeft);
                    y = (getOwner().getY() + this.getHeight() + marginTop);
                } else {
                    x = (getOwner().getX() + ((getOwner().getWidth() - this.getWidth()) / 2));
                    y = (getOwner().getY() + this.getHeight() + marginTop);
                }
                break;
            case BOTTOM_RIGHT:
                if (isPossibleWith()) {
                    x = (getOwner().getX() + getOwner().getWidth() - getWidth() - marginRight);
                    y = (getOwner().getY() + getOwner().getHeight() - getHeight() - marginBottom);
                } else {
                    x = (getOwner().getX() + ((getOwner().getWidth() - this.getWidth()) / 2));
                    y = (getOwner().getY() + getOwner().getHeight() - this.getHeight() - marginBottom);
                }
                break;
            case BOTTOM_LEFT:
                if (isPossibleWith()) {
                    x = (getOwner().getX() + marginLeft);
                    y = (getOwner().getY() + getOwner().getHeight() - getHeight() - marginBottom);
                } else {
                    x = (getOwner().getX() + ((getOwner().getWidth() - this.getWidth()) / 2));
                    y = (getOwner().getY() + getOwner().getHeight() - this.getHeight() - marginBottom);
                }
                break;
            default:
                x = (getOwner().getX() + ((getOwner().getWidth() - this.getWidth()) / 2));
                y = (getOwner().getY() + getOwner().getHeight() - this.getHeight() - marginBottom);
        }
        Point point = new Point(x, y);
        super.setLocation(point);
    }

    protected boolean isPossibleWith() {
        System.out.println("Root wight: " + getOwner().getWidth());
        System.out.println("SnackBar wight: " + this.getWidth() * 2);
        return getOwner().getWidth() > (this.getWidth() * 2);
    }

    //Component API
    public SnackBar setText(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Invalid text");
        }
        this.snackBarContainer.setText(text);
        return this;
    }

    public SnackBar setIcon(Icon icon) {
        if (icon == null) {
            throw new IllegalArgumentException("Invalid icon");
        }
        this.snackBarContainer.setIcon(icon);
        return this;
    }

    public SnackBar setSnackBarBackground(Color color){
        super.setBackground(color);
        super.getRootPane().setBackground(color);
        this.snackBarContainer.setBackground(color);
        return this;
    }

    public SnackBar setSnackBarForeground(Color color){
        this.snackBarContainer.getSnackBarText().setForeground(color);
        return this;
    }

    public SnackBar setBorder(Border border) {
        if (border == null) {
            throw new IllegalArgumentException("Border null");
        }
        this.snackBarContainer.setBorder(border);
        return this;
    }

    public SnackBar setIconTextStyle(Font font){
        if(font == null){
            throw new IllegalArgumentException("- Font null\n");
        }
        this.snackBarContainer.setIconTextStyle(font);
        return this;
    }

    public SnackBar setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    protected SnackBar getInstance(){
        return this;
    }

    public SnackBar setAction(AbstractSnackBarAction action) {
        boolean actionIsNull = (action == null);
        if (action == null) {
            throw new IllegalArgumentException("- Action is null\n");
        }
        this.snackBarContainer.setAction(action);
        return this;
    }

   public SnackBar setIconTextColor(Color color){
        if(color == null){
            throw new IllegalArgumentException("- Color is null\n");
        }
        this.snackBarContainer.setIconTextColor(color);
        return this;
   }

    public SnackBar setPosition(SnackBarPosition position) {
        this.position = position;
        this.doCalculatePosition();
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public SnackBarPosition getPosition() {
        return position;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public SnackBar setMarginTop(int marginTop) {
        this.marginTop = marginTop;
        return this;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public SnackBar setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
        return this;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public SnackBar setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
        return this;
    }

    public SnackBar setGap(int gap){
        if(gap < 0){
            throw new IllegalArgumentException("\n- Gap should be positive or equal to 0");
        }
        this.snackBarContainer.setGap(gap);
        return this;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public SnackBar setMarginRight(int marginRight) {
        this.marginRight = marginRight;
        return this;
    }

    public boolean isRunning() {
        return running;
    }

    public void dismiss() {
        this.running = false;
        super.setVisible(this.running);
    }

    public SnackBar run() {
        this.run(this.position);
        return this;
    }

    //TODO refactoring with timer and not with a Thread
    public void run(SnackBarPosition position) {
        if (!running) {
            running = true;
            this.position = position;
            doCalculatePosition();

            if(duration == LENGTH_INDEFINITE){
                setVisible(true);
                return;
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    setVisible(true);
                    try {
                        Thread.sleep(duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //If isn't call dismiss method before to end
                    if (isVisible()) {
                        setVisible(false);
                        running = false;
                    }
                }
            }).start();
        }
    }

    /**
     * Default SnackBar position.
     */
    protected enum SnackBarPosition {
        TOP(),
        BOTTOM(),
        TOP_LEFT(),
        TOP_RIGHT(),
        BOTTOM_RIGHT(),
        BOTTOM_LEFT()
    }

    protected class WindowEventDimensionChanged implements WindowStateListener {

        @Override
        public void windowStateChanged(WindowEvent e) {
            int oldState = e.getOldState();
            int newState = e.getNewState();

            if ((oldState & Frame.ICONIFIED) == 0 && (newState & Frame.ICONIFIED) != 0) {
                getInstance().dismiss();
            } else if ((oldState & Frame.MAXIMIZED_BOTH) == 0 && (newState & Frame.MAXIMIZED_BOTH) != 0) {
                getInstance().dismiss();
            } else if ((oldState & Frame.MAXIMIZED_BOTH) != 0 && (newState & Frame.MAXIMIZED_BOTH) == 0) {
                getInstance().dismiss();
            }
        }
    }
}
