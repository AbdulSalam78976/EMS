package screens;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import utils.AppColors;
import utils.UIUtils;
import components.EventRegistrationDialog;
import components.GradientButton;
import components.HeaderPanel;
import components.RoundedPanel;

/**
 * Event Details Screen
 * Implements the design from event_details.md with modern UI components
 */
@SuppressWarnings("unused")
public class EventDetailsScreen extends JFrame {
    private final String eventId;
    private final String eventName;
    private final Date eventDate;
    private final String venue;
    private final int availableSlots;
    private final int totalSlots;
    private final String description;
    private final String category;
    private final String organizer;
    private final String contactInfo;
    private final String eligibilityCriteria;
    private final String[] schedule;
    private final boolean isRegistered;

    public EventDetailsScreen(
            String eventId,
            String eventName,
            Date eventDate,
            String venue,
            int availableSlots,
            int totalSlots,
            String description,
            String category,
            String organizer,
            String contactInfo,
            String eligibilityCriteria,
            String[] schedule,
            boolean isRegistered) {

        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.venue = venue;
        this.availableSlots = availableSlots;
        this.totalSlots = totalSlots;
        this.description = description;
        this.category = category;
        this.organizer = organizer;
        this.contactInfo = contactInfo;
        this.eligibilityCriteria = eligibilityCriteria;
        this.schedule = schedule;
        this.isRegistered = isRegistered;

        setTitle("Event Details - " + eventName);
        setSize(1200, 800);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setupUI();
        setVisible(true);
    }

    private void setupUI() {
        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppColors.BACKGROUND_LIGHT);

        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Create content panel
        JPanel contentPanel = createContentPanel();

