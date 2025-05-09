package components;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import utils.AppColors;

/**
 * Custom renderer for buttons in tables
 */
public class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
        setFocusPainted(false);
        setBorderPainted(true);
        setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value.toString());
        setBackground(AppColors.PRIMARY);
        setForeground(Color.WHITE);
        return this;
    }
}
