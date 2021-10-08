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

import br.com.lanfranchi.cpcv.model.Product;
import br.com.lanfranchi.cpcv.services.ProductService;
import br.com.lanfranchi.cpcv.tablemodels.ProductTableModel;

public class ConsultaProdutoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private ProductService productService;
	private List<Product> linhas;
	private JButton botaoLimpar;
	private JButton botaoPesquisar;
	private JLabel jLabel1;
	private JScrollPane jScrollPane1;
	private JTextField pesquisa;
	private JTable tabela;
	private String stringPesquisa;

	protected ConsultaProdutoGUI(ProductService productService) {
		this.productService = productService;
		getProducts(0);
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
		setTitle("Produtos Cadastrados");
		setResizable(false);

		this.tabela.setFont(new Font("Tahoma", 0, 14));
		this.tabela.setModel((TableModel) new ProductTableModel(linhas));
		this.tabela.setColumnSelectionAllowed(false);
		this.tabela.setRowSelectionAllowed(true);
		this.tabela.getTableHeader().setReorderingAllowed(false);
		this.tabela.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				ConsultaProdutoGUI.this.tabelaMouseClicked(evt);
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
				ConsultaProdutoGUI.this.botaoLimparActionPerformed(evt);
			}
		});

		this.botaoPesquisar.setFont(new Font("Tahoma", 0, 14));
		this.botaoPesquisar.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/pesquisar.png")));
		this.botaoPesquisar.setText("PESQUISAR");
		this.botaoPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ConsultaProdutoGUI.this.botaoPesquisarActionPerformed(evt);
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup().addContainerGap(-1, 32767)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
										.addComponent(this.jScrollPane1)
										.addGroup(layout.createSequentialGroup().addComponent(this.jLabel1)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(this.pesquisa, -2, 113, -2)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(this.botaoPesquisar)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(this.botaoLimpar)))
								.addContainerGap()));

		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -2, 190, -2)
				.addGap(18, 18, 18)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1)
								.addComponent(this.botaoPesquisar, -1, -1, 32767)
								.addComponent(this.pesquisa, -2, -1, -2))
						.addComponent(this.botaoLimpar, -1, -1, 32767))
				.addContainerGap(-1, 32767)));

		pack();
	}

	private void botaoLimparActionPerformed(ActionEvent evt) {
		this.botaoLimpar.setEnabled(false);
		this.botaoPesquisar.setEnabled(false);
		this.tabela.setEnabled(false);

		getProducts(0);
		this.tabela.setModel((TableModel) new ProductTableModel(linhas));
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
		getProducts(1);
		this.tabela.setModel((TableModel) new ProductTableModel(linhas));
		configureTable();

		this.botaoLimpar.setEnabled(true);
		this.botaoPesquisar.setEnabled(true);
		this.tabela.setEnabled(true);
	}

	private void tabelaMouseClicked(MouseEvent evt) {
		int qtdCliques = evt.getClickCount();
		if (qtdCliques >= 2) {
			int indexArray = this.tabela.getSelectedRow();
			Product c = linhas.get(indexArray);
			NovoProdutoGUI gui = new NovoProdutoGUI(productService, c, linhas, this.tabela);
			gui.setDefaultCloseOperation(2);
			gui.setLocationRelativeTo(this);
			gui.setVisible(true);
		}
	}

	private void getProducts(int option) {
		try {
			if (option == 0) {
				linhas = productService.listAll();
			} else if (option == 1) {
				linhas = productService.listAll(stringPesquisa);
			}
		} catch (Exception ex) {
			ex.getMessage();
		}
	}

	private void configureTable() {
		this.tabela.getColumnModel().getColumn(0).setPreferredWidth(200);
		this.tabela.getColumnModel().getColumn(1).setPreferredWidth(10);
	}
}
