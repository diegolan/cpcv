package br.com.lanfranchi.cpcv.tablemodels;

import java.text.NumberFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.lanfranchi.cpcv.model.Item;
import br.com.lanfranchi.cpcv.util.FieldConverter;

public class ItemTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private List<Item> linhas;
	private String[] colunas = new String[] { "Nome:", "Quantidade:", "Subtotal R$:" };
	private NumberFormat format = FieldConverter.getNumberFormat();
	private NumberFormat format3 = FieldConverter.getNumberFormat(3);

	public ItemTableModel(List<Item> linhas) {
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
		Item product = this.linhas.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return product.getItem().getName();
		case 1:
			return format3.format(new Double(product.getQuantity()));
		case 2:
			return format.format(product.getTotal());
		}
		throw new IndexOutOfBoundsException("A coluna com o índice: " + columnIndex + " não existe!");
	}

	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
		case 1:
		case 2:
			return String.class;
		}
		throw new IndexOutOfBoundsException("A coluna com o índice: " + columnIndex + " não existe!");
	}
}
