/*
 * Copyright (C) 2011 The Stanford MobiSocial Laboratory
 *
 * This file is part of Musubi, a mobile social network.
 *
 *  This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package mobisocial.musubi.util;

import java.text.SimpleDateFormat;


// This class due to Kurtis Chiappone. No license was provided, so we really need to
// follow up with him.
 
public class RelativeDate {
    private static SimpleDateFormat wayDistantDateFormat = new SimpleDateFormat( "MMM dd, yyyy" );
    private static SimpleDateFormat distantDateFormat = new SimpleDateFormat( "MMM dd" );
 
    /**
     * This method computes the relative date according to
     * the Calendar being passed in and the number of years,
     * months, days, etc. that differ. This will compute both
     * past and future relative dates. E.g., "one day ago" and
     * "one day from now".
     * <p>
     * <strong>NOTE:</strong> If the calendar date relative
     * to "now" is older than one day, we display the actual date in
     * its default format as specified by this class. The date format
     * may be changed by calling {@link RelativeDate#setDateFormat(SimpleDateFormat)}
     * If you don't want to show the actual date, but you want to show
     * the relative date for days, months, and years, you can add the
     * other cases in by copying the logic for hours, minutes, seconds.
     *
     * @param Calendar calendar
     * @param int years
     * @param int months
     * @param int days
     * @param int hours
     * @param int minutes
     * @param int seconds
     * @return String representing the relative date
     */
 
    private static String computeRelativeDate(long timestamp, int years, int months, int days,
            int hours, int minutes, int seconds) {
 
        // Year
 
        if ( years != 0 ) {
            return wayDistantDateFormat.format(timestamp);
        }
 
        // Month
 
        else if ( months != 0 || days > 7) {
            return distantDateFormat.format(timestamp);
        }
 
        // Day
 
        else if ( days != 0 ) {
            if (days == -1) {
                return "Yesterday";
            }
            if (days == 1) {
                return "Tomorrow";
            }
            if (days > 0) {
                return days + " days from now";
            } else {
                return Math.abs(days) + " days ago";
            }
        }
 
        // Hour
 
        else if ( hours == 1 ) return 1 + " hour from now";
        else if ( hours == -1 ) return 1 + " hour ago";
        else if ( hours > 0 ) return hours + " hours from now";
        else if ( hours < 0 ) return Math.abs( hours ) + " hours ago";
 
        // Minute
 
        else if ( minutes == 1 ) return 1 + " minute from now";
        else if ( minutes == -1 ) return 1 + " minute ago";
        else if ( minutes > 0 ) return minutes + " minutes from now";
        else if ( minutes < 0 ) return Math.abs( minutes ) + " minutes ago";
 
        // Second
 
        else if ( seconds == 1 ) return 1 + " second from now";
        else if ( seconds > 0 ) return seconds + " seconds from now";

        else if ( seconds < 0 ) return "Just now";
 
        // Must be now (date and times are identical)
 
        else return "now";
 
    } // end method computeRelativeDate
 
    /**
     * This method returns a String representing the relative
     * date by comparing the Calendar being passed in to the
     * date / time that it is right now.
     *
     * @param Date date
     * @return String representing the relative date
     */
 
    public static String getRelativeDate(long timestamp) {

        double seconds = (timestamp - System.currentTimeMillis()) / 1000;
        int years = (int)(seconds / 31556926);
        seconds -= years * 31556926;

        int months = (int)(seconds / 2629743.83);
        seconds -= months * 2629743.83;

        int days = (int)(seconds / 86400);
        seconds -= days * 86400;

        int hours = (int)(seconds / 3600);
        seconds -= hours * 3600;

        int minutes = (int)(seconds / 60);
        seconds -= minutes * 60;

        return computeRelativeDate(timestamp, years, months, days, hours, minutes, (int)seconds);
 
    } // end method getRelativeDate 
} // end class RelativeDate
