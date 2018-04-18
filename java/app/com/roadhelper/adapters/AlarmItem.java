package app.com.roadhelper.adapters;

/**
 * Created by Luminance on 4/11/2018.
 */

public class AlarmItem {
   String id , title , details , time ;

    public AlarmItem(String id, String title, String details, String time) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
