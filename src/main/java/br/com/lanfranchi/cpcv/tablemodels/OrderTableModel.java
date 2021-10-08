package br.com.lanfranchi.cpcv.tablemodels;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.lanfranchi.cpcv.model.Order;
import br.com.lanfranchi.cpcv.util.FieldConverter;

public class OrderTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private List<Order> linhas;
	private String[] colunas = new String[] { "Data:", "Total R$:" };

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
	private NumberFormat format = FieldConverter.getNumberFormat();

	public OrderTableModel(List<Order> linhas) {
		this.linhas = linhas;
	}

	public int getRowCount() {
		return this.linhas.size();
	}

	public int getColumnCount() {
		return this.colunas.length;
	}

	public String getColumnName(int indice) {
		return this.colunas[indice];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Order order = this.linhas.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return this.sdf.format(order.getCreatedAt());
		case 1:
			return format.format(order.getTotal());
		}
		throw new IndexOutOfBoundsException("A coluna com o índice: " + columnIndex + " não existe!");
	}

	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
		case 1:
			return String.class;
		}
		throw new IndexOutOfBoundsException("A coluna com o índice: " + columnIndex + " não existe!");
	}
}
