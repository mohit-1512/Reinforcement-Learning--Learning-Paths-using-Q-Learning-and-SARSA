package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import common.GridKey;
import common.QvaluesOperators;
import common.State;
import common.StateKey;

public class GridOptionFrame extends JFrame {
	static boolean firstTerminalScreenshot;
	static boolean firstDropScreenshot;
	static int iteration;
	static int operatorsAppliedInCurrIter;
	static long reward;
	static State temp;
	static int iPass;
	static double alpha=0.5;
	static double gamma=0.5;
	static int seed=21234;
	static Random randomno;
	static Character N= new Character('N');
	static Character S= new Character('S');
	static Character E= new Character('E');
	static Character W= new Character('W');
	static Character P= new Character('P');
	static Character D= new Character('D');
	static TreeMap<StateKey,State> allState=new TreeMap<>();
	static ArrayList<JPanel> gridColorResetter;
	static int expirementNo=1;

	static int getReward(char action) {
		if(action=='P' || action=='D') {
			return 12;
		}
		return -1;
	}
static int timerTime=1;
	static boolean printFlag;
	private static JPanel contentPane;
	private JButton btnStart;
	private JComboBox comboBoxExpNum;
	private JComboBox comboSeed;
	private static LinkedHashMap<GridKey,JLabel[]> allGridLabels;
	private JLabel[] label_11_Arr,label_12_Arr,label_13_Arr,label_14_Arr,label_15_Arr,label_21_Arr,label_22_Arr,label_23_Arr,label_24_Arr,label_25_Arr,label_31_Arr,label_32_Arr,label_33_Arr,label_34_Arr,label_35_Arr,label_41_Arr,label_42_Arr,label_43_Arr,label_44_Arr,label_45_Arr,label_51_Arr,label_52_Arr,label_53_Arr,label_54_Arr,label_55_Arr;
	static String upArrow="/resources/up-arrow.png";
	static String downArrow="/resources/down-arrow.png";
	static String leftArrow="/resources/left-arrow.png";
	static String rightArrow="/resources/right-arrow.png";
	static String cross="/resources/x.png";
	static String blackTie="/resources/stop.jpg";
	private JLabel lblN_11;
	private JPanel panel_15;
	private JLabel lblIcon_15,lblN_15,lblS_15,lblE_15,lblW_15;
	private static JLabel lblRewardVal;
	private static JLabel labelForIt3000;
	private JLabel labelForIt6000;
	static private JPanel panel;
	static Color redCol=new Color(255, 200,200);
	static Color blueCol=new Color(200, 200,255);
	static Color whiteCol=new Color(255, 255,255);
	static Color currentColor;
	static private JLabel lblCurrStateVal;
	static private JLabel lblCurrPassVal;
	static private JLabel lblCurrIterationVal;
	static private JLabel lblOperatorsAppliedVal;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GridOptionFrame frame = new GridOptionFrame();
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
	public GridOptionFrame() {
		initComponents();
		createEvents();

	}

