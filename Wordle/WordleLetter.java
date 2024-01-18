public class WordleLetter {
    private char letter;
    private String color;

    // Constructor to initialize a WordleLetter with a given character.
    public WordleLetter(char letterIn){
        this.letter = letterIn;
    }

    // Method to set the color of the WordleLetter.
    public void setColor(String colorIn){
        this.color=colorIn;
    }

    // Method to check if the color of the WordleLetter is set.
    public boolean isColorSet(){
        if(this.color==null){
            return false;
        }
        return true;
    }

    // Method to check if the color of the WordleLetter is green.
    public boolean isGreen(){
        if(this.color==null){
            return false;
        }
        if(this.color.equals("green")){
            return true;
        }
        return false;
    }

    // Method to generate a string representation of the WordleLetter.
    public String toString() {
        // Determine the special characters to add
        // at the beginning of the String
        // to change the text color to the right color.
        String colorCode;
        if (color != null) {
            if (color.equals("green")) {
                colorCode = "\u001B[32m";
            } else if (color.equals("yellow")) {
                colorCode = "\u001B[33m";
            } else {
                colorCode = "\u001B[31m";
            }
            // These are the special characters to add
            // to the end of the String
            // to signify the end of the color change.
            String resetCode = "\u001B[0m";

            // Surround the letter with
            // space characters and with
            // the above color changing special characters.
            return String.format(
                    "%s %s %s",
                    colorCode, letter, resetCode);
        } else {
            // If color is not set, return the letter without color.
            return String.valueOf(letter);
        }
    }
}
