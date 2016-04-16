package razibkani.recyclerviewsample.model.pojo;

/**
 * Created by razibkani on 4/16/16.
 */
public class News {

    public int id;
    public String urlImage;
    public String title;
    public int type;

    public News() {}

    public News(int id, String urlImage, String title, int type) {
        this.id = id;
        this.urlImage = urlImage;
        this.title = title;
        this.type = type;
    }
}
