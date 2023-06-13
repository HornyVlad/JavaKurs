import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

public class GameFieldPanel extends JPanel implements ActionListener {
    protected int rowNum;
    protected int columnNum;
    public static int[][] board;
    static final int EMPTY = 0;
    static final int MAX = 4;
    protected JButton[][] balls;
    public static int selectedRow = -1;
    public static int selectedCol = -1;
    public static boolean pieceSelected = false;
    private ImageLoader imageLoader;
    GameFieldPanel(){

        setLayout(new GridLayout(7, 9));
        setPreferredSize(new Dimension(580, 470));
        setOpaque(false);// Сделать фон прозрачным
        reset();
    }

    public void reset() {
        rowNum = 7;
        columnNum = 9;
        board = new int[rowNum][columnNum];
        // Заполняем таблицу случайными шарами
        for(int row = 0; row < rowNum; row++)
            for(int col = 0; col < columnNum; col++)
            {
                board[row][col] = EMPTY;
            }

        start();
    }
    public void start(){

        while(existsEmptyCell())
        {
            dropPieces();
            eliminateMatches();
        }
        balls = new JButton[rowNum][columnNum];
        imageLoader = new ImageLoader();
        for(int i=0;i<rowNum;i++) {
            for(int j=0;j<columnNum;j++) {

                balls[i][j] = new JButton();
                balls[i][j].setIcon(new ImageIcon(imageLoader.getImage(board[i][j])));
                balls[i][j].addActionListener(this);
                balls[i][j].setBorder(BorderFactory.createEmptyBorder());
                balls[i][j].setContentAreaFilled(false);
                add(balls[i][j]);
            }
        }
    }


    public boolean existsEmptyCell() {
        boolean empty_cell_found = false;

        for(int i=0;i < rowNum; i++) {
            for(int j=0;j < columnNum; j++) {
                if(board[i][j] == EMPTY) {
                    empty_cell_found = true;
                }
            }
        }

        return empty_cell_found;
    }

    public void dropPieces() {
        for(int j=0; j<columnNum; j++) {
            int[] thiscol = new int[rowNum];
            for(int i=0;i<rowNum;i++) {
                thiscol[i] = EMPTY;
            }

            int target_index = rowNum - 1;
            for(int i=rowNum-1; i>=0; i--) {
                if(board[i][j] != EMPTY) {
                    thiscol[target_index] = board[i][j];
                    target_index--;
                }
            }

            while(target_index >= 0) {
                thiscol[target_index] = new Random().nextInt(MAX)+1;
                target_index--;
            }

            for(int i=0;i<rowNum;i++) {
                board[i][j] = thiscol[i];
            }
        }
    }

