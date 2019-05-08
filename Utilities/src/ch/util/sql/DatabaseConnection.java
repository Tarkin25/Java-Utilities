package ch.util.sql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.mariadb.jdbc.MariaDbDataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DatabaseConnection {
    
    private Connection conn;
    
    public DatabaseConnection(String hostname, int port, String user, String password, String dbname) throws SQLException {
        try {
            MariaDbDataSource ds = new MariaDbDataSource();
            ds.setServerName(hostname);
            ds.setPort(port);
            ds.setUserName(user);
            ds.setPassword(password);
            ds.setDatabaseName(dbname);
            
            conn = ds.getConnection();
            
        } catch(SQLException e) {
            try {
                MysqlDataSource ds = new MysqlDataSource();
                ds.setServerName(hostname);
                ds.setPort(port);
                ds.setUser(user);
                ds.setPassword(password);
                ds.setDatabaseName(dbname);
                
                conn = ds.getConnection();
                
            } catch(SQLException e1) {
                throw e1;
            }
        }
    }
    
    public static void main(String[] args) {
        
        try {
            new DatabaseConnection("localhost", 3306, "root", "bla", "mysql");
        } catch (SQLException e) {
            System.out.println(e);
        }
        
    }
    
    public boolean executeCommand(String sql) throws SQLException {
            if(sql.length() >= 1) {
                Statement stmt = conn.createStatement();
                
                return !stmt.execute(sql);
            } else {
                return true;
            }
    }
    
    public <T extends DatabaseObject> boolean executeCommand(InsertQueue<T> bufferedInsert) throws SQLException {
        String sql = bufferedInsert.getSql();
        
        return executeCommand(sql);
    }
    
    public ResultSet executeQuery(String sql) {
        try {
             Statement stmt = conn.createStatement();
            
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean executeScript(String path) throws IOException, SQLException {
            BufferedReader br = new BufferedReader(new FileReader(path));

            String line;

            StringBuilder content = new StringBuilder();

            while((line = br.readLine()) != null) {
                content.append(line);
            }

            br.close();
            
            beginTransaction();

            for(String command : content.toString().split(";")) {
                if(!executeCommand(command)) {
                    rollback();
                    return false;
                }
            }
            
            commit();

        return true;
    }
    
    public PreparedStatement prepareStatement(String sql) throws SQLException {
            return conn.prepareStatement(sql);
    }
    
    public boolean beginTransaction() throws SQLException {
            conn.setAutoCommit(false);
            
            return true;
    }
    
    public boolean commit() throws SQLException {
            conn.commit();
            conn.setAutoCommit(true);
            
            return true;
    }
    
    public boolean rollback() throws SQLException {
            conn.rollback();
            
            return true;
    }
    
}
