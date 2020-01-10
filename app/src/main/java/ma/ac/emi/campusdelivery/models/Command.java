package ma.ac.emi.campusdelivery.models;

public class Command {
    String id,title,price,store;

    public Command(String id, String title, String price, String store) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.store = store;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }
}
