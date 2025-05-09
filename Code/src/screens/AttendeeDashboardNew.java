package screens;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import utils.AppColors;
import utils.UIUtils;
import components.GradientButton;
import components.HeaderPanel;
import components.RoundedPanel;
import components.SidebarPanel;

/**
 * Dashboard screen for Attendees (Students)
 * Implements a modern UI with rounded corners and consistent sidebar
 */
@SuppressWarnings("unused")
public class AttendeeDashboardNew extends JFrame {
    private JPanel contentPanel;
    private CardLayout contentLayout;
    private String username = "John Doe"; // Simulate authenticated user

    private enum TabInfo {
        HOME("🏠", "Home"),
        SEARCH("🔍", "Search"),
        CALENDAR("📅", "Calendar"),
        MY_EVENTS("⭐", "My Events"),
        PROFILE("👤", "Profile");

        final String icon;
        final String title;

        TabInfo(String icon, String title) {
            this.icon = icon;
            this.title = title;
        }
    }

    public AttendeeDashboardNew() {
        setTitle("Event Management System - Attendee Dashboard");
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

        // Create content panel with card layout
        contentLayout = new CardLayout();
        contentPanel = new JPanel(contentLayout);
        contentPanel.setBackground(AppColors.BACKGROUND_LIGHT);

        // Add content cards for each section
        contentPanel.add(createDashboardContent(), TabInfo.HOME.title);
        contentPanel.add(createSearchContent(), TabInfo.SEARCH.title);
        contentPanel.add(createCalendarContent(), TabInfo.CALENDAR.title);
        contentPanel.add(createMyEventsContent(), TabInfo.MY_EVENTS.title);
        contentPanel.add(createProfileContent(), TabInfo.PROFILE.title);

        // Create sidebar panel
        JPanel sidebarPanel = createSidebarPanel();
        mainPanel.add(sidebarPanel, BorderLayout.WEST);

        // Add content panel to main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Set the main panel as the content pane
        setContentPane(mainPanel);
    }

    private JPanel createHeaderPanel() {
        // Use the reusable HeaderPanel component
        return new HeaderPanel(username, "Attendee");
    }

    private JPanel createSidebarPanel() {
        // Create a new sidebar panel using our reusable component
        SidebarPanel sidebarPanel = new SidebarPanel(
            contentLayout,
            contentPanel,
            username,
            "Attendee"
        );

        // Add navigation buttons for each tab
        for (TabInfo tab : TabInfo.values()) {
            sidebarPanel.addNavButton(tab.title, tab.icon, tab.title, tab == TabInfo.HOME);
        }

        // Add logout button
        sidebarPanel.addLogoutButton(e -> handleLogout());

        return sidebarPanel;
    }

    // Create placeholder content panels for each section
    private JPanel createSearchContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Search Events");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Create a search panel with rounded corners
        RoundedPanel searchPanel = new RoundedPanel(new BorderLayout(), Color.WHITE, UIUtils.CORNER_RADIUS);
        searchPanel.setShadow(3);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel searchForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchForm.setOpaque(false);

