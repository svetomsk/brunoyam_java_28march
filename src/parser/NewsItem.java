package parser;

import java.time.LocalDateTime;

public class NewsItem {
    private String title;
    private String link;
    private String description;
    private LocalDateTime time;

    public NewsItem() {

    }

    public NewsItem(String title, String link, String description, LocalDateTime time) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "News Item: title = " + title + ", link " + link
                + ", description" + description + "time" + time;
    }
}
