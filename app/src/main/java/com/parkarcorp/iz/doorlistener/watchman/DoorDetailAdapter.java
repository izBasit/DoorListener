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

package com.parkarcorp.iz.doorlistener.watchman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parkarcorp.iz.doorlistener.R;

import java.util.ArrayList;

/**
 * Created by Basit on 6/15/2014.
 */
public class DoorDetailAdapter extends BaseAdapter {

    private ArrayList<DoorDetailBean> mData;

    private Context mContext;
    private LayoutInflater inflater = null;

    DoorDetailAdapter(ArrayList<DoorDetailBean> mData, Context context) {
        mContext = context;
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public DoorDetailBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {

            convertView = inflater.inflate(
                    R.layout.door_detail_list_item, null);
            holder = new ViewHolder();

            holder.tvDoorStatus = (TextView) convertView
                    .findViewById(R.id.tvDoorStatus);
            holder.tvTime = (TextView) convertView
                    .findViewById(R.id.tvTime);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final DoorDetailBean tempBean = mData.get(position);

        final String doorStatus = tempBean.getDoorStatus();
        holder.tvDoorStatus.setText(doorStatus);
        if (null != doorStatus || mContext.getString(R.string.open).equals(doorStatus)) {
            holder.tvDoorStatus.setTextColor(mContext.getResources().getColor(android.R.color.holo_green_dark));
        } else if (null != doorStatus || mContext.getString(R.string.closed).equals(doorStatus)) {
            holder.tvDoorStatus.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_light));
        } else {
            holder.tvDoorStatus.setTextColor(mContext.getResources().getColor(android.R.color.holo_orange_dark));
        }
        holder.tvTime.setText(tempBean.getDoorTime());


        return convertView;

    }

    static class ViewHolder {
        private TextView tvDoorStatus, tvTime;
    }
}
