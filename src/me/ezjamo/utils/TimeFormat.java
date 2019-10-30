package me.ezjamo.utils;

public class TimeFormat {
	static String sec = "s";
	static String min = "m";
	static String hr = "h";
	static String day = "d";
	
	public static String getTime(long l) {
        if (l < 60L) {
            return l + sec;
        }
        int minutes = (int)(l / 60L);
        int s = 60 * minutes;
        int secondsLeft = (int)(l - s);
        if (minutes < 60) {
            if (secondsLeft > 0) {
                return minutes + min + " " + secondsLeft + sec;
            }
            return minutes + min;
        }
        else {
            if (minutes < 1440) {
                String time;
                int hours = minutes / 60;
                time = hours + hr;
                int inMins = 60 * hours;
                int left = minutes - inMins;
                if (left >= 1) {
                    time = time + " " + left + min;
                }
                if (secondsLeft > 0) {
                    time = time + " " + secondsLeft + sec;
                }
                return time;
            }
            String time;
            int days = minutes / 1440;
            time = days + day;
            int inMins = 1440 * days;
            int leftOver = minutes - inMins;
            if (leftOver >= 1) {
                if (leftOver < 60) {
                    time = time + " " + leftOver + min;
                }
                else {
                    int hours2 = leftOver / 60;
                    time = time + " " + hours2 + hr;
                    int hoursInMins = 60 * hours2;
                    int minsLeft = leftOver - hoursInMins;
                    time = time + " " + minsLeft + min;
                }
            }
            if (secondsLeft > 0) {
                time = time + " " + secondsLeft + sec;
            }
            return time;
        }
    }
}
