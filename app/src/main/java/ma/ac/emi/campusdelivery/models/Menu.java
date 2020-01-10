package ma.ac.emi.campusdelivery.models;

public class Menu {


    String id,title,description,prix,storeId;

    public Menu() {
    }

    public Menu(String id, String title, String description, String prix, String storeId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.prix=prix;
        this.storeId=storeId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }
}
