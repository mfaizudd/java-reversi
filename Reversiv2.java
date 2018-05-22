import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class Reversiv2 extends JFrame {

    protected Reversiv2(){
        Reversiv2Graph content = new Reversiv2Graph();
        setContentPane(content);
        setTitle("Reversi 2.0");
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main (String args[]){
        Reversiv2 run = new Reversiv2();
        run.setVisible(true);
    }

}

class Reversiv2Graph extends JPanel implements MouseListener, MouseMotionListener {
    
    final int VALID = 3;
    final int BLACK = 2;
    final int WHITE = 1;
    final int EMPTY = 0;
    int turn = 2;
    boolean gameOver = false;

    Graphics drawGraphics;

    int[][] board = new int[][] {
        {0,0,0,0, 0,0,0,0},
        {0,0,0,0, 0,0,0,0},
        {0,0,0,0, 0,0,0,0},
        {0,0,0,1, 2,0,0,0},

        {0,0,0,2, 1,0,0,0},
        {0,0,0,0, 0,0,0,0},
        {0,0,0,0, 0,0,0,0},
        {0,0,0,0, 0,0,0,0}
    };

    Reversiv2Graph(){
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public int totalWhite = 0;
    public int totalBlack = 0;
    public int validCount = 0;
    public int winner = 0;
   

    public void paintComponent(Graphics g){
        totalWhite = 0;
        totalBlack = 0;
        
        
        Font fontBebas = new Font ("Segoe UI", 1, 25);
        Font fontBebasH2 = new Font ("Segoe UI", 1, 20);
        
        Font fontBebasSmall = new Font ("Segoe UI", 1, 15);
        
        int width = getWidth();
        int height = getHeight();
        
        for(int row = 0; row<board.length;row++) {
            for(int col=0; col<board[row].length;col++) {
                if(board[row][col]==VALID) {
                    board[row][col] = EMPTY;
                } else if(board[row][col]==WHITE) {
                    totalWhite++;
                } else if(board[row][col]==BLACK) {
                    totalBlack++;
                }
            }
        }
        for(int row = 0; row<board.length;row++) {
            for(int col=0; col<board[row].length;col++) {
                if(board[row][col] == EMPTY && checkDirection(col, row)) {
                    board[row][col] = VALID;
                }
            }
        }
        
        

        int size = height/8;
        g.setColor(java.awt.Color.decode("#0C643A"));
        g.fillRect(0,0,width,height);
        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[row].length; col++) {
                g.setColor(java.awt.Color.black);
                g.drawRect(row*size, col*size, size,size);
            }
        }
        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[row].length; col++) {
                if(board[row][col]==1) {
                    g.setColor(Color.decode("#FFFFFF"));
                    g.fillOval(col*size+5, row*size+5, size*7/8,size*7/8); 
                } else if (board[row][col]==2) {
                    g.setColor(Color.decode("#000000"));
                    g.fillOval(col*size+5, row*size+5, size*7/8,size*7/8);
                } else if(board[row][col]==VALID) {
                    g.setColor(Color.decode("#0000FF"));
                    g.fillOval(col*size+((size-(size*1/2))/2), row*size+((size-(size*1/2))/2), size*1/2,size*1/2);
                }
            }
        }
        
        g.setFont(fontBebas);
        g.setColor(Color.decode("#FFFFFF"));
        g.drawString("Reversi Project", width-203, 50);

        g.setColor(java.awt.Color.white);
        g.drawRect(width-170, 100 , size*3/2,size*3/2);
        g.setColor(Color.decode("#FFFFFF"));
        g.setFont(fontBebasH2);
        g.drawString("Turn", width-140, 90);

        if(turn==BLACK){
            g.setColor(Color.decode("#000000"));
            g.fillOval(width-163, 108 , size*4/3,size*4/3);
        }
        else{
            g.setColor(Color.decode("#FFFFFF"));
            g.fillOval(width-163, 108 , size*4/3,size*4/3);
        }

        g.setColor(Color.white);
        g.drawString("Score", width-140, 260);
     
        
        
        g.setColor(Color.decode("#000000"));
        g.fillOval(width-150, 270 , size*2/3,size*2/3);
        g.drawString(totalBlack+"", width-90, 300);
        
        g.setColor(Color.decode("#FFFFFF"));
        g.fillOval(width-150, 330 , size*2/3,size*2/3);
        g.drawString(totalWhite+"", width-90, 360);
        
        g.setFont(fontBebasSmall);
        g.setColor(java.awt.Color.white);
        g.fillRect(width-170, 450 ,size*3/2, size*1/2);
        g.setColor(java.awt.Color.decode("#1B5E20"));
        g.drawString("New Game", width-155, 475);
        
        checkWinner(totalBlack,totalWhite);
       

        
    }


    public void mousePressed(MouseEvent evt) { }
    
    public void mouseReleased(MouseEvent evt){ }
        
    public void mouseDragged(MouseEvent evt) {
         
        
    } 
    
   
    public void mouseEntered(MouseEvent evt) { 

    }   
    public void mouseExited(MouseEvent evt) { }    

    
    public void mouseClicked(MouseEvent evt) { 
        int width = getWidth();
        int height = getHeight();
        int size = height/8;

        int x = evt.getX();
        int y = evt.getY();
        if(x<height){
            int col = (int)Math.floor(x/size);
            int row = (int)Math.floor(y/size);System.out.print("clicked row: "+row+", col: "+col+"\n");
            
            makeMove(col, row);
            repaint();
           
        }
            
        else if (x>width-170 && y>450 && y<480){
                newGame();
        }

        if (winner > 0){
            winnerDialog(winner);
        }
    }   
    
    public void mouseMoved(MouseEvent evt) {
        int width = getWidth();
        int height = getHeight();
        int size = height/8;

        int x = evt.getX();
        int y = evt.getY();

        if(x>width-170 && y>450 && y<480){
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

        else{
           setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

    
       
     }   

    // START MOVEMENT HANDLER
    private boolean validDirection(int dirX, int dirY, int curX, int curY) {
        int enemy = (turn == WHITE) ? BLACK : WHITE;
        int x = curX + dirX;
        int y = curY + dirY;
        if(x < 0 || y < 0 || x > 7 || y > 7) return false;
        if(board[y][x]==enemy) {
            return true;
        } else {
            return false;
        }
    }

    private boolean makeMoveIsValid(int x, int y) {
        if(board[y][x]==EMPTY || board[y][x]==VALID) {
            if(
                validDirection(-1,-1, x, y) ||
                validDirection(-1, 0, x, y) ||
                validDirection(-1, 1, x, y) ||

                validDirection( 0,-1, x, y) ||
                validDirection( 0, 1, x, y) ||
                
                validDirection( 1,-1, x, y) ||
                validDirection( 1, 0, x, y) ||
                validDirection( 1, 1, x, y)
            ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean discFound = false;

    boolean checkDirection(int x, int y){
        if(makeMoveIsValid(x,y)) {
            if(
                directionHelper(-1,-1, x, y) ||
                directionHelper(-1, 0, x, y) ||
                directionHelper(-1, 1, x, y) ||

                directionHelper( 0,-1, x, y) ||
                directionHelper( 0, 1, x, y) ||

                directionHelper( 1,-1, x, y) ||
                directionHelper( 1, 0, x, y) ||
                directionHelper( 1, 1, x, y)
            ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    boolean directionHelper(int dirX, int dirY, int curX, int curY) {
        boolean myDiscFound = false;
        int initialX = curX;
        int initialY = curY;
        if(validDirection(dirX, dirY, curX, curY)) {
            curX += dirX;
            curY += dirY;
            while(curX >= 0 && curY >= 0 && curX <=7 && curY <=7) {
                if(board[curY][curX]==turn || myDiscFound) {
                    if(!myDiscFound) {
                        myDiscFound = true;
                    }
                    if(curX == initialX && curY == initialY) {
                        return myDiscFound;
                    } else {
                        curX -= dirX;
                        curY -= dirY;
                    }
                } else if(board[curY][curX]==EMPTY) {
                    return false;
                } else {
                    curX += dirX;
                    curY += dirY;
                }
            }
        }
        return myDiscFound;
    }

    void flipDisc(int dirX, int dirY, int curX, int curY) {
        int initialX = curX;
        int initialY = curY;
        if(validDirection(dirX, dirY, curX, curY)) {
            curX += dirX;
            curY += dirY;
            while(curX >= 0 && curY >= 0 && curX <=7 && curY <=7) {
                if(board[curY][curX]==turn) {
                    if(!discFound) {
                        discFound = true;
                    }
                    if(curX == initialX && curY == initialY) {
                        return;
                    } else {
                        curX -= dirX;
                        curY -= dirY;
                        board[curY][curX] = turn;
                    }
                } else if(board[curY][curX]==EMPTY) {
                    return;
                } else {
                    curX += dirX;
                    curY += dirY;
                }
            }
        }
    }

    void makeMove(int x, int y) {
        if(makeMoveIsValid(x, y)) {
            flipDisc(-1,-1, x, y);  // TOP LEFT
            flipDisc(-1, 0, x, y);  // LEFT
            flipDisc(-1, 1, x, y);  // BOTTOM LEFT

            flipDisc( 0,-1, x, y);  // TOP
            flipDisc( 0, 1, x, y);  // BOTTOM
            
            flipDisc( 1,-1, x, y);  // TOP RIGHT
            flipDisc( 1, 0, x, y);  // RIGHT
            flipDisc( 1, 1, x, y);  // BOTTOM RIGHT

            if(discFound) {
                discFound = false;
                changeTurn();
            }

        }
    }

    void changeTurn() {
        turn = (turn == WHITE) ? BLACK : WHITE;
    }
    // END MOVEMENT HANDLER

    protected void newGame(){
        board = new int[][] {
            {0,0,0,0, 0,0,0,0},
            {0,0,0,0, 0,0,0,0},
            {0,0,0,0, 0,0,0,0},
            {0,0,0,1, 2,0,0,0},
    
            {0,0,0,2, 1,0,0,0},
            {0,0,0,0, 0,0,0,0},
            {0,0,0,0, 0,0,0,0},
            {0,0,0,0, 0,0,0,0}
        };
        winner = EMPTY;
        repaint();
        
      
    }

    void checkWinner(int totalBlack, int totalWhite){
        validCount = 0;
        for(int row = 0; row<board.length;row++) {
            for(int col=0; col<board[row].length;col++) {
                if(board[row][col] == VALID) {
                    validCount++;
                }
            }
        }
        
        System.out.print("validmove : " + validCount);
        
        if( validCount <= 0){
            if(totalWhite<totalBlack) {
                    winner = BLACK;
                } else if(totalWhite==totalBlack) {
                    winner = 4;
                } else {
                    winner = WHITE;
                }
        }
    }
    void winnerDialog(int winner){
        String win ="";
        switch(winner){
            case BLACK:
                win = "BLACK";
                break;
            case WHITE:
                win = "WHITE";
                break;
            case 4:
                win = "DRAW";
                break;
        }

        JOptionPane.showMessageDialog(this,"Congratulation " + win + " win the game! !\n Let's play new game !");
        
        
    }
    
    

}