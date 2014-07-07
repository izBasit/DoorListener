

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
 *    * @modified 7/7/14 12:59 PM
 *
 */

package com.parkarcorp.iz.doorlistener;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import butterknife.ButterKnife;
import butterknife.InjectView;
import utility.BaseActivity;
import utility.LogUtility;

public class ChangePhoneNo extends BaseActivity {

    @InjectView(R.id.spUser)
    Spinner spUser;
    @InjectView(R.id.etOldPhoneNo)
    EditText etOldPhoneNo;
    @InjectView(R.id.etNewPhoneNo)
    EditText etNewPhoneNo;
    @InjectView(R.id.llContainer)
    LinearLayout llContainer;
    @InjectView(R.id.btnSave)
    com.dd.CircularProgressButton btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_no);
        ButterKnife.inject(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.change_phone_no, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to send Message
     *
     * @param message
     * @param number
     */
    private void sendMessage(String message, String number) {
        try {
            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(number, null, message, null, null);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            LogUtility.NoteLog(ex);
        }
    }
}
