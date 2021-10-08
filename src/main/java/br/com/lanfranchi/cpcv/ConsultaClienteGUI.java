package br.com.lanfranchi.cpcv;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.TableModel;

import br.com.lanfranchi.cpcv.model.Client;
import br.com.lanfranchi.cpcv.services.ClientService;
import br.com.lanfranchi.cpcv.tablemodels.ClientTableModel;

public class ConsultaClienteGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private ClientService clientService;
	private List<Client> linhas;
	private JButton botaoLimpar;
	private JButton botaoPesquisar;
	private JLabel jLabel1;
	private JScrollPane jScrollPane1;
	private JTextField pesquisa;
	private JTable tabela;
	private String stringPesquisa;

	protected ConsultaClienteGUI(ClientService clientService) {
		this.clientService = clientService;
		getClients(0);
		initComponents();
		configureTable();
		this.pesquisa.requestFocus();
	}

	private void initComponents() {
		this.jScrollPane1 = new JScrollPane();
		this.tabela = new JTable();
		this.jLabel1 = new JLabel();
		this.pesquisa = new JTextField();
		this.botaoLimpar = new JButton();
		this.botaoPesquisar = new JButton();

		setDefaultCloseOperation(3);
		setTitle("Clientes Cadastrados");
		setResizable(false);

		this.tabela.setFont(new Font("Tahoma", 0, 14));
		this.tabela.setModel(new ClientTableModel(linhas));
		this.tabela.setColumnSelectionAllowed(false);
		this.tabela.setRowSelectionAllowed(true);
		this.tabela.getTableHeader().setReorderingAllowed(false);
		this.tabela.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				ConsultaClienteGUI.this.tabelaMouseClicked(evt);
			}
		});
		this.jScrollPane1.setViewportView(this.tabela);
		this.tabela.getColumnModel().getSelectionModel().setSelectionMode(0);

		this.jLabel1.setFont(new Font("Tahoma", 0, 14));
		this.jLabel1.setText("Pesquisar por nome:");

		this.pesquisa.setFont(new Font("Tahoma", 0, 14));

		this.botaoLimpar.setFont(new Font("Tahoma", 0, 14));
		this.botaoLimpar.setText("LIMPAR");
		this.botaoLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ConsultaClienteGUI.this.botaoLimparActionPerformed(evt);
			}
		});

		this.botaoPesquisar.setFont(new Font("Tahoma", 0, 14));
		this.botaoPesquisar.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/pesquisar.png")));
		this.botaoPesquisar.setText("PESQUISAR");
		this.botaoPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ConsultaClienteGUI.this.botaoPesquisarActionPerformed(evt);
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addComponent(this.jLabel1)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(this.pesquisa, -2, 211, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(this.botaoPesquisar, -2, 149, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(this.botaoLimpar).addContainerGap(186, 32767))
								.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
										.addComponent(this.jScrollPane1).addContainerGap()))));

		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -2, 270, -2)
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
										.addComponent(this.botaoLimpar, -2, 49, -2).addComponent(this.botaoPesquisar))
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(this.pesquisa, -2, -1, -2).addComponent(this.jLabel1))
										.addGap(13, 13, 13)))
						.addContainerGap(-1, 32767)));

		pack();
	}

	private void botaoLimparActionPerformed(ActionEvent evt) {
		this.botaoLimpar.setEnabled(false);
		this.botaoPesquisar.setEnabled(false);
		this.tabela.setEnabled(false);

		getClients(0);
		this.tabela.setModel((TableModel) new ClientTableModel(linhas));
		configureTable();
		this.pesquisa.setText("");

		this.botaoLimpar.setEnabled(true);
		this.botaoPesquisar.setEnabled(true);
		this.tabela.setEnabled(true);
	}

	private void botaoPesquisarActionPerformed(ActionEvent evt) {
		if ("".equals(this.pesquisa.getText().trim())) {
			this.pesquisa.setText("");

			return;
		}
		this.botaoLimpar.setEnabled(false);
		this.botaoPesquisar.setEnabled(false);
		this.tabela.setEnabled(false);

		stringPesquisa = this.pesquisa.getText();
		getClients(1);
		this.tabela.setModel((TableModel) new ClientTableModel(linhas));
		configureTable();

		this.botaoLimpar.setEnabled(true);
		this.botaoPesquisar.setEnabled(true);
		this.tabela.setEnabled(true);
	}

	private void tabelaMouseClicked(MouseEvent evt) {
		int qtdCliques = evt.getClickCount();
		if (qtdCliques >= 2) {
			int indexArray = this.tabela.getSelectedRow();
			Client c = linhas.get(indexArray);
			NovoClienteGUI gui = new NovoClienteGUI(clientService, c, linhas, this.tabela);
			gui.setDefaultCloseOperation(2);
			gui.setLocationRelativeTo(this);
			gui.setVisible(true);
		}
	}

	private void getClients(int option) {
		try {
			if (option == 0) {
				linhas = clientService.listAll();
			} else if (option == 1) {
				linhas = clientService.listAll(stringPesquisa);
			}
		} catch (Exception ex) {
			ex.getMessage();
		}
	}

	private void configureTable() {
		this.tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
		this.tabela.getColumnModel().getColumn(1).setPreferredWidth(30);
		this.tabela.getColumnModel().getColumn(2).setPreferredWidth(180);
		this.tabela.getColumnModel().getColumn(3).setPreferredWidth(80);
		this.tabela.getColumnModel().getColumn(4).setPreferredWidth(80);
	}
}
