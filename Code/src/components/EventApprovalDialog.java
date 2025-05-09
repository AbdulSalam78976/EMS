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
 * A dialog for approving or rejecting events
 */
public class EventApprovalDialog extends JDialog {
    private final String eventId;
    private final String eventName;
    private final String organizer;
    private final Date submissionDate;
    private final Date eventDate;
    private final String venue;
    private final String category;
    private final int capacity;
    private final String description;
    private final String eligibilityCriteria;
    private final Consumer<Boolean> onApprovalComplete;
    
    private JTextArea commentsArea;
    
    /**
     * Creates a new event approval dialog
     * 
     * @param parent The parent frame
     * @param eventId The event ID
     * @param eventName The event name
     * @param organizer The organizer name
     * @param submissionDate The submission date
     * @param eventDate The event date
     * @param venue The venue
     * @param category The category
     * @param capacity The capacity
     * @param description The description
     * @param eligibilityCriteria The eligibility criteria
     * @param onApprovalComplete Callback for when approval is complete (true for approved, false for rejected)
     */
    public EventApprovalDialog(
            JFrame parent,
            String eventId,
            String eventName,
            String organizer,
            Date submissionDate,
            Date eventDate,
            String venue,
            String category,
            int capacity,
            String description,
            String eligibilityCriteria,
            Consumer<Boolean> onApprovalComplete) {
        
        super(parent, "Event Approval - " + eventName, true);
        
        this.eventId = eventId;
        this.eventName = eventName;
        this.organizer = organizer;
        this.submissionDate = submissionDate;
        this.eventDate = eventDate;
        this.venue = venue;
        this.category = category;
        this.capacity = capacity;
        this.description = description;
        this.eligibilityCriteria = eligibilityCriteria;
        this.onApprovalComplete = onApprovalComplete;
        
        setupUI();
        
        // Set dialog properties
        setSize(900, 700);
        setLocationRelativeTo(parent);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void setupUI() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Event Header Panel
        RoundedPanel eventHeaderPanel = createSectionPanel("EVENT DETAILS");
        JPanel eventInfoPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        eventInfoPanel.setOpaque(false);
        eventInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        
        eventInfoPanel.add(createDetailRow("Event:", eventName));
        eventInfoPanel.add(createDetailRow("Organizer:", organizer));
        eventInfoPanel.add(createDetailRow("Email:", organizer.toLowerCase().replace(" ", ".") + "@example.com")); // Placeholder email
        eventInfoPanel.add(createDetailRow("Submitted:", dateFormat.format(submissionDate)));
        
        eventHeaderPanel.add(eventInfoPanel, BorderLayout.CENTER);
        eventHeaderPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(eventHeaderPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Event Details Panel
        RoundedPanel eventDetailsPanel = createSectionPanel("EVENT INFORMATION");
        JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 20, 10));
        detailsPanel.setOpaque(false);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Left column
        detailsPanel.add(createDetailRow("Event Date:", dateFormat.format(eventDate)));
        detailsPanel.add(createDetailRow("Venue:", venue));
        detailsPanel.add(createDetailRow("Category:", category));
        detailsPanel.add(createDetailRow("Capacity:", String.valueOf(capacity)));
        
        // Right column
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setOpaque(false);
        
        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JTextArea descArea = new JTextArea(description);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setBackground(new Color(245, 245, 245));
        descArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        descriptionPanel.add(descLabel, BorderLayout.NORTH);
        descriptionPanel.add(descArea, BorderLayout.CENTER);
        
        JPanel criteriaPanel = new JPanel(new BorderLayout());
        criteriaPanel.setOpaque(false);
        
        JLabel criteriaLabel = new JLabel("Eligibility Criteria:");
        criteriaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JTextArea criteriaArea = new JTextArea(eligibilityCriteria);
        criteriaArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        criteriaArea.setLineWrap(true);
        criteriaArea.setWrapStyleWord(true);
        criteriaArea.setEditable(false);
        criteriaArea.setBackground(new Color(245, 245, 245));
        criteriaArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        criteriaPanel.add(criteriaLabel, BorderLayout.NORTH);
        criteriaPanel.add(criteriaArea, BorderLayout.CENTER);
        
