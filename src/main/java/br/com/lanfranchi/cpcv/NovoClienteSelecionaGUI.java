package br.com.lanfranchi.cpcv;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import br.com.lanfranchi.cpcv.model.Client;

public class NovoClienteSelecionaGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private Client c;
	private JButton botaoCadastrar;

	protected NovoClienteSelecionaGUI(Client c) {
		this.c = c;
		initComponents();
		setJTextField();
	}

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
		this.botaoCadastrar.setText("SELECIONAR");
		this.botaoCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				NovoClienteSelecionaGUI.this.botaoCadastrarActionPerformed(evt);
			}
		});

		this.cep.setFont(new Font("Tahoma", 0, 14));

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
						.addGroup(layout.createSequentialGroup().addComponent(this.botaoCadastrar).addGap(0, 0, 32767))
						.addGroup(layout.createSequentialGroup().addComponent(this.jLabel6)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.city)
								.addGap(18, 18, 18).addComponent(this.jLabel7)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(this.cep, -2, 125, -2)))
				.addContainerGap()));

		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2)
								.addComponent(this.name, -2, -1, -2))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4)
								.addComponent(this.street, -2, -1, -2))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1)
								.addComponent(this.number, -2, -1, -2))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5)
								.addComponent(this.district, -2, -1, -2).addComponent(this.jLabel3)
								.addComponent(this.phone, -2, -1, -2))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6)
								.addComponent(this.city, -2, -1, -2).addComponent(this.jLabel7)
								.addComponent(this.cep, -2, -1, -2))
						.addGap(18, 18, 18).addComponent(this.botaoCadastrar).addContainerGap(-1, 32767)));

		pack();
	}

	private void botaoCadastrarActionPerformed(ActionEvent evt) {
		NovoPedidoGUI.setClientVO(c);
		dispose();
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
}
