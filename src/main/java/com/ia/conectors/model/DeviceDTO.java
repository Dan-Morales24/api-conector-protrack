package com.ia.conectors.model;

public class DeviceDTO {

    private String imei;
    private String devicename;
    private String devicetype;
    private String platenumber;
    private long onlinetime;
    private long platformduetime;
    
    
	public String getImei() {
		return imei;
	}
	
	public void setImei(String imei) {
		this.imei = imei;
	}
	
	public String getDevicename() {
		return devicename;
	}
	
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	
	public String getDevicetype() {
		return devicetype;
	}
	
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	public String getPlatenumber() {
		return platenumber;
	}
	
	public void setPlatenumber(String platenumber) {
		this.platenumber = platenumber;
	}
	
	public long getOnlinetime() {
		return onlinetime;
	}
	
	public void setOnlinetime(long onlinetime) {
		this.onlinetime = onlinetime;
	}
	
	public long getPlatformduetime() {
		return platformduetime;
	}
	
	public void setPlatformduetime(long platformduetime) {
		this.platformduetime = platformduetime;
	}
    
    
	
}
