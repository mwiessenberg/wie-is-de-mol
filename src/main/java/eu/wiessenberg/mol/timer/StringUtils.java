package eu.wiessenberg.mol.timer;

class StringUtils {
    static String formatTime(long totalSeconds) {
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }
}
