import java.io.IOException;

public class Teste {
    public static void main(String[] args) throws IOException {
        System.out.println(br.com.correios.ClienteWs.getEnderecoPorCep("70002900"));
        System.out.println(br.com.correios.ClienteWs.getMapPorCep("70002900"));

        System.out.println(br.com.viacep.ClienteWs.getEnderecoPorCep("70002900"));
        System.out.println(br.com.viacep.ClienteWs.getMapPorCep("70002900"));

        System.out.println(br.com.postmon.ClienteWs.getEnderecoPorCep("69046380"));
        System.out.println(br.com.postmon.ClienteWs.getMapPorCep("69046380"));
    }
}
