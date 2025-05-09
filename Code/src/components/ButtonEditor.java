package components;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import utils.AppColors;

/**
 * Custom editor for buttons in tables
 */
public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private ActionListener actionListener;
    private int currentRow;
    private int currentColumn;
    private JTable currentTable;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(AppColors.PRIMARY);
        button.setForeground(Color.WHITE);

        button.addActionListener(e -> fireEditingStopped());
    }

    /**
     * Sets the action listener for the button
     *
     * @param actionListener The action listener
     */
    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    /**
     * Gets the current row being edited
     *
     * @return The current row
     */
    public int getCurrentRow() {
        return currentRow;
    }

    /**
     * Gets the current column being edited
     *
     * @return The current column
     */
    public int getCurrentColumn() {
        return currentColumn;
    }

    /**
     * Gets the current table being edited
     *
     * @return The current table
     */
    public JTable getCurrentTable() {
        return currentTable;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Store the current table, row, and column
        currentTable = table;
        currentRow = row;
        currentColumn = column;

        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;

        // If an action listener is set, add it to the button
        if (actionListener != null) {
            // Remove existing action listeners except the one that fires editing stopped
            ActionListener[] listeners = button.getActionListeners();
            for (ActionListener listener : listeners) {
                button.removeActionListener(listener);
            }

            // Add the action listener
            button.addActionListener(actionListener);

            // Re-add the listener that fires editing stopped
            button.addActionListener(e -> fireEditingStopped());
        }

        return button;
    }

    @Override
    public Object getCellEditorValue() {
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
