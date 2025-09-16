import javax.swing.ImageIcon;

public class Card {
    //card class
    
        private String cardName; 
        private ImageIcon cardImageIcon; //each image will be clickable icon

        //constructor
        Card(String cardName, ImageIcon cardImageIcon){
            this.cardName = cardName;
            this.cardImageIcon = cardImageIcon; 
        }

        //getters
        public String getName() {
            return cardName;
        }
    
        public ImageIcon getIcon() {
            return cardImageIcon;
        }

        //toString operator to print out class
        public String toString(){
            return cardName; //this will return the card object
        }

    
}
