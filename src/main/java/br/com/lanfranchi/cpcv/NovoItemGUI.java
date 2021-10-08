package br.com.lanfranchi.cpcv;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import br.com.lanfranchi.cpcv.model.Item;
import br.com.lanfranchi.cpcv.model.Product;

public class NovoItemGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Product p;
	private List<Item> items;
	private JButton botaoAdicionar;
	private JLabel jLabel3;
	private JTextField quantidade;
	
	protected NovoItemGUI(Product p, List<Item> items) {
		this.p = p;
		this.items = items;
		initComponents();
		this.quantidade.requestFocus();
	}

	private void initComponents() {
		this.botaoAdicionar = new JButton();
		this.jLabel3 = new JLabel();
		this.quantidade = new JTextField();

		setDefaultCloseOperation(3);
		setTitle("Adicionar Item");
		setResizable(false);

		this.botaoAdicionar.setFont(new Font("Tahoma", 0, 14));
		this.botaoAdicionar.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/adicionar.png")));
		this.botaoAdicionar.setText("ADICIONAR");
		this.botaoAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				NovoItemGUI.this.botaoAdicionarActionPerformed(evt);
			}
		});

		this.jLabel3.setFont(new Font("Tahoma", 0, 14));
		this.jLabel3.setText("Quantidade:");

		this.quantidade.setFont(new Font("Tahoma", 0, 14));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel3)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(this.quantidade, -2, 74, -2).addGap(18, 18, 18).addComponent(this.botaoAdicionar)
						.addContainerGap(-1, 32767)));

		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3)
								.addComponent(this.quantidade, -2, -1, -2).addComponent(this.botaoAdicionar))
						.addContainerGap(-1, 32767)));

		pack();
	}

	private void botaoAdicionarActionPerformed(ActionEvent evt) {
		this.botaoAdicionar.setEnabled(false);

		try {
			if ("".equals(this.quantidade.getText().trim())) {
				JOptionPane.showMessageDialog(this, "Campo quantidade em branco.");
			} else {

				double qtd = Double.parseDouble(this.quantidade.getText().replace(",", "."));

				if (qtd <= 0.0D) {
					JOptionPane.showMessageDialog(this, "Quantidade mínima tem que ser maior do que 0.");

					return;
				}
				Item newItem = new Item();
				newItem.setItem(p);
				newItem.setQuantity(qtd);
				newItem.setTotal(newItem.getQuantity() * p.getPrice());

				items.add(newItem);

				NovoPedidoGUI.rebuild();

				dispose();
			}
		} catch (Exception e) {
			e.getMessage();
			JOptionPane.showMessageDialog(this, "Insira valores válidos.");
		} finally {
			this.botaoAdicionar.setEnabled(true);
		}
	}
}
