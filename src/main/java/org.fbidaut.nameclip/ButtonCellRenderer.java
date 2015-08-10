package org.fbidaut.nameclip;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

/**
 * Created by fabien.bidaut on 31/07/2015.
 */
public class ButtonCellRenderer extends JButton implements TableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, final int row, int column) {
        if(value instanceof ActionValue) {
            this.setText(((ActionValue)value).getAction());
        } else {
            this.setText("");
        }
        return this;
    }
}
