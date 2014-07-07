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

package utility;


import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import com.parkarcorp.iz.doorlistener.R;

/**
 * Alert Dialog fragment for Showing Alert Dialogs.
 *
 * @author Harshal Gaikwad
 */
public class AlertDialogFrag extends DialogFragment {

    public static AlertDialogFrag sInstance;
    private String mDialogMsg;
    private boolean mIsSingleOption;
    private DialogInterface.OnClickListener mPositiveClickListener;
    private DialogInterface.OnClickListener mNegativeClickListener;

    /**
     * This method give instance of AlertDialogFrag.
     *
     * @return Instance of AlertDialogFrag.
     */
    public static AlertDialogFrag getInstance() {
        if (sInstance == null) {
            sInstance = new AlertDialogFrag();
        }
        return sInstance;
    }

    /**
     * This method set required attribute for dialog .
     *
     * @param msg                   Message of dialog.
     * @param positiveClickListener Dialog Positive click listener.
     * @param negativeClickListener Dialog Negative click listener.
     * @param isSingleOption        Whether dialog contain one one click option.
     */
    public void setAttribute(String msg,
                             DialogInterface.OnClickListener positiveClickListener,
                             DialogInterface.OnClickListener negativeClickListener,
                             boolean isSingleOption) {
        mDialogMsg = msg;
        mIsSingleOption = isSingleOption;
        mPositiveClickListener = positiveClickListener;
        mNegativeClickListener = negativeClickListener;
    }

    /**
     * This method set required attribute for dialog for Single option dialog .
     *
     * @param msg Message of dialog.
     */
    public void setAttribute(String msg) {
        mDialogMsg = msg;
        mIsSingleOption = true;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder dialog = new Builder(getActivity())
                .setIcon(R.drawable.ic_launcher);

        TextView tvMsg = new TextView(getActivity());
        tvMsg.setText(mDialogMsg);
        tvMsg.setGravity(Gravity.CENTER);

        tvMsg.setPadding(10, 10, 10, 10);

        tvMsg.setTextSize(18);
        dialog.setView(tvMsg);
        dialog.setCancelable(false);
        if (mIsSingleOption == true) {
            dialog.setPositiveButton(getActivity().getString(R.string.ok),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }
            );
        } else {
            dialog.setPositiveButton(getActivity().getString(R.string.ok),
                    mPositiveClickListener);
            dialog.setNegativeButton(getActivity().getString(R.string.cancel),
                    mNegativeClickListener);
        }
        dialog.setCancelable(false);

        return dialog.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDialogMsg = null;
        mPositiveClickListener = null;
        mNegativeClickListener = null;
    }
}
