import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
class Reversi extends JFrame {
    static final long serialVersionUID = 0;

    final int BLACK = 2;
    final int WHITE = 1;
    final int SPACE = 0;
    int turn = 2;

    Canvas canvas = new Canvas() {

        public void paint(Graphics g) {
            g.setColor(java.awt.Color.decode("#1B5E20"));
            g.fillRect(0,0,800,800);
            for(int row = 0; row < board.length; row++) {
                for(int col = 0; col < board[row].length; col++) {
                    g.setColor(java.awt.Color.black);
                    g.drawRect(row*100, col*100, 100,100);
                    
                }
            }
            for(int row = 0; row < board.length; row++) {
                for(int col = 0; col < board[row].length; col++) {
                    if(board[row][col]==1) {
                        g.setColor(Color.decode("#FFFFFF"));
                        g.fillOval(col*100+10, row*100+10, 80,80); 
                    } else if (board[row][col]==2) {
                        g.setColor(Color.decode("#000000"));
                        g.fillOval(col*100+10, row*100+10, 80,80);
                    }
                }
            }
        }
    };
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


    public Graphics graphicsForDrawing;
    Reversi() {
        
        add(canvas);
        setSize(1200,840);
        setTitle("Reversi");
        // showValidMove(turn);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        canvas.addMouseListener(new MouseListener(){
        
            @Override
            public void mouseReleased(MouseEvent e) {
                
            }
        
            @Override
            public void mousePressed(MouseEvent e) {
                
            }
        
            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        
            @Override
            public void mouseEntered(MouseEvent e) {
                int row = (int)Math.floor((e.getY())/100);
                int col = (int)Math.floor((e.getX())/100);
                if(validMove(turn, row, col)){
                    System.out.println("entered : " + col + row);
                }
            }
        
            @Override
            public void mouseClicked(MouseEvent e) {
                int offset = canvas.getParent().getSize().height-canvas.getSize().height;
                int row = (int)Math.floor((e.getY())/100);
                int col = (int)Math.floor((e.getX())/100);
                System.out.print("clicked row: "+row+", col: "+col+"\n");
                takeTurn(turn, row, col);
            }
        });
    }

    // void showValidMove(int turn){
    //     for( int row = 0; row<board.length;row++){
    //         for(int col = 0; col<board[row].length;col++)
    //             if (validMove(turn, row, col)){
    //                 System.out.println("hai = " + validMove(turn, row, col));
                    
    //                 }
    //     }
    // }

    void takeTurn(int turn, int row, int col) {
        if(row>=0 && row<board.length && col >= 0 && col < board[row].length) {
            if(validMove(turn, row, col)) {
                board[row][col] = turn;
            }
        }
        this.turn = (turn==BLACK)?WHITE:BLACK;
        canvas.repaint();
    }

    boolean validMove(int turn, int row, int col) {

        if(board[row][col]!=SPACE) return false;

        if(board[row-1][col-1]!=SPACE) return true;
        if(board[row-1][ col ]!=SPACE) return true;
        if(board[row-1][col+1]!=SPACE) return true;
        
        if(board[ row ][col-1]!=SPACE) return true;
        if(board[ row ][col+1]!=SPACE) return true;

        if(board[row+1][col-1]!=SPACE) return true;
        if(board[row+1][ col ]!=SPACE) return true;
        if(board[row+1][col+1]!=SPACE) return true;

        return false;
    }

    public void paintValidMove(int row, int col){
        Graphics g = getGraphics();
        g.setColor(java.awt.Color.white);
        g.drawRect(row*100, col*100, 100,100);
        canvas.repaint();
    }

    void showWindow() {
        setVisible(true);
    }

    public static void main(String[] args) {
        Reversi reversi = new Reversi();
        reversi.showWindow();
    }
}