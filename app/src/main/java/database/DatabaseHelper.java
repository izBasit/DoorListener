/*
 *
 *    * Copyright 2014 Basit Parkar.
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
 *    * @date 7/7/14 1:02 PM
 *    * @modified 7/7/14 12:57 PM
 *
 */

package database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.parkarcorp.iz.doorlistener.admin.UserBean;
import com.parkarcorp.iz.doorlistener.watchman.DoorDetailBean;
import com.parkarcorp.iz.doorlistener.watchman.DoorListBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import utility.DateUtil;
import utility.LogUtility;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mprescribe.db";
    private static final int DatabaseVersion = 1;
    /**
     * Package Name
     */
    private static final String PACKAGE = "com.mobien.mprescribe.mprescribe";
    private static SQLiteDatabase sqLiteDb = null;
    private static String DB_OPEN_MODE = "C"; // C For Close, W for Write, R For
    // Read
    private static String TAG = "dbhelper";
    private static DatabaseHelper sInstance;
    private Context mContext = null;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DatabaseVersion);
        mContext = context;
    }

    public static DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            sqLiteDb = this.getWritableDatabase();
            sqLiteDb = db;
        }
        catch (Exception ex) {
            LogUtility.NoteLog(ex);
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query = "ALTER TABLE names ADD COLUMN hidden integer default 0";
        db.rawQuery(query, null);

    }

    private SQLiteDatabase openDatabaseInReadableMode() throws SQLException {
        if (DB_OPEN_MODE.equals("C")) {
            sqLiteDb = this.getReadableDatabase();
        } else if (DB_OPEN_MODE.equals("W")) {
            closeDB(null);
            sqLiteDb = this.getWritableDatabase();
        }
        DB_OPEN_MODE = "R";
        return sqLiteDb;
    }

    private SQLiteDatabase openDatabaseInWritableMode() throws SQLException {
        if (DB_OPEN_MODE.equals("C")) {
            sqLiteDb = this.getWritableDatabase();
        } else if (DB_OPEN_MODE.equals("R")) {
            closeDB(null);
            sqLiteDb = this.getWritableDatabase();
        }
        DB_OPEN_MODE = "W";
        return sqLiteDb;
    }

    /**
     * Method to close DB if open
     *
     * @param cursor pass cursor is cursor object is available, else pass null
     * @throws android.database.SQLException
     */
    public void closeDB(Cursor cursor) throws SQLException {
        if (cursor != null) {
            cursor.close();
        }
        DB_OPEN_MODE = "C";
    }

    /**
     * Method to execute SQL Query
     *
     * @param s SQL Query
     * @return cursor of returned results
     */
    public synchronized Cursor ExecuteRawSql(String s) {//Select Query
        try {

//			if(sqLiteDb != null)
//				closeDB(null);

            sqLiteDb = openDatabaseInReadableMode();
            Log.d(TAG, "Actual Query--->>" + s);
            return sqLiteDb.rawQuery(s, null);

        }
        catch (Exception e) {
            e.printStackTrace();
            LogUtility.NoteLog(e);
            return null;
        }
    }

    /**
     * Method to execute SQL Query
     *
     * @param s SQL Query
     * @return true if query was successfully executed.
     */
    public synchronized boolean ExecuteSql(String s) {// Update Query
        try {

            if (sqLiteDb != null)
                closeDB(null);

            sqLiteDb = openDatabaseInReadableMode();
            sqLiteDb.execSQL(s);
            Log.d(TAG, "Actual Query--->>" + s);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            LogUtility.NoteLog(e);
            return false;
        }
        finally {
            closeDB(null);
        }
    }

    private boolean batchCreate(Context _mContext, String[] _recordList) {

        sqLiteDb = openDatabaseInReadableMode();

        for (int i = 0; i < _recordList.length; i++) {
            Log.d(TAG, "_recordList[i] ====> " + _recordList[i]);
            sqLiteDb.execSQL(_recordList[i] + ";");
        }
        closeDB(null);
        return true;
    }

    /*public boolean saveDocProfile(DocProfileBean docProfileBean) {
        boolean executionStatus = false;
        try {

            String insertQuery = "INSERT INTO USERINFO (USERNAME,PASSWORD,GENDER, QUALIFICATION, SPECIALITY, " +
                    "REGISTRATION_NO, HOSPITAL_NAME, PHONE_NO, ADDRESS, EMAIL) VALUES('"+docProfileBean.getDocName()+"','" +
                    docProfileBean.getPassword()+"','" +
                    docProfileBean.getDocGender()+"','" +
                    docProfileBean.getQualification()+"','" +
                    docProfileBean.getSpeciality()+"','" +
                    docProfileBean.getRegNo()+"','" +
                    docProfileBean.getHospitalName()+"','" +
                    docProfileBean.getClinicPhoneNo()+"','" +
                    docProfileBean.getClinicAddress()+"','" +
                    docProfileBean.getEmailId()+"')";

            executionStatus = ExecuteSql(insertQuery);

        } catch (Exception ex) {
            Log.d(TAG, "SQLite exception: " + ex.getLocalizedMessage());
            LogUtility.NoteLog(ex);
        } finally {
            closeDB(null);
            return executionStatus;
        }

    }

    *//**
     * Update Doctor's Profile.
     * @param docProfileBean
     * @return
     *//*
    public boolean updateDocProfile(DocProfileBean docProfileBean) {
        boolean executionStatus = false;
        try {

            String updateQuery = "UPDATE USERINFO SET USERNAME = '"+docProfileBean.getDocName()+"'," +
                    "GENDER = '"+docProfileBean.getDocGender()+"', QUALIFICATION = '"+docProfileBean.getQualification()+"'," +
                    " SPECIALITY = '"+docProfileBean.getSpeciality()+"', REGISTRATION_NO = '"+docProfileBean.getRegNo()+"',"+
                    " HOSPITAL_NAME = '"+docProfileBean.getHospitalName()+"', PHONE_NO = '"+docProfileBean.getClinicPhoneNo()+"',"+
                    " EMAIL = '"+docProfileBean.getEmailId()+"', ADDRESS = '"+docProfileBean.getClinicAddress()+"'";

            executionStatus = ExecuteSql(updateQuery);

        } catch (Exception ex) {
            Log.d(TAG, "SQLite exception: " + ex.getLocalizedMessage());
            LogUtility.NoteLog(ex);
        } finally {
            closeDB(null);
            return executionStatus;
        }

    }*/

    /**
     * Method to check if the table has any elements
     *
     * @param tableName
     * @return true if table has elements
     */
    public boolean hasElements(String tableName) {

        Boolean status = false;
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + tableName;
            cursor = ExecuteRawSql(query);

            int count = cursor.getCount();
            if (count > 0)
                status = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            LogUtility.NoteLog(e);
        }
        finally {
            closeDB(cursor);
        }

        return status;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDatabaseFromAssets() throws IOException {
        try {
            copyDatabase();
        }
        catch (IOException e) {
            LogUtility.NoteLog(e);
            throw new Error("Error copying database");
        }
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDatabase() throws IOException {

        AssetManager assetManager = mContext.getAssets();
        InputStream myInput = assetManager.open(DATABASE_NAME);

        getReadableDatabase();
        File mDbFile = mContext.getDatabasePath(DATABASE_NAME);
//        File mDbFile = new File("/data/data/" + PACKAGE + "/databases/"+DATABASE_NAME);
        OutputStream myOutput = new FileOutputStream(mDbFile);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;

        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    /**
     * Add a new Door
     *
     * @param doorId
     * @param doorName
     * @return If door is added successfully
     */
    public boolean addDoor(String doorId, String doorName) {
        boolean executionStatus = false;

        doorId = (null == doorId || doorId.equals("")) ? DateUtil.getDateddMMyyyyHHmmSS() : doorId;
        final String createdDate = DateUtil.getDateInddMMyyyyWithDash();
        try {

            String insertQuery = "INSERT INTO DOORHDR(DOOR_ID, DOOR_NAME,CREATED_DATE) " +
                    "VALUES('" + doorId + "','" +
                    doorName + "','" +
                    createdDate + "')";
            executionStatus = ExecuteSql(insertQuery);

        }
        catch (Exception ex) {
            Log.d(TAG, "SQLite exception: " + ex.getLocalizedMessage());
            LogUtility.NoteLog(ex);
        }
        finally {
            closeDB(null);
            return executionStatus;
        }

    }

    /**
     * Add a new Door
     *
     * @param doorId
     * @param doorStatus
     * @return If door detail is added successfully
     */
    public boolean addDoorDetail(String doorId, String doorStatus) {
        boolean executionStatus = false;

        final String createdDate = DateUtil.getDateInddMMyyyyWithDash();
        try {
            String insertQuery = "INSERT INTO DOORDTL(DOOR_ID, STATUS,DATE, TIME) " +
                    "VALUES('" + doorId + "','" +
                    doorStatus + "','" +
                    createdDate + "','" +
                    DateUtil.getCurrentTime() + "')";
            executionStatus = ExecuteSql(insertQuery);

        }
        catch (Exception ex) {
            Log.d(TAG, "SQLite exception: " + ex.getLocalizedMessage());
            LogUtility.NoteLog(ex);
        }
        finally {
            closeDB(null);
            return executionStatus;
        }

    }


    /**
     * Update Door Alias
     *
     * @param doorId
     * @param doorAlias
     * @return
     */
    public boolean updateDoorAlias(String doorId, String doorAlias) {
        boolean executionStatus = false;
        try {
            String updateQuery = "UPDATE DOORHDR( SET DOOR_ALIAS = '" + doorAlias + "'" +
                    " WHERE DOOR_ID = '" + doorId + "'";

            executionStatus = ExecuteSql(updateQuery);

        }
        catch (Exception ex) {
            Log.d(TAG, "SQLite exception: " + ex.getLocalizedMessage());
            LogUtility.NoteLog(ex);
        }
        finally {
            closeDB(null);
            return executionStatus;
        }

    }

    /**
     * Change Phone No
     * @param userId
     * @param newPhNo
     * @param userName
     * @return
     */
    public boolean changePhoneNo(String userId, String newPhNo, String userName) {
        boolean executionStatus = false;
        try {
            String updateQuery = "UPDATE USERS( SET USER_PHONE = '" + newPhNo + "', SET USER_NAME = '" + userName + "'" +
                    " WHERE USER_ID = '" + userId + "'";

            executionStatus = ExecuteSql(updateQuery);

        }
        catch (Exception ex) {
            Log.d(TAG, "SQLite exception: " + ex.getLocalizedMessage());
            LogUtility.NoteLog(ex);
        }
        finally {
            closeDB(null);
            return executionStatus;
        }

    }

    /**
     * Door List
     *
     * @return array list of door.
     */
    public ArrayList<DoorListBean> getDoorList() {
        final ArrayList<DoorListBean> mArr = new ArrayList<DoorListBean>();
        Cursor cursor = null;
        String query = "SELECT DISTINCT DH.DOOR_ID, DH.DOOR_NAME, DD.STATUS, DD.ENTRY_TIME FROM DOORHDR DH, DOORDTL DD WHERE DH.DOOR_ID = DD.DOOR_ID ORDER BY DH.DOOR_NAME ASC;";
        try {

            cursor = ExecuteRawSql(query);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    DoorListBean tempBean = new DoorListBean(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3));
                    mArr.add(tempBean);
                } while (cursor.moveToNext());
            }

        }
        catch (Exception e) {
            LogUtility.NoteLog(e);
        }
        finally {
            closeDB(cursor);
        }
        return mArr;

    }

    /**
     * Door Details
     *
     * @return array list of door details.
     */
    public ArrayList<DoorDetailBean> getDoorDetails(String doorId) {
        final ArrayList<DoorDetailBean> mArr = new ArrayList<DoorDetailBean>();
        Cursor cursor = null;
        String query = "SELECT STATUS, ENTRY_TIME FROM DOORDTL WHERE DOOR_ID = '" + doorId + "'  ORDER BY ENTRY_DATE DESC, ENTRY_TIME DESC;";
        try {

            cursor = ExecuteRawSql(query);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    DoorDetailBean tempBean = new DoorDetailBean(cursor.getString(0),cursor.getString(1));
                    mArr.add(tempBean);
                } while (cursor.moveToNext());
            }

        }
        catch (Exception e) {
            LogUtility.NoteLog(e);
        }
        finally {
            closeDB(cursor);
        }
        return mArr;

    }

    /**
     * User Details
     * @return array list of door details.
     */
    public UserBean getUserDetails(String userId) {
        Cursor cursor = null;
        String query = "SELECT USER_ID, USER_NAME, USER_PHONE FROM USERS WHERE USER_ID = '"+userId+"'";
        try {

            cursor = ExecuteRawSql(query);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                return new UserBean(cursor.getString(0), cursor.getString(1),cursor.getString(2));
            }

        }
        catch (Exception e) {
            LogUtility.NoteLog(e);
        }
        finally {
            closeDB(cursor);
        }
        return null;

    }


    /**
     * Door Details
     * @return array list of door details.
     */
    public String getDoorName(String doorId) {
        Cursor cursor = null;
        String query = "SELECT DOOR_NAME FROM DOORHDR WHERE DOOR_ID = '"+doorId+"'";
        try {

            cursor = ExecuteRawSql(query);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                return cursor.getString(0);
            }

        }
        catch (Exception e) {
            LogUtility.NoteLog(e);
        }
        finally {
            closeDB(cursor);
        }
        return null;

    }
}
