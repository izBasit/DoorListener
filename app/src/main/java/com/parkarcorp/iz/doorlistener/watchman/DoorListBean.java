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
public class DoorListBean {

    private String doorId, doorName, doorStatus, lastUpdate;

    public DoorListBean(String doorId, String doorName, String doorStatus, String lastUpdate) {
        this.doorId = doorId;
        this.doorName = doorName;
        this.doorStatus = doorStatus;
        this.lastUpdate = lastUpdate;
    }

    public String getDoorId() {
        return doorId;
    }

    public void setDoorId(String doorId) {
        this.doorId = doorId;
    }

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }

    public String getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(String doorStatus) {
        this.doorStatus = doorStatus;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
