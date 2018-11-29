# ceputil

Este pequeno projeto utiliza os webservices do correio SIGEP WEB (http://www.correios.com.br), viacep (https://viacep.com.br) e postmon (http://postmon.com.br), para disponibilizar informações de endereço.

# Info

Projeto desenvolvido utilizando o Java SDK 8 (Nivel de linguagem 1.8), com a IDE Intellij IDEA 2017.3.5

# Consumo do WS do viacep.com.br usando apenas classes nativas java (SDK 8)

``` java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClienteViaCepWS {

    public static String buscarCep(String cep) {
        String json;

        try {
            URL url = new URL("http://viacep.com.br/ws/"+ cep +"/json");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder jsonSb = new StringBuilder();

            br.lines().forEach(l -> jsonSb.append(l.trim()));

            json = jsonSb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return json;
    }

    public static void main(String[] args) throws IOException {
        String json = buscarCep("69046000");
        System.out.println(json);

        Map<String,String> mapa = new HashMap<>();

        Matcher matcher = Pattern.compile("\"\\D.*?\": \".*?\"").matcher(json);
        while (matcher.find()) {
            String[] group = matcher.group().split(":");
            mapa.put(group[0].replaceAll("\"", "").trim(), group[1].replaceAll("\"", "").trim());
        }

        System.out.println(mapa);
    }
}
```
