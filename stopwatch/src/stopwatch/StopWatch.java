package stopwatch;

/*
 * Title : Java�� ������ Disital Stopwatch
 * Author : ������
 * Date : 2014.05.25
 */
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StopWatch{
	JFrame frame; //��ž��ġ ����� ���� �� frame�� �����մϴ�.
	JPanel panel1, panel2; //panel1�� �ð��� ��Ÿ���� �г�, panel2�� ��ư�� ��ġ�� �г��Դϴ�.
	JButton startBtn, stopBtn, resetBtn; //����,����,�ʱ�ȭ �� ���� ��ư�� ����ϱ� ���� �����Ͽ����ϴ�.
	
	//��,��,��,��Ƽ��(=1/100��)�� �ʱ� ���´� 00���� �����մϴ�.
	String hour = "00";
	String min = "00";
	String sec = "00";
	String csec = "00";
	
	//�ð��� ���� �� ��ž��ġ Ŭ������ TimeDisplay�� ����Ͽ����� �̸� timeDisplay��� �̸����� ����մϴ�.
	TimeDisplay timeDisplay;	
	boolean st = false; //���ۻ���(start,stop)�� ���� reset��ư�� ������ �����ϱ� ���Ͽ� boolean Ÿ���� ������ ����մϴ�.
	
	//���� �޼ҵ� �Դϴ�.
	public static void main(String[] args) {
		new StopWatch().go(); //StopWatch Ŭ���� ���� go()�޼ҵ带 �����մϴ�. 
	}  
	
	//go �޼ҵ带 ������ �κ��Դϴ�.
	public void go() {
		//frame�� panel1,2�� ��ü�� �����մϴ�.
		frame = new JFrame();
		panel1 = new CoverPanel();
		panel2 = new JPanel();
		
		//���۹�ư, ������ư, �ʱ�ȭ��ư�� ��ü�� �����մϴ�.
		startBtn = new JButton("Start");
		stopBtn = new JButton("Stop");
		resetBtn = new JButton("Reset");
		
		//ó�� ���� �� stop,reset��ư�� Ŭ���ص� ���ٸ� ������ �����Ƿ� ��Ȱ��ȭ ���·� �����մϴ�.
		stopBtn.setEnabled(false);
		resetBtn.setEnabled(false);
		
		//frame�� Ÿ��Ʋ�� ũ�⸦ �����մϴ�.
		frame.setTitle("Stop Watch");
		frame.setSize(350, 160);
		
		//frame�� X��ư�� ������ ���α׷��� ������ ����ǵ��� �մϴ�.
		//�̸� ���������� ������ â�� �ݾ������� ���α׷��� ������ ������� �ʰ� ���� ������ ������ ��� ���� ���� ���·� �����ϰ� �˴ϴ�.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//panel2���� ����,����,�ʱ�ȭ��ư�� ��ġ�մϴ�.
		panel2.add(startBtn);
		panel2.add(stopBtn);
		panel2.add(resetBtn);
	
		//panel1�� �����, panel2�� ���ʿ� ��ġ�ϵ��� BorderLayout�� �־� frame�� panel1,2�� �߰��մϴ�.
		frame.getContentPane().add(panel1, BorderLayout.CENTER);
		frame.getContentPane().add(panel2, BorderLayout.SOUTH);
		frame.setResizable(false);
		frame.setVisible(true); //frame�� ������ ���̵��� �մϴ�.
		
		//���۹�ư�� Ŭ������ �� �����ϴ� ActionListener�Դϴ�.
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				st = true; //start������ ��, st�� true���� �ݴϴ�.
				startBtn.setEnabled(false); //���۹�ư ��Ȱ��ȭ
				stopBtn.setEnabled(true); //������ư Ȱ��ȭ
				resetBtn.setEnabled(true); //�ʱ�ȭ��ư Ȱ��ȭ
				
				timeDisplay = new TimeDisplay(); //��ž��ġ�� �ð��� ������ �� �ֵ��� timeDisplay ��ü�� �����մϴ�. 
				//frame�� ���̴� �ð����κ��� ��ž��ġ�� �����ϱ� ���� setTime()�޼ҵ忡 ��,��,��,��Ƽ�� ���� �����մϴ�.
				timeDisplay.setTime(Integer.parseInt(hour), Integer.parseInt(min), Integer.parseInt(sec), Integer.parseInt(csec));
				
				//Thread timeDisplay�� �����մϴ�.
				//start()�޼ҵ� ȣ�� �� TimeDisplayŬ���� ���� run()�޼ҵ尡 ����˴ϴ�.
				timeDisplay.start();
				
			}
		});
		
		//������ư�� Ŭ������ �� �����ϴ� ActionListener�Դϴ�.
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				st = false; //start���°� �ƴ� ��� st���� false�� �����մϴ�.
				stopBtn.setEnabled(false); //������ư ��Ȱ��ȭ
				startBtn.setEnabled(true); //���۹�ư Ȱ��ȭ
				resetBtn.setEnabled(true); //�ʱ�ȭ��ư Ȱ��ȭ
				
				timeDisplay = null; //Thread�� �������� �ʵ��� null�� �����մϴ�.
			}
		});
		
		//�ʱ�ȭ��ư�� Ŭ������ �� �����ϴ� ActionListener�Դϴ�.
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(st) {//��ž��ġ�� start������ �� �ʱ�ȭ��ư�� ���� ���	
					timeDisplay.setTime(0, 0, 0, 0); //�ٽ� 0��Ƽ�� ���� ��ž��ġ�� ���ư����� �մϴ�.
					
					//�ٽ� ó������ ��ž��ġ�� ���ư��� �ִ� ����(=start����)�̹Ƿ� 
					//���۹�ư�� ��Ȱ��ȭ ��ŵ�ϴ�.
					startBtn.setEnabled(false);
				}
				else {//��ž��ġ�� stop������ �� �ʱ�ȭ ��ư�� ���� ���
					
					startBtn.setEnabled(true); //���۹�ư Ȱ��ȭ
					//stop���¿��� �ʱ�ȭ ��ư�� ���� �� ������ư, �ʱ�ȭ��ư�� ���ٸ� ������ ���� �����Ƿ� ��Ȱ��ȭ �մϴ�.
					resetBtn.setEnabled(false); //�ʱ�ȭ��ư ��Ȱ��ȭ
					stopBtn.setEnabled(false); //������ư ��Ȱ��ȭ
					
					//��,��,��,��Ƽ�ʸ� �ٽ� �ʱ� ������ 00���� �����մϴ�.
					hour = "00";
					min = "00";
					sec = "00";
					csec = "00";
				}
				
				frame.repaint(); //frame�� component�� �ٽ� �׷��� �ٲ� �ð��� ���̵��� �մϴ�.
			}
		});
	}
	
	//������ �ð��� ��Ÿ���� ���� JPanel�� ��ӹ޾� ������ panel Ŭ�����Դϴ�.
	class CoverPanel extends JPanel {
		public void paintComponent(Graphics g) { 
			//text component�� ������� �ʱ� ���� paint component�� �̿��Ͽ� panel�� �ð��� ���̵��� �մϴ�.
	
			String time = hour+":"+min+":"+sec+"."; //��,��,�� ���ڿ��� :�� �ٿ��� ȭ�鿡 ���� �ð� ������ �����մϴ�.

			g.setFont(new Font("Serif", Font.PLAIN, 40)); //��,��,���� ��Ʈ�� �۾�ũ�⸦ �����մϴ�.
			g.drawString(time, 80, 60); //��,��,�ʸ� ���� ��ġ�� �ð��� �׸��ϴ�.
			g.setFont(new Font("Serif", Font.PLAIN, 25)); //��Ƽ���� ��Ʈ�� �۾�ũ�⸦ �����մϴ�.
			//TimeDisplay���� csec�� �и��ʴ������� ��Ÿ���� �����Ƿ� �տ��� 2�ڸ������� �߶� ��Ƽ�ʴ����� ȭ�鿡 ���̵��� �մϴ�.
			g.drawString(csec.substring(0, 2), 230, 60); //��,��,�� ���� ��Ƽ�ʰ� ���̵��� �׸��ϴ�.
		}
	}
	
	// Thread�� ����Ͽ� ��ž��ġ�� �ð��� ������ Ŭ�����Դϴ�.
	class TimeDisplay extends Thread {                               
		int hour2, min2, sec2, csec2; //��,��,��,��Ƽ�ʸ� ��Ÿ�� ���ڸ� �����մϴ�.
		
		/*
		 * ��ž��ġ�� �ð��� �������ִ� �޼ҵ��Դϴ�.
		 * @param h �ð�
		 * @param m ��
		 * @param s ��
		 * @param c ��Ƽ��
		 */
		public void setTime(int h, int m, int s, int c) {
			this.hour2 = h;
			this.min2 = m;
			this.sec2 = s;
			this.csec2 = c;
		}
		
		//Thread.start()�� �����ϴ� �޼ҵ� �Դϴ�. �ð��� �����ϴ� ����� �����մϴ�.
		public void run() {
			//timeDisplay�� ���� ���� ��� ��Ƽ�ʸ� ����ؼ� +1�մϴ�.
			//timeDisplay�� null�� �Ǹ� �����մϴ�.
			while(timeDisplay!=null) {
				try{	
					//1�и���(=0.1��Ƽ��)���� Thread�� ���߾��� ����ǵ��� �մϴ�.
					//Thread.sleep(1) �޼ҵ�� 1�и��ʸ� ��Ȯ�� ���� ���� �ƴ϶�
					//1�и��� �̻� ���� ���� �����ϱ� ������ �и����� ������ ���̱� ���Ͽ� 
					//10�и���(1��Ƽ��)�� �ƴ� 1�и��ʸ� ����Ͽ����ϴ�.
					Thread.sleep(1); 
					
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
				csec2++; //1�и���(=1/1000��) �� �����մϴ�.
				    
				//1�� �����Կ� ���� ���ǹ��� �̿��� ��,��,��,��Ƽ�ʸ� ���� ���ִ� �κ��Դϴ�.
				if(csec2==1000) { //1000�и���(=1��)�� �Ǹ� �ʴ����� ���ڸ� 1�� ������Ű�� �и��ʸ� 0���� �����մϴ�.
					sec2++;
					csec2 = 0;
				}
				if(sec2==60) { //60��(=1��)�� �Ǹ� �д����� ���ڸ� 1�� ������Ű�� �ʴ�  0���� �����մϴ�.
					min2++;
					sec2 = 0;
				}
				if(min2==60) { //60��(=1�ð�)�� �Ǹ� �ô����� ���ڸ� 1�� ������Ű�� ���� 0���� �����մϴ�.
					hour2++;
					min2 = 0;
				}
				
				//��,��,�ʸ� �ּ� ���ڸ� �� �̻��� �ǵ��� ���ڿ� ������ �����ݴϴ�.
				hour = String.format("%02d", hour2);
				min = String.format("%02d", min2);
				sec = String.format("%02d", sec2);
				//�и��ʸ� ��ġ�ʷ� ��ȯ�Ͽ� csec���� ǥ���ϱ� ���� ���� ���ڸ� �������� �����ݴϴ�.
				csec = String.format("%03d", csec2);
			
				frame.repaint(); ////frame�� component�� �ٽ� �׷��� �ٲ� �ð��� ���̵��� �մϴ�.
			
			}
		}
	}
}
