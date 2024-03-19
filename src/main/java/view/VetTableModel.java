package view;

import java.util.List;
import model.Veterinario;
import model.VeterinarioDAO;

public class VetTableModel extends GenericTableModel {
    public VetTableModel(List vDados) {
        super(vDados, new String[]{"ID", "Nome", "E-mail", "Telefone"});
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return Integer.class;
                
            case 1:
                return String.class;
            
            case 2:
                return String.class;
            
            case 3:
                return String.class;
                
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Veterinario veterinario = (Veterinario) vDados.get(rowIndex);
        
        switch(columnIndex) {
            case 0:
                return veterinario.getId();
            
            case 1:
                return veterinario.getNome();
            
            case 2:
                return veterinario.getEmail();
                
            case 3:
                return veterinario.getTelefone();
                                       
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Veterinario veterinario = (Veterinario) vDados.get(rowIndex);
        
        switch(columnIndex) {
            case 0:
                break;
            
            case 1:
                veterinario.setNome((String) value);
                break;
            
            case 2:
                veterinario.setEmail((String) value);
                break;
                
            case 3:
                veterinario.setTelefone((String) value);
                break;
                                              
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        
        VeterinarioDAO.getInstance().update(veterinario);
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }
    
}