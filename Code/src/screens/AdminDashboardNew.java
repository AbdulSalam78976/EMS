package screens;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import utils.AppColors;
import utils.UIUtils;
import components.CreateEventForm;
import components.ButtonRenderer;
import components.ButtonEditor;
import components.EventApprovalDialog;
import components.GradientButton;
import components.HeaderPanel;
import components.RoundedPanel;
import components.SidebarPanel;

/**
 * Dashboard screen for Administrators
 * Implements the design from admin_dashboard.md
 */
@SuppressWarnings("unused")
public class AdminDashboardNew extends JFrame {
    private JPanel mainContentPanel;
    private CardLayout cardLayout;
    private String username = "Admin"; // This would come from user authentication

    public AdminDashboardNew() {
        setTitle("Event Management System - Admin Dashboard");
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

        // Create main content panel with card layout
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(Color.WHITE);

        // Add different content panels
        mainContentPanel.add(createDashboardPanel(), "Dashboard");
        mainContentPanel.add(createPendingApprovalsPanel(), "Pending Approvals");
        mainContentPanel.add(createAllEventsPanel(), "All Events");
        mainContentPanel.add(createUsersPanel(), "Registered Users");
        mainContentPanel.add(createSettingsPanel(), "System Settings");

        mainPanel.add(mainContentPanel, BorderLayout.CENTER);

        // Set the main panel as the content pane
        setContentPane(mainPanel);
    }

    private JPanel createHeaderPanel() {
        // Use the reusable HeaderPanel component with create event button
        return new HeaderPanel(
            username,
            "Administrator",
            true,
            v -> showCreateEventDialog()
        );
    }

    private JPanel createSidebarPanel() {
        // Create a new sidebar panel using our improved component
        SidebarPanel sidebarPanel = new SidebarPanel(
            cardLayout,
            mainContentPanel,
            username,
            "Administrator"
        );

        // Main navigation items with icons
        sidebarPanel.addNavButton("Dashboard", "📊", "Dashboard", true);
        sidebarPanel.addNavButton("Pending Approvals", "⏳", "Pending Approvals", false);
        sidebarPanel.addNavButton("All Events", "📅", "All Events", false);
        sidebarPanel.addNavButton("Registered Users", "👥", "Registered Users", false);

        // Add system section
        sidebarPanel.addSectionLabel("SYSTEM");
        sidebarPanel.addNavButton("System Settings", "⚙️", "System Settings", false);

        // Add action listeners for navigation buttons
        sidebarPanel.addNavButtonActionListener("Dashboard", e -> {
            // Refresh dashboard data if needed
            System.out.println("Dashboard selected");
        });

        sidebarPanel.addNavButtonActionListener("Pending Approvals", e -> {
            // Refresh pending approvals data
            System.out.println("Pending Approvals selected");
            refreshPendingApprovals();
        });

        sidebarPanel.addNavButtonActionListener("All Events", e -> {
            // Refresh all events data
            System.out.println("All Events selected");
            loadAllEvents();
        });

        sidebarPanel.addNavButtonActionListener("Registered Users", e -> {
            // Refresh registered users data
            System.out.println("Registered Users selected");
            loadRegisteredUsers();
        });

        sidebarPanel.addNavButtonActionListener("System Settings", e -> {
            // Load system settings
            System.out.println("System Settings selected");
            // No need to call a specific method as the settings are already loaded in the panel
        });

        // Add logout button
        sidebarPanel.addLogoutButton(e -> handleLogout());

        return sidebarPanel;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Dashboard title
        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setOpaque(false);

        // Stats Panel
        JPanel statsPanel = createStatsPanel();
        contentPanel.add(statsPanel, BorderLayout.NORTH);

        // Bottom panel with pending approvals and recent activity
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        bottomPanel.setOpaque(false);

        // Pending Approvals Panel
        JPanel pendingPanel = createSectionPanel("PENDING APPROVALS");
        pendingPanel.add(createPendingApprovalsList(), BorderLayout.CENTER);

        GradientButton viewAllPendingBtn = GradientButton.createPrimaryButton("VIEW ALL PENDING");
        viewAllPendingBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        viewAllPendingBtn.setPreferredSize(new Dimension(200, 40));
        viewAllPendingBtn.addActionListener(e -> cardLayout.show(mainContentPanel, "Pending Approvals"));

        JPanel viewAllPendingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        viewAllPendingPanel.setOpaque(false);
        viewAllPendingPanel.add(viewAllPendingBtn);
        pendingPanel.add(viewAllPendingPanel, BorderLayout.SOUTH);

        // Recent Activity Panel
        JPanel activityPanel = createSectionPanel("RECENT ACTIVITY");
        activityPanel.add(createRecentActivityList(), BorderLayout.CENTER);

        GradientButton viewAllActivityBtn = GradientButton.createPrimaryButton("VIEW ALL ACTIVITY");
        viewAllActivityBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        viewAllActivityBtn.setPreferredSize(new Dimension(200, 40));

        JPanel viewAllActivityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        viewAllActivityPanel.setOpaque(false);
        viewAllActivityPanel.add(viewAllActivityBtn);
        activityPanel.add(viewAllActivityPanel, BorderLayout.SOUTH);

        bottomPanel.add(pendingPanel);
        bottomPanel.add(activityPanel);

        contentPanel.add(bottomPanel, BorderLayout.CENTER);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Add stat cards
        addStatCard(statsPanel, "Pending Approvals", "12", AppColors.WARNING);
        addStatCard(statsPanel, "Upcoming Events", "8", new Color(33, 150, 243)); // Blue
        addStatCard(statsPanel, "Registered Users", "245", AppColors.SUCCESS);
        addStatCard(statsPanel, "Today's Events", "3", new Color(156, 39, 176)); // Purple
        addStatCard(statsPanel, "Total Events", "42", AppColors.ERROR);
        addStatCard(statsPanel, "System Health", "Good", new Color(0, 150, 136)); // Teal

        return statsPanel;
    }

