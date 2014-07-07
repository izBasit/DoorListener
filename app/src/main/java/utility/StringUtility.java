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

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class StringUtility {

    /*
     * This method returns splited string array
     * @param the string which is to be split
     * @param the character for split
     * @returns splited string
     */
    public static String[] split(String original, char splitchar) {
        Vector nodes = new Vector();

        int index = original.indexOf(splitchar); // Parse nodes into vector

        while (index >= 0) {
            nodes.addElement(original.substring(0, index));
            original = original.substring(index + 1);
            index = original.indexOf(splitchar);
        }
        // Get the last node
        nodes.addElement(original);

        String[] split_string_array = new String[nodes.size()];
        if (nodes.size() > 0) {
            for (int loop = 0; loop < nodes.size(); loop++)
                split_string_array[loop] = (String) nodes.elementAt(loop);
        }

        return split_string_array;
    }


    /*
     * This will return a string by padding zero
     * @param String value
     * @param padding length
     * @returns String by padding zeros
     */
    public static String padZero(String val, int padLength) {

        String temp = "";
        int inLen = val.length();
        for (int i = 0; i < padLength - inLen; i++) {
            temp = temp + "0";
        }
        return (temp + val);

    }

    /*
     * This method returns splited string array
     * @param the string which is to be split
     * @param the string for split
     * @returns splited string
     */
    public static String[] split(String original, String splitStr) {
        Vector<String> nodes = new Vector<String>();

        int index = original.indexOf(splitStr); // Parse nodes into vector
        while (index >= 0) {
            nodes.addElement(original.substring(0, index));
            original = original.substring(index + 1);
            index = original.indexOf(splitStr);
        }
        // Get the last node
        nodes.addElement(original);

        String[] split_string_array = new String[nodes.size()];
        if (nodes.size() > 0) {
            for (int loop = 0; loop < nodes.size(); loop++)
                split_string_array[loop] = (String) nodes.elementAt(loop);
        }

        return split_string_array;
    }

    /*
     * This method returns splited vector
     * @param the string which is to be split
     * @param the character for split
     * @returns splited vector
     */
    public static Vector<String> splitInVec(String original, char splitchar) {
        Vector<String> nodes = new Vector<String>();

        int index = original.indexOf(splitchar); // Parse nodes into vector
        while (index >= 0) {
            nodes.addElement(original.substring(0, index));
            original = original.substring(index + 1);
            index = original.indexOf(splitchar);
        }
        // Get the last node
        nodes.addElement(original);

		/*Vector<String> split_string_array = new Vector<String>();
        if (nodes.size() > 0) {
			for (int loop = 0; loop < nodes.size(); loop++)
				split_string_array.add((String) nodes.elementAt(loop).trim());
		}*/

        return sort(nodes);
    }

    /*
     * This will sort the string
     * @param vector of String
     * @returns Sorted vector of string
     */
    public static Vector<String> sort(Vector<String> sort) {
        Vector<String> v = new Vector<String>();
        for (int count = 0; count < sort.size(); count++) {
            String s = sort.elementAt(count).toString();
            int i = 0;
            for (i = 0; i < v.size(); i++) {
                int c = s.compareTo((String) v.elementAt(i));
                if (c < 0) {
                    v.insertElementAt(s, i);
                    break;
                } else if (c == 0) {
                    break;
                }
            }
            if (i >= v.size()) {
                v.addElement(s);
            }
        }
        return v;
    }

    /*
     * Convert String into hex
     * @param String value
     * @returns hex string
     */
    public static String stringToHex(String arg) {

        byte[] theByteArray = arg.getBytes();
        String hexstr = byteArrayToHexString(arg.getBytes());
        return hexstr;

    }

    /*
     * Convert byte array to hex string
     * @param byte array
     * @returns hex string from byte array
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }

        return sb.toString().toUpperCase();
    }

    /*
     *
     * @param string message which need to be pad
     * @param padded by String
     * @param maximum length of String
     * @returns padded String by padding string if it cross maximum limit
     *
     */
    public static String padding(String msg, String pad, int maxLen) {
        int msg_len = msg.trim().length();
        String ret = "";
        String padstr = "";

        for (int i = 0; i < maxLen - msg_len; i++) {
            padstr = padstr + pad;
        }
        ret = padstr.trim() + msg.trim();
        return ret;

    }

    /*
     * Encrypt the string
     * @param String want to encrypt
     * @returns encrypted string
     */
    public static String encrypt(String password) {

        String new_pass = "";
        new_pass = stringToHex(password);

        String encryptedPass = "";
        // get ascii code of each character and assign it
        for (int i = 0; i < new_pass.length(); i++) {
            char reply;
            if (i != new_pass.length()) {
                reply = new_pass.charAt(i);
                int charVal = reply;
                float charFloat = charVal;
                charFloat = charFloat * 10;
                charFloat = charFloat / 4;
                charFloat = charFloat - 50;
                charVal = (int) charFloat;
                char charVal1 = (char) charVal;
                encryptedPass = encryptedPass + charVal1;
            }
        }
        return encryptedPass;
    }

    /*
     * This will gives image base64 String
     * @param bitmap
     * @returns string which contain image of base64 String
     */
    public static String getImageStringFromBitmap(Bitmap bitmap) {
        String imgBse64String = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] image = baos.toByteArray();
            imgBse64String = Base64.encodeToString(image, Base64.DEFAULT);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
        }
        return imgBse64String;
    }

    /*
     * This method return bitmap from image path
     * @param String of image path
     * @param Context
     * @returns bitmap of given image path
     */
    public static Bitmap getBitMapFromPath(String imgPath, Context mContext) {
        Bitmap bitmap = null;
        Log.i("MakeMachine", "onPhotoTaken");
        ExifInterface exif;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, options);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int desiredWidth = displaymetrics.widthPixels + 40;
        int srcWidth = options.outWidth;

        int srcHeight = options.outHeight;

        // Only scale if the source is big enough. This code is just trying to
        // fit a image into a certain width.
        if (desiredWidth > srcWidth)
            desiredWidth = srcWidth;

        // Calculate the correct inSampleSize/scale value. This helps reduce
        // memory use. It should be a power of 2
        // from:
        // http://stackoverflow.com/questions/477572/android-strange-out-of-memory-issue/823966#823966
        int inSampleSize = 1;
        while (srcWidth / 2 > desiredWidth) {
            srcWidth /= 4;
            srcHeight /= 2;
            inSampleSize *= 2;
        }

        float desiredScale = (float) desiredWidth / srcWidth;

        // Decode with inSampleSize
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inSampleSize = 8;
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;


        bitmap = BitmapFactory.decodeFile(imgPath);

        try {
            exif = new ExifInterface(imgPath);

            int exifOrientation = Integer.parseInt(exif
                    .getAttribute(ExifInterface.TAG_ORIENTATION));
            float rotate = 0;
            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }

            if (rotate > 0) {
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                // File file = new File(_path);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);
                OutputStream outStream;
                try {
                    outStream = new FileOutputStream(imgPath);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outStream);
                }
                catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Log.i("MakeMachine", "onPhotoTaken");
        return bitmap;
    }

    public static boolean IsAlphaNumeric(String strToTest) {
        boolean status = false;
        String regExForAlphaNemeric = "^[a-zA-Z0-9_]*$";

        if (strToTest.matches(regExForAlphaNemeric))
            status = true;

        return status;
    }

    /*
     * This method filter text and disable special character only allow letter and digits
     * @param integer length to enter text
     * @returns InputFilter array
     */
    public InputFilter[] DisableSpecialCharacters(int length) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        InputFilter[] FilterArraym = new InputFilter[2];
        FilterArraym[0] = filter;

        FilterArraym[1] = new InputFilter.LengthFilter(length);
        return FilterArraym;
    }

    /*
     * This will check for String contain character and digits
     * @param input String
     * @returns true if string contains both character and digit else false
     */
    public boolean CheckCharactersAndDigit(String source) {
        boolean isOK = false;
        boolean is_char = false, is_digit = false;
        int start = 0, end = source.length();
        for (int i = start; i < end; i++) {
            if (!Character.isLetter(source.charAt(i))) {
                is_char = true;
            } else if (!Character.isDigit(source.charAt(i))) {
                is_digit = true;
            }
        }
        if (is_char && is_digit)
            isOK = true;
        return isOK;
    }

    /*
     * function to check whether edit text contains special character
     * @param length in integer for string limit
     * @param Context
     * @returns InputFilter array
     */
    public InputFilter[] checkSpecialchars(int length, final Context _context) {
        InputFilter filterm = new InputFilter() {

            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned arg3, int arg4, int arg5) {
                // TODO Auto-generated method stub

                for (int i = start; i < end; i++) {

                    if (source.charAt(i) == '#' || source.charAt(i) == '|'
                            || source.charAt(i) == '^'
                            || source.charAt(i) == '>'
                            || source.charAt(i) == '!'
                            || source.charAt(i) == '('
                            || source.charAt(i) == ')'
                            || source.charAt(i) == '{'
                            || source.charAt(i) == '}'
                            || source.charAt(i) == '~'
                            || source.charAt(i) == '+'
                            || source.charAt(i) == '='
                            || source.charAt(i) == '?'
                            || source.charAt(i) == '['
                            || source.charAt(i) == ']'
                            || source.charAt(i) == '<'
                            || source.charAt(i) == '&'
                            || source.charAt(i) == '$'
                            || source.charAt(i) == ';'
                            || source.charAt(i) == '�'
                            || source.charAt(i) == '�'
                            || source.charAt(i) == '�'
                            || source.charAt(i) == '�'
                            || source.charAt(i) == '�'
                            || source.charAt(i) == '�'
                            || source.charAt(i) == '�'
                            || source.charAt(i) == '�'
                            || source.charAt(i) == '�'
                            || source.charAt(i) == '�'
                            || source.charAt(i) == '�'
                            || source.charAt(i) == '`'
                            || source.charAt(i) == '?'
                            || source.charAt(i) == '�'
                            || source.charAt(i) == '*'
                            /*|| source.charAt(i) == '.'*/
                            || source.charAt(i) == '@'
                            || source.charAt(i) == '%'
                            || source.charAt(i) == '"'
                            || source.charAt(i) == ':'
                            || source.charAt(i) == '_'

                            ) {

						/*showAlert("Special Character !,#,$,&,<,>,(,),{,},~,^,|, +,=,?,[,],',;,?,�,�,�,�,�,�,�,�,�,�,�,�,`... is not allowed.");*/
                        return "";
                    }
                }

                return null;

            }
        };
        InputFilter[] FilterArraym = new InputFilter[2];
        FilterArraym[0] = filterm;
        FilterArraym[1] = new InputFilter.LengthFilter(length);

        return FilterArraym;
    }

    /*
     * This will return double value with two round decimals
     * @param double value
     * @returns double formated value with two decimal round
     */
    public double roundTwoDecimals(double d) {

        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

    /*
     * This will return double value with three round decimals
     * @param double value
     * @returns double formated value with three decimal round
     */
    public double roundThreeDecimals(double d) {

        DecimalFormat twoDForm = new DecimalFormat("#.###");
        return Double.valueOf(twoDForm.format(d));
    }

    /*
     * Taken String Double value and convert it to rounded two decimal value
     * @param String value
     * @returns String by adding decimals point
     */
    public String convertToDecimal(String Qty) {
        double qty;

        qty = Double.parseDouble(Qty);
        Qty = String.valueOf(qty);
        if (Qty.contains(".")) {

            qty = roundTwoDecimals(qty);
            Qty = String.valueOf(qty);

            if (Qty.substring(Qty.indexOf('.') + 1, Qty.length()).equalsIgnoreCase("0") || Qty.substring(Qty.indexOf('.') + 1, Qty.length()).length() == 1) {
                Qty = String.valueOf(qty) + "0";
            }
            System.out.println("Double Qty--->>" + Qty);
        } else
            Qty = String.valueOf(qty);
        return Qty;

    }

    /* This method is useful to convert web response into map
     * @param response get from web service
     * @returns Map of response String - value pair
     */
    public Map<String, String> getRecordFromString(String response) {
        int length = response.length();
        Map<String, String> myMap1 = new HashMap<String, String>();
        String mainString = response;
        Vector aor = new Vector();

        while (mainString.indexOf('=') != -1) {
            String[][] rec = new String[1][2];
            rec[0][0] = mainString.substring(0, mainString.indexOf('='));
            // &amp;

            if (mainString.indexOf(';') != -1) {
                rec[0][1] = mainString.substring(mainString.indexOf('=') + 1,
                        mainString.indexOf(';'));
                mainString = mainString.substring(mainString.indexOf(';') + 1)
                        .trim();
            } else {
                rec[0][1] = mainString.substring(mainString.indexOf('=') + 1);
                mainString = "";
            }
            aor.addElement(rec);

        }
        String[][] records = new String[aor.size()][2];
        for (int i = 0; i < aor.size(); i++) {
            String[][] recTemp = new String[1][2];
            recTemp = (String[][]) aor.elementAt(i);
            myMap1.put(recTemp[0][0], recTemp[0][1]);

        }
        return myMap1;
    }
}
