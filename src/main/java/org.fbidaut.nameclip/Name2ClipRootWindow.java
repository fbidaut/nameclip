package org.fbidaut.nameclip;

import java.awt.*;
import java.awt.event.WindowEvent;

public class Name2ClipRootWindow extends Frame {

    private static final int FRAME_HEIGHT = 200;
    private static final int FRAME_WIDTH = 200;
    private final ApplicationEngine engine;

    public Name2ClipRootWindow(ApplicationEngine engine) {
        this.engine = engine;
        setupLayout();
    }
    public void setupLayout(){
        //setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setTitle("Clipboard manager");
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent winEvt) {
                handleCloseWindow();
            }
        });
        Name2ClipMainPanel mainPanel = new Name2ClipMainPanel(engine);

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        this.add(mainPanel,c);
    }

    private void handleCloseWindow() {
        setVisible(false);
        engine.terminate();
    }
}
