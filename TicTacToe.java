package packed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class TicTacToe implements ActionListener
{
    private JFrame frame;
    private JPanel mainPnl, topPnl, centerPnl, bottomPnl;
    private JButton[] buttons = new JButton[9];
    private boolean winnerFound = false;
    private int moveCount = 0;
    boolean xTurn = true;
    public TicTacToe() {

        JFrame frame = new JFrame();
        frame.setTitle("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPnl = new JPanel(new GridLayout(3, 3,10, 10));
        mainPnl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topPnl = new JPanel(new BorderLayout());
        JLabel titleLbl = new JLabel("Tic Tac Toe");
        topPnl.add(titleLbl, BorderLayout.NORTH);
       // mainPnl.add(titleLbl);

        for (int i = 0; i < 9; i++){
            buttons[i]=new JButton();
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 40));
            buttons[i].addActionListener(this);
            mainPnl.add(buttons[i]);
        }
        bottomPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton quitBtn = new JButton("Quit");
        bottomPnl.add(quitBtn);
        quitBtn.addActionListener(new QuitListener());
        frame.add(bottomPnl, BorderLayout.SOUTH);

        JButton againBtn = new JButton("Play Again?");
        againBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearBoardGUI();
            }
        });
        bottomPnl.add(againBtn);
        frame.add(bottomPnl, BorderLayout.SOUTH);

        frame.add(mainPnl);
        frame.setSize(400, 400);
        frame.setVisible(true);

        /* centerPnl = new JPanel();
        centerPnl.add(scroller);

        mainPnl.add(titleLbl, BorderLayout.NORTH);
        mainPnl.add(centerPnl, BorderLayout.CENTER);
        mainPnl.add(quitBtn, BorderLayout.SOUTH);


        QuitListener listener = new QuitListener();
        quitBtn.addActionListener(listener);

        frame.setTitle("Tic Tac Toe");


        topPnl = new JPanel();
        bottomPnl = new JPanel(); */
    }
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (xTurn){
            button.setText("X");
        }
        else {
            button.setText("O");
        }
        button.setEnabled(false);
        xTurn = !xTurn;

        checkForWinner();
        moveCount++;

        if (moveCount >= 9 && !winnerFound) {
            checkTie();
        }
    }
    public void checkForWinner() {
        // this for loops checks rows for a winner
        for (int i = 0; i < 9; i += 3) {
            if (buttons[i].getText().equals(buttons[i + 1].getText()) && buttons[i].getText().equals(buttons[i + 2].getText()) && !buttons[i].isEnabled()) {
                JOptionPane.showMessageDialog(frame, buttons[i].getText() + " Wins!!");
                clearBoard();
                return;
            }
        }
        // checks columns for a winner
        for (int i = 0; i < 3; i++) {
            if (buttons[i].getText().equals(buttons[i + 3].getText()) && buttons[i].getText().equals(buttons[i + 6].getText()) && !buttons[i].isEnabled()) {
                JOptionPane.showMessageDialog(frame, buttons[i].getText() + " Wins!!");
            }
        }
        if (buttons[0].getText().equals(buttons[4].getText()) && buttons[0].getText().equals(buttons[8].getText()) && !buttons[0].isEnabled()) {
            JOptionPane.showMessageDialog(frame, buttons[0].getText() + " Wins!!");
            return;
        }
        if (buttons[2].getText().equals(buttons[4].getText()) && buttons[2].getText().equals(buttons[6].getText()) && !buttons[2].isEnabled()) {
            JOptionPane.showMessageDialog(frame, buttons[2].getText() + " Wins!!");
        }
    }
        private boolean checkTie() {
        boolean boardFull = true;
            for (int i = 0; i < 3; i++) {
                for (int z = 0; z < 3; z++) {
                    if (board[i][z].equals(" ")) {
                        boardFull = false;
                        break;
                    }
                }
            }
                if (!boardFull) {
                    JOptionPane.showMessageDialog(null, "Full Board Tie!");
                }
                return  true;
        }


    private static final int ROW = 3;
    private static final int COL = 3;
    private static String[][] board = new String[ROW][COL];
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        new TicTacToe();

       boolean finished = false;
       boolean playing = true;
       Scanner in = new Scanner(System.in);
       String player = "X";
       int moveCnt = 0;
       int row = -1;
       int col = -1;
       final int MOVES_FOR_WIN = 5;
       final int MOVES_FOR_TIE = 7;
       do // program loop
       {
           //begin a game
           player = "X";
           playing = true;
           moveCnt = 0;
           clearBoard();
           do  // game loop
           {
              // get the move
              do 
              {
                
                display();  
                System.out.println("Enter move for " + player);
                row = SafeInput.getRangedInt(in,"Enter row ", 1, 3);
                col = SafeInput.getRangedInt(in,"Enter col ", 1, 3);
                row--; col--;  
              }while(!isValidMove(row, col));
              board[row][col] = player;
              moveCnt++;
              
              if(moveCnt >= MOVES_FOR_WIN)
              {
                  if(isWin(player))
                  {
                      display();
                      System.out.println("Player " + player + " wins!");
                      playing = false;
                  }
              }
              if(moveCnt >= MOVES_FOR_TIE)
              {
                  if(isTie())
                  {
                      display();
                      System.out.println("It's a Tie!");
                      playing = false;
                  }
              }
              if(player.equals("X"))
              {
                  player = "O";
              }
              else
              {
                  player = "X";
              }
              
           }while(playing);
           
           finished = SafeInput.getYNConfirm(in, "Done Playing? ");
       }while(!finished);
       
        
    }
    private void clearBoardGUI() {
        for (JButton button : buttons) {
            button.setText("");
            button.setEnabled(true);
        }
    }
    private static void clearBoard()
    {
       // sets all the board elements to a space
       for(int row=0; row < ROW; row++)
       {
           for(int col=0; col < COL; col++)
           {
               board[row][col] = " ";
           }
       }    
    }
    private static void display() 
    {
       // shows the Tic Tac Toe game 
       for(int row=0; row < ROW; row++)
       {
           System.out.print("| ");
           for(int col=0; col < COL; col++)
           {
               System.out.print(board[row][col] + " | ");
           }
           System.out.println();
       }    

    }
    private static boolean isValidMove(int row, int col)
    {
        boolean retVal = false;
       if(board[row][col].equals(" "))
           retVal = true;
       
       return retVal;
           
    }
    private static boolean isWin(String player)
    {
        if(isColWin(player) || isRowWin(player) || isDiagnalWin(player))
        {
            return true;
        }
        
        return false;
    }
    private static boolean isColWin(String player)
    {
       // checks for a col win for specified player
        for(int col=0; col < COL; col++)
        {
            if(board[0][col].equals(player) &&
               board[1][col].equals(player) &&     
               board[2][col].equals(player))
            {
                return true;
            }                
        }
        return false; // no col win
    }
    private static boolean isRowWin(String player)
    {
       // checks for a row win for the specified player
        for(int row=0; row < ROW; row++)
        {
            if(board[row][0].equals(player) &&
               board[row][1].equals(player) &&     
               board[row][2].equals(player))
            {
                return true;
            }                
        }
        return false; // no row win        
    }
    private static boolean isDiagnalWin(String player)
    {
       // checks for a diagonal win for the specified player
        
        if(board[0][0].equals(player) &&
           board[1][1].equals(player) &&    
           board[2][2].equals(player) )
        {
            return true;
        } 
        if(board[0][2].equals(player) &&
           board[1][1].equals(player) &&    
           board[2][0].equals(player) )
        {
            return true;
        }
        return false;
    }
    
    // checks for a tie before board is filled.
    // check for the win first to be efficient
    private static boolean isTie()
    {
        boolean xFlag = false;
        boolean oFlag = false;
        // Check all 8 win vectors for an X and O so 
        // no win is possible
        // Check for row ties
        for(int row=0; row < ROW; row++)
        {
            if(board[row][0].equals("X") || 
               board[row][1].equals("X") ||
               board[row][2].equals("X"))
            {
                xFlag = true; // there is an X in this row
            }
            if(board[row][0].equals("O") || 
               board[row][1].equals("O") ||
               board[row][2].equals("O"))
            {
                oFlag = true; // there is an O in this row
            }
            
            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a row win
            }
            
            xFlag = oFlag = false;
            
        }
        // Now scan the columns
        for(int col=0; col < COL; col++)
        {
            if(board[0][col].equals("X") || 
               board[1][col].equals("X") ||
               board[2][col].equals("X"))
            {
                xFlag = true; // there is an X in this col
            }
            if(board[0][col].equals("O") || 
               board[1][col].equals("O") ||
               board[2][col].equals("O"))
            {
                oFlag = true; // there is an O in this col
            }
            
            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a col win
            }
        }    
        // Now check for the diagonals
        xFlag = oFlag = false;
        
        if(board[0][0].equals("X") ||
           board[1][1].equals("X") ||    
           board[2][2].equals("X") )
        {
            xFlag = true;
        } 
        if(board[0][0].equals("O") ||
           board[1][1].equals("O") ||    
           board[2][2].equals("O") )
        {
            oFlag = true;
        } 
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }        
        xFlag = oFlag = false;        
        
        if(board[0][2].equals("X") ||
           board[1][1].equals("X") ||    
           board[2][0].equals("X") )
        {
            xFlag =  true;
        }
        if(board[0][2].equals("O") ||
           board[1][1].equals("O") ||    
           board[2][0].equals("O") )
        {
            oFlag =  true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }        

        // Checked every vector so I know I have a tie
        return true;
    }

}
