package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import utils.AppColors;
import utils.UIUtils;
import components.CreateEventForm;
import components.ButtonRenderer;
import components.ButtonEditor;
import components.GradientButton;
import components.HeaderPanel;
import components.RoundedPanel;
import components.SidebarPanel;

/**
 * Organizer Dashboard
 * Implements the design from organizer_dashboard.md
 */
public class OrganizerDashboard extends JFrame {
    // Enum for tab information
    private enum TabInfo {
        HOME("🏠", "Dashboard"),
        MY_EVENTS("📅", "My Events"),
        CREATE_EVENT("➕", "Create Event"),
        PARTICIPANTS("👥", "Participants"),
        MEDIA("🖼️", "Media Upload");

        final String icon;
        final String title;

        TabInfo(String icon, String title) {
            this.icon = icon;
            this.title = title;
        }
    }

    // UI Components
    private JPanel contentPanel;
    private CardLayout contentLayout;
    private JLabel eventCountLabel;
    private JLabel pendingCountLabel;
    private JLabel approvedCountLabel;
    private JLabel rejectedCountLabel;
    private JLabel participantsCountLabel;

    // Sample data - in a real app, this would come from a database
    private final String organizerName = "John Smith";
    private final String organizerEmail = "organizer@example.com";
    private final int totalEvents = 12;
    private final int pendingEvents = 3;
    private final int approvedEvents = 8;
    private final int rejectedEvents = 1;
    private final int totalParticipants = 245;

    public OrganizerDashboard() {
        setTitle("Event Management System - Organizer Dashboard");
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

        // Create sidebar panel
        JPanel sidebarPanel = createSidebarPanel();
        mainPanel.add(sidebarPanel, BorderLayout.WEST);

        // Create content panel with card layout
        contentPanel = new JPanel();
        contentLayout = new CardLayout();
        contentPanel.setLayout(contentLayout);

        // Add content cards
        contentPanel.add(createDashboardPanel(), TabInfo.HOME.title);
        contentPanel.add(createMyEventsPanel(), TabInfo.MY_EVENTS.title);
        contentPanel.add(createCreateEventPanel(), TabInfo.CREATE_EVENT.title);
        contentPanel.add(createParticipantsPanel(), TabInfo.PARTICIPANTS.title);
        contentPanel.add(createMediaUploadPanel(), TabInfo.MEDIA.title);

        // Show the dashboard by default
        contentLayout.show(contentPanel, TabInfo.HOME.title);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Set the main panel as the content pane
        setContentPane(mainPanel);
    }

    private JPanel createHeaderPanel() {
        // Use the reusable HeaderPanel component with create event button
        return new HeaderPanel(
            organizerName,
            "External Event Organizer",
            true,
            v -> contentLayout.show(contentPanel, TabInfo.CREATE_EVENT.title)
        );
    }

    private JPanel createSidebarPanel() {
        // Create a new sidebar panel using our reusable component
        SidebarPanel sidebarPanel = new SidebarPanel(
            contentLayout,
            contentPanel,
            organizerName,
            "External Event Organizer"
        );

        // Add navigation buttons for each tab
        for (TabInfo tab : TabInfo.values()) {
            sidebarPanel.addNavButton(tab.title, tab.icon, tab.title, tab == TabInfo.HOME);
        }

        // Add logout button
        sidebarPanel.addLogoutButton(e -> handleLogout());

        return sidebarPanel;
    }

