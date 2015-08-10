package org.fbidaut.nameclip;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabien.bidaut on 31/07/2015.
 */
public class DataTableModel extends AbstractTableModel {
    private final ApplicationEngine engine;
    private List<ClipData> clipdataList= new ArrayList<ClipData>();

    public static final int LABEL_COLUMN=0;
    public static final int VALUE_COLUMN=1;
    public static final int COPY_COLUMN=2;
    public static final int DEL_COLUMN=3;

    public DataTableModel(ApplicationEngine engine) {
        this.engine = engine ;
        clipdataList = engine.currentFileList();
    }

    public int getRowCount() {
        return clipdataList.size();
    }

    public int getColumnCount() {
        return 4;
    }

    @Override
    public Class<?> getColumnClass(int aColumnIndex) {
        switch (aColumnIndex) {
            case LABEL_COLUMN:
                return String.class;
            case VALUE_COLUMN:
                // Apply global date format to error raised date
                return String.class;
            case COPY_COLUMN:
                return ActionValue.class;
            case DEL_COLUMN:
                return ActionValue.class;
        }
        return null;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case LABEL_COLUMN:
                return clipdataList.get(rowIndex).getLabel();
            case VALUE_COLUMN:
                return clipdataList.get(rowIndex).getValue();
            case COPY_COLUMN:
                ActionValue av=new ActionValue();
                av.setAction("Copier");
                return av;
            case DEL_COLUMN:
                ActionValue av2=new ActionValue();
                av2.setAction("X");
                return av2;
        }
        return null;
    }

    /**
     * Editable si l'index de colonne correspond à la premiere.
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }


    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case LABEL_COLUMN:
                clipdataList.get(rowIndex).setLabel((String) value);
                fireTableCellUpdated(rowIndex, columnIndex);
                break;
            case VALUE_COLUMN:
                clipdataList.get(rowIndex).setValue((String) value);
                fireTableCellUpdated(rowIndex, columnIndex);
                break;
            case COPY_COLUMN:
                String v = (String) this.getValueAt(rowIndex, DataTableModel.VALUE_COLUMN);
                handleCopy(v);
                fireTableCellUpdated(rowIndex, columnIndex);
                break;
            case DEL_COLUMN:
                handleDelete(rowIndex);
                fireTableDataChanged();
                break;
        }

        return ;
    }

    private void handleCopy(String value) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(value), null);
    }

    private void handleDelete(int rowIndex) {
        engine.removeValue(clipdataList.get(rowIndex));
    }

}