	private void createEvents() {
		// TODO Auto-generated method stub
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstDropScreenshot=true;
				firstTerminalScreenshot=true;
				printFlag=false;
				System.out.println("Results");
				operatorsAppliedInCurrIter=0;
				iteration=1;
				lblCurrIterationVal.setText(iteration+"");
				
				setAllToZeroInGrid();
				expirementNo=Integer.parseInt((String) comboBoxExpNum.getSelectedItem());
				seed=Integer.parseInt((String) comboSeed.getSelectedItem());
				
				//				JOptionPane.showMessageDialog(null,comboBoxExpNum.getSelectedItem());
				randomno=new Random(seed);
				
				QvaluesOperators qval=new QvaluesOperators(Double.NEGATIVE_INFINITY,0,Double.NEGATIVE_INFINITY,0,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY); 
				State initialState=new State(1,5,0,4,4,4,4,0,0,qval,seed);
				gridColorResetter=new ArrayList<>();
				gridColorResetter.add(panel_15);
				currentColor=blueCol;
				reward=0;
				iPass=1;
				temp=initialState;
				switch (expirementNo) {
				case 1:
					Timer timer1 = new Timer(timerTime, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							if(iPass<=3000) {
								updateExp1FstPass();
								iPass++;
								if(printFlag) {
									System.out.println("Currr I+"+iPass);	
								}
							}else {
								updateExp12ndPass();
								iPass++;
								if(printFlag) {
									System.out.println("Currr I+"+iPass);	
								}							}
							if(iPass==6001) {
								executeRemaining();
								((Timer)e.getSource()).stop();
							}
						}
					});
					timer1.start();
					break;
				case 2:
					Timer timer2 = new Timer(timerTime, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							if(iPass<=200) {
								updateExp2FstPass();
								iPass++;
								if(printFlag) {
									System.out.println("Currr I+"+iPass);	
								}							}else {
									updateExp22ndPass();
									iPass++;
									if(printFlag) {
										System.out.println("Currr I+"+iPass);	
									}							}
							if(iPass==6001) {
								executeRemaining();
								((Timer)e.getSource()).stop();
							}
						}
					});
					timer2.start();
					break;
				case 3:
					
					temp.actionForPExploit=pRandom(temp);
					Timer timer3 = new Timer(timerTime, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(iPass<=200) {
								updateExp3FstPass();
								iPass++;
								if(printFlag) {
									System.out.println("Currr I+"+iPass);	
								}							
							}else{
								updateExp32ndPass();
								iPass++;
								if(printFlag) {
									System.out.println("Currr I+"+iPass);	
								}							
							}
							if(iPass==6001) {
								executeRemaining();
								((Timer)e.getSource()).stop();
							}
						}
					});
					timer3.start();
					break;
				default:
					System.out.println("Invalid Expirement Number");
					break;
				}
			}

			private void executeRemaining() {
				if(printFlag) {
					System.out.println("End of all- Printing Q Values");	
				}
				Set set = allState.entrySet();
				// Get an iterator
				Iterator it = set.iterator();
				while(it.hasNext()) {
					Map.Entry me = (Map.Entry)it.next();
					if(printFlag) {
						System.out.println("Value is: "+me.getValue());	
					}
				} 
			}
			private void updateExp3FstPass() {
				allState.put(temp.getStateKey(), temp);
				temp=applyOperator(temp,temp.actionForPExploit);				
			}
			private void updateExp32ndPass() {
				allState.put(temp.getStateKey(), temp);
				temp=applyOperator(temp,temp.actionForPExploit);					
			}
			private void updateExp2FstPass() {
				allState.put(temp.getStateKey(), temp);
				char action=pRandom(temp);
				temp=applyOperator(temp,action);
			}
			private void updateExp22ndPass() {
				allState.put(temp.getStateKey(), temp);
				char action=pExploit(temp);
				temp=applyOperator(temp,action);
			}


			private void updateExp1FstPass() {
				// TODO Auto-generated method stub
				allState.put(temp.getStateKey(), temp);
				char action=pRandom(temp);
				temp=applyOperator(temp,action);
			}
			private void updateExp12ndPass() {
				// TODO Auto-generated method stub
				allState.put(temp.getStateKey(), temp);
				char action=pGreedy(temp);
				temp=applyOperator(temp,action);
			}
			private void setAllToZeroInGrid() {
				// TODO Auto-generated method stub


				for (Entry<GridKey, JLabel[]> entry : allGridLabels.entrySet()) {
					GridKey key = entry.getKey();
					JLabel[] value = entry.getValue();
					if(printFlag) {
						System.out.println(key);	
					}
					for(int i=1;i<=4;i++) {
						value[i].setText("0");
					}
					//		The actual state space of the PD World is as follows:
					//		(i, j, x, a, b, c, d, e, f) with
					//		(i,j) is the position of the agent
					//		x is 1 if the agent carries a block and 0 if not 
					//		(a,b,c,d,e,f) are the number of blocks in cells
					//		     (1,1), (4,1), (3,3), (5,5), (5,1) and (4,4), respectively
					if((key.i==1 && key.j==1) || 
							(key.i==4 && key.j==1) ||
							(key.i==3 && key.j==3) ||
							(key.i==5 && key.j==5)) {
						value[5].setText("4");
					}

					if((key.i==5 && key.j==1) ||
							(key.i==4 && key.j==4)) {
						value[5].setText("0");
					}

				}
			}
		});
	}



	static void qtableUpdate(State state,char action,double value) {

		state.qUpdate(action,value);
	}


	static ArrayList<Character> getApplicableOperators(State state) {


		ArrayList<Character> op=new ArrayList<>();

		op.add(N);
		op.add(S);
		op.add(E);
		op.add(W);

		//		The actual state space of the PD World is as follows:
		//		(i, j, x, a, b, c, d, e, f) with
		//		(i,j) is the position of the agent
		//		x is 1 if the agent carries a block and 0 if not 
		//		(a,b,c,d,e,f) are the number of blocks in cells
		//		     (1,1), (4,1), (3,3), (5,5), (5,1) and (4,4), respectively


		//Boundary
		if(state.j-1==0) {
			op.remove(W);
		}

		if(state.j+1==6) {
			op.remove(E);
		}

		if(state.i-1==0) {
			op.remove(N);
		}

		if(state.i+1==6) {
			op.remove(S);
		}

		// For poss op D
		// Dropoff Cells: (5,1), (4,4)
		if(state.x==1) {
			if(state.i==5 && state.j==1 && state.e<8) {
				op.add(new Character('D'));
			}
			if(state.i==4 && state.j==4 && state.f<8) {
				op.add(new Character('D'));
			}
		}else {
			// For poss op P
			// Pickup: Cells: (1,1), (4,1),(3,3),(5,5)
			if((state.i==1 && state.j==1 && state.a>0) || (state.i==4 && state.j==1 && state.b>0) 
					|| (state.i==3 && state.j==3 && state.c>0)
					|| (state.i==5 && state.j==5 && state.d>0)) {
				op.add(new Character('P'));
			}
		}
		return op;
	}


	static char pRandom(State state) {
		//		PRandom: If pickup and dropoff is applicable, 
		//	     choose this operator; otherwise, choose an operator randomly
		ArrayList<Character> allAppOperators=getApplicableOperators(state);
		if(allAppOperators.contains(P)) {
			return 'P';
		}
		if(allAppOperators.contains(D)) {
			return 'D';
		}
		//		double randNum=Math.random();
		double randNum=randomno.nextDouble();
		int index=randomno.nextInt(allAppOperators.size());
		if(printFlag) {
			System.out.println("index"+index);
			System.out.println("All appl op"+allAppOperators);	
		}
		return allAppOperators.get(index);
	}
	static char pExploit(State state) {
		//		PExploit: If pickup and dropoff is applicable, choose this 
		//	    operator; otherwise, apply the applicable operator with the
		//	    highest q-value (break ties by rolling a dice for operators with
		//	    the same utility) with probability 0.85 and choose a different  
		//	    applicable operator randomly with probability 0.15. 

		ArrayList<Character> allAppOperators=getApplicableOperators(state);
		if(printFlag) {
			System.out.println("allAppOperators"+allAppOperators);	
		}
		if(allAppOperators.contains(P)) {
			return 'P';
		}
		if(allAppOperators.contains(D)) {
			return 'D';
		}
		char finAction=state.getOperatorWithMaxQvalue(allAppOperators);
		if(printFlag) {
			System.out.println(finAction);	
		}
		//		double randNum=Math.random();
		double randNum=randomno.nextDouble();
		if(printFlag) {
			System.out.println(randNum);	
		}
		if(randNum<=.85) {
			if(printFlag) {
				System.out.println("Final Action:"+finAction);	
			}
			return finAction;
		}
		ArrayList<Character> remaininAppOperator=(ArrayList<Character>) allAppOperators.clone();
		if(printFlag) {
			System.out.println(remaininAppOperator);	
		}
		remaininAppOperator.remove(new Character(finAction));
		randNum=randomno.nextDouble();
		int index=(int) (Math.floor(randNum*remaininAppOperator.size()));
		if(printFlag) {
			System.out.println("Final Action:"+allAppOperators.get(index));	
		}
		return allAppOperators.get(index);
	}

	static char pGreedy(State state) {
		//		If pickup and dropoff is applicable, choose this 
		//	    operator; otherwise, apply the applicable operator with the
		//	    highest q-value (break ties by rolling a dice for operators with
		//	    the same utility). 
		ArrayList<Character> allAppOperators=getApplicableOperators(state);
		if(allAppOperators.contains(P)) {
			return 'P';
		}
		if(allAppOperators.contains(D)) {
			return 'D';
		}
		return state.getOperatorWithMaxQvalue(allAppOperators);
	}

	static State applyOperator(State state,char operator) {
		operatorsAppliedInCurrIter++;
		lblOperatorsAppliedVal.setText(operatorsAppliedInCurrIter+"");
		State finalState=new State(state.i,state.j, state.x, state.a, state.b, state.c, state.d, state.e, state.f,new QvaluesOperators(0,0,0,0,0,0),seed);
		if(operator=='N') {
			finalState.i--;
		}
		if(operator=='S') {
			finalState.i++;
		}
		if(operator=='E') {
			finalState.j++;
		}
		if(operator=='W') {
			finalState.j--;
		}
		if(operator=='P') {
			finalState.x++;
		}
		if(operator=='D') {
			finalState.x--;
		}
		if(allState.containsKey(finalState.getStateKey())) {
			finalState=allState.get(finalState.getStateKey());
		}else {
			finalState.setQvals(getApplicableOperators(finalState));
			allState.put(finalState.getStateKey(), finalState);
		}
		finalState.a=state.a;
		finalState.b=state.b;
		finalState.c=state.c;
		finalState.d=state.d;
		finalState.e=state.e;
		finalState.f=state.f;
		if(operator=='P') {
			//			Pickup: Cells: (1,1), (4,1),(3,3),(5,5)
			if(state.i==1 && state.j==1) {
				finalState.a=state.a-1;
			}
			if(state.i==4 && state.j==1) {
				finalState.b=state.b-1;
			}
			if(state.i==3 && state.j==3) {
				finalState.c=state.c-1;
			}
			if(state.i==5 && state.j==5) {
				finalState.d=state.d-1;
			}
		}
		if(operator=='D') {
			if(state.i==5 && state.j==1) {
				finalState.e=state.e+1;
			}
			if(state.i==4 && state.j==4) {
				finalState.f=state.f+1;
			}
		}
		if(printFlag) {
			System.out.println("Current_State------"+state);
			System.out.println("Action Performed------"+operator);
			System.out.println("Next_State------"+finalState);	
		}

		
		//update q value of state
		//		Q(a,s)= (1-alpha)*Q(a,s) + 
		//		 alpha*[R(s’,a,s)+ γ*maxa’Q(a’,s’)]
		double stateQvalAction=state.getStateQvalue(operator);
		int currReward=getReward(operator);
		reward+=currReward;
		double newQvalue=0;
		if(expirementNo==3) {
			//			Q(a,s) = Q(a,s) + 
			//            α [ R(s) + γ*Q(a’,s’) - Q(a,s) ]
			char actionForSDash;
			if(iPass<=200) {
				actionForSDash=pRandom(finalState);
			}else {
				actionForSDash=pExploit(finalState);
			}
			finalState.actionForPExploit=actionForSDash;
			newQvalue=stateQvalAction+alpha*(currReward+gamma*finalState.getStateQvalue(actionForSDash)-stateQvalAction);
		}else {
			double maxQValNextState=finalState.getMaxApplicableQvalue(getApplicableOperators(finalState));
			newQvalue=(1-alpha)*stateQvalAction+alpha*(currReward+gamma*maxQValNextState);
		}
			
		qtableUpdate(state,operator,newQvalue);
		allState.put(state.getStateKey(), state);
		updateGridGraphics(state,operator,reward);
		if((iPass==3000 || iPass==6000) && expirementNo==1) {
			saveQValScreenShot(state,"Exp"+expirementNo+"_QVal_"+iPass+"Itr");
		}
		if(iPass==6000 && (expirementNo==2 || expirementNo==3)) {
			saveQValScreenShot(state,"Exp"+expirementNo+"_QVal_"+iPass+"Itr_FinalVal");
		}
		
//		if(expirementNo==2 && (state.e==8 || state.f==8) && firstDropScreenshot) {
		if(firstDropScreenshot && expirementNo==2 && (state.e==8 || state.f==8)) {
			firstDropScreenshot=false;
			saveQValScreenShot(state,"Exp"+expirementNo+"_QVal_AtFrstDropFilled");
		}
		
		if(finalState.isFinalState()) {
			if(firstTerminalScreenshot && expirementNo==2) {
				firstTerminalScreenshot=false;
				saveQValScreenShot(state,"Exp"+expirementNo+"_QVal_AtFrstTerminal");
			}
			printAllForAnalysis(finalState);
			operatorsAppliedInCurrIter=0;
			reward=0;
			iteration++;
			lblCurrIterationVal.setText(iteration+"");
			if(printFlag) {
				System.out.println("Reached Final State");
			}
			State initState=finalState.getInitialState(true);
			initState.seed=seed;
			State InitstateForQval=allState.get(finalState.getInitialState(true).getStateKey());
			initState.qvalues=InitstateForQval.qvalues;
			allState.put(initState.getStateKey(), initState);
			for (Entry<GridKey, JLabel[]> entry : allGridLabels.entrySet()) {
				GridKey key = entry.getKey();
				JLabel[] value = entry.getValue();
				//		The actual state space of the PD World is as follows:
				//		(i, j, x, a, b, c, d, e, f) with
				//		(i,j) is the position of the agent
				//		x is 1 if the agent carries a block and 0 if not 
				//		(a,b,c,d,e,f) are the number of blocks in cells
				//		     (1,1), (4,1), (3,3), (5,5), (5,1) and (4,4), respectively
				if((key.i==5 && key.j==1)) {
					if(printFlag) {
						System.out.println(key+"Set to 0");
					}
					value[5].setText("0");
				}
				if((key.i==4 && key.j==4)) {
					if(printFlag) {
						System.out.println(key+"Set to 0");
					}
					value[5].setText("0");
				}
				if((key.i==1 && key.j==1) || 
						(key.i==4 && key.j==1) ||
						(key.i==3 && key.j==3) ||
						(key.i==5 && key.j==5)) {
					value[5].setText("4");
				}
			}
//			if((iPass==3000 || iPass==6000) && expirementNo==1) {
//				saveQValScreenShot(state,"Exp"+expirementNo+"_QVal_"+iPass+"Itr");
//			}
//			if(iPass==6000 && (expirementNo==2 || expirementNo==3)) {
//				saveQValScreenShot(state,"Exp"+expirementNo+"_QVal_"+iPass+"Itr_FinalVal");
//			}
			if(expirementNo==3) {
				char actionForSDash;
				if(iPass<=200) {
					actionForSDash=pRandom(initState);
				}else {
					actionForSDash=pExploit(initState);
				}
				initState.actionForPExploit=actionForSDash;
			}
			updateGridGraphics(state,operator,reward);
			
			return initState;
		}
		
		
		return finalState;
	}

	private static void printAllForAnalysis(State state) {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("Current Pass:"+iPass);
		System.out.println("Iteration:"+iteration);
		System.out.println("Current State:"+state);
		System.out.println("Operators Applied:"+operatorsAppliedInCurrIter);
		System.out.println("Total Rewards:"+reward);
	}

	private static void saveQValScreenShot(State state,String fileName) {
		if(printFlag) {
			System.out.println("Print Pane");
		}

		setAllToZeroInGrid(1);
		putScreenShotOnPanel(fileName+"_X1");
		setAllToZeroInGrid(0);
		putScreenShotOnPanel(fileName+"_X0");
		if(state.x==1) {
			setAllToZeroInGrid(1);
		}else
			setAllToZeroInGrid(0);
	}


	private static void putScreenShotOnPanel(String imgFileName) {
		// TODO Auto-generated method stub
		BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		panel.printAll(g);
		g.dispose();
		try {
			ImageIO.write(image, "jpg", new File(imgFileName+".jpg"));
			//            ImageIO.write(image, "png", new File("Paint.png"));
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}

	private static void updateGridGraphics(State state, char operator, long reward2) {
		lblCurrPassVal.setText(iPass+"");
		JPanel oldGridPanel=gridColorResetter.get(0);
		gridColorResetter.remove(0);
		oldGridPanel.setBackground(whiteCol);
		oldGridPanel.repaint();
		GridKey keyForUpdate=new GridKey(state.i, state.j);
		JLabel[] currGraphVal=allGridLabels.get(keyForUpdate);
		NumberFormat formatter = new DecimalFormat("#0.0000");
		formatter.setRoundingMode(RoundingMode.DOWN);
		lblRewardVal.setText(formatter.format(reward2));
		switch (operator) {
		case 'N':
			currGraphVal[0].setIcon(new ImageIcon(GridOptionFrame.class.getResource(upArrow)));
			currGraphVal[1].setText(getQValFor(state,operator));
			break;
		case 'S':
			currGraphVal[0].setIcon(new ImageIcon(GridOptionFrame.class.getResource(downArrow)));
			currGraphVal[2].setText(getQValFor(state,operator));
			break;
		case 'E':
			currGraphVal[0].setIcon(new ImageIcon(GridOptionFrame.class.getResource(rightArrow)));
			currGraphVal[3].setText(getQValFor(state,operator));
			break;
		case 'W':
			currGraphVal[0].setIcon(new ImageIcon(GridOptionFrame.class.getResource(leftArrow)));
			currGraphVal[4].setText(getQValFor(state,operator));
			break;
		case 'P':
			setAllToZeroInGrid(1);
			currentColor=redCol;
			if(state.i==1 && state.j==1) {
				currGraphVal[5].setText(state.a-1+"");
			}
			if(state.i==4 && state.j==1) {
				currGraphVal[5].setText(state.b-1+"");
			}
			if(state.i==3 && state.j==3) {
				currGraphVal[5].setText(state.c-1+"");
			}
			if(state.i==5 && state.j==5) {
				if(printFlag) {
					System.out.println(state.d);
				}
				currGraphVal[5].setText(state.d-1+"");
			}
			break;
		case 'D':
			setAllToZeroInGrid(0);
			currentColor=blueCol;
			if(state.i==5 && state.j==1) {
				currGraphVal[5].setText(state.e+1+"");
			}
			if(state.i==4 && state.j==4) {
				currGraphVal[5].setText(state.f+1+"");
			}	
			break;
		default:
			break;
		}
		JPanel currGridPanel=(JPanel)currGraphVal[0].getParent();
		currGridPanel.setBackground(currentColor);
		currGridPanel.repaint();
		gridColorResetter.add(currGridPanel);
		lblCurrStateVal.setText(state.toString1());
	}




	private static void setAllToZeroInGrid(int testt) {
		NumberFormat formatter = new DecimalFormat("#0.0000");
		formatter.setRoundingMode(RoundingMode.DOWN); // Note this extra step

		for (Entry<GridKey, JLabel[]> entry : allGridLabels.entrySet()) {
			GridKey key = entry.getKey();
			JLabel[] value = entry.getValue();
			if(printFlag) {
				System.out.println(key);
			}
			value[0].setIcon(new ImageIcon(GridOptionFrame.class.getResource(cross)));
			for(int i=1;i<5;i++) {
				value[i].setText("0");
			}
		}
		for (Entry<StateKey, State> entry1 : allState.entrySet()) {
			StateKey statekey = entry1.getKey();
			State currState = entry1.getValue();
			if(currState.x==testt) {
				GridKey currKeyForUpdate=new GridKey(currState.i, currState.j);
				JLabel[] setGraphVal=allGridLabels.get(currKeyForUpdate);
				char iconSymbol=currState.getMaxForIcon();
				if(printFlag) {
					System.out.println(currState);
					System.out.println("iconSymbol--"+iconSymbol);
				}
				switch (iconSymbol) {
				case 'C':
					setGraphVal[0].setIcon(new ImageIcon(GridOptionFrame.class.getResource(blackTie)));
					break;
				case 'N':
					setGraphVal[0].setIcon(new ImageIcon(GridOptionFrame.class.getResource(upArrow)));
					break;
				case 'S':
					setGraphVal[0].setIcon(new ImageIcon(GridOptionFrame.class.getResource(downArrow)));
					break;
				case 'E':
					setGraphVal[0].setIcon(new ImageIcon(GridOptionFrame.class.getResource(rightArrow)));
					break;
				case 'W':
					setGraphVal[0].setIcon(new ImageIcon(GridOptionFrame.class.getResource(leftArrow)));
					break;
				default:
					break;
				}
				setGraphVal[1].setText(formatter.format(currState.getStateQvalue('N')));
				setGraphVal[2].setText(formatter.format(currState.getStateQvalue('S')));
				setGraphVal[3].setText(formatter.format(currState.getStateQvalue('E')));
				setGraphVal[4].setText(formatter.format(currState.getStateQvalue('W')));
			}
		}

		if(printFlag) {
			System.out.println("Ipass:"+iPass);
			System.out.println("Check Mohit");
		}
		Set set = allState.entrySet();
		// Get an iterator
		Iterator it = set.iterator();

		while(it.hasNext()) {
			Map.Entry me = (Map.Entry)it.next();
			if(printFlag) {
				System.out.print("Key is: "+me.getKey() + " & ");
				System.out.println("Value is: "+me.getValue());
			}
		} 
	}

	//		private static void putScreenShotOnPanel(int i) {
	//			// TODO Auto-generated method stub
	//			BufferedImage image=new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
	//			labelForIt3000 = new JLabel();
	//			labelForIt3000.setBorder(new TitledBorder(null, "Qvalues after 3000 Iterations", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	//	
	//			labelForIt3000.setBounds(6, 615, 704, 138);
	//			contentPane.add(labelForIt3000);
	//		}

	private static String getQValFor(State state, char operator) {
		NumberFormat formatter = new DecimalFormat("#0.00");
		formatter.setRoundingMode(RoundingMode.DOWN); // Note this extra step

		//		double temp=state.getStateQvalue(operator);
		return formatter.format(state.getStateQvalue(operator));
	}

	private void initComponents() {
		setTitle("GridWorld");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 804, 676);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnStart = new JButton("Start");

		btnStart.setBounds(6, 6, 75, 29);
		contentPane.add(btnStart);

		comboBoxExpNum = new JComboBox();
		comboBoxExpNum.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		comboBoxExpNum.setBounds(145, 7, 64, 28);
		contentPane.add(comboBoxExpNum);

		JLabel lblExpirement = new JLabel("Expirement");
		lblExpirement.setBounds(77, 11, 80, 16);
		contentPane.add(lblExpirement);

		JLabel lblSeed = new JLabel("Seed");
		lblSeed.setBounds(210, 11, 29, 16);
		contentPane.add(lblSeed);

		comboSeed = new JComboBox();
		comboSeed.setModel(new DefaultComboBoxModel(new String[] {"1", "2"}));
		comboSeed.setBounds(237, 7, 58, 28);
		contentPane.add(comboSeed);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Grid", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(6, 47, 761, 493);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(5, 5, 2, 2));
		allGridLabels=new LinkedHashMap<GridKey, JLabel[]>();

		//For GRID 1,1
		GridKey key11=new GridKey(1,1);
		label_11_Arr=new JLabel[5];
		allGridLabels.put(key11, label_11_Arr);		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_11);
		panel_11.setLayout(null);
		label_11_Arr=new JLabel[6];
		JLabel lblIcon_11 = new JLabel("");
		lblIcon_11.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_11.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_11.setBounds(63, 28, 24, 38);
		panel_11.add(lblIcon_11);
		label_11_Arr[0]=lblIcon_11;	
		lblN_11 = new JLabel("N");
		lblN_11.setBounds(26, 0, 73, 16);
		panel_11.add(lblN_11);
		label_11_Arr[1]=lblN_11;	
		JLabel lblS_11 = new JLabel("S");
		lblS_11.setBounds(26, 76, 79, 16);
		panel_11.add(lblS_11);
		label_11_Arr[2]=lblS_11;	
		JLabel lblE_11 = new JLabel("E");
		lblE_11.setBounds(89, 38, 53, 16);
		panel_11.add(lblE_11);
		label_11_Arr[3]=lblE_11;
		JLabel lblW_11 = new JLabel("W");
		lblW_11.setBounds(6, 38, 55, 16);
		panel_11.add(lblW_11);
		label_11_Arr[4]=lblW_11;

		JLabel lblPlblA = new JLabel("P:");
		lblPlblA.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblPlblA.setBounds(99, 0, 11, 16);
		panel_11.add(lblPlblA);

		JLabel lblPA = new JLabel("");
		lblPA.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblPA.setBounds(111, 0, 31, 16);
		panel_11.add(lblPA);
		label_11_Arr[5]=lblPA;
		allGridLabels.put(key11, label_11_Arr);

		//For GRID 1,2
		GridKey key12=new GridKey(1,2);
		label_12_Arr=new JLabel[5];
		allGridLabels.put(key12, label_12_Arr);		
		JPanel panel_12 = new JPanel();
		panel_12.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_12);
		panel_12.setLayout(null);
		label_12_Arr=new JLabel[5];
		JLabel lblIcon_12 = new JLabel("");
		lblIcon_12.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_12.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_12.setBounds(63, 28, 24, 38);
		panel_12.add(lblIcon_12);
		label_12_Arr[0]=lblIcon_12;	
		JLabel lblN_12 = new JLabel("N");
		lblN_12.setBounds(38, 0, 67, 16);
		panel_12.add(lblN_12);
		label_12_Arr[1]=lblN_12;	
		JLabel lblS_12 = new JLabel("S");
		lblS_12.setBounds(38, 78, 67, 16);
		panel_12.add(lblS_12);
		label_12_Arr[2]=lblS_12;	
		JLabel lblE_12 = new JLabel("E");
		lblE_12.setBounds(89, 38, 53, 16);
		panel_12.add(lblE_12);
		label_12_Arr[3]=lblE_12;
		JLabel lblW_12 = new JLabel("W");
		lblW_12.setBounds(6, 38, 55, 16);
		panel_12.add(lblW_12);
		label_12_Arr[4]=lblW_12;
		allGridLabels.put(key12, label_12_Arr);

		//For GRID 1,3
		GridKey key13=new GridKey(1,3);
		label_13_Arr=new JLabel[5];
		allGridLabels.put(key13, label_13_Arr);		
		JPanel panel_13 = new JPanel();
		panel_13.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_13);
		panel_13.setLayout(null);
		label_13_Arr=new JLabel[5];
		JLabel lblIcon_13 = new JLabel("");
		lblIcon_13.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_13.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_13.setBounds(63, 28, 24, 38);
		panel_13.add(lblIcon_13);
		label_13_Arr[0]=lblIcon_13;	
		JLabel lblN_13 = new JLabel("N");
		lblN_13.setBounds(38, 0, 67, 16);
		panel_13.add(lblN_13);
		label_13_Arr[1]=lblN_13;	
		JLabel lblS_13 = new JLabel("S");
		lblS_13.setBounds(38, 78, 67, 16);
		panel_13.add(lblS_13);
		label_13_Arr[2]=lblS_13;	
		JLabel lblE_13 = new JLabel("E");
		lblE_13.setBounds(89, 38, 53, 16);
		panel_13.add(lblE_13);
		label_13_Arr[3]=lblE_13;
		JLabel lblW_13 = new JLabel("W");
		lblW_13.setBounds(6, 38, 55, 16);
		panel_13.add(lblW_13);
		label_13_Arr[4]=lblW_13;
		allGridLabels.put(key13, label_13_Arr);


		//For GRID 1,4
		GridKey key14=new GridKey(1,4);
		label_14_Arr=new JLabel[5];
		allGridLabels.put(key14, label_14_Arr);		
		JPanel panel_14 = new JPanel();
		panel_14.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_14);
		panel_14.setLayout(null);
		label_14_Arr=new JLabel[5];
		JLabel lblIcon_14 = new JLabel("");
		lblIcon_14.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_14.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_14.setBounds(63, 28, 24, 38);
		panel_14.add(lblIcon_14);
		label_14_Arr[0]=lblIcon_14;	
		JLabel lblN_14 = new JLabel("N");
		lblN_14.setBounds(38, 0, 77, 16);
		panel_14.add(lblN_14);
		label_14_Arr[1]=lblN_14;	
		JLabel lblS_14 = new JLabel("S");
		lblS_14.setBounds(38, 78, 77, 16);
		panel_14.add(lblS_14);
		label_14_Arr[2]=lblS_14;	
		JLabel lblE_14 = new JLabel("E");
		lblE_14.setBounds(89, 38, 53, 16);
		panel_14.add(lblE_14);
		label_14_Arr[3]=lblE_14;
		JLabel lblW_14 = new JLabel("W");
		lblW_14.setBounds(6, 38, 55, 16);
		panel_14.add(lblW_14);
		label_14_Arr[4]=lblW_14;
		allGridLabels.put(key14, label_14_Arr);

		//For GRID 1,5
		GridKey key15=new GridKey(1,5);
		label_15_Arr=new JLabel[5];
		allGridLabels.put(key15, label_15_Arr);		
		panel_15 = new JPanel();
		panel_15.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_15);
		panel_15.setLayout(null);
		label_15_Arr=new JLabel[5];
		lblIcon_15 = new JLabel("");
		lblIcon_15.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_15.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_15.setBounds(63, 28, 24, 38);
		panel_15.add(lblIcon_15);
		label_15_Arr[0]=lblIcon_15;	
		lblN_15 = new JLabel("N");
		lblN_15.setBounds(18, 0, 69, 16);
		panel_15.add(lblN_15);
		label_15_Arr[1]=lblN_15;	
		lblS_15 = new JLabel("S");
		lblS_15.setBounds(18, 78, 69, 16);
		panel_15.add(lblS_15);
		label_15_Arr[2]=lblS_15;	
		lblE_15 = new JLabel("E");
		lblE_15.setBounds(89, 38, 53, 16);
		panel_15.add(lblE_15);
		label_15_Arr[3]=lblE_15;
		lblW_15 = new JLabel("W");
		lblW_15.setBounds(6, 38, 55, 16);
		panel_15.add(lblW_15);
		label_15_Arr[4]=lblW_15;

		JLabel lblI = new JLabel("Initial ");
		lblI.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblI.setBounds(99, 0, 51, 16);
		panel_15.add(lblI);
		allGridLabels.put(key15, label_15_Arr);

		//For GRID 2,1
		GridKey key21=new GridKey(2,1);
		label_21_Arr=new JLabel[5];
		allGridLabels.put(key21, label_21_Arr);		
		JPanel panel_21 = new JPanel();
		panel_21.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_21);
		panel_21.setLayout(null);
		label_21_Arr=new JLabel[5];
		JLabel lblIcon_21 = new JLabel("");
		lblIcon_21.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_21.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_21.setBounds(63, 28, 24, 38);
		panel_21.add(lblIcon_21);
		label_21_Arr[0]=lblIcon_21;	
		JLabel lblN_21 = new JLabel("N");
		lblN_21.setBounds(38, 0, 72, 16);
		panel_21.add(lblN_21);
		label_21_Arr[1]=lblN_21;	
		JLabel lblS_21 = new JLabel("S");
		lblS_21.setBounds(38, 78, 72, 16);
		panel_21.add(lblS_21);
		label_21_Arr[2]=lblS_21;	
		JLabel lblE_21 = new JLabel("E");
		lblE_21.setBounds(89, 38, 53, 16);
		panel_21.add(lblE_21);
		label_21_Arr[3]=lblE_21;
		JLabel lblW_21 = new JLabel("W");
		lblW_21.setBounds(6, 38, 55, 16);
		panel_21.add(lblW_21);
		label_21_Arr[4]=lblW_21;
		allGridLabels.put(key21, label_21_Arr);

		//For GRID 2,2
		GridKey key22=new GridKey(2,2);
		label_22_Arr=new JLabel[5];
		allGridLabels.put(key22, label_22_Arr);		
		JPanel panel_22 = new JPanel();
		panel_22.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_22);
		panel_22.setLayout(null);
		label_22_Arr=new JLabel[5];
		JLabel lblIcon_22 = new JLabel("");
		lblIcon_22.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_22.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_22.setBounds(63, 28, 24, 38);
		panel_22.add(lblIcon_22);
		label_22_Arr[0]=lblIcon_22;	
		JLabel lblN_22 = new JLabel("N");
		lblN_22.setBounds(38, 0, 69, 16);
		panel_22.add(lblN_22);
		label_22_Arr[1]=lblN_22;	
		JLabel lblS_22 = new JLabel("S");
		lblS_22.setBounds(38, 78, 69, 16);
		panel_22.add(lblS_22);
		label_22_Arr[2]=lblS_22;	
		JLabel lblE_22 = new JLabel("E");
		lblE_22.setBounds(89, 38, 53, 16);
		panel_22.add(lblE_22);
		label_22_Arr[3]=lblE_22;
		JLabel lblW_22 = new JLabel("W");
		lblW_22.setBounds(6, 38, 55, 16);
		panel_22.add(lblW_22);
		label_22_Arr[4]=lblW_22;
		allGridLabels.put(key22, label_22_Arr);

		//For GRID 2,3
		GridKey key23=new GridKey(2,3);
		label_23_Arr=new JLabel[5];
		allGridLabels.put(key23, label_23_Arr);		
		JPanel panel_23 = new JPanel();
		panel_23.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_23);
		panel_23.setLayout(null);
		label_23_Arr=new JLabel[5];
		JLabel lblIcon_23 = new JLabel("");
		lblIcon_23.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_23.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_23.setBounds(63, 28, 24, 38);
		panel_23.add(lblIcon_23);
		label_23_Arr[0]=lblIcon_23;	
		JLabel lblN_23 = new JLabel("N");
		lblN_23.setBounds(38, 0, 74, 16);
		panel_23.add(lblN_23);
		label_23_Arr[1]=lblN_23;	
		JLabel lblS_23 = new JLabel("S");
		lblS_23.setBounds(38, 78, 74, 16);
		panel_23.add(lblS_23);
		label_23_Arr[2]=lblS_23;	
		JLabel lblE_23 = new JLabel("E");
		lblE_23.setBounds(89, 38, 53, 16);
		panel_23.add(lblE_23);
		label_23_Arr[3]=lblE_23;
		JLabel lblW_23 = new JLabel("W");
		lblW_23.setBounds(6, 38, 55, 16);
		panel_23.add(lblW_23);
		label_23_Arr[4]=lblW_23;
		allGridLabels.put(key23, label_23_Arr);

		//For GRID 2,4
		GridKey key24=new GridKey(2,4);
		label_24_Arr=new JLabel[5];
		allGridLabels.put(key24, label_24_Arr);		
		JPanel panel_24 = new JPanel();
		panel_24.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_24);
		panel_24.setLayout(null);
		label_24_Arr=new JLabel[5];
		JLabel lblIcon_24 = new JLabel("");
		lblIcon_24.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_24.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_24.setBounds(63, 28, 24, 38);
		panel_24.add(lblIcon_24);
		label_24_Arr[0]=lblIcon_24;	
		JLabel lblN_24 = new JLabel("N");
		lblN_24.setBounds(38, 0, 70, 16);
		panel_24.add(lblN_24);
		label_24_Arr[1]=lblN_24;	
		JLabel lblS_24 = new JLabel("S");
		lblS_24.setBounds(38, 78, 70, 16);
		panel_24.add(lblS_24);
		label_24_Arr[2]=lblS_24;	
		JLabel lblE_24 = new JLabel("E");
		lblE_24.setBounds(89, 38, 53, 16);
		panel_24.add(lblE_24);
		label_24_Arr[3]=lblE_24;
		JLabel lblW_24 = new JLabel("W");
		lblW_24.setBounds(6, 38, 55, 16);
		panel_24.add(lblW_24);
		label_24_Arr[4]=lblW_24;
		allGridLabels.put(key24, label_24_Arr);

		//For GRID 2,5
		GridKey key25=new GridKey(2,5);
		label_25_Arr=new JLabel[5];
		allGridLabels.put(key25, label_25_Arr);		
		JPanel panel_25 = new JPanel();
		panel_25.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_25);
		panel_25.setLayout(null);
		label_25_Arr=new JLabel[5];
		JLabel lblIcon_25 = new JLabel("");
		lblIcon_25.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_25.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_25.setBounds(63, 28, 24, 38);
		panel_25.add(lblIcon_25);
		label_25_Arr[0]=lblIcon_25;	
		JLabel lblN_25 = new JLabel("N");
		lblN_25.setBounds(38, 0, 71, 16);
		panel_25.add(lblN_25);
		label_25_Arr[1]=lblN_25;	
		JLabel lblS_25 = new JLabel("S");
		lblS_25.setBounds(38, 78, 71, 16);
		panel_25.add(lblS_25);
		label_25_Arr[2]=lblS_25;	
		JLabel lblE_25 = new JLabel("E");
		lblE_25.setBounds(89, 38, 53, 16);
		panel_25.add(lblE_25);
		label_25_Arr[3]=lblE_25;
		JLabel lblW_25 = new JLabel("W");
		lblW_25.setBounds(6, 38, 55, 16);
		panel_25.add(lblW_25);
		label_25_Arr[4]=lblW_25;
		allGridLabels.put(key25, label_25_Arr);

		//For GRID 3,1
		GridKey key31=new GridKey(3,1);
		label_31_Arr=new JLabel[5];
		allGridLabels.put(key31, label_31_Arr);		
		JPanel panel_31 = new JPanel();
		panel_31.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_31);
		panel_31.setLayout(null);
		label_31_Arr=new JLabel[5];
		JLabel lblIcon_31 = new JLabel("");
		lblIcon_31.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_31.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_31.setBounds(63, 28, 24, 38);
		panel_31.add(lblIcon_31);
		label_31_Arr[0]=lblIcon_31;	
		JLabel lblN_31 = new JLabel("N");
		lblN_31.setBounds(38, 0, 79, 16);
		panel_31.add(lblN_31);
		label_31_Arr[1]=lblN_31;	
		JLabel lblS_31 = new JLabel("S");
		lblS_31.setBounds(38, 78, 79, 16);
		panel_31.add(lblS_31);
		label_31_Arr[2]=lblS_31;	
		JLabel lblE_31 = new JLabel("E");
		lblE_31.setBounds(89, 38, 53, 16);
		panel_31.add(lblE_31);
		label_31_Arr[3]=lblE_31;
		JLabel lblW_31 = new JLabel("W");
		lblW_31.setBounds(6, 38, 55, 16);
		panel_31.add(lblW_31);
		label_31_Arr[4]=lblW_31;
		allGridLabels.put(key31, label_31_Arr);

		//For GRID 3,2
		GridKey key32=new GridKey(3,2);
		label_32_Arr=new JLabel[5];
		allGridLabels.put(key32, label_32_Arr);		
		JPanel panel_32 = new JPanel();
		panel_32.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_32);
		panel_32.setLayout(null);
		label_32_Arr=new JLabel[5];
		JLabel lblIcon_32 = new JLabel("");
		lblIcon_32.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_32.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_32.setBounds(63, 28, 24, 38);
		panel_32.add(lblIcon_32);
		label_32_Arr[0]=lblIcon_32;	
		JLabel lblN_32 = new JLabel("N");
		lblN_32.setBounds(38, 0, 74, 16);
		panel_32.add(lblN_32);
		label_32_Arr[1]=lblN_32;	
		JLabel lblS_32 = new JLabel("S");
		lblS_32.setBounds(38, 78, 74, 16);
		panel_32.add(lblS_32);
		label_32_Arr[2]=lblS_32;	
		JLabel lblE_32 = new JLabel("E");
		lblE_32.setBounds(89, 38, 53, 16);
		panel_32.add(lblE_32);
		label_32_Arr[3]=lblE_32;
		JLabel lblW_32 = new JLabel("W");
		lblW_32.setBounds(6, 38, 55, 16);
		panel_32.add(lblW_32);
		label_32_Arr[4]=lblW_32;
		allGridLabels.put(key32, label_32_Arr);

		//For GRID 3,3
		GridKey key33=new GridKey(3,3);
		label_33_Arr=new JLabel[5];
		allGridLabels.put(key33, label_33_Arr);		
		JPanel panel_33 = new JPanel();
		panel_33.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_33);
		panel_33.setLayout(null);
		label_33_Arr=new JLabel[6];
		JLabel lblIcon_33 = new JLabel("");
		lblIcon_33.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_33.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_33.setBounds(63, 28, 24, 38);
		panel_33.add(lblIcon_33);
		label_33_Arr[0]=lblIcon_33;	
		JLabel lblN_33 = new JLabel("N");
		lblN_33.setBounds(18, 0, 69, 16);
		panel_33.add(lblN_33);
		label_33_Arr[1]=lblN_33;	
		JLabel lblS_33 = new JLabel("S");
		lblS_33.setBounds(18, 78, 69, 16);
		panel_33.add(lblS_33);
		label_33_Arr[2]=lblS_33;	
		JLabel lblE_33 = new JLabel("E");
		lblE_33.setBounds(89, 38, 53, 16);
		panel_33.add(lblE_33);
		label_33_Arr[3]=lblE_33;
		JLabel lblW_33 = new JLabel("W");
		lblW_33.setBounds(6, 38, 55, 16);
		panel_33.add(lblW_33);
		label_33_Arr[4]=lblW_33;

		JLabel lblPlblC = new JLabel("P:");
		lblPlblC.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblPlblC.setBounds(99, 0, 11, 16);
		panel_33.add(lblPlblC);

		JLabel lblPC = new JLabel("");
		lblPC.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblPC.setBounds(111, 0, 31, 16);
		panel_33.add(lblPC);
		label_33_Arr[5]=lblPC;
		allGridLabels.put(key33, label_33_Arr);

		//For GRID 3,4
		GridKey key34=new GridKey(3,4);
		label_34_Arr=new JLabel[5];
		allGridLabels.put(key34, label_34_Arr);		
		JPanel panel_34 = new JPanel();
		panel_34.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_34);
		panel_34.setLayout(null);
		label_34_Arr=new JLabel[5];
		JLabel lblIcon_34 = new JLabel("");
		lblIcon_34.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_34.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_34.setBounds(63, 28, 24, 38);
		panel_34.add(lblIcon_34);
		label_34_Arr[0]=lblIcon_34;	
		JLabel lblN_34 = new JLabel("N");
		lblN_34.setBounds(38, 0, 72, 16);
		panel_34.add(lblN_34);
		label_34_Arr[1]=lblN_34;	
		JLabel lblS_34 = new JLabel("S");
		lblS_34.setBounds(38, 78, 72, 16);
		panel_34.add(lblS_34);
		label_34_Arr[2]=lblS_34;	
		JLabel lblE_34 = new JLabel("E");
		lblE_34.setBounds(89, 38, 53, 16);
		panel_34.add(lblE_34);
		label_34_Arr[3]=lblE_34;
		JLabel lblW_34 = new JLabel("W");
		lblW_34.setBounds(6, 38, 55, 16);
		panel_34.add(lblW_34);
		label_34_Arr[4]=lblW_34;
		allGridLabels.put(key34, label_34_Arr);

		//For GRID 3,5
		GridKey key35=new GridKey(3,5);
		label_35_Arr=new JLabel[5];
		allGridLabels.put(key35, label_35_Arr);		
		JPanel panel_35 = new JPanel();
		panel_35.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_35);
		panel_35.setLayout(null);
		label_35_Arr=new JLabel[5];
		JLabel lblIcon_35 = new JLabel("");
		lblIcon_35.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_35.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_35.setBounds(63, 28, 24, 38);
		panel_35.add(lblIcon_35);
		label_35_Arr[0]=lblIcon_35;	
		JLabel lblN_35 = new JLabel("N");
		lblN_35.setBounds(38, 0, 77, 16);
		panel_35.add(lblN_35);
		label_35_Arr[1]=lblN_35;	
		JLabel lblS_35 = new JLabel("S");
		lblS_35.setBounds(38, 78, 77, 16);
		panel_35.add(lblS_35);
		label_35_Arr[2]=lblS_35;	
		JLabel lblE_35 = new JLabel("E");
		lblE_35.setBounds(89, 38, 53, 16);
		panel_35.add(lblE_35);
		label_35_Arr[3]=lblE_35;
		JLabel lblW_35 = new JLabel("W");
		lblW_35.setBounds(6, 38, 55, 16);
		panel_35.add(lblW_35);
		label_35_Arr[4]=lblW_35;
		allGridLabels.put(key35, label_35_Arr);

		//For GRID 4,1
		GridKey key41=new GridKey(4,1);
		label_41_Arr=new JLabel[5];
		allGridLabels.put(key41, label_41_Arr);		
		JPanel panel_41 = new JPanel();
		panel_41.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_41);
		panel_41.setLayout(null);
		label_41_Arr=new JLabel[6];
		JLabel lblIcon_41 = new JLabel("");
		lblIcon_41.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_41.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_41.setBounds(63, 28, 24, 38);
		panel_41.add(lblIcon_41);
		label_41_Arr[0]=lblIcon_41;	
		JLabel lblN_41 = new JLabel("N");
		lblN_41.setBounds(18, 0, 69, 16);
		panel_41.add(lblN_41);
		label_41_Arr[1]=lblN_41;	
		JLabel lblS_41 = new JLabel("S");
		lblS_41.setBounds(38, 78, 69, 16);
		panel_41.add(lblS_41);
		label_41_Arr[2]=lblS_41;	
		JLabel lblE_41 = new JLabel("E");
		lblE_41.setBounds(89, 38, 53, 16);
		panel_41.add(lblE_41);
		label_41_Arr[3]=lblE_41;
		JLabel lblW_41 = new JLabel("W");
		lblW_41.setBounds(6, 38, 55, 16);
		panel_41.add(lblW_41);
		label_41_Arr[4]=lblW_41;

		JLabel lblPlblB = new JLabel("P:");
		lblPlblB.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblPlblB.setBounds(99, 0, 11, 16);
		panel_41.add(lblPlblB);

		JLabel lblPB = new JLabel("");
		lblPB.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblPB.setBounds(111, 0, 31, 16);
		panel_41.add(lblPB);
		label_41_Arr[5]=lblPB;
		allGridLabels.put(key41, label_41_Arr);

		//For GRID 4,2
		GridKey key42=new GridKey(4,2);
		label_42_Arr=new JLabel[5];
		allGridLabels.put(key42, label_42_Arr);		
		JPanel panel_42 = new JPanel();
		panel_42.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_42);
		panel_42.setLayout(null);
		label_42_Arr=new JLabel[5];
		JLabel lblIcon_42 = new JLabel("");
		lblIcon_42.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_42.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_42.setBounds(63, 28, 24, 38);
		panel_42.add(lblIcon_42);
		label_42_Arr[0]=lblIcon_42;	
		JLabel lblN_42 = new JLabel("N");
		lblN_42.setBounds(38, 0, 72, 16);
		panel_42.add(lblN_42);
		label_42_Arr[1]=lblN_42;	
		JLabel lblS_42 = new JLabel("S");
		lblS_42.setBounds(38, 78, 72, 16);
		panel_42.add(lblS_42);
		label_42_Arr[2]=lblS_42;	
		JLabel lblE_42 = new JLabel("E");
		lblE_42.setBounds(89, 38, 53, 16);
		panel_42.add(lblE_42);
		label_42_Arr[3]=lblE_42;
		JLabel lblW_42 = new JLabel("W");
		lblW_42.setBounds(6, 38, 55, 16);
		panel_42.add(lblW_42);
		label_42_Arr[4]=lblW_42;
		allGridLabels.put(key42, label_42_Arr);

		//For GRID 4,3
		GridKey key43=new GridKey(4,3);
		label_43_Arr=new JLabel[5];
		allGridLabels.put(key43, label_43_Arr);		
		JPanel panel_43 = new JPanel();
		panel_43.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_43);
		panel_43.setLayout(null);
		label_43_Arr=new JLabel[5];
		JLabel lblIcon_43 = new JLabel("");
		lblIcon_43.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_43.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_43.setBounds(63, 28, 24, 38);
		panel_43.add(lblIcon_43);
		label_43_Arr[0]=lblIcon_43;	
		JLabel lblN_43 = new JLabel("N");
		lblN_43.setBounds(38, 0, 73, 16);
		panel_43.add(lblN_43);
		label_43_Arr[1]=lblN_43;	
		JLabel lblS_43 = new JLabel("S");
		lblS_43.setBounds(38, 78, 73, 16);
		panel_43.add(lblS_43);
		label_43_Arr[2]=lblS_43;	
		JLabel lblE_43 = new JLabel("E");
		lblE_43.setBounds(89, 38, 53, 16);
		panel_43.add(lblE_43);
		label_43_Arr[3]=lblE_43;
		JLabel lblW_43 = new JLabel("W");
		lblW_43.setBounds(6, 38, 55, 16);
		panel_43.add(lblW_43);
		label_43_Arr[4]=lblW_43;
		allGridLabels.put(key43, label_43_Arr);

		//For GRID 4,4
		GridKey key44=new GridKey(4,4);
		label_44_Arr=new JLabel[5];
		allGridLabels.put(key44, label_44_Arr);		
		JPanel panel_44 = new JPanel();
		panel_44.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_44);
		panel_44.setLayout(null);
		label_44_Arr=new JLabel[6];
		JLabel lblIcon_44 = new JLabel("");
		lblIcon_44.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_44.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_44.setBounds(63, 28, 24, 38);
		panel_44.add(lblIcon_44);
		label_44_Arr[0]=lblIcon_44;	
		JLabel lblN_44 = new JLabel("N");
		lblN_44.setBounds(17, 0, 70, 16);
		panel_44.add(lblN_44);
		label_44_Arr[1]=lblN_44;	
		JLabel lblS_44 = new JLabel("S");
		lblS_44.setBounds(17, 78, 70, 16);
		panel_44.add(lblS_44);
		label_44_Arr[2]=lblS_44;	
		JLabel lblE_44 = new JLabel("E");
		lblE_44.setBounds(89, 38, 53, 16);
		panel_44.add(lblE_44);
		label_44_Arr[3]=lblE_44;
		JLabel lblW_44 = new JLabel("W");
		lblW_44.setBounds(6, 38, 55, 16);
		panel_44.add(lblW_44);
		label_44_Arr[4]=lblW_44;

		JLabel lblF = new JLabel("D:");
		lblF.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblF.setBounds(97, 0, 13, 16);
		panel_44.add(lblF);

		JLabel lblPF = new JLabel("");
		lblPF.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblPF.setBounds(111, 0, 31, 16);
		panel_44.add(lblPF);
		label_44_Arr[5]=lblPF;
		allGridLabels.put(key44, label_44_Arr);


		//For GRID 4,5
		GridKey key45=new GridKey(4,5);
		label_45_Arr=new JLabel[5];
		allGridLabels.put(key45, label_45_Arr);		
		JPanel panel_45 = new JPanel();
		panel_45.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_45);
		panel_45.setLayout(null);
		label_45_Arr=new JLabel[5];
		JLabel lblIcon_45 = new JLabel("");
		lblIcon_45.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_45.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_45.setBounds(63, 28, 24, 38);
		panel_45.add(lblIcon_45);
		label_45_Arr[0]=lblIcon_45;	
		JLabel lblN_45 = new JLabel("N");
		lblN_45.setBounds(38, 0, 78, 16);
		panel_45.add(lblN_45);
		label_45_Arr[1]=lblN_45;	
		JLabel lblS_45 = new JLabel("S");
		lblS_45.setBounds(38, 78, 78, 16);
		panel_45.add(lblS_45);
		label_45_Arr[2]=lblS_45;	
		JLabel lblE_45 = new JLabel("E");
		lblE_45.setBounds(89, 38, 53, 16);
		panel_45.add(lblE_45);
		label_45_Arr[3]=lblE_45;
		JLabel lblW_45 = new JLabel("W");
		lblW_45.setBounds(6, 38, 55, 16);
		panel_45.add(lblW_45);
		label_45_Arr[4]=lblW_45;
		allGridLabels.put(key45, label_45_Arr);

		//For GRID 5,1
		GridKey key51=new GridKey(5,1);
		label_51_Arr=new JLabel[5];
		allGridLabels.put(key51, label_51_Arr);		
		JPanel panel_51 = new JPanel();
		panel_51.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_51);
		panel_51.setLayout(null);
		label_51_Arr=new JLabel[6];
		JLabel lblIcon_51 = new JLabel("");
		lblIcon_51.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_51.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_51.setBounds(63, 28, 24, 38);
		panel_51.add(lblIcon_51);
		label_51_Arr[0]=lblIcon_51;	
		JLabel lblN_51 = new JLabel("N");
		lblN_51.setBounds(19, 0, 68, 16);
		panel_51.add(lblN_51);
		label_51_Arr[1]=lblN_51;	
		JLabel lblS_51 = new JLabel("S");
		lblS_51.setBounds(31, 78, 75, 16);
		panel_51.add(lblS_51);
		label_51_Arr[2]=lblS_51;	
		JLabel lblE_51 = new JLabel("E");
		lblE_51.setBounds(89, 38, 53, 16);
		panel_51.add(lblE_51);
		label_51_Arr[3]=lblE_51;
		JLabel lblW_51 = new JLabel("W");
		lblW_51.setBounds(6, 38, 55, 16);
		panel_51.add(lblW_51);
		label_51_Arr[4]=lblW_51;

		JLabel lblPlblE = new JLabel("D:");
		lblPlblE.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblPlblE.setBounds(99, 0, 17, 16);
		panel_51.add(lblPlblE);

		JLabel lblPE = new JLabel("");
		lblPE.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblPE.setBounds(118, 0, 24, 16);
		label_51_Arr[5]=lblPE;
		panel_51.add(lblPE);
		allGridLabels.put(key51, label_51_Arr);


		//For GRID 5,2
		GridKey key52=new GridKey(5,2);
		label_52_Arr=new JLabel[5];
		allGridLabels.put(key52, label_52_Arr);		
		JPanel panel_52 = new JPanel();
		panel_52.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_52);
		panel_52.setLayout(null);
		label_52_Arr=new JLabel[5];
		JLabel lblIcon_52 = new JLabel("");
		lblIcon_52.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_52.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_52.setBounds(63, 28, 24, 38);
		panel_52.add(lblIcon_52);
		label_52_Arr[0]=lblIcon_52;	
		JLabel lblN_52 = new JLabel("N");
		lblN_52.setBounds(25, 0, 77, 16);
		panel_52.add(lblN_52);
		label_52_Arr[1]=lblN_52;	
		JLabel lblS_52 = new JLabel("S");
		lblS_52.setBounds(31, 78, 71, 16);
		panel_52.add(lblS_52);
		label_52_Arr[2]=lblS_52;	
		JLabel lblE_52 = new JLabel("E");
		lblE_52.setBounds(89, 38, 53, 16);
		panel_52.add(lblE_52);
		label_52_Arr[3]=lblE_52;
		JLabel lblW_52 = new JLabel("W");
		lblW_52.setBounds(6, 38, 55, 16);
		panel_52.add(lblW_52);
		label_52_Arr[4]=lblW_52;
		allGridLabels.put(key52, label_52_Arr);

		//For GRID 5,3
		GridKey key53=new GridKey(5,3);
		label_53_Arr=new JLabel[5];
		allGridLabels.put(key53, label_53_Arr);		
		JPanel panel_53 = new JPanel();
		panel_53.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_53);
		panel_53.setLayout(null);
		label_53_Arr=new JLabel[5];
		JLabel lblIcon_53 = new JLabel("");
		lblIcon_53.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_53.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_53.setBounds(63, 28, 24, 38);
		panel_53.add(lblIcon_53);
		label_53_Arr[0]=lblIcon_53;	
		JLabel lblN_53 = new JLabel("N");
		lblN_53.setBounds(38, 0, 69, 16);
		panel_53.add(lblN_53);
		label_53_Arr[1]=lblN_53;	
		JLabel lblS_53 = new JLabel("S");
		lblS_53.setBounds(38, 78, 69, 16);
		panel_53.add(lblS_53);
		label_53_Arr[2]=lblS_53;	
		JLabel lblE_53 = new JLabel("E");
		lblE_53.setBounds(89, 38, 53, 16);
		panel_53.add(lblE_53);
		label_53_Arr[3]=lblE_53;
		JLabel lblW_53 = new JLabel("W");
		lblW_53.setBounds(6, 38, 55, 16);
		panel_53.add(lblW_53);
		label_53_Arr[4]=lblW_53;
		allGridLabels.put(key53, label_53_Arr);

		//For GRID 5,4
		GridKey key54=new GridKey(5,4);
		label_54_Arr=new JLabel[5];
		allGridLabels.put(key54, label_54_Arr);		
		JPanel panel_54 = new JPanel();
		panel_54.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_54);
		panel_54.setLayout(null);
		label_54_Arr=new JLabel[5];
		JLabel lblIcon_54 = new JLabel("");
		lblIcon_54.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_54.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_54.setBounds(63, 28, 24, 38);
		panel_54.add(lblIcon_54);
		label_54_Arr[0]=lblIcon_54;	
		JLabel lblN_54 = new JLabel("N");
		lblN_54.setBounds(38, 0, 67, 16);
		panel_54.add(lblN_54);
		label_54_Arr[1]=lblN_54;	
		JLabel lblS_54 = new JLabel("S");
		lblS_54.setBounds(38, 78, 67, 16);
		panel_54.add(lblS_54);
		label_54_Arr[2]=lblS_54;	
		JLabel lblE_54 = new JLabel("E");
		lblE_54.setBounds(89, 38, 53, 16);
		panel_54.add(lblE_54);
		label_54_Arr[3]=lblE_54;
		JLabel lblW_54 = new JLabel("W");
		lblW_54.setBounds(6, 38, 55, 16);
		panel_54.add(lblW_54);
		label_54_Arr[4]=lblW_54;
		allGridLabels.put(key54, label_54_Arr);


		//For GRID 5,5
		GridKey key55=new GridKey(5,5);
		label_55_Arr=new JLabel[5];
		allGridLabels.put(key55, label_55_Arr);		
		JPanel panel_55 = new JPanel();
		panel_55.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_55);
		panel_55.setLayout(null);
		label_55_Arr=new JLabel[6];
		JLabel lblIcon_55 = new JLabel("");
		lblIcon_55.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon_55.setIcon(new ImageIcon(GridOptionFrame.class.getResource("/resources/x.png")));
		lblIcon_55.setBounds(63, 28, 24, 38);
		panel_55.add(lblIcon_55);
		label_55_Arr[0]=lblIcon_55;	
		JLabel lblN_55 = new JLabel("N");
		lblN_55.setBounds(16, 0, 71, 16);
		panel_55.add(lblN_55);
		label_55_Arr[1]=lblN_55;	
		JLabel lblS_55 = new JLabel("S");
		lblS_55.setBounds(26, 78, 71, 16);
		panel_55.add(lblS_55);
		label_55_Arr[2]=lblS_55;	
		JLabel lblE_55 = new JLabel("E");
		lblE_55.setBounds(89, 38, 53, 16);
		panel_55.add(lblE_55);
		label_55_Arr[3]=lblE_55;
		JLabel lblW_55 = new JLabel("W");
		lblW_55.setBounds(6, 38, 55, 16);
		panel_55.add(lblW_55);
		label_55_Arr[4]=lblW_55;

		JLabel lblPlblD = new JLabel("P:");
		lblPlblD.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblPlblD.setBounds(99, 0, 11, 16);
		panel_55.add(lblPlblD);

		JLabel lblPD = new JLabel("");
		lblPD.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblPD.setBounds(111, 0, 31, 16);
		panel_55.add(lblPD);
		label_55_Arr[5]=lblPD;
		allGridLabels.put(key55, label_55_Arr);


		panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		JLabel lblRewards = new JLabel("Total Rewards:");
		lblRewards.setBounds(6, 621, 92, 16);
		contentPane.add(lblRewards);

		lblRewardVal = new JLabel("0.0");
		lblRewardVal.setBounds(110, 621, 61, 16);
		contentPane.add(lblRewardVal);







		JLabel lblCurrState = new JLabel("Current State :");
		lblCurrState.setBounds(472, 563, 92, 16);
		contentPane.add(lblCurrState);

		lblCurrStateVal = new JLabel("");
		lblCurrStateVal.setVerticalAlignment(SwingConstants.TOP);
		lblCurrStateVal.setBounds(595, 563, 161, 16);
		contentPane.add(lblCurrStateVal);

		JLabel lblStateTag = new JLabel("i  j  x a  b c d e f");
		lblStateTag.setBounds(595, 591, 161, 16);
		contentPane.add(lblStateTag);

		JLabel lblCurrentPass = new JLabel("Current Pass:");
		lblCurrentPass.setBounds(6, 563, 92, 16);
		contentPane.add(lblCurrentPass);

		lblCurrPassVal = new JLabel("");
		lblCurrPassVal.setBounds(96, 563, 61, 16);
		contentPane.add(lblCurrPassVal);

		JLabel lblCurrentIterationLBL = new JLabel("Current Iteration:");
		lblCurrentIterationLBL.setBounds(6, 593, 116, 16);
		contentPane.add(lblCurrentIterationLBL);

		lblCurrIterationVal = new JLabel("");
		lblCurrIterationVal.setBounds(121, 593, 61, 16);
		contentPane.add(lblCurrIterationVal);

		JLabel lblOperatorsApplied = new JLabel("Operators Applied:");
		lblOperatorsApplied.setBounds(200, 591, 133, 16);
		contentPane.add(lblOperatorsApplied);

		lblOperatorsAppliedVal = new JLabel("");
		lblOperatorsAppliedVal.setBounds(341, 591, 61, 16);
		contentPane.add(lblOperatorsAppliedVal);

		//		contentPane.add(labelForIt3000);


		//		private static void putScreenShotOnPanel(int i) {
		//		// TODO Auto-generated method stub
		//		BufferedImage image=new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
		//		labelForIt3000 = new JLabel();
		//		labelForIt3000.setBorder(new TitledBorder(null, "Qvalues after 3000 Iterations", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		//
		//		labelForIt3000.setBounds(6, 615, 704, 138);
		//		contentPane.add(labelForIt3000);
		//	}

	}

	//	private void doALl() {
	//        BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
	//        Graphics2D g = image.createGraphics();
	//        paintPane.printAll(g);
	//        g.dispose();
	//        try {
	//            ImageIO.write(image, "jpg", new File("Paint.jpg"));
	//            ImageIO.write(image, "png", new File("Paint.png"));
	//        } catch (IOException exp) {
	//            exp.printStackTrace();
	//        }			
	//		}
	private ImageIcon getScaledImage(ImageIcon imageIcon, int w, int h){
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		return imageIcon;
	}	
}
