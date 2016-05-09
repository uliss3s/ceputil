package br.com.viacep;

/**
 * Entidade baseada nos dados do WS do viacep.com
 */
public class Endereco {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String unidade;
    private String ibge;
    private String gia;

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getUf() {
        return uf;
    }

    public String getUnidade() {
        return unidade;
    }

    public String getIbge() {
        return ibge;
    }

    public String getGia() {
        return gia;
    }

    public Endereco setCep(String cep) {
        this.cep = cep;
        return this;
    }

    public Endereco setLogradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public Endereco setComplemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public Endereco setBairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public Endereco setLocalidade(String localidade) {
        this.localidade = localidade;
        return this;
    }

    public Endereco setUf(String uf) {
        this.uf = uf;
        return this;
    }

    public Endereco setUnidade(String unidade) {
        this.unidade = unidade;
        return this;
    }

    public Endereco setIbge(String ibge) {
        this.ibge = ibge;
        return this;
    }

    public Endereco setGia(String gia) {
        this.gia = gia;
        return this;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "cep='" + cep + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", complemento='" + complemento + '\'' +
                ", bairro='" + bairro + '\'' +
                ", localidade='" + localidade + '\'' +
                ", uf='" + uf + '\'' +
                ", unidade='" + unidade + '\'' +
                ", ibge='" + ibge + '\'' +
                ", gia='" + gia + '\'' +
                '}';
    }
}
