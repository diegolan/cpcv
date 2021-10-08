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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import br.com.lanfranchi.cpcv.model.Client;
import br.com.lanfranchi.cpcv.services.ClientService;

public class NovoClienteGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private ClientService clientService;
	private Client c;
	private List<Client> linhas;
	private JTable tabela;
	private JButton botaoAtualizar;
	private JButton botaoCadastrar;

	protected NovoClienteGUI(ClientService clientService, Client c, List<Client> linhas, JTable tabela) {
		this.clientService = clientService;
		this.linhas = linhas;
		this.c = c;
		this.tabela = tabela;
		initComponents();
		configure();
	}

	private JButton botaoExcluir;
	private JTextField cep;
	private JTextField city;
	private JTextField district;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JTextField name;
	private JTextField number;
	private JTextField phone;
	private JTextField street;

	private void initComponents() {
		this.jLabel2 = new JLabel();
		this.name = new JTextField();
		this.jLabel3 = new JLabel();
		this.phone = new JTextField();
		this.jLabel4 = new JLabel();
		this.street = new JTextField();
		this.jLabel1 = new JLabel();
		this.number = new JTextField();
		this.jLabel5 = new JLabel();
		this.district = new JTextField();
		this.jLabel6 = new JLabel();
		this.city = new JTextField();
		this.jLabel7 = new JLabel();
		this.botaoCadastrar = new JButton();
		this.cep = new JTextField();
		this.botaoAtualizar = new JButton();
		this.botaoExcluir = new JButton();

		setDefaultCloseOperation(3);
		setTitle("Cadastrar Cliente");
		setResizable(false);

		this.jLabel2.setFont(new Font("Tahoma", 0, 14));
		this.jLabel2.setText("Nome:");

		this.name.setFont(new Font("Tahoma", 0, 14));

		this.jLabel3.setFont(new Font("Tahoma", 0, 14));
		this.jLabel3.setText("Telefone:");

		this.phone.setFont(new Font("Tahoma", 0, 14));

		this.jLabel4.setFont(new Font("Tahoma", 0, 14));
		this.jLabel4.setText("Endereço:");

		this.street.setFont(new Font("Tahoma", 0, 14));

		this.jLabel1.setFont(new Font("Tahoma", 0, 14));
		this.jLabel1.setText("Complemento:");

		this.number.setFont(new Font("Tahoma", 0, 14));

		this.jLabel5.setFont(new Font("Tahoma", 0, 14));
		this.jLabel5.setText("Bairro:");

		this.district.setFont(new Font("Tahoma", 0, 14));

		this.jLabel6.setFont(new Font("Tahoma", 0, 14));
		this.jLabel6.setText("Cidade:");

		this.city.setFont(new Font("Tahoma", 0, 14));

		this.jLabel7.setFont(new Font("Tahoma", 0, 14));
		this.jLabel7.setText("CEP:");

		this.botaoCadastrar.setFont(new Font("Tahoma", 0, 14));
		this.botaoCadastrar.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/adicionar.png")));
		this.botaoCadastrar.setText("CADASTRAR");
		this.botaoCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				NovoClienteGUI.this.botaoCadastrarActionPerformed(evt);
			}
		});

		this.cep.setFont(new Font("Tahoma", 0, 14));

		this.botaoAtualizar.setFont(new Font("Tahoma", 0, 14));
		this.botaoAtualizar.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/alterar.png")));
		this.botaoAtualizar.setText("ATUALIZAR");
		this.botaoAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				NovoClienteGUI.this.botaoAtualizarActionPerformed(evt);
			}
		});

		this.botaoExcluir.setFont(new Font("Tahoma", 0, 14));
		this.botaoExcluir.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imgs/excluir.png")));
		this.botaoExcluir.setText("EXCLUIR");
		this.botaoExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				NovoClienteGUI.this.botaoExcluirActionPerformed(evt);
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup().addGap(0, 0, 32767).addComponent(this.jLabel2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(this.name, -2, 420, -2))
						.addGroup(layout.createSequentialGroup().addComponent(this.jLabel4)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.street))
						.addGroup(layout.createSequentialGroup().addComponent(this.jLabel1)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.number))
						.addGroup(layout.createSequentialGroup().addComponent(this.jLabel3)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.phone)
								.addGap(18, 18, 18).addComponent(this.jLabel5)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(this.district, -2, 210, -2))
						.addGroup(layout.createSequentialGroup().addComponent(this.botaoCadastrar).addGap(18, 18, 18)
								.addComponent(this.botaoAtualizar).addGap(18, 18, 18).addComponent(this.botaoExcluir)
								.addGap(0, 0, 32767))
						.addGroup(layout.createSequentialGroup().addComponent(this.jLabel6)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.city)
								.addGap(18, 18, 18).addComponent(this.jLabel7)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(this.cep, -2, 125, -2)))
				.addContainerGap()));

		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addContainerGap()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(this.jLabel2).addComponent(this.name, -2, -1, -2))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(this.jLabel4).addComponent(this.street, -2, -1, -2))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(this.jLabel1).addComponent(this.number, -2, -1, -2))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(this.jLabel5).addComponent(this.district, -2, -1, -2)
										.addComponent(this.jLabel3).addComponent(this.phone, -2, -1, -2))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(this.jLabel6).addComponent(this.city, -2, -1, -2)
										.addComponent(this.jLabel7).addComponent(this.cep, -2, -1, -2))
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(this.botaoCadastrar).addComponent(this.botaoAtualizar)
										.addComponent(this.botaoExcluir))
								.addContainerGap(-1, 32767)));

		pack();
	}

	private void botaoCadastrarActionPerformed(ActionEvent evt) {
		this.botaoCadastrar.setEnabled(false);

		try {
			if ("".equals(this.name.getText().trim())) {
				JOptionPane.showMessageDialog(this, "Campo nome em branco.");
			} else {
				Client client = new Client();
				setClient(client);
				boolean a = clientService.save(client);
				if (a) {
					JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso.");
					this.number.setText("");
					this.name.setText("");
					this.phone.setText("");
					this.street.setText("");
					this.district.setText("");
					this.city.setText("");
					this.cep.setText("");
				}
			}
		} catch (Exception ex) {
			ex.getMessage();
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
				setClient(c);
				boolean a = clientService.save(c);
				if (a) {
					JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso.");
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
		int x = JOptionPane.showConfirmDialog(this, "Deseja excluir o cliente?", "Escolha uma opção", 0);
		if (x != 0) {
			return;
		}

		this.botaoAtualizar.setEnabled(false);
		this.botaoExcluir.setEnabled(false);

		try {
			boolean a = clientService.delete(c);
			if (a) {
				linhas.remove(c);
				tabela.repaint();
				JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso.");
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
			setTitle("Cliente Cadastrado");
			setJTextField();
		}
	}

	private void setJTextField() {
		this.name.setText(c.getName());
		this.phone.setText(c.getPhone());
		this.street.setText(c.getStreet());
		this.number.setText(c.getNumber());
		this.district.setText(c.getDistrict());
		this.city.setText(c.getCity());
		this.cep.setText(c.getCep());
	}

	private void setClient(Client client) {
		client.setName(this.name.getText());
		client.setPhone(this.phone.getText());
		client.setStreet(this.street.getText());
		client.setNumber(this.number.getText());
		client.setDistrict(this.district.getText());
		client.setCity(this.city.getText());
		client.setCep(this.cep.getText());
	}
}
