package yahtzee_project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class LoginUI extends JFrame implements Runnable {

	boolean confirm = false;
	JTextField idField;
	JPasswordField passField;
	JButton loginBtn, signUpBtn, ipBtn, bgmBtn, ruleBtn, ruckBtn;
	Client client;
	DBJoin jdb;

	private Player player;
	BufferedImage img = null;	
	//������//
	public LoginUI(Client client) {

		setTitle("�α���");
		this.client = client;
		//mp.start();
		
		setBounds(100, 100, 510, 530);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		
		try {
			img = ImageIO.read(new File("img/backimg2.png"));
					
		}catch (IOException e){
			JOptionPane.showMessageDialog(null,"�̹��� �ҷ����� ����");
		}				
		
		JPanel panel = new JPanel(){
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(img,0, 0, null);
		}};
		panel.setBounds(0, 0, 510, 530);
		
		panel.setLayout(null);
		
		JButton idLabel = new JButton(new ImageIcon("img/idtext.png"));			// �α��� â id �۾�
		
		idLabel.setBorderPainted(false);
		idLabel.setContentAreaFilled(false);
		idLabel.setBounds(160, 255, 57, 15);		
		panel.add(idLabel);		
		
		JButton passLabel = new JButton(new ImageIcon("img/passtext.png")); // �α��� â password �۾�
		passLabel.setBorderPainted(false);
		passLabel.setContentAreaFilled(false);
		passLabel.setBounds(160, 286, 57, 15);
		panel.add(passLabel);
		
		idField = new JTextField();
		idField.setBounds(229, 252, 116, 21);
		panel.add(idField);
		idField.setColumns(10);
		
		passField = new JPasswordField();
		passField.setBounds(229, 283, 116, 21);
		panel.add(passField);
		passField.setColumns(10);
		
		loginBtn = new JButton(new ImageIcon("img/logintext.png"));
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				msgSummit();
				new Thread(new Runnable() {  // ��ư ������ ȿ���� �߰� //
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
			}
		});
		
		loginBtn.setBounds(150, 311, 97, 23);
		panel.add(loginBtn);
		
		signUpBtn = new JButton(new ImageIcon("img/signtext.png"));
		signUpBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//ȸ������
				jdb = new DBJoin();
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
			}
		});
		
		signUpBtn.setBounds(249, 311, 97, 23);
		panel.add(signUpBtn);
		
	
		ruleBtn = new JButton(new ImageIcon("img/rulebtimg.png"));  //�� ��ư �߰� //		
		ruleBtn.setBorderPainted(false);						
		ruleBtn.setContentAreaFilled(false);
	    
		ruleBtn.addActionListener(new ActionListener(){		 //���ư ������ ���������� �̵�//
			@Override
			public void actionPerformed(ActionEvent e){
				if(e.getSource()==ruleBtn){					
					rulepage p = new rulepage(); // ��ư ������  �߰��� rulepage Ŭ���� �߰� //
					new Thread(new Runnable() {
						@Override
						public void run(){  // ��ư ������ ȿ���� �߰� //
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
				}
			}
		});
		
		ruleBtn.setBounds(360, 370, 50, 50);
		panel.add(ruleBtn);
		
		ruckBtn = new JButton(new ImageIcon("img/ruckimg.png"));  //����� ��ư �߰� //		
		ruckBtn.setBorderPainted(false);
		ruckBtn.setContentAreaFilled(false);
	    
		ruckBtn.addActionListener(new ActionListener(){		 //������ư ������ ��������� �̵�//
			@Override
			public void actionPerformed(ActionEvent e){
				if(e.getSource()==ruckBtn){
					new ruckpage();
					new Thread(new Runnable() {
						@Override
						public void run(){  // ��ư ������ ȿ���� �߰� //
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
				}
			}
		});
		ruckBtn.setBounds(307, 370, 50, 50);
		panel.add(ruckBtn);	
		
		JButton ipLabel = new JButton(new ImageIcon("img/iptext.png"));
		ipLabel.setBorderPainted(false);
		ipLabel.setContentAreaFilled(false);
		ipLabel.setBounds(150, 210, 78, 15);
		panel.add(ipLabel);
		
		ipBtn = new JButton("����IP �Է�");
		ipBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ServerIP serverIP = new ServerIP(LoginUI.this);
				setVisible(false);
			}
		});
		ipBtn.setBounds(230, 206, 113, 23);
		ipBtn.setBackground(Color.WHITE);
		panel.add(ipBtn);
		add(panel);
		setLocationRelativeTo(null);
		setVisible(true);
	}	
	
	public void run() {
		try {
			player.play();
			//player.close(); bgm����
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//���θ޼���
	public void msgSummit() {
		System.out.println("msgSummit");
		
		new Thread(new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				//���ϻ���				
				if(client.serverAccess()) {
					try {
						//�α������� (���̵�+�н�����) ����
						client.getDos().writeUTF(User.LOGIN + "/" + idField.getText() + "/" + passField.getText());
					} catch (IOException e1) {
						e1.printStackTrace(); 
						
					}
				}				
			}	
		}).start();
		
		//msgSummit() end
	}
	

}
