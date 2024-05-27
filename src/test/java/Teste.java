import java.io.IOException;

public class Teste {
    public static void main(String[] args) throws IOException {
        System.out.println("Teste viacep...");
        try {
            System.out.println(br.com.viacep.ClienteWs.getEnderecoPorCep("70002900"));
            System.out.println(br.com.viacep.ClienteWs.getMapPorCep("70002900"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Teste postmon...");
        try {
            System.out.println(br.com.postmon.ClienteWs.getEnderecoPorCep("70002900"));
            System.out.println(br.com.postmon.ClienteWs.getMapPorCep("70002900"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