        // Wrap content in scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Set the main panel as the content pane
        setContentPane(mainPanel);
    }

    private JPanel createHeaderPanel() {
        // Use the reusable HeaderPanel component
        return new HeaderPanel("User", "Attendee");
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(AppColors.BACKGROUND_LIGHT);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Navigation panel
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navPanel.setOpaque(false);
        navPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        GradientButton backButton = GradientButton.createSecondaryButton("< Back to Events");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> handleBack());

        navPanel.add(backButton);
        contentPanel.add(navPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        // Event header panel
        JPanel eventHeaderPanel = createEventHeaderPanel();
        eventHeaderPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(eventHeaderPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        // Description section
        JPanel descriptionPanel = createSectionPanel("Description");
        JTextArea descriptionText = new JTextArea(description);
        descriptionText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descriptionText.setLineWrap(true);
        descriptionText.setWrapStyleWord(true);
        descriptionText.setEditable(false);
        descriptionText.setBackground(null);
        descriptionText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        descriptionPanel.add(descriptionText, BorderLayout.CENTER);
        descriptionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(descriptionPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        // Event details section
        JPanel detailsPanel = createSectionPanel("Event Details");
        JPanel detailsContent = new JPanel(new GridLayout(0, 1, 0, 10));
        detailsContent.setOpaque(false);
        detailsContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        detailsContent.add(createDetailRow("Category:", category));
        detailsContent.add(createDetailRow("Organizer:", organizer));
        detailsContent.add(createDetailRow("Contact:", contactInfo));

        detailsPanel.add(detailsContent, BorderLayout.CENTER);
        detailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(detailsPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        // Eligibility criteria section
        JPanel eligibilityPanel = createSectionPanel("Eligibility Criteria");
        JTextArea eligibilityText = new JTextArea(eligibilityCriteria);
        eligibilityText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        eligibilityText.setLineWrap(true);
        eligibilityText.setWrapStyleWord(true);
        eligibilityText.setEditable(false);
        eligibilityText.setBackground(null);
        eligibilityText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        eligibilityPanel.add(eligibilityText, BorderLayout.CENTER);
        eligibilityPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(eligibilityPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        // Schedule section
        if (schedule != null && schedule.length > 0) {
            JPanel schedulePanel = createSectionPanel("Schedule");
            JPanel scheduleContent = new JPanel(new GridLayout(0, 1, 0, 10));
            scheduleContent.setOpaque(false);
            scheduleContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            for (String item : schedule) {
                JLabel scheduleItem = new JLabel(item);
                scheduleItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                scheduleContent.add(scheduleItem);
            }

            schedulePanel.add(scheduleContent, BorderLayout.CENTER);
            schedulePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(schedulePanel);
        }

        return contentPanel;
    }

    private JPanel createEventHeaderPanel() {
        RoundedPanel panel = new RoundedPanel(new BorderLayout(20, 0), Color.WHITE, UIUtils.CORNER_RADIUS);
        panel.setShadow(3);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Event image placeholder
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(200, 200, 200));
        imagePanel.setPreferredSize(new Dimension(200, 200));
        panel.add(imagePanel, BorderLayout.WEST);

        // Event details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setOpaque(false);

        // Event title
        JLabel titleLabel = new JLabel(eventName);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        detailsPanel.add(titleLabel);
        detailsPanel.add(Box.createVerticalStrut(15));

        // Event date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

        JLabel dateLabel = new JLabel("📅 " + dateFormat.format(eventDate));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        detailsPanel.add(dateLabel);
        detailsPanel.add(Box.createVerticalStrut(5));

        JLabel timeLabel = new JLabel("🕒 " + timeFormat.format(eventDate) + " - " + timeFormat.format(new Date(eventDate.getTime() + 3600000))); // Placeholder end time
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        detailsPanel.add(timeLabel);
        detailsPanel.add(Box.createVerticalStrut(5));

        JLabel venueLabel = new JLabel("📍 " + venue);
        venueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        detailsPanel.add(venueLabel);
        detailsPanel.add(Box.createVerticalStrut(5));

        JLabel slotsLabel = new JLabel("👥 " + availableSlots + "/" + totalSlots + " Slots Available");
        slotsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        detailsPanel.add(slotsLabel);
        detailsPanel.add(Box.createVerticalStrut(20));

        // Register button with gradient
        GradientButton registerButton;
        if (isRegistered) {
            registerButton = GradientButton.createDangerButton("UNREGISTER");
        } else {
            registerButton = GradientButton.createPrimaryButton("REGISTER");
        }
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        registerButton.setPreferredSize(new Dimension(150, 40));
        registerButton.addActionListener(e -> handleRegistration());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(registerButton);
        detailsPanel.add(buttonPanel);

        panel.add(detailsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createSectionPanel(String title) {
        // Create a rounded panel with shadow
        RoundedPanel panel = new RoundedPanel(new BorderLayout(), Color.WHITE, UIUtils.CORNER_RADIUS);
        panel.setShadow(3);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Section title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        panel.add(titleLabel, BorderLayout.NORTH);

        return panel;
    }

    private JPanel createDetailRow(String label, String value) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setOpaque(false);

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panel.add(labelComponent);
        panel.add(valueComponent);

        return panel;
    }

    private void handleRegistration() {
        if (isRegistered) {
            int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel your registration for this event?",
                "Confirm Unregistration",
                JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this,
                    "You have successfully unregistered from this event.",
                    "Unregistration Successful",
                    JOptionPane.INFORMATION_MESSAGE);

                // Return to dashboard
                handleBack();
            }
        } else {
            // Show registration dialog instead of navigating to a new screen
            EventRegistrationDialog dialog = new EventRegistrationDialog(
                this,
                eventId,
                eventName,
                eventDate,
                venue,
                availableSlots,
                eligibilityCriteria,
                success -> {
                    if (success) {
                        // Registration was successful, return to dashboard
                        handleBack();
                    }
                }
            );

            dialog.setVisible(true);
        }
    }

    private void handleBack() {
        dispose();
        SwingUtilities.invokeLater(AttendeeDashboardNew::new);
    }



    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Sample data for testing
        SwingUtilities.invokeLater(() -> new EventDetailsScreen(
            "EVENT001",
            "Tech Conference 2024",
            new Date(),
            "Main Hall",
            120,
            150,
            "Join us for the biggest tech conference of the year featuring industry experts and cutting-edge technologies. Network with professionals and learn about the latest trends in technology.",
            "Conference",
            "Tech Association",
            "organizer@example.com",
            "- Open to all students\n- Must have valid student ID",
            new String[] {
                "09:00 AM - 10:00 AM: Registration",
                "10:00 AM - 12:00 PM: Keynote Speeches",
                "12:00 PM - 01:00 PM: Lunch Break",
                "01:00 PM - 03:00 PM: Workshop Sessions",
                "03:00 PM - 04:00 PM: Panel Discussion",
                "04:00 PM - 05:00 PM: Networking"
            },
            false
        ));
    }
}
