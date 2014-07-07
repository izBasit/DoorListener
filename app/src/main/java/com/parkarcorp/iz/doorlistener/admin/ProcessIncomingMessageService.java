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

package com.parkarcorp.iz.doorlistener.admin;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.parkarcorp.iz.doorlistener.LoginActivity;
import com.parkarcorp.iz.doorlistener.R;

import java.util.Random;

import database.DatabaseHelper;

public class ProcessIncomingMessageService extends IntentService {
    private DatabaseHelper mDbHandle;

    public ProcessIncomingMessageService() {

        super("ProcessIncomingMessageService");
        mDbHandle = DatabaseHelper.getInstance(getApplicationContext());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (null != intent) {

            final String sms = intent.getExtras().getString("SMS");
            if (null != sms && !"".equals(sms)) {
                final String[] smsArr = sms.split("|");
                if (null != smsArr && smsArr.length > 0) {
                    /* Data before the pipe is something we are interested in. So thats what we are using. */

                    final String parsedSMS = smsArr[0];

                }

            }
        }

    }

    /**
     * Message to be used!!
     * 1. Status Message : DST#DOOR_ID#STATUS|
     * 2. Power : DPW#DOOR_ID#POWER|
     * 3. Power Critical : DPC#DOOR_ID|
     * 4. Add Door : ADD#DOOR_ID#DOOR_NAME|
     * 5. Check Battery Status : BTR#DOOR_ID
     * 6. Battery Status : BTS#DOOR_ID#TIME|
     * 7. Change Ph No : CHU#OLDPHNO#NEWPHNO
     *
     * @param msg Msg from server.
     */
    private void parseMessage(final String msg) {

        final String[] mParsedSMS = msg.split("#");
        if (null != mParsedSMS && mParsedSMS.length > 0) {
            /* Status SMS */
            if (getApplicationContext().getResources().getString(R.string.sms_status).equalsIgnoreCase(mParsedSMS[0])) {
                final String doorName = mDbHandle.getDoorName(mParsedSMS[1]);
                //TODO  STATUS DEKH SMS MEIN KYA AA RAHA HAI!!
                String doorStatus = mParsedSMS[2];
                if (doorStatus.equals("O"))
                    doorStatus = getApplicationContext().getResources().getString(R.string.open);
                else if (doorStatus.equals("C"))
                    doorStatus = getApplicationContext().getResources().getString(R.string.closed);
                else
                    doorStatus = getApplicationContext().getResources().getString(R.string.partial);

                final String notificationMsg = String.format(getApplicationContext().getResources().getString(R.string.door_status), doorName, doorStatus);
                showNotification(doorName, notificationMsg);

                mDbHandle.addDoorDetail(mParsedSMS[1], doorStatus);

            } else if (getApplicationContext().getResources().getString(R.string.sms_critical_power).equalsIgnoreCase(mParsedSMS[0])) {
                final String doorName = mDbHandle.getDoorName(mParsedSMS[1]);
                final String notificationMsg = String.format(getApplicationContext().getResources().getString(R.string.power_critical), doorName);
                showNotification("CRITICAL POWER", notificationMsg);

            } else if (getApplicationContext().getResources().getString(R.string.sms_battery_status).equalsIgnoreCase(mParsedSMS[0])) {
                final String doorName = mDbHandle.getDoorName(mParsedSMS[1]);
                final String notificationMsg = String.format(getApplicationContext().getResources().getString(R.string.battery_stat), doorName, mParsedSMS[2]);
                showNotification("Battery Status", notificationMsg);
            } else if (getApplicationContext().getResources().getString(R.string.sms_add_door).equalsIgnoreCase(mParsedSMS[0])) {
                final String doorId = mParsedSMS[1];
                final String doorName = mParsedSMS[2];
                mDbHandle.addDoor(doorId, doorName);

                showNotification("New Door added", doorName + " has been added.");
            }
        }


    }

    private void showNotification(String contentTitle, String contentText) {

        final Random r = new Random();
        final int notificationId = r.nextInt();

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, LoginActivity.class), 0);

        Bitmap largeIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.ic_launcher);

        NotificationManager mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon(largeIcon)
                        .setContentTitle(contentTitle)
                        .setContentText(contentText)
                        .setAutoCancel(true)
                        .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0));

//        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(notificationId, mBuilder.build());
    }

}
