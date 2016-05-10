package br.com.postmon;

/**
 * Entidade baseada nos dados do WS do postmon.com
 */
public class Endereco {
    private String bairro;
    private String cidade;
    private String cep;
    private String logradouro;
    private EstadoInfo estadoInfo;
    private CidadeInfo cidadeInfo;
    private String estado;

    public String getBairro() {
        return bairro;
    }

    public Endereco setBairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public String getCidade() {
        return cidade;
    }

    public Endereco setCidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public String getCep() {
        return cep;
    }

    public Endereco setCep(String cep) {
        this.cep = cep;
        return this;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public Endereco setLogradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public EstadoInfo getEstadoInfo() {
        return estadoInfo;
    }

    public Endereco setEstadoInfo(EstadoInfo estadoInfo) {
        this.estadoInfo = estadoInfo;
        return this;
    }

    public CidadeInfo getCidadeInfo() {
        return cidadeInfo;
    }

    public Endereco setCidadeInfo(CidadeInfo cidadeInfo) {
        this.cidadeInfo = cidadeInfo;
        return this;
    }

    public String getEstado() {
        return estado;
    }

    public Endereco setEstado(String estado) {
        this.estado = estado;
        return this;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", cep='" + cep + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", estadoInfo=" + estadoInfo +
                ", cidadeInfo=" + cidadeInfo +
                ", estado='" + estado + '\'' +
                '}';
    }
}
