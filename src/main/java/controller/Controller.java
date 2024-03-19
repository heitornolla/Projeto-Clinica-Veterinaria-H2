/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.Animal;
import model.ClienteDAO;
import model.AnimalDAO;
import model.Cliente;
import model.Consulta;
import model.ConsultaDAO;
import model.Especie;
import model.EspecieDAO;
import model.ExameDAO;
import model.Veterinario;
import model.VeterinarioDAO;
import view.AnimalTableModel;
import view.ClienteTableModel;
import view.ConsultasTableModel;
import view.GenericTableModel;
import view.VetTableModel;

/**
 *
 * @author heito
 */
public class Controller {
    public Controller(){
        
    }
    
    public static void setTableModel(JTable table, GenericTableModel tableModel) {
        table.setModel(tableModel);
    }
    
    public boolean createClient(String name, String adress, String zip, String email, String phone){
        Cliente client = ClienteDAO.getInstance().create(name, adress, zip, email, phone);
        return client.getId() > 0;
    }
    
    public void deleteClient(int id){
        ClienteDAO.getInstance().delete(ClienteDAO.getInstance().retrieveById(id));
    }
    public void deleteAnimal(int id){
        AnimalDAO.getInstance().delete(AnimalDAO.getInstance().retrieveById(id));
    }
       
    public void retrieveAllClientsToTable(JTable table){
        setTableModel(table, new ClienteTableModel(ClienteDAO.getInstance().retrieveAll()));
    }
    
    public void retrieveClientById(JFrame frame, JTable table, int id){        
        List<Cliente> clients = new ArrayList();
        clients.add(ClienteDAO.getInstance().retrieveById(id));
        if(clients.get(0) == null){
            JOptionPane.showMessageDialog(frame, "Customer not found!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            setTableModel(table, new ClienteTableModel(clients));            
        }
    }
    
    public void retrieveClienstBySimilarName(JFrame frame, JTable table, String name){
        List<Cliente> clients = ClienteDAO.getInstance().retrieveBySimilarName(name);
        if(clients.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Customer not found!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
        setTableModel(table, new ClienteTableModel(clients));
        }
    }
     
    public void retrieveAllAnimalsToTable(JTable table){
        setTableModel(table, new AnimalTableModel(AnimalDAO.getInstance().retrieveAll()));
    }
    
    public void retrieveAnimalById(JFrame frame, JTable table, int id){        
        List<Animal> animals = new ArrayList();
        animals.add(AnimalDAO.getInstance().retrieveById(id));
        if(animals.get(0) == null){
            JOptionPane.showMessageDialog(frame, "Animal not found!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            setTableModel(table, new AnimalTableModel(animals));            
        }
    }
    
    public Animal retrieveAnimalById(int id){        
        return AnimalDAO.getInstance().retrieveById(id);
    }
    
    public void retrieveAnimalsBySimilarName(JFrame frame, JTable table, String name){
        List<Animal> animals = AnimalDAO.getInstance().retrieveBySimilarName(name);
        if(animals.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Animal not found!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
        setTableModel(table, new AnimalTableModel(animals));
        }
    }
    
    public String retieveClientNameByAnimalId(int id){
        Animal animal = AnimalDAO.getInstance().retrieveById(id);
        Cliente client = ClienteDAO.getInstance().retrieveById(animal.getIdCliente());
        return client.getNome();
    }
    
    public String retieveSpecieNameByAnimalId(int id){
        Animal animal = AnimalDAO.getInstance().retrieveById(id);
        Especie specie = EspecieDAO.getInstance().retrieveById(animal.getIdCliente());
        return specie.getNome();
    }
    
    public void retrieveAnimalsBySimilarSpecieName(JFrame frame, JTable table, String name){
        List<Animal> animals = AnimalDAO.getInstance().retrieveBySimilarSpecieName(name);
        if(animals.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Animal not found!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
        setTableModel(table, new AnimalTableModel(animals));
        }
    }
    
    public boolean createVet (String name, String email, String phone){
        Veterinario vet = VeterinarioDAO.getInstance().create(name, email, phone);
        return vet.getId() > 0;
    }
    
    public void deleteVet(int id){
        VeterinarioDAO.getInstance().delete(VeterinarioDAO.getInstance().retrieveById(id));
    }
    
    public void allVetsToTable(JTable table){
        setTableModel(table, new VetTableModel(VeterinarioDAO.getInstance().retrieveAll()));
    }
    
    public void retrieveVetById(JFrame frame, JTable table, int id){        
        List<Veterinario> vets = new ArrayList();
        vets.add(VeterinarioDAO.getInstance().retrieveById(id));
        if(vets.get(0) == null){
            JOptionPane.showMessageDialog(frame, "Vet not found!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            setTableModel(table, new VetTableModel(vets));            
        }
    }
    
    public void retrieveVetsBySimilarName(JFrame frame, JTable table, String name){
        List<Veterinario> vets = VeterinarioDAO.getInstance().retrieveBySimilarName(name);
        if(vets.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Vet not found!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
        setTableModel(table, new VetTableModel(vets));
        }
    }
    
    public void retrieveAllAppointmentsToTable(JTable table){
        setTableModel(table, new ConsultasTableModel(ConsultaDAO.getInstance().retrieveAll()));
    }
    
    public void retrieveAllAppointmentsToTableByAnimalId(JTable table, int animalId){
        setTableModel(table, new ConsultasTableModel(ConsultaDAO.getInstance().retrieveByAnimalId(animalId)));
    }
    
    public void retrieveAppointmentById(JFrame frame, JTable table, int id){        
        List<Consulta> appointments = new ArrayList();
        appointments.add(ConsultaDAO.getInstance().retrieveById(id));
        if(appointments.get(0) == null){
            JOptionPane.showMessageDialog(frame, "Appointment not found!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            setTableModel(table, new ConsultasTableModel(appointments));            
        }
    }
    
    public void retrieveAppointmentsByDate(JFrame frame, JTable table, String date){
        List<Consulta> appointments = ConsultaDAO.getInstance().retrieveByDate(date);
        if(appointments.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Appointment not found! is Date in corret format? (dd/MM/yyyy)", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
        setTableModel(table, new ConsultasTableModel(appointments));
        }
    }
    
    public void retrieveAppointmentsByAnimalName(JFrame frame, JTable table, String name){
        List<Consulta> appointments = ConsultaDAO.getInstance().retrieveAppointmentsByAnimalName(name);
        if(appointments.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Appointment not found!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
        setTableModel(table, new ConsultasTableModel(appointments));
        }
    }
    
    public void createSpecie(String name){
        Especie specie = EspecieDAO.getInstance().create(name);        
    }
    
    public void deleteSpecie(int id){
        EspecieDAO.getInstance().delete(EspecieDAO.getInstance().retrieveById(id));        
    }
    
    public List<Especie> retrieveAllSpecie() {
        return EspecieDAO.getInstance().retrieveAll();
    }
    
    public boolean createAnimal(String name, Integer birth, String sex, Integer specieId, Integer clientId){
        Animal animal = AnimalDAO.getInstance().create(name, birth, sex, specieId, clientId);
        return animal.getId() > 0;
    }
    
}