        // Add right column panels
        JPanel rightColumn = new JPanel(new GridLayout(2, 1, 0, 10));
        rightColumn.setOpaque(false);
        rightColumn.add(descriptionPanel);
        rightColumn.add(criteriaPanel);
        
        detailsPanel.add(rightColumn);
        
        // View documents button
        GradientButton docsButton = GradientButton.createSecondaryButton("VIEW ATTACHED DOCUMENTS");
        docsButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        docsButton.setPreferredSize(new Dimension(250, 40));
        docsButton.addActionListener(e -> JOptionPane.showMessageDialog(
            this,
            "Document viewer will be implemented here",
            "View Documents",
            JOptionPane.INFORMATION_MESSAGE
        ));
        
        JPanel docsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        docsPanel.setOpaque(false);
        docsPanel.add(docsButton);
        detailsPanel.add(docsPanel);
        
        eventDetailsPanel.add(detailsPanel, BorderLayout.CENTER);
        eventDetailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(eventDetailsPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Admin Decision Panel
        RoundedPanel decisionPanel = createSectionPanel("ADMIN DECISION");
        JPanel decisionContent = new JPanel(new BorderLayout(0, 15));
        decisionContent.setOpaque(false);
        decisionContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Comments area
        JLabel commentsLabel = new JLabel("Comments (optional):");
        commentsLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        commentsArea = new JTextArea();
        commentsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        commentsArea.setLineWrap(true);
        commentsArea.setWrapStyleWord(true);
        commentsArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane commentsScroll = new JScrollPane(commentsArea);
        commentsScroll.setPreferredSize(new Dimension(0, 100));
        
        decisionContent.add(commentsLabel, BorderLayout.NORTH);
        decisionContent.add(commentsScroll, BorderLayout.CENTER);
        
        // Decision buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        
        // Create custom gradient buttons for reject and approve
        GradientButton rejectButton = GradientButton.createDangerButton("REJECT");
        rejectButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rejectButton.setPreferredSize(new Dimension(150, 40));
        rejectButton.addActionListener(e -> handleReject());
        
        GradientButton approveButton = GradientButton.createSuccessButton("APPROVE");
        approveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        approveButton.setPreferredSize(new Dimension(150, 40));
        approveButton.addActionListener(e -> handleApprove());
        
        buttonPanel.add(rejectButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(approveButton);
        
        decisionContent.add(buttonPanel, BorderLayout.SOUTH);
        
        decisionPanel.add(decisionContent, BorderLayout.CENTER);
        decisionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(decisionPanel);
        
        // Add content to dialog
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        setContentPane(scrollPane);
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
    
    private RoundedPanel createSectionPanel(String title) {
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
    
    private void handleApprove() {
        int choice = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to approve this event?",
            "Confirm Approval",
            JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                "Event has been approved and published.",
                "Event Approved",
                JOptionPane.INFORMATION_MESSAGE);
            
            if (onApprovalComplete != null) {
                onApprovalComplete.accept(true);
            }
            
            dispose();
        }
    }
    
    private void handleReject() {
        // If no comments provided, prompt for rejection reason
        if (commentsArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please provide a reason for rejection in the comments field.",
                "Comments Required",
                JOptionPane.WARNING_MESSAGE);
            commentsArea.requestFocus();
            return;
        }
        
        int choice = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to reject this event?",
            "Confirm Rejection",
            JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                "Event has been rejected. The organizer will be notified.",
                "Event Rejected",
                JOptionPane.INFORMATION_MESSAGE);
            
            if (onApprovalComplete != null) {
                onApprovalComplete.accept(false);
            }
            
            dispose();
        }
    }
}
