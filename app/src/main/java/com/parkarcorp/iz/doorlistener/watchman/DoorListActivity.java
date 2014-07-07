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

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.parkarcorp.iz.doorlistener.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import database.DatabaseHelper;
import utility.BaseActivity;
import utility.DateUtil;

public class DoorListActivity extends BaseActivity {
    @InjectView(R.id.tvDate)
    TextView tvDate;

    @InjectView(R.id.lvDoorList)
    ListView lvDoorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_list);

        ButterKnife.inject(this);
        tvDate.setText(DateUtil.getDateTime());
        new GetDoorList().execute();

        lvDoorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final DoorListBean doorBean = (DoorListBean) lvDoorList.getItemAtPosition(position);

                final Intent intent = new Intent(mContext, DoorDetailActivity.class);
                intent.putExtra("DOOR_ID", doorBean.getDoorId());
                intent.putExtra("DOOR_NAME", doorBean.getDoorName());
                startActivity(intent);
            }
        });
    }

    private class GetDoorList extends AsyncTask<Void, Void, Void> {

        private DoorListAdapter mAdapter;

        @Override
        protected Void doInBackground(Void... voids) {

            final ArrayList<DoorListBean> mArr = DatabaseHelper.getInstance(mContext).getDoorList();
            if (null != mArr && mArr.size() > 0) {
                mAdapter = new DoorListAdapter(mArr, mContext);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (null != mAdapter && mAdapter.getCount() > 0)
                lvDoorList.setAdapter(mAdapter);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.door_list, menu);
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
}
