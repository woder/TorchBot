package me.woder.bot;

import java.util.regex.Pattern;


/**
 * All supported color values for chat
 */
@SuppressWarnings("unused")
public enum ChatColor {
    /**
     * Represents black
     */
    BLACK('0', 0x00),
    /**
     * Represents dark blue
     */
    DARK_BLUE('1', 0x1),
    /**
     * Represents dark green
     */
    DARK_GREEN('2', 0x2),
    /**
     * Represents dark blue (aqua)
     */
    DARK_AQUA('3', 0x3),
    /**
     * Represents dark red
     */
    DARK_RED('4', 0x4),
    /**
     * Represents dark purple
     */
    DARK_PURPLE('5', 0x5),
    /**
     * Represents gold
     */
    GOLD('6', 0x6),
    /**
     * Represents gray
     */
    GRAY('7', 0x7),
    /**
     * Represents dark gray
     */
    DARK_GRAY('8', 0x8),
    /**
     * Represents blue
     */
    BLUE('9', 0x9),
    /**
     * Represents green
     */
    GREEN('a', 0xA),
    /**
     * Represents aqua
     */
    AQUA('b', 0xB),
    /**
     * Represents red
     */
    RED('c', 0xC),
    /**
     * Represents light purple
     */
    LIGHT_PURPLE('d', 0xD),
    /**
     * Represents yellow
     */
    YELLOW('e', 0xE),
    /**
     * Represents white
     */
    WHITE('f', 0xF),
    /**
     * Represents magical characters that change around randomly
     */
    MAGIC('k', 0x10, true),
    /**
     * Makes the text bold.
     */
    BOLD('l', 0x11, true),
    /**
     * Makes a line appear through the text.
     */
    STRIKETHROUGH('m', 0x12, true),
    /**
     * Makes the text appear underlined.
     */
    UNDERLINE('n', 0x13, true),
    /**
     * Makes the text italic.
     */
    ITALIC('o', 0x14, true),
    /**
     * Resets all previous chat colors or formats.
     */
    RESET('r', 0x15);

    /**
     * The special character which prefixes all chat colour codes. Use this if you need to dynamically
     * convert colour codes from your custom format.
     */
    public static final char COLOR_CHAR = '\u00A7';
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf(COLOR_CHAR) + "[0-9A-FK-OR]");
    private final int intCode;
    private final char code;
    private final boolean isFormat;
    private final String toString;
    
    private ChatColor(char code, int intCode) {
        this(code, intCode, false);
    }

    private ChatColor(char code, int intCode, boolean isFormat) {
        this.code = code;
        this.intCode = intCode;
        this.isFormat = isFormat;
        this.toString = new String(new char[] {COLOR_CHAR, code});
    }
    
    public static String stripColor(final String input) {
        if (input == null) {
            return null;
        }

        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

}
