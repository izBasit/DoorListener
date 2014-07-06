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
package utility;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Pattern;

/**
 * Validation utility to validate Edit Texts.
 * Contains different filters for filtering data in edit texts.
 *
 * @author Basit Parkar
 */
public class ValidationUtility {

    public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$", Pattern.CASE_INSENSITIVE);

    public final static Pattern WEBSITE_PATTERN = Pattern // for testing website pattern
            .compile("[a-zA-Z0-9]{1,5}" + "(" + "." + "[-a-zA-Z0-9]{1,64}"
                    + ")" + "(" + "." + "[-a-zA-Z0-9]{1,64}" + ")");

    //for matching Decimal (9,3)
    public final static Pattern DECIMAL_NUMBER_PATTERN = Pattern
            .compile("[0-9]{0,6}" + "(" + "[.][0-9]{1,3}" + "){0,1}");

    /*
     * function to check whether an email id is valid or not
     * @param email id
     * returns true if email id is valid
     */
    public static boolean isEmailIdValid(String emailId) {
        return checkEmail(emailId);
    }

    private static boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    /*
     * function to validate website
     * @param length
     * @param Context
     * @returns true if website is valid
     */
    public static boolean isWebsiteValid(String website) {
        return checkWebsite(website);
    }

    private static boolean checkWebsite(String website) {
        return WEBSITE_PATTERN.matcher(website).matches();
    }

    /**
     * Get Alphanumeric filter
     *
     * @return filter allowing alphabets and numbers
     */
    public static InputFilter getAlphaNumericAllowedFilter() {
        InputFilter filter = new InputFilter() {
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

        return filter;
    }

    /**
     * Get alphabets allowed filter
     *
     * @return filter allowing alphabets
     */
    public static InputFilter getAlphabetsAllowedFilter() {
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };

        return filter;
    }

    /**
     * Get Numeric filter
     *
     * @return filter allowing numbers
     */
    public static InputFilter getNumericAllowedFilter() {
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };

        return filter;
    }

    /**
     * Get Alphanumeric filter with space
     *
     * @return filter allowing alphabets, numbers and Space
     */
    public static InputFilter getAlphaNumericWithSpaceAllowedFilter() {
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if ((!Character.isLetterOrDigit(source.charAt(i))) && (!Character.isSpaceChar(source.charAt(i)))) {
                        return "";
                    }
                }
                return null;
            }
        };

        return filter;
    }

    /**
     * Remark or Comment Filter
     *
     * @return
     */
    public static InputFilter getRemarkFilter() {

        final char[] acceptedChars = new char[]{',', '.'};
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if ((!Character.isLetterOrDigit(source.charAt(i))) && (!Character.isSpaceChar(source.charAt(i)))
                            && (!new String(acceptedChars).contains(String.valueOf(source.charAt(i))))) {
                        return "";
                    }
                }
                return null;
            }
        };

        return filter;
    }

    /**
     * Blood pressure Filter
     *
     * @return
     */
    public static InputFilter getBPFilter() {

        final char[] acceptedChars = new char[]{'/'};
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if ((!Character.isDigit(source.charAt(i)))
                            && (!new String(acceptedChars).contains(String.valueOf(source.charAt(i))))) {
                        return "";
                    }
                }
                return null;
            }
        };

        return filter;
    }

    /**
     * Get Alphanumeric filter with space
     *
     * @return filter allowing alphabets, numbers and Space
     */
    public static InputFilter getAlphabetsWithSpaceAllowedFilter() {
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if ((!Character.isLetter(source.charAt(i))) && (!Character.isSpaceChar(source.charAt(i)))) {
                        return "";
                    }
                }
                return null;
            }
        };

        return filter;
    }

    /**
     * Get Numeric filter with decimal
     *
     * @return filter allowing numbers
     */
    public static InputFilter getNumericWithDecimalAllowedFilter() {
        final String acceptedCharacter = ".";
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if ((!Character.isDigit(source.charAt(i))) && (!acceptedCharacter.contains(String.valueOf(source.charAt(i))))) {
                        return "";
                    }
                }
                return null;
            }
        };

        return filter;
    }

    /**
     * Get PhoneNo
     *
     * @return filter allowing numbers
     */
    public static InputFilter getPhoneNoAllowedFilter() {
        final String acceptedCharacter = "+";
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if ((!Character.isDigit(source.charAt(i))) && (!acceptedCharacter.contains(String.valueOf(source.charAt(i))))) {
                        return "";
                    }
                }
                return null;
            }
        };

        return filter;
    }

    /**
     * Name Filter
     *
     * @return
     */
    public static InputFilter getNameFilter() {

        final char[] acceptedChars = new char[]{'\'', '.'};
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if ((!Character.isLetter(source.charAt(i))) && (!Character.isSpaceChar(source.charAt(i)))
                            && (!new String(acceptedChars).contains(String.valueOf(source.charAt(i))))) {
                        return "";
                    }
                }
                return null;
            }
        };

        return filter;
    }

    /**
     * Get address filter
     *
     * @return
     */
    public static InputFilter getAddressFilter() {
        final char[] acceptedChars = new char[]{'\'', '.', '-', '\\', '/', ','};
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if ((!Character.isLetterOrDigit(source.charAt(i))) && (!Character.isSpaceChar(source.charAt(i)))
                            && (!new String(acceptedChars).contains(String.valueOf(source.charAt(i))))) {
                        return "";
                    }
                }
                return null;
            }
        };

        return filter;
    }

    /**
     * Get Length filter
     *
     * @param allowedLength
     * @return
     */
    public static InputFilter getLengthFilter(int allowedLength) {
        InputFilter filter = new InputFilter.LengthFilter(allowedLength);
        return filter;
    }

    /**
     * Get Decimal Filter
     *
     * @param digitsBeforeZero
     * @return
     */
    public static InputFilter getDecimalFilter(int digitsBeforeZero) {
        InputFilter filter = new DecimalDigitsInputFilter(digitsBeforeZero, 2);
        return filter;
    }
}

