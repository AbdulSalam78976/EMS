package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;
import utils.AppColors;
import components.GradientButton;

public class ForgotPasswordScreen extends JFrame {
    private JTextField emailField;
    private JLabel errorLabel;

    public ForgotPasswordScreen() {
        setTitle("Event Management System - Forgot Password");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Main panel with GridBagLayout for perfect centering
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(AppColors.BACKGROUND_LIGHT);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Content panel to hold everything
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));

        // System name label
        JLabel systemName = new JLabel("Event Management System");
        systemName.setFont(new Font("Segoe UI", Font.BOLD, 28));
        systemName.setForeground(AppColors.PRIMARY);
        systemName.setAlignmentX(Component.CENTER_ALIGNMENT);
        systemName.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Form panel with GridBagLayout for precise control
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppColors.BORDER_LIGHT),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel heading = new JLabel("Reset Password", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(AppColors.TEXT_PRIMARY);
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(heading, gbc);

        // Instructions
        JLabel instructionLabel = new JLabel("<html><div style='text-align: center; width: 300px;'>"
                + "Enter your email to receive password reset instructions"
                + "</div></html>", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        instructionLabel.setForeground(AppColors.TEXT_SECONDARY);
        gbc.gridy++;
        formPanel.add(instructionLabel, gbc);

        // Email field - Centered using a wrapper panel
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        emailPanel.setOpaque(false);
        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailPanel.add(emailLabel);
        gbc.gridy++;
        formPanel.add(emailPanel, gbc);

        JPanel emailFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        emailFieldPanel.setOpaque(false);
        emailField = new JTextField(20);
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setPreferredSize(new Dimension(300, 30));
        emailFieldPanel.add(emailField);
        gbc.gridy++;
        formPanel.add(emailFieldPanel, gbc);

        // Error label
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(AppColors.ERROR);
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy++;
        formPanel.add(errorLabel, gbc);

        // Reset button - Centered using a wrapper panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        GradientButton resetButton = GradientButton.createPrimaryButton("Send Reset Link");
        resetButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resetButton.setPreferredSize(new Dimension(220, 45));
        resetButton.addActionListener(e -> handleResetPassword());
        buttonPanel.add(resetButton);
        gbc.gridy++;
        gbc.insets = new Insets(25, 10, 15, 10);
        formPanel.add(buttonPanel, gbc);

        // Back to login link
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        linkPanel.setOpaque(false);
        JLabel backText = new JLabel("Remember your password?");
        backText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JLabel backLink = new JLabel("Sign in");
        backLink.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backLink.setForeground(AppColors.PRIMARY);
        backLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                SwingUtilities.invokeLater(LoginScreen::new);
            }
        });
        linkPanel.add(backText);
        linkPanel.add(backLink);
        gbc.gridy++;
        gbc.insets = new Insets(15, 10, 0, 10);
        formPanel.add(linkPanel, gbc);

        // Add components to content panel
        contentPanel.add(systemName);
        contentPanel.add(formPanel);

        // Add content panel to main panel (centered)
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.weightx = 1;
        gbcMain.weighty = 1;
        gbcMain.fill = GridBagConstraints.CENTER;
        mainPanel.add(contentPanel, gbcMain);

        add(mainPanel);
        setVisible(true);
    }

    private void handleResetPassword() {
        String email = emailField.getText().trim();
        errorLabel.setText(" "); // Reset error message

        if (email.isEmpty()) {
            errorLabel.setText("Please enter your email address");
            return;
        }

        if (!isValidEmail(email)) {
            errorLabel.setText("Please enter a valid email address");
            return;
        }

        JOptionPane.showMessageDialog(this,
            "Password reset instructions have been sent to your email",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
        dispose();
        SwingUtilities.invokeLater(LoginScreen::new);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ForgotPasswordScreen screen = new ForgotPasswordScreen();
            screen.setVisible(true);
        });
    }
}