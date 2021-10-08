package br.com.lanfranchi.cpcv.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.lanfranchi.cpcv.model.Product;

public class ExportData {
	
	private static String SEP1 = ";;";
	private static String SEP2 = "##";
	
	private ProductService productService;
	
	public ExportData(ProductService productService) {
		this.productService = productService;
	}

	public void exportProduto() {

		try {

			List<Product> products = productService.listAll();
			Iterator<Product> iterator = products.iterator();
			StringBuffer sb = new StringBuffer();
			while (iterator.hasNext()) {
				Product p = iterator.next();
				sb.append(p.getName()).append(SEP1).append(p.getPrice());
				if (iterator.hasNext()) {
					sb.append(SEP2);
				}
			}

			if (!sb.toString().isEmpty()) {
				exportFile(sb.toString());
			} else {
				JOptionPane.showMessageDialog(null, "Não existem produtos para exportar dados.");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro de banco de dados: " + e.getMessage());
		}
	}

	public void importProduto() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("\\"));
		chooser.setFileSelectionMode(0);
		FileFilter filter = new FileNameExtensionFilter("Arquivo de Texto", new String[] { "txt" });
		chooser.setFileFilter(filter);
		int retrival = chooser.showSaveDialog(null);
		if (retrival == 0) {

			File file = null;
			FileReader fr = null;
			BufferedReader br = null;

			try {
				file = new File(chooser.getSelectedFile().toString());
				fr = new FileReader(file);
				br = new BufferedReader(fr);

				String content = null;
				StringBuffer sb = new StringBuffer();
				while ((content = br.readLine()) != null) {
					sb.append(content);
				}

				if (!sb.toString().isEmpty()) {

					try {

						String[] produtos = sb.toString().split(SEP2);
						for (String produto : produtos) {
							String[] dados = produto.split(SEP1);
							Product p = new Product();
							p.setName(dados[0]);
							p.setPrice((new Double(dados[1])).doubleValue());
							productService.save(p);
						}

						JOptionPane.showMessageDialog(null, "Produtos importados com sucesso.");
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Erro importando produtos. " + e.getMessage());
					}
				} else {

					JOptionPane.showMessageDialog(null, "Nenhum produto para importar.");
				}

			} catch (Exception ex) {
				Logger.getLogger(ExportData.class.getName()).log(Level.SEVERE, (String) null, ex);
				JOptionPane.showMessageDialog(null, "Erro de geração de arquivo" + ex.getMessage());
			} finally {
				if (fr != null) {
					try {
						fr.close();
					} catch (IOException ex) {
						Logger.getLogger(ExportData.class.getName()).log(Level.SEVERE, (String) null, ex);
					}
				}
				if (br != null) {
					try {
						br.close();
					} catch (IOException ex) {
						Logger.getLogger(ExportData.class.getName()).log(Level.SEVERE, (String) null, ex);
					}
				}
			}
		}
	}

	private void exportFile(String content) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("\\"));
		FileFilter filter = new FileNameExtensionFilter("Arquivo de Texto", new String[] { "txt" });
		chooser.setFileFilter(filter);
		int retrival = chooser.showSaveDialog(null);
		if (retrival == 0) {

			File file = null;
			FileOutputStream fos = null;

			try {
				file = new File(chooser.getSelectedFile() + ".txt");
				fos = new FileOutputStream(file);

				if (!file.exists()) {
					file.createNewFile();
				}

				byte[] contentInBytes = content.getBytes();

				fos.write(contentInBytes);
				fos.flush();

				JOptionPane.showMessageDialog(null,
						"Produtos exportados com sucesso para: " + chooser.getSelectedFile() + ".txt");
			} catch (Exception ex) {
				Logger.getLogger(ExportData.class.getName()).log(Level.SEVERE, (String) null, ex);
				JOptionPane.showMessageDialog(null, "Erro de geração de arquivo" + ex.getMessage());
			} finally {
				if (fos != null)
					try {
						fos.close();
					} catch (IOException ex) {
						Logger.getLogger(ExportData.class.getName()).log(Level.SEVERE, (String) null, ex);
					}
			}
		}
	}
}
