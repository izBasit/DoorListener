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

/**
 * Created by Ba on 7/3/2014.
 */
public class DoorDetailBean {

    private String doorStatus;
    private String doorTime;

    public DoorDetailBean(String doorStatus, String doorTime) {
        this.doorStatus = doorStatus;
        this.doorTime = doorTime;
    }

    public String getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(String doorStatus) {
        this.doorStatus = doorStatus;
    }

    public String getDoorTime() {
        return doorTime;
    }

    public void setDoorTime(String doorTime) {
        this.doorTime = doorTime;
    }
}
