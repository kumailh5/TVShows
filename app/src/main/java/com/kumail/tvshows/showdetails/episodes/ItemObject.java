package com.kumail.tvshows.showdetails.episodes;

public class ItemObject {
    private String contents;
    private int type;
    public ItemObject(String contents, int type) {
        this.contents = contents;
        this.type = type;
    }
    public String getContents() {
        return contents;
    }
    public int getType(){
        return type;
    }
}
