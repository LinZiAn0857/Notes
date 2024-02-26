package com.example.notes;

public class DataClass {
    private String note_id;
    private String note_title;
    private String note_content;

//    public DataClass(String note_title, String note_content) {
//        this.note_title = note_title;
//        this.note_content = note_content;
//    }

    public DataClass(String note_id, String note_title, String note_content) {
        this.note_id = note_id;
        this.note_title = note_title;
        this.note_content = note_content;
    }

    public String getNote_title() {
        return note_title;
    }

    public String getNote_content() {
        return note_content;
    }

    public String getNote_id() {
        return note_id;
    }
}
