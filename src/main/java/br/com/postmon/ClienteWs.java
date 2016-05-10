package br.com.postmon;

import com.github.uliss3s.ceputil.Util;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.*;

/**
 * Classe para recuperar informações do WS do postmon.com.br
 */
public class ClienteWs {

    private static final Set<String> CAMPOS = new HashSet<String>(Arrays.asList(
            "bairro",
            "cidade",
            "cep",
            "logradouro",
            "estado_info",
            "area_km2",
            "codigo_ibge",
            "nome",
            "cidade_info",
            "area_km2",
            "codigo_ibge",
            "estado"
    ));

    /**
     * Recupera objeto Endereco pelo CEP
     * @param cep String no formato 00000000
     * @return instancia de br.com.postmon.Endereco
     */
    public static Endereco getEnderecoPorCep(String cep) {

        JsonObject jsonObject = getCepResponse(cep);
        Endereco endereco = new Endereco()
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

        return endereco;
    }

    /**
     * Recupera Map<String,String> pelo CEP
     * @param cep String no formato 00000000
     * @return instancia de Map<String,Object>
     */
    public static Map<String, Object> getMapPorCep(String cep) {

        JsonObject jsonObject = getCepResponse(cep);

        Map<String, Object> mapa = null;
        mapa = new HashMap<String, Object>();

        for (Iterator<Map.Entry<String,JsonValue>> it = jsonObject.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String,JsonValue> entry = it.next();

            if (entry.getValue().getValueType().equals(JsonValue.ValueType.OBJECT)) {
                JsonObject subObject = jsonObject.getJsonObject(entry.getKey());

                Map<String, String> mapaSo = new HashMap<String, String>();
                for (Iterator<Map.Entry<String,JsonValue>> itso = subObject.entrySet().iterator(); itso.hasNext();) {
                    Map.Entry<String,JsonValue> entrySo = itso.next();

                    mapaSo.put(entrySo.getKey(), entrySo.getValue().toString());
                }

                mapa.put(entry.getKey(), mapaSo);
            } else {
                mapa.put(entry.getKey(), entry.getValue().toString());
            }
        }

        return mapa;
    }

    private static JsonObject getCepResponse(String cep) {

        JsonObject responseJO = null;

        try {
            if (!Util.validaCep(cep)) {
                throw new RuntimeException("Formato de CEP inválido");
            }

            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://api.postmon.com.br/v1/cep/" + cep);
            HttpResponse response = httpclient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException(response.getStatusLine().getReasonPhrase());
            }

            HttpEntity entity = response.getEntity();

            responseJO = Json.createReader(entity.getContent()).readObject();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return responseJO;
    }
}
