package main;


import access.MySQLAccess;

/**
 * Created by oleh on 19.11.14.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        MySQLAccess dao = new MySQLAccess();
        dao.readDataBase();

    }
}
