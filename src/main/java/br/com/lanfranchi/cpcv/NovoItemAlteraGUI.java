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

public class NovoItemAlteraGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Item p;
	private List<Item> items;
	private JButton botaoAtualizar;
	private JButton botaoExcluir;
	private JLabel jLabel3;
	private JTextField quantidade;
	
	protected NovoItemAlteraGUI(Item p, List<Item> items) {
		this.p = p;
		this.items = items;
		initComponents();
		this.quantidade.requestFocus();
	}

	private void initComponents() {
		this.botaoAtualizar = new JButton();
		this.botaoExcluir = new JButton();
		this.jLabel3 = new JLabel();
		this.quantidade = new JTextField();

		setDefaultCloseOperation(3);
		setTitle("Alterar Item");
		setResizable(false);

		this.botaoAtualizar.setFont(new Font("Tahoma", 0, 14));
		this.botaoAtualizar.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/alterar.png")));
		this.botaoAtualizar.setText("ATUALIZAR");
		this.botaoAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				NovoItemAlteraGUI.this.botaoAtualizarActionPerformed(evt);
			}
		});

		this.botaoExcluir.setFont(new Font("Tahoma", 0, 14));
		this.botaoExcluir.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/excluir.png")));
		this.botaoExcluir.setText("EXCLUIR");
		this.botaoExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				NovoItemAlteraGUI.this.botaoExcluirActionPerformed(evt);
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
						.addComponent(this.quantidade, -2, 70, -2)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.botaoAtualizar)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.botaoExcluir)
						.addContainerGap(-1, 32767)));

		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE, false)
								.addComponent(this.botaoAtualizar, -1, -1, 32767)
								.addComponent(this.botaoExcluir, -1, -1, 32767))
						.addContainerGap(-1, 32767))
				.addGroup(GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup().addContainerGap(-1, 32767)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(this.jLabel3).addComponent(this.quantidade, -2, -1, -2))
								.addContainerGap(-1, 32767)));

		pack();
	}

	private void botaoExcluirActionPerformed(ActionEvent evt) {
		int x = JOptionPane.showConfirmDialog(this, "Deseja excluir o item?", "Escolha uma opção", 0);
		if (x != 0) {
			return;
		}

		this.botaoAtualizar.setEnabled(false);
		this.botaoExcluir.setEnabled(false);

		items.remove(p);
		NovoPedidoGUI.rebuild();
		dispose();

		this.botaoAtualizar.setEnabled(true);
		this.botaoExcluir.setEnabled(true);
	}

	private void botaoAtualizarActionPerformed(ActionEvent evt) {
		this.botaoAtualizar.setEnabled(false);
		this.botaoExcluir.setEnabled(false);

		try {
			if ("".equals(this.quantidade.getText().trim())) {
				JOptionPane.showMessageDialog(this, "Campo quantidade em branco.");
			} else {

				double qtd = Double.parseDouble(this.quantidade.getText().replace(",", "."));

				if (qtd <= 0.0D) {
					JOptionPane.showMessageDialog(this, "Quantidade mínima tem que ser maior do que 0.");

					return;
				}
				int x = JOptionPane.showConfirmDialog(this, "Deseja atualizar o item?", "Escolha uma opção", 0);
				if (x != 0) {
					return;
				}

				p.setQuantity(qtd);
				p.setTotal(qtd * p.getItem().getPrice());

				NovoPedidoGUI.rebuild();
				dispose();
			}
		} catch (Exception e) {
			e.getMessage();
			JOptionPane.showMessageDialog(this, "Insira valores válidos.");
		} finally {
			this.botaoAtualizar.setEnabled(true);
			this.botaoExcluir.setEnabled(true);
		}
	}
}
