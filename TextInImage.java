/*

Program where user inputs a filepath to an image, text to encode in that image, and a filepath for where to output the new steganographic image.
Filepath to existing image = args[0]
Filepath to new steganographic image = args[1]
Text is read with Scanner during runtime

Omer Baddour, 6/8/19

3301

 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TextInImage {

    public static void main(String[] args){

        BufferedImage image = null;
        // dimensions of image
        int width;
        int height;

        try {
            // read image to encode text in
            // filepath for input
            image = ImageIO.read(new File(args[0]));

            System.out.println("Image read successfully.");
            width = image.getWidth();
            height = image.getHeight();

            // read text to encode - MAXIMUM length of 16383 characters
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the text you would like to encode (max length 16383 characters): ");
            String text = "";
            String tmp;
            while(sc.hasNextLine() && !(tmp = sc.nextLine()).equals("")){
                text += tmp;
            }

            // convert text.length() and text to binary 7 bit US-ASCII representation
            String binaryText = Integer.toBinaryString(text.length());
            // force length to be 14 bit number by prepending 0s
            while(binaryText.length() < 14){
                binaryText = "0" + binaryText;
            }
            char[] textArr = text.toCharArray();
            for(int i = 0; i < textArr.length; i++){
                String binaryChar = Integer.toBinaryString(textArr[i]);
                if(binaryChar.length() > 7){
                    System.out.println("The " + i + "th character you have entered cannot be represented in US-ASCII with 7 bits," +
                            " thus the text cannot be encoded by this character set.");
                    return;
                }
                while(binaryChar.length() < 7){
                    binaryChar = "0" + binaryChar;
                }
                binaryText += binaryChar;
            }

            // steganographise new image with image + binaryText
            // make first pixels store the length of the text

            // make lowest order bit of each pixel equal to each bit in binary text
            int position = 0;
            boolean finished = false;
            for(int i = 0; i < height && !finished; i++){
                for(int j = 0; j < width && !finished; j++){
                    if(binaryText.charAt(position) == '1'){
                        image.setRGB(j,i, image.getRGB(j,i) | 1);
                    }
                    else if(binaryText.charAt(position) == '0'){
                        // -2 = 1...10, bitwise & forces last bit to be 0
                        image.setRGB(j,i, image.getRGB(j,i) & -2);
                    }
                    position++;
                    if(position >= binaryText.length()){
                        finished = true;
                    }
                }
            }

            // write new image to memory (lossless)
            // filepath for output
            ImageIO.write(image, "png", new File(args[1]));

            System.out.println("Steganographic image written successfully.");
        }
        catch(IOException e){
            System.out.println("Error: " + e);
        }
    }
}
