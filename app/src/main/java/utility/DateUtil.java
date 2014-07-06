/*
 *
 *    * Copyright 2014 Mobien Technologies Pvt. Ltd.
 *    *
 *    * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *    * use this file except in compliance with the License. You may obtain a copy of
 *    * the License at
 *    *
 *    * http://www.apache.org/licenses/LICENSE-2.0
 *    *
 *    * Unless required by applicable law or agreed to in writing, software
 *    * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *    * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *    * License for the specific language governing permissions and limitations under
 *    * the License.
 *    *
 *    * @author Basit Parkar
 *    * @date 7/6/14 6:33 PM
 *
 */

package utility;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static final SimpleDateFormat DEVICE_DATE_FORMAT = new SimpleDateFormat(
            "dd MMM, yyyy", Locale.US);

    public static int dateDiff(Calendar fromDate, Calendar toDate) {
        return daysSinceEpoch(toDate) - daysSinceEpoch(fromDate);
    }

    public static int daysSinceEpoch(Calendar day) {
        int year = day.get(Calendar.YEAR);
        int month = day.get(Calendar.MONTH);

        int daysThisYear = cumulDaysToMonth[month]
                + day.get(Calendar.DAY_OF_MONTH) - 1;
        if ((month > 1) && isLeapYear(year)) {
            daysThisYear++;
        }

        return daysToYear(year) + daysThisYear;
    }

    static int daysToYear(int year) {
        return (365 * year) + numLeapsToYear(year);
    }

    static int numLeapsToYear(int year) {
        int num4y = (year - 1) / 4;
        int num100y = (year - 1) / 100;
        int num400y = (year - 1) / 400;
        return num4y - num100y + num400y;
    }

    public static boolean isLeapYear(int year) {
        return (year % 400 == 0) || ((year % 100 != 0) && (year % 4 == 0));
    }

    private static final int[] cumulDaysToMonth = {0, 31, 59, 90, 120, 151,
            181, 212, 243, 273, 304, 334, 365};

    public static boolean check_IsBefore(String date1, String date2) {
        boolean flag = false;
        try {
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            Date d1 = null;
            Date d2 = null;
            try {
                d1 = outputFormat.parse(date1);
                d2 = outputFormat.parse(date2);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(d1);

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(d2);

            if (!(date1.equals(date2)) && cal2.before(cal1))
                flag = true;
        }
        catch (Exception e) {
        }
        return flag;
    }

    public static boolean check_IsBefore(Date date1, Date date2) {
        boolean flag = false;
        try {
//			SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            Date d1 = null;
            Date d2 = null;
            d1 = date1;
            d2 = date2;

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(d1);

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(d2);

            if (!(date1.equals(date2)) && cal2.before(cal1))
                flag = true;
        }
        catch (Exception e) {
        }
        return flag;
    }

    public static String getDateInddMMMyyyy() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        String s = formatter.format(date);
        return s;
    }

    /**
     * @return date time in format yyyy-MM-dd HH:MM:SS.SSS
     */
    public static String getDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS.SSS", Locale.US);
        String formattedDate = formatter.format(c.getTime());
        String s = formattedDate;
        return s;
    }

    /**
     * @return current time in format HH:mm:SS
     */
    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.US);
        String formattedDate = formatter.format(c.getTime());
        String s = formattedDate;
        return s;
    }

    /**
     * @return date in dd/MM/yyyy format
     */
    public static String getDateInddMMyyyy() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String s = formatter.format(date);
        return s;
    }

    /**
     * @return date in dd-MM-yyyy format
     */
    public static String getDateInddMMyyyyWithDash() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String s = formatter.format(date);
        return s;
    }

    public static String getDateInMMddyyyy() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String s = formatter.format(date);
        return s;
    }

    /**
     * get Date Time in ddMMyyyyHHmmSS format
     *
     * @return
     */
    public static String getDateddMMyyyyHHmmSS() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmSS", Locale.US);
        String s = formatter.format(date);
        return s;
    }

    /**
     * Method to get in between dates
     *
     * @param fromDate where dateformat = MM-dd-yyyy
     * @param toDate   where dateformat = MM-dd-yyyy
     * @param day      as Dayofweek 1 = Mon,2 = Tues,3 = Wed,4 = Thu, 5 = Fri,6 =
     *                 Sat,7 = Sun which is used to get Only dates of that day only
     *                 during specific time interval,insert 0 to keep param optional.
     * @return list of dates as String in MM-dd-yyyy format
     */
    // --------- Added by Keyur Gandhi on 7 JAN 2013
    public static ArrayList<String> getDates_InbetweenDays(String fromDate,
                                                           String toDate, int day, int month) {
        ArrayList<String> dates = new ArrayList<String>();
        DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(fromDate);
            date2 = df1.parse(toDate);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        if (cal1.get(Calendar.MONTH) != month) {
            cal1.add(Calendar.MONTH, 1);
            cal1.set(Calendar.DAY_OF_MONTH, 1);
        }

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2)) {
            if (day > 0) {
                if (cal1.get(Calendar.DAY_OF_WEEK) == day) { // if(String.valueOf(cal1.get(Calendar.DAY_OF_WEEK)).equals(day)){
                    String date = String.valueOf(cal1.get(Calendar.MONTH) + 1)
                            + "/" + String.valueOf(cal1.get(Calendar.DATE))
                            + "/" + String.valueOf(cal1.get(Calendar.YEAR));
                    System.out.println("date = " + date);
                    // dates.add(cal1.getTime());
                    dates.add(date);
                }
            } else {
                System.out.println("DAY_OF_WEEK = "
                        + cal1.get(Calendar.DAY_OF_WEEK));
                System.out.println("cal1.getTime() = " + cal1.getTime());
                String date = String.valueOf(cal1.get(Calendar.MONTH) + 1)
                        + "/" + String.valueOf(cal1.get(Calendar.DATE)) + "/"
                        + String.valueOf(cal1.get(Calendar.YEAR));
                dates.add(date);

            }
            cal1.add(Calendar.DATE, 1);

        }
        return dates;
    }

    /**
     * Method to get in between dates
     *
     * @param year      where year format = yyyy
     * @param month     where month format = MM
     * @param startDate format = MM/DD/yyyy pass black if don't want it
     * @return list of dates as String of that specific month and Year
     */
    public static ArrayList<String> getDates_InbetweenDays_basedOnMonthYearDay(
            int day, int month, int year, String startDate) {
        ArrayList<String> dates = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        c.set(year, month, 1); // ------>
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endDate = String.valueOf(month + 1) + "/"
                + c.getActualMaximum(Calendar.DATE) + "/" + year;
        System.out
                .println("startDate = " + startDate + " endDate = " + endDate);
        dates = getDates_InbetweenDays(startDate, endDate, day, month);
        return dates;
    }

    /**
     * This will return Date which will taken as string
     *
     * @returns Date format of given String date
     */
    public static Date convertStringIntoDate(String dateFormat, String strDate) {
        DateFormat formatter;
        Date dateToReturned = null;
        formatter = new SimpleDateFormat(dateFormat, Locale.US);
        try {
            dateToReturned = (Date) formatter.parse(strDate);
        }
        catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
        }
        System.out.println("Converted date is " + dateToReturned);

        return dateToReturned;
    }

    /**
     * This will converts given date with required format
     *
     * @param date string which you want to convert
     * @returns converted given date as string with required format
     */
    public static String convertDateFormat(String date, String sourceFormat,
                                           String destinationFormat) throws ParseException {
        String returnDate = "";

        if (date != null && !date.equals("")) {
            SimpleDateFormat formatter = new SimpleDateFormat(sourceFormat,
                    Locale.US);
            Date date1 = formatter.parse(date);

            formatter = new SimpleDateFormat(destinationFormat, Locale.US);
            returnDate = formatter.format(date1);
        }

        return returnDate;
    }

    /**
     * This function returns current date with require format
     *
     * @returns current date as String with required format
     */
    public static String getCurrentRequiredFormatDate(String requiredFormat) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat(requiredFormat,
                Locale.US);
        String returnDate = formatter.format(date);
        return returnDate;
    }

    public static String getSpinnerDate(String dateformat, String currentDate,
                                        int noDays, boolean flag) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat,
                Locale.US);
        Date myDate;
        String date = null;
        try {
            System.out.println("noDays-->" + noDays);
            myDate = dateFormat.parse(currentDate);
            long str = noDays * 24 * 60 * 60 * 1000;
            Date newDate;
            if (flag)
                newDate = new Date(myDate.getTime() + str); // 7 * 24 * 60 * 60
                // * 1000
            else
                newDate = new Date(myDate.getTime() - str);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy",
                    Locale.US);
            date = formatter.format(newDate);
            System.out.println("Date is --->>" + date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getddMMyyyy(String date, String currentFormat,
                                     String inWhatFormat) {

        SimpleDateFormat fromUser = new SimpleDateFormat(currentFormat,
                Locale.US);
        SimpleDateFormat myFormat = new SimpleDateFormat(inWhatFormat,
                Locale.US);
        String reformattedStr = "";
        try {
            reformattedStr = myFormat.format(fromUser.parse(date));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return reformattedStr;
    }

    public static boolean isPreviousMonth(String currSelDate, String todaysDate) {
        try {
            SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy",
                    Locale.US);
            Date d1 = null;
            Date d2 = null;

            try {
                d1 = outputFormat.parse(currSelDate);
                d2 = outputFormat.parse(todaysDate);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(d1);
            int currMonth = cal1.get(Calendar.MONTH);
            int currYear = cal1.get(Calendar.YEAR);

            Calendar todayCal = Calendar.getInstance();
            todayCal.setTime(d2);
            int todayMonth = todayCal.get(Calendar.MONTH);
            int todayYear = todayCal.get(Calendar.YEAR);

            boolean isPrevMonth = (todayYear > currYear) ? true : (todayMonth > currMonth) ? true : false;

            if (!(currSelDate.equals(todaysDate)) && isPrevMonth)
                return true;

        }
        catch (Exception e) {
        }
        return false;
    }

    public static String getConversion24HrsTo12Hrs(String time24Hrs) {

        String time12Hrs = "";
        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss",
                    Locale.US);
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a",
                    Locale.US);
            Date _24HourDt;

            _24HourDt = _24HourSDF.parse(time24Hrs);
            time12Hrs = _12HourSDF.format(_24HourDt);
            // System.out.println(_12HourSDF.format(_24HourDt));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return time12Hrs;

    }

    /**
     * Get the date in DEVICE_DATE_FORMAT format
     *
     * @param day
     * @param month
     * @param year
     * @return
     */
    public static String getDateDDMMMYYYY(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        String date = DEVICE_DATE_FORMAT.format(calendar.getTime());
        return date;
    }

    /**
     * Return dd , MM, yyyy
     *
     * @param dateStr Date in DEVICE_DATE_FORMAT
     * @return 0-dd, 1- mm, 2-yyyy
     */
    public static int[] splitDate(String dateStr) {

        int splittedDate[];
        try {
            Date date = DEVICE_DATE_FORMAT.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            splittedDate = new int[]{calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)};
        }
        catch (ParseException e) {
            e.printStackTrace();
            splittedDate = new int[]{0, 0, 0};
        }
        return splittedDate;
    }

    /**
     * Get the age
     *
     * @param yyyy
     * @param mm
     * @param dd
     * @return age in String
     */
    public static String getAge(final int yyyy, final int mm, final int dd) {

        LocalDate birthdate = new LocalDate(yyyy, mm, dd);
        LocalDate now = new LocalDate();
        Years age = Years.yearsBetween(birthdate, now);
        final String mAge = age.getYears() + "";
        return mAge;
    }
}