        JTextField searchField = UIUtils.createRoundedTextField();
        searchField.setColumns(30);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        GradientButton searchButton = GradientButton.createPrimaryButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 35));

        searchForm.add(searchField);
        searchForm.add(searchButton);

        searchPanel.add(searchForm, BorderLayout.NORTH);

        JLabel placeholderLabel = new JLabel("Search results will appear here", SwingConstants.CENTER);
        placeholderLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        searchPanel.add(placeholderLabel, BorderLayout.CENTER);

        panel.add(searchPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCalendarContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Event Calendar");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Create a calendar panel with rounded corners
        RoundedPanel calendarPanel = new RoundedPanel(new BorderLayout(), Color.WHITE, UIUtils.CORNER_RADIUS);
        calendarPanel.setShadow(3);
        calendarPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel placeholderLabel = new JLabel("Calendar view will be implemented here", SwingConstants.CENTER);
        placeholderLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        calendarPanel.add(placeholderLabel, BorderLayout.CENTER);

        panel.add(calendarPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createMyEventsContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("My Registered Events");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Create a my events panel with rounded corners
        RoundedPanel eventsPanel = new RoundedPanel(new BorderLayout(), Color.WHITE, UIUtils.CORNER_RADIUS);
        eventsPanel.setShadow(3);
        eventsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel eventsGrid = createEventGrid(getMyRegisteredEvents());
        eventsPanel.add(eventsGrid, BorderLayout.CENTER);

        panel.add(eventsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createProfileContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("My Profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Create a profile panel with rounded corners
        RoundedPanel profilePanel = new RoundedPanel(new BorderLayout(), Color.WHITE, UIUtils.CORNER_RADIUS);
        profilePanel.setShadow(3);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel placeholderLabel = new JLabel("Profile information will be displayed here", SwingConstants.CENTER);
        placeholderLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        profilePanel.add(placeholderLabel, BorderLayout.CENTER);

        panel.add(profilePanel, BorderLayout.CENTER);

        return panel;
    }

    private JScrollPane createDashboardContent() {
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BoxLayout(dashboardPanel, BoxLayout.Y_AXIS));
        dashboardPanel.setBackground(AppColors.BACKGROUND_LIGHT);
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Welcome section with rounded corners
        RoundedPanel welcomePanel = new RoundedPanel(new BorderLayout(), AppColors.PRIMARY_LIGHT, UIUtils.CORNER_RADIUS);
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        welcomePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(AppColors.TEXT_PRIMARY);

        JLabel dateLabel = new JLabel(new SimpleDateFormat("EEEE, MMMM d, yyyy").format(new Date()));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateLabel.setForeground(AppColors.TEXT_SECONDARY);

        JPanel welcomeTextPanel = new JPanel();
        welcomeTextPanel.setLayout(new BoxLayout(welcomeTextPanel, BoxLayout.Y_AXIS));
        welcomeTextPanel.setOpaque(false);
        welcomeTextPanel.add(dateLabel);
        welcomeTextPanel.add(Box.createVerticalStrut(10));
        welcomeTextPanel.add(welcomeLabel);

        welcomePanel.add(welcomeTextPanel, BorderLayout.CENTER);

        // Upcoming events section with rounded corners
        RoundedPanel upcomingEventsPanel = new RoundedPanel(new BorderLayout(), Color.WHITE, UIUtils.CORNER_RADIUS);
        upcomingEventsPanel.setShadow(3);
        upcomingEventsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        upcomingEventsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel upcomingEventsLabel = new JLabel("UPCOMING EVENTS");
        upcomingEventsLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        upcomingEventsLabel.setForeground(AppColors.TEXT_PRIMARY);
        upcomingEventsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel upcomingEventsGrid = createEventGrid(getUpcomingEvents());

        GradientButton viewAllEventsBtn = GradientButton.createPrimaryButton("VIEW ALL EVENTS");
        viewAllEventsBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        viewAllEventsBtn.setPreferredSize(new Dimension(200, 40));
        viewAllEventsBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Navigating to All Events", "Navigation", JOptionPane.INFORMATION_MESSAGE);
            // Show the Search tab which would contain all events
            contentLayout.show(contentPanel, TabInfo.SEARCH.title);
        });

        upcomingEventsPanel.add(upcomingEventsLabel, BorderLayout.NORTH);
        upcomingEventsPanel.add(upcomingEventsGrid, BorderLayout.CENTER);
        upcomingEventsPanel.add(centeredPanel(viewAllEventsBtn), BorderLayout.SOUTH);

        // My registered events section with rounded corners
        RoundedPanel myEventsPanel = new RoundedPanel(new BorderLayout(), Color.WHITE, UIUtils.CORNER_RADIUS);
        myEventsPanel.setShadow(3);
        myEventsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        myEventsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel myEventsLabel = new JLabel("MY REGISTERED EVENTS");
        myEventsLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        myEventsLabel.setForeground(AppColors.TEXT_PRIMARY);
        myEventsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel myEventsGrid = createEventGrid(getMyRegisteredEvents());

        GradientButton viewMyEventsBtn = GradientButton.createPrimaryButton("VIEW ALL MY EVENTS");
        viewMyEventsBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        viewMyEventsBtn.setPreferredSize(new Dimension(200, 40));
        viewMyEventsBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Navigating to My Events", "Navigation", JOptionPane.INFORMATION_MESSAGE);
            // Show the My Events tab
            contentLayout.show(contentPanel, TabInfo.MY_EVENTS.title);
        });

        myEventsPanel.add(myEventsLabel, BorderLayout.NORTH);
        myEventsPanel.add(myEventsGrid, BorderLayout.CENTER);
        myEventsPanel.add(centeredPanel(viewMyEventsBtn), BorderLayout.SOUTH);

        // Add all sections to the dashboard
        dashboardPanel.add(welcomePanel);
        dashboardPanel.add(Box.createVerticalStrut(20));
        dashboardPanel.add(upcomingEventsPanel);
        dashboardPanel.add(Box.createVerticalStrut(20));
        dashboardPanel.add(myEventsPanel);

        // Create a scroll pane for the dashboard
        JScrollPane scrollPane = new JScrollPane(dashboardPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    private JPanel centeredPanel(Component comp) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        panel.add(comp);
        return panel;
    }

    private JPanel createEventGrid(String[][] events) {
        JPanel grid = new JPanel(new GridLayout(0, 2, 15, 15));
        grid.setOpaque(false);

        for (String[] event : events) {
            grid.add(createEventCard(event[0], event[1], event[2], event[3], event[4]));
        }

        return grid;
    }

    private JPanel createEventCard(String title, String dateStr, String location, String buttonText, String buttonAction) {
        // Create a rounded panel with shadow for the card
        RoundedPanel card = new RoundedPanel(new BorderLayout(10, 10), Color.WHITE, UIUtils.CORNER_RADIUS);
        card.setShadow(2);
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Image panel (placeholder)
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(200, 200, 200));
        imagePanel.setPreferredSize(new Dimension(80, 80));

        // Details panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);

        JLabel dateLabel = new JLabel(dateStr);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dateLabel.setForeground(AppColors.TEXT_SECONDARY);

        JLabel locationLabel = new JLabel(location);
        locationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        locationLabel.setForeground(AppColors.TEXT_SECONDARY);

        detailsPanel.add(titleLabel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(dateLabel);
        detailsPanel.add(Box.createVerticalStrut(3));
        detailsPanel.add(locationLabel);

        // Action button with gradient
        GradientButton actionButton;

        if ("register".equals(buttonAction)) {
            actionButton = GradientButton.createPrimaryButton(buttonText);
        } else {
            actionButton = GradientButton.createSecondaryButton(buttonText);
        }

        actionButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        actionButton.setPreferredSize(new Dimension(0, 35));

        actionButton.addActionListener(e -> {
            try {
                Date date = new SimpleDateFormat("MMM dd, yyyy").parse(dateStr);
                showEventDetails(title, "", date, location, 50);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        });

        // Top panel with image and details
        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        topPanel.setOpaque(false);
        topPanel.add(imagePanel, BorderLayout.WEST);
        topPanel.add(detailsPanel, BorderLayout.CENTER);

        // Add components to card
        card.add(topPanel, BorderLayout.CENTER);
        card.add(actionButton, BorderLayout.SOUTH);

        return card;
    }

    private String[][] getUpcomingEvents() {
        return new String[][]{
            {"Tech Conference", "Mar 15, 2024", "Main Hall", "REGISTER", "register"},
            {"AI Workshop", "Mar 20, 2024", "Room 101", "REGISTER", "register"},
            {"Career Fair", "Apr 01, 2024", "Auditorium", "REGISTER", "register"},
            {"Coding Contest", "Mar 25, 2024", "Lab 3", "REGISTER", "register"}
        };
    }

    private String[][] getMyRegisteredEvents() {
        return new String[][]{
            {"AI Workshop", "Mar 20, 2024", "Room 101", "VIEW DETAILS", "view"},
            {"Career Fair", "Apr 01, 2024", "Auditorium", "VIEW DETAILS", "view"}
        };
    }

    private void showEventDetails(String name, String desc, Date date, String venue, int slots) {
        dispose();
        // Navigate to EventDetailsScreen instead of directly to registration
        SwingUtilities.invokeLater(() -> new EventDetailsScreen(
            "EVENT" + name.hashCode(),
            name,
            date,
            venue,
            slots,
            slots + 30, // Total slots (placeholder)
            desc.isEmpty() ? "Join us for this exciting event!" : desc, // Description
            "Conference", // Category (placeholder)
            "Event Organizer", // Organizer (placeholder)
            "organizer@example.com", // Contact (placeholder)
            "Open to all students", // Eligibility criteria
            new String[] { "09:00 AM - 05:00 PM: Event" }, // Schedule (placeholder)
            false // Not registered
        ));
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
        SwingUtilities.invokeLater(AttendeeDashboardNew::new);
    }
}
