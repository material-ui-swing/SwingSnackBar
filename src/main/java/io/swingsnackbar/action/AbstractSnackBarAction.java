package io.swingsnackbar.action;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class AbstractSnackBarAction implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}
