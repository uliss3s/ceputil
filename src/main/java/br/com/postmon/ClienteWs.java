package br.com.postmon;

import com.github.uliss3s.ceputil.ClientException;
import com.github.uliss3s.ceputil.Util;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.util.*;

/**
 * Classe para recuperar informações do WS do postmon.com.br
 */
public class ClienteWs {

    private ClienteWs() {}

    /**
     * Recupera objeto Endereco pelo CEP
     * @param cep String no formato 00000000
     * @return instancia de br.com.postmon.Endereco
     */
    public static Endereco getEnderecoPorCep(String cep) {
        JsonObject jsonObject = getCepResponse(cep);
        return new Endereco()
                .setBairro(jsonObject.getString("bairro"))
                .setCidade(jsonObject.getString("cidade"))
                .setCep(jsonObject.getString("cep"))
                .setLogradouro(jsonObject.getString("logradouro"))
                .setEstadoInfo(new EstadoInfo()
                        .setNome(jsonObject.getJsonObject("estado_info").getString("nome"))
                        .setArea_km2(jsonObject.getJsonObject("estado_info").getString("area_km2"))
                        .setCodigo_ibge(jsonObject.getJsonObject("estado_info").getString("codigo_ibge"))
                )
                .setCidadeInfo(new CidadeInfo()
                        .setArea_km2(jsonObject.getJsonObject("cidade_info").getString("area_km2"))
                        .setCodigo_ibge(jsonObject.getJsonObject("cidade_info").getString("codigo_ibge"))
                )
                .setEstado(jsonObject.getString("estado"));
    }

    /**
     * Recupera Map<String,String> pelo CEP
     * @param cep String no formato 00000000
     * @return instancia de Map<String,Object>
     */
    public static Map<String, Object> getMapPorCep(String cep) {

        JsonObject jsonObject = getCepResponse(cep);

        Map<String, Object> mapa = null;
        mapa = new HashMap<>();

        for (Map.Entry<String, JsonValue> entry : jsonObject.entrySet()) {
            if (entry.getValue().getValueType().equals(JsonValue.ValueType.OBJECT)) {
                JsonObject subObject = jsonObject.getJsonObject(entry.getKey());

                Map<String, String> mapaSo = new HashMap<>();
                for (Map.Entry<String, JsonValue> entrySo : subObject.entrySet()) {
                    mapaSo.put(entrySo.getKey(), entrySo.getValue().toString());
                }

                mapa.put(entry.getKey(), mapaSo);
            } else {
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
                HttpGet httpGet = new HttpGet("http://api.postmon.com.br/v1/cep/" + cep);
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
