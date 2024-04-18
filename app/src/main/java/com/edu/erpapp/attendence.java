package com.edu.erpapp;

public class attendence {
    String AttendanceDate;
    int StudentId, StatusId, EntryBy;

    public attendence() {
    }

    public String getAttendanceDate() {
        return AttendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        AttendanceDate = attendanceDate;
    }

    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int studentId) {
        StudentId = studentId;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }

    public int getEntryBy() {
        return EntryBy;
    }

    public void setEntryBy(int entryBy) {
        EntryBy = entryBy;
    }
}
