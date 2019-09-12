# Steganography
Basic steganographic programs which work together. 

TextInImage.java:

Takes in as input a filepath to an image, text to encode, and a filepath for the output steganographic image. Encoding works by
altering the least significant bits of consecutive pixels. The first 14 pixels' least significant bits encode the length of the
message encoded. The following pixels' least significant bits encode the ASCII values for each character in the message. The new
image is then output, with a filepath equal to that which was input.

TextFromImage.java

Takes in as input a steganographic image output by TextInImage.java, and outputs the decoded message. Decoding works by reading 
the number formed by the least significant bits of the first 14 pixels (= number of pixels which encode text). Then, the least 
significant bits of the relevant number of following pixels are concatenated to form ASCII values, which are then converted back into
characters, which are then concatenated to recover the original message. This message is output to the user.
