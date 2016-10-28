package lab14;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuBar;

import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.Checkbox;
import java.awt.List;
import java.awt.Choice;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.Label;

import javax.swing.JCheckBoxMenuItem;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;

public class MyFrame extends JFrame {

	private JPanel contentPane;
	final JButton btnOk = new JButton("Ok");
	File file;

	/**
	 * Launch the application.
	 */
	public static void writeFile(String fileName, String text) {
		// Определяем файл
		File file = new File(fileName);

		try {
			// если файла нет, то создаем его
			if (!file.exists()) {
				file.createNewFile();
			}

			// определяем переменную out типа PrintWriter, и создаем экземпляр
			// объекта
			PrintWriter out = new PrintWriter(file.getAbsoluteFile());

			try {
				// с помощью метода print записываем строку text в файл
				out.print(text);
			} finally {
				// закрываем файл, если не закрыть, то данные не запишутся
				out.close();
			}
		} catch (IOException ewr) {

			System.out.println(ewr.getMessage());
		}
	}

	public static ArrayList<String> readFile(File fileName) {

		ArrayList<String> stringList = new ArrayList<>();
		// использование try-with-resources
		try (BufferedReader reader = new BufferedReader(
				new FileReader(fileName))) {
			String line;
			while ((line = reader.readLine()) != null) {

				stringList.add(line);
			}

			reader.close();
		} catch (IOException e) {

			System.out.println(e.getMessage());
		}
		return stringList;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFrame frame = new MyFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyFrame() {
		setTitle("Lab14");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 600);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("OptionPane.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnOk.setEnabled(false);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (file.isFile()) {

					if (file != null) {
						Calculation variableCalculation = new Calculation(1);
						ArrayList<Double> doubleList = new ArrayList<>();

						ArrayList<String> stringList = new ArrayList<>();
						// переменная stringList хранит список строк прочитанных
						// из
						// файла in.txt
						stringList = readFile(file);
						// System.out.println(stringList);
						// в переменную sb записываем все полученные результаты
						StringBuilder sb = new StringBuilder();
						// пробегаем по все му списку stringList(каждый элемент
						// списка содержит
						// одну строку)
						for (int i = 0; i < stringList.size(); i++) {

							// все числа в строке записываем отдельно в массив
							// isParts
							String[] isParts = stringList.get(i).split(" ");
							// пробегаем по всему массиву isParts и преобразуем
							// в
							// Double и
							// добавляем в список doubleList
							for (int j = 0; j < isParts.length; j++) {
								doubleList.add(j,
										Double.parseDouble(isParts[j]));

							}
							// минимальное значение аргумента х которое
							// находится в
							// списке
							// doubleList по индексу 4
							Double x = doubleList.get(4);
							Double y = 0.0d; // переменная, значение вычисленной
												// функции
							// выполняем пока аргумент х <= максимальному
							// аргументу
							// х
							while (x <= doubleList.get(5)) {
								// вычисляем уравнение
								y = variableCalculation.calculateFunction(
										doubleList.get(0), doubleList.get(1),
										doubleList.get(2), doubleList.get(3), x);
								// выводим значения уравнения для всег
								// аргументов от
								// минимального до максимального значения с
								// шагом
								// doubleList.get(6)
								// выводим с точностью 2 знака после запятой
								System.out.printf("%1$.2f", y);
								System.out.print(" ");
								// в переменную sb класса StringBuilder помещаем
								// значение
								// уравнения через пробел
								sb.append(String.format("%.2f ", y) + " ");
								// прибавляем к начальному значению аргумента
								// шаг
								// doubleList.get(6)
								x = x + doubleList.get(6);

							}
							// переводим на новую строку рассчет значений
							// функции
							// для других
							// коэффициентов
							System.out.print("\n");
							// в переменную sb класса StringBuilder символ
							// окончания
							// строки
							sb.append("\n");
						}
						// записываем в файл out.txt полученные результаты с
						// помощью
						// метода writeFile,
						// который сам обрабатывает исключения
						MyFrame.writeFile("src/out.txt", sb.toString());
						// записываем полученные результаты в файл out.txt,

					} else {
						System.out.println("Файл не выбран!!!");
					}
				}
				if (file.isDirectory()) {

					Path testFilePath = Paths.get(file.getAbsolutePath());

					for (Path element : testFilePath) {
						System.out.println("\t path element: " + element);
					}

					DefaultMutableTreeNode books = new DefaultMutableTreeNode(
							testFilePath.getRoot());

					// Three Departments
					DefaultMutableTreeNode fiction = new DefaultMutableTreeNode(
							"Fiction");
					DefaultMutableTreeNode nonfiction = new DefaultMutableTreeNode(
							"Non-Fiction");
					DefaultMutableTreeNode biography = new DefaultMutableTreeNode(
							"Biography");

					// Fiction Books
					fiction.add(new DefaultMutableTreeNode("Moby Dick"));
					fiction.add(new DefaultMutableTreeNode("MacBeth"));
					fiction.add(new DefaultMutableTreeNode("War and Peace"));

					// Non Fiction Books
					nonfiction.add(new DefaultMutableTreeNode("Unbroken"));
					nonfiction.add(new DefaultMutableTreeNode(
							"The Diary of a Young Girl"));
					nonfiction.add(new DefaultMutableTreeNode("The Prince"));

					// Biography Books
					biography.add(new DefaultMutableTreeNode("John Adams"));
					biography.add(new DefaultMutableTreeNode("Steve Jobs"));

					books.add(fiction);
					books.add(nonfiction);
					books.add(biography);

					JTree tree_1 = new JTree(books);
					tree_1.setBounds(10, 110, 420, 424);

					contentPane.add(tree_1);
					contentPane.updateUI();
				}
			}
		});

		btnOk.setBounds(131, 25, 97, 25);
		contentPane.add(btnOk);
		JButton selectFileButton = new JButton("Select File");
		selectFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileopen = new JFileChooser();

				fileopen.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

				int ret = fileopen.showDialog(null, "Открыть");
				if (ret == JFileChooser.APPROVE_OPTION) {

					file = fileopen.getSelectedFile();

					// void setDialogType(int dialogType)
					if (file.isDirectory()) {
						System.out.println("Выбрана директория: "
								+ file.getAbsolutePath());

					}
					if (file.isFile()) {
						System.out.println("Выбран файл: " + file.getName());
					}
					btnOk.setEnabled(true);
				} else {
					System.out.println("Файл или директория не выбрны");
					btnOk.setEnabled(false);

					// System.out.println(fileopen.getRootPane().getD);

				}
			}
		});

		selectFileButton.setBounds(10, 25, 97, 25);
		contentPane.add(selectFileButton);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				System.exit(0);
			}
		});
		btnCancel.setBounds(252, 25, 97, 25);
		contentPane.add(btnCancel);

		// String[] elements = new String[] {"Вася", "Петя",
		// "<html><font size = +1 color = yellow>Иван</font>"};
		//
		//
		// JComboBox comboBox = new JComboBox(elements);
		// comboBox.setSelectedIndex(1);
		// comboBox.setSize(getSize());
		// comboBox.setEditable(true);
		// comboBox.setBounds(161, 63, 31, 22);
		//
		//
		//
		// contentPane.add(comboBox);

	}
}
