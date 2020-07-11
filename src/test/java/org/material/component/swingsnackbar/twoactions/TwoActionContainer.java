package org.material.component.swingsnackbar.twoactions;

import org.material.component.swingsnackbar.SnackBar;
import org.material.component.swingsnackbar.model.SnackBarContainer;

import javax.swing.*;
import java.awt.*;

public class TwoActionContainer extends SnackBarContainer {

    private JLabel secondAction;

    public TwoActionContainer(SnackBar snackBar, JLabel snackBarText, JLabel secondAction, JLabel snackBarIcon) {
        super(snackBar, snackBarText, snackBarIcon);
        this.secondAction = secondAction;
    }

    public TwoActionContainer(SnackBar snackBar, JLabel snackBarText, JLabel secondAction, Icon snackBarIcon) {
        super(snackBar, snackBarText, snackBarIcon);
        this.secondAction = secondAction;
    }

    @Deprecated
    public TwoActionContainer(SnackBar snackBar, JLabel snackBarText) {
        super(snackBar, snackBarText);

    }

    @Override
    public Dimension getDimension() {
        int width;
        if (this.snackBarIcon != null && snackBarIcon.getIcon() != null
                && this.secondAction == null && this.secondAction.getIcon() != null) {
            width = this.snackBarText.getFontMetrics(
                    this.snackBarText.getFont()).stringWidth(this.snackBarText.getText())
                    + this.snackBarIcon.getIcon().getIconWidth()
                    + this.secondAction.getIcon().getIconWidth();
        } else {
            this.snackBarText.setHorizontalTextPosition(SwingConstants.LEFT);
            width = this.snackBarText.getFontMetrics(
                    this.snackBarText.getFont()).stringWidth(this.snackBarText.getText());
            if (this.snackBarIcon != null) {
                this.snackBarIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
                width += (this.snackBarIcon.getText() != null
                        ? this.snackBarIcon.getText().length() * 8
                        : 10) + gap;
            }
            if (this.secondAction != null) {
                this.secondAction.setHorizontalTextPosition(SwingConstants.RIGHT);
                width += (this.secondAction.getText() != null
                        ? this.secondAction.getText().length() * 8
                        : 10) + gap;
            }
        }
        width += gap;
        return new Dimension(width, 50);
    }

    /**
     * This method decided the position of the component
     * such as secondAction.
     *
     * TODO I should be use another layout, because with BorderLayout is not possible make this change.
     */
    @Override
    public void inflateContent() {
        super.add(snackBarText, BorderLayout.WEST);
        JPanel container = new JPanel();
        if (secondAction != null) {
            container.add(secondAction, BorderLayout.WEST);
            //secondAction.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            //super.add(secondAction, BorderLayout.WEST);
        }
        if (snackBarIcon != null) {
            container.add(snackBarIcon, BorderLayout.EAST);
            //super.add(snackBarIcon, BorderLayout.LINE_END);
        }
        container.setBackground(this.getBackground());
        super.add(container, BorderLayout.EAST);
        setVisible(true);
    }
}
