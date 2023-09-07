import java.util.Date;

public class Tratamento {
    private Date dataInicio;
    private Date dataFim;

    public Date getDataInicio() {
        return this.dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return this.dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    @Override
    public String toString() {
        return "Tratamento{" + "dataInicio=" + dataInicio + ", dataFim=" + dataFim + '}';
    }
    
}