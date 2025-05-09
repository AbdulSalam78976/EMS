package components;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;
import utils.AppColors;

/**
 * Reusable Create Event Form component
 * Extracted from CreateEventScreenNew to be used in multiple places
 */
public class CreateEventForm extends JPanel {
    private final String userRole; // "Admin" or "Organizer"
    private final Consumer<Boolean> onSubmitCallback; // Callback for when form is submitted (success/failure)

    // Form fields
    private JTextField eventNameField;
    private JComboBox<String> categoryComboBox;
    private JTextArea descriptionArea;
    private JTextField dateField;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JTextField venueField;
    private JSpinner capacitySpinner;
    private JTextField contactField;
    private JTextArea eligibilityArea;
    private JButton imageButton;
    private JButton documentsButton;
    private JLabel imageLabel;
    private JLabel documentsLabel;
    private JLabel errorLabel;
    private File selectedImageFile;
    private File[] selectedDocuments;

    /**
     * Create a new event form
     * 
     * @param userRole The role of the user ("Admin" or "Organizer")
     * @param onSubmitCallback Callback function that receives a boolean indicating success/failure
     */
    public CreateEventForm(String userRole, Consumer<Boolean> onSubmitCallback) {
        this.userRole = userRole;
        this.onSubmitCallback = onSubmitCallback;
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Create form panel
        JPanel formPanel = createEventForm();
        
        // Add to scroll pane for better usability
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createEventForm() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Form title
        JLabel titleLabel = new JLabel("Create New Event");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form content
        JPanel formContent = new JPanel(new GridBagLayout());
        formContent.setBackground(Color.WHITE);
        formContent.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.weightx = 1.0;

        // Event Name
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Event Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formContent.add(nameLabel, gbc);

        gbc.gridy = 1;
        eventNameField = new JTextField();
        eventNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formContent.add(eventNameField, gbc);

        // Category
        gbc.gridy = 2;
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formContent.add(categoryLabel, gbc);

        gbc.gridy = 3;
        String[] categories = {"Conference", "Workshop", "Seminar", "Competition", "Career Fair", "Other"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formContent.add(categoryComboBox, gbc);

        // Description
        gbc.gridy = 4;
        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formContent.add(descLabel, gbc);

        gbc.gridy = 5;
        gbc.gridheight = 3;
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        formContent.add(descScroll, gbc);

        // Date
        gbc.gridheight = 1;
        gbc.gridy = 8;
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formContent.add(dateLabel, gbc);

        gbc.gridy = 9;
        dateField = new JTextField();
        dateField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formContent.add(dateField, gbc);

        // Time
        gbc.gridy = 10;
        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formContent.add(timeLabel, gbc);

        gbc.gridy = 11;
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        timePanel.setOpaque(false);

        JLabel startLabel = new JLabel("Start:");
        startLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        startTimeField = new JTextField(8);
        startTimeField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel endLabel = new JLabel("End:");
        endLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        endTimeField = new JTextField(8);
        endTimeField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        timePanel.add(startLabel);
        timePanel.add(startTimeField);
        timePanel.add(endLabel);
        timePanel.add(endTimeField);

        formContent.add(timePanel, gbc);

        // Venue
        gbc.gridy = 12;
        JLabel venueLabel = new JLabel("Venue:");
        venueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formContent.add(venueLabel, gbc);

        gbc.gridy = 13;
        venueField = new JTextField();
        venueField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formContent.add(venueField, gbc);

        // Capacity
        gbc.gridy = 14;
        JLabel capacityLabel = new JLabel("Available Slots:");
        capacityLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formContent.add(capacityLabel, gbc);

        gbc.gridy = 15;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(50, 1, 1000, 1);
        capacitySpinner = new JSpinner(spinnerModel);
        capacitySpinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formContent.add(capacitySpinner, gbc);

        // Contact Info
        gbc.gridy = 16;
        JLabel contactLabel = new JLabel("Contact Info:");
        contactLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formContent.add(contactLabel, gbc);

        gbc.gridy = 17;
        contactField = new JTextField();
        contactField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formContent.add(contactField, gbc);

        // Eligibility Criteria
        gbc.gridy = 18;
        JLabel eligibilityLabel = new JLabel("Eligibility Criteria:");
        eligibilityLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formContent.add(eligibilityLabel, gbc);

        gbc.gridy = 19;
        gbc.gridheight = 2;
        eligibilityArea = new JTextArea(3, 20);
        eligibilityArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        eligibilityArea.setLineWrap(true);
        eligibilityArea.setWrapStyleWord(true);
        JScrollPane eligibilityScroll = new JScrollPane(eligibilityArea);
        formContent.add(eligibilityScroll, gbc);

        // Event Image
        gbc.gridheight = 1;
        gbc.gridy = 21;
        JLabel imageFileLabel = new JLabel("Event Image:");
        imageFileLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formContent.add(imageFileLabel, gbc);

        gbc.gridy = 22;
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        imagePanel.setOpaque(false);

        imageButton = new JButton("CHOOSE FILE");
        imageButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        imageButton.setBackground(AppColors.PRIMARY);
        imageButton.setForeground(Color.WHITE);
        imageButton.setFocusPainted(false);
        imageButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        imageButton.addActionListener(e -> handleImageUpload());

        imageLabel = new JLabel("No file chosen");
        imageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        imagePanel.add(imageButton);
        imagePanel.add(imageLabel);

        formContent.add(imagePanel, gbc);

        // Additional Documents
        gbc.gridy = 23;
        JLabel docsLabel = new JLabel("Additional Documents:");
        docsLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formContent.add(docsLabel, gbc);

        gbc.gridy = 24;
        JPanel docsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        docsPanel.setOpaque(false);

        documentsButton = new JButton("CHOOSE FILES");
        documentsButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        documentsButton.setBackground(AppColors.PRIMARY);
        documentsButton.setForeground(Color.WHITE);
        documentsButton.setFocusPainted(false);
        documentsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        documentsButton.addActionListener(e -> handleDocumentsUpload());

        documentsLabel = new JLabel("No files chosen");
        documentsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        documentsLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        docsPanel.add(documentsButton);
        docsPanel.add(documentsLabel);

        formContent.add(docsPanel, gbc);

        // Error label
        gbc.gridy = 25;
        errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        errorLabel.setForeground(Color.RED);
        formContent.add(errorLabel, gbc);

        // Buttons
        gbc.gridy = 26;
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setOpaque(false);

        JButton cancelButton = new JButton("CANCEL");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(AppColors.TEXT_PRIMARY);
        cancelButton.setBorder(BorderFactory.createLineBorder(AppColors.TEXT_PRIMARY));
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> handleCancel());

        JButton createButton = new JButton("CREATE");
        createButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        createButton.setBackground(AppColors.PRIMARY);
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createButton.addActionListener(e -> handleCreateEvent());

        // Set preferred size for buttons
        Dimension buttonSize = new Dimension(150, 40);
        cancelButton.setPreferredSize(buttonSize);
        createButton.setPreferredSize(buttonSize);

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(createButton);

        formContent.add(buttonsPanel, gbc);

        mainPanel.add(formContent, BorderLayout.CENTER);

        return mainPanel;
    }

    private void handleImageUpload() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory()) return true;
                String name = f.getName().toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".jpeg") ||
                       name.endsWith(".png") || name.endsWith(".gif");
            }
            public String getDescription() {
                return "Image Files (*.jpg, *.jpeg, *.png, *.gif)";
            }
        });

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = fileChooser.getSelectedFile();
            imageLabel.setText(selectedImageFile.getName());
        }
    }

    private void handleDocumentsUpload() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedDocuments = fileChooser.getSelectedFiles();
            documentsLabel.setText(selectedDocuments.length + " file(s) chosen");
        }
    }

    private void handleCancel() {
        // Clear form fields
        clearForm();
        
        // Notify parent that form was cancelled
        if (onSubmitCallback != null) {
            onSubmitCallback.accept(false);
        }
    }

    private void handleCreateEvent() {
        // Reset error message
        errorLabel.setText(" ");

        // Validate form
        if (eventNameField.getText().trim().isEmpty()) {
            errorLabel.setText("Please enter an event name");
            return;
        }

        if (descriptionArea.getText().trim().isEmpty()) {
            errorLabel.setText("Please enter a description");
            return;
        }

        if (dateField.getText().trim().isEmpty()) {
            errorLabel.setText("Please enter a date");
            return;
        }

        if (!isValidDateFormat(dateField.getText())) {
            errorLabel.setText("Please enter a valid date in YYYY-MM-DD format");
            return;
        }

        if (startTimeField.getText().trim().isEmpty() || endTimeField.getText().trim().isEmpty()) {
            errorLabel.setText("Please enter both start and end times");
            return;
        }

        // Validate time format (HH:MM AM/PM)
        if (!isValidTimeFormat(startTimeField.getText()) || !isValidTimeFormat(endTimeField.getText())) {
            errorLabel.setText("Please enter times in format HH:MM AM/PM (e.g., 09:00 AM)");
            return;
        }

        // Validate that end time is after start time
        if (!isEndTimeAfterStartTime(startTimeField.getText(), endTimeField.getText())) {
            errorLabel.setText("End time must be after start time");
            return;
        }

        if (venueField.getText().trim().isEmpty()) {
            errorLabel.setText("Please enter a venue");
            return;
        }

        if (contactField.getText().trim().isEmpty()) {
            errorLabel.setText("Please enter contact information");
            return;
        }

        if (eligibilityArea.getText().trim().isEmpty()) {
            errorLabel.setText("Please enter eligibility criteria");
            return;
        }

        // Show success message
        String message;
        if (userRole.equals("Admin")) {
            message = "Event has been created and published successfully!";
        } else {
            message = "Event has been submitted for approval. You will be notified once it's reviewed.";
        }

        JOptionPane.showMessageDialog(this,
            message,
            "Success",
            JOptionPane.INFORMATION_MESSAGE);

        // Clear form
        clearForm();
        
        // Notify parent that form was submitted successfully
        if (onSubmitCallback != null) {
            onSubmitCallback.accept(true);
        }
    }

    private void clearForm() {
        eventNameField.setText("");
        categoryComboBox.setSelectedIndex(0);
        descriptionArea.setText("");
        dateField.setText("");
        startTimeField.setText("");
        endTimeField.setText("");
        venueField.setText("");
        capacitySpinner.setValue(50);
        contactField.setText("");
        eligibilityArea.setText("");
        imageLabel.setText("No file chosen");
        documentsLabel.setText("No files chosen");
        selectedImageFile = null;
        selectedDocuments = null;
        errorLabel.setText(" ");
    }

    private boolean isValidDateFormat(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidTimeFormat(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            sdf.setLenient(false);
            sdf.parse(time);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isEndTimeAfterStartTime(String startTime, String endTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            Date start = sdf.parse(startTime);
            Date end = sdf.parse(endTime);
            return end.after(start);
        } catch (Exception e) {
            return false;
        }
    }
}
