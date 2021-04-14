package org.material.component.swingsnackbar.twoactions;

import java.awt.*;
import javax.swing.*;
import org.material.component.swingsnackbar.SnackBar;

public class TwoActionSnackbar extends SnackBar {

  public static TwoActionSnackbar make(
      Window contextView, String withMessage, String iconText, String secondIconText) {
    if (contextView == null
        || (withMessage == null || withMessage.isEmpty())) { // TODO check also secondIconText
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
    return new TwoActionSnackbar(contextView, withMessage, iconText, secondIconText);
  }

  public static TwoActionSnackbar make(
      Window contextView, String withMessage, String iconText, Icon secondIconText) {
    if (contextView == null
        || (withMessage == null || withMessage.isEmpty())) { // TODO check also secondIconText
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
    return new TwoActionSnackbar(
        contextView, withMessage, new JLabel(iconText), new JLabel(secondIconText));
  }

  public TwoActionSnackbar(Window frame, String message, Icon icon, Icon secondIcon) {
    this(frame, message, new JLabel(icon), new JLabel(secondIcon));
  }

  public TwoActionSnackbar(Window frame, String message, String iconText, String secondText) {
    this(frame, message, new JLabel(iconText), new JLabel(secondText));
  }

  public TwoActionSnackbar(Window frame, String message, JLabel icon, JLabel secondIcon) {
    super(frame, message, icon);
    super.snackBarContainer = new TwoActionContainer(this, new JLabel(message), icon, secondIcon);
    super.snackBarContainer.inflateContent();
    ((JComponent) super.snackBarContainer).updateUI();
    super.revalidate();
    super.setContentPane((Container) super.snackBarContainer);
  }
}
