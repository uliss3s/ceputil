package br.com.viacep;

import com.github.uliss3s.ceputil.ClientException;
import com.github.uliss3s.ceputil.Util;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe para recuperar informações do WS do viacep.com.br
 */
public class ClienteWs {
    
    private ClienteWs() {}

    /**
     * Recupera objeto Endereco pelo CEP
     * @param cep String no formato 00000000
     * @return instancia de br.com.viacep.Endereco
     */
    public static Endereco getEnderecoPorCep(String cep) {

        JsonObject jsonObject = getCepResponse(cep);
        Endereco endereco = null;

        JsonValue erro = jsonObject.get("erro");

        if (erro == null) {

            endereco = new Endereco()
                    .setCep(jsonObject.getString("cep"))
                    .setLogradouro(jsonObject.getString("logradouro"))
                    .setComplemento(jsonObject.getString("complemento"))
                    .setBairro(jsonObject.getString("bairro"))
                    .setLocalidade(jsonObject.getString("localidade"))
                    .setUf(jsonObject.getString("uf"))
                    .setIbge(jsonObject.getString("ibge"))
                    .setGia(jsonObject.getString("gia"))
                    .setDdd(jsonObject.getString("ddd"))
                    .setSiafi(jsonObject.getString("siafi"));
        }

        return endereco;
    }

    /**
     * Recupera Map<String,String> pelo CEP
     * @param cep String no formato 00000000
     * @return instancia de Map<String,String>
     */
    public static Map<String, String> getMapPorCep(String cep) {

        JsonObject jsonObject = getCepResponse(cep);

        JsonValue erro = jsonObject.get("erro");

        Map<String, String> mapa = null;
        if (erro == null) {
            mapa = new HashMap<>();
            
            for (Map.Entry<String, JsonValue> entry : jsonObject.entrySet()) {
                mapa.put(entry.getKey(), entry.getValue().toString());
            }
        }

        return mapa;
    }
    
    //TODO criar um client único para os serviços
    private static JsonObject getCepResponse(String cep) {
        JsonObject responseJO;

        try {
            if (!Util.validaCep(cep)) {
                throw new ClientException("Formato de CEP inválido");
            }

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpGet httpGet = new HttpGet(String.format("https://viacep.com.br/ws/%s/json", cep));
                CloseableHttpResponse response = httpClient.execute(httpGet);
                
                HttpEntity entity = response.getEntity();

                try (JsonReader reader = Json.createReader(entity.getContent())) {
                    responseJO = reader.readObject();
                }

                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new ClientException(response.getStatusLine().getReasonPhrase());
                }
                
                response.close();
            }

        } catch (Exception e) {
            throw new ClientException(e);
        }

        return responseJO;
    }
}
