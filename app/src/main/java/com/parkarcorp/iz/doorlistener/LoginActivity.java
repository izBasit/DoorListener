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

package com.parkarcorp.iz.doorlistener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.parkarcorp.iz.doorlistener.watchman.DoorListActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import customview.FloatingLabelLayout;
import database.DatabaseHelper;
import utility.CustomToast;


public class LoginActivity extends ActionBarActivity {

    public final static String SAVED_PASSWORD = "com.iz.doorlistener.loginid";
    public final static String APP_SHARED_PREF = "com.iz.doorlistener";
    private final static String DEFAULT_PASSWORD = "anvil";
    @InjectView(R.id.spUserType)
    Spinner spUserType;
    @InjectView(R.id.btnLogin)
    Button btnLogin;
    @InjectView(R.id.etPassword)
    EditText etPass;
    @InjectView(R.id.flPass)
    FloatingLabelLayout flPass;
    private Context mContext;
    /**
     * Flag denoting whether this is a fresh install or the user
     * already exists.
     * false = User already exits
     * true = First time login
     */
    private boolean isFirstTime = false;
    private String mStoredPass;
    private DatabaseHelper mDbHelper;
    private SharedPreferences mSharedPrefs;
    private String selUserType = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        mContext = this;

        mSharedPrefs = getSharedPreferences(APP_SHARED_PREF, Context.MODE_PRIVATE);
        mStoredPass = mSharedPrefs.getString(SAVED_PASSWORD, DEFAULT_PASSWORD);

        setSpinner();
        setButton();
    }

    /**
     * Animation for shaking a view.
     *
     * @param view View which is to be animated.
     */
    private void shakeAnimation(View view) {
        Animation shake = AnimationUtils.loadAnimation(mContext, R.anim.shake);
        view.startAnimation(shake);
    }

    /**
     * Method to set Spinner data
     */
    private void setSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.user_type, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spUserType.setAdapter(adapter);
        selUserType = (String) adapter.getItem(0);
        spUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selUserType = (String) parent.getItemAtPosition(position);

                if (!selUserType.equals("Watchman")) {
                    flPass.setVisibility(View.VISIBLE);
                } else {
                    flPass.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setButton() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent();
                if (selUserType.equals(getString(R.string.watchman))) {
                    intent.setClass(mContext, DoorListActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    if (etPass.getText().toString().equals(mStoredPass)) {
                        intent.setClass(mContext, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        CustomToast.showMessage(mContext, "Incorrect Password.");
                        shakeAnimation(etPass);
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
     * Method to hide Keyboard
     */
    private void hideKeyboard() {
        View focus = getCurrentFocus();
        if (null != focus) {
            IBinder binder = focus.getWindowToken();
            InputMethodManager imeManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imeManager.hideSoftInputFromWindow(binder, 0);
        }
    }
}
