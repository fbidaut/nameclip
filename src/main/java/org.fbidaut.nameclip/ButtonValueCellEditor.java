package org.fbidaut.nameclip;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by fabien.bidaut on 31/07/2015.
 */
public class ButtonValueCellEditor extends AbstractCellEditor
        implements TableCellEditor {
    private Object value;

    ButtonCellRenderer renderer ;

    public ButtonValueCellEditor() {
        renderer = new ButtonCellRenderer();
        renderer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.value = value;
        return renderer;
    }

    public Object getCellEditorValue() {
        return value;
    }


}
