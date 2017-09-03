package s3585826.assignment1.Model;

/**
 * Person class
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public abstract class Person {
    private String id;
    private String name;
    private String email;
    private String birthday;
    private Location location;
    //private photo;

    public Person(String id, String name, String email, String birthday) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.location = null;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    public String getLocationString() {
        if (location==null)
            return "Unknown";
        else
            return location.toString();
    }
}
