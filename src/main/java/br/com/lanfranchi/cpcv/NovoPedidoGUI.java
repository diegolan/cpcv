package br.com.lanfranchi.cpcv;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.TableModel;

import br.com.lanfranchi.cpcv.model.Client;
import br.com.lanfranchi.cpcv.model.Configurations;
import br.com.lanfranchi.cpcv.model.Item;
import br.com.lanfranchi.cpcv.model.ItemHistory;
import br.com.lanfranchi.cpcv.model.Order;
import br.com.lanfranchi.cpcv.model.Product;
import br.com.lanfranchi.cpcv.services.ClientService;
import br.com.lanfranchi.cpcv.services.ConfigurationsService;
import br.com.lanfranchi.cpcv.services.OrderService;
import br.com.lanfranchi.cpcv.services.ProductService;
import br.com.lanfranchi.cpcv.tablemodels.ItemTableModel;
import br.com.lanfranchi.cpcv.tablemodels.ProductTableModel;
import br.com.lanfranchi.cpcv.util.ImprimirUtil;
import br.com.lanfranchi.cpcv.util.FieldConverter;

public class NovoPedidoGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private ClientService clientService;
	private ProductService productService;
	private OrderService orderService;
	private ConfigurationsService configurationsService;
	private static Client cliente;
	private List<Product> linhas;
	private static List<Item> items;
	private JButton botaoFinalizar;
	private JButton botaoLimpar;
	private JButton botaoPesquisar;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JSeparator jSeparator1;
	private JLabel labelCliente;
	private JLabel labelTroco;
	private JTextField pesquisa;
	private JTable tabela;
	private static JTable tabelaItems;
	private static JTextField textCliente;
	private static JFormattedTextField totalField;
	private static String stringPesquisa;
	
	private JRadioButton radioComendo;
	private JRadioButton radioEntrega;
	private JRadioButton radioLocal;
	private JRadioButton radioViagem;
	private ButtonGroup orderType;
	
	private JFormattedTextField money;
	private JRadioButton radioDebit;
	private JRadioButton radioCredit;
	private JRadioButton radioMoney;
	private ButtonGroup paymentType;

	protected NovoPedidoGUI(ClientService clientService, ProductService productService, OrderService orderService, ConfigurationsService configurationsService) {
		this.clientService = clientService;
		this.productService = productService;
		this.orderService = orderService;
		this.configurationsService = configurationsService;
		items = new ArrayList<>();
		getProducts(0);
		initComponents();
		configureTable();
		this.pesquisa.requestFocus();
		this.labelTroco.setVisible(false);
		this.labelCliente.setVisible(false);
		textCliente.setVisible(false);
		checkImprimir();
	}

	private void initComponents() {
		
		this.jScrollPane1 = new JScrollPane();
		this.tabela = new JTable();
		this.jLabel1 = new JLabel();
		this.pesquisa = new JTextField();
		this.botaoLimpar = new JButton();
		this.botaoPesquisar = new JButton();
		this.jSeparator1 = new JSeparator();
		this.jScrollPane2 = new JScrollPane();
		tabelaItems = new JTable();
		this.jLabel2 = new JLabel();
		totalField = FieldConverter.createField();
		this.botaoFinalizar = new JButton();
		this.labelTroco = new JLabel();
		this.labelCliente = new JLabel();
		textCliente = new JTextField();
		
		this.radioLocal = new JRadioButton("Local", true);
		this.radioComendo = new JRadioButton("Comendo");
		this.radioViagem = new JRadioButton("Viagem");
		this.radioEntrega = new JRadioButton("Entrega");
		this.orderType = new ButtonGroup();
		this.orderType.add(radioLocal);
		this.orderType.add(radioComendo);
		this.orderType.add(radioViagem);
		this.orderType.add(radioEntrega);
		
		this.money = FieldConverter.createField();
		this.moneyDisabled();
		this.radioDebit = new JRadioButton("Debito", true);
		this.radioDebit.addActionListener(e-> this.moneyDisabled());
		this.radioCredit = new JRadioButton("Credito");
		this.radioCredit.addActionListener(e-> this.moneyDisabled());
		this.radioMoney = new JRadioButton("Dinheiro");
		this.radioMoney.addActionListener(e-> {
			this.money.setEnabled(true);
			this.money.requestFocus();
		});
		this.paymentType = new ButtonGroup();
		this.paymentType.add(radioDebit);
		this.paymentType.add(radioCredit);
		this.paymentType.add(radioMoney);
		
		setDefaultCloseOperation(3);
		setTitle("Novo Pedido");
		setResizable(false);

		this.tabela.setFont(new Font("Tahoma", 0, 14));
		this.tabela.setModel((TableModel) new ProductTableModel(linhas));
		this.tabela.setColumnSelectionAllowed(false);
		this.tabela.setRowSelectionAllowed(true);
		this.tabela.getTableHeader().setReorderingAllowed(false);
		this.tabela.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				NovoPedidoGUI.this.tabelaMouseClicked(evt);
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
				NovoPedidoGUI.this.botaoLimparActionPerformed(evt);
			}
		});

		this.botaoPesquisar.setFont(new Font("Tahoma", 0, 14));
		this.botaoPesquisar.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/pesquisar.png")));
		this.botaoPesquisar.setText("PESQUISAR");
		this.botaoPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				NovoPedidoGUI.this.botaoPesquisarActionPerformed(evt);
			}
		});

		tabelaItems.setFont(new Font("Tahoma", 0, 14));
		tabelaItems.setModel((TableModel) new ItemTableModel(items));
		tabelaItems.setColumnSelectionAllowed(false);
		tabelaItems.setRowSelectionAllowed(true);
		tabelaItems.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				NovoPedidoGUI.this.tabelaItemsMouseClicked(evt);
			}
		});
		this.jScrollPane2.setViewportView(tabelaItems);

		this.jLabel2.setFont(new Font("Tahoma", 0, 14));
		this.jLabel2.setText("Total R$:");

		totalField.setEditable(false);
		totalField.setFont(new Font("Tahoma", 0, 14));

		this.botaoFinalizar.setFont(new Font("Tahoma", 0, 14));
		this.botaoFinalizar.setText("FINALIZAR PEDIDO");
		this.botaoFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				NovoPedidoGUI.this.botaoFinalizarActionPerformed(evt);
			}
		});

		this.labelTroco.setFont(new Font("Tahoma", 0, 14));
		this.labelTroco.setText("Troco R$: ");

		this.radioLocal.addActionListener(e -> this.limpaCliente());
		
		this.radioComendo.addActionListener(e -> this.limpaCliente());

		this.radioViagem.addActionListener(e -> this.limpaCliente());
		
		this.radioEntrega.addActionListener(e -> {
			
			cliente = null;
			textCliente.setEditable(false);
			textCliente.setText("");
			
			try {
				
				if (configurationsService.get().isPrintClient()) {
					ConsultaClienteImpressaoGUI gui = new ConsultaClienteImpressaoGUI(clientService);
					gui.setDefaultCloseOperation(2);
					gui.setLocationRelativeTo(this);
					gui.setVisible(true);
				}
				
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(this, "Erro listando clientes.");
			}
		});

		this.labelCliente.setText("Cliente:");

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup().addContainerGap()
										.addGroup(
												layout.createParallelGroup(GroupLayout.Alignment.LEADING)
														.addGroup(layout
																.createSequentialGroup().addGroup(layout
																		.createParallelGroup(
																				GroupLayout.Alignment.TRAILING)
																		.addComponent(
																				this.jScrollPane1,
																				GroupLayout.Alignment.LEADING)
																		.addGroup(layout.createSequentialGroup()
																				.addGap(0, 0, 32767)
																				.addComponent(this.jLabel1)
																				.addPreferredGap(
																						LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(this.pesquisa, -2, 113,
																						-2)
																				.addPreferredGap(
																						LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(this.botaoPesquisar)
																				.addPreferredGap(
																						LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(this.botaoLimpar)))
																.addGap(12, 12, 12))
														.addGroup(layout.createSequentialGroup()
																.addGroup(layout
																		.createParallelGroup(
																				GroupLayout.Alignment.TRAILING)
																		.addComponent(this.jScrollPane2, -1, 584, 32767)
																		.addComponent(this.jSeparator1))
																.addContainerGap())
														.addGroup(layout.createSequentialGroup().addGroup(layout
																.createParallelGroup(GroupLayout.Alignment.LEADING)
																.addComponent(this.botaoFinalizar)
																.addGroup(layout.createSequentialGroup()
																		.addComponent(this.jLabel2)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(totalField, -2, 85, -2)
																		.addGap(18, 18, 18)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(this.radioDebit)
																		.addComponent(this.radioCredit)
																		.addComponent(this.radioMoney)
																		.addComponent(this.money)
																		.addGap(18, 18, 18))
																.addGroup(layout.createSequentialGroup()
																		.addComponent(this.labelTroco))
																.addGroup(layout.createSequentialGroup()
																		.addGap(43, 43, 43)
																		.addComponent(this.radioLocal)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(this.radioViagem)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(this.radioComendo)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(this.radioEntrega)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(this.labelCliente)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(textCliente, -2, 150, -2)))
																.addContainerGap(-1, 32767)))));

		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -2, 139, -2)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(this.botaoPesquisar, -1, -1, 32767)
										.addComponent(this.botaoLimpar, -2, 49, -2)))
						.addGroup(layout.createSequentialGroup().addGap(19, 19, 19)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.pesquisa, -2, -1, -2)
										.addGroup(layout.createSequentialGroup().addGap(3, 3, 3)
												.addComponent(this.jLabel1)))))
				.addGap(12, 12, 12).addComponent(this.jSeparator1, -2, 10, -2)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane2, -2, 140, -2)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2)
						.addComponent(totalField, -2, -1, -2)
						.addComponent(this.radioDebit)
						.addComponent(this.radioCredit)
						.addComponent(this.radioMoney)
						.addComponent(this.money, -2, -1, -2))
				.addGap(18, 18, 18)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(this.labelTroco))
				.addGap(18, 18, 18)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(this.radioLocal).addComponent(this.radioViagem).addComponent(this.radioComendo)
						.addComponent(this.radioEntrega).addComponent(this.labelCliente)
						.addComponent(textCliente, -2, -1, -2))
				.addGap(18, 18, 18).addComponent(this.botaoFinalizar).addContainerGap()));

		pack();
	}
	
	private void moneyDisabled() {
		this.money.setEnabled(false);
		this.money.setValue(0d);
	}

	private void botaoLimparActionPerformed(ActionEvent evt) {
		setCompsOff();

		getProducts(0);
		this.tabela.setModel((TableModel) new ProductTableModel(linhas));
		configureTable();
		this.pesquisa.setText("");

		setCompsOn();
	}

	private void botaoPesquisarActionPerformed(ActionEvent evt) {
		if ("".equals(this.pesquisa.getText().trim())) {
			this.pesquisa.setText("");

			return;
		}
		setCompsOff();

		stringPesquisa = this.pesquisa.getText();
		getProducts(1);
		this.tabela.setModel((TableModel) new ProductTableModel(linhas));
		configureTable();

		setCompsOn();
	}

	private void tabelaMouseClicked(MouseEvent evt) {
		int qtdCliques = evt.getClickCount();
		if (qtdCliques >= 2) {
			int indexArray = this.tabela.getSelectedRow();
			Product c = linhas.get(indexArray);
			NovoItemGUI novoItemGUI = new NovoItemGUI(c, items);
			novoItemGUI.setDefaultCloseOperation(2);
			novoItemGUI.setLocationRelativeTo(this);
			novoItemGUI.setVisible(true);
		}
	}

	private void botaoFinalizarActionPerformed(ActionEvent evt) {
		setCompsOff();

		boolean vendeu = false;
		double troco = 0.0D;
		
		try {
			
			Configurations configs = configurationsService.get();
			
			if (items.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Não há items na lista.");
			} else if (configs.isPrintClient() && !this.radioEntrega.isSelected() && "".equals(textCliente.getText().trim())) {
				JOptionPane.showMessageDialog(this, "Campo cliente em branco.");
				textCliente.requestFocus();
			} else if (configs.isPrintClient() && this.radioEntrega.isSelected() && cliente == null) {
				JOptionPane.showMessageDialog(this, "Campo cliente em branco.");
				radioLocal.setSelected(true);
				textCliente.requestFocus();
				ConsultaClienteImpressaoGUI gui = new ConsultaClienteImpressaoGUI(clientService);
				gui.setDefaultCloseOperation(2);
				gui.setLocationRelativeTo(this);
				gui.setVisible(true);
			} else {
				
				double grana = FieldConverter.parse(money);
				double total = FieldConverter.parse(totalField);
				
				if (!radioMoney.isSelected()) {
					grana = total;
				}
				
				troco = grana - total;

				if (troco < 0.0D) {
					this.money.requestFocus();
					JOptionPane.showMessageDialog(this, "A venda não pode ser realizada.\nSaldo insuficiente.");
					return;
				}
				
				Order order = new Order();
				order.setTotal(total);
				order.setCreatedAt(new Date());
				
				final List<ItemHistory> itemsHistory = new ArrayList<>();
				
				items.stream().forEach(item -> {
					ItemHistory itemHistory = new ItemHistory();
					itemHistory.setName(item.getItem().getName());
					itemHistory.setPrice(item.getItem().getPrice());
					itemHistory.setQuantity(item.getQuantity());
					itemHistory.setOrder(order);
					itemsHistory.add(itemHistory);
				});
				
				order.setItems(itemsHistory);

				orderService.save(order);

				vendeu = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Insira valores válidos.");
		} finally {
			if (vendeu) {
				if (troco == 0.0D) {
					JOptionPane.showMessageDialog(this, "Pedido cadastrado com sucesso.");
					if (radioMoney.isSelected()) {
						JOptionPane.showMessageDialog(this, "Não há troco para este pedido.");
					}
				} else {
					this.labelTroco.setText("Troco R$: " + FieldConverter.getNumberFormat().format(troco));
					this.labelTroco.setVisible(true);
					JOptionPane.showMessageDialog(this, "Pedido cadastrado com sucesso.");
					JOptionPane.showMessageDialog(this, "O troco é de R$" + FieldConverter.getNumberFormat().format(troco));
				}
				imprimirPedido();
				dispose();
			}

			setCompsOn();
		}
	}

	private void tabelaItemsMouseClicked(MouseEvent evt) {
		int qtdCliques = evt.getClickCount();
		if (qtdCliques >= 2) {
			int indexArray = tabelaItems.getSelectedRow();
			Item c = items.get(indexArray);
			NovoItemAlteraGUI novoItemAlteraGUI = new NovoItemAlteraGUI(c, items);
			novoItemAlteraGUI.setDefaultCloseOperation(2);
			novoItemAlteraGUI.setLocationRelativeTo(this);
			novoItemAlteraGUI.setVisible(true);
		}
	}

	private void checkImprimir() {
		this.radioLocal.setVisible(true);
		this.radioEntrega.setVisible(true);
		this.radioComendo.setVisible(true);
		this.radioViagem.setVisible(true);
		this.radioLocal.setSelected(true);
		this.labelCliente.setVisible(true);
		textCliente.setVisible(true);
		textCliente.setText("");
		textCliente.setEditable(true);
		cliente = null;
	}

	private void limpaCliente() {
		if (cliente != null) {
			textCliente.setText("");
			cliente = null;
		}
		textCliente.setEditable(true);
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

	private void setCompsOff() {
		this.tabela.setEnabled(false);
		this.pesquisa.setEnabled(false);
		this.botaoPesquisar.setEnabled(false);
		this.botaoLimpar.setEnabled(false);
		tabelaItems.setEnabled(false);
		this.money.setEnabled(false);
		this.botaoFinalizar.setEnabled(false);
	}

	private void setCompsOn() {
		this.tabela.setEnabled(true);
		this.pesquisa.setEnabled(true);
		this.botaoPesquisar.setEnabled(true);
		this.botaoLimpar.setEnabled(true);
		tabelaItems.setEnabled(true);
		this.money.setEnabled(true);
		this.botaoFinalizar.setEnabled(true);
	}

	protected static void rebuild() {
		double valorTotal = 0.0D;
		for (Item a : items) {
			valorTotal += a.getTotal();
		}
		tabelaItems.setModel((TableModel) new ItemTableModel(items));
		tabelaItems.repaint();
		totalField.setValue(valorTotal);
	}

	protected static void setClientVO(Client cliente) {
		if (cliente != null) {
			NovoPedidoGUI.cliente = cliente;
			textCliente.setText(cliente.getName());
		}
	}
	
	private synchronized void imprimirPedido() {
		try {

			String labelPedido = "ENTREGA";
			if (this.radioLocal.isSelected()) {
				labelPedido = "LOCAL";
			} else if (this.radioComendo.isSelected()) {
				labelPedido = "COMENDO";
			} else if (this.radioViagem.isSelected()) {
				labelPedido = "VIAGEM";
			}
			
			String labelFormaPagamento = "DEBITO";
			if (this.radioCredit.isSelected()) {
				labelFormaPagamento = "CREDITO";
			} else if (this.radioMoney.isSelected()) {
				labelFormaPagamento = "DINHEIRO";
			}

			double grana = FieldConverter.parse(money);
			double total = FieldConverter.parse(totalField);
			
			if (!radioMoney.isSelected()) {
				grana = total;
			}
			
			double troco = grana - total;

			ImprimirUtil util = new ImprimirUtil(
					cliente, 
					textCliente.getText(), 
					items, 
					labelPedido, 
					labelFormaPagamento, 
					grana, 
					total, 
					troco, 
					configurationsService.get());

			ConsultaPedidoGUI.setImprimirUtil(util);
			
			util.imprimirPedido();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao imprimir: " + e.getMessage());
		}
	}

}
