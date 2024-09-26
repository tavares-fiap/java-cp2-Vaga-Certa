package Model;

import View.MainMenu_GUI;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class CRUD_DAO {

    private static String url = "jdbc:mysql://localhost/PARKINGLOT";
    private static String username = "root";
    private static String password = "";
    private static int hourPrice = 15;

    public static void create(String licensePlate, String brand, String model, String color, String entryTime) {

        Controller.Connect_DB.loadDriver();

        if (Model.Funcs_DAO.isValidTime(entryTime)) {
            try {
                Connection con = null;
                try {
                    con = (Connection) DriverManager.getConnection(url, username, password);
                } catch (SQLException ex) {
                    Logger.getLogger(MainMenu_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

                String sql = "INSERT INTO VEHICLE (license_plate, brand, model, color, entry_time) VALUES ('" + licensePlate + "','" + brand + "','" + model + "','" + color + "','" + entryTime + "')";

                try {
                    PreparedStatement insert = (PreparedStatement) con.prepareStatement(sql);
                    insert.execute(); // Executando a inserção 
                    JOptionPane.showMessageDialog(null, "\nInserção realizada com sucesso!\n", "", -1);
                    Model.Funcs_DAO.cleanCreateFields();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "\nErro na inserção!", "ERRO!", 0);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "\nOcorreu algum erro!", "ERRO", 0);
            }
        } else {
            View.MainMenu_GUI.entryTime_txt.setText("");
        }
    }

    public static void closeTicket(String licensePlate, String exitTime) {
        if (Model.Funcs_DAO.isValidTime(exitTime)) {
            try {
                Connection con = null;
                try {
                    con = (Connection) DriverManager.getConnection(url, username, password);
                } catch (SQLException ex) {
                    Logger.getLogger(MainMenu_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

                String sqlQuery = "SELECT brand, model, color, entry_time FROM VEHICLE WHERE license_plate = ?";
                PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sqlQuery);
                pstmt.setString(1, licensePlate);

                // Executando a consulta
                ResultSet rs = pstmt.executeQuery();
                int i = 0;

                if (rs.next()) {
                    String brand = rs.getString("brand");
                    String model = rs.getString("model");
                    String color = rs.getString("color");
                    String entryTime = rs.getString("entry_time");
                    i++;
                    // Verifica se horario de saida e maior que horario de entrada
                    if (Integer.parseInt(exitTime.split(":")[0]) > Integer.parseInt(entryTime.split(":")[0])) {
                        double totalPrice = ((Model.Funcs_DAO.transformTimeToMinutes(exitTime) - Model.Funcs_DAO.transformTimeToMinutes(entryTime)) / 60) * hourPrice;
                        View.MainMenu_GUI.entryTime_txt2.setText(String.valueOf(entryTime));
                        View.MainMenu_GUI.price_txt.setText(String.valueOf(totalPrice));
                        Model.Funcs_DAO.generateTicket(licensePlate, brand, model, color, entryTime, exitTime, totalPrice);

                        //apaga registro do veiculo
                        String sqlDelete = "DELETE FROM VEHICLE WHERE license_plate = ?";
                        PreparedStatement pstmtDelete = (PreparedStatement) con.prepareStatement(sqlDelete);
                        pstmtDelete.setString(1, licensePlate);
                        pstmtDelete.execute();

                        JOptionPane.showMessageDialog(null, "Exclusão realizada com sucesso!", "", -1);
                        //Model.Funcs_DAO.cleanCloseTicketFields(); Decidi tirar pois se o usuario pressiona OK sem querer, nao consegue jamais ver aqueles campos se perder o txt

                    } else {
                        View.MainMenu_GUI.entryTime_txt2.setText(String.valueOf(entryTime));
                        JOptionPane.showMessageDialog(null, "Horário de saída não pode ser menor que o horário de entrada!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Placa não encontrada", "Resultado", -1);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar com o servidor", "ERRO!", 0);
            }
        } else {
            View.MainMenu_GUI.exitTime_txt.setText("");
        }
    }
}
