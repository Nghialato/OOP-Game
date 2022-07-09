package object;


import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Bomb extends SuperObject {

        public OBJ_Bomb(){
            name = "Bomb";
            try{
                image = ImageIO.read(getClass().getResourceAsStream("/objects/bomb.png"));
            }catch(IOException e){
                e.printStackTrace();
            }
            collision = false;
        }
    }
