package br.com.lanfranchi.cpcv.services;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lanfranchi.cpcv.model.Configurations;
import br.com.lanfranchi.cpcv.repositories.ConfigurationsRepository;

@Service
public class ConfigurationsService {
	
	public static final String MASTER_PASSWORD = "masteradmin1234";
	
	@Autowired
	private ConfigurationsRepository configurationsRepository;
	
	public boolean savePassword(String password) throws SQLException {
		try {
			Configurations p = get();
			p.setPassword(password);
			configurationsRepository.save(p);
			return true;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problemas com o banco de dados.\n" + ex.getMessage());
			throw new SQLException(ex);
		}
	}
	
	public boolean saveConfigurations(Configurations c) throws SQLException {
		try {
			Configurations p = get();
			p.setName(c.getName());
			p.setTelephone(c.getTelephone());
			p.setAddress(c.getAddress());
			p.setPrintClient(c.isPrintClient());
			p.setPrintOrderType(c.isPrintOrderType());
			p.setPrintPaymentType(c.isPrintPaymentType());
			p.setTopSpaces(c.getTopSpaces());
			p.setBottomSpaces(c.getBottomSpaces());
			configurationsRepository.save(p);
			return true;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problemas com o banco de dados.\n" + ex.getMessage());
			throw new SQLException(ex);
		}
	}

	public String getPassword() throws SQLException {
		try {
			return get().getPassword();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problemas com o banco de dados.\n" + ex.getMessage());
			throw new SQLException(ex);
		}
	}
	
	public Configurations get() throws SQLException {
		try {
			return configurationsRepository.findAll().stream().findFirst().get();
		} catch (Exception e) {
			try {
				Configurations c = new Configurations();
				c.setPassword(MASTER_PASSWORD);
				c.setName("");
				c.setTelephone("");
				c.setAddress("");
				c.setPrintClient(false);
				c.setPrintOrderType(false);
				c.setPrintPaymentType(false);
				c.setTopSpaces(1);
				c.setBottomSpaces(1);
				return configurationsRepository.save(c);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Problemas com o banco de dados.\n" + ex.getMessage());
				throw new SQLException(ex);
			}
		}
	}
}
