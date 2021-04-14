package org.material.component.swingsnackbar.model;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import org.material.component.swingsnackbar.action.AbstractSnackBarAction;

public interface ISnackBarContainer {

  void inflateContent();

  void setIcon(Icon icon);

  void setText(String text);

  void setBackground(Color bg);

  JLabel getSnackBarText();

  JLabel getSnackBarIcon();

  void setBorder(Border border);

  void setGap(int gap);

  void setIconTextColor(Color color);

  void setIconTextStyle(Font font);

  void setAction(AbstractSnackBarAction action);

  Dimension getDimension();
}
