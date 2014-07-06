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
package notification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.parkarcorp.iz.doorlistener.LoginActivity;

import utility.StringUtility;


public class receiveSMSService extends IntentService {
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public receiveSMSService() {
        super("receiveSMSService");
    }

    public static final String TAG = "Reception Service";

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle

            final String msg = extras.getString("SMS");


        }
    }

    private void makeMessage(String msg) {

        if (!msg.equals("") || msg.contains("#")) {
            String temp[] = StringUtility.split(msg, '#');
            String header = temp[0];
            Log.d(TAG, "Header Message :" + header);
            if (header.trim().contains("DLV")) {
                sendNotification("Del. No. " + temp[1], "Against SAP SO.No. " + temp[2], 123);
            } else if (header.trim().contains("PGI")) {
                sendNotification("PGI No. " + temp[1], "Against Del. No." + temp[2], 99);
            } else if (header.trim().contains("INV")) {
                sendNotification("Inv. No. " + temp[1], "Against Del. No." + temp[2], 157);
            }
        }
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message. 
    private void sendNotification(String contentTitle, String contentText, int notificationId) {
//    	DLV#INTF_SO_NO#DELIVERY_NO#PGI_NO#INVOICENO

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, LoginActivity.class), 0);

    	/*Bitmap largeIcon= BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.ic_launcher);*/

        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
//        .setSmallIcon(R.drawable.ic_launcher)
//        .setLargeIcon(largeIcon)
                        .setContentTitle(contentTitle)
                        .setContentText(contentText)
                        .setAutoCancel(true)
                        .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0));


//        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(notificationId, mBuilder.build());
    }


}
