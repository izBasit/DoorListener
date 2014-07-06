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

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import utility.LogUtility;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DatabaseVersion = 1;
    private static SQLiteDatabase sqLiteDb = null;
    /**
     * Package Name
     */
    private static final String PACKAGE = "com.mobien.mprescribe.mprescribe";
    private Context mContext = null;
    private static String DB_OPEN_MODE = "C"; // C For Close, W for Write, R For
    // Read
    private static String TAG = "dbhelper";
    private static DatabaseHelper sInstance;

    public static final String DATABASE_NAME = "mprescribe.db";

    public static DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DatabaseVersion);
        mContext = context;
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

    public String getDocName() {
        Cursor cursor = null;
        String query = "SELECT USERNAME FROM USERINFO";
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
        return "";

    }

    public String[] getDocInfo() {

        Cursor cursor = null;
        String query = "SELECT USERNAME, QUALIFICATION FROM USERINFO";
        try {

            cursor = ExecuteRawSql(query);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return new String[]{cursor.getString(0), cursor.getString(1)};
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
     * Get Doctor's details
     * @return DocProfileBean
     *//*
    public DocProfileBean getDocDetails(){

        Cursor cursor = null;
        String query = "SELECT * FROM USERINFO";
        try {

            cursor = ExecuteRawSql(query);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                DocProfileBean tempBean = new DocProfileBean(cursor.getString(0), cursor.getString(2),
                        cursor.getString(3),cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),
                        cursor.getString(8), cursor.getString(2), cursor.getString(9));

                return tempBean;
            }

        } catch (Exception e) {
            LogUtility.NoteLog(e);
        } finally {
            closeDB(cursor);
        }
        return null;
    }*/

    /**
     * Returns Last Visit Date and Transaction date
     *
     * @param patientId Patient registration Id
     * @return 0 - Transaction Id
     * 1 - Visit Date.
     */
    public String[] getLastVisitPatientInfo(String patientId) {

        Cursor cursor = null;
        String query = "SELECT TRANSACTION_ID, VISIT_DATE FROM PRESCRIPTION_HEADER WHERE PATIENT_ID = '" + patientId +
                "' ORDER BY VISIT_DATE DESC";
        try {

            cursor = ExecuteRawSql(query);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return new String[]{cursor.getString(0), cursor.getString(1)};
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
     * Get Prescription List.
     * @param transactionId Last visit date's transaction id.
     * @return
     *//*
    public ArrayList<DosageBean> getLastVisitPrescriptionInfo(String transactionId){

        final ArrayList<DosageBean> mArr = new ArrayList<DosageBean>();
        Cursor cursor = null;
        String query = "SELECT MOLECULE_NAME, DURATION, DOSAGE FROM PRESCRIPTION_DETAIL WHERE " +
                "TRANSACTION_CODE = '"+transactionId+"';";
        try {

            cursor = ExecuteRawSql(query);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    DosageBean tempBean = new DosageBean();
                    tempBean.setMoleculeName(cursor.getString(0));
                    tempBean.setDuration(cursor.getString(1));
                    tempBean.setDosage(cursor.getString(2));

                    mArr.add(tempBean);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            LogUtility.NoteLog(e);
        } finally {
            closeDB(cursor);
        }
        return mArr;
    }


    *//**
     * Get Patient List.
     * @return patient list
     *//*
    public ArrayList<PatientBean> getPatientList(){

        final ArrayList<PatientBean> mArr = new ArrayList<PatientBean>();
        Cursor cursor = null;
        String query = "SELECT PATIENT_ID, PATIENT_NAME, PATIENT_AGE, LAST_VISIT_DATE FROM PATIENT_INFO ORDER BY PATIENT_NAME ASC;";
        try {

            cursor = ExecuteRawSql(query);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    PatientBean tempBean = new PatientBean(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3));
                    mArr.add(tempBean);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            LogUtility.NoteLog(e);
        } finally {
            closeDB(cursor);
        }
        return mArr;
    }

    *//**
     * Get Patient details
     * @param patientId patient Id
     * @return
     *//*
    public PatientDetailsBean getPatientDetails(String patientId){

        Cursor cursor = null;
        String query = "SELECT * FROM PATIENT_INFO WHERE PATIENT_ID = '"+patientId+"'";
        try {

            cursor = ExecuteRawSql(query);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                PatientDetailsBean tempBean = new PatientDetailsBean(cursor.getString(0), cursor.getString(1),cursor.getString(12),
                        cursor.getString(2),cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8), cursor.getString(13));

                return tempBean;
            }

        } catch (Exception e) {
            LogUtility.NoteLog(e);
        } finally {
            closeDB(cursor);
        }
        return null;
    }*/

    /**
     * Get Patient Name
     *
     * @param patientId
     * @return patient name
     */
    public String getPatientName(String patientId) {

        Cursor cursor = null;
        String query = "SELECT PATIENT_NAME FROM PATIENT_INFO WHERE PATIENT_ID = '" + patientId + "'";
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

    /**
     * Get Patient header details
     * @param patientId
     * @return patient details
     *//*
    public PatientDetailsBean getPatientHeaderDetails(String patientId){

        Cursor cursor = null;
        String query = "SELECT HEIGHT, WEIGHT, PATIENT_AGE, BLOOD_PRESSURE FROM PATIENT_INFO WHERE PATIENT_ID = '"+patientId+"'";
        try {

            cursor = ExecuteRawSql(query);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                final PatientDetailsBean tempBean = new PatientDetailsBean();
                tempBean.setHeight(cursor.getString(0));
                tempBean.setWeight(cursor.getString(1));
                tempBean.setAge(cursor.getString(2));
                tempBean.setBloodPressure(cursor.getString(3));

                return tempBean;
            }

        } catch (Exception e) {
            LogUtility.NoteLog(e);
        } finally {
            closeDB(cursor);
        }
        return null;
    }

    public boolean savePatientDetails(PatientDetailsBean patientDetailsBean) {
        boolean executionStatus = false;
        try {

            String insertQuery = "INSERT INTO PATIENT_INFO (PATIENT_NAME,DOB,GENDER,HEIGHT," +
                    " WEIGHT,PHONE_NO,MEDICAL_HISTORY,ALLERGIES,FAMILY_HISTORY," +
                    " PATIENT_AGE, BlOOD_PRESSURE, PATIENT_ID) VALUES('"+patientDetailsBean.getPatientName()+"','" +
                    patientDetailsBean.getDob()+"','" +
                    patientDetailsBean.getGender()+"','" +
                    patientDetailsBean.getHeight()+"','" +
                    patientDetailsBean.getWeight()+"','" +
                    patientDetailsBean.getPhoneNo()+"','" +
                    patientDetailsBean.getMedicalHistory()+"','" +
                    patientDetailsBean.getAllergies()+"','" +
                    patientDetailsBean.getFamilyHistory()+"','" +
                    patientDetailsBean.getAge()+"','" +
                    patientDetailsBean.getBloodPressure()+"','" +
                    DateUtil.getDateddMMyyyyHHmmSS()+"')";

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
     * Save Prescription
     * @param prescriptionBean
     * @return Execution Status
     *//*
    public boolean savePrescriptionHeader(PrescriptionBean prescriptionBean) {
        boolean executionStatus = false;

        final String prescriptionId = DateUtil.getDateddMMyyyyHHmmSS();
        final String prescriptionDate = DateUtil.getDateInddMMyyyyWithDash();
        try {

            String insertQuery = "INSERT INTO PRESCRIPTION_HEADER (TRANSACTION_ID,VISIT_DATE,PATIENT_ID) " +
                    "VALUES('"+prescriptionId+"','" +
                    prescriptionDate+"','" +
                    prescriptionBean.getPatientId()+"')";
            executionStatus = ExecuteSql(insertQuery);

            if(executionStatus) {
                updateLastVisitDate(prescriptionBean.getPatientId(), prescriptionDate);
                final ArrayList<DosageBean> mArr = prescriptionBean.getDosage();
                for(int i= 0; i < mArr.size(); i++) {
                    savePrescriptionDetails(prescriptionId,mArr.get(i));
                }
            }

        } catch (Exception ex) {
            Log.d(TAG, "SQLite exception: " + ex.getLocalizedMessage());
            LogUtility.NoteLog(ex);
        } finally {
            closeDB(null);
            return executionStatus;
        }

    }

    *//**
     * Inserting dosage
     * @param prescriptionId prescription / transaction id
     * @param dosageBean dosage of medicine
     * @return Execution Status
     *//*
    private boolean savePrescriptionDetails(String prescriptionId, DosageBean dosageBean) {
        boolean executionStatus = false;
        try {

            String insertQuery = "INSERT INTO PRESCRIPTION_DETAIL (TRANSACTION_CODE,MOLECULE_NAME,DURATION,DOSAGE," +
                    " REMARK ) VALUES('"+prescriptionId+"','" +
                    dosageBean.getMoleculeName()+"','" +
                    dosageBean.getDuration()+"','" +
                    dosageBean.getDosage()+"','" +
                    "')";
            executionStatus = ExecuteSql(insertQuery);

        } catch (Exception ex) {
            Log.d(TAG, "SQLite exception: " + ex.getLocalizedMessage());
            LogUtility.NoteLog(ex);
        } finally {
            closeDB(null);
            return executionStatus;
        }

    }*/

    /**
     * Update Last Visit Details of patient
     *
     * @param patientId     patient id of patient who is to be updated
     * @param lastVisitDate visit date
     * @return Execution Status
     */
    private boolean updateLastVisitDate(String patientId, String lastVisitDate) {
        boolean executionStatus = false;
        try {
            String updateQuery = "UPDATE PATIENT_INFO SET LAST_VISIT_DATE = '" + lastVisitDate + "'" +
                    " WHERE PATIENT_ID = '" + patientId + "'";

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

 /*   *//**
     * Updating patient information
     * @param patientDetailsBean Patient Info
     * @param patientId patient id of patient who is to be updated
     * @return Execution Status
     *//*
    public boolean updatePatientDetails(PatientDetailsBean patientDetailsBean, String patientId ) {
            boolean executionStatus = false;
            try {
                String updateQuery = "UPDATE PATIENT_INFO SET PATIENT_NAME = '"+patientDetailsBean.getPatientName()+"'," +
                        "DOB = '"+patientDetailsBean.getDob()+"'," +
                        "GENDER = '"+patientDetailsBean.getGender()+"'," +
                        "HEIGHT ='"+patientDetailsBean.getHeight()+"'," +
                        " WEIGHT ='"+patientDetailsBean.getWeight()+"'," +
                        "PHONE_NO ='"+patientDetailsBean.getPhoneNo()+"'," +
                        "MEDICAL_HISTORY ='"+patientDetailsBean.getMedicalHistory()+"'," +
                        "ALLERGIES ='"+patientDetailsBean.getAllergies()+"'," +
                        "FAMILY_HISTORY ='"+patientDetailsBean.getFamilyHistory()+"',"+
                        "PATIENT_AGE ='"+patientDetailsBean.getAge()+"'," +
                        "BLOOD_PRESSURE ='"+patientDetailsBean.getBloodPressure()+"'" +
                        " WHERE PATIENT_ID = '"+patientId+"'";

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
     * Method to get drugs
     *
     * @return drug list.
     */
    public ArrayList<String> getDrugs() {
        final ArrayList<String> mArr = new ArrayList<String>();
        Cursor cursor = null;
        String query = "SELECT DISTINCT DRUG_NAME FROM DRUGS ORDER BY DRUG_NAME ASC;";
        try {

            cursor = ExecuteRawSql(query);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    mArr.add(cursor.getString(0).trim());
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
}
