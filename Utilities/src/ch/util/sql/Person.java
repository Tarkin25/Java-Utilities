package ch.util.sql;

public class Person implements DatabaseObject {
    
    private String name;
    
    private String email;
    
    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String asDatabaseObject() {
        return "('"+name+"', '"+email+"')";
    }

}
