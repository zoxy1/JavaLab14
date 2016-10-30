package lab14;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.tree.TreeModel;

public class MyFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
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
		setBounds(100, 100, 820, 600);
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
								try {
									doubleList.add(j, Double.parseDouble(isParts[j]));
								} catch (NumberFormatException ewr) {

									System.out.println("Выбранный файл содержит не корректные данные");
									contentPane.setVisible(false);
									System.exit(0);

								}
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
								y = variableCalculation.calculateFunction(doubleList.get(0), doubleList.get(1), doubleList.get(2), doubleList.get(3), x);
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
						System.out.println("Полученные результаты расчетов записаны в файл out.txt");

					}
				}
				if (file.isDirectory()) {

					TreeModel model = new FileTreeModel(file);
					JTree tree_1 = new JTree(model);
					JScrollPane scrollPane = new JScrollPane(tree_1);
					scrollPane.setViewportBorder(new EmptyBorder(1, 1, 1, 1));
					scrollPane.setToolTipText("Scroll panell");
					scrollPane.setBounds(5, 60, 800, 500);
					contentPane.add(scrollPane);

					contentPane.updateUI();

				}
			}
		});

		btnOk.setBounds(131, 25, 97, 25);
		contentPane.add(btnOk);
		JButton selectFileButton = new JButton(" Select");
		selectFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileopen = new JFileChooser();

				fileopen.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

				int ret = fileopen.showDialog(null, "Open");
				if (ret == JFileChooser.APPROVE_OPTION) {

					file = fileopen.getSelectedFile();

					if (file.isDirectory()) {
						System.out.println("Выбрана директория: " + file.getAbsolutePath());

					}
					if (file.isFile()) {
						System.out.println("Выбран файл: " + file.getName());
					}
					btnOk.setEnabled(true);
				} else {
					System.out.println("Файл или директория не выбраны");
					btnOk.setEnabled(false);

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

	}
}
