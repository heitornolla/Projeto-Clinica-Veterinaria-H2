package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import model.Animal;
import model.AnimalDAO;
import model.Cliente;
import model.ClienteDAO;
import model.Consulta;
import model.ConsultaDAO;
import model.Veterinario;
import model.VeterinarioDAO;


public class ConsultasTableModel extends GenericTableModel{
    public ConsultasTableModel(List vDados){
        super(vDados, new String[]{"ID", "Animal e Dono","Data", "Horario", "Veterinario", "Comentario", "Status"});
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class; 
            case 4:
                return String.class;   
            case 5:
                return String.class;   
            case 6:
                return Integer.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Consulta c = (Consulta) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return c.getId();
                
            case 1:
                Animal animal = AnimalDAO.getInstance().retrieveById(c.getIdAnimal());
                if (animal != null) {
                    Cliente cliente = ClienteDAO.getInstance().retrieveById(animal.getIdCliente());
                    String nomeCliente = cliente.getNome();
                    return animal.getNome() + " || " + nomeCliente;
                } else {
                    return "";
                }
                
            case 2:
                return c.getData();
                
            case 3:
                return c.getHorario(); 
                
            case 4:
                Veterinario veterinario = VeterinarioDAO.getInstance().retrieveById(c.getIdVeterinario());
                if (veterinario != null) {
                    return veterinario.getNome();
                } else {
                    return "";
                }
                
            case 5:
                return c.getComentario();
                
            case 6:
                return c.getTerminado();
                
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }    
       
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Consulta c = (Consulta) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                break;
                
            case 1:
                break;
                
            case 2:
                String textoData = (String)aValue;
                
                if (!textoData.matches("[0-9()-/]*")) {
                    return;
                }

                String diaString = textoData.substring(0, 2);
                String mesString = textoData.substring(textoData.indexOf('/') + 1, textoData.lastIndexOf('/'));
                String anoString = textoData.substring(textoData.lastIndexOf('/') + 1);

                int diaInserido = Integer.parseInt(diaString);
                int mesInserido = Integer.parseInt(mesString);
                int anoInserido = Integer.parseInt(anoString);

                LocalDate dataAtual = LocalDate.now();
                int diaAtual = dataAtual.getDayOfMonth();
                int mesAtual = dataAtual.getMonthValue();
                int anoAtual = dataAtual.getYear();

                if ((diaInserido < diaAtual && mesInserido <= mesAtual && anoInserido <= anoAtual)
                        || diaInserido > 31 || mesInserido > 12) {
                    JOptionPane.showMessageDialog(null, "Preencha uma data válida!\n"
                            + "Formato correto: DD/MM/AAAA\n"
                            + "Sem espaços e com barras!",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar dataCalendar = Calendar.getInstance();

                    try {
                        dataCalendar.setTime(formatoData.parse(textoData));
                    } catch (ParseException e) {
                        e.printStackTrace(); 
                    }
                    
                    c.setData(dataCalendar);
                }
                break;
            
            case 3:
                String horario = (String)aValue;
                if (!horario.matches("[0-9()-:]*") || horario.length() != 5) {
                    JOptionPane.showMessageDialog(null, "Insira um horário válido!\n"
                            + "Formato correto: HH:MM\n"
                            + "Sem espaços e com o dois pontos!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                c.setHorario(horario);
                break;
                
            case 4:
                break;
                
            case 5:
                c.setComentario((String)aValue);
                break;
                
            case 6:
                int status = (Integer)aValue;
                if (status < 0 || status > 2) {
                    JOptionPane.showMessageDialog(null, "Insira um Status válido!\n"
                            + "Opções: 0 (Em andamento) || 1 (Finalizada) || 2 (Cancelada)",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    c.setTerminado((Integer)aValue);
                }
                break;
                
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        ConsultaDAO.getInstance().update(c);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0 && columnIndex != 1 && columnIndex != 4;
    }
}
