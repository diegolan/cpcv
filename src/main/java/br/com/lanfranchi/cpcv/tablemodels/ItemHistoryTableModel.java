package br.com.lanfranchi.cpcv.tablemodels;

import java.text.NumberFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.lanfranchi.cpcv.model.ItemHistory;
import br.com.lanfranchi.cpcv.util.FieldConverter;

public class ItemHistoryTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private List<ItemHistory> linhas;
	private String[] colunas = new String[] { "Nome:", "Quantidade:", "Preço:", "Subtotal:" };
	private double total;
	private NumberFormat format = FieldConverter.getNumberFormat();
	private NumberFormat format3 = FieldConverter.getNumberFormat(3);

	public ItemHistoryTableModel(List<ItemHistory> linhas) {
		this.linhas = linhas;
		for (ItemHistory item : this.linhas) {
			total += item.getPrice() * item.getQuantity();
		}
		ItemHistory itemTotal = new ItemHistory();
		itemTotal.setName("Total:");
		itemTotal.setPrice(-1);
		this.linhas.add(itemTotal);
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
		ItemHistory item = this.linhas.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return item.getName();
		case 1:
			if (item.getPrice() == -1) {
				return "";
			}
			return format3.format(item.getQuantity());
		case 2:
			if (item.getPrice() == -1) {
				return "";
			}
			return format.format(item.getPrice());
		case 3:
			if (item.getPrice() == -1) {
				return format.format(total);
			}
			return format.format(item.getPrice() * item.getQuantity());
		}
		throw new IndexOutOfBoundsException("A coluna com o índice: " + columnIndex + " não existe!");
	}

	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			return String.class;
		}
		throw new IndexOutOfBoundsException("A coluna com o índice: " + columnIndex + " não existe!");
	}
}
