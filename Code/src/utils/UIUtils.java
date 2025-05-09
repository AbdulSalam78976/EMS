package utils;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Utility class for UI components with consistent styling
 */
public class UIUtils {
    // Default corner radius for rounded components
    public static final int CORNER_RADIUS = 10;
    
    // Default padding for components
    public static final int PADDING_SMALL = 5;
    public static final int PADDING_MEDIUM = 10;
    public static final int PADDING_LARGE = 15;
    
    /**
     * Creates a panel with rounded corners
     * 
     * @param backgroundColor The background color of the panel
     * @param radius The corner radius
     * @return A JPanel with rounded corners
     */
    public static JPanel createRoundedPanel(Color backgroundColor, int radius) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Paint background
                g2.setColor(backgroundColor);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
                
                g2.dispose();
            }
        };
    }
    
    /**
     * Creates a panel with rounded corners and a layout manager
     * 
     * @param backgroundColor The background color of the panel
     * @param radius The corner radius
     * @param layout The layout manager to use
     * @return A JPanel with rounded corners and the specified layout
     */
    public static JPanel createRoundedPanel(Color backgroundColor, int radius, LayoutManager layout) {
        JPanel panel = createRoundedPanel(backgroundColor, radius);
        panel.setLayout(layout);
        return panel;
    }
    
    /**
     * Creates a rounded border
     * 
     * @param color The border color
     * @param radius The corner radius
     * @param thickness The border thickness
     * @return A Border with rounded corners
     */
    public static Border createRoundedBorder(Color color, int radius, int thickness) {
        return new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(color);
                g2.setStroke(new BasicStroke(thickness));
                g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
                
                g2.dispose();
            }
            
            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(thickness, thickness, thickness, thickness);
            }
            
            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        };
    }
    
    /**
     * Creates a rounded border with padding
     * 
     * @param color The border color
     * @param radius The corner radius
     * @param thickness The border thickness
     * @param padding The padding inside the border
     * @return A Border with rounded corners and padding
     */
    public static Border createRoundedBorderWithPadding(Color color, int radius, int thickness, int padding) {
        return BorderFactory.createCompoundBorder(
            createRoundedBorder(color, radius, thickness),
            BorderFactory.createEmptyBorder(padding, padding, padding, padding)
        );
    }
    
    /**
     * Creates a sidebar button with consistent styling
     * 
     * @param text The button text
     * @param icon The button icon (can be null)
     * @param isSelected Whether the button is selected
     * @return A styled JButton for the sidebar
     */
    public static JButton createSidebarButton(String text, Icon icon, boolean isSelected) {
        JButton button = new JButton(text);
        if (icon != null) {
            button.setIcon(icon);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setIconTextGap(10);
        }
        
        // Set button properties
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(isSelected ? new Color(70, 70, 70) : AppColors.BACKGROUND_DARK);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(200, 40));
        
        // Add rounded corners
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        
        return button;
    }
    
    /**
     * Creates a text field with rounded corners
     * 
     * @return A JTextField with rounded corners
     */
    public static JTextField createRoundedTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(createRoundedBorderWithPadding(AppColors.BORDER_LIGHT, CORNER_RADIUS, 1, PADDING_MEDIUM));
        return textField;
    }
    
    /**
     * Creates a password field with rounded corners
     * 
     * @return A JPasswordField with rounded corners
     */
    public static JPasswordField createRoundedPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(createRoundedBorderWithPadding(AppColors.BORDER_LIGHT, CORNER_RADIUS, 1, PADDING_MEDIUM));
        return passwordField;
    }
    
    /**
     * Creates a text area with rounded corners
     * 
     * @return A JTextArea with rounded corners
     */
    public static JTextArea createRoundedTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(createRoundedBorderWithPadding(AppColors.BORDER_LIGHT, CORNER_RADIUS, 1, PADDING_MEDIUM));
        return textArea;
    }
    
    /**
     * Creates a scroll pane with rounded corners
     * 
     * @param component The component to scroll
     * @return A JScrollPane with rounded corners
     */
    public static JScrollPane createRoundedScrollPane(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setBorder(createRoundedBorder(AppColors.BORDER_LIGHT, CORNER_RADIUS, 1));
        return scrollPane;
    }
    
    /**
     * Creates a combo box with rounded corners
     * 
     * @param items The items to display in the combo box
     * @return A JComboBox with rounded corners
     */
    public static <T> JComboBox<T> createRoundedComboBox(T[] items) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBorder(createRoundedBorder(AppColors.BORDER_LIGHT, CORNER_RADIUS, 1));
        return comboBox;
    }
}
