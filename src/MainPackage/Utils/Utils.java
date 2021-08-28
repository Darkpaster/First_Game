package MainPackage.Utils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Utils {

    public static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        newImage.getGraphics().drawImage(image, 0, 0, width, height, null);
        return newImage;
    }

    public static Integer[][] levelParser(String filePath) throws FileNotFoundException {
        //randomTiles(filePath);
        Integer result[][] = null;
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = null;
            ArrayList<Integer[]>lvlLines = new ArrayList<Integer[]>();
            while((line = reader.readLine()) != null) {
                lvlLines.add(stringToInt(line.split(" ")));
            }
            result = new Integer[lvlLines.size()][lvlLines.get(0).length];
            for(int i = 0; i < lvlLines.size(); i++){
                result[i] = lvlLines.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void randomTiles(String FilePath) throws FileNotFoundException {
        File out = new File(FilePath);
        FileWriter fw = null;
        int n = 0;
        int m = 0;
        try {
            fw = new FileWriter(out);
            BufferedWriter writer = new BufferedWriter(fw);
            int line;
            Random random = new Random();
            while (n < 157) {
                line = random.nextInt(3) + 1; // bound - from 0 to number, +number - add min and max 22 - 27
                n++;
                writer.write(line + " ");
                /*if (n > 8 && n < 12 && m < 7 && m > 4) {
                    line = 8;
                    writer.write(line + " ");
                } else {
                    writer.write(line + " ");
                }
                while (n > 200 && m > 200) {
                    line = random.nextInt(3) + 4;
                    writer.write(line + " ");
                    n--;
                }*/
                if (n == 157) {
                    n = 0;
                    writer.newLine();
                    m++;
                    if (m == 157) {
                        n = 158;
                        m = 158;
                    }
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static final Integer[] stringToInt(String[] sArr){
        Integer[] result = new Integer[sArr.length];

        for(int i = 0; i < sArr.length; i++){
            result[i] = Integer.parseInt(sArr[i]);
        }

        return result;
    }
}
