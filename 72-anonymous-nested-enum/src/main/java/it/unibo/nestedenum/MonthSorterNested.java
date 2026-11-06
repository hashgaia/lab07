package it.unibo.nestedenum;

import java.lang.classfile.instruction.ReturnInstruction;
import java.util.Comparator;
import java.util.Locale;

import javax.management.JMRuntimeException;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    @Override
    public Comparator<String> sortByDays() {
        return new SortByDate();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }

    /**
     * Enumeration of the months of the year, each with its number of days
     * 
     */
    enum Month {
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);

        private final int days;

        Month(final int days) {
            this.days = days;
        }

        /**
         * Days' number getter of the month
         * @return the number of days of this month
         */
        public int getDays() {
            return days;
        }

        /**
         * Finds the month that matches the given text
         * Comparison is case-sensitive
         * 
         * @param name the text to interpret as a month
         * @return the matching {@link Month}
         */
        public static Month fromString(final String name) {
            final String search = name.toUpperCase(Locale.ROOT);
            Month match = null;
            for (final Month month : values()) {
                if (month.toString().startsWith(search)) {
                    if (match != null) {
                        throw new IllegalArgumentException("inexisting month. " + name);
                    }
                    match = month;
                }
            }
            if (match == null) {
                throw new IllegalArgumentException("There's no month matching: " + name);
            }
            return match;
        }

        /**
         * Sorts months by their position during the year
         * 
         */
        private static final class SortByMonthOrder implements Comparator<String> {
            @Override
            public int compare(final String a, final String b) {
                return Month.fromString(a).compareTo(Month.fromString(b));
            }
        }
        /**
         * Sorts months by their number of days
         */

        private static final class SortByDate implements Comparator<String> {
            @Override 
            public int compare(final String a, final String b) {
                return Integer.compare(Month.fromString(a).getDays(), Month.fromString(b).getDays());
            }
        }

}
