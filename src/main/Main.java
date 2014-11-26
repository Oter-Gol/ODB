package main;


import access.Connector;
import access.MySQLAccess;
import managers.CategoryDAO;
import tableClasses.Category;

/**
 * Created by oleh on 19.11.14.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        Connector connector = new Connector();
        CategoryDAO categoryDAO = new CategoryDAO(connector.getConnection());

        System.out.println( "Lol");


    }
}
