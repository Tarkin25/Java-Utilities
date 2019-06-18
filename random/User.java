package random;

public class User {

    private String name;
    private String email;
    private boolean gay;

    public User(String name, String email, boolean gay) {
        this.name = name;
        this.email = email;
        this.gay = gay;
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

    public boolean isGay() {
        return gay;
    }

    public void setGay(boolean gay) {
        this.gay = gay;
    }
}
