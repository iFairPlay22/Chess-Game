package model.util;

import java.io.Serializable;

public class Time implements Serializable {
	private static final long serialVersionUID = 1L;
	private long start;
	private long beginTime;
	
	public Time() {
		restart();
	}
	
	public Time(long beginTime) {
		this();
		this.beginTime = beginTime;
	}
	
	public long getPastTime() {
		return System.currentTimeMillis() - start + beginTime;
	}
	
	public long toSeconds() {
		return getPastTime() / 1000;
	}
	
	public void restart() {
		initialize();
		beginTime = 0;
	}
	
	public void save() {
		beginTime = getPastTime();
	}
	
	public void initialize() {
		start = System.currentTimeMillis();
	}
}