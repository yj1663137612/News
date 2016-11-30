package edu.zhuoxin.entity;

/**
 * Created by ACER on 2016/11/21.
 */

public class LoginCenterInfo {
    String time;
    String address;
    String device;

    public LoginCenterInfo() {

    }

    public LoginCenterInfo(String time, String address, String device) {
        this.time = time;
        this.address = address;
        this.device = device;

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }


    @Override
    public String toString() {
        return "LoginCenterInfo{" +
                "time='" + time + '\'' +
                ", address='" + address + '\'' +
                ", device='" + device + '\'' +
                '}';
    }
}
