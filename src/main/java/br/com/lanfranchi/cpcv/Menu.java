package br.com.lanfranchi.cpcv;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import br.com.lanfranchi.cpcv.services.ClientService;
import br.com.lanfranchi.cpcv.services.ConfigurationsService;
import br.com.lanfranchi.cpcv.services.ExportData;
import br.com.lanfranchi.cpcv.services.OrderService;
import br.com.lanfranchi.cpcv.services.ProductService;

@SpringBootApplication
public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ConfigurationsService configurationsService;

	private JMenu menuProdutos;
	private JMenu menuClientes;
	private JMenu menuPedidos;
	private JMenuBar menuBar;
	private JMenuItem imprimirPedidos;
	private JMenuItem novoCliente;
	private JMenuItem novoProduto;
	private JMenuItem exportarProdutos;
	private JMenuItem consultarProduto;
	private JMenuItem consultarCliente;
	private JMenuItem consultarPedidos;
	private JMenuItem importarProdutos;
	private JMenuItem novoPedido;

	public Menu() {
		initComponents();
	}

	private void initComponents() {
		this.menuBar = new JMenuBar();
		this.menuProdutos = new JMenu();
		this.novoProduto = new JMenuItem();
		this.consultarProduto = new JMenuItem();
		this.exportarProdutos = new JMenuItem();
		this.importarProdutos = new JMenuItem();
		this.menuClientes = new JMenu();
		this.novoCliente = new JMenuItem();
		this.consultarCliente = new JMenuItem();
		this.menuPedidos = new JMenu();
		this.novoPedido = new JMenuItem();
		this.consultarPedidos = new JMenuItem();
		this.imprimirPedidos = new JMenuItem();
		
		setDefaultCloseOperation(3);
		setTitle("Controle de Produtos, Clientes e Vendas");
		setFont(new Font("Aharoni", 0, 36));
		
		this.menuBar.setFont(new Font("Segoe UI", 0, 18));
		
		this.menuProdutos.setText("Produtos");
		this.menuProdutos.setFont(new Font("Segoe UI", 0, 18));
		this.novoProduto.setFont(new Font("Segoe UI", 0, 18));
		this.novoProduto.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/adicionar.png")));
		this.novoProduto.setText("Novo");
		this.novoProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Menu.this.novoProdutoActionPerformed(evt);
			}
		});
		this.menuProdutos.add(this.novoProduto);
		
		this.consultarProduto.setFont(new Font("Segoe UI", 0, 18));
		this.consultarProduto.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/pesquisar.png")));
		this.consultarProduto.setText("Consultar");
		this.consultarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Menu.this.consultaProdutoActionPerformed(evt);
			}
		});
		this.menuProdutos.add(this.consultarProduto);
		
		this.exportarProdutos.setFont(new Font("Segoe UI", 0, 18));
		this.exportarProdutos.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/exportar.png")));
		this.exportarProdutos.setText("Exportar Produtos");
		this.exportarProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Menu.this.exportarProdutosActionPerformed(evt);
			}
		});
		this.menuProdutos.add(this.exportarProdutos);
		
		this.importarProdutos.setFont(new Font("Segoe UI", 0, 18));
		this.importarProdutos.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/importar.png")));
		this.importarProdutos.setText("Importar Produtos");
		this.importarProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Menu.this.importarProdutosActionPerformed(evt);
			}
		});
		this.menuProdutos.add(this.importarProdutos);
		
		this.menuBar.add(this.menuProdutos);
		
		this.menuClientes.setText("Clientes");
		this.menuClientes.setFont(new Font("Segoe UI", 0, 18));
		
		this.novoCliente.setFont(new Font("Segoe UI", 0, 18));
		this.novoCliente.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/adicionar.png")));
		this.novoCliente.setText("Novo");
		this.novoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Menu.this.novoClienteActionPerformed(evt);
			}
		});
		this.menuClientes.add(this.novoCliente);
		
		this.consultarCliente.setFont(new Font("Segoe UI", 0, 18));
		this.consultarCliente.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/pesquisar.png")));
		this.consultarCliente.setText("Consultar");
		this.consultarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Menu.this.consultaClienteActionPerformed(evt);
			}
		});
		this.menuClientes.add(this.consultarCliente);
		
		this.menuBar.add(this.menuClientes);
		
		this.menuPedidos.setBorder(null);
		this.menuPedidos.setText("Pedidos");
		this.menuPedidos.setFont(new Font("Segoe UI", 0, 18));
		
		this.novoPedido.setFont(new Font("Segoe UI", 0, 18));
		this.novoPedido.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/adicionar.png")));
		this.novoPedido.setText("Novo");
		this.novoPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Menu.this.novoPedidoActionPerformed(evt);
			}
		});
		this.menuPedidos.add(this.novoPedido);
		
		this.consultarPedidos.setFont(new Font("Segoe UI", 0, 18));
		this.consultarPedidos.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/pesquisar.png")));
		this.consultarPedidos.setText("Consultar");
		this.consultarPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Menu.this.consultarPedidosActionPerformed(evt);
			}
		});
		this.menuPedidos.add(this.consultarPedidos);
		
		this.imprimirPedidos.setFont(new Font("Segoe UI", 0, 18));
		this.imprimirPedidos.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/imprimir.png")));
		this.imprimirPedidos.setText("Imprimir");
		this.imprimirPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Menu.this.imprimirPedidoActionPerformed(evt);
			}
		});
		this.menuPedidos.add(this.imprimirPedidos);
		
		this.menuBar.add(this.menuPedidos);
		
		setJMenuBar(this.menuBar);
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 802, 32767));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 502, 32767));
		pack();
	}

	private void novoProdutoActionPerformed(ActionEvent evt) {
		NovoProdutoGUI gui = new NovoProdutoGUI(productService, null, null, null);
		gui.setDefaultCloseOperation(2);
		gui.setLocationRelativeTo(this);
		gui.setVisible(true);
	}

	private void novoClienteActionPerformed(ActionEvent evt) {
		NovoClienteGUI gui = new NovoClienteGUI(clientService, null, null, null);
		gui.setDefaultCloseOperation(2);
		gui.setLocationRelativeTo(this);
		gui.setVisible(true);
	}

	private void consultaClienteActionPerformed(ActionEvent evt) {
		ConsultaClienteGUI gui = new ConsultaClienteGUI(clientService);
		gui.setDefaultCloseOperation(2);
		gui.setLocationRelativeTo(this);
		gui.setVisible(true);
	}

	private void consultaProdutoActionPerformed(ActionEvent evt) {
		ConsultaProdutoGUI gui = new ConsultaProdutoGUI(productService);
		gui.setDefaultCloseOperation(2);
		gui.setLocationRelativeTo(this);
		gui.setVisible(true);
	}

	private void consultarPedidosActionPerformed(ActionEvent evt) {
		JLabel label = new JLabel("Digite a senha");
		JPasswordField jpf = new JPasswordField();
		int option = JOptionPane.showConfirmDialog(null, new Object[] { label, jpf }, "Vendas Cadastradas", 2);
		String senha = new String(jpf.getPassword());
		String senhaAtual = null;
		try {
			senhaAtual = configurationsService.getPassword();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Problemas: " + ex.getMessage());
			return;
		}
		if (option == 0 && senhaAtual.equals(senha) || ConfigurationsService.MASTER_PASSWORD.equals(senha)) {
			ConsultaPedidoGUI gui = new ConsultaPedidoGUI(orderService, configurationsService);
			gui.setDefaultCloseOperation(2);
			gui.setLocationRelativeTo(this);
			gui.setVisible(true);
		} else if (option == 0) {
			JOptionPane.showMessageDialog(this, "Senha inválida");
		}
	}

	private void novoPedidoActionPerformed(ActionEvent evt) {
		NovoPedidoGUI gui = new NovoPedidoGUI(clientService, productService, orderService, configurationsService);
		gui.setDefaultCloseOperation(2);
		gui.setLocationRelativeTo(this);
		gui.setVisible(true);
	}

	private void imprimirPedidoActionPerformed(ActionEvent evt) {
		if (ConsultaPedidoGUI.imprimirUtil == null) {
			JOptionPane.showMessageDialog(this, "Nenhum pedido para imprimir!");
		} else {
			ConsultaPedidoGUI.imprimirPedido();
		}
	}

	private void exportarProdutosActionPerformed(ActionEvent evt) {
		new ExportData(productService).exportProduto();
	}

	private void importarProdutosActionPerformed(ActionEvent evt) {
		new ExportData(productService).importProduto();
	}
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Menu.class).headless(false).run(args);
		
		EventQueue.invokeLater(() -> {
			Menu menu = ctx.getBean(Menu.class);
			menu.setLocationRelativeTo(null);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String data = sdf.format(new Date());
			JOptionPane.showMessageDialog(null, "Verifique a DATA e a HORA do sistema.\n\n" + data, "IMPORTANTE!", 0);
			menu.setVisible(true);
		});
	}

}
