package Model;

import View.MainMenu_GUI;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Funcs_DAO {

    public static void changeScreen(JFrame currentScreen, JFrame nextScreen) {
        currentScreen.dispose();
        nextScreen.setVisible(true);
    }

    public static void exit() {
        String response = JOptionPane.showInputDialog(null, "Certeza que deseja sair?\n1 - Sim\n2 - Cancelar");
        try {
            if (Integer.parseInt(response) == 1) {
                System.exit(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao tentar encerrar o programa.\n(talvez opcao invalida?)");
        }
    }

    public static boolean isValidTime(String time) {
        try {
            LocalTime.parse(time);
            return true;
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Insira um horario valido! (hh:mm:ss)");
            return false;
        }
    }

    public static void cleanCreateFields() {
        View.MainMenu_GUI.licensePlate_txt.setText("");
        View.MainMenu_GUI.brand_txt.setText("");
        View.MainMenu_GUI.model_txt.setText("");
        View.MainMenu_GUI.color_txt.setText("");
        View.MainMenu_GUI.entryTime_txt.setText("");
    }

    public static void cleanReadUpdateDeleteFields() {
        View.MainMenu_GUI.licensePlate_txt1.setText("");
        View.MainMenu_GUI.brand_txt1.setText("");
        View.MainMenu_GUI.model_txt1.setText("");
        View.MainMenu_GUI.color_txt1.setText("");
        View.MainMenu_GUI.entryTime_txt1.setText("");
    }

    public static void cleanCloseTicketFields() {
        View.MainMenu_GUI.licensePlate_txt2.setText("");
        View.MainMenu_GUI.entryTime_txt2.setText("");
        View.MainMenu_GUI.exitTime_txt.setText("");
        View.MainMenu_GUI.price_txt.setText("");
    }

    public static int transformTimeToMinutes(String time) {
        if (isValidTime(time)) {
            String[] parts = time.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            return hours * 60 + minutes;
        }
        return -1; // Retorna -1 para indicar que o tempo é inválido
    }

    public static void generateTicket(String licensePlate, String brand, String model, String color, String entryTime, String exitTime, double price) {

        String filePath = "TICKET/ticket.txt";

        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Cria diretórios se não existirem

            FileWriter writer = new FileWriter(file);
            writer.write("Placa: " + licensePlate + "\n");
            writer.write("Marca: " + brand + "\n");
            writer.write("Modelo: " + model + "\n");
            writer.write("Cor: " + color + "\n");
            writer.write("Horário de entrada: " + entryTime + "\n");
            writer.write("Horário de saída: " + exitTime + "\n");
            writer.write("Preço final: R$ " + price + "\n");
            writer.close();

            Runtime.getRuntime().exec("cmd.exe /c start notepad.exe " + file.getAbsolutePath());

        } catch (IOException ex) {
            Logger.getLogger(MainMenu_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
