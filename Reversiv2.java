import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Reversiv2 extends JFrame {

    private Reversiv2(){
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

class Reversiv2Graph extends JPanel implements MouseListener {
    
    final int BLACK = 2;
    final int WHITE = 1;
    final int EMPTY = 0;
    int turn = 2;

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
        setBackground(Color.GREEN);
        addMouseListener(this);
    }

    public void paintComponent(Graphics g){
        int width = getWidth();
        int height = getHeight();

        int size = height/8;
        g.setColor(java.awt.Color.decode("#1B5E20"));
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
                }
            }
        }
    }


    public void mousePressed(MouseEvent evt) { }
    
    public void mouseReleased(MouseEvent evt){ }
        
    public void mouseDragged(MouseEvent evt) { } 
    
    public void mouseEntered(MouseEvent evt) { }   
    public void mouseExited(MouseEvent evt) { }    

    public void mouseClicked(MouseEvent evt) { 
        int width = getWidth();
        int height = getHeight();
        int size = height/8;

        int x = evt.getX();
        int y = evt.getY();

        int col = (int)Math.floor(x/size);
        int row = (int)Math.floor(y/size);
        System.out.print("clicked row: "+row+", col: "+col+"\n");
        
        // if(validMove(turn, row, col)){
        //     board[row][col] = turn;
        //     this.turn = (turn==BLACK)?WHITE:BLACK;
        // }
        // repaint();
        // this.turn = (turn==BLACK)?WHITE:BLACK;
        makeMove(col, row);
    }   
    public void mouseMoved(MouseEvent evt) { }   

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
        if(board[y][x]==EMPTY || board[y][x]==turn) {
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

    void flipDisc(int dirX, int dirY, int curX, int curY) {
        if(makeMoveIsValid(curX, curY)) {
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
    }

    void makeMove(int x, int y) {
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
        repaint();
    }

    void changeTurn() {
        turn = (turn == WHITE) ? BLACK : WHITE;
    }
    // END MOVEMENT HANDLER

    protected boolean validMove(int turn, int row, int col){
        if(board[row][col] != EMPTY) return false;

        boolean checkVerti = false;
        boolean checkHoriz = false;
        boolean checkRowDiagonal = false;
        boolean checkColDiagonal = false;

        int lastValidPiece;

        //check row
        //horizontal
        if(row >= 1 && row < 7){
            //horizontal
            if(board[row-1][col] != EMPTY) {
                for(int i=0; i<row; i++){
                    if(board[i][col] == turn){
                        checkVerti = true;
                        System.out.println("ada " +turn+ "di "+ i);
                    }
                }
                        
            }
            
            
            if(board[row+1][col] != EMPTY) {
                for(int i=0; row+i<=7; i++){
                    if(board[row+i][col] == turn){
                        checkVerti = true;
                        System.out.println("ada " +turn+ "di "+ (row+i));
                    }
                }
            }

        }
        
        if(row == 0 ){
            if(board[row+1][col] != EMPTY) checkVerti = true;
            //diagonal 
            if(col == 0) {
             if(board[row+1][col+1] != EMPTY) checkVerti = true;
            }
            if(col == 7) {
                if(board[row+1][col-1] != EMPTY) checkVerti = true;
               }
        }
        if(row == 7) {
            if(board[row-1][col] != EMPTY) checkVerti = true;

            if(col == 7){
                if(board[row-1][col-1] != EMPTY) checkRowDiagonal = true;
            }
            if(col == 0){
                if(board[row-1][col+1] != EMPTY) checkRowDiagonal = true;
            }
        }

        //check col
        //veritcal
        if(col >= 1 && col < 7){
            if(board[row][col+1] != EMPTY)  {
                for(int i=0; col+i<=7; i++){
                    if(board[row][col+i] == turn){
                        checkHoriz = true;
                        System.out.println("ada " +turn+ "di col"+ (col+i));
                    }
                }
            }
            if(board[row][col-1] != EMPTY) {
                for(int i=0; i<row; i++){
                    if(board[row][i] == turn){
                        checkHoriz = true;
                        System.out.println("ada " +turn+ "di col"+ i);
                    }
                };
            }

           
        }
        if(col == 0){
            if(board[row][col+1] != EMPTY) checkVerti = true; 

            if(row == 0){
                if(board[row+1][col+1] != EMPTY) checkRowDiagonal = true;
            }
        }
        if(col == 7){
            if(board[row][col-1] != EMPTY) checkVerti = true;

            if(row == 7){
                if(board[row-1][col-1] != EMPTY) checkRowDiagonal = true;
            }
        }

        //diagonal
        if(col > 0 && col <7){
            
            //check coloum diagonal
            if (row > 0 && row <7){
                
                if(board[row-1][col-1] != EMPTY) checkColDiagonal = true;
                if(board[row-1][col+1] != EMPTY) checkColDiagonal = true;
                if(board[row+1][col+1] != EMPTY) checkColDiagonal = true;
                if(board[row+1][col-1] != EMPTY) checkColDiagonal = true;
            }
            if (row == 0){
                if(board[row+1][col+1] != EMPTY) checkRowDiagonal = true;
                if(board[row+1][col-1] != EMPTY) checkRowDiagonal = true;
            }
            if (row == 7){
                if(board[row-1][col+1] != EMPTY) checkRowDiagonal = true;
                if(board[row-1][col-1] != EMPTY) checkRowDiagonal = true;
            }

        }

        if(row > 0 && row <7){
            if (col > 0 && col < 7){
                if(board[row-1][col-1] != EMPTY) checkRowDiagonal = true;
                if(board[row-1][col+1] != EMPTY) checkRowDiagonal = true;
                if(board[row+1][col+1] != EMPTY) checkRowDiagonal = true;
                if(board[row+1][col-1] != EMPTY) checkRowDiagonal = true;
            }
            if (col == 0){
                if(board[row+1][col+1] != EMPTY) checkRowDiagonal = true;
                if(board[row-1][col+1] != EMPTY) checkRowDiagonal = true;
            }
            if (col == 7){
                if(board[row+1][col-1] != EMPTY) checkRowDiagonal = true;
                if(board[row-1][col-1] != EMPTY) checkRowDiagonal = true;
            }
        }


      
        boolean checked = checkHoriz || checkVerti || checkRowDiagonal || checkColDiagonal;
        // System.out.println("row = " + checkHoriz);
        // System.out.println("check = " + checkVerti);
        // System.out.println("rowD = " + checkRowDiagonal);
        // System.out.println("colD = " + checkColDiagonal);

        return checked;
    
    }

}