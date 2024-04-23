import javax.swing.*;
import java.awt.event.*;

public class Section2 extends JFrame {
    private JPanel section2;
    private JLabel opcoes;

    public Section2() {
        setContentPane(section2);
        setSize(1990, 1070);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Criar um JPopupMenu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem1 = new JMenuItem("Cadastro");
        JMenuItem menuItem2 = new JMenuItem("Login");

        // Adicionar os itens de menu ao JPopupMenu
        popupMenu.add(menuItem1);
        popupMenu.add(menuItem2);

        // ActionListener para menuItem1 (Cadastro)
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Main(); // Abrir tela de cadastro (Main)
            }
        });

        // ActionListener para menuItem2 (Login)
        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login().setVisible(true); // Abrir tela de login (Login)
            }
        });

        // Adicionar um ouvinte de mouse à JLabel "opcoes" para exibir o menu pop-up quando o botão direito do mouse for clicado
        opcoes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenu.show(opcoes, e.getX(), e.getY());
                }
            }
        });
    }

    public static void main(String[] args) {
        new Section2();
    }
}
