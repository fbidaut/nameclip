package org.fbidaut.nameclip;

import java.util.List;

/**
 * Created by fabien.bidaut on 31/07/2015.
 */
public interface ApplicationEngine {
    List<ClipData> currentFileList();

    List<String> getDossierList();

    void terminate();

    String getCurrentDossier();

    void setCurrentDossier(String dossier);

    void insertNewValue(String clipboardData);

    void removeValue(ClipData clipData);

    void addDossier(String value);

    void addDossierListener(DossierListener aListener);

    void removeDossierListener(DossierListener aListener);
}
