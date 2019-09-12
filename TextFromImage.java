/*

Program where user enters a filepath to a steganographic image produced by my TextInImage.java program, in order to derive the encoded text.
Filepath to steganographic image = args[0]

Omer Baddour, 6/8/19

3301

 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextFromImage {

    public static void main(String[] args){

        BufferedImage image = null;
        // dimensions of image
        int width;
        int height;

        try{
            // read steganographic image
            // filepath for input
            image = ImageIO.read(new File(args[0]));

            System.out.println("Image read successfully.");
            width = image.getWidth();
            height = image.getHeight();

            // recover encoded text.length()
            String binaryTextLengthStr = "";
            int j = 0;
            for(; j < 14; j++){
                binaryTextLengthStr += (image.getRGB(j,0) & 1);
            }
            int binaryTextLengthInt = 7*Integer.parseInt(binaryTextLengthStr, 2); // 7* because word size = 7 bits

            // recover encoded text
            String hiddenText = "";
            int position = 0;
            boolean finished = false;
            for(int i = 0; i < height && !finished; i++){
                for(; j < width && !finished; j++){
                    hiddenText += image.getRGB(j,i) & 1;
                    position++;
                    if(position >= binaryTextLengthInt){
                        finished = true;
                    }
                }
            }

            // split binary string of encoded bits into substrings of length 7
            String[] asciis = new String[binaryTextLengthInt/7];
            for(int i = 0; i <  binaryTextLengthInt/7; i++){
                asciis[i] = hiddenText.substring(7*i,7*i+7);
            }
            // convert binary substrings into characters and append to final text
            String text = "";
            int temp;
            for(int i = 0; i < asciis.length; i++){
                temp = Integer.parseInt(asciis[i],2);
                text += Character.toString((char)temp);
            }

            System.out.println("Steganographic hidden message: " + text);

        }
        catch(IOException e){
            System.out.println("Error: " + e);
        }
    }
}
