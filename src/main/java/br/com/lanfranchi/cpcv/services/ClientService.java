package br.com.lanfranchi.cpcv.services;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lanfranchi.cpcv.model.Client;
import br.com.lanfranchi.cpcv.repositories.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	public boolean save(Client client) throws SQLException {
		try {
			clientRepository.save(client);
			return true;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problemas com o banco de dados.\n" + ex.getMessage());
			throw ex;
		}
	}

	public boolean delete(Client client) throws SQLException {
		try {
			clientRepository.delete(client);
			return true;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problemas com o banco de dados.\n" + ex.getMessage());
			throw ex;
		}
	}

	public List<Client> listAll() throws SQLException {
		try {
			List<Client> clientList = clientRepository.findAll();
			Collections.sort(clientList);
			return clientList;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problemas com o banco de dados.\n" + ex.getMessage());
			throw ex;
		}
	}

	public List<Client> listAll(String pesquisa) throws SQLException {
		try {
			List<Client> clientList = clientRepository.findByNameIgnoreCaseOrPhoneIgnoreCase(pesquisa.toLowerCase());
			Collections.sort(clientList);
			return clientList;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problemas com o banco de dados.\n" + ex.getMessage());
			throw ex;
		}
	}
}
