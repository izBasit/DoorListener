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

package com.parkarcorp.iz.doorlistener.watchman;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.parkarcorp.iz.doorlistener.R;

import java.util.ArrayList;

import butterknife.InjectView;
import database.DatabaseHelper;
import utility.BaseActivity;

public class DoorDetailActivity extends BaseActivity {

    private String doorId;

    @InjectView(R.id.tvDoorName)
    TextView tvDoorName;

    @InjectView(R.id.lvDoorDetail)
    ListView lvDoorDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_detail);

        doorId = getIntent().getStringExtra("DOOR_ID");
        if (null != doorId) {
            final String doorName = getIntent().getStringExtra("DOOR_NAME");
            tvDoorName.setText(doorName);
            new GetDoorDetails().execute();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.door_detail, menu);
        return true;
    }

    private class GetDoorDetails extends AsyncTask<Void, Void, Void> {

        private DoorDetailAdapter mAdapter;

        @Override
        protected Void doInBackground(Void... voids) {

            final ArrayList<DoorDetailBean> mArr = DatabaseHelper.getInstance(mContext).getDoorDetails(doorId);
            if (null != mArr && mArr.size() > 0) {
                mAdapter = new DoorDetailAdapter(mArr, mContext);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (null != mAdapter && mAdapter.getCount() > 0)
                lvDoorDetail.setAdapter(mAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
