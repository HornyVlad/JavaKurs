
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {

    public Image loadImage(String fileName,int width,int height) {
        Image img = null;
        try {
            File imageFile = null;
            imageFile = new File(getClass().getClassLoader().getResource(fileName).getFile());
            // Очистка изображения, если оно было до этого
            if (img != null) {
                img.flush();
            }
            // Загрузка изображения из файла в объект img
            img = ImageIO.read(imageFile).getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
        } catch (IOException exOb) {
            // Вывод в консоль сообщения об ошибке
            System.out.println("Cannot load image file: " + fileName);
        }
        return img;
    }

    public ImageIcon loadIconImage(String fileName) {
        ImageIcon img = null;
        img = new ImageIcon(fileName);
        return img;
    }

    public BufferedImage loadBuffImage(String fileName) {
        BufferedImage img = null;
        File imageFile;
        // Получение файла из папки ресурсов
        imageFile = new File(fileName);
        // Загрузка изображения из файла в объект img
        try {
            img = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.out.println("Cannot load image file: " + fileName);
        }
        return img;
    }
    private Image buffIMG;
    public Image getImage(int id)
    {
        switch (id)
        {
            case 1:
                buffIMG = loadBuffImage("src/Image/blackBallDark.png");
                break;
//            case 2:
//                buffIMG = loadBuffImage("src/Image/blueBallDark.png");
//                break;
            case 2:
                buffIMG = loadBuffImage("src/Image/greenBallDark.png");
                break;
            case 3:
                buffIMG = loadBuffImage("src/Image/orangeBallDark.png");
                break;
            case 4:
                buffIMG = loadBuffImage("src/Image/pinkBallDark.png");
                break;
            case 5:
                buffIMG = loadBuffImage("src/Image/blackBallLight.png");
                break;
//            case 7:
//                buffIMG = loadBuffImage("src/Image/blueBallLight.png");
//                break;
            case 6:
                buffIMG = loadBuffImage("src/Image/greenBallLight.png");
                break;
            case 7:
                buffIMG = loadBuffImage("src/Image/orangeBallLight.png");
                break;
            case 8:
                buffIMG = loadBuffImage("src/Image/pinkBallLight.png");
                break;
        }
        return buffIMG;
    }
}
