package stopwatch;

/*
 * Title : Java로 구현한 Disital Stopwatch
 * Author : 전진희
 * Date : 2014.05.25
 */
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StopWatch{
	JFrame frame; //스탑워치 기능을 수행 할 frame을 선언합니다.
	JPanel panel1, panel2; //panel1은 시간을 나타내는 패널, panel2는 버튼을 배치한 패널입니다.
	JButton startBtn, stopBtn, resetBtn; //시작,중지,초기화 세 개의 버튼을 사용하기 위해 선언하였습니다.
	
	//시,분,초,센티초(=1/100초)의 초기 상태는 00으로 설정합니다.
	String hour = "00";
	String min = "00";
	String sec = "00";
	String csec = "00";
	
	//시간을 측정 할 스탑워치 클래스를 TimeDisplay라 명명하였으며 이를 timeDisplay라는 이름으로 사용합니다.
	TimeDisplay timeDisplay;	
	boolean st = false; //동작상태(start,stop)에 따라 reset버튼의 동작을 설정하기 위하여 boolean 타입의 변수를 사용합니다.
	
	//메인 메소드 입니다.
	public static void main(String[] args) {
		new StopWatch().go(); //StopWatch 클래스 내의 go()메소드를 실행합니다. 
	}  
	
	//go 메소드를 정의한 부분입니다.
	public void go() {
		//frame과 panel1,2의 객체를 생성합니다.
		frame = new JFrame();
		panel1 = new CoverPanel();
		panel2 = new JPanel();
		
		//시작버튼, 중지버튼, 초기화버튼의 객체를 생성합니다.
		startBtn = new JButton("Start");
		stopBtn = new JButton("Stop");
		resetBtn = new JButton("Reset");
		
		//처음 실행 시 stop,reset버튼은 클릭해도 별다른 동작이 없으므로 비활성화 상태로 설정합니다.
		stopBtn.setEnabled(false);
		resetBtn.setEnabled(false);
		
		//frame의 타이틀과 크기를 설정합니다.
		frame.setTitle("Stop Watch");
		frame.setSize(350, 160);
		
		//frame의 X버튼을 누르면 프로그램이 완전히 종료되도록 합니다.
		//이를 설정해주지 않으면 창을 닫았음에도 프로그램이 완전히 종료되지 않고 눈에 보이진 않지만 계속 실행 중인 상태로 존재하게 됩니다.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//panel2에는 시작,중지,초기화버튼을 배치합니다.
		panel2.add(startBtn);
		panel2.add(stopBtn);
		panel2.add(resetBtn);
	
		//panel1은 가운데에, panel2는 남쪽에 위치하도록 BorderLayout을 주어 frame에 panel1,2를 추가합니다.
		frame.getContentPane().add(panel1, BorderLayout.CENTER);
		frame.getContentPane().add(panel2, BorderLayout.SOUTH);
		frame.setResizable(false);
		frame.setVisible(true); //frame이 실제로 보이도록 합니다.
		
		//시작버튼을 클릭했을 때 동작하는 ActionListener입니다.
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				st = true; //start상태일 때, st에 true값을 줍니다.
				startBtn.setEnabled(false); //시작버튼 비활성화
				stopBtn.setEnabled(true); //중지버튼 활성화
				resetBtn.setEnabled(true); //초기화버튼 활성화
				
				timeDisplay = new TimeDisplay(); //스탑워치의 시간을 측정할 수 있도록 timeDisplay 객체를 생성합니다. 
				//frame에 보이는 시간으로부터 스탑워치를 시작하기 위해 setTime()메소드에 시,분,초,센티초 값을 설정합니다.
				timeDisplay.setTime(Integer.parseInt(hour), Integer.parseInt(min), Integer.parseInt(sec), Integer.parseInt(csec));
				
				//Thread timeDisplay를 가동합니다.
				//start()메소드 호출 시 TimeDisplay클래스 내의 run()메소드가 실행됩니다.
				timeDisplay.start();
				
			}
		});
		
		//중지버튼을 클릭했을 때 동작하는 ActionListener입니다.
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				st = false; //start상태가 아닌 경우 st값은 false로 설정합니다.
				stopBtn.setEnabled(false); //중지버튼 비활성화
				startBtn.setEnabled(true); //시작버튼 활성화
				resetBtn.setEnabled(true); //초기화버튼 활성화
				
				timeDisplay = null; //Thread가 구동되지 않도록 null로 설정합니다.
			}
		});
		
		//초기화버튼을 클릭했을 때 동작하는 ActionListener입니다.
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(st) {//스탑워치가 start상태일 때 초기화버튼을 누를 경우	
					timeDisplay.setTime(0, 0, 0, 0); //다시 0센티초 부터 스탑워치가 돌아가도록 합니다.
					
					//다시 처음부터 스탑워치가 돌아가고 있는 상태(=start상태)이므로 
					//시작버튼을 비활성화 시킵니다.
					startBtn.setEnabled(false);
				}
				else {//스탑워치가 stop상태일 때 초기화 버튼을 누를 경우
					
					startBtn.setEnabled(true); //시작버튼 활성화
					//stop상태에서 초기화 버튼을 누른 후 중지버튼, 초기화버튼은 별다른 동작을 하지 않으므로 비활성화 합니다.
					resetBtn.setEnabled(false); //초기화버튼 비활성화
					stopBtn.setEnabled(false); //중지버튼 비활성화
					
					//시,분,초,센티초를 다시 초기 상태인 00으로 설정합니다.
					hour = "00";
					min = "00";
					sec = "00";
					csec = "00";
				}
				
				frame.repaint(); //frame의 component를 다시 그려서 바뀐 시간이 보이도록 합니다.
			}
		});
	}
	
	//측정된 시간을 나타내기 위해 JPanel을 상속받아 생성한 panel 클래스입니다.
	class CoverPanel extends JPanel {
		public void paintComponent(Graphics g) { 
			//text component를 사용하지 않기 위해 paint component를 이용하여 panel에 시간이 보이도록 합니다.
	
			String time = hour+":"+min+":"+sec+"."; //시,분,초 문자열과 :을 붙여서 화면에 보일 시간 포맷을 설정합니다.

			g.setFont(new Font("Serif", Font.PLAIN, 40)); //시,분,초의 폰트와 글씨크기를 설정합니다.
			g.drawString(time, 80, 60); //시,분,초를 보일 위치에 시간을 그립니다.
			g.setFont(new Font("Serif", Font.PLAIN, 25)); //센티초의 폰트와 글씨크기를 설정합니다.
			//TimeDisplay에서 csec을 밀리초단위까지 나타내고 있으므로 앞에서 2자리까지만 잘라 센티초단위로 화면에 보이도록 합니다.
			g.drawString(csec.substring(0, 2), 230, 60); //시,분,초 옆에 센티초가 보이도록 그립니다.
		}
	}
	
	// Thread를 사용하여 스탑워치의 시간을 측정할 클래스입니다.
	class TimeDisplay extends Thread {                               
		int hour2, min2, sec2, csec2; //시,분,초,센티초를 나타낼 숫자를 선언합니다.
		
		/*
		 * 스탑워치의 시간을 설정해주는 메소드입니다.
		 * @param h 시간
		 * @param m 분
		 * @param s 초
		 * @param c 센티초
		 */
		public void setTime(int h, int m, int s, int c) {
			this.hour2 = h;
			this.min2 = m;
			this.sec2 = s;
			this.csec2 = c;
		}
		
		//Thread.start()시 동작하는 메소드 입니다. 시간을 측정하는 기능을 수행합니다.
		public void run() {
			//timeDisplay가 동작 중일 경우 센티초를 계속해서 +1합니다.
			//timeDisplay가 null이 되면 정지합니다.
			while(timeDisplay!=null) {
				try{	
					//1밀리초(=0.1센티초)마다 Thread가 멈추었다 실행되도록 합니다.
					//Thread.sleep(1) 메소드는 1밀리초를 정확히 쉬는 것이 아니라
					//1밀리초 이상 쉬는 것을 보장하기 때문에 밀리초의 오차를 줄이기 위하여 
					//10밀리초(1센티초)가 아닌 1밀리초를 사용하였습니다.
					Thread.sleep(1); 
					
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
				csec2++; //1밀리초(=1/1000초) 씩 증가합니다.
				    
				//1씩 증가함에 따라 조건문을 이용해 시,분,초,센티초를 설정 해주는 부분입니다.
				if(csec2==1000) { //1000밀리초(=1초)가 되면 초단위의 숫자를 1씩 증가시키고 밀리초를 0으로 설정합니다.
					sec2++;
					csec2 = 0;
				}
				if(sec2==60) { //60초(=1분)가 되면 분단위의 숫자를 1씩 증가시키고 초는  0으로 설정합니다.
					min2++;
					sec2 = 0;
				}
				if(min2==60) { //60분(=1시간)이 되면 시단위의 숫자를 1씩 증가시키고 분은 0으로 설정합니다.
					hour2++;
					min2 = 0;
				}
				
				//시,분,초를 최소 두자리 수 이상이 되도록 문자열 포맷을 맞춰줍니다.
				hour = String.format("%02d", hour2);
				min = String.format("%02d", min2);
				sec = String.format("%02d", sec2);
				//밀리초를 센치초로 변환하여 csec값을 표현하기 위해 먼저 세자리 포맷으로 맞춰줍니다.
				csec = String.format("%03d", csec2);
			
				frame.repaint(); ////frame의 component를 다시 그려서 바뀐 시간이 보이도록 합니댜.
			
			}
		}
	}
}
