package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import utils.AppColors;

/**
 * A custom button with gradient background and rounded corners
 */
public class GradientButton extends JButton {
    private Color gradientStart;
    private Color gradientEnd;
    private Color textColor;
    private Color hoverGradientStart;
    private Color hoverGradientEnd;
    private Color pressedGradientStart;
    private Color pressedGradientEnd;
    private int cornerRadius = 8;
    private boolean isHovered = false;
    private boolean isPressed = false;
    
    /**
     * Creates a gradient button with the primary color scheme
     * 
     * @param text The button text
     */
    public GradientButton(String text) {
        this(text, AppColors.PRIMARY_GRADIENT_START, AppColors.PRIMARY_GRADIENT_END, Color.WHITE);
    }
    
    /**
     * Creates a gradient button with custom colors
     * 
     * @param text The button text
     * @param gradientStart The start color of the gradient
     * @param gradientEnd The end color of the gradient
     * @param textColor The text color
     */
    public GradientButton(String text, Color gradientStart, Color gradientEnd, Color textColor) {
        super(text);
        this.gradientStart = gradientStart;
        this.gradientEnd = gradientEnd;
        this.textColor = textColor;
        
        // Calculate hover and pressed colors (slightly lighter and darker)
        this.hoverGradientStart = lighten(gradientStart, 0.1f);
        this.hoverGradientEnd = lighten(gradientEnd, 0.1f);
        this.pressedGradientStart = darken(gradientStart, 0.1f);
        this.pressedGradientEnd = darken(gradientEnd, 0.1f);
        
        // Set button properties
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(textColor);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Add mouse listeners for hover and press effects
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }
    
    /**
     * Creates a primary gradient button
     */
    public static GradientButton createPrimaryButton(String text) {
        return new GradientButton(text, AppColors.PRIMARY_GRADIENT_START, AppColors.PRIMARY_GRADIENT_END, Color.WHITE);
    }
    
    /**
     * Creates a secondary gradient button
     */
    public static GradientButton createSecondaryButton(String text) {
        return new GradientButton(text, AppColors.SECONDARY_GRADIENT_START, AppColors.SECONDARY_GRADIENT_END, Color.WHITE);
    }
    
    /**
     * Creates a success gradient button
     */
    public static GradientButton createSuccessButton(String text) {
        return new GradientButton(text, AppColors.SUCCESS_GRADIENT_START, AppColors.SUCCESS_GRADIENT_END, Color.WHITE);
    }
    
    /**
     * Creates a danger gradient button
     */
    public static GradientButton createDangerButton(String text) {
        return new GradientButton(text, AppColors.DANGER_GRADIENT_START, AppColors.DANGER_GRADIENT_END, Color.WHITE);
    }
    
    /**
     * Creates a warning gradient button
     */
    public static GradientButton createWarningButton(String text) {
        return new GradientButton(text, AppColors.WARNING_GRADIENT_START, AppColors.WARNING_GRADIENT_END, Color.BLACK);
    }
    
    /**
     * Creates an info gradient button
     */
    public static GradientButton createInfoButton(String text) {
        return new GradientButton(text, AppColors.INFO_GRADIENT_START, AppColors.INFO_GRADIENT_END, Color.WHITE);
    }
    
    /**
     * Set the corner radius for the button
     */
    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        
        // Determine which colors to use based on state
        Color startColor, endColor;
        
        if (isPressed) {
            startColor = pressedGradientStart;
            endColor = pressedGradientEnd;
        } else if (isHovered) {
            startColor = hoverGradientStart;
            endColor = hoverGradientEnd;
        } else {
            startColor = gradientStart;
            endColor = gradientEnd;
        }
        
        // Create gradient paint
        GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, height, endColor);
        g2.setPaint(gradient);
        
        // Draw rounded rectangle
        g2.fill(new RoundRectangle2D.Double(0, 0, width, height, cornerRadius, cornerRadius));
        
        // Draw text
        FontMetrics metrics = g2.getFontMetrics(getFont());
        int textWidth = metrics.stringWidth(getText());
        int textHeight = metrics.getHeight();
        int x = (width - textWidth) / 2;
        int y = ((height - textHeight) / 2) + metrics.getAscent();
        
        g2.setColor(textColor);
        g2.setFont(getFont());
        g2.drawString(getText(), x, y);
        
        g2.dispose();
    }
    
    /**
     * Lighten a color by the given factor
     */
    private Color lighten(Color color, float factor) {
        int r = Math.min(255, (int)(color.getRed() + 255 * factor));
        int g = Math.min(255, (int)(color.getGreen() + 255 * factor));
        int b = Math.min(255, (int)(color.getBlue() + 255 * factor));
        return new Color(r, g, b);
    }
    
    /**
     * Darken a color by the given factor
     */
    private Color darken(Color color, float factor) {
        int r = Math.max(0, (int)(color.getRed() - 255 * factor));
        int g = Math.max(0, (int)(color.getGreen() - 255 * factor));
        int b = Math.max(0, (int)(color.getBlue() - 255 * factor));
        return new Color(r, g, b);
    }
}
