public class Main {
    public static void main(String[] args) {       
        System.out.println("Oi!");
        
        //ClienteDAO.getInstance().create("Heitor", "Piracicaba", "14416-705", "heitor@gmail.com", "19991182254");
        //EspecieDAO.getInstance().create("Vira-Latas");
        //AnimalDAO.getInstance().create("Vida", 15, "F", 1, 1);
        
        System.out.println(ClienteDAO.getInstance().retrieveAll());
        System.out.println(EspecieDAO.getInstance().retrieveAll());
        System.out.println(AnimalDAO.getInstance().retrieveAll());
    }
}
