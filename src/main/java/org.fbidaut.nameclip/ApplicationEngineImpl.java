package org.fbidaut.nameclip;

import javax.sound.sampled.Clip;
import javax.swing.event.EventListenerList;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabien.bidaut on 31/07/2015.
 */
public class ApplicationEngineImpl implements ApplicationEngine {

    public static final String USER_HOME_PROP = "user.home";
    private static final String APP_DIR = ".nameclip";
    private static final String APP_DATA_FILE = "data.csv";
    public static final String SEPARATOR = "@SEP@";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator").toString();
    public static final String DEFAUT = "Defaut";
    private List<ClipData> clipdataList;
    private List<ClipData> currentClipdataList;
    private String currentDossier;
    private List<String> dossierList;

    private final EventListenerList listeners = new EventListenerList();


    public ApplicationEngineImpl() throws IOException {
        initAppData();
        setCurrentDossier("Defaut");
    }

    public List<ClipData> currentFileList() {
        return currentClipdataList;
    }

    @Override
    public String getCurrentDossier() {
        return currentDossier;
    }

    @Override
    public void setCurrentDossier(String dossier) {
        if (dossier == null || dossier.length() == 0) {
            return;
        }
        currentDossier = dossier;
        if (currentClipdataList != null) {
            currentClipdataList.clear();
        } else {
            currentClipdataList = new ArrayList<>();
        }
        for (ClipData cdata : clipdataList) {
            if (dossier.equals(cdata.getDossier())) {
                currentClipdataList.add(cdata);
            }
        }
        fireActiveDossierChanged();
    }

    @Override
    public List<String> getDossierList() {
        return dossierList;
    }

    public void terminate() {
        try {
            persist();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void insertNewValue(String value) {
        ClipData cdata;
        cdata = new ClipData();
        cdata.setDossier(currentDossier);
        cdata.setValue(value);
        //Add data to main app list
        clipdataList.add(cdata);
        //add data to current view
        currentClipdataList.add(cdata);
    }

    public void removeValue(ClipData clipData) {
        clipdataList.remove(clipData);
        currentClipdataList.remove(clipData);
    }

    private void initAppData() throws IOException {
        Path appDir = Paths.get(System.getProperty(USER_HOME_PROP), APP_DIR);
        if (!Files.exists(appDir)) {
            Files.createDirectory(appDir);
        }

        Path dataFile = Paths.get(System.getProperty(USER_HOME_PROP), APP_DIR, APP_DATA_FILE);
        if (Files.exists(dataFile)) {
            readDataFromFile(dataFile);
        } else {
            readDataFromStatic();
        }
    }

    private void readDataFromStatic() {
        clipdataList = new ArrayList<ClipData>();
        dossierList = new ArrayList<String>();
        addDossierIfNotExists(DEFAUT);
        ClipData cdata;
        cdata = new ClipData();
        cdata.setDossier(DEFAUT);
        cdata.setLabel("pere");
        cdata.setValue("DUPONT");
        clipdataList.add(cdata);
        cdata = new ClipData();
        cdata.setDossier(DEFAUT);
        cdata.setLabel("mere");
        cdata.setValue("DURANT");
        clipdataList.add(cdata);
    }

    private void readDataFromFile(Path dataFilePath) throws IOException {
        clipdataList = new ArrayList<ClipData>();
        dossierList = new ArrayList<String>();

        List<String> lines = Files.readAllLines(dataFilePath, StandardCharsets.UTF_8);
        for (String line : lines) {
            String[] data = line.split(SEPARATOR);
            if (data.length > 1) {
                ClipData cdata = new ClipData();
                addDossierIfNotExists(data[0]);
                cdata.setDossier(data[0]);
                cdata.setValue(data[1]);
                if (data.length > 2) {
                    cdata.setLabel(data[2]);
                }
                clipdataList.add(cdata);
            }
        }
    }

    @Override
    public void addDossier(String value) {
        addDossierIfNotExists(value);
    }

    private void addDossierIfNotExists(String dossier) {
        if (dossier == null || dossier.length() == 0) {
            return;
        }
        for (String v : dossierList) {
            if (v.equals(dossier)) {
                return;
            }
        }
        dossierList.add(dossier);
    }

    private void persist() throws IOException {
        Path dataFile = Paths.get(System.getProperty(USER_HOME_PROP), APP_DIR, APP_DATA_FILE);
        if (Files.exists(dataFile)) {
            Files.delete(dataFile);
        }
        persistData(dataFile);
    }

    private void persistData(Path dataFilePath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(dataFilePath, StandardCharsets.UTF_8)) {
            for (ClipData cdata : clipdataList) {
                if (cdata.getDossier() != null && cdata.getDossier().length() > 0) {
                    writer.write(cdata.getDossier());
                }
                writer.write(SEPARATOR);
                writer.write(cdata.getValue());
                if (cdata.getLabel() != null && cdata.getLabel().length() > 0) {
                    writer.write(SEPARATOR);
                    writer.write(cdata.getLabel());
                }
                writer.write(LINE_SEPARATOR);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void addDossierListener(DossierListener aListener) {
        listeners.add(DossierListener.class, aListener);
    }

    @Override
    public void removeDossierListener(DossierListener aListener) {
        listeners.remove(DossierListener.class, aListener);
    }

    public DossierListener[] getDossierListeners() {
        return listeners.getListeners(DossierListener.class);
    }

    protected void fireActiveDossierChanged() {
        for (DossierListener listener : getDossierListeners()) {
            listener.activeDossierChanged(currentDossier);
        }
    }

}
