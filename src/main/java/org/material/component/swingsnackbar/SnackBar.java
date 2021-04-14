/**
 * MIT License
 *
 * <p>Copyright (c) 2019-2020 Vincenzo Palazzo vincenzopalazzo1996@gmail.com
 *
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * <p>The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.material.component.swingsnackbar;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import org.material.component.swingsnackbar.action.AbstractSnackBarAction;
import org.material.component.swingsnackbar.model.ISnackBarContainer;
import org.material.component.swingsnackbar.model.SnackBarContainer;

/**
 * Reference implementation style https://material.io/components/snackbars#
 *
 * <p>The api is the same to https://material.io/develop/android/components/snackbar/
 *
 * <p>This component try to reproduce the google material SnackBar component.
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
    if (contextView == null || (withMessage == null || withMessage.isEmpty())) {
      String message = "\n";
      if (contextView == null) {
        message =
            "- Context View null, this propriety should be an instance of Window swing component\n";
      }
      if ((withMessage == null || withMessage.isEmpty())) {
        if (withMessage == null) {
          message =
              "- The message is null, this propriety should be contains any "
                  + "text because is the text content inside the Snackbar\n";
        } else {
          message =
              "- The message is empty, this propriety should be contains any "
                  + "text because is the text content inside the Snackbar\n";
        }
      }
      throw new IllegalArgumentException(message);
    }
    return new SnackBar(contextView, withMessage, iconText);
  }

  public static SnackBar make(Window contextView, String withMessage, JLabel labelWithIconOrText) {
    if (contextView == null || (withMessage == null || withMessage.isEmpty())) {
      String message = "\n";
      if (contextView == null) {
        message =
            "- Context View null, this propriety should be an instance of Window swing component\n";
      }
      if ((withMessage == null || withMessage.isEmpty())) {
        if (withMessage == null) {
          message =
              "- The message is null, this propriety should be contains any "
                  + "text because is the text content inside the Snackbar\n";
        } else {
          message =
              "- The message is empty, this propriety should be contains any "
                  + "text because is the text content inside the Snackbar\n";
        }
      }
      throw new IllegalArgumentException(message);
    }
    return new SnackBar(contextView, withMessage, labelWithIconOrText);
  }

  public static SnackBar make(Window contextView, String withMessage, Icon icon) {
    if (contextView == null || (withMessage == null || withMessage.isEmpty())) {
      String message = "\n";
      if (contextView == null) {
        message =
            "- Context View null, this propriety should be an instance of Window swing component\n";
      }
      if ((withMessage == null || withMessage.isEmpty())) {
        if (withMessage == null) {
          message =
              "- The message is null, this propriety should be contains any "
                  + "text because is the text content inside the Snackbar\n";
        } else {
          message =
              "- The message is empty, this propriety should be contains any "
                  + "text because is the text content inside the Snackbar\n";
        }
      }
      throw new IllegalArgumentException(message);
    }
    return new SnackBar(contextView, withMessage, icon);
  }

  protected ISnackBarContainer snackBarContainer;
  protected boolean running = false;
  protected int duration = LENGTH_INDEFINITE; // DEFAULT value
  protected SnackBarPosition position = SnackBarPosition.BOTTOM; // Default value
  protected int marginTop = 25;
  protected int marginBottom = 25;
  protected int marginLeft = 25;
  protected int marginRight = 25;
  protected Timer showTimer;

  public SnackBar(Window frame, String message, Icon icon) {
    this(frame, message, new JLabel(icon));
  }

  public SnackBar(Window frame, String message, String iconText) {
    this(frame, message, new JLabel(iconText));
  }

  public SnackBar(Window frame, String message, JLabel icon) {
    super(frame);
    this.snackBarContainer = this.getSnackBarContainer(message, icon);
    this.snackBarContainer.inflateContent();
    this.inflateSnackBar();
  }

  @Override
  public void paint(Graphics g) {
    Graphics2D graphics2D = (Graphics2D) g;
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    super.paint(graphics2D);
  }

  @Override
  public void revalidate() {
    super.revalidate();
    this.setPersonalDimension();
  }

  protected void inflateSnackBar() {
    super.setContentPane((Container) snackBarContainer);
    setModal(false);
    getRootPane().setWindowDecorationStyle(JRootPane.NONE);
    initStyle();
    super.getOwner().addWindowStateListener(new WindowEventDimensionChanged());
  }

  protected ISnackBarContainer getSnackBarContainer(String message, JLabel icon) {
    return new SnackBarContainer(this, new JLabel(message), icon);
  }

  /**
   * With this method you can set the style inside the component. I'm thinking to move JPanel
   * component inside an personal method and use the dialog only how container
   */
  public void initStyle() {
    this.setPersonalDimension();
    addComponentListener(
        new ComponentAdapter() {
          @Override
          public void componentResized(ComponentEvent e) {
            setShape(
                new RoundRectangle2D.Double(
                    0, 0, getWidth(), getHeight(), WINDOW_RADIUS, WINDOW_RADIUS));
          }
        });
    getRootPane().setWindowDecorationStyle(JRootPane.NONE);
    setAlwaysOnTop(true);
    setUndecorated(true);
    setFocusableWindowState(false);
    setModalityType(ModalityType.MODELESS);
    Color background =
        UIManager.getColor("SnackBar.background") == null
            ? new ColorUIResource(55, 58, 60)
            : UIManager.getColor("SnackBar.background");
    setBackground(background);
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    getRootPane().setBackground(background);
    getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
  }

  protected void setPersonalDimension() {
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
    return getOwner().getWidth() > (this.getWidth() * 2);
  }

  // Component API
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

  public SnackBar setSnackBarBackground(Color color) {
    super.setBackground(color);
    super.getRootPane().setBackground(color);
    this.snackBarContainer.setBackground(color);
    return this;
  }

  public SnackBar setSnackBarForeground(Color color) {
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

  public SnackBar setIconTextStyle(Font font) {
    if (font == null) {
      throw new IllegalArgumentException("- Font null\n");
    }
    this.snackBarContainer.setIconTextStyle(font);
    return this;
  }

  public SnackBar setDuration(int duration) {
    this.duration = duration;
    return this;
  }

  protected SnackBar getInstance() {
    return this;
  }

  public SnackBar setAction(AbstractSnackBarAction action) {
    if (action == null) {
      throw new IllegalArgumentException("- Action is null\n");
    }
    this.snackBarContainer.setAction(action);
    return this;
  }

  public SnackBar refresh() {
    this.doCalculatePosition();
    return this;
  }

  public SnackBar setIconTextColor(Color color) {
    if (color == null) {
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

  public SnackBarPosition getPosition() {
    return position;
  }

  public SnackBar setMarginTop(int marginTop) {
    this.marginTop = marginTop;
    return this;
  }

  public SnackBar setMarginBottom(int marginBottom) {
    this.marginBottom = marginBottom;
    return this;
  }

  public SnackBar setMarginLeft(int marginLeft) {
    this.marginLeft = marginLeft;
    return this;
  }

  public SnackBar setGap(int gap) {
    if (gap < 0) {
      throw new IllegalArgumentException("\n- Gap should be positive or equal to 0");
    }
    this.snackBarContainer.setGap(gap);
    return this;
  }

  public SnackBar setMarginRight(int marginRight) {
    this.marginRight = marginRight;
    return this;
  }

  public JLabel getSnackBarIconIcon() {
    return snackBarContainer.getSnackBarIcon();
  }

  public int getMarginTop() {
    return marginTop;
  }

  public int getMarginBottom() {
    return marginBottom;
  }

  public int getMarginLeft() {
    return marginLeft;
  }

  public int getMarginRight() {
    return marginRight;
  }

  public String getText() {
    return this.snackBarContainer.getSnackBarText().getText();
  }

  public int getDuration() {
    return duration;
  }

  public boolean isRunning() {
    return running;
  }

  public void dismiss() {
    this.running = false;
    super.setVisible(this.running);
    if (this.showTimer != null) {
      this.showTimer.stop();
    }
  }

  public SnackBar run() {
    this.run(this.position);
    return this;
  }

  public void run(SnackBarPosition position) {
    if (!running) {
      running = true;
      this.position = position;
      doCalculatePosition();
      setVisible(true);
      if (duration == LENGTH_INDEFINITE) {
        return;
      }
      showTimer = new Timer(duration, new ActionShowSnackBar());
      showTimer.start();
    }
  }

  protected enum SnackBarPosition {
    TOP(),
    BOTTOM(),
    TOP_LEFT(),
    TOP_RIGHT(),
    BOTTOM_RIGHT(),
    BOTTOM_LEFT()
  }

  protected class ActionShowSnackBar extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
      dismiss();
    }
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
