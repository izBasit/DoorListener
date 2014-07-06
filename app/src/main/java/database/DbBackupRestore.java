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

package database;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class DbBackupRestore {
    /**
     * Called when the activity is first created.
     */
    public static final String DatabaseName = "mprescribe.db";

    public static void backupDb(String dbPath, String dbName) throws IOException {
        File sd = Environment.getExternalStorageDirectory();
        if (sd.canWrite()) {

            String currentDBPath = dbPath;

            String backupPath = Environment
                    .getExternalStorageDirectory()
                    + "/mPrescribe/";
            File backUpDir = new File(backupPath);
            if (!backUpDir.exists())
                backUpDir.mkdirs();

            String backupDBPath = backupPath + dbName;

            File currentDB = new File(currentDBPath);
            File backupDB = new File(backupDBPath);

            if (backupDB.exists())
                backupDB.delete();

            if (currentDB.exists()) {
                copy(currentDB, backupDB);
            }
        }
    }

    private void makeLogsFolder() {
        try {
            File sdFolder = new File(Environment.getExternalStorageDirectory()
                    + "/database");
            sdFolder.mkdirs();
        }
        catch (Exception e) {
            // com.mobien.pmi.screen.Login.UTILITY.writeToSDFile(e.getMessage());
        }
    }

    private static void copy(File from, File to) throws FileNotFoundException,
            IOException {
        FileChannel src = null;
        FileChannel dst = null;
        try {
            src = new FileInputStream(from).getChannel();
            dst = new FileOutputStream(to).getChannel();
            dst.transferFrom(src, 0, src.size());
        }
        finally {
            if (src != null)
                src.close();
            if (dst != null)
                dst.close();
        }
    }
}