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
        if (Model.Funcs_DAO.isValidTime(exitTime) && !licensePlate.equals("")) {
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

                if (rs.next()) {
                    String brand = rs.getString("brand");
                    String model = rs.getString("model");
                    String color = rs.getString("color");
                    String entryTime = rs.getString("entry_time");
                    // Verifica se horario de saida e maior que horario de entrada
                    if (Integer.parseInt(exitTime.split(":")[0]) > Integer.parseInt(entryTime.split(":")[0])) {
                        double totalHours = (Model.Funcs_DAO.transformTimeToMinutes(exitTime) - Model.Funcs_DAO.transformTimeToMinutes(entryTime)) / 60.0;
                        double totalPrice = Math.ceil(totalHours) * hourPrice;
                        View.MainMenu_GUI.entryTime_txt2.setText(String.valueOf(entryTime));
                        View.MainMenu_GUI.price_txt.setText(String.valueOf(totalPrice));
                        Model.Funcs_DAO.generateTicket(licensePlate, brand, model, color, entryTime, exitTime, totalPrice);

                        String sqlDelete = "DELETE FROM VEHICLE WHERE license_plate = ?";
                        PreparedStatement pstmtDelete = (PreparedStatement) con.prepareStatement(sqlDelete);
                        pstmtDelete.setString(1, licensePlate);
                        pstmtDelete.execute();

                        JOptionPane.showMessageDialog(null, "Ticket finalizado com sucesso!\nCarro de placa: " + licensePlate + " acaba de ser deletado do banco.", "", -1);
                        //Model.Funcs_DAO.cleanCloseTicketFields(); Decidi tirar pois se o usuario pressiona OK sem querer, nao consegue jamais ver aqueles campos se perder o txt

                    } else {
                        View.MainMenu_GUI.entryTime_txt2.setText(String.valueOf(entryTime));
                        JOptionPane.showMessageDialog(null, "Horário de saída não pode ser menor que o horário de entrada!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Placa não encontrada", "Resultado", -1);
                    View.MainMenu_GUI.licensePlate_txt2.setText("");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar com o servidor", "ERRO!", 0);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Horario ou placa invalidos!\nVerifique as informacoes!");
            View.MainMenu_GUI.exitTime_txt.setText("");
            View.MainMenu_GUI.licensePlate_txt2.setText("");
        }
    }

    public static void read(String licensePlate) {
        if (!licensePlate.equals("")) {
            try {
                // Estabelecendo a conexão
                Connection con = null;
                try {
                    con = (Connection) DriverManager.getConnection(url, username, password);
                } catch (SQLException ex) {
                    Logger.getLogger(MainMenu_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Usando PreparedStatement para evitar erros de sintaxe
                String sql = "SELECT brand, model, color, entry_time FROM VEHICLE WHERE license_plate = ?";
                PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
                pstmt.setString(1, licensePlate); // Atribui o valor da placa ao primeiro "?"

                // Executando a consulta
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Se o carro foi encontrado
                    String brand = rs.getString("brand");
                    String model = rs.getString("model");
                    String color = rs.getString("color");
                    String entryTime = rs.getString("entry_time");
                    View.MainMenu_GUI.entryTime_txt1.setText(String.valueOf(entryTime));
                    View.MainMenu_GUI.color_txt1.setText(String.valueOf(color));
                    View.MainMenu_GUI.model_txt1.setText(String.valueOf(model));
                    View.MainMenu_GUI.brand_txt1.setText(String.valueOf(brand));

                    System.out.println("Carro com a placa " + licensePlate + " foi encontrado:");
                    System.out.println("Marca: " + brand + ", Modelo: " + model + ", Horário de Entrada: " + entryTime);
                } else {
                    // Se não há resultados
                    JOptionPane.showMessageDialog(null, "Carro com a placa " + licensePlate + " não foi encontrado.");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar com o servidor", "ERRO!", 0);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Placa nao inserida!");
        }
    }

    public static void delete(String licensePlate) {
        if (!licensePlate.equals("")){    
            Connection con = null;
            try {
                con = (Connection) DriverManager.getConnection(url, username, password);  
            } catch (SQLException ex) {
                Logger.getLogger(MainMenu_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //apaga registro do veiculo
                String sqlDelete = "DELETE FROM VEHICLE WHERE license_plate = ?";
                PreparedStatement pstmtDelete = (PreparedStatement) con.prepareStatement(sqlDelete);
                pstmtDelete.setString(1, licensePlate);
                int rowsAffected = pstmtDelete.executeUpdate(); //executeUpdate para saber se houveram linhas afetadas pelo comando.
                
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Carro de placa: " + licensePlate + " deletado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Placa: " + licensePlate + " inexistente...\nVerifique as informacoes!");
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao excluir dados!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Placa nao inserida!");
        }
    }
}
