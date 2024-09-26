package Controller;

public class Connect_DB {

    public static void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("Driver carregado com sucesso!");
        } catch (Exception e) {
            System.out.println("Driver nao pode ser carregado!");
        }
    }
}
