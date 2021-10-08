package br.com.lanfranchi.cpcv;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import br.com.lanfranchi.cpcv.model.Product;
import br.com.lanfranchi.cpcv.services.ProductService;
import br.com.lanfranchi.cpcv.util.FieldConverter;

public class NovoProdutoGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private ProductService productService;
	private Product c;
	private List<Product> linhas;
	private JTable tabela;
	private JButton botaoAtualizar;
	private JButton botaoCadastrar;
	private JButton botaoExcluir;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JTextField name;
	private JFormattedTextField price;

	protected NovoProdutoGUI(ProductService productService, Product c, List<Product> linhas, JTable tabela) {
		this.productService = productService;
		this.linhas = linhas;
		this.c = c;
		this.tabela = tabela;
		initComponents();
		configure();
	}
	
	private void initComponents() {
		this.jLabel1 = new JLabel();
		this.name = new JTextField();
		this.jLabel2 = new JLabel();
		this.price = FieldConverter.createField();
		this.botaoCadastrar = new JButton();
		this.botaoAtualizar = new JButton();
		this.botaoExcluir = new JButton();

		setDefaultCloseOperation(3);
		setTitle("Cadastrar Produto");
		setResizable(false);

		this.jLabel1.setFont(new Font("Tahoma", 0, 14));
		this.jLabel1.setText("Nome:");

		this.name.setFont(new Font("Tahoma", 0, 14));

		this.jLabel2.setFont(new Font("Tahoma", 0, 14));
		this.jLabel2.setText("Preço R$:");

		this.price.setFont(new Font("Tahoma", 0, 14));

		this.botaoCadastrar.setFont(new Font("Tahoma", 0, 14));
		this.botaoCadastrar.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/adicionar.png")));
		this.botaoCadastrar.setText("CADASTRAR");
		this.botaoCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				NovoProdutoGUI.this.botaoCadastrarActionPerformed(evt);
			}
		});

		this.botaoAtualizar.setFont(new Font("Tahoma", 0, 14));
		this.botaoAtualizar.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/alterar.png")));
		this.botaoAtualizar.setText("ATUALIZAR");
		this.botaoAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				NovoProdutoGUI.this.botaoAtualizarActionPerformed(evt);
			}
		});

		this.botaoExcluir.setFont(new Font("Tahoma", 0, 14));
		this.botaoExcluir.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/excluir.png")));
		this.botaoExcluir.setText("EXCLUIR");
		this.botaoExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				NovoProdutoGUI.this.botaoExcluirActionPerformed(evt);
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addContainerGap()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addComponent(this.jLabel1)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(this.name, -2, 350, -2))
										.addGroup(layout.createSequentialGroup().addComponent(this.botaoCadastrar)
												.addGap(18, 18, 18).addComponent(this.botaoAtualizar).addGap(18, 18, 18)
												.addComponent(this.botaoExcluir))
										.addGroup(layout.createSequentialGroup().addComponent(this.jLabel2)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(this.price, -2, 100, -2)))
								.addContainerGap(-1, 32767)));

		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addContainerGap()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(this.jLabel1).addComponent(this.name, -2, -1, -2))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(this.jLabel2).addComponent(this.price, -2, -1, -2))
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(this.botaoCadastrar).addComponent(this.botaoAtualizar)
										.addComponent(this.botaoExcluir))
								.addContainerGap(-1, 32767)));

		pack();
	}

	private void botaoCadastrarActionPerformed(ActionEvent evt) {
		try {
			this.botaoCadastrar.setEnabled(false);
			if ("".equals(this.name.getText().trim())) {
				JOptionPane.showMessageDialog(this, "Campo nome em branco.");
			} else {
				Product product = new Product();
				setProduct(product);
				boolean a = productService.save(product);
				if (a) {
					JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso.");
					this.name.setText("");
					this.price.setValue(0D);
				}
			}
		} catch (Exception ex) {
			if (!(ex instanceof SQLException)) {
				JOptionPane.showMessageDialog(this, "Insira valores válidos");
			}
		} finally {
			this.botaoCadastrar.setEnabled(true);
		}
	}

	private void botaoAtualizarActionPerformed(ActionEvent evt) {
		
		this.botaoAtualizar.setEnabled(false);
		this.botaoExcluir.setEnabled(false);

		try {
			if ("".equals(this.name.getText().trim())) {
				JOptionPane.showMessageDialog(this, "Campo nome em branco.");
			} else {
				setProduct(c);
				boolean a = productService.save(c);
				if (a) {
					JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso.");
					tabela.repaint();
					dispose();
				}
			}
		} catch (Exception ex) {
			ex.getMessage();
		} finally {
			this.botaoAtualizar.setEnabled(true);
			this.botaoExcluir.setEnabled(true);
		}
	}

	private void botaoExcluirActionPerformed(ActionEvent evt) {
		int x = JOptionPane.showConfirmDialog(this, "Deseja excluir o produto?", "Escolha uma opção", 0);
		if (x != 0) {
			return;
		}

		this.botaoAtualizar.setEnabled(false);
		this.botaoExcluir.setEnabled(false);

		try {
			boolean a = productService.delete(c);
			if (a) {
				linhas.remove(c);
				tabela.repaint();
				JOptionPane.showMessageDialog(this, "Produto excluído com sucesso.");
			}
			dispose();
		} catch (Exception ex) {
			ex.getMessage();
		} finally {
			this.botaoAtualizar.setEnabled(true);
			this.botaoExcluir.setEnabled(true);
		}
	}

	private void configure() {
		if (c == null) {
			this.botaoAtualizar.setEnabled(false);
			this.botaoExcluir.setEnabled(false);
		} else {
			this.botaoCadastrar.setEnabled(false);
			setTitle("Produto Cadastrado");
			setJTextField();
		}
	}

	private void setJTextField() {
		this.name.setText(c.getName());
		this.price.setValue(c.getPrice());
	}

	private void setProduct(Product product) {
		product.setName(this.name.getText());
		product.setPrice(FieldConverter.parse(price));
	}
}
