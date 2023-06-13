import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Form extends JFrame {
    private JPanel gameAndScore;
    private JPanel startPanel;
    private GameFieldPanel GameFieldPanel;
    public static JTextField nameField;
    public static JLabel status, steps;
    public static int points , life;
    private int choice;
    public static ArrayList<Data> stroka = new ArrayList<>();
    public Form() {
        startPanel = new JPanel(new GridLayout(3, 1));
        JLabel startLabel = new JLabel("Добро пожаловать!");
        startLabel.setFont(new Font("Arial", Font.BOLD, 22));
        startPanel.add(startLabel);

        JLabel nameLabel = new JLabel("Введите своё имя");
        startLabel.setFont(new Font("Arial", Font.BOLD, 16));
        startPanel.add(nameLabel);

        nameField = new JTextField(10);
        nameField.setToolTipText("Поле для имени");
        startPanel.add(nameField);

        while (true) {
            choice = JOptionPane.showOptionDialog(this, startPanel,
                    "Главное меню", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, new String[]{"Начать", "Выйти"}, null);
            if (nameField.getText().equals(""))
                JOptionPane.showMessageDialog(this, "Укажите своё имя", "Читать научись!", JOptionPane.PLAIN_MESSAGE);
            else break;
        }
        startGame();
    }
    public Form( int life)
    {
        this.life = life;
    }
    public void startGame() {

        points = 0;
        life = 1;

        if (choice == 0) {
            //JOptionPane.showInputDialog();

            setLayout(new GridLayout(1, 2));
            status = new JLabel("Points: " + String.valueOf(points));
            steps = new JLabel("Steps: " + String.valueOf(life));
            gameAndScore = new JPanel();
            gameAndScore.setLayout(new BorderLayout());
            gameAndScore.setOpaque(false);// Сделать фон прозрачным
            GameFieldPanel=new GameFieldPanel();
            gameAndScore.add(GameFieldPanel);
            gameAndScore.add(status, "South");
            gameAndScore.add(steps, "North");
            getContentPane().add(gameAndScore);
            getContentPane().revalidate();
            getContentPane().repaint();
        } else {
            System.exit(0);
        }
    }
    public void endGame() {

        JTable table = new JTable();
        table.setDefaultEditor(Object.class, null);
        DefaultTableModel MyModel = (DefaultTableModel) table.getModel();
        MyModel.addColumn("Имя игрока");
        MyModel.addColumn("Счёт");
        JScrollPane scroll = new JScrollPane(table);

        for (int i = 0; i < stroka.size(); i++) {
            MyModel.addRow(new Object[]{stroka.get(i).getName(), stroka.get(i).getPoints()});
        }

        JOptionPane.showMessageDialog(this, scroll, "Конец игры", JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
    }

    public static void main(String[] args) {

        Form MainFrame = new Form();
        // MainFrame.setContentPane(MainFrame.MainPanel);
        MainFrame.setTitle("Match4.2");
        MainFrame.setSize(600, 700);
        MainFrame.setResizable(false);
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainFrame.setVisible(true);
    }
}
