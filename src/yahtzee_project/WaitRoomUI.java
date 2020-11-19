package yahtzee_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.sound.sampled.*;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class WaitRoomUI extends JFrame {

	String temp, id;
	int lastRoomNum = 50;
	
	JButton makeBtn, getInBtn, sendBtn;
	JTree userTree;
	JList roomList;
	JTextField chatField;
	JTextArea roomArea, chatArea;
	JLabel lbid, lbnick;
	JTextField lbip;
	
	Client client;
	ArrayList<User> userList;
	String currentSelectedTreeNode;
	DefaultListModel model;
	DefaultMutableTreeNode root;
	DefaultMutableTreeNode user;
	
	private Player player;
	playBGM mp = new playBGM(true);
	
	public WaitRoomUI(Client client) { 
		setTitle("����");
		userList = new ArrayList<User>();
		this.client = client;
		mp.start();
		init();
		
	}
	public void init() {
		
		setBounds(100, 100, 700, 490);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		setLayout(null);
		//JMenuBar menuBar = new JMenuBar();
		this.getContentPane().setBackground(Color.pink);		// �����ӿ� ������ ������.. ���Ŀ� ���� �ٲ㵵 ����! 
		
		JPanel roomPanel = new JPanel();
		TitledBorder tb = new TitledBorder(new LineBorder(Color.white), "�� �� ��", TitledBorder.CENTER, TitledBorder.TOP, null, null);
															//UIManager.getBorder("TitledBorder.border") �̾��µ� �׵θ� �� �ٲܷ��� new LineBorder(Color.white) �� ����
		roomPanel.setBorder(tb);	
		
		roomPanel.setBounds(12, 10, 477, 215);
		roomPanel.setLayout(new BorderLayout(0,0));
		roomPanel.setOpaque(false);  // �̰� ������ border �׵θ����� ������ �� ������ //
		
		JScrollPane scrollPane = new JScrollPane();
		roomPanel.add(scrollPane, BorderLayout.CENTER);
		roomList = new JList(new DefaultListModel());
		roomList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//�� ����Ʈ ��ѹ� ���� �� ���� �ʿ�
				int i = roomList.getFirstVisibleIndex();
				// System.out.println(">>>>>>>>>>>" + i);
				if (i != -1) {
					// ä�ù� ��� �� �ϳ��� ������ ���,
					// ������ ���� ���ȣ�� ����
					String temp = (String) roomList.getSelectedValue();
					if(temp.equals(null)){
						return;
					}
					/*
					try {
						client.getUser().getDos().writeUTF(User.UPDATE_SELECTEDROOM_USERLIST + "/" + temp.substring(0, 3));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					*/
				}
			}
		});
		model = (DefaultListModel) roomList.getModel();
		scrollPane.setViewportView(roomList);
		
		JPanel roomBtnPanel = new JPanel();
		makeBtn = new JButton(new ImageIcon("img/makeBtnimg.png")); // �� ����� ��ư
		makeBtn.setPreferredSize(new Dimension(238, 30));					//���� ��ư ũ�� (�̹���(png)���� ���� ��ư)
		makeBtn.addMouseListener(new MouseAdapter() {			
			public void mouseClicked(MouseEvent e) {				
				new Thread(new Runnable() {     // ��ư ������ ȿ���� �߰� //
					@Override
					public void run(){
						try{
							 AudioInputStream stream = AudioSystem.getAudioInputStream(new File("bgm/clicksound.wav")); 
				              Clip clip = AudioSystem.getClip(); 
				              clip.open(stream); 
				              clip.start();
						}catch(Exception e) { 
				              e.printStackTrace(); 
				          } 
					}
				}).start();
				createRoom();
			}
			
		});
		getInBtn = new JButton(new ImageIcon("img/getinBtnimg.png")); //�� ���� ��ư
		getInBtn.setPreferredSize(new Dimension(238, 30));		//���� ��ư ũ�� (�̹���(png)���� ���� ��ư)
		getInBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new Thread(new Runnable() {     // ��ư ������ ȿ���� �߰� //
					@Override
					public void run(){
						try{
							 AudioInputStream stream = AudioSystem.getAudioInputStream(new File("bgm/clicksound.wav")); 
				              Clip clip = AudioSystem.getClip(); 
				              clip.open( stream ); 
				              clip.start();
						}catch(Exception e) { 
				              e.printStackTrace(); 
				          } 
					}
				}).start();
				getIn();
			}
			
		});
		roomBtnPanel.add(makeBtn);
		roomBtnPanel.add(getInBtn);
		roomBtnPanel.setLayout(new GridLayout(1, 0, 0, 0));
		roomPanel.add(roomBtnPanel, BorderLayout.SOUTH);
		
		add(roomPanel);
		
		JPanel chatPanel = new JPanel();
		chatPanel.setBorder(new TitledBorder(new LineBorder(Color.white), "ä �� â", TitledBorder.CENTER, TitledBorder.TOP, null, null));
												//UIManager.getBorder("TitledBorder.border") �̾��µ� �׵θ� �� �ٲܷ��� new LineBorder(Color.white) �� ����
		chatPanel.setBounds(12, 235, 477, 185);
		add(chatPanel);
		chatPanel.setLayout(new BorderLayout(0,0));
		chatPanel.setOpaque(false);  // �̰� ������ border �׵θ����� ������ �� ������ //
		
		JPanel chatAreaPanel = new JPanel();
		JPanel chatInputPanel = new JPanel();
		JScrollPane scrollPane2 = new JScrollPane();
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatPanel.add(scrollPane2, BorderLayout.CENTER);
		scrollPane2.setViewportView(chatArea);
		
		chatField = new JTextField();
		chatField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					msgSummit();
				}
			}
		});
		chatField.setColumns(10);
		
		sendBtn = new JButton(new ImageIcon("img/sendBtnimg.png"));  //���� ��ư �̹���
		sendBtn.setPreferredSize(new Dimension(59, 26));						//���� ��ư ũ�� (�̹���(png)���� ���� ��ư)
		
		sendBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				msgSummit();
				chatField.requestFocus();
			}
		
		});
		chatInputPanel.add(chatField);
		chatInputPanel.add(sendBtn);
		chatInputPanel.setLayout(new BoxLayout(chatInputPanel, BoxLayout.X_AXIS));
		chatPanel.add(chatInputPanel, BorderLayout.SOUTH);
		JPanel treePanel = new JPanel();
		treePanel.setBorder(new TitledBorder(new LineBorder(Color.white), "����� ���", TitledBorder.CENTER,	TitledBorder.TOP, null, null));
										//UIManager.getBorder("TitledBorder.border") �̾��µ� �׵θ� �� �ٲܷ��� new LineBorder(Color.white) �� ����
		treePanel.setBounds(501, 10, 171, 409);
		JScrollPane scrollPane3 = new JScrollPane();
		
		treePanel.setLayout(new BorderLayout(0,0));
		treePanel.add(scrollPane3, BorderLayout.CENTER);
		treePanel.setOpaque(false);  // �̰� ������ border �׵θ����� ������ �� ������ //
		
		userTree = new JTree();
		userTree.setEditable(false);
		root = new DefaultMutableTreeNode("������");
		DefaultTreeModel model = new DefaultTreeModel(root);
		userTree.setModel(model);

		scrollPane3.setViewportView(userTree);
		add(treePanel);
		
		setLocationRelativeTo(null);
		setVisible(true);
	};
	
	public void msgSummit() {
		String string = chatField.getText();
		//�޼��� ������ ����
	}
	public void createRoom() {
		String roomname = JOptionPane.showInputDialog("��ȭ�� �̸��� �Է��ϼ���~");
		if(roomname==null) {	// ��� ��ư
			
		} else {
			
		}
	}
	
	
	
	public void getIn() {
		
		/*
		String selectedRoom = (String) roomList.getSelectedValue();
		StringTokenizer token = new StringTokenizer(selectedRoom, "/"); // ��ū ����
		String rNum = token.nextToken();
		String rName = token.nextToken();

		Room theRoom = new Room(rName); // �� ��ü ����
		theRoom.setRoomNum(Integer.parseInt(rNum)); // ���ȣ ����
		theRoom.setrUI(new RoomUI(client, theRoom)); // UI

		// Ŭ���̾�Ʈ�� ������ �� ��Ͽ� �߰�
		client.getUser().getRoomArray().add(theRoom);

		try {
			client.getDos().writeUTF(User.GETIN_ROOM + "/" + theRoom.getRoomNum());
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
	
	class playBGM extends Thread { //���� Ŭ���� �ȿ� �ִ��� ������� 
		private boolean isLoop;
		public  playBGM(boolean isLoop) {
			try {
				this.isLoop = isLoop;
				FileInputStream fis = new FileInputStream("bgm/bgm2.mp3");
				BufferedInputStream bis = new BufferedInputStream(fis);
				player = new Player(bis);				
			
			} catch(Exception e) {
				e.printStackTrace();
			}	
		}
		public void run(){
			try {
				do{
				player.play();
				FileInputStream fis = new FileInputStream("bgm/bgm2.mp3");
				BufferedInputStream bis = new BufferedInputStream(fis);
				player = new Player(bis);	
				}while(isLoop);				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("error");
			}
		}
	}	
	
}
