import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main extends JFrame {
    private JPanel telaDeLogin;
    private JLabel titulo;
    private JTextField nome;
    private JPasswordField password;
    private JButton cancelar;
    private JLabel usuario;
    private JLabel senha;
    private JButton enviar;

    public Main() {
        // Configurações da Janela!
        setContentPane(telaDeLogin);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Ir para outra Janela!
        enviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nomeUsuario = nome.getText();
                    String senhaUsuario = new String(password.getPassword());

                    // Verifica se os campos não estão vazios
                    if (nomeUsuario.isEmpty() || senhaUsuario.isEmpty()) {
                        throw new Exception("Por favor, preencha todos os campos.");
                    }

                    // Aqui você pode adicionar a lógica para enviar os dados para o banco de dados
                    sendDataToDatabase(nomeUsuario, senhaUsuario);

                    // Após enviar os dados para o banco, abra a janela de login
                    new Login().setVisible(true);
                    dispose(); // Fechar a janela atual
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Main.this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    // Method to send data to the database
    private void sendDataToDatabase(String username, String password) {
        // Coloque aqui a lógica para enviar os dados para o banco de dados
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5434/Usuarios", "postgres", "admin");

            String query = "INSERT INTO users (nome, senha) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Enviado com sucesso!");

            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao enviar os dados.");
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
