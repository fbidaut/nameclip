package org.fbidaut.nameclip;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by fabien.bidaut on 31/07/2015.
 */
public class Name2ClipMainPanel extends JPanel implements DossierListener{

//    private static final int PANEL_WIDTH = 300;
//    private static final int PANEL_HEIGHT = 200 ;
    public static final int LABEL_COLUMN_WIDTH_MIN=5;
    public static final int LABEL_COLUMN_WIDTH_DEFAULT=30;

    private static final int VALUE_COLUMN_WIDTH_MIN = 90;
    private static final int VALUE_COLUMN_WIDTH_DEFAULT = 90;
    private static final int COPY_COLUMN_WIDTH = 90;
    private static final int DEL_COLUMN_WIDTH = 50;

    private final ApplicationEngine engine;
    private DataTableModel tableModel;
    private JTable clipdataTable;
    JComboBox<String> comboDossier;

    public Name2ClipMainPanel(ApplicationEngine engine) {
        this.engine = engine;
        setupLayout();
    }

    private void setupLayout() {

        tableModel = new DataTableModel(engine);
        clipdataTable = createTable(tableModel);


        JScrollPane tablePanel = new JScrollPane(clipdataTable);

        JButton pasteBtn = createBtnPaste();
        JButton btnAddDossier = createBtnAddDossier();


        comboDossier = createComboDossier();


        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth=3;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(5,5,0,5);
        c.fill = GridBagConstraints.BOTH;
        this.add(tablePanel, c);

        c.gridy = 1;
        c.weighty = 0;
        c.fill= GridBagConstraints.HORIZONTAL;
        this.add(pasteBtn,c);

        c.gridy = 0;
        c.weighty = 0;
        c.gridwidth=1;
        c.fill= GridBagConstraints.HORIZONTAL;
        this.add(comboDossier,c);

        c.gridx=1;
        c.weightx=0;
        this.add(btnAddDossier,c);

        engine.addDossierListener(this);
    }

    private JComboBox<String> createComboDossier() {
        final JComboBox<String> combo = new JComboBox<>();
        for(String dossier: engine.getDossierList()) {
            combo.addItem(dossier);
        }
        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dossier = (String)combo.getSelectedItem();
                engine.setCurrentDossier(dossier);
            }
        });
        return combo;
    }


    private JTable createTable(DataTableModel aTableModel) {
        JTable clipdataTable = new JTable(aTableModel);

        // Defines renderer for specific columns
        clipdataTable.setDefaultRenderer(ActionValue.class, new ButtonCellRenderer());
        clipdataTable.setDefaultEditor(ActionValue.class, new ButtonValueCellEditor());
        clipdataTable.setTableHeader(null);
        clipdataTable.setRowHeight(30);
        clipdataTable.setIntercellSpacing(new Dimension(5, 5));

        clipdataTable.getColumnModel().getColumn(DataTableModel.LABEL_COLUMN).setMinWidth(LABEL_COLUMN_WIDTH_MIN);
        clipdataTable.getColumnModel().getColumn(DataTableModel.LABEL_COLUMN).setWidth(LABEL_COLUMN_WIDTH_DEFAULT);
        clipdataTable.getColumnModel().getColumn(DataTableModel.VALUE_COLUMN).setMinWidth(VALUE_COLUMN_WIDTH_MIN);
        clipdataTable.getColumnModel().getColumn(DataTableModel.VALUE_COLUMN).setWidth(VALUE_COLUMN_WIDTH_DEFAULT);
        clipdataTable.getColumnModel().getColumn(DataTableModel.COPY_COLUMN).setMinWidth(COPY_COLUMN_WIDTH);
        clipdataTable.getColumnModel().getColumn(DataTableModel.COPY_COLUMN).setMaxWidth(COPY_COLUMN_WIDTH);
        clipdataTable.getColumnModel().getColumn(DataTableModel.DEL_COLUMN).setMinWidth(DEL_COLUMN_WIDTH);
        clipdataTable.getColumnModel().getColumn(DataTableModel.DEL_COLUMN).setMaxWidth(DEL_COLUMN_WIDTH);
//        clipdataTable.setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
//        clipdataTable.setSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        return clipdataTable;
    }

    /**
     * Creates the button PASTE.
     *
     * @return a JButton to place in layout
     */
    private JButton createBtnAddDossier() {
        JButton pasteBtn = new JButton("+");
        pasteBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                handleAddDossier();
            }
        });
        pasteBtn.setToolTipText("Créer un nouveau dossier");
        return pasteBtn;
    }

    private void handleAddDossier() {
        String value = JOptionPane.showInputDialog(this,"Nom du dossier", "Nouveau dossier",JOptionPane.QUESTION_MESSAGE);
        if(value!=null && value.length()>0){
            engine.addDossier(value);
            engine.setCurrentDossier(value);
        }
    }

    /**
     * Creates the button PASTE.
     *
     * @return a JButton to place in layout
     */
    private JButton createBtnRemoveDossier() {
        JButton pasteBtn = new JButton("-");
        pasteBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                handRemoveDossier();
            }
        });
        pasteBtn.setToolTipText("Supprimer le dossier courant");
        return pasteBtn;
    }

    private void handRemoveDossier() {

    }

    /**
     * Creates the button PASTE.
     *
     * @return a JButton to place in layout
     */
    private JButton createBtnPaste() {
        JButton pasteBtn = new JButton("Coller");
        pasteBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                handlePaste();
            }
        });
        pasteBtn.setToolTipText("Insérer le contenu du presse papier en temps que nouvelle valeur");
        return pasteBtn;
    }

    private void handlePaste() {
        String clipboardData = ClipboardUtils.getClipboardDataAsString();
        if (clipboardData != null) {
            System.out.println("Found <"+clipboardData+"> for dossier "+ engine.getCurrentDossier());
            this.engine.insertNewValue(clipboardData);
        }
        tableModel.fireTableDataChanged();
    }

    @Override
    public void activeDossierChanged(String activeDossier) {
        boolean found=false;
        for(int i=0; i<comboDossier.getModel().getSize();i++) {
            if(activeDossier.equals( comboDossier.getItemAt(i))) {
                found=true;
                comboDossier.setSelectedIndex(i);
                break;
            }
        }
        if(!found) {
            comboDossier.addItem(activeDossier);
            comboDossier.setSelectedItem(activeDossier);
        }

        tableModel.fireTableDataChanged();
    }
}
