package DB;

public class Notepad {private Integer id;
    private String brand;
    private String name;
    private  int pageAmount;
    private String cover;
    private String country;
    private String pageType;

    public Notepad(String brand, String name, int pageAmount, String cover, String country, String pageType) {
        this.id = null;
        this.brand = brand;
        this.name = name;
        this.pageAmount = pageAmount;
        this.cover = cover;
        this.country = country;
        this.pageType = pageType;
    }

    public Notepad(Integer id, String brand, String name, int pageAmount, String cover, String country, String pageType) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.pageAmount = pageAmount;
        this.cover = cover;
        this.country = country;
        this.pageType = pageType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageAmount() {
        return pageAmount;
    }

    public void setPageAmount(int pageAmount) {
        this.pageAmount = pageAmount;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    @Override
    public String toString() {
        return "Notepad{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", pageAmount=" + pageAmount +
                ", cover='" + cover + '\'' +
                ", country='" + country + '\'' +
                ", pageType='" + pageType + '\'' +
                '}';
    }

}