    public void eliminateMatches() {
        for(int i=0;i<rowNum;i++) {
            for(int j=0;j<columnNum;j++) {
                // 5 in a row
                if(0<j && j<columnNum-3) {
                    if(board[i][j-1] == board[i][j] &&
                            board[i][j+1] == board[i][j] &&
                            board[i][j+2] == board[i][j] &&
                            board[i][j+3] == board[i][j]) {
                        board[i][j] = EMPTY;
                        board[i][j-1] = EMPTY;
                        board[i][j+1] = EMPTY;
                        board[i][j+2] = EMPTY;
                        board[i][j+3] = EMPTY;
                        Form.points+=100;
                    }
                }

                // 5 in a col
                if(0<i && i<rowNum-3) {
                    if(board[i-1][j] == board[i][j] &&
                            board[i+1][j] == board[i][j] &&
                            board[i+2][j] == board[i][j] &&
                            board[i+3][j] == board[i][j]) {
                        board[i][j] = EMPTY;
                        board[i-1][j] = EMPTY;
                        board[i+1][j] = EMPTY;
                        board[i+2][j] = EMPTY;
                        board[i+3][j] = EMPTY;
                        Form.points += 100;
                    }
                }
                // 4 in a row
                if(0<j && j<columnNum-2) {
                    if(board[i][j-1] == board[i][j] &&
                            board[i][j+1] == board[i][j] &&
                            board[i][j+2] == board[i][j]) {
                        board[i][j] = EMPTY;
                        board[i][j-1] = EMPTY;
                        board[i][j+1] = EMPTY;
                        board[i][j+2] = EMPTY;
                        Form.points += 50;
                    }
                }

                // 4 in a col
                if(0<i && i<rowNum-2) {
                    if(board[i-1][j] == board[i][j] &&
                            board[i+1][j] == board[i][j] &&
                            board[i+2][j] == board[i][j]) {
                        board[i][j] = EMPTY;
                        board[i-1][j] = EMPTY;
                        board[i+1][j] = EMPTY;
                        board[i+2][j] = EMPTY;
                        Form.points += 50;
                    }
                }

                // 3 in a row
                if(0<j && j<columnNum-2) {
                    if(board[i][j-1] == board[i][j] &&
                            board[i][j+1] == board[i][j]) {
                        board[i][j] = EMPTY;
                        board[i][j-1] = EMPTY;
                        board[i][j+1] = EMPTY;
                        Form.points += 25;
                    }
                }

                // 3 in a col
                if(0<i && i<rowNum-2) {
                    if(board[i-1][j] == board[i][j] &&
                            board[i+1][j] == board[i][j]) {
                        board[i][j] = EMPTY;
                        board[i-1][j] = EMPTY;
                        board[i+1][j] = EMPTY;
                        Form.points += 25;
                    }
                }
            }
        }
    }


    public boolean isValidSwap(int row1,int col1,int row2,int col2) {
        if(row1==row2 && Math.abs(col1-col2)==1) {
            // ok
        } else if (col1==col2 && Math.abs(row1-row2)==1) {
            // ok
        } else {
            return false;
        }

        int tmp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = tmp;
        eliminateMatches();
        if(existsEmptyCell()) {
            do {
                dropPieces();
                eliminateMatches();
            } while(existsEmptyCell());
            return true;
        } else {
            tmp = board[row1][col1];
            board[row1][col1] = board[row2][col2];
            board[row2][col2] = tmp;
            return false;
        }
    }

    public void handleClick(int row,int col) {
        if(!pieceSelected) {
            pieceSelected = true;
            selectedRow = row;
            selectedCol = col;
            balls[row][col].setIcon(new ImageIcon(imageLoader.getImage(board[row][col] + 4)));
            return;
        }

        if(pieceSelected && selectedRow==row && selectedCol==col) {
            pieceSelected = false;
            selectedRow = -1;
            selectedCol = -1;
            balls[row][col].setIcon(new ImageIcon(imageLoader.getImage(board[row][col])));
            return;
        }

        if(!(isValidSwap(row,col,selectedRow,selectedCol))) {
            Form.status.setText("Invalid move");
            return;
        }
        pieceSelected = false;
        selectedRow = -1;
        selectedCol = -1;
        redrawBoard();
        Form.life--;
        Form.status.setText("Points: " + String.valueOf(Form.points));
        Form.steps.setText("Steps: " + String.valueOf(Form.life));
        if(Form.life == 0) {
            Form.stroka.add(new Data(Form.nameField.getText(), Form.points));
            JOptionPane.showMessageDialog(this, "Ваш счёт: " + Form.points, "Результат", JOptionPane.PLAIN_MESSAGE);
            connectToServer();
            Form a = new Form( 3);
            a.endGame();
        }

    }

    public void redrawBoard() {
        for(int i=0; i<rowNum; i++) {
            for(int j=0; j<columnNum; j++) {
                balls[i][j].setIcon(new ImageIcon(imageLoader.getImage(board[i][j])));
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        //setStatus("Points: " + String.valueOf(points));
        JButton button = (JButton) e.getSource();

        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < columnNum; j++) {
                if (button == balls[i][j]) {
                    handleClick(i, j);
                    break;
                }
            }
        }
    }

    public void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to server.");

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(Form.stroka);

            Form.stroka = (ArrayList<Data>) in.readObject();


            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Не удалось синхронизироваться с сервером", "Конец игры", JOptionPane.PLAIN_MESSAGE);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
