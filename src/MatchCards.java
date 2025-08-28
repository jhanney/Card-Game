import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; //to store the cards
import javax.swing.*; 

public class MatchCards {

    //card class
    class Card{
        String cardName; 
        ImageIcon cardImageIcon; //each image will be clickable icon

        //constructor
        Card(String cardName, ImageIcon cardImageIcon){
            this.cardName = cardName;
            this.cardImageIcon = cardImageIcon; 
        }

        //toString operator to print out class
        public String toString(){
            return cardName; //this will return the card object
        }

    }

    String[] cardList = { //array of the card names
        "darkness",
        "double",
        "fairy",
        "fighting",
        "fire",
        "grass",
        "lightning",
        "metal",
        "psychic",
        "water"
    };

    //difficulties
    String[] difficulties = {
        "Easy",
        "Medium",
        "Hard"
    };

    int rows = 4;
    int cols = 5; 
    int cardWith = 90;
    int cardHeight = 128;


    ArrayList<Card> cardSet; //deck of cards with cardNames and image icons
    ImageIcon cardBackImageIcon; //back of card

    int boardWidth = cols *cardWith; //640px
    int boardHeight = rows * cardHeight; //360px

    JFrame frame = new JFrame("Match Cards");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel(); 
    JPanel boardPanel = new JPanel(); //add the board
     
    JPanel restartGamePanel = new JPanel(); 
    JButton restartGame = new JButton(); //restart button

    JPanel startGamePanel = new JPanel(); // start game button 
    JButton startGame = new JButton(); 

    int delay = 1500; 

    int errorCount = 0; 
    int pairComplete = 0; 
    ArrayList<JButton> board; //arraylist to hold buttons
    Timer hideCards; //timer for showing cards
    boolean gameReady = false; 
    JButton card1;
    JButton card2;


    MatchCards(){
        setupCards(); 
        shuffleCards(); 

        /*Make a start game button
         * only allowed to start after difficulty is selected
         * also try to fix the placement of select difficulty, maybe make a new jPanel
         */



        //frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);//cannot change size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Errors: " + Integer.toString(errorCount));

        textPanel.setPreferredSize(new Dimension(boardWidth, 30));
        textPanel.add(textLabel, BorderLayout.NORTH); 
         
        JPanel gameDifficulty = new JPanel();
        JComboBox<String> difficultyBox = new JComboBox<>(difficulties); 
        difficultyBox.setSelectedItem("Medium");
        difficultyBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String selected = (String) difficultyBox.getSelectedItem(); 
                if(selected.equals("Easy")){
                    delay = 5000; 
                }else if(selected.equals("Medium")){
                    delay = 1500; 
                }else if(selected.equals("Hard")){
                    delay = 1250; 
                }
            }
        });

        gameDifficulty.add(new JLabel("Difficulty")); 
        gameDifficulty.add(difficultyBox); 
        textPanel.add(gameDifficulty, BorderLayout.NORTH); 


        frame.add(textPanel, BorderLayout.NORTH);

        //game board 
        board = new ArrayList<JButton>();
        boardPanel.setLayout(new GridLayout(rows, cols));
        for(int i = 0; i < cardSet.size(); i++){
            JButton tile = new JButton();
            tile.setPreferredSize(new Dimension(cardWith, cardHeight));//each button is card witdh and height
            tile.setOpaque(true);
            tile.setIcon(cardSet.get(i).cardImageIcon);
            tile.setFocusable(false);
            tile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    if(!gameReady){
                        return;
                    }
                    JButton	tile = (JButton) e.getSource(); //returns the button we click
                    if(tile.getIcon() == cardBackImageIcon){
                        if(card1 == null){
                            card1 = tile; 
                            int index = board.indexOf(card1);//assign index to that of selected card
                            card1.setIcon(cardSet.get(index).cardImageIcon);//set the image to the card
                        }else if(card2 == null){
                            card2 = tile;
                            int index = board.indexOf(card2);
                            card2.setIcon(cardSet.get(index).cardImageIcon);

                            if(card1.getIcon() != card2.getIcon()){
                                errorCount ++;
                                textLabel.setText("Errors: " + Integer.toString(errorCount));
                                hideCards.stop();
                                hideCards.setInitialDelay(1500); //use medium delay 
                                hideCards.start (); //flip cards back 
                            }
                            else{
                                card1 = null; //leaves both faced up as we dont chage images
                                card2 = null; 
                                pairComplete ++; 
                                int totalPairs = board.size() / 2; 
                                if(pairComplete == totalPairs){
                                    textLabel.setText("You have completed the game!!");
                                }
                            }                           
                        }
                    }
                }
            });
            board.add(tile); //add to array list
            boardPanel.add(tile); //adds button to panel 
        }
        frame.add(boardPanel); 

        //restart button
        restartGame.setFont(new Font("Arial", Font.PLAIN, 16)); 
        restartGame.setText("Restart Game");
        restartGame.setPreferredSize(new Dimension(boardWidth/2, 30));
        restartGame.setFocusable(false);
        restartGame.setEnabled(false);
        restartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(!gameReady){
                    return; 
                }
                gameReady = false; 
                restartGame.setEnabled(false);
                card1 = null;
                card2 = null;
                shuffleCards();

                //re assigns all the buttons with new cards
                for(int i = 0; i < cardSet.size(); i++){
                    board.get(i).setIcon(cardSet.get(i).cardImageIcon);
                }

                errorCount = 0;
                textLabel.setText("Errors: " + Integer.toString(errorCount));
                //gameReady = false;                 // lock input during preview
                hideCards.stop();
                hideCards.setInitialDelay(delay);
                hideCards.start(); 
            }
        });


        startGame.setFont(new Font("Arial", Font.PLAIN, 16)); 
        startGame.setText("Start Game");
        startGame.setPreferredSize(new Dimension(boardWidth/2, 30));
        startGame.setFocusable(false);
        startGame.setEnabled(false);

        restartGamePanel.add(startGame); 
        restartGamePanel.add(restartGame);
        frame.add(restartGamePanel, BorderLayout.SOUTH); //add to frame and place under board

        frame.pack();//recalculates width and height after components added
        frame.setVisible(true);

        //start game 


        //start game
        hideCards = new Timer(0, e -> hideCards());
        hideCards.setRepeats(false); //only calls hide cards once
        //gameReady = false;                 // lock input during preview
        hideCards.stop();
        hideCards.setInitialDelay(delay);  // use difficulty
        hideCards.start();
        
    }


    private void setupCards() {
        cardSet = new ArrayList<>();
        for(String cardName: cardList){
            //load our card images
            Image cardImg = new ImageIcon(getClass().getResource("/img/" + cardName + ".jpg")).getImage();
            //scaling our image
            ImageIcon cardImageIcon = new ImageIcon(cardImg.getScaledInstance(cardWith, cardHeight, java.awt.Image.SCALE_SMOOTH)); 

            Card card = new Card(cardName, cardImageIcon); //card ogbject to add to set
            cardSet.add(card);//this adds the 10 cards
        }
        cardSet.addAll(cardSet); //adds 10 more cards

        Image cardBImage = new ImageIcon(getClass().getResource("/img/back.jpg")).getImage();
        cardBackImageIcon = new ImageIcon(cardBImage.getScaledInstance(cardWith, cardHeight, java.awt.Image.SCALE_SMOOTH)); 
    }


    private void shuffleCards(){
        System.out.println(cardSet);

        //shuffle the cards
        for(int i = 0; i < cardSet.size(); i++){
            int j = (int) (Math.random() * cardSet.size()); //get random index of a card

            //swap the cards
            Card temp = cardSet.get(i); //assign the temp i
            cardSet.set(i, cardSet.get(j)); //set i as the card at j (random index)
            cardSet.set(j, temp); //assign the card at j the temp (originally i)
        }
        System.out.println(cardSet);
    }

    private void hideCards() {
        //check if game is ready and 2 cards selected
        if(gameReady && card1 != null && card2 != null){
            card1.setIcon(cardBackImageIcon);//set image to back of card
            card1 = null; //assign back to null
            card2.setIcon(cardBackImageIcon);
            card2 = null; 
        }
        else {
            //loop through all buttons and change image to back 
            for(int i = 0; i < cardSet.size(); i++){
                board.get(i).setIcon(cardBackImageIcon);
            }
            gameReady = true; 
            restartGame.setEnabled(true);
        }
    }
}