    private void addStatCard(JPanel parent, String title, String value, Color color) {
        // Create a rounded panel with shadow
        RoundedPanel card = new RoundedPanel(new BorderLayout(), Color.WHITE, UIUtils.CORNER_RADIUS);
        card.setShadow(3);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        parent.add(card);
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
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        panel.add(titleLabel, BorderLayout.NORTH);

        return panel;
    }

    private JPanel createPendingApprovalsList() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        // Sample pending approvals
        String[][] pendingEvents = {
            {"Tech Conference 2024", "org1@example.com", "REVIEW"},
            {"Workshop on AI", "org2@example.com", "REVIEW"},
            {"Career Fair", "org3@example.com", "REVIEW"}
        };

        for (String[] event : pendingEvents) {
            JPanel itemPanel = new JPanel(new BorderLayout(10, 0));
            itemPanel.setOpaque(false);
            itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            textPanel.setOpaque(false);

            JLabel eventLabel = new JLabel("• " + event[0]);
            eventLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

            JLabel orgLabel = new JLabel("  by " + event[1]);
            orgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            orgLabel.setForeground(AppColors.TEXT_SECONDARY);

            textPanel.add(eventLabel);
            textPanel.add(orgLabel);

            GradientButton reviewButton = GradientButton.createPrimaryButton(event[2]);
            reviewButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
            reviewButton.setPreferredSize(new Dimension(100, 30));
            reviewButton.addActionListener(e -> showEventApprovalDialog(event[0]));

            itemPanel.add(textPanel, BorderLayout.CENTER);
            itemPanel.add(reviewButton, BorderLayout.EAST);

            panel.add(itemPanel);
        }

