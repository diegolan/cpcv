package br.com.lanfranchi.cpcv.tablemodels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.lanfranchi.cpcv.model.Client;

public class ClientTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private List<Client> linhas;
	private String[] colunas = new String[] { "Nome:", "Telefone:", "Endereço:", "Complemento:", "Bairro:" };

	public ClientTableModel(List<Client> linhas) {
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
		Client client = this.linhas.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return client.getName();
		case 1:
			return client.getPhone();
		case 2:
			return client.getStreet();
		case 3:
			return client.getNumber();
		case 4:
			return client.getDistrict();
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
