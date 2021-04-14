package org.material.component.swingsnackbar.jmars;

import java.awt.*;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import org.material.component.swingsnackbar.SnackBar;
import org.material.component.swingsnackbar.model.SnackBarContainer;

public class JMarsSnackBarContainer extends SnackBarContainer {

  protected JLabel leftIcon;

  public JMarsSnackBarContainer(SnackBar snackBar) {
    super(snackBar);
  }

  public JMarsSnackBarContainer(SnackBar snackBar, JLabel snackBarText, JLabel snackBarIcon) {
    super(snackBar, snackBarText, snackBarIcon);
  }

  public JMarsSnackBarContainer(SnackBar snackBar, JLabel snackBarText, Icon snackBarIcon) {
    super(snackBar, snackBarText, snackBarIcon);
  }

  public JMarsSnackBarContainer(SnackBar snackBar, JLabel snackBarText) {
    super(snackBar, snackBarText);
  }

  public void setLeftIcon(JLabel leftIcon) {
    boolean isNewLayout = this.leftIcon == null;
    this.leftIcon = leftIcon;
    this.leftIcon.setBorder(new EmptyBorder(5, gap / 4, 5, gap / 4));
    if (isNewLayout) this.inflateContent();
    this.updateUI();
  }

  @Override
  public void inflateContent() {
    super.inflateContent();
    this.snackBarIcon.setBorder(new EmptyBorder(5, gap / 4, 5, gap / 4));
    if (leftIcon != null) this.add(this.leftIcon, BorderLayout.WEST);
  }

  @Override
  public Dimension getDimension() {
    // the width is calculate with the knowledge about the text and
    // the icon to make the standard action with the SnackBar
    // whi need to take in consideration the additional width component
    // that this container is adding to the snackbar
    int width = this.calculateMinimumDimension();
    if (this.leftIcon != null) {
      width += this.leftIcon.getIcon().getIconWidth() + gap; // The gap is only extra space
    }
    return new Dimension(width, 50);
  }
}
