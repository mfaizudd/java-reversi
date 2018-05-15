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
        
        if(validMove(turn, row, col)){
            board[row][col] = turn;
        }
        repaint();
        this.turn = (turn==BLACK)?WHITE:BLACK;
        
    }   
    public void mouseMoved(MouseEvent evt) { }   
    
    protected boolean validMove(int turn, int row, int col){
        if(board[row][col] != EMPTY)
        return false;

        return true;
    
    }

}