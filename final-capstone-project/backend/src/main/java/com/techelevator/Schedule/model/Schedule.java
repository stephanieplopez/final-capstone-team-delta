package com.techelevator.Schedule.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Schedule {

	private int scheduleId;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate matchmakingDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime startTime;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime endTime;
	
	private int interviewLength;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime breakStartTime;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime breakEndTime;
	
	public int getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	public LocalDate getMatchmakingDate() {
		return matchmakingDate;
	}
	public void setMatchmakingDate(LocalDate matchmakingDate) {
		this.matchmakingDate = matchmakingDate;
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	public int getInterviewLength() {
		return interviewLength;
	}
	public void setInterviewLength(int interviewLength) {
		this.interviewLength = interviewLength;
	}
	
	public LocalTime getBreakStartTime() {
		return breakStartTime;
	}
	public void setBreakStartTime(LocalTime breakStartTime) {
		this.breakStartTime = breakStartTime;
	}
	
	public LocalTime getBreakEndTime() {
		return breakEndTime;
	}
	public void setBreakEndTime(LocalTime breakEndTime) {
		this.breakEndTime = breakEndTime;
	}

	public String incrementTime(LocalTime startTime, int lengthOfSlot) throws ParseException {
		
		String myTime = startTime.toString();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date d = df.parse(myTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, lengthOfSlot);
		String newTime = df.format(cal.getTime());
		
		return newTime;
	}
	
	public List<String> listOfTimeSlots(LocalTime startTime, LocalTime endTime, int lengthOfSlot) throws ParseException{
		
		List<String> listOfSlots = new ArrayList<>();
		listOfSlots.add(startTime.toString());
		
		String endTimeString = endTime.toString();
		String incrementedTime = null;
		
		do {
			String startTimeString = startTime.toString();
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");
			Date d = df.parse(startTimeString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.MINUTE, lengthOfSlot);
			incrementedTime = df.format(cal.getTime());
			listOfSlots.add(incrementedTime);
			startTime = LocalTime.parse(incrementedTime);
			
		} while (!incrementedTime.equals(endTimeString));
		
		return listOfSlots;
	}
	
	public List<String> formattedSlots(List<String> oldList) throws ParseException{
		
		List<String> newList = new ArrayList<>();
		
		for(int i = 0; i < oldList.size() - 1; i++) {
			StringBuilder makeSlotString = new StringBuilder();
			makeSlotString.append(oldList.get(i));
			makeSlotString.append(new String(" to "));
			makeSlotString.append(oldList.get(i+1));
			newList.add(makeSlotString.toString());
		}
		return newList;
	}
}