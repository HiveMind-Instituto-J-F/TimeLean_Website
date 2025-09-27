package hivemind.hivemindweb.Tool;
import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

public class Tool {
    //Legacy Code of Login Systhen:
    // public static Admin binarySeach(List<Admin> adminsList, Admin admin){
    //     // Algoritmo De Pesquisa , Mais informçoes em https://www.geeksforgeeks.org/java/binary-search-in-java/
    //     // Admin class is ref for Id for ther I use the class more for sematic question for 1 List for 1 Class of this list
    //     try{
    //         int low = 0;
    //         int high = (adminsList.size() - 1);
            
    //         while (low <= high) {
    //             int med = (low + high) / 2;
    //             Admin point = adminsList.get(med);      
    //             if(point.getId() == admin.getId()){
    //                 return point; // User Exit
    //             }
    //             else if (point.getId() > admin.getId()) {
    //                 high = med - 1;
    //             } 
    //             else {
    //                 low = med + 1;                
    //             }
    //         }
    //     }catch(NullPointerException npe){
    //         System.out.println("[ERROR] Null Point Error, Erro: " + npe.getMessage());
    //     }
    //     return null; // Its Nessesary beacose if i retrun bolean values in DAO i make 2 search for get Class of id
    // }

    public static String hash(String password) throws IOException {
        if (password == null){
            throw new IOException("password must not be null");
        }
        String salt = BCrypt.gensalt();
        String hash = BCrypt.hashpw(password.trim(), salt);

        return hash;
    }

    public static boolean matchHash(String password, String hash) {
        if (password == null || hash == null) {
            return false;
        }
        String cleanPassword = password.trim();
        return BCrypt.checkpw(cleanPassword, hash);
    }
}
