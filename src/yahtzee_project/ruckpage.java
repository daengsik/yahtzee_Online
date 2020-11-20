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
		setTitle("운세");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setBounds(0,0,560,530);
		try {				
			img = ImageIO.read(new File("img/ruckbackimg.png"));
					
		}catch (IOException e){
			JOptionPane.showMessageDialog(null,"이미지 불러오기 실패");
		}				
		
		panel = new JPanel(){
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(img,0, 0, null);
		}};
		panel.setBounds(0, 0, 548, 500);		
		panel.setLayout(null);
		
		
		one= new JLabel(luck());				// 1~100% 확률 받기
		one1 = emp;									// 세 개 수치 비교하기 위해 static으로 만든 emp를 사용해서 one1에 정수 담아둠
		one.setFont(new Font("Serif",Font.BOLD,30));
		one.setForeground(Color.black);
		one.setBounds(94, 135, 100, 100);		
		panel.add(one);
		
		two= new JLabel(luck());					// 1~100% 확률 받기
		two1 = emp;										// 세 개 수치 비교하기 위해 static으로 만든 emp를 사용해서 one1에 정수 담아둠
		two.setFont(new Font("Serif",Font.BOLD,30));
		two.setForeground(Color.black);
		two.setBounds(244, 135, 100, 100);
		panel.add(two);
		
		three= new JLabel(luck());					// 1~100% 확률 받기
		three1 = emp;									// 세 개 수치 비교하기 위해 static으로 만든 emp를 사용해서 one1에 정수 담아둠
		three.setFont(new Font("Serif",Font.BOLD,30));
		three.setForeground(Color.black);
		three.setBounds(394, 135, 100, 100);
		panel.add(three);		
		
		
		
		if(Max(one1,two1,three1)==one1)				// one = 건강 , two = 행운 , three = 금전 중 .. 젤 큰거 비교해서 밑에 글씨 나오게
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
	
	int Max(int a, int b, int c)	{					//크기 비교 메소드
	if(a>b)	 {
		 if(a>c)  {
			 return a;
		 }	  else  {
			 return c;
		 }
	 }
	 else  { //a<b인경우	 
		 if(b>c)	  {
			 return b;
		 }	  else	  {
			 return c;
		 }	
	 }
	}
	
	public String luck(){			// 행운 수치제공해주는 메소드
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

