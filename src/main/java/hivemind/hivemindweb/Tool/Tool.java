package hivemind.hivemindweb.Tool;

// Esta classe e temporaria mais seu conceito deve ser mantido sendo a Idea de manter funçoes statics ultilirias globlais   

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import hivemind.hivemindweb.models.Admin;

public class Tool {
    public static boolean binarySeach(List<Admin> adminsList, Admin admin){
        try{
            int low = 0;
            int high = (adminsList.size() - 1);
            
            while (low <= high) {
                int medio = (low + high) / 2;
                Admin point = adminsList.get(medio);                
            }
        }catch(NullPointerException npe){
            System.out.println("[ERROR] Null Point Error, Erro: " + npe.getMessage());
        }
        return true;
    }
}
