package br.com.lanfranchi.cpcv.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

import org.apache.commons.lang.StringUtils;

import br.com.lanfranchi.cpcv.model.Client;
import br.com.lanfranchi.cpcv.model.Configurations;
import br.com.lanfranchi.cpcv.model.Item;

public class ImprimirUtil {
	
	private Client cliente;
	private String nomeCliente;
	private List<Item> items;
	private String labelPedido;
	private String labelFormaPagamento;
	private double grana;
	private double total;
	private double troco;
	private Configurations configs;
	private NumberFormat format = FieldConverter.getNumberFormat();
	private NumberFormat format3 = FieldConverter.getNumberFormat(3);
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	public ImprimirUtil(
			Client cliente, 
			String nomeCliente, 
			List<Item> items, 
			String labelPedido, 
			String labelFormaPagamento,
			double grana, 
			double total, 
			double troco,
			Configurations configs
			) {
		this.cliente = cliente;
		this.nomeCliente = nomeCliente;
		this.items = items;
		this.labelPedido = labelPedido;
		this.labelFormaPagamento = labelFormaPagamento;
		this.grana = grana;
		this.total = total;
		this.troco = troco;
		this.configs = configs;
	}
	
	public ImprimirUtil(
			Configurations configs
			) {
		this.configs = configs;
	}

	public void imprimirPedido() throws Exception {
		String printText = getPrintOrderText();
		PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
		DocPrintJob pj = ps.createPrintJob();
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		SimpleDoc doc = new SimpleDoc(String.valueOf(printText).getBytes(), flavor, null);
		pj.print(doc, null);
	}
	
	public void imprimirFechamento(Map<String, Double> resume) throws Exception {
		String printText = getPrintClosureText(resume);
		PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
		DocPrintJob pj = ps.createPrintJob();
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		SimpleDoc doc = new SimpleDoc(String.valueOf(printText).getBytes(), flavor, null);
		pj.print(doc, null);
	}
	
	private String getPrintOrderText() {
		
		StringBuffer sb = new StringBuffer("");
		
		for (int i = 0; i < configs.getTopSpaces(); i++) {
			sb.append("\n");
		}
		
		sb.append(padMidAjuste(configs.getName()));
		sb.append("\n");
		sb.append(padMidAjuste(configs.getTelephone()));
		sb.append("\n");
		sb.append(padMidAjuste(configs.getAddress()));
		sb.append("\n");
		
		if (configs.isPrintOrderType()) {
			
			sb.append("----------------------------------------------");
			sb.append("\n");
			sb.append("Tipo Pedido: ");
			sb.append(labelPedido);
			sb.append("\n");
		}
		
		if (configs.isPrintClient()) {
		
			sb.append("----------------------------------------------");
			sb.append("\n");
			sb.append("Cliente: ");
			
			if (cliente != null) {
	
				sb.append(cliente.getName());
				sb.append("\n");
	
				if (cliente.getPhone() != null && !cliente.getPhone().trim().equals("")
						&& cliente.getPhone().length() > 6) {
					sb.append("Telefone: ");
					sb.append(cliente.getPhone());
					sb.append("\n");
				}
	
				if (cliente.getStreet() != null && !cliente.getStreet().trim().equals("")) {
	
					sb.append("Endereco: ");
					sb.append(cliente.getStreet());
					sb.append("\n");
	
					if (cliente.getNumber() != null && !cliente.getNumber().trim().equals("")) {
						sb.append("Complemento: ");
						sb.append(cliente.getNumber());
						sb.append("\n");
					}
	
					if (cliente.getDistrict() != null && !cliente.getDistrict().trim().equals("")) {
						sb.append("Bairro: ");
						sb.append(cliente.getDistrict());
						sb.append("\n");
					}
	
					if (cliente.getCity() != null && !cliente.getCity().trim().equals("")) {
						sb.append("Cidade: ");
						sb.append(cliente.getCity());
						sb.append("\n");
					}
	
					if (cliente.getCep() != null && !cliente.getCep().trim().equals("")) {
						sb.append("CEP: ");
						sb.append(cliente.getCep());
						sb.append("\n");
					}
	
				}
			} else {
				sb.append(nomeCliente);
				sb.append("\n");
			}
		
		}
		
		sb.append("----------------------------------------------");
		sb.append("\n");
		sb.append("ITEM                      QTD  PRECO  SUBTOTAL");
		sb.append("\n");
		sb.append("----------------------------------------------");
		sb.append("\n");

		for (Item item : items) {
			sb.append(StringUtils.rightPad(item.getItem().getName(), 22, " "));
			sb.append(StringUtils.leftPad(format3.format(item.getQuantity()), 7, " "));
			sb.append(StringUtils.leftPad(format.format(item.getItem().getPrice()), 7, " "));
			sb.append(StringUtils.leftPad(format.format(item.getItem().getPrice() * item.getQuantity()), 10, " "));
			sb.append("\n");
		}

		sb.append("----------------------------------------------");
		sb.append("\n");
		
		if (configs.isPrintPaymentType()) {
			
			sb.append("Forma Pagamento: ");
			sb.append(labelFormaPagamento);
			sb.append("\n");
		}
		
		sb.append("Total");
		sb.append(StringUtils.leftPad(": R$ " + format.format(total), 41, "."));
		sb.append("\n");

		sb.append("Dinheiro");
		sb.append(StringUtils.leftPad(": R$ " + format.format(grana), 38, "."));
		sb.append("\n");

		sb.append("Troco");
		sb.append(StringUtils.leftPad(": R$ " + format.format(troco), 41, "."));
		sb.append("\n");
		
		sb.append(padMidAjuste("Obrigado", "-"));
		
		for (int i = 0; i < configs.getBottomSpaces(); i++) {
			sb.append("\n");
		}
		
		String printText = sb.toString();
		
		System.out.println(printText);
		
		return printText;
	}
	
	private String getPrintClosureText(Map<String, Double> resume) {
		
		StringBuffer sb = new StringBuffer("");
		
		for (int i = 0; i < configs.getTopSpaces(); i++) {
			sb.append("\n");
		}
		
		sb.append(padMidAjuste("Fechamento", "-"));
		sb.append("\n");
		sb.append("Data: " + dateFormat.format(new Date()));
		sb.append("\n");
		sb.append("Hora: " + timeFormat.format(new Date()));
		sb.append("\n");
		
		sb.append("----------------------------------------------");
		sb.append("\n");
		sb.append("ITEM                                 QTD");
		sb.append("\n");
		sb.append("----------------------------------------------");
		sb.append("\n");
		
		resume.forEach((String name, Double quantity) -> {
			
			sb.append(StringUtils.rightPad(name, 30, " "));
			sb.append(StringUtils.leftPad(format3.format(quantity), 16, " "));
			sb.append("\n");
			
		});

		sb.append("----------------------------------------------");
		sb.append("\n");
		
		for (int i = 0; i < configs.getBottomSpaces(); i++) {
			sb.append("\n");
		}
		
		String printText = sb.toString();
		
		System.out.println(printText);
		
		return printText;
	}
	
	private String padMidAjuste(String texto, String ajuste) {
		if (!StringUtils.isNotEmpty(texto)) {
			return "";
		}
		return StringUtils.center(texto, 46, ajuste);
	}
	
	private String padMidAjuste(String texto) {
		return padMidAjuste(texto, " ");
	}
	
}
