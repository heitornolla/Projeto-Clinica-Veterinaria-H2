package view;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import model.Animal;
import model.Cliente;
import model.AnimalDAO;
import model.ClienteDAO;
import model.Especie;
import model.EspecieDAO;

public class AnimalTableModel extends GenericTableModel {
    public AnimalTableModel(List vDados) {
        super(vDados, new String[]{"ID", "Nome", "Idade", "Sexo", "Dono", "Especie"});
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return Integer.class;
            
            case 1:
                return String.class;

            case 2:
                return Integer.class;

            case 3:
                return String.class;

            case 4:
                return String.class;
            
            case 5:
                return String.class;
                
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Animal animal = (Animal) vDados.get(rowIndex);
        
        switch(columnIndex) {
            case 0:
                return animal.getId();
            
            case 1:
                return animal.getNome();

            case 2:
                LocalDate dataAtual = LocalDate.now();
                int idade = dataAtual.getYear() - animal.getAnoNasc();
                return idade;

            case 3:
                return animal.getSexo();

            case 4:
                Cliente cliente = ClienteDAO.getInstance().retrieveById(animal.getIdCliente());
                if (cliente != null) {
                    return cliente.getNome();
                } else {
                    return "";
                }
                
            case 5:
                Especie especie = EspecieDAO.getInstance().retrieveById(animal.getIdEspecie());
                if (especie != null) {
                    return especie.getNome();
                } else {
                    return "";
                }

            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Animal animal = (Animal) vDados.get(rowIndex);
        
        switch(columnIndex) {
            case 0:
                break;
            
            case 1:
                animal.setNome((String) value);
                break;

            case 2:
                LocalDate dataAtual = LocalDate.now();
                int anoNasc = dataAtual.getYear() - (Integer)value;
                animal.setAnoNasc(anoNasc);
                break;

            case 3:
                 if (value.toString().charAt(0) == 'f' || value.toString().charAt(0) == 'F') {
                    animal.setSexo("Feminino");
                } else if (value.toString().charAt(0) == 'm' || value.toString().charAt(0) == 'M') {
                    animal.setSexo("Masculino");
                } else break;

            case 4:
                break;
                
            case 5:
                String busca = (String)value;
                if (busca != null) {
                    Especie especie = EspecieDAO.getInstance().retrieveByName((String)value);
                    if (especie != null) {
                        int id = especie.getId();
                        animal.setIdEspecie(id);
                    } else {
                        Especie newEspecie = EspecieDAO.getInstance().create(busca);
                        int id = newEspecie.getId();
                        animal.setIdEspecie(id);
                    }
                }
                break;

            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }

        AnimalDAO.getInstance().update(animal);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0 && columnIndex != 4;
    }
}