        return panel;
    }

    private JPanel createRecentActivityList() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        // Sample activities
        String[][] activities = {
            {"John Doe registered for AI Workshop", "2 minutes ago"},
            {"Sarah Smith created Music Festival event", "15 minutes ago"},
            {"Mike Johnson cancelled registration for Career Fair", "1 hour ago"}
        };

        for (String[] activity : activities) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            itemPanel.setOpaque(false);
            itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

            JLabel activityLabel = new JLabel("• " + activity[0]);
            activityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            JLabel timeLabel = new JLabel("  " + activity[1]);
            timeLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            timeLabel.setForeground(AppColors.TEXT_SECONDARY);

            itemPanel.add(activityLabel);
            itemPanel.add(timeLabel);

            panel.add(itemPanel);
        }

        return panel;
    }

    private JPanel createPendingApprovalsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header panel with title
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        // Panel title
        JLabel titleLabel = new JLabel("Pending Event Approvals");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        panel.add(headerPanel, BorderLayout.NORTH);

        // Create table for pending events
        String[] columns = {"Event ID", "Event Name", "Organizer", "Date", "Venue", "Actions"};
        Object[][] data = {
            {"E1001", "Tech Conference 2024", "org1@example.com", "2024-03-15", "Main Hall", "Review"},
            {"E1002", "Workshop on AI", "org2@example.com", "2024-03-20", "Room 101", "Review"},
            {"E1003", "Career Fair", "org3@example.com", "2024-04-01", "Auditorium", "Review"}
        };

        // Create a custom table model that makes the last column non-editable
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only the action column is editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };

        JTable pendingTable = new JTable(model);
        pendingTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pendingTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        pendingTable.setRowHeight(50);
        pendingTable.setShowGrid(false);
        pendingTable.setIntercellSpacing(new Dimension(0, 0));

        // Set column widths
        pendingTable.getColumnModel().getColumn(0).setPreferredWidth(80); // ID
        pendingTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Name
        pendingTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Organizer
        pendingTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Date
        pendingTable.getColumnModel().getColumn(4).setPreferredWidth(150); // Venue
        pendingTable.getColumnModel().getColumn(5).setPreferredWidth(120); // Actions

        // Custom renderer for action column
        pendingTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());

        // Custom editor for action column
        ButtonEditor buttonEditor = new ButtonEditor(new JCheckBox());
        buttonEditor.setActionListener(e -> {
            // Get the event name from the table
            String eventName = (String) pendingTable.getValueAt(buttonEditor.getCurrentRow(), 1);

            // Show the approval dialog
            showEventApprovalDialog(eventName);
        });

        pendingTable.getColumnModel().getColumn(5).setCellEditor(buttonEditor);

        // Create a rounded panel for the table
        RoundedPanel tablePanel = new RoundedPanel(new BorderLayout(), Color.WHITE, UIUtils.CORNER_RADIUS);
        tablePanel.setShadow(3);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Add refresh button at the top
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        GradientButton refreshButton = GradientButton.createSecondaryButton("Refresh");
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshButton.setPreferredSize(new Dimension(120, 40));
        refreshButton.addActionListener(e -> refreshPendingApprovals());

        buttonPanel.add(refreshButton);
        tablePanel.add(buttonPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(pendingTable);
        scrollPane.setBorder(null);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    // Improved panel methods with rounded panels
    private JPanel createAllEventsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel title
        JLabel titleLabel = new JLabel("All Events");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Create a rounded panel for the content
        RoundedPanel contentPanel = new RoundedPanel(new BorderLayout(), Color.WHITE, UIUtils.CORNER_RADIUS);
        contentPanel.setShadow(3);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create table for all events
        String[] columns = {"Event ID", "Event Name", "Date", "Venue", "Capacity", "Status", "Actions"};
        Object[][] data = {
            {"E1001", "Tech Conference 2024", "2024-03-15", "Main Hall", "150/200", "Active", "View"},
            {"E1002", "Workshop on AI", "2024-03-20", "Room 101", "45/50", "Active", "View"},
            {"E1003", "Career Fair", "2024-04-01", "Auditorium", "120/300", "Active", "View"},
            {"E1004", "Music Festival", "2024-04-15", "Central Park", "500/1000", "Active", "View"},
            {"E1005", "Coding Bootcamp", "2024-05-01", "Tech Hub", "30/30", "Full", "View"}
        };

        // Create a custom table model
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only the action column is editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };

        JTable eventsTable = new JTable(model);
        eventsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        eventsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        eventsTable.setRowHeight(50);
        eventsTable.setShowGrid(false);
        eventsTable.setIntercellSpacing(new Dimension(0, 0));

        // Set column widths
        eventsTable.getColumnModel().getColumn(0).setPreferredWidth(80); // ID
        eventsTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Name
        eventsTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Date
        eventsTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Venue
        eventsTable.getColumnModel().getColumn(4).setPreferredWidth(80); // Capacity
        eventsTable.getColumnModel().getColumn(5).setPreferredWidth(80); // Status
        eventsTable.getColumnModel().getColumn(6).setPreferredWidth(80); // Actions

        // Custom renderer for action column
        eventsTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());

        // Custom editor for action column
        ButtonEditor buttonEditor = new ButtonEditor(new JCheckBox());
        buttonEditor.setActionListener(e -> {
            // Get the event name from the table
            String eventName = (String) eventsTable.getValueAt(buttonEditor.getCurrentRow(), 1);

            // Show event details
            showEventDetails(eventName);
        });

        eventsTable.getColumnModel().getColumn(6).setCellEditor(buttonEditor);

        JScrollPane scrollPane = new JScrollPane(eventsTable);
        scrollPane.setBorder(null);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Add search and filter panel at the top
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppColors.BORDER_LIGHT),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        GradientButton searchButton = GradientButton.createPrimaryButton("Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setPreferredSize(new Dimension(100, 30));

        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        contentPanel.add(searchPanel, BorderLayout.NORTH);

        // Add refresh button at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        GradientButton refreshButton = GradientButton.createSecondaryButton("Refresh");
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshButton.setPreferredSize(new Dimension(120, 40));
        refreshButton.addActionListener(e -> loadAllEvents());

        buttonPanel.add(refreshButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Loads all events data into the All Events panel
     */
    private void loadAllEvents() {
        // In a real application, this would load data from the database
        System.out.println("Loading all events data");

        // For now, we'll just show a message
        JOptionPane.showMessageDialog(this,
            "Events data refreshed successfully!",
            "Data Refreshed",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows details for a specific event
     *
     * @param eventName The name of the event to show details for
     */
    private void showEventDetails(String eventName) {
        // In a real application, this would show a dialog with event details
        // or navigate to an event details screen
        System.out.println("Showing details for event: " + eventName);

        // For now, we'll just show a message
        JOptionPane.showMessageDialog(this,
            "Event Details for: " + eventName,
            "Event Details",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel title
        JLabel titleLabel = new JLabel("Registered Users");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Create a rounded panel for the content
        RoundedPanel contentPanel = new RoundedPanel(new BorderLayout(), Color.WHITE, UIUtils.CORNER_RADIUS);
        contentPanel.setShadow(3);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create table for registered users
        String[] columns = {"User ID", "Name", "Email", "Role", "Registered Date", "Status", "Actions"};
        Object[][] data = {
            {"U1001", "John Doe", "john.doe@example.com", "Attendee", "2024-01-15", "Active", "View"},
            {"U1002", "Jane Smith", "jane.smith@example.com", "Attendee", "2024-01-20", "Active", "View"},
            {"U1003", "Bob Johnson", "bob.johnson@example.com", "Attendee", "2024-02-01", "Active", "View"},
            {"U1004", "Alice Brown", "alice.brown@example.com", "Attendee", "2024-02-15", "Active", "View"},
            {"U1005", "Charlie Davis", "charlie.davis@example.com", "External Organizer", "2024-01-10", "Active", "View"}
        };

        // Create a custom table model
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only the action column is editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };

        JTable usersTable = new JTable(model);
        usersTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usersTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        usersTable.setRowHeight(50);
        usersTable.setShowGrid(false);
        usersTable.setIntercellSpacing(new Dimension(0, 0));

        // Set column widths
        usersTable.getColumnModel().getColumn(0).setPreferredWidth(80); // ID
        usersTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Name
        usersTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Email
        usersTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Role
        usersTable.getColumnModel().getColumn(4).setPreferredWidth(120); // Registered Date
        usersTable.getColumnModel().getColumn(5).setPreferredWidth(80); // Status
        usersTable.getColumnModel().getColumn(6).setPreferredWidth(80); // Actions

        // Custom renderer for action column
        usersTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());

        // Custom editor for action column
        ButtonEditor buttonEditor = new ButtonEditor(new JCheckBox());
        buttonEditor.setActionListener(e -> {
            // Get the user name from the table
            String userName = (String) usersTable.getValueAt(buttonEditor.getCurrentRow(), 1);

            // Show user details
            showUserDetails(userName);
        });

        usersTable.getColumnModel().getColumn(6).setCellEditor(buttonEditor);

        JScrollPane scrollPane = new JScrollPane(usersTable);
        scrollPane.setBorder(null);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Add search and filter panel at the top
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppColors.BORDER_LIGHT),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        GradientButton searchButton = GradientButton.createPrimaryButton("Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setPreferredSize(new Dimension(100, 30));

        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        contentPanel.add(searchPanel, BorderLayout.NORTH);

        // Add refresh button at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        GradientButton refreshButton = GradientButton.createSecondaryButton("Refresh");
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshButton.setPreferredSize(new Dimension(120, 40));
        refreshButton.addActionListener(e -> loadRegisteredUsers());

        buttonPanel.add(refreshButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Loads registered users data into the Registered Users panel
     */
    private void loadRegisteredUsers() {
        // In a real application, this would load data from the database
        System.out.println("Loading registered users data");

        // For now, we'll just show a message
        JOptionPane.showMessageDialog(this,
            "User data refreshed successfully!",
            "Data Refreshed",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows details for a specific user
     *
     * @param userName The name of the user to show details for
     */
    private void showUserDetails(String userName) {
        // In a real application, this would show a dialog with user details
        System.out.println("Showing details for user: " + userName);

        // For now, we'll just show a message
        JOptionPane.showMessageDialog(this,
            "User Details for: " + userName,
            "User Details",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel title
        JLabel titleLabel = new JLabel("System Settings");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Create a rounded panel for the content
        RoundedPanel contentPanel = new RoundedPanel(new BorderLayout(), Color.WHITE, UIUtils.CORNER_RADIUS);
        contentPanel.setShadow(3);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create settings form
        JPanel settingsForm = new JPanel();
        settingsForm.setLayout(new BoxLayout(settingsForm, BoxLayout.Y_AXIS));
        settingsForm.setOpaque(false);

        // System Name Setting
        JPanel systemNamePanel = createSettingPanel("System Name", "Event Management System");
        settingsForm.add(systemNamePanel);
        settingsForm.add(Box.createVerticalStrut(15));

        // Email Settings
        JPanel emailSettingsPanel = createSettingPanel("Email Server", "smtp.example.com");
        settingsForm.add(emailSettingsPanel);
        settingsForm.add(Box.createVerticalStrut(15));

        JPanel emailPortPanel = createSettingPanel("Email Port", "587");
        settingsForm.add(emailPortPanel);
        settingsForm.add(Box.createVerticalStrut(15));

        JPanel emailUserPanel = createSettingPanel("Email Username", "admin@example.com");
        settingsForm.add(emailUserPanel);
        settingsForm.add(Box.createVerticalStrut(15));

        // Notification Settings
        JPanel notificationPanel = new JPanel(new BorderLayout());
        notificationPanel.setOpaque(false);
        notificationPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(AppColors.BORDER_LIGHT),
            "Notification Settings",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14)
        ));

        JPanel checkboxPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        checkboxPanel.setOpaque(false);
        checkboxPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JCheckBox emailNotifCheckbox = new JCheckBox("Send Email Notifications");
        emailNotifCheckbox.setSelected(true);
        emailNotifCheckbox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JCheckBox smsNotifCheckbox = new JCheckBox("Send SMS Notifications");
        smsNotifCheckbox.setSelected(false);
        smsNotifCheckbox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JCheckBox pushNotifCheckbox = new JCheckBox("Send Push Notifications");
        pushNotifCheckbox.setSelected(true);
        pushNotifCheckbox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        checkboxPanel.add(emailNotifCheckbox);
        checkboxPanel.add(smsNotifCheckbox);
        checkboxPanel.add(pushNotifCheckbox);

        notificationPanel.add(checkboxPanel, BorderLayout.CENTER);
        settingsForm.add(notificationPanel);
        settingsForm.add(Box.createVerticalStrut(15));

        // Backup Settings
        JPanel backupPanel = new JPanel(new BorderLayout());
        backupPanel.setOpaque(false);
        backupPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(AppColors.BORDER_LIGHT),
            "Backup Settings",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14)
        ));

        JPanel backupOptionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        backupOptionsPanel.setOpaque(false);
        backupOptionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        backupOptionsPanel.add(new JLabel("Backup Frequency:"));
        String[] frequencies = {"Daily", "Weekly", "Monthly"};
        JComboBox<String> frequencyComboBox = new JComboBox<>(frequencies);
        frequencyComboBox.setSelectedItem("Weekly");
        backupOptionsPanel.add(frequencyComboBox);

        backupOptionsPanel.add(new JLabel("Backup Location:"));
        JTextField backupLocationField = new JTextField("/backups");
        backupOptionsPanel.add(backupLocationField);

        backupPanel.add(backupOptionsPanel, BorderLayout.CENTER);
        settingsForm.add(backupPanel);
        settingsForm.add(Box.createVerticalStrut(20));

        // Save and Reset buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        GradientButton resetButton = GradientButton.createSecondaryButton("Reset");
        resetButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        resetButton.setPreferredSize(new Dimension(120, 40));
        resetButton.addActionListener(e -> resetSettings());

        GradientButton saveButton = GradientButton.createPrimaryButton("Save Settings");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setPreferredSize(new Dimension(150, 40));
        saveButton.addActionListener(e -> saveSettings());

        buttonPanel.add(resetButton);
        buttonPanel.add(saveButton);

        settingsForm.add(buttonPanel);

        // Add the form to a scroll pane
        JScrollPane scrollPane = new JScrollPane(settingsForm);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Creates a setting panel with a label and text field
     *
     * @param labelText The label text
     * @param defaultValue The default value for the text field
     * @return A panel containing the label and text field
     */
    private JPanel createSettingPanel(String labelText, String defaultValue) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JLabel label = new JLabel(labelText + ":");
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setPreferredSize(new Dimension(150, 30));

        JTextField textField = new JTextField(defaultValue);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppColors.BORDER_LIGHT),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        panel.add(label, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Saves the system settings
     */
    private void saveSettings() {
        // In a real application, this would save the settings to a database or file
        System.out.println("Saving system settings");

        // For now, we'll just show a message
        JOptionPane.showMessageDialog(this,
            "Settings saved successfully!",
            "Settings Saved",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Resets the system settings to default values
     */
    private void resetSettings() {
        // In a real application, this would reset the settings to default values
        System.out.println("Resetting system settings");

        // For now, we'll just show a message
        JOptionPane.showMessageDialog(this,
            "Settings reset to default values!",
            "Settings Reset",
            JOptionPane.INFORMATION_MESSAGE);
    }



    private void showEventApprovalDialog(String eventName) {
        // Show event approval dialog instead of navigating to a new screen
        EventApprovalDialog dialog = new EventApprovalDialog(
            this,
            "E" + Math.abs(eventName.hashCode()),
            eventName,
            "External Organizer",
            new Date(System.currentTimeMillis() - 86400000), // Yesterday
            new Date(System.currentTimeMillis() + 86400000 * 10), // 10 days from now
            "Main Hall",
            "Conference",
            150,
            "Event description will be displayed here. This is a sample description for the event that would include details about the event, its purpose, and what attendees can expect.",
            "- Open to all students\n- Must have valid student ID\n- Registration required",
            approved -> {
                // Handle approval result
                if (approved) {
                    // Event was approved
                    updateEventStatus(eventName, "Approved");
                } else {
                    // Event was rejected
                    updateEventStatus(eventName, "Rejected");
                }
                // Refresh the pending approvals panel
                refreshPendingApprovals();
            }
        );

        dialog.setVisible(true);
    }

    private void updateEventStatus(String eventName, String status) {
        // In a real application, this would update the database
        // For now, we'll just show a message
        System.out.println("Event '" + eventName + "' status updated to: " + status);
    }

    private void refreshPendingApprovals() {
        // In a real application, this would refresh the data from the database
        // For now, we'll just show a message
        System.out.println("Refreshing pending approvals list");

        // If we're on the Pending Approvals panel, refresh it
        // This is a placeholder for actual implementation
        if (mainContentPanel.isShowing() &&
            mainContentPanel.getComponent(1).isShowing()) {
            // We're on the Pending Approvals panel
            // In a real app, we would refresh the data here
        }
    }

    private void showCreateEventDialog() {
        // Create a new panel for the Create Event form
        JPanel createEventPanel = new JPanel(new BorderLayout());
        createEventPanel.setBackground(Color.WHITE);
        createEventPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        createEventPanel.setName("Create Event");

        // Panel title
        JLabel titleLabel = new JLabel("Create New Event");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        createEventPanel.add(titleLabel, BorderLayout.NORTH);

        // Use the reusable CreateEventForm component
        CreateEventForm eventForm = new CreateEventForm("Admin", success -> {
            // If form was submitted successfully, return to dashboard
            if (success) {
                cardLayout.show(mainContentPanel, "Dashboard");
            }
        });

        createEventPanel.add(eventForm, BorderLayout.CENTER);

        // Add the panel to the main content panel if it doesn't exist yet
        boolean panelExists = false;
        for (Component comp : mainContentPanel.getComponents()) {
            if (comp instanceof JPanel && comp.getName() != null && comp.getName().equals("Create Event")) {
                panelExists = true;
                break;
            }
        }

        if (!panelExists) {
            mainContentPanel.add(createEventPanel, "Create Event");
        }

        // Show the Create Event panel
        cardLayout.show(mainContentPanel, "Create Event");
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

        SwingUtilities.invokeLater(AdminDashboardNew::new);
    }
}