    private JPanel createDashboardPanel() {
        JPanel dashboardPanel = new JPanel(new BorderLayout(20, 20));
        dashboardPanel.setBackground(AppColors.BACKGROUND_LIGHT);
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Welcome section with rounded corners
        RoundedPanel welcomePanel = new RoundedPanel(new BorderLayout(), AppColors.PRIMARY_LIGHT, UIUtils.CORNER_RADIUS);
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        String currentDate = dateFormat.format(new Date());

        JLabel dateLabel = new JLabel(currentDate);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateLabel.setForeground(AppColors.TEXT_DARK);

        JLabel welcomeLabel = new JLabel("Welcome back, " + organizerName + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(AppColors.TEXT_DARK);

        JLabel summaryLabel = new JLabel("Here's a summary of your events and activities");
        summaryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        summaryLabel.setForeground(AppColors.TEXT_DARK);

        JPanel welcomeTextPanel = new JPanel();
        welcomeTextPanel.setLayout(new BoxLayout(welcomeTextPanel, BoxLayout.Y_AXIS));
        welcomeTextPanel.setOpaque(false);
        welcomeTextPanel.add(dateLabel);
        welcomeTextPanel.add(Box.createVerticalStrut(10));
        welcomeTextPanel.add(welcomeLabel);
        welcomeTextPanel.add(Box.createVerticalStrut(5));
        welcomeTextPanel.add(summaryLabel);

        welcomePanel.add(welcomeTextPanel, BorderLayout.CENTER);

        // Stats section
        JPanel statsPanel = new JPanel(new GridLayout(1, 5, 15, 0));
        statsPanel.setOpaque(false);

        // Event count
        JPanel eventCountPanel = createStatPanel("Total Events", totalEvents, AppColors.PRIMARY);
        eventCountLabel = (JLabel) eventCountPanel.getComponent(1);

        // Pending count
        JPanel pendingPanel = createStatPanel("Pending", pendingEvents, AppColors.WARNING);
        pendingCountLabel = (JLabel) pendingPanel.getComponent(1);

        // Approved count
        JPanel approvedPanel = createStatPanel("Approved", approvedEvents, AppColors.SUCCESS);
        approvedCountLabel = (JLabel) approvedPanel.getComponent(1);

        // Rejected count
        JPanel rejectedPanel = createStatPanel("Rejected", rejectedEvents, AppColors.DANGER);
        rejectedCountLabel = (JLabel) rejectedPanel.getComponent(1);

        // Participants count
        JPanel participantsPanel = createStatPanel("Participants", totalParticipants, AppColors.INFO);
        participantsCountLabel = (JLabel) participantsPanel.getComponent(1);

        statsPanel.add(eventCountPanel);
        statsPanel.add(pendingPanel);
        statsPanel.add(approvedPanel);
        statsPanel.add(rejectedPanel);
        statsPanel.add(participantsPanel);

        // Recent events section with rounded corners
        RoundedPanel recentEventsPanel = new RoundedPanel(new BorderLayout(), Color.WHITE, UIUtils.CORNER_RADIUS);
        recentEventsPanel.setShadow(3);
        recentEventsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel recentEventsLabel = new JLabel("Recent Events");
        recentEventsLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        // Sample table data - in a real app, this would come from a database
        String[] columnNames = {"Event Name", "Date", "Location", "Status", "Participants"};
        Object[][] data = {
            {"Tech Conference 2023", "2023-10-15", "Convention Center", "Approved", 120},
            {"Charity Gala", "2023-09-30", "Grand Hotel", "Approved", 85},
            {"Product Launch", "2023-11-05", "Innovation Hub", "Pending", 0},
            {"Workshop Series", "2023-10-22", "Training Center", "Approved", 40}
        };

        JTable recentEventsTable = new JTable(data, columnNames);
        recentEventsTable.setRowHeight(30);
        recentEventsTable.setShowGrid(false);
        recentEventsTable.setIntercellSpacing(new Dimension(0, 0));
        recentEventsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        recentEventsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane tableScrollPane = new JScrollPane(recentEventsTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

        recentEventsPanel.add(recentEventsLabel, BorderLayout.NORTH);
        recentEventsPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Quick actions section with rounded corners
        RoundedPanel quickActionsPanel = new RoundedPanel(new BorderLayout(), Color.WHITE, UIUtils.CORNER_RADIUS);
        quickActionsPanel.setShadow(3);
        quickActionsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel quickActionsLabel = new JLabel("Quick Actions");
        quickActionsLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel actionsButtonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        actionsButtonPanel.setOpaque(false);

        JButton createEventButton = createActionButton("Create New Event", "➕");
        JButton manageEventsButton = createActionButton("Manage Events", "📋");
        JButton viewParticipantsButton = createActionButton("View Participants", "👥");
        JButton uploadMediaButton = createActionButton("Upload Media", "🖼️");

        actionsButtonPanel.add(createEventButton);
        actionsButtonPanel.add(manageEventsButton);
        actionsButtonPanel.add(viewParticipantsButton);
        actionsButtonPanel.add(uploadMediaButton);

        quickActionsPanel.add(quickActionsLabel, BorderLayout.NORTH);
        quickActionsPanel.add(actionsButtonPanel, BorderLayout.CENTER);

        // Layout the dashboard components
        JPanel topPanel = new JPanel(new BorderLayout(0, 20));
        topPanel.setOpaque(false);
        topPanel.add(welcomePanel, BorderLayout.NORTH);
        topPanel.add(statsPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.add(recentEventsPanel);
        bottomPanel.add(quickActionsPanel);

        dashboardPanel.add(topPanel, BorderLayout.NORTH);
        dashboardPanel.add(bottomPanel, BorderLayout.CENTER);

        return dashboardPanel;
    }

    private JPanel createStatPanel(String title, int value, Color color) {
        // Create a rounded panel with shadow
        RoundedPanel panel = new RoundedPanel(new BoxLayout(null, BoxLayout.Y_AXIS), Color.WHITE, UIUtils.CORNER_RADIUS);
        panel.setShadow(3);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(AppColors.TEXT_DARK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valueLabel = new JLabel(String.valueOf(value));
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(valueLabel);

        return panel;
    }

    private JButton createActionButton(String text, String icon) {
        // Create a gradient button with primary colors
        GradientButton button = GradientButton.createPrimaryButton(icon + " " + text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 45));

        // Add action listener
        button.addActionListener(e -> {
            if (text.equals("Create New Event")) {
                contentLayout.show(contentPanel, TabInfo.CREATE_EVENT.title);
            } else if (text.equals("Manage Events")) {
                contentLayout.show(contentPanel, TabInfo.MY_EVENTS.title);
            } else if (text.equals("View Participants")) {
                contentLayout.show(contentPanel, TabInfo.PARTICIPANTS.title);
            } else if (text.equals("Upload Media")) {
                contentLayout.show(contentPanel, TabInfo.MEDIA.title);
            }
        });

        return button;
    }

    private JPanel createMyEventsPanel() {
        JPanel myEventsPanel = new JPanel(new BorderLayout(0, 20));
        myEventsPanel.setBackground(AppColors.BACKGROUND_LIGHT);
        myEventsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header with title and create button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("My Events");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));

        GradientButton createButton = GradientButton.createPrimaryButton("Create New Event");
        createButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        createButton.setPreferredSize(new Dimension(180, 40));
        createButton.addActionListener(e -> contentLayout.show(contentPanel, TabInfo.CREATE_EVENT.title));

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(createButton, BorderLayout.EAST);

        // Filter and search panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setOpaque(false);

        String[] statusOptions = {"All Events", "Pending", "Approved", "Rejected"};
        JComboBox<String> statusFilter = UIUtils.createRoundedComboBox(statusOptions);

        JTextField searchField = UIUtils.createRoundedTextField();
        searchField.setColumns(20);

        GradientButton searchButton = GradientButton.createSecondaryButton("Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setPreferredSize(new Dimension(100, 35));

        filterPanel.add(new JLabel("Status: "));
        filterPanel.add(statusFilter);
        filterPanel.add(Box.createHorizontalStrut(20));
        filterPanel.add(searchField);
        filterPanel.add(searchButton);

        // Events table
        String[] columnNames = {"Event Name", "Date", "Location", "Status", "Participants", "Actions"};
        Object[][] data = {
            {"Tech Conference 2023", "2023-10-15", "Convention Center", "Approved", 120, ""},
            {"Charity Gala", "2023-09-30", "Grand Hotel", "Approved", 85, ""},
            {"Product Launch", "2023-11-05", "Innovation Hub", "Pending", 0, ""},
            {"Workshop Series", "2023-10-22", "Training Center", "Approved", 40, ""},
            {"Annual Meeting", "2023-12-10", "Corporate HQ", "Pending", 0, ""},
            {"Industry Mixer", "2023-11-18", "Downtown Venue", "Rejected", 0, ""}
        };

        JTable eventsTable = new JTable(data, columnNames);
        eventsTable.setRowHeight(40);
        eventsTable.setShowGrid(false);
        eventsTable.setIntercellSpacing(new Dimension(0, 0));
        eventsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        eventsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Add action buttons to the table
        eventsTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        eventsTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane tableScrollPane = new JScrollPane(eventsTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Add components to the panel
        myEventsPanel.add(headerPanel, BorderLayout.NORTH);
        myEventsPanel.add(filterPanel, BorderLayout.CENTER);
        myEventsPanel.add(tableScrollPane, BorderLayout.SOUTH);

        return myEventsPanel;
    }

    private JPanel createCreateEventPanel() {
        JPanel createEventPanel = new JPanel(new BorderLayout(0, 20));
        createEventPanel.setBackground(AppColors.BACKGROUND_LIGHT);
        createEventPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Use the reusable CreateEventForm component
        CreateEventForm eventForm = new CreateEventForm("Organizer", success -> {
            // If form was submitted successfully, switch back to dashboard
            if (success) {
                contentLayout.show(contentPanel, TabInfo.HOME.title);
            }
        });

        createEventPanel.add(eventForm, BorderLayout.CENTER);

        return createEventPanel;
    }

    private JPanel createParticipantsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Participants");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JLabel placeholderLabel = new JLabel("Participants management will be implemented here", SwingConstants.CENTER);
        placeholderLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        panel.add(placeholderLabel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createMediaUploadPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Media Upload");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JLabel placeholderLabel = new JLabel("Media upload functionality will be implemented here", SwingConstants.CENTER);
        placeholderLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        panel.add(placeholderLabel, BorderLayout.CENTER);

        return panel;
    }

    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            SwingUtilities.invokeLater(LoginScreen::new);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(OrganizerDashboard::new);
    }
}
