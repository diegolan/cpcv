package br.com.lanfranchi.cpcv.tablemodels;

import java.text.NumberFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.lanfranchi.cpcv.model.Product;
import br.com.lanfranchi.cpcv.util.FieldConverter;

public class ProductTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private List<Product> linhas;
	private String[] colunas = new String[] { "Nome:", "Preço R$:" };
	private NumberFormat format = FieldConverter.getNumberFormat();

	public ProductTableModel(List<Product> linhas) {
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
		Product product = this.linhas.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return product.getName();
		case 1:
			return format.format(product.getPrice());
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
