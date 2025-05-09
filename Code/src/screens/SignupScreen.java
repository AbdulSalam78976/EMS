package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;
import components.GradientButton;
import utils.AppColors;

public class SignupScreen extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passField;
    private JPasswordField confirmPassField;
    private JComboBox<String> roleComboBox;
    private JLabel errorLabel;

    public SignupScreen() {
        setTitle("Event Management System - Sign Up");
        setSize(900, 700);
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
                + "<h2>Create an Account</h2>"
                + "<p style='width: 300px;'>Join us to discover and participate in exciting events</p>"
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

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy++;
        formPanel.add(nameField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy++;
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

        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        confirmPassLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy++;
        formPanel.add(confirmPassLabel, gbc);

        confirmPassField = new JPasswordField(20);
        confirmPassField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy++;
        formPanel.add(confirmPassField, gbc);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy++;
        formPanel.add(roleLabel, gbc);

        String[] roles = {"Attendee", "External Event Organizer"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy++;
        formPanel.add(roleComboBox, gbc);

        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridy++;
        formPanel.add(errorLabel, gbc);

        GradientButton signupBtn = GradientButton.createPrimaryButton("Sign Up");
        signupBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        signupBtn.setPreferredSize(new Dimension(200, 45));
        signupBtn.addActionListener(e -> validateSignup());
        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 10, 10);
        formPanel.add(signupBtn, gbc);

        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginPanel.setOpaque(false);
        JLabel loginText = new JLabel("Already have an account? ");
        loginText.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel loginLink = new JLabel("<html><u>Login</u></html>");
        loginLink.setForeground(new Color(65, 105, 225));
        loginLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLink.setFont(new Font("Segoe UI", Font.BOLD, 12));
        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                SwingUtilities.invokeLater(LoginScreen::new);
            }
        });
        loginPanel.add(loginText);
        loginPanel.add(loginLink);
        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 0, 10);
        formPanel.add(loginPanel, gbc);

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

    private void validateSignup() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passField.getPassword()).trim();
        String confirmPassword = new String(confirmPassField.getPassword()).trim();
        String role = (String) roleComboBox.getSelectedItem();

        errorLabel.setText(" "); // Reset error message

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorLabel.setText("Please fill in all fields");
            return;
        }

        if (!isValidEmail(email)) {
            errorLabel.setText("Please enter a valid email address");
            return;
        }

        if (password.length() < 6) {
            errorLabel.setText("Password must be at least 6 characters long");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match");
            return;
        }

        JOptionPane.showMessageDialog(this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
            SignupScreen screen = new SignupScreen();
            screen.setVisible(true);
        });
    }
}
