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

      
    MatchCards(){
        setupCards(); 
        shuffleCards(); 

    }


    private void setupCards() {
        cardSet = new ArrayList<>();
        for(String cardName: cardList){
            //load our card images
            Image cardImg = new ImageIcon(getClass().getResource("./img/" + cardName + ".jpg")).getImage();
            //scaling our image
            ImageIcon cardImageIcon = new ImageIcon(cardImg.getScaledInstance(cardWith, cardHeight, java.awt.Image.SCALE_SMOOTH)); 

            Card card = new Card(null, cardImageIcon); //card ogbject to add to set
            cardSet.add(card);//this adds the 10 cards
        }
        cardSet.addAll(cardSet); //adds 10 more cards

        Image cardBImage = new ImageIcon(getClass().getResource("./imageback.jpg")).getImage();
        cardBackImageIcon = new ImageIcon(cardBImage.getScaledInstance(cardWith, cardHeight, java.awt.Image.SCALE_SMOOTH)); 
    }
}
