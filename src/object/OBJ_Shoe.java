package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Shoe extends SuperObject {
    public OBJ_Shoe(){
        name = "Shoe";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        solidArea.x = 5 ;

    }
}
