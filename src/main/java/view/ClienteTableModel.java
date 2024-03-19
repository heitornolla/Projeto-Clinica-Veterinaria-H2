package view;

import java.util.List;
import model.Cliente;
import model.ClienteDAO;

public class ClienteTableModel extends GenericTableModel {
    public ClienteTableModel(List vDados) {
        super(vDados, new String[]{"ID", "Nome", "Endereço", "Telefone", "CEP", "Email"});
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
        Cliente cliente = (Cliente) vDados.get(rowIndex);
        
        switch(columnIndex) {
            case 0:
                return cliente.getId();
            
            case 1:
                return cliente.getNome();
            
            case 2:
                return cliente.getEndereco();
                
            case 3:
                return cliente.getTelefone();
                
            case 4:
                return cliente.getCep();
                
            case 5:
                return cliente.getEmail();
                
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Cliente cliente = (Cliente) vDados.get(rowIndex);
        
        switch(columnIndex) {
            case 0:
                break;
            
            case 1:
                cliente.setNome((String) value);
                break;
            
            case 2:
                cliente.setEndereco((String) value);
                break;
                
            case 3:
                cliente.setTelefone((String) value);
                break;
                
            case 4:
                cliente.setCep((String) value);
                break;
                
            case 5:
                cliente.setEmail((String) value);
                break;
                
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
                
        }
        
        ClienteDAO.getInstance().update(cliente);
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }
    
}