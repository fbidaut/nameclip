package org.fbidaut.nameclip;

import java.util.EventListener;

/**
 * Created by fabien.bidaut on 10/08/2015.
 */
public interface DossierListener extends EventListener {
    void activeDossierChanged(String activeDossier);
}
