import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JavaBookshop {

	private JFrame frame;
	private JTextField BookName;
	private JTextField Description;
	private JTextField Price;
	private JTextField NumBook;
	private JTable table;
	private JTextField BookId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaBookshop window = new JavaBookshop();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaBookshop() {
		initialize();
		connect();
		tableload();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public void connect() {
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/book shop","root","");
			
		}catch(ClassNotFoundException e) {
			System.out.println("ClassNotFoundException"+e);
		}catch (SQLException e) {
			System.out.println("SQLException"+e);
		}
	}
	
	public void tableload() {
		try {
			pst = con.prepareStatement("select * from book");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1080, 593);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Record Management System");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblNewLabel.setBounds(275, 0, 446, 130);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(32, 131, 427, 288);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name :");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1.setBounds(41, 40, 127, 48);
		panel.add(lblNewLabel_1);
		
		BookName = new JTextField();
		BookName.setBounds(196, 57, 206, 19);
		panel.add(BookName);
		BookName.setColumns(10);
		
		Description = new JTextField();
		Description.setColumns(10);
		Description.setBounds(196, 114, 206, 19);
		panel.add(Description);
		
		JLabel lblNewLabel_1_1 = new JLabel("Description :\r\n");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1_1.setBounds(41, 97, 109, 48);
		panel.add(lblNewLabel_1_1);
		
		Price = new JTextField();
		Price.setColumns(10);
		Price.setBounds(196, 172, 206, 19);
		panel.add(Price);
		
		JLabel lblNewLabel_1_2 = new JLabel("Price :");
		lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1_2.setBounds(41, 155, 99, 48);
		panel.add(lblNewLabel_1_2);
		
		NumBook = new JTextField();
		NumBook.setColumns(10);
		NumBook.setBounds(196, 230, 206, 19);
		panel.add(NumBook);
		
		JLabel lblNewLabel_1_3 = new JLabel("Number of Book :");
		lblNewLabel_1_3.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1_3.setBounds(41, 213, 145, 48);
		panel.add(lblNewLabel_1_3);
		
		JButton btnNewButton = new JButton("Update");
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String SaveBookName,SaveDescription,SavePrice,SaveNumBook,Saveid;
				SaveBookName = BookName.getText();
				SaveDescription = Description.getText();
				SavePrice = Price.getText();
				SaveNumBook = NumBook.getText();
				Saveid = BookId.getText();
				try{
					pst = con.prepareStatement("update book set Book_name = ?,Description = ?,Price = ?,num_book = ? where id = ?");
					pst.setString(1,SaveBookName);
					pst.setString(2,SaveDescription);
					pst.setString(3,SavePrice);
					pst.setString(4,SaveNumBook);
					pst.setString(5,Saveid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Update successfully !!");
					tableload();
					BookName.setText("");
					Description.setText("");
					Price.setText("");
					NumBook.setText("");
					BookId.setText("");
					BookName.requestFocus();
					System.out.println(pst);
					
				}catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println("SQLException"+ex);
				}	
			}
		});
		btnNewButton.setBounds(54, 443, 120, 53);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Save");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String SaveBookName,SaveDescription,SavePrice,SaveNumBook;
			SaveBookName = BookName.getText();
			SaveDescription = Description.getText();
			SavePrice = Price.getText();
			SaveNumBook = NumBook.getText();
			
			try{
				pst = con.prepareStatement("insert into book(Book_name,Description,Price,num_book) value (?,?,?,?)");
				pst.setString(1,SaveBookName);
				pst.setString(2,SaveDescription);
				pst.setString(3,SavePrice);
				pst.setString(4,SaveNumBook);
				pst.executeUpdate();
				System.out.println(pst);
				JOptionPane.showMessageDialog(null, "Save successfully !!");
				tableload();
				BookName.setText("");
				Description.setText("");
				Price.setText("");
				NumBook.setText("");
				BookName.requestFocus();
				System.out.println(pst);
				
			}catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println("SQLException"+ex);
			}
			
			}
		});
		btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnNewButton_1.setBounds(184, 443, 120, 53);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnDelete = new JButton("Delete ");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Saveid;
				Saveid = BookId.getText();
				try{
					pst = con.prepareStatement("delete from book where id = ?");
					pst.setString(1,Saveid);
					pst.executeUpdate();
					System.out.println(pst);
					JOptionPane.showMessageDialog(null, "Delete successfully !!");
					tableload();
					BookName.setText("");
					Description.setText("");
					Price.setText("");
					NumBook.setText("");
					BookId.setText("");
					BookName.requestFocus();
					System.out.println(pst);
					
				}catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println("SQLException"+ex);
				}	
			}
		});
		btnDelete.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnDelete.setBounds(314, 443, 120, 53);
		frame.getContentPane().add(btnDelete);
		
		JScrollPane tableresult = new JScrollPane();
		tableresult.setBounds(512, 223, 513, 303);
		frame.getContentPane().add(tableresult);
		
		table = new JTable();
		tableresult.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(512, 131, 513, 66);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Book ID :");
		lblNewLabel_1_2_1.setBounds(45, 24, 96, 21);
		lblNewLabel_1_2_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		panel_1.add(lblNewLabel_1_2_1);
		
		BookId = new JTextField();
		BookId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String id = BookId.getText();
					pst = con.prepareStatement("select Book_name,Description,Price,num_book from book where id = ?");
					pst.setString(1,id);
					System.out.println(pst);
					ResultSet rs = pst.executeQuery();
					
					if(rs.next() == true) {
						String Book_name = rs.getString(1);
						String Descriptions = rs.getString(2);
						String Prices = rs.getString(3);
						String num_book = rs.getString(4);
						
						BookName.setText(Book_name);
						Description.setText(Descriptions);
						Price.setText(Prices);
						NumBook.setText(num_book);
	
					} else {
						BookName.setText("");
						Description.setText("");
						Price.setText("");
						NumBook.setText("");
					}
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		BookId.setBounds(164, 27, 281, 19);
		BookId.setColumns(10);
		panel_1.add(BookId);
	}
}
