package org.fbidaut.nameclip;

import javax.swing.*;
import javax.swing.event.ListDataListener;

/**
 * Created by fabien.bidaut on 9/08/2015.
 */
public class DossierComboBoxModel<String> extends DefaultComboBoxModel<String> implements ComboBoxModel<String>  {

    private final ApplicationEngine engine;

    public  DossierComboBoxModel(ApplicationEngine engine) {
        this.engine = engine;
    }

}
