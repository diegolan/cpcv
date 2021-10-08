package br.com.lanfranchi.cpcv.services;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lanfranchi.cpcv.model.Product;
import br.com.lanfranchi.cpcv.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public boolean save(Product product) throws SQLException {
		try {
			productRepository.save(product);
			return true;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problemas com o banco de dados.\n" + ex.getMessage());
			throw ex;
		}
	}

	public boolean delete(Product product) throws SQLException {
		try {
			productRepository.delete(product);
			return true;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problemas com o banco de dados.\n" + ex.getMessage());
			throw ex;
		}
	}

	public List<Product> listAll() throws SQLException {
		try {
			List<Product> productList = productRepository.findAll();
			Collections.sort(productList);
			return productList;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problemas com o banco de dados.\n" + ex.getMessage());
			throw ex;
		}
	}

	public List<Product> listAll(String pesquisa) throws SQLException {
		try {
			List<Product> productList = productRepository.findByNameIgnoreCase(pesquisa.toLowerCase());
			Collections.sort(productList);
			return productList;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problemas com o banco de dados.\n" + ex.getMessage());
			throw ex;
		}
	}
}
