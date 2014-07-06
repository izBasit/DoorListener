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

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * Class to log the exceptions in a text file
 *
 * @author Basit Parkar
 * @version 1.0
 * @date Jan 1, 2014
 */
public class LogUtility {


    /**
     * If true, the log will also print the class from where log has been generated.
     */
    private static final boolean PRINT_DETAILED_LOG = true;

    /**
     * TODO : Set Package Name
     * Package Name
     */
    private static final String PACKAGE = "com.mobien.mprescribe.mprescribe";

    /**
     * TODO : Change path of log file
     * Path of log file
     */
    public static final String LOG_FILE_PATH = Environment.getExternalStorageDirectory() + "";

    /*
     * Method to write log to a file
     * @param e
     * 		Exception
     *
     * Log File Format : date | time | (exception location in code) | actual exception
     */
    public static void NoteLog(Exception e) {

        final StringBuilder mLogFormat = new StringBuilder();
        LogUtility objLog = new LogUtility();

        mLogFormat.append(objLog.getTodaysDate());
        mLogFormat.append("|" + objLog.getCurrentTime());
        if (PRINT_DETAILED_LOG)
            mLogFormat.append(objLog.getProjectSpecificClass(e));
        mLogFormat.append("|" + e.getMessage());

        File logFile = new File(LOG_FILE_PATH + "/log.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        try {
            // BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
                    true));
            buf.append(mLogFormat);
            buf.newLine();
            buf.close();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    /**
     * Method for manual message logging for the developer
     *
     * @param manualLog
     */
    public static void NoteLog(String manualLog) {

        final StringBuilder mLogFormat = new StringBuilder();
        mLogFormat.append(manualLog);

        File logFile = new File(LOG_FILE_PATH + "/log.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        try {
            // BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
                    true));
            buf.append(mLogFormat);
            buf.newLine();
            buf.close();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    /**
     * Method to get the line in the stack trace referring to the current project
     *
     * @param ex Exception
     * @return Pipe '|' separated
     * Class Name,
     * Method Name,
     * Line Number
     */
    private String getProjectSpecificClass(Exception ex) {
        final StringBuilder mLog = new StringBuilder();
        for (int i = 0; i < ex.getStackTrace().length; i++) {
            if (ex.getStackTrace()[i].getClassName().contains(PACKAGE))
                mLog.append("|" + ex.getStackTrace()[i].getClassName() + "|" + ex.getStackTrace()[i].getMethodName()
                        + "|" + ex.getStackTrace()[i].getLineNumber());

        }

        return mLog.toString();
    }


    private String getCurrentTime() {
        String time = "";
        final Calendar c2 = Calendar.getInstance();
        final int curr_hour = c2.get(Calendar.HOUR_OF_DAY);
        String str_hour = "" + curr_hour;
        if (str_hour.length() == 1) {
            str_hour = "0" + str_hour;
        }
        int curr_min = c2.get(Calendar.MINUTE);
        String str_min = "" + curr_min;
        if (str_min.length() == 1) {
            str_min = "0" + str_min;
        }

        int curr_milisecond = c2.get(Calendar.MILLISECOND);
        time = str_hour + str_min + curr_milisecond;
        return time;
    }

    private String getTodaysDate() {

        final Calendar c = Calendar.getInstance();
        final SimpleDateFormat df1 = new SimpleDateFormat("ddMMyyyy", Locale.US);
        final String formattedDate1 = df1.format(c.getTime());
        return formattedDate1;
    }

}
