package io.swingsnackbar;

import io.swingsnackbar.model.SnackBarContainer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

/**
 * I can add an panel with personal UI?? What do you think?
 * <p>
 * Reference implementation style https://material.io/components/snackbars#
 *
 * The api is the same to https://material.io/develop/android/components/snackbar/
 * @author https://github.com/vincenzopalazzo
 */
public class SnackBar extends JDialog {

    public static final int LENGTH_LONG = 8000;
    public static final int SHORT_LONG = 5000;
    private static final int LENGTH_INDEFINITE = -1;

    public static SnackBar make(JFrame contextView, String withMessage, int duration){
        //TODO check on the value
        SnackBar snackBar = new SnackBar(contextView, withMessage);
        snackBar.setDuration(duration);
        return snackBar;
    }

    private SnackBarContainer snackBarContainer;
    protected boolean running = false;
    protected int duration = LENGTH_INDEFINITE; //DEFAULT value
    protected SnackBarPosition position = SnackBarPosition.BOTTOM; //Default value

    public SnackBar(JFrame frame, String message, Icon icon) {
        this(frame, message, new JLabel(icon));
    }

    public SnackBar(JFrame frame, String message, JLabel icon) {
        super(frame);
        this.snackBarContainer = new SnackBarContainer(this, new JLabel(message), icon);
        this.snackBarContainer.inflateContent();
        super.setContentPane(snackBarContainer);
        setModal(false);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        initStyle();
    }


    public SnackBar(JFrame frame, String message) {
        super(frame);
        this.snackBarContainer = new SnackBarContainer(this, new JLabel(message));
        this.snackBarContainer.inflateContent();
        super.setContentPane(snackBarContainer);
        setModal(false);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        initStyle();
    }

    /**
     * With this method you can set the style inside the component.
     * I'm thinking to move JPanel component inside an personal method and use the dialog only how container
     */
    public void initStyle() {
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

    protected void doCalculatePosition() {
        int x;
        int y;
        switch(this.position){
            case TOP:
                x = (getOwner().getX() + ((getOwner().getWidth() - this.getWidth()) / 2));
                y = (getOwner().getY() + 30);
                break;
            case TOP_LEFT:
                if(isPossibleWith()) {
                    x = (getOwner().getWidth() - 350);
                    y = (getOwner().getY() + 30);
                }else{
                    x = (getOwner().getX() + ((getOwner().getWidth() - this.getWidth()) / 2));
                    y = (getOwner().getY() + getOwner().getHeight() - 100);
                }
                break;
            case TOP_RIGHT:
                if(isPossibleWith()) {
                    x = (getOwner().getX() + 10);
                    y = (getOwner().getY() + 30);
                }else{
                    x = (getOwner().getX() + ((getOwner().getWidth() - this.getWidth()) / 2));
                    y = (getOwner().getY() + getOwner().getHeight() - 100);
                }
                break;
            case BOTTOM_LEFT:
                if(isPossibleWith()) {
                    x = (getOwner().getWidth() - 350);
                    y = (getOwner().getHeight() - 85);
                }else{
                    x = (getOwner().getX() + ((getOwner().getWidth() - this.getWidth()) / 2));
                    y = (getOwner().getY() + getOwner().getHeight() - 100);
                }
                break;
            case BOTTOM_RIGHT:
                if(isPossibleWith()) {
                    x = (getOwner().getX() + 10);
                    y = (getOwner().getHeight() - 85);
                }else{
                    x = (getOwner().getX() + ((getOwner().getWidth() - this.getWidth()) / 2));
                    y = (getOwner().getY() + getOwner().getHeight() - 100);
                }
                break;
            default:
                x = (getOwner().getX() + ((getOwner().getWidth() - this.getWidth()) / 2));
                y = (getOwner().getY() + getOwner().getHeight() - 100);
        }
        Point point = new Point(x, y);
        super.setLocation(point);
    }

    private boolean isPossibleWith() {
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

    public SnackBar setBorder(Border border) {
        if (border == null) {
            throw new IllegalArgumentException("Border null");
        }
        this.snackBarContainer.setBorder(border);
        return this;
    }

    public SnackBar setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public SnackBar setAction(String text, MouseListener action){
        boolean textIsInvalid = (text == null || text.isEmpty());
        boolean actionIsNull = (action == null);
        boolean areInsecure =  textIsInvalid || actionIsNull;
        if(areInsecure){
            String message = "\n";
            if(textIsInvalid) message += "- Text null.\n";
            if(actionIsNull)  message +="- Action is null\n";
            throw new IllegalArgumentException(message);
        }
        this.snackBarContainer.setAction(text, action);
        return this;
    }

    public SnackBar setActionTextColor(String text, Color color, MouseListener action){
        boolean textIsInvalid = (text == null || text.isEmpty());
        boolean colorIsNull = (color == null);
        boolean actionIsNull = (action == null);
        boolean areInsecure =  textIsInvalid || colorIsNull || actionIsNull;
        if(areInsecure){
            String message = "\n";
            if(textIsInvalid) message += "- Text null.\n";
            if(colorIsNull)  message +="- Color is null\n";
            if(actionIsNull)  message +="- Action is null\n";
            throw new IllegalArgumentException(message);
        }
        this.snackBarContainer.setAction(text, color, action);
        return this;
    }

    public SnackBar setAction(Icon icon, MouseListener action){
        boolean iconIsNull = (icon == null);
        boolean actionIsNull = (action == null);
        boolean areInsecure =  iconIsNull || actionIsNull;
        if(areInsecure){
            String message = "\n";
            if(iconIsNull) message += "- Icon is null.\n";
            if(actionIsNull)  message +="- Action is null\n";
            throw new IllegalArgumentException(message);
        }
        this.snackBarContainer.setAction(icon, action);
        return this;
    }

    public SnackBar setPosition(SnackBarPosition position){
        this.position = position;
        this.doCalculatePosition();
        return this;
    }


    public boolean isRunning() {
        return running;
    }

    public void dismiss(){
        super.setVisible(false);
        this.running = false;
    }

    public SnackBar run() {
        this.run(SnackBarPosition.BOTTOM);
        return this;
    }

    public void run(SnackBarPosition position) {
        if (!running) {
            running = true;
            this.position = position;
            doCalculatePosition();
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
                    if(isVisible()){
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
    public enum SnackBarPosition {
        TOP(),
        BOTTOM(),
        TOP_LEFT(),
        TOP_RIGHT(),
        BOTTOM_RIGHT(),
        BOTTOM_LEFT()
    }
}
