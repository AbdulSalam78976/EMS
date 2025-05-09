package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;
import utils.AppColors;
import utils.UIUtils;

/**
 * A dialog for event registration
 */
public class EventRegistrationDialog extends JDialog {
    private final String eventId;
    private final String eventName;
    private final Date eventDate;
    private final String venue;
    private final int availableSlots;
    private final String eligibilityCriteria;
    private final Consumer<Boolean> onRegistrationComplete;
    
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField studentIdField;
    private JTextArea specialRequirementsArea;
    private JCheckBox eligibilityCheckbox;
    private JLabel errorLabel;
    
    /**
     * Creates a new event registration dialog
     * 
     * @param parent The parent frame
     * @param eventId The event ID
     * @param eventName The event name
     * @param eventDate The event date
     * @param venue The venue
     * @param availableSlots The available slots
     * @param eligibilityCriteria The eligibility criteria
     * @param onRegistrationComplete Callback for when registration is complete
     */
    public EventRegistrationDialog(
            JFrame parent,
            String eventId,
            String eventName,
            Date eventDate,
            String venue,
            int availableSlots,
            String eligibilityCriteria,
            Consumer<Boolean> onRegistrationComplete) {
        
        super(parent, "Event Registration - " + eventName, true);
        
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.venue = venue;
        this.availableSlots = availableSlots;
        this.eligibilityCriteria = eligibilityCriteria;
        this.onRegistrationComplete = onRegistrationComplete;
        
        setupUI();
        
        // Set dialog properties
        setSize(800, 700);
        setLocationRelativeTo(parent);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void setupUI() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(AppColors.BACKGROUND_LIGHT);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Registration form panel
        RoundedPanel formPanel = createRegistrationForm();
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(formPanel);
        
        // Add content to dialog
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        setContentPane(scrollPane);
    }
    
