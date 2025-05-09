package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;
import components.GradientButton;
import utils.AppColors;

public class LoginScreen extends JFrame {
    private JTextField emailField;
    private JPasswordField passField;
    private JLabel errorLabel;

    public LoginScreen() {
        setTitle("Event Management System - Login");
        // open the login screen in full size
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setMinimumSize(new Dimension(700, 700));

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(240, 240, 240)); // Light gray background
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.setOpaque(false);

        JLabel systemName = new JLabel("Event Management System");
        systemName.setFont(new Font("Segoe UI", Font.BOLD, 28));
        systemName.setForeground(new Color(65, 105, 225)); // Royal blue
        systemName.setAlignmentX(Component.CENTER_ALIGNMENT);
        systemName.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<h2>Welcome Back!</h2>"
                + "<p style='width: 300px;'>Login to access your events and account</p>"
                + "</div></html>");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        welcomeLabel.setForeground(new Color(100, 100, 100));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        formPanel.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 0;
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy++;
        formPanel.add(emailField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy++;
        formPanel.add(passLabel, gbc);

        passField = new JPasswordField(20);
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy++;
        formPanel.add(passField, gbc);

        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridy++;
        formPanel.add(errorLabel, gbc);

        GradientButton loginBtn = GradientButton.createPrimaryButton("Login");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.setPreferredSize(new Dimension(200, 45));
        loginBtn.addActionListener(e -> validateLogin());
        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 10, 10);
        formPanel.add(loginBtn, gbc);

        JPanel linksPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        linksPanel.setOpaque(false);

        JLabel forgotLabel = new JLabel("<html><u>Forgot Password?</u></html>");
        forgotLabel.setForeground(new Color(65, 105, 225));
        forgotLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        forgotLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                SwingUtilities.invokeLater(ForgotPasswordScreen::new);
            }
        });

        JPanel signupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signupPanel.setOpaque(false);
        JLabel signupText = new JLabel("Don't have an account? ");
        signupText.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel signupLink = new JLabel("<html><u>Sign Up</u></html>");
        signupLink.setForeground(new Color(65, 105, 225));
        signupLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signupLink.setFont(new Font("Segoe UI", Font.BOLD, 12));
        signupLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                SwingUtilities.invokeLater(SignupScreen::new);
            }
        });
        signupPanel.add(signupText);
        signupPanel.add(signupLink);

        linksPanel.add(forgotLabel);
        linksPanel.add(signupPanel);
        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 0, 10);
        formPanel.add(linksPanel, gbc);

        centerPanel.add(systemName);
        centerPanel.add(welcomeLabel);
        centerPanel.add(formPanel);

        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.weightx = 1;
        gbcMain.weighty = 1;
        gbcMain.fill = GridBagConstraints.CENTER;
        mainPanel.add(centerPanel, gbcMain);

        add(mainPanel);
        setVisible(true);
    }

    private void validateLogin() {
        String email = emailField.getText().trim();
        String password = new String(passField.getPassword()).trim();

        errorLabel.setText(" "); // Reset error message

        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please fill in all fields");
            return;
        }

        if (!isValidEmail(email)) {
            errorLabel.setText("Please enter a valid email address");
            return;
        }

        // For demonstration purposes, hardcoded credentials
        // In a real application, this would validate against a database
        if (email.equals("admin@example.com") && password.equals("admin123")) {
            JOptionPane.showMessageDialog(this, "Admin login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            SwingUtilities.invokeLater(AdminDashboardNew::new);
        } else if (email.equals("organizer@example.com") && password.equals("organizer123")) {
            JOptionPane.showMessageDialog(this, "Organizer login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            SwingUtilities.invokeLater(OrganizerDashboard::new);
        } else if (email.equals("attendee@example.com") && password.equals("attendee123")) {
            JOptionPane.showMessageDialog(this, "Attendee login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            SwingUtilities.invokeLater(AttendeeDashboardNew::new);
        } else {
            errorLabel.setText("Invalid email or password");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen screen = new LoginScreen();
            screen.setVisible(true);
        });
    }
}