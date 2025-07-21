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

    int rows = 4;
    int cols = 5; 
    int cardWith = 90;
    int cardHeight = 128;


    ArrayList<Card> cardSet; //deck of cards with cardNames and image icons
    ImageIcon cardBackImageIcon; //back of card

    int boardWidth = cols *cardWith; //640px
    int boardHeight = rows * cardHeight; //360px

    JFrame frame = new JFrame("Match Cards");


    MatchCards(){
        setupCards(); 
        shuffleCards(); 


        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);//cannot change size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
}
