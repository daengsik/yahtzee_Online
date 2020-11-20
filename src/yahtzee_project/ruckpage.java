package yahtzee_project;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ruckpage extends JFrame {
	
	BufferedImage img = null;	
	JLabel one;
	JLabel two;
	JLabel three;
	JLabel label;
	JPanel panel;
	JButton maxBtn;
	int max = 0;
	int one1,two1,three1;
	static int emp = 0; 
	public ruckpage(){
		setTitle("�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setBounds(0,0,560,530);
		try {				
			img = ImageIO.read(new File("img/ruckbackimg.png"));
					
		}catch (IOException e){
			JOptionPane.showMessageDialog(null,"�̹��� �ҷ����� ����");
		}				
		
		panel = new JPanel(){
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(img,0, 0, null);
		}};
		panel.setBounds(0, 0, 548, 500);		
		panel.setLayout(null);
		
		
		one= new JLabel(luck());				// 1~100% Ȯ�� �ޱ�
		one1 = emp;									// �� �� ��ġ ���ϱ� ���� static���� ���� emp�� ����ؼ� one1�� ���� ��Ƶ�
		one.setFont(new Font("Serif",Font.BOLD,30));
		one.setForeground(Color.black);
		one.setBounds(94, 135, 100, 100);		
		panel.add(one);
		
		two= new JLabel(luck());					// 1~100% Ȯ�� �ޱ�
		two1 = emp;										// �� �� ��ġ ���ϱ� ���� static���� ���� emp�� ����ؼ� one1�� ���� ��Ƶ�
		two.setFont(new Font("Serif",Font.BOLD,30));
		two.setForeground(Color.black);
		two.setBounds(244, 135, 100, 100);
		panel.add(two);
		
		three= new JLabel(luck());					// 1~100% Ȯ�� �ޱ�
		three1 = emp;									// �� �� ��ġ ���ϱ� ���� static���� ���� emp�� ����ؼ� one1�� ���� ��Ƶ�
		three.setFont(new Font("Serif",Font.BOLD,30));
		three.setForeground(Color.black);
		three.setBounds(394, 135, 100, 100);
		panel.add(three);		
		
		
		
		if(Max(one1,two1,three1)==one1)				// one = �ǰ� , two = ��� , three = ���� �� .. �� ū�� ���ؼ� �ؿ� �۾� ������
			label = new JLabel(new ImageIcon("img/gunimg.png"));			
		else if(Max(one1,two1,three1)==two1)
			label = new JLabel(new ImageIcon("img/luck2img.png"));			
		else
			label = new JLabel(new ImageIcon("img/costimg.png"));		
		
		label.setBounds(140,240,270,250);
		panel.add(label);
		
		add(panel);		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	int Max(int a, int b, int c)	{					//ũ�� �� �޼ҵ�
	if(a>b)	 {
		 if(a>c)  {
			 return a;
		 }	  else  {
			 return c;
		 }
	 }
	 else  { //a<b�ΰ��	 
		 if(b>c)	  {
			 return b;
		 }	  else	  {
			 return c;
		 }	
	 }
	}
	
	public String luck(){			// ��� ��ġ�������ִ� �޼ҵ�
		String result;
		int x = (int)(Math.random() * 100);
		emp = x;
		if(x>=10){
			result = x + "%";
		} else{
			result = " "+x+"%";
		}
		return result;		
	}

	public static void main(String[] args) {
		new ruckpage();			
	}
}

