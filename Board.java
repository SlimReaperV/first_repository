
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;             /*each import is shown */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{
    private final int B_WIDTH = 300; //defining the constants used in our game 
    private final int B_HEIGHT = 300; //B_WIDTH and B_HEIGHT --> size of the board 
    private final int DOT_SIZE = 10; // DOT_SIZE --> size of the apple and the dot of the snake
    private final int ALL_DOTS = 900;  // all_dots defines the maximum number of dots on the board (900)                            
    private final int RAND_POS = 29; //random position of the apple 
    private final int DELAY = 140; //speed of the game 

    private final int x[] = new int [ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];  //the two arrays store the coordinates of all joints of the snake 

    private int dots;
    private int apple_x;
    private int apple_y;
    

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;
    
    public Board(){
        initBoard();
    
    }
    private void initBoard(){
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH,B_HEIGHT));
        loadImages();
        initGame();


    }

    private void loadImages(){
        ImageIcon lid = new ImageIcon("src/resources/dot.png");
        ball = lid.getImage();
        ImageIcon lia = new ImageIcon("src/resources/apple.png"); //we get the images of the game 
        apple = lia.getImage();
        ImageIcon lih = new ImageIcon("src/resource/head.png");
        head = lih.getImage();

    }
    public void initGame(){
        dots = 3;
        for(int z = 0; z<3; z++){
            x[z] = 50 - z*10;       //we create the snake , locate the apple on the board. and start the timer
            y[z] = 50;

        }
        locateApple();
        timer = new Timer(DELAY,this);
        timer.start();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);

       }
    public void doDrawing(Graphics g){
        if (inGame){
            g.drawImage(apple, apple_x, apple_y,this);

            for(int z = 0; z < dots; z++){
                if(z==0){
                    g.drawImage(head, x[z], y[z], this);
                }
                    else{
                        g.drawImage(ball, x [z], y[z], this);
                    }
                }
                Toolkit.getDefaultToolkit().sync();
                
            } 
            else {
                gameOver(g);
            }

            

                    }
    public void gameOver(Graphics g){
        String msg = "Game over";
        Font small =  new Font("Helvetica", Font.BOLD, 24);
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT/2);
        

    }
    private void checkApple(){
        if ((x[0] == apple_x) && (y[0] ==  apple_y)){
            dots++; //if the coordinates of the snake align with those of the snake , the snake's size increased and the apple relocates 
            locateApple();

        }
    }

    private void move(){
        for(int z = dots; z >0; z--){
            x[z] = x[(z-1)];           // this is the key algorithm to the game (we change the direction with the cursor keys )
            y[z] = y[(z-1)];  //the rest of the joints move one position up the chain. The second joint moves where the first was etc.
        }                      
        if(leftDirection){
            x[0] = x[0] - DOT_SIZE; //moving the head to the left 

        }
        if (rightDirection){
            x[0] = x[0] + DOT_SIZE;


        }
        if(upDirection){
            y[0] = y[0] - DOT_SIZE;

        }
        if (downDirection){
            y[0] = y[0] +DOT_SIZE;
        }


        }
        private void checkCollision(){
            for(int z = dots; z>0; z--){
                if((z>4) && (x[0] == x[z]) && (y[0] == y[z])){
                    inGame = false;  // this method checks if the snake has hit itself or the walls(if it has the game is over)

                }
            }
            if (y[0] >=  B_HEIGHT){
                inGame = false;

            }
        
        if (y[0] >= B_HEIGHT){
            inGame = false;

        }
        if(y[0] <0){
            inGame = false;

        }
        if (x[0] >= B_WIDTH){
            inGame = false;

        }
        if (x[0] < 0){
            inGame = false;
        }
        if(!inGame){
            timer.stop();

        }
    }
    private void locateApple(){
        int r  =  (int) (Math.random() *RAND_POS);
        apple_x = ((r * DOT_SIZE));
        r = (int) (Math.random() *RAND_POS);
        apple_y = ((r * DOT_SIZE));

    }
    @Override
    public void actionPerformed (ActionEvent e){
        if (inGame){
            checkApple();
            checkCollision();
            move();

        }
        repaint();

    }

    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if((key == KeyEvent.VK_LEFT) && (!rightDirection)){
                leftDirection = true; 
                upDirection = false;
                downDirection = false;

            }
                if((key == KeyEvent.VK_LEFT) && (!leftDirection)){
                rightDirection = true; 
                upDirection = false;
                downDirection = false;

            }
                if((key == KeyEvent.VK_LEFT) && (!upDirection)){
                leftDirection = false; 
                rightDirection = false;
                downDirection = true;

            }
                if((key == KeyEvent.VK_LEFT) && (!downDirection)){
                leftDirection = true; 
                upDirection = true;
                rightDirection = false;

            }

        }
    }



    }
      