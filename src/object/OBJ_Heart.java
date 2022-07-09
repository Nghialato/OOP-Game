package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends SuperObject{
    public OBJ_Heart(){
        name = "Heart";
        try{
             image = ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
