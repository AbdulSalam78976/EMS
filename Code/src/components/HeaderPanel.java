package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;
import utils.AppColors;
import utils.UIUtils;
import screens.LoginScreen;

/**
 * Reusable header panel component with consistent styling for all screens
 */
public class HeaderPanel extends JPanel {
    private final String username;
    private final String userRole;
    private final boolean showCreateEventButton;
    private final Consumer<Void> onCreateEventClick;

    /**
     * Creates a header panel with user information and optional create event button
     *
     * @param username The username to display
     * @param userRole The user role to display
     * @param showCreateEventButton Whether to show the create event button
     * @param onCreateEventClick Callback for when the create event button is clicked
     */
    public HeaderPanel(String username, String userRole, boolean showCreateEventButton, Consumer<Void> onCreateEventClick) {
        this.username = username;
        this.userRole = userRole;
        this.showCreateEventButton = showCreateEventButton;
        this.onCreateEventClick = onCreateEventClick;

        setupUI();
    }

    /**
     * Creates a header panel without create event button
     *
     * @param username The username to display
     * @param userRole The user role to display
     */
    public HeaderPanel(String username, String userRole) {
        this(username, userRole, false, null);
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(AppColors.PRIMARY_GRADIENT_START);
        setPreferredSize(new Dimension(getWidth(), 60));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Create gradient background
        setOpaque(false);

        // System name on the left
        JLabel systemName = new JLabel("EVENT MANAGEMENT SYSTEM");
        systemName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        systemName.setForeground(Color.WHITE);
        add(systemName, BorderLayout.WEST);

        // User info and actions on the right
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        userPanel.setOpaque(false);

        // User dropdown
        JPanel userDropdown = createUserDropdown();
        userPanel.add(userDropdown);

        // Create Event Button (if enabled)
        if (showCreateEventButton) {
            GradientButton createEventButton = GradientButton.createPrimaryButton("Create Event");
            createEventButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            createEventButton.setPreferredSize(new Dimension(150, 40));
            createEventButton.setCornerRadius(UIUtils.CORNER_RADIUS);
            createEventButton.addActionListener(e -> {
                if (onCreateEventClick != null) {
                    onCreateEventClick.accept(null);
                }
            });
            userPanel.add(createEventButton);
        }

        // Notifications icon
        JLabel notificationIcon = new JLabel("🔔");
        notificationIcon.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        notificationIcon.setForeground(Color.WHITE);
        notificationIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        notificationIcon.setToolTipText("Notifications");

        // Add badge to notification icon if there are unread notifications
        JPanel notificationPanel = new JPanel(new BorderLayout());
        notificationPanel.setOpaque(false);
        notificationPanel.add(notificationIcon, BorderLayout.CENTER);

        // Add a red dot for unread notifications (can be controlled programmatically)
        JLabel badgeLabel = new JLabel("•");
        badgeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        badgeLabel.setForeground(AppColors.DANGER);
        notificationPanel.add(badgeLabel, BorderLayout.NORTH);

        userPanel.add(notificationPanel);

        add(userPanel, BorderLayout.EAST);

        // Add gradient background
        addGradientBackground();
    }

    private JPanel createUserDropdown() {
        JPanel userDropdown = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        userDropdown.setOpaque(false);
        userDropdown.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // User avatar (circle with first letter of username)
        JPanel avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw circle
                g2.setColor(Color.WHITE);
                g2.fillOval(0, 0, getWidth(), getHeight());

                // Draw first letter
                g2.setColor(AppColors.PRIMARY_DARK);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));

                String firstLetter = username.substring(0, 1).toUpperCase();
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(firstLetter)) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();

                g2.drawString(firstLetter, x, y);
                g2.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(30, 30);
            }
        };

        JLabel userLabel = new JLabel(username);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(Color.WHITE);

        JLabel roleLabel = new JLabel("(" + userRole + ")");
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        roleLabel.setForeground(new Color(220, 220, 220));

        JLabel dropdownIcon = new JLabel("▼");
        dropdownIcon.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        dropdownIcon.setForeground(Color.WHITE);

        JPanel userInfo = new JPanel();
        userInfo.setLayout(new BoxLayout(userInfo, BoxLayout.Y_AXIS));
        userInfo.setOpaque(false);
        userInfo.add(userLabel);
        userInfo.add(roleLabel);

        userDropdown.add(avatarPanel);
        userDropdown.add(Box.createHorizontalStrut(5));
        userDropdown.add(userInfo);
        userDropdown.add(dropdownIcon);

        // Add popup menu to user dropdown
        JPopupMenu userMenu = createUserMenu();

        userDropdown.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userMenu.show(userDropdown, 0, userDropdown.getHeight());
            }
        });

        return userDropdown;
    }

    private JPopupMenu createUserMenu() {
        JPopupMenu userMenu = new JPopupMenu();

        // Profile menu item
        JMenuItem profileItem = new JMenuItem("Profile");
        profileItem.addActionListener(e -> showProfile());

        // Settings menu item
        JMenuItem settingsItem = new JMenuItem("Settings");
        settingsItem.addActionListener(e -> showSettings());

        // Logout menu item
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> handleLogout());

        userMenu.add(profileItem);
        userMenu.add(settingsItem);
        userMenu.addSeparator();
        userMenu.add(logoutItem);

        return userMenu;
    }

    private void addGradientBackground() {
        // Override paintComponent to create gradient background
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int w = getWidth();
        int h = getHeight();

        // Create gradient paint
        GradientPaint gp = new GradientPaint(
            0, 0, AppColors.PRIMARY_GRADIENT_START,
            w, 0, AppColors.PRIMARY_GRADIENT_END
        );

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);

        g2d.dispose();
    }

    private void showProfile() {
        JOptionPane.showMessageDialog(
            this,
            "Profile view will be implemented here",
            "Profile",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showSettings() {
        JOptionPane.showMessageDialog(
            this,
            "Settings view will be implemented here",
            "Settings",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            // Get the parent window
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }

            // Show login screen
            SwingUtilities.invokeLater(LoginScreen::new);
        }
    }
}
