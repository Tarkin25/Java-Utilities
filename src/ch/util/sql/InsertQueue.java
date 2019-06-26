package ch.util.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class InsertQueue<T extends DatabaseObject> {

    private List<T> values = new ArrayList<>();

    private StringBuilder sql = new StringBuilder();
    
    private Class<T> c;
    
    private String table;

    public InsertQueue(Class<T> c, String table) {
        this.c = c;
        this.table = table;
        
        initSql();
    }

    public void addValue(T value) {
        values.add(value);
    }

    public void clear() {
        values.clear();
        initSql();
    }

    private void updateSql() {
        if(values.size() >= 1) {
            for(T value : values) {
                sql.append(value.asDatabaseObject() + ",");
            }
            
            sql = new StringBuilder(sql.substring(0, sql.length()-1));
        } else {
            sql = new StringBuilder("");
        }
    }

    private void initSql() {
        String fieldStr = "";

        Field[] fields = c.getDeclaredFields();

        if (fields.length >= 1) {
            fieldStr += fields[0].getName();

            for (int i = 1; i < fields.length; i++) {
                fieldStr += ", " + fields[i].getName();
            }
        }

        sql.append("insert into " + table + "(" + fieldStr + ") values ");
    }

    public String getSql() {
        updateSql();

        return sql.toString();
    }
    
    public static void main(String[] args) {
        InsertQueue<Person> iq = new InsertQueue<Person>(Person.class, "person");
        
        iq.addValue(new Person("Max Muster", "max.muster@example.com"));
        iq.addValue(new Person("Max Muster", "max.muster@example.com"));
        
        System.out.println(iq.getSql());
    }

}
