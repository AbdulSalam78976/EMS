package utils;

import java.awt.Color;

/**
 * Modern color palette for the Event Management System
 * Includes gradient color pairs for buttons and UI elements
 */
public class AppColors {
    // Backgrounds
    public static final Color BACKGROUND_LIGHT = new Color(248, 249, 250);  // #F8F9FA - Light gray background
    public static final Color BACKGROUND_DARK = new Color(33, 37, 41);      // #212529 - Dark background
    public static final Color PANEL_BACKGROUND = Color.WHITE;               // White panels

    // Primary theme - Blue gradient
    public static final Color PRIMARY = new Color(13, 110, 253);            // #0D6EFD - Bootstrap primary blue
    public static final Color PRIMARY_DARK = new Color(10, 88, 202);        // #0A58CA - Darker blue
    public static final Color PRIMARY_LIGHT = new Color(207, 226, 255);     // #CFE2FF - Light blue

    // Primary gradient pair
    public static final Color PRIMARY_GRADIENT_START = new Color(13, 110, 253);  // #0D6EFD
    public static final Color PRIMARY_GRADIENT_END = new Color(10, 88, 202);     // #0A58CA

    // Secondary theme - Purple gradient
    public static final Color SECONDARY = new Color(108, 117, 125);         // #6C757D - Bootstrap secondary
    public static final Color SECONDARY_DARK = new Color(73, 80, 87);       // #495057 - Darker gray
    public static final Color SECONDARY_LIGHT = new Color(222, 226, 230);   // #DEE2E6 - Light gray

    // Secondary gradient pair
    public static final Color SECONDARY_GRADIENT_START = new Color(108, 117, 125);  // #6C757D
    public static final Color SECONDARY_GRADIENT_END = new Color(73, 80, 87);       // #495057

    // Success theme - Green gradient
    public static final Color SUCCESS = new Color(25, 135, 84);             // #198754 - Bootstrap success
    public static final Color SUCCESS_DARK = new Color(20, 108, 67);        // #146C43 - Darker green
    public static final Color SUCCESS_LIGHT = new Color(209, 231, 221);     // #D1E7DD - Light green

    // Success gradient pair
    public static final Color SUCCESS_GRADIENT_START = new Color(25, 135, 84);  // #198754
    public static final Color SUCCESS_GRADIENT_END = new Color(20, 108, 67);    // #146C43

    // Danger theme - Red gradient
    public static final Color DANGER = new Color(220, 53, 69);              // #DC3545 - Bootstrap danger
    public static final Color DANGER_DARK = new Color(176, 42, 55);         // #B02A37 - Darker red
    public static final Color DANGER_LIGHT = new Color(248, 215, 218);      // #F8D7DA - Light red

    // Danger gradient pair (also used for ERROR)
    public static final Color DANGER_GRADIENT_START = new Color(220, 53, 69);  // #DC3545
    public static final Color DANGER_GRADIENT_END = new Color(176, 42, 55);    // #B02A37

    // Warning theme - Yellow gradient
    public static final Color WARNING = new Color(255, 193, 7);             // #FFC107 - Bootstrap warning
    public static final Color WARNING_DARK = new Color(204, 154, 6);        // #CC9A06 - Darker yellow
    public static final Color WARNING_LIGHT = new Color(255, 243, 205);     // #FFF3CD - Light yellow

    // Warning gradient pair
    public static final Color WARNING_GRADIENT_START = new Color(255, 193, 7);  // #FFC107
    public static final Color WARNING_GRADIENT_END = new Color(204, 154, 6);    // #CC9A06

    // Info theme - Cyan gradient
    public static final Color INFO = new Color(13, 202, 240);               // #0DCAF0 - Bootstrap info
    public static final Color INFO_DARK = new Color(10, 162, 192);          // #0AA2C0 - Darker cyan
    public static final Color INFO_LIGHT = new Color(207, 244, 252);        // #CFF4FC - Light cyan

    // Info gradient pair
    public static final Color INFO_GRADIENT_START = new Color(13, 202, 240);  // #0DCAF0
    public static final Color INFO_GRADIENT_END = new Color(10, 162, 192);    // #0AA2C0

    // Text colors
    public static final Color TEXT_PRIMARY = new Color(33, 37, 41);         // #212529 - Dark text
    public static final Color TEXT_SECONDARY = new Color(108, 117, 125);    // #6C757D - Gray text
    public static final Color TEXT_DARK = new Color(33, 37, 41);            // #212529 - Dark text
    public static final Color TEXT_LIGHT = new Color(248, 249, 250);        // #F8F9FA - Light text

    // Borders
    public static final Color BORDER_LIGHT = new Color(222, 226, 230);      // #DEE2E6 - Light border
    public static final Color BORDER_DARK = new Color(108, 117, 125);       // #6C757D - Dark border

    // For backward compatibility
    public static final Color ERROR = DANGER;                               // Same as DANGER
    public static final Color ERROR_DARK = DANGER_DARK;                     // Same as DANGER_DARK
    public static final Color ERROR_LIGHT = DANGER_LIGHT;                   // Same as DANGER_LIGHT

    private AppColors() {
        // Prevent instantiation
    }
}