    private RoundedPanel createRegistrationForm() {
        RoundedPanel mainPanel = new RoundedPanel(new BorderLayout(), Color.WHITE, UIUtils.CORNER_RADIUS);
        mainPanel.setShadow(3);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("EVENT REGISTRATION");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        
        // Event info panel
        JPanel eventInfoPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        eventInfoPanel.setOpaque(false);
        eventInfoPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        
        eventInfoPanel.add(new JLabel("Event: " + eventName));
        eventInfoPanel.add(new JLabel("Date: " + dateFormat.format(eventDate)));
        eventInfoPanel.add(new JLabel("Time: " + timeFormat.format(eventDate) + " - " + timeFormat.format(new Date(eventDate.getTime() + 3600000)))); // Placeholder end time
        eventInfoPanel.add(new JLabel("Venue: " + venue));
        
        titlePanel.add(eventInfoPanel, BorderLayout.CENTER);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Form content
        JPanel formContent = new JPanel();
        formContent.setLayout(new BoxLayout(formContent, BoxLayout.Y_AXIS));
        formContent.setOpaque(false);
        
        // Eligibility confirmation section
        RoundedPanel eligibilityPanel = createSectionPanel("ELIGIBILITY CONFIRMATION");
        
        JPanel checkboxPanel = new JPanel(new BorderLayout());
        checkboxPanel.setOpaque(false);
        checkboxPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        eligibilityCheckbox = new JCheckBox("I confirm that I meet the following criteria:");
        eligibilityCheckbox.setFont(new Font("Segoe UI", Font.BOLD, 14));
        eligibilityCheckbox.setOpaque(false);
        
        JTextArea criteriaText = new JTextArea(eligibilityCriteria);
        criteriaText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        criteriaText.setEditable(false);
        criteriaText.setOpaque(false);
        criteriaText.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 5));
        criteriaText.setLineWrap(true);
        criteriaText.setWrapStyleWord(true);
        
        checkboxPanel.add(eligibilityCheckbox, BorderLayout.NORTH);
        checkboxPanel.add(criteriaText, BorderLayout.CENTER);
        
        eligibilityPanel.add(checkboxPanel, BorderLayout.CENTER);
        formContent.add(eligibilityPanel);
        formContent.add(Box.createVerticalStrut(20));
        
        // Attendee information section
        RoundedPanel attendeeInfoPanel = createSectionPanel("ATTENDEE INFORMATION");
        
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setOpaque(false);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        
        // Name field (pre-filled and disabled)
        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(new JLabel("Name:"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        nameField = UIUtils.createRoundedTextField();
        nameField.setText("John Doe");
        nameField.setEditable(false);
        nameField.setBackground(new Color(240, 240, 240));
        fieldsPanel.add(nameField, gbc);
        
        // Email field (pre-filled and disabled)
        gbc.gridx = 0;
        gbc.gridy = 2;
        fieldsPanel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        emailField = UIUtils.createRoundedTextField();
        emailField.setText("john.doe@example.com");
        emailField.setEditable(false);
        emailField.setBackground(new Color(240, 240, 240));
        fieldsPanel.add(emailField, gbc);
        
        // Phone field
        gbc.gridx = 0;
        gbc.gridy = 4;
        fieldsPanel.add(new JLabel("Phone:"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        phoneField = UIUtils.createRoundedTextField();
        fieldsPanel.add(phoneField, gbc);
        
        // Student ID field
        gbc.gridx = 0;
        gbc.gridy = 6;
        fieldsPanel.add(new JLabel("Student ID:"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        studentIdField = UIUtils.createRoundedTextField();
        fieldsPanel.add(studentIdField, gbc);
        
        // Special requirements field
        gbc.gridx = 0;
        gbc.gridy = 8;
        fieldsPanel.add(new JLabel("Special Requirements (Optional):"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridheight = 2;
        specialRequirementsArea = new JTextArea(3, 20);
        specialRequirementsArea.setLineWrap(true);
        specialRequirementsArea.setWrapStyleWord(true);
        specialRequirementsArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppColors.BORDER_LIGHT),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        JScrollPane scrollPane = new JScrollPane(specialRequirementsArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        fieldsPanel.add(scrollPane, gbc);
        
        attendeeInfoPanel.add(fieldsPanel, BorderLayout.CENTER);
        formContent.add(attendeeInfoPanel);
        formContent.add(Box.createVerticalStrut(20));
        
        // Terms and buttons section
        JPanel termsPanel = new JPanel(new BorderLayout());
        termsPanel.setOpaque(false);
        termsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        JLabel termsLabel = new JLabel("<html>By registering, you agree to the event terms and conditions and cancellation policy.</html>");
        termsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        termsPanel.add(termsLabel, BorderLayout.NORTH);
        
        // Error label
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(AppColors.ERROR);
        errorLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        termsPanel.add(errorLabel, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonsPanel.setOpaque(false);
        
        GradientButton cancelButton = GradientButton.createSecondaryButton("CANCEL");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setPreferredSize(new Dimension(150, 40));
        cancelButton.addActionListener(e -> dispose());
        
        GradientButton registerButton = GradientButton.createPrimaryButton("REGISTER");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerButton.setPreferredSize(new Dimension(150, 40));
        registerButton.addActionListener(e -> handleRegistration());
        
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(registerButton);
        
        termsPanel.add(buttonsPanel, BorderLayout.SOUTH);
        formContent.add(termsPanel);
        
        mainPanel.add(formContent, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    private RoundedPanel createSectionPanel(String title) {
        RoundedPanel panel = new RoundedPanel(new BorderLayout(), Color.WHITE, UIUtils.CORNER_RADIUS);
        panel.setShadow(2);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Section title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        panel.add(titleLabel, BorderLayout.NORTH);
        
        return panel;
    }
    
    private void handleRegistration() {
        // Reset error message
        errorLabel.setText(" ");
        
        // Validate form
        if (!eligibilityCheckbox.isSelected()) {
            errorLabel.setText("You must confirm that you meet the eligibility criteria.");
            return;
        }
        
        String phoneNumber = phoneField.getText().trim();
        if (phoneNumber.isEmpty()) {
            errorLabel.setText("Please enter your phone number.");
            return;
        }
        
        // Validate phone number format (10 digits, can have optional formatting)
        if (!isValidPhoneNumber(phoneNumber)) {
            errorLabel.setText("Please enter a valid phone number (10 digits).");
            return;
        }
        
        String studentId = studentIdField.getText().trim();
        if (studentId.isEmpty()) {
            errorLabel.setText("Please enter your student ID.");
            return;
        }
        
        // Validate student ID format (alphanumeric, at least 5 characters)
        if (!isValidStudentId(studentId)) {
            errorLabel.setText("Please enter a valid student ID (alphanumeric, at least 5 characters).");
            return;
        }
        
        // Show success message
        JOptionPane.showMessageDialog(this,
            "You have successfully registered for " + eventName + "!",
            "Registration Successful",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Call the callback
        if (onRegistrationComplete != null) {
            onRegistrationComplete.accept(true);
        }
        
        // Close the dialog
        dispose();
    }
    
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Remove any non-digit characters
        String digitsOnly = phoneNumber.replaceAll("\\D", "");
        // Check if it has exactly 10 digits
        return digitsOnly.length() == 10;
    }
    
    private boolean isValidStudentId(String studentId) {
        // Check if it's alphanumeric and at least 5 characters
        return studentId.matches("^[a-zA-Z0-9]{5,}$");
    }
}
