package br.com.lanfranchi.cpcv.services;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lanfranchi.cpcv.model.ItemHistory;
import br.com.lanfranchi.cpcv.model.Order;
import br.com.lanfranchi.cpcv.repositories.ItemHistoryRepository;
import br.com.lanfranchi.cpcv.repositories.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ItemHistoryRepository itemHistoryRepository;
	
	public boolean save(Order order) throws SQLException {
		try {
			orderRepository.save(order);
			return true;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problemas com o banco de dados.\n" + ex.getMessage());
			throw ex;
		}
	}

	public List<Order> getOrders() throws SQLException {
		try {
			List<Order> orderList = orderRepository.findAll();
			Collections.sort(orderList);
			return orderList;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problemas com o banco de dados.\n" + ex.getMessage());
			throw ex;
		}
	}

	public List<Order> getOrdersDay() throws SQLException {
		try {
			
			Calendar today = Calendar.getInstance();
			
			today.set(Calendar.HOUR_OF_DAY, 0);
			today.set(Calendar.MINUTE, 0);
			today.set(Calendar.SECOND, 0);
			Date dateInicio = today.getTime();
			
			today.set(Calendar.HOUR_OF_DAY, 23);
			today.set(Calendar.MINUTE, 59);
			today.set(Calendar.SECOND, 59);
			Date dateFim =  today.getTime();
			
			return this.getOrders(dateInicio, dateFim);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problemas com o banco de dados.\n" + ex.getMessage());
			throw ex;
		}
	}

	public List<Order> getOrders(Date inicio, Date fim) throws SQLException {
		try {
			List<Order> orderList = orderRepository.findByCreatedAtBetween(inicio, fim);
			Collections.sort(orderList);
			return orderList;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problemas com o banco de dados.\n" + ex.getMessage());
			throw ex;
		}
	}

	public List<ItemHistory> findAllItems(Order order) {
		try {
			return itemHistoryRepository.findByOrder(order);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problemas com o banco de dados.\n" + ex.getMessage());
			throw ex;
		}
	}
}
