package object;

import javax.imageio.ImageIO;
import java.io.IOException;


public class OBJ_Wood extends SuperObject {
    public OBJ_Wood() {
        name = "Wood";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/floor01.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        solidArea.x = 5;

    }
}
