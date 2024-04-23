import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {
    private JPanel login;
    private JLabel titulo;
    private JTextField nome;
    private JPasswordField password;
    private JButton cancelar;
    private JLabel usuario;
    private JLabel senha;
    private JButton enviar;

    public Login() {
        // Configurações da Janela!
        setContentPane(login);
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

                    // Verifica o login
                    if (verificarLogin(nomeUsuario, senhaUsuario)) {
                        // Se o login for válido, abra a próxima janela
                        new Section2().setVisible(true);
                        dispose(); // Fechar a janela atual
                    } else {
                        // Caso contrário, exiba uma mensagem de erro
                        throw new Exception("Campos inválidos, tente novamente.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Login.this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fechar a janela atual
            }
        });
    }

    // Método para verificar o login
    private boolean verificarLogin(String nome, String senha) {
        String url = "jdbc:postgresql://localhost:5434/Usuarios";
        String usuarioBD = "postgres";
        String senhaBD = "admin";

        try (Connection conn = DriverManager.getConnection(url, usuarioBD, senhaBD)) {
            String sql = "SELECT * FROM users WHERE nome = ? AND senha = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nome);
                stmt.setString(2, senha);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next(); // Se encontrar um resultado, o login é válido
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
