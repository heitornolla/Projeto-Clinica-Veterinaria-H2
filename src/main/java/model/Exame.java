package model;

public class Exame {
    private int id;
    private String nome;
    private int idConsulta;
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Exame(int id, String nome, int idConsulta) {
        this.id = id;
        this.nome = nome;
        this.idConsulta = idConsulta;
    }  

    @Override
    public String toString() {
        return "Exame{" + "id=" + id + ", nome=" + nome + ", idConsulta=" + idConsulta + '}';
    }
    
}