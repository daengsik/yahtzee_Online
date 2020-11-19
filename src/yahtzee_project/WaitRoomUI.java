package yahtzee_project;

import java.awt.BorderLayout;
import java.awt.Cursor;
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
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
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
		setTitle("대기실");
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
		
		JPanel roomPanel = new JPanel();
		roomPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "대 기 실", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		roomPanel.setBounds(12, 10, 477, 215);
		roomPanel.setLayout(new BorderLayout(0,0));
		
		JScrollPane scrollPane = new JScrollPane();
		roomPanel.add(scrollPane, BorderLayout.CENTER);
		roomList = new JList(new DefaultListModel());
		roomList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//룸 리스트 룸넘버 전달 및 구현 필요
				int i = roomList.getFirstVisibleIndex();
				// System.out.println(">>>>>>>>>>>" + i);
				if (i != -1) {
					// 채팅방 목록 중 하나를 선택한 경우,
					// 선택한 방의 방번호를 전송
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
		makeBtn = new JButton(new ImageIcon("img/makeBtnimg.png"));
		makeBtn.setBorderPainted(false);
		makeBtn.setContentAreaFilled(false);
		makeBtn.addMouseListener(new MouseAdapter() {			
			public void mouseClicked(MouseEvent e) {				
				new Thread(new Runnable() {     // 버튼 누르면 효과음 추가 //
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
			public void mouseEntered(MouseEvent e) {    // 버튼 올렸을 때 까맣게 
				makeBtn.setIcon(new ImageIcon("img/makeBtnimg2.png"));
			}
			public void mouseExited(MouseEvent e) {		// 버튼 올렸을 때 까맣게 
				makeBtn.setIcon(new ImageIcon("img/makeBtnimg.png"));				
			}
		});
		getInBtn = new JButton(new ImageIcon("img/getinBtnimg.png"));
		getInBtn.setBorderPainted(false);
		getInBtn.setContentAreaFilled(false);
		getInBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new Thread(new Runnable() {     // 버튼 누르면 효과음 추가 //
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
			public void mouseEntered(MouseEvent e) {    // 버튼 올렸을 때 까맣게 
				getInBtn.setIcon(new ImageIcon("img/getinBtnimg2.png"));
			}
			public void mouseExited(MouseEvent e) {		// 버튼 올렸을 때 까맣게 
				getInBtn.setIcon(new ImageIcon("img/getinBtnimg.png"));				
			}
		});
		roomBtnPanel.add(makeBtn);
		roomBtnPanel.add(getInBtn);
		roomBtnPanel.setLayout(new GridLayout(1, 0, 0, 0));
		roomPanel.add(roomBtnPanel, BorderLayout.SOUTH);
		
		add(roomPanel);
		
		JPanel chatPanel = new JPanel();
		chatPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "채 팅 창", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		chatPanel.setBounds(12, 235, 477, 185);
		add(chatPanel);
		chatPanel.setLayout(new BorderLayout(0,0));
		
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
		
		sendBtn = new JButton(new ImageIcon("img/sendBtnimg.png"));
		sendBtn.setBorderPainted(false);
		sendBtn.setContentAreaFilled(false);
		sendBtn.setSize(10,10);
		sendBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				msgSummit();
				chatField.requestFocus();
			}
			public void mouseEntered(MouseEvent e) {    // 버튼 올렸을 때 까맣게 
				sendBtn.setIcon(new ImageIcon("img/sendBtnimg2.png"));
			}
			public void mouseExited(MouseEvent e) {		// 버튼 올렸을 때 까맣게 
				sendBtn.setIcon(new ImageIcon("img/sendBtnimg.png"));				
			}
		});
		chatInputPanel.add(chatField);
		chatInputPanel.add(sendBtn);
		chatInputPanel.setLayout(new BoxLayout(chatInputPanel, BoxLayout.X_AXIS));
		chatPanel.add(chatInputPanel, BorderLayout.SOUTH);
		JPanel treePanel = new JPanel();
		treePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "사용자 목록", TitledBorder.CENTER,	TitledBorder.TOP, null, null));
		treePanel.setBounds(501, 10, 171, 409);
		JScrollPane scrollPane3 = new JScrollPane();
		
		treePanel.setLayout(new BorderLayout(0,0));
		treePanel.add(scrollPane3, BorderLayout.CENTER);
		
		userTree = new JTree();
		userTree.setEditable(false);
		root = new DefaultMutableTreeNode("접속자");
		DefaultTreeModel model = new DefaultTreeModel(root);
		userTree.setModel(model);

		scrollPane3.setViewportView(userTree);
		add(treePanel);
		
		setLocationRelativeTo(null);
		setVisible(true);
	};
	
	public void msgSummit() {
		String string = chatField.getText();
		//메세지 보내기 구현
	}
	public void createRoom() {
		String roomname = JOptionPane.showInputDialog("대화방 이름을 입력하세요~");
		if(roomname==null) {	// 취소 버튼
			
		} else {
			
		}
	}
	
	
	
	public void getIn() {
		
		/*
		String selectedRoom = (String) roomList.getSelectedValue();
		StringTokenizer token = new StringTokenizer(selectedRoom, "/"); // 토큰 생성
		String rNum = token.nextToken();
		String rName = token.nextToken();

		Room theRoom = new Room(rName); // 방 객체 생성
		theRoom.setRoomNum(Integer.parseInt(rNum)); // 방번호 설정
		theRoom.setrUI(new RoomUI(client, theRoom)); // UI

		// 클라이언트가 접속한 방 목록에 추가
		client.getUser().getRoomArray().add(theRoom);

		try {
			client.getDos().writeUTF(User.GETIN_ROOM + "/" + theRoom.getRoomNum());
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
	
	class playBGM extends Thread { //원래 클래스 안에 있던걸 스레드로 
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
