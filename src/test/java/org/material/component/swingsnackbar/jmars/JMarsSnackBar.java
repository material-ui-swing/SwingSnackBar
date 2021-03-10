package org.material.component.swingsnackbar.jmars;

import org.material.component.swingsnackbar.SnackBar;
import org.material.component.swingsnackbar.model.ISnackBarContainer;

import javax.swing.*;
import java.awt.*;

public class JMarsSnackBar extends SnackBar {

    public static JMarsSnackBar make(Window contextView, String withMessage, Icon icon) {
        if (contextView == null || (withMessage == null || withMessage.isEmpty())) {
            String message = "\n";
            if (contextView == null) {
                message = "- Context View null, this propriety should be an instance of Window swing component\n";
            }
            if ((withMessage == null || withMessage.isEmpty())) {
                if (withMessage == null) {
                    message = "- The message is null, this propriety should be contains any " +
                            "text because is the text content inside the Snackbar\n";
                } else {
                    message = "- The message is empty, this propriety should be contains any " +
                            "text because is the text content inside the Snackbar\n";
                }
            }
            throw new IllegalArgumentException(message);
        }
        return new JMarsSnackBar(contextView, withMessage, icon);
    }

    public JMarsSnackBar(Window frame, String message, Icon icon) {
        super(frame, message, icon);
    }

    public JMarsSnackBar(Window frame, String message, String iconText) {
        super(frame, message, iconText);
    }

    public JMarsSnackBar(Window frame, String message, JLabel icon) {
        super(frame, message, icon);
    }

    @Override
    protected ISnackBarContainer getSnackBarContainer(String message, JLabel icon) {
        return new JMarsSnackBarContainer(this, new JLabel(message), icon);
    }

    public JMarsSnackBar setLeftIcon(Icon icon) {
        ((JMarsSnackBarContainer) this.snackBarContainer).setLeftIcon(new JLabel(icon));
        this.setPersonalDimension();
        this.refresh();
        return this;
    }
}
