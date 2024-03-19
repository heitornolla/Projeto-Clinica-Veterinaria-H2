package model;

public class Main {
    public static void main(String[] args) {               
        
        
        System.out.println(ClienteDAO.getInstance().retrieveAll());
        System.out.println(EspecieDAO.getInstance().retrieveAll());
        System.out.println(AnimalDAO.getInstance().retrieveAll());
        System.out.println(VeterinarioDAO.getInstance().retrieveAll());
    }
}

