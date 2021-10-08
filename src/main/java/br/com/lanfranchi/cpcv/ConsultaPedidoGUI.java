package br.com.lanfranchi.cpcv;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.TableModel;

import br.com.lanfranchi.cpcv.exceptions.CamposException;
import br.com.lanfranchi.cpcv.mask.MaskFactory;
import br.com.lanfranchi.cpcv.model.Configurations;
import br.com.lanfranchi.cpcv.model.Item;
import br.com.lanfranchi.cpcv.model.ItemHistory;
import br.com.lanfranchi.cpcv.model.Order;
import br.com.lanfranchi.cpcv.model.Product;
import br.com.lanfranchi.cpcv.services.ConfigurationsService;
import br.com.lanfranchi.cpcv.services.OrderService;
import br.com.lanfranchi.cpcv.tablemodels.OrderTableModel;
import br.com.lanfranchi.cpcv.util.ImprimirUtil;
import br.com.lanfranchi.cpcv.util.FieldConverter;

public class ConsultaPedidoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private OrderService orderService;
	private ConfigurationsService configurationsService;
	private List<Order> lista;
	public static ImprimirUtil imprimirUtil;
	private JButton botaoHoje;
	private JButton botaoTrocaSenha;
	private JButton botaoConfiguracoes;
	private JButton botaoImprimirPrevia;

	protected ConsultaPedidoGUI(OrderService orderService, ConfigurationsService passwordService) {
		this.orderService = orderService;
		this.configurationsService = passwordService;
		initLista(0);
		initComponents();
		configureTable();
		this.botaoHoje.requestFocus();
		if (imprimirUtil != null) {
			this.botaoImprimir.setVisible(true);
		} else {
			this.botaoImprimir.setVisible(false);
		}
	}

	private JButton botaoImprimir;
	private JButton botaoPesquisar;
	private JButton botaoTodos;
	private JFormattedTextField campoFim;
	private JFormattedTextField campoInicio;
	private JTextField campoTotal;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JScrollPane jScrollPane1;
	private JSeparator jSeparator1;
	private JSeparator jSeparator2;
	private JTable tabela;
	private Timestamp timeInicio;
	private Timestamp timeFim;

	private void initComponents() {
		this.jScrollPane1 = new JScrollPane();
		this.tabela = new JTable();
		this.jLabel1 = new JLabel();
		this.campoTotal = new JTextField();
		this.botaoHoje = new JButton();
		this.botaoTodos = new JButton();
		this.jLabel2 = new JLabel();
		this.botaoPesquisar = new JButton();
		this.jSeparator1 = new JSeparator();
		this.campoInicio = new JFormattedTextField((new MaskFactory()).getMask("##/##/####"));
		this.campoFim = new JFormattedTextField((new MaskFactory()).getMask("##/##/####"));
		this.botaoImprimir = new JButton();
		this.jSeparator2 = new JSeparator();
		this.botaoTrocaSenha = new JButton();
		this.botaoConfiguracoes = new JButton();
		this.botaoImprimirPrevia = new JButton();

		setDefaultCloseOperation(3);
		setTitle("Vendas Cadastradas");
		setResizable(false);

		this.tabela.setFont(new Font("Tahoma", 0, 14));
		this.tabela.setModel((TableModel) new OrderTableModel(lista));
		this.tabela.setColumnSelectionAllowed(false);
		this.tabela.setRowSelectionAllowed(true);
		this.tabela.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				ConsultaPedidoGUI.this.tabelaMouseClicked(evt);
			}
		});
		this.jScrollPane1.setViewportView(this.tabela);

		this.jLabel1.setFont(new Font("Tahoma", 0, 14));
		this.jLabel1.setText("Total das vendas R$:");

		this.campoTotal.setEditable(false);
		this.campoTotal.setFont(new Font("Tahoma", 0, 14));

		this.botaoHoje.setFont(new Font("Tahoma", 0, 14));
		this.botaoHoje.setText("HOJE");
		this.botaoHoje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ConsultaPedidoGUI.this.botaoHojeActionPerformed(evt);
			}
		});

		this.botaoTodos.setFont(new Font("Tahoma", 0, 14));
		this.botaoTodos.setText("TODOS");
		this.botaoTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ConsultaPedidoGUI.this.botaoTodosActionPerformed(evt);
			}
		});

		this.jLabel2.setFont(new Font("Tahoma", 0, 14));
		this.jLabel2.setText("Período:");

		this.botaoPesquisar.setFont(new Font("Tahoma", 0, 14));
		this.botaoPesquisar.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/pesquisar.png")));
		this.botaoPesquisar.setText("PESQUISAR");
		this.botaoPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ConsultaPedidoGUI.this.botaoPesquisarActionPerformed(evt);
			}
		});

		this.botaoImprimir.setFont(new Font("Tahoma", 0, 14));
		this.botaoImprimir.setText("IMPRIMIR ÚLTIMO");
		this.botaoImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ConsultaPedidoGUI.this.botaoImprimirActionPerformed(evt);
			}
		});

		this.botaoTrocaSenha.setFont(new Font("Tahoma", 0, 14));
		this.botaoTrocaSenha.setText("TROCAR SENHA ADM");
		this.botaoTrocaSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ConsultaPedidoGUI.this.botaoTrocaSenhaActionPerformed(evt);
			}
		});
		
		this.botaoConfiguracoes.setFont(new Font("Tahoma", 0, 14));
		this.botaoConfiguracoes.setText("CONFIGURACOES");
		this.botaoConfiguracoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ConsultaPedidoGUI.this.botaoConfiguracoesActionPerformed(evt);
			}
		});
		
		this.botaoImprimirPrevia.setFont(new Font("Tahoma", 0, 14));
		this.botaoImprimirPrevia.setText("IMPRIMIR FECHAMENTO");
		this.botaoImprimirPrevia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ConsultaPedidoGUI.this.botaoImprimirFechamentoActionPerformed(evt);
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.jScrollPane1, -2, 0, 32767)
						.addGroup(layout.createSequentialGroup().addComponent(this.botaoHoje)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.botaoTodos)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
								.addComponent(this.botaoImprimir))
						.addComponent(this.jSeparator2).addComponent(this.jSeparator1).addGroup(
								layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup().addComponent(this.jLabel2)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(this.campoInicio, -2, 80, -2)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(this.campoFim, -2, 80, -2)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(this.botaoPesquisar))
												.addComponent(this.botaoTrocaSenha)
												.addComponent(this.botaoConfiguracoes)
												.addComponent(this.botaoImprimirPrevia)
												.addGroup(layout.createSequentialGroup().addComponent(this.jLabel1)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(this.campoTotal, -2, 100, -2)))
										.addGap(0, 0, 32767)))
				.addContainerGap()));

		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup().addContainerGap()
										.addComponent(this.jScrollPane1, -2, 173, -2).addGap(18, 18, 18)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addComponent(this.botaoImprimir, -2, 25, -2)
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														.addComponent(this.botaoHoje, -1, -1, 32767)
														.addComponent(this.botaoTodos, -1, -1, 32767)))
										.addGap(18, 18, 18)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(this.jLabel2).addComponent(this.botaoPesquisar)
												.addComponent(this.campoInicio, -2, -1, -2)
												.addComponent(this.campoFim, -2, -1, -2))
										.addGap(18, 18, 18).addComponent(this.jSeparator2, -2, 10, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(this.botaoTrocaSenha, -1, -1, 32767).addGap(10, 10, 10)
										.addComponent(this.botaoConfiguracoes, -1, -1, 32767).addGap(10, 10, 10)
										.addComponent(this.botaoImprimirPrevia, -1, -1, 32767).addGap(10, 10, 10)
										.addComponent(this.jSeparator1, -2, 10, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(this.jLabel1).addComponent(this.campoTotal, -2, -1, -2))
										.addContainerGap()));

		pack();
	}
	
	private void tabelaMouseClicked(MouseEvent evt) {
		int qtdCliques = evt.getClickCount();
		if (qtdCliques >= 2) {
			int indexArray = this.tabela.getSelectedRow();
			Order o = lista.get(indexArray);
			DetalharPedidoGUI gui = new DetalharPedidoGUI(orderService, o);
			gui.setDefaultCloseOperation(2);
			gui.setLocationRelativeTo(this);
			gui.setVisible(true);
		}
	}

	private void botaoHojeActionPerformed(ActionEvent evt) {
		setCompsOff();
		initLista(0);
		configureTable();
		setCompsOn();
		this.botaoHoje.requestFocus();
	}

	private void botaoTodosActionPerformed(ActionEvent evt) {
		setCompsOff();
		initLista(1);
		configureTable();
		setCompsOn();
		this.botaoTodos.requestFocus();
	}

	private void botaoPesquisarActionPerformed(ActionEvent evt) {
		try {
			setCompsOff();
			camposComErros();
			initLista(2);
			configureTable();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		} finally {
			setCompsOn();
			this.botaoPesquisar.requestFocus();
		}
	}

	private void botaoImprimirActionPerformed(ActionEvent evt) {
		imprimirPedido();
	}

	private void botaoTrocaSenhaActionPerformed(ActionEvent evt) {
		JLabel labelSenhaAtual = new JLabel("Digite a senha atual.");
		JPasswordField jpfAtual = new JPasswordField();
		JLabel labelSenhaNova1 = new JLabel("Digite a nova senha.");
		JPasswordField jpfNova1 = new JPasswordField();
		JLabel labelSenhaNova2 = new JLabel("Repita a nova senha.");
		JPasswordField jpfNova2 = new JPasswordField();
		int option = JOptionPane.showConfirmDialog(this,
				new Object[] { labelSenhaAtual, jpfAtual, labelSenhaNova1, jpfNova1, labelSenhaNova2, jpfNova2 },
				"Troca senha ADM", 2);
		if (option == 0) {
			String senhaAtual = new String(jpfAtual.getPassword());
			String senhaNova1 = new String(jpfNova1.getPassword());
			String senhaNova2 = new String(jpfNova2.getPassword());
			String senhaAtualBD = null;
			try {
				senhaAtualBD = configurationsService.getPassword();
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, "Problemas: " + ex.getMessage());
				return;
			}
			if (!senhaAtualBD.equals(senhaAtual)) {
				JOptionPane.showMessageDialog(this, "Senha atual incorreta.");
				return;
			}
			if (senhaNova1.trim().length() < 4) {
				JOptionPane.showMessageDialog(this, "Senha deve ter no mínimo 4 dígitos.");
				return;
			}
			if (!senhaNova1.equals(senhaNova2)) {
				JOptionPane.showMessageDialog(this, "Senhas não conferem. Tente novamente.");
				return;
			}
			try {
				configurationsService.savePassword(senhaNova1);
				JOptionPane.showMessageDialog(this, "Senha atualizada com sucesso!");
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, "Problemas: " + ex.getMessage());
			}
		}
	}
	
	private void botaoConfiguracoesActionPerformed(ActionEvent evt) {
		
		Configurations configs = null;
		
		try {
			configs = configurationsService.get();
		} catch (SQLException e) {
			return;
		}
		
		JLabel labelName = new JLabel("Nome Empresa");
		JTextField name = new JTextField();
		name.setText(configs.getName());
		
		JLabel labelTelephone = new JLabel("Telefone");
		JTextField telephone = new JTextField();
		telephone.setText(configs.getTelephone());
		
		JLabel labelAddress = new JLabel("Endereco");
		JTextField address = new JTextField();
		address.setText(configs.getAddress());
		
		JLabel labelTopSpaces = new JLabel("Quantidade de espaços acima");
		JFormattedTextField topSpaces = FieldConverter.createIntegerField(configs.getTopSpaces());
		
		JLabel labelBottomSpaces = new JLabel("Quantidade de espaço abaixo");
		JFormattedTextField bottomSpaces = FieldConverter.createIntegerField(configs.getBottomSpaces());
		
		JLabel labelPrintClient = new JLabel("Imprimir Cliente");
		JCheckBox printClient = new JCheckBox();
		printClient.setSelected(configs.isPrintClient());
		
		JLabel labelPrintOrderType = new JLabel("Imprimir Tipo Pedido");
		JCheckBox printOrderType = new JCheckBox();
		printOrderType.setSelected(configs.isPrintOrderType());
		
		JLabel labelPrintPaymentType = new JLabel("Imprimir Forma de Pagamento");
		JCheckBox printPaymentType = new JCheckBox();
		printPaymentType.setSelected(configs.isPrintPaymentType());
		
		JButton printTest = new JButton();
		printTest.setText("Teste de Impressao");
		printTest.addActionListener(e -> {
			
			Product p1 = new Product();
			p1.setName("Produto Teste");
			p1.setPrice(10);
			p1.setId(1);
			
			Item item1 = new Item();
			item1.setQuantity(1);
			item1.setTotal(10);
			item1.setItem(p1);
			
			Configurations mock = new Configurations();
			mock.setName(name.getText());
			mock.setTelephone(telephone.getText());
			mock.setAddress(address.getText());
			mock.setPrintClient(printClient.isSelected());
			mock.setPrintOrderType(printOrderType.isSelected());
			mock.setPrintPaymentType(printPaymentType.isSelected());
			mock.setTopSpaces(FieldConverter.parseInteger(topSpaces));
			mock.setBottomSpaces(FieldConverter.parseInteger(bottomSpaces));
			
			try {
				new ImprimirUtil(
						null, 
						"Fulano", 
						Arrays.asList(item1) , 
						"LOCAL",
						"DEBITO",
						10, 
						9, 
						1,
						mock)
						.imprimirPedido();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro na comunicacao com a impressora.");
			}
		});
		
		int option = JOptionPane.showConfirmDialog(this,
				new Object[] { 
						labelName,
						name,
						labelTelephone,
						telephone,
						labelAddress,
						address,
						labelTopSpaces,
						topSpaces,
						labelBottomSpaces,
						bottomSpaces,
						labelPrintClient,
						printClient,
						labelPrintOrderType,
						printOrderType,
						labelPrintPaymentType,
						printPaymentType,
						printTest
					},
				"Configuracoes", 2);
		
		if (option == 0) {
			
			configs.setName(name.getText());
			configs.setTelephone(telephone.getText());
			configs.setAddress(address.getText());
			configs.setPrintClient(printClient.isSelected());
			configs.setPrintOrderType(printOrderType.isSelected());
			configs.setPrintPaymentType(printPaymentType.isSelected());
			configs.setTopSpaces(FieldConverter.parseInteger(topSpaces));
			configs.setBottomSpaces(FieldConverter.parseInteger(bottomSpaces));
			
			try {
				configurationsService.saveConfigurations(configs);
				JOptionPane.showMessageDialog(this, "Configuracoes atualizadas com sucesso!");
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, "Problemas: " + ex.getMessage());
			}
		}
	}
	
	private void botaoImprimirFechamentoActionPerformed(ActionEvent evt) {
		
		try {
			
			Map<String, Double> resume = new HashMap<>();
			
			for (Order order : orderService.getOrdersDay()) {
				for (ItemHistory itemHistory : orderService.findAllItems(order)) {
					if (resume.containsKey(itemHistory.getName())) {
						Double quantity = resume.get(itemHistory.getName());
						quantity += itemHistory.getQuantity();
						resume.put(itemHistory.getName(), quantity);
					} else {
						resume.put(itemHistory.getName(), itemHistory.getQuantity());
					}
				}
			}
			
			new ImprimirUtil(configurationsService.get()).imprimirFechamento(resume);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Problemas: " + e.getMessage());
		}
	}

	private void initLista(int opcao) {
		try {
			switch (opcao) {
			case 1:
				lista = orderService.getOrders();
				break;
			case 2:
				lista = orderService.getOrders(this.timeInicio, this.timeFim);
				break;
			default:
				lista = orderService.getOrdersDay();
				break;
			}
		} catch (Exception ex) {
			ex.getMessage();
			lista = new ArrayList<>();
		}
	}

	private void setCompsOff() {
		this.botaoHoje.setEnabled(false);
		this.botaoTodos.setEnabled(false);
		this.botaoPesquisar.setEnabled(false);
		this.campoInicio.setEnabled(false);
		this.campoFim.setEnabled(false);
	}

	private void setCompsOn() {
		this.botaoHoje.setEnabled(true);
		this.botaoTodos.setEnabled(true);
		this.botaoPesquisar.setEnabled(true);
		this.campoInicio.setEnabled(true);
		this.campoFim.setEnabled(true);
	}

	private void configureTable() {
		this.tabela.setModel((TableModel) new OrderTableModel(lista));
		this.tabela.repaint();
		this.tabela.getColumnModel().getColumn(0).setPreferredWidth(200);
		this.tabela.getColumnModel().getColumn(1).setPreferredWidth(10);
		double total = 0.0D;
		for (Order a : lista) {
			total += a.getTotal();
		}
		this.campoTotal.setText(String.format("%,.2f", new Object[] { Double.valueOf(total) }));
	}

	private void camposComErros() throws CamposException {
		String[] splitInicio = this.campoInicio.getText().split("/");
		String[] splitFim = this.campoFim.getText().split("/");

		for (int i = 0; i < 3; i++) {
			if (splitInicio[i].contains("_") || splitFim[i].contains("_")) {
				throw new CamposException("Informe valores válidos no formato dd/MM/aaaa.");
			}
		}

		int diaInicio = Integer.parseInt(splitInicio[0]);
		int diaFim = Integer.parseInt(splitFim[0]);

		if (diaInicio <= 0 || diaFim <= 0 || diaInicio > 31 || diaFim > 31) {
			throw new CamposException("Informe um dia entre 1 e 31.");
		}

		int mesInicio = Integer.parseInt(splitInicio[1]);
		int mesFim = Integer.parseInt(splitFim[1]);

		if (mesInicio <= 0 || mesFim <= 0 || mesInicio > 12 || mesFim > 12) {
			throw new CamposException("Informe um mês entre 1 e 12.");
		}

		int anoInicio = Integer.parseInt(splitInicio[2]);
		int anoFim = Integer.parseInt(splitFim[2]);

		GregorianCalendar dataInicio = new GregorianCalendar(anoInicio, mesInicio - 1, diaInicio, 0, 0, 0);
		GregorianCalendar dataFinal = new GregorianCalendar(anoFim, mesFim - 1, diaFim, 23, 59, 59);

		this.timeInicio = new Timestamp(dataInicio.getTimeInMillis());
		this.timeFim = new Timestamp(dataFinal.getTimeInMillis());

		if (this.timeFim.getTime() < this.timeInicio.getTime()) {
			throw new CamposException("Período inválido.");
		}
	}

	public static void setImprimirUtil(ImprimirUtil imprimirUtil) {
		ConsultaPedidoGUI.imprimirUtil = imprimirUtil;
	}

	public static synchronized void imprimirPedido() {
		try {
			imprimirUtil.imprimirPedido();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao imprimir: " + e.getMessage());
		}
	}
}
