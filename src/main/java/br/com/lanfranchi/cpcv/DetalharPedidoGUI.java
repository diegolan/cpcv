package br.com.lanfranchi.cpcv;

import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import br.com.lanfranchi.cpcv.model.Order;
import br.com.lanfranchi.cpcv.services.OrderService;
import br.com.lanfranchi.cpcv.tablemodels.ItemHistoryTableModel;

public class DetalharPedidoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private OrderService orderService;
	private Order order;
	private JScrollPane jScrollPane1;
	private JTable tabela;

	protected DetalharPedidoGUI(OrderService orderService, Order order) {
		this.orderService = orderService;
		this.order = order;
		initComponents();
		configureTable();
	}

	private void initComponents() {
		this.jScrollPane1 = new JScrollPane();
		this.tabela = new JTable();

		setDefaultCloseOperation(3);
		setTitle("Pedido " + order.getId());
		setResizable(false);

		this.tabela.setFont(new Font("Tahoma", 0, 14));
		this.tabela.setModel(new ItemHistoryTableModel(orderService.findAllItems(order)));
		this.tabela.setColumnSelectionAllowed(false);
		this.tabela.setRowSelectionAllowed(true);
		this.tabela.getTableHeader().setReorderingAllowed(false);
		this.jScrollPane1.setViewportView(this.tabela);
		this.tabela.getColumnModel().getSelectionModel().setSelectionMode(0);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout
										.createSequentialGroup())
								.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
										.addComponent(this.jScrollPane1).addContainerGap()))));

		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -2, 270, -2)
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER))
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE))
										.addGap(13, 13, 13)))
						.addContainerGap(-1, 32767)));

		pack();
	}

	private void configureTable() {
		this.tabela.getColumnModel().getColumn(0).setPreferredWidth(180);
		this.tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
		this.tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
		this.tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
	}
}
