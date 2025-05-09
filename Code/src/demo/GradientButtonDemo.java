package demo;

import javax.swing.*;
import java.awt.*;
import components.GradientButton;
import utils.AppColors;

/**
 * Demo application to showcase the gradient buttons
 */
public class GradientButtonDemo extends JFrame {
    
    public GradientButtonDemo() {
        setTitle("Gradient Button Demo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create main panel with light background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppColors.BACKGROUND_LIGHT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Create title
        JLabel titleLabel = new JLabel("Gradient Button Styles", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Create content panel with grid layout
        JPanel contentPanel = new JPanel(new GridLayout(6, 2, 20, 20));
        contentPanel.setBackground(AppColors.BACKGROUND_LIGHT);
        
        // Primary buttons
        contentPanel.add(createLabelPanel("Primary Button"));
        contentPanel.add(GradientButton.createPrimaryButton("Primary Button"));
        
        // Secondary buttons
        contentPanel.add(createLabelPanel("Secondary Button"));
        contentPanel.add(GradientButton.createSecondaryButton("Secondary Button"));
        
        // Success buttons
        contentPanel.add(createLabelPanel("Success Button"));
        contentPanel.add(GradientButton.createSuccessButton("Success Button"));
        
        // Danger buttons
        contentPanel.add(createLabelPanel("Danger Button"));
        contentPanel.add(GradientButton.createDangerButton("Danger Button"));
        
        // Warning buttons
        contentPanel.add(createLabelPanel("Warning Button"));
        contentPanel.add(GradientButton.createWarningButton("Warning Button"));
        
        // Info buttons
        contentPanel.add(createLabelPanel("Info Button"));
        contentPanel.add(GradientButton.createInfoButton("Info Button"));
        
        // Add content panel to main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Create custom button panel
        JPanel customPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        customPanel.setBackground(AppColors.BACKGROUND_LIGHT);
        customPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        
        // Create custom gradient button
        GradientButton customButton = new GradientButton(
            "Custom Gradient Button",
            new Color(138, 43, 226), // BlueViolet
            new Color(75, 0, 130),   // Indigo
            Color.WHITE
        );
        customButton.setPreferredSize(new Dimension(250, 50));
        customButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        customButton.setCornerRadius(25); // Rounded corners
        
        customPanel.add(customButton);
        
        // Add custom panel to main panel
        mainPanel.add(customPanel, BorderLayout.SOUTH);
        
        // Set content pane
        setContentPane(mainPanel);
        setVisible(true);
    }
    
    private JPanel createLabelPanel(String text) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(AppColors.BACKGROUND_LIGHT);
        
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(AppColors.TEXT_PRIMARY);
        
        panel.add(label);
        return panel;
    }
    
    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(GradientButtonDemo::new);
    }
}
