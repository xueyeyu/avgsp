package testday27;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.text.SimpleDateFormat;
import java.util.Date;
//打印数组值的类
import java.util.Arrays;

//
/*
算法思路
找出两个卡口的数量结果集rs1和rs2
找出两个卡口的不重复车牌chepai1和chepai2
参照chepai1和chepai2寻找rs1和rs2的结果集
*/
//




//计算模块
public class jisuan  {
	//
	//类变量不放在构造器里
	//
	//
	//
	//定义和修改数据
	/***********************************************/
	//定义数据库名称
	static String dbname;                                    //
	//定义数据库用户名 ‘sa’ 为数据库管理员
	static String dbuser;
	//定义数据库密码
	static String dbpassword;
	//定义表
	static String sheet;
	//定义时间
	//循环查询(每次加时间)都会重新定义 不用final变量
	static String time1;
	static String time2;
	//定义每次循环增加的时间
	/******
	time1 和 time2都增加，可自定义容错时间
	例如 time1和time2的时间间隔为20分钟，
	每次循环增加时间为15分钟，
	03:00:00 03:20:00
	03:15:00 03:35:00
	03:30:00 03:50:00
	03:45:00 04:05:00 
	合理设置容错时间可以避免忽略如下情况
	03:18:00 进入卡口1
	03:22:00 进入卡口2
	******/
	//15为15分钟，以此类推
	 static int timex=15;
	//定义卡口编号
	 static String kakou1;
	 static String kakou2;
	//定义卡口之间的距离
	static int distance;
	//定义下游进口驶入交叉口的方向
	//1 南向北 2 东向西 3 北向南 4 西向东
	static int downfx;
	
	//定义合理速度区间
	private final double maxspeed=120/3.6;
	private final double minspeed=0.001/3.6;
	
	//定义每辆车最多能被卡口记录几次(每辆车能经过几次)
	private final int maxitem=10;


	//定义车道，
	//第一维是车道号码，第二维代表着
	//private final int[][]
	
	
	/*********************************************/
	

	//private String sheet;
	//定义日期格式
	//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	//Calendar calendar = Calendar.getInstance();
	//未知？？
	//Date myDate1;
	//Date myDate2;
	

	

	//定义基本参数
	//基础数据
	
	//定义同时跑过卡口1和卡口2的车辆数(不重复)
	private static int liuliang;
	//定义卡口1、卡口2经过的不重复的车牌号
	private static String[] chepai1 = new String [100*1000];
	private static String[] chepai2 = new String [100*1000];
	//定义sql直接导出的卡口1和卡口2的数据，
	//二维数组，第一列表示车牌，第二列表示时间，第三列表示行车方向，第四列表示车道号
	private static String rs1[][]=new String [1000*100][4];
	private static String rs2[][]=new String [1000*100][4];
	//定义sql记录卡口1和卡口2的条数，卡口1和卡口2不重复车辆的条数
	private static int rs1i,rs2i,chepai1i,chepai2i;
	//定义两个卡口都有的车牌
	private static String chepai[] = new String [100*100];
	//private static int time[] = new int [5000][5000];
	
	//按车道划分平均行程时间和平均行程车速
	
	
	//计算卡口的方向和车道流量
	

	
	
	//根据卡口进口道方向分类车辆
	//第二维 1 南向北 2 东向西 3 北向南 4 西向东
	String [][] fxchepai = new String[100*100][4];
	
	//每个卡口进口道方向和车道分类车牌和流量
	//1 南向北 2 东向西 3 北向南 4 西向东
	String [][] fx1chedaochepai = new String [100*100][9];
	String [][] fx2chedaochepai = new String [100*100][9];
	String [][] fx3chedaochepai = new String [100*100][9];
	String [][] fx4chedaochepai = new String [100*100][9];
	//根据卡口进口道分类各股车道的流量
	//第一维表示方向，第二维表示车道
	//向前推一个 0 南向北 1 东向西 2 北向南 3 西向东
	int [][] chedaoliuliang = new int [4][9];
	
	
	
	
	
	
//	public static void main (String[] args)  {
		public void run(){
			
		WindowsDemo.textArea.append(dbname+dbuser+dbpassword+sheet+kakou1+kakou2+time1+time2+distance+downfx);
//		jisuan this = new jisuan();
		Calendar calendar = Calendar.getInstance();
		Date myDate1;
		int i=0;
		for(;i<=95;i++)  {
			
			
			avgsp link=new avgsp(this.dbname,this.dbuser,this.dbpassword,this.sheet);
			//time1转换成日期格式

			
			//执行查询
			link.query(this.time1,this.time2,this.kakou1,this.kakou2);
			
			
			//获取结果 给本类的变量赋值
			this.getchepai1(link.returnchepai1());
			this.getchepai2(link.returnchepai2());
			this.getrs1(link.returnrs1());
			this.getrs2(link.returnrs2());
			//
			this.getrs1i(link.returnrs1i());
			this.getrs2i(link.returnrs2i());
			this.getchepai1i(link.returnchepai1i());
			this.getchepai2i(link.returnchepai2i());
			
			
			//WindowsDemo.textArea.append("chepai1i "+chepai1i);
			//WindowsDemo.textArea.append("rs1i "+rs1i);
			WindowsDemo.textArea.append("time1 "+time1+System.getProperty("line.separator"));
			WindowsDemo.textArea.append("time2 "+time2+System.getProperty("line.separator"));
			
			//计算流量
			this.liuliang();
			//
			//A.find1(chepai[0]);
			//计算平均行程时间和平均行程速度
			this.function();
			
			this.chedaoliuliang(this.rs1,this.rs1i,1);
			this.chedaoliuliang(this.rs1,this.rs1i,2);
			this.chedaoliuliang(this.rs1,this.rs1i,3);
			this.chedaoliuliang(this.rs1,this.rs1i,4);
			
			this.chedaoliuliang(this.rs2,this.rs2i,1);
			this.chedaoliuliang(this.rs2,this.rs2i,2);
			this.chedaoliuliang(this.rs2,this.rs2i,3);
			this.chedaoliuliang(this.rs2,this.rs2i,4);
			
			
			//时间变化
			myDate1=StrToDate(this.time1);
			calendar.setTime(myDate1);
			//加上timex时间
			calendar.add(Calendar.MINUTE,timex);
			myDate1 = calendar.getTime();
			this.time1=DateToStr(myDate1);
			
			
			myDate1=StrToDate(this.time2);
			calendar.setTime(myDate1);
			//加上timex时间
			calendar.add(Calendar.MINUTE,timex);
			myDate1 = calendar.getTime();
			this.time2=DateToStr(myDate1);
			
			this.chetime();
			
		}
		
		//********************
		//主函数结束
	}
	
	
	//计算车头时距
	public void chetime()  {
		WindowsDemo.textArea.append(Arrays.deepToString(rs1)+System.getProperty("line.separator"));
	
	}
	
		
		
		
		
	
	
	//处理函数，可调用任何函数
	public void function()  {
		int i=0;
		
		//定义临时变量传递chepai数组里的值
		String tempchepai;
		//循环卡口1
		//WindowsDemo.textArea.append("卡口 "+this.kakou1+"\n车牌\t时间\t方向\t车道\t");
		
		//定义二维数组，作为每辆车的数据
		//0 车牌 1 时间 2 方向 3 车道
		String [][] temp1 = new String [maxitem][4];
		String [][] temp2 = new String [maxitem][4];
		//不同车存储时间
		int [] sumtime = new int [9];
		//接受qujiantime的返回值并按车道传递给temp3
		int [][] temp3 = new int [maxitem][9];
		//chedaoliuliang用于计算匹配的车辆流量，用于计算平均行程车速，不包含速度过大或速度过小的流量
		int [] matchliuliang = new int [9];
		//chedaoliuliang2用于全部有记录的车辆，包括速度过大或速度过小的留恋那个
		int [] matchliuliang2=new int [9];
		double [] avgtime=new double [9];
		double [] avgspeed = new double [9];
		
		
		
		//debug
		//temp1=this.find1("f941c96603e51c3c3f6297dc385d4cdc",this.rs1,this.rs1i);
		//temp2=this.find1("f941c96603e51c3c3f6297dc385d4cdc",this.rs2,this.rs2i);
		//WindowsDemo.textArea.append("车牌\t时间\t方向\t车道\t");
		//temp1=Arrays.sort(temp1);
		//temp1=Arrays.sort(temp2);
		
		
		//debug
		//for(;i<=temp1.length;i++)
		//WindowsDemo.textArea.append(Arrays.toString(temp1[i])+Arrays.toString(temp2[i]));
		//select * from (select a,f,j,i from k0302 where e='3701033112' and f between '2016-03-02 03:00:00' and '2016-03-02 04:00:00' and a='f941c96603e51c3c3f6297dc385d4cdc') a join (select a,f,j,i from k0302 where e='3701033109' and f between '2016-03-02 03:00:00' and '2016-03-02 04:00:00' and a='f941c96603e51c3c3f6297dc385d4cdc') b on a.a=b.a 
		
		
		//注意 find1已更改为有返回值
	
	
		//WindowsDemo.textArea.append("\n车牌\t时间\t方向\t车道\t");
		//kakou1和kakou2同时计算
		//循环每辆车
		for (;i<=this.liuliang-1;i++)  {
			tempchepai=this.chepai[i];
			//取出一辆车两个卡口的数据
			temp1=this.find1(tempchepai,this.rs1,this.rs1i);
			temp2=this.find1(tempchepai,this.rs2,this.rs2i);
			//debug
			//WindowsDemo.textArea.append("temp1 "+Arrays.deepToString(temp1));
			//WindowsDemo.textArea.append("temp2 "+Arrays.deepToString(temp2));
			
			//调用接口
			//调用处理函数接口
			//int timei=0;
			//50-1为定义数组的长度
			//for (;temp1[timei][1]!=null&&timei<=50-1;timei++)  
				/////////////////////////////////////////////////
			temp3=this.qujiantime(temp1,temp2,downfx);
			//WindowsDemo.textArea.append("temp3 "+Arrays.deepToString(temp3));
			int j=0;
			int k=0;
			//长度为10
			for (;j<=temp3.length-1;j++)  {
				//长度为8
				for (;k<=temp3[0].length-1;k++)  {
					if (temp3[j][k]!=0)  {
						matchliuliang2[k]++;
						if ((distance/maxspeed)*1000<temp3[j][k] && (distance/minspeed)*1000>temp3[j][k])  {
							sumtime[k]=temp3[j][k]+sumtime[k];
							matchliuliang[k]++;
						}
						//debug
						//WindowsDemo.textArea.append("temp3 "+temp3[j][k]);
						//WindowsDemo.textArea.append("sumtime "+Arrays.toString(sumtime));
					}
				}
			}
		}
		
		//WindowsDemo.textArea.append("sumtime "+Arrays.toString(sumtime));
		//WindowsDemo.textArea.append("matchliuliang  \t"+Arrays.toString(matchliuliang));
		//WindowsDemo.textArea.append("matchliuliang2  \t"+Arrays.toString(matchliuliang2));
		int l=0;
		for(;l<=sumtime.length-1;l++)  {
			if (matchliuliang[l]!=0)  {
				avgtime[l]=sumtime[l]/(matchliuliang[l]);
				avgtime[l]=avgtime[l]/1000;
				avgspeed[l]=3.6*distance/avgtime[l];
			} 
		}
		//WindowsDemo.textArea.append("avgtime\t"+Arrays.toString(avgtime));
		//WindowsDemo.textArea.append("avgspeed\t"+Arrays.toString(avgspeed));
		
		
		
		//this.chedaoliuliang=chedaoliuliang;
		//this.avgtime=avgtime;

	}
	

	//输入卡口的记录信息,记录条数和要求的进口道方向，按方向和车道返回值
	//进口道方向 1 ↑ 2 ← 3 ↓ 4 →
	public void chedaoliuliang(String rsx[][],int rsxi,int fx)  {
		int i=0;
		int j=0;
		
		//int j1,j2,j3,j4,j5,j6,j7,j8,j0;
		String tempchedao;
		int [] temp= new int [9];
		//七个车道
		//一维数组存车牌
		String [][] chedaochepai = new String [100*100][9];
		//遍历数组
		for (;i<=rsxi-1;i++)  {
			if (rsx[i][2].equals(Integer.toString(fx)))  {
				//赋值车道号
				tempchedao=rsx[i][3];
				if (tempchedao.equals("1"))  {
					//赋值车牌
					chedaochepai[j][1]=rsx[rsxi][0];
					temp[1]++;
				}
				else if (tempchedao.equals("2"))  {
					//赋值车牌
					chedaochepai[j][2]=rsx[rsxi][0];
					temp[2]++;
				}
				else if (tempchedao.equals("3"))  {
					//赋值车牌
					chedaochepai[j][3]=rsx[rsxi][0];
					temp[3]++;
				}
				else if (tempchedao.equals("4"))  {
					//赋值车牌
					chedaochepai[j][4]=rsx[rsxi][0];
					temp[4]++;
				}
				else if (tempchedao.equals("5"))  {
					//赋值车牌
					chedaochepai[j][5]=rsx[rsxi][0];
					temp[5]++;
				}
				else if (tempchedao.equals("6"))  {
					//赋值车牌
					chedaochepai[j][6]=rsx[rsxi][0];
					temp[6]++;
				}
				else if (tempchedao.equals("7"))  {
					//赋值车牌
					chedaochepai[j][7]=rsx[rsxi][0];
					temp[7]++;
				}
				else if (tempchedao.equals("8"))  {
					//赋值车牌
					chedaochepai[j][8]=rsx[rsxi][0];
					temp[8]++;
				}
				else  {
					//赋值车牌
					chedaochepai[j][0]=rsx[rsxi][0];
					temp[0]++;
				}
			}
			else  {
				continue;
			}
		}
		//类变量chedaoliuliang是0123 减一
		//在 fx做下标数组时全部减一
		for(;j<=this.chedaoliuliang[fx-1].length-1;j++)  {
			this.chedaoliuliang[fx-1][j]=temp[j];
		}
		//WindowsDemo.textArea.append("chedaochepai \t "+Arrays.deepToString(chedaochepai));
		WindowsDemo.textArea.append("chedaoliuliang "+fx+" \t"+Arrays.toString(this.chedaoliuliang[fx-1])+System.getProperty("line.separator"));
	}
		
		
	//根据三股车流求区间车速
	//根据下游交叉口分类
	//fx为下游交叉口进口道的方向
	//输入为每个车在卡口的参数，卡口2 temp2 为下游卡口，fx为上游掉头经过的下游卡口方向
	public int [][] qujiantime(String[][] temp1 ,String[][] temp2,int fx)  {
		//
		//定义后一个参数为下游交叉口
		//设定此目的 在函数调用的时候 正反执行两遍而不是在放在此函数内，调用一次
		
		
		//debug
		//static int [] sumtime = new int [8]={0,0,0,0,0,0,0,0};
		//设置下标 i temp1 j temp2
		int i=0,j=0;
		//设置左直右的时间数组
		//定义7个车道 对应数组第二维的1-7 二维的第0个表示1-7之外的车道(大于7)
		//第一维记录每辆车通过kakou1和kakou2的时间
		int  chedaotime[][]=new int[maxitem][9]; 
		//按车道分类
		//按照下游车道分类
		for (;j<=temp2.length-1&&temp2[j][0]!=null&&temp2[j][2].equals(Integer.toString(fx));j++)  {
			//循环kakou1(temp1)
			for (;i<=temp1.length-1&&temp1[i][0]!=null /*&&!temp1[i][3].equals(fx)*/;i++)  {
				//寻找到了匹配车辆
				//WindowsDemo.textArea.append("shijian "+Arrays.toString(temp2[0])+"\nshijian"+Arrays.toString(temp1[i]));
				//关于匹配时间
				//temp1[j][1]				temp2[i][1];
				//07:00:00					07:05:00
				//07:03:00
				//这时候7:03:00要和07:05:00匹配
				//如果temp1第一个时间小于temp1的赋值
				//如果temp1第二个时间小于temp1再次赋值，
				//如果temp1第三个时间大于temp1存在第二列
				if (temp2[j][0].compareTo(temp1[i][0]) == 0 && /*(temp1[i+1][1]==null) || (*/(temp2[j][1].compareTo(temp1[i][1]) > 0)) {
					//一号车道

					
					if (temp2[j][3].equals("1"))  {
						chedaotime[j][1]=diffstrtime(temp2[j][1],temp1[i][1]);
						//WindowsDemo.textArea.append("xxxxxxxxxxxxxxxxx "+chedaotime[j][3]);
						//WindowsDemo.textArea.append("xxxxxxxxxxxxxxxxxtemp1 "+Arrays.deepToString(temp1));
						//WindowsDemo.textArea.append("xxxxxxxxxxxxxxxxxtemp2 "+Arrays.deepToString(temp2));
						//记录给sumtime，sumtime是static变量，每次执行都保留原来的值
						//sumtime[1]=chedaotime[j][1]+sumtime[1];
						//WindowsDemo.textArea.append("执行了1");
						//j++;
					}
					//二号车道
					else if (temp2[j][3].equals("2"))  {
						chedaotime[j][2]=diffstrtime(temp2[j][1],temp1[i][1]);
						//sumtime[2]=chedaotime[j][2]+sumtime[2];
						//WindowsDemo.textArea.append("执行了2");
						//j++;
					}
					//三号车道
					else if (temp2[j][3].equals("3"))  {
						chedaotime[j][3]=diffstrtime(temp2[j][1],temp1[i][1]);
						//sumtime[3]=chedaotime[j][3]+sumtime[3];
						//j++;
					}
					//四号车道
					else if (temp2[j][3].equals("4"))  {
						chedaotime[j][4]=diffstrtime(temp2[j][1],temp1[i][1]);
						//sumtime[4]=chedaotime[j][4]+sumtime[4];
						//j++;
					}
					//五号车道
					else if (temp2[j][3].equals("5"))  {
						chedaotime[j][5]=diffstrtime(temp2[j][1],temp1[i][1]);
						//sumtime[5]=chedaotime[j][5]+sumtime[5];
						//j++;
					}
					//六号车道
					else if (temp2[j][3].equals("6"))  {
						chedaotime[j][6]=diffstrtime(temp2[j][1],temp1[i][1]);
						//sumtime[6]=chedaotime[j][6]+sumtime[6];
						//j++;
					}
					//七号车道
					else if (temp2[j][3].equals("7"))  {
						chedaotime[j][7]=diffstrtime(temp2[j][1],temp1[i][1]);
						//sumtime[7]=chedaotime[j][7]+sumtime[7];
						//j++;
					}
					else if (temp2[j][3].equals("7"))  {
						chedaotime[j][7]=diffstrtime(temp2[j][1],temp1[i][1]);
						//sumtime[7]=chedaotime[j][7]+sumtime[7];
						//j++;
					}
					else if (temp2[j][3].equals("8"))  {
						chedaotime[j][8]=diffstrtime(temp2[j][1],temp1[i][1]);
						//sumtime[7]=chedaotime[j][7]+sumtime[7];
						//j++;
					}

					//其他,在8个之外
					else  {
						chedaotime[j][0]=diffstrtime(temp2[j][1],temp1[i][1]);
						//sumtime[0]=chedaotime[j][0]+sumtime[0];
						//WindowsDemo.textArea.append("执行了else");
						//j++;
					}
					
				}
				else if (temp2[j][0].compareTo(temp1[i][0]) == 0 &&temp2[j][1].compareTo(temp1[i][1]) < 0)  {
					break;
				}
				//未找到(应该是有的如果执行这一步就有问题)
				else if (temp2[j][0].compareTo(temp1[i][0]) > 0)  {
					WindowsDemo.textArea.append("未找到(应该是有的如果执行这一步就有问题)"+System.getProperty("line.separator"));
					break;
				}
				//未寻找到(车牌号是按升序排序的)
				else if (temp2[j][0].compareTo(temp1[i][0]) > 0)  {
					WindowsDemo.textArea.append("未找到(应该是有的如果执行这一步就有问题)"+System.getProperty("line.separator"));
					break;
				}
				else {
					WindowsDemo.textArea.append("未找到(应该是有的如果执行这一步就有问题)"+System.getProperty("line.separator"));
				}
			}
		}
		//每辆车根据下游车道记录，记录完毕
		

		//WindowsDemo.textArea.append(Arrays.deepToString(chedaotime));
		
		
		//运行结果如下
		//[[0, 0, 70860, 0, 0, 0, 0, 0], 
		//[0, 0, 0, 0, 0, 0, 0, 0], 
		//[0, 0, 0, 0, 0, 0, 0, 0], 
		//。。。。。。。。
		//[0, 0, 0, 0, 0, 0, 0, 0]]
		
		return chedaotime;

	}
		
	
	
	
	
	
	//在chepai1和chepai2里寻找相同的车并给类变量数组chepai赋值,liuliang是chepai有效数据的长度

	//计算流量
	//考虑到流量是个基本参数，放在类变量里不用函数返回
	public void liuliang()  {
		int liuliang=0;
		//定义不重复车牌1和车牌2的数组下标
		//i chepai1 j chepai2 k chepai
		int i=0,j=0,k=0;
		String temp;
		//debug
		//WindowsDemo.textArea.append(chepai1i+"  "+chepai2i);
		//遍历经过卡口1不重复的车牌
		//记录的条数在数组里减一
		for(;(i<=chepai1i-1)&&(j<=chepai2i-1);i++)  {
			//赋值临时变量
			temp=chepai1[i];
			
			//比较chepai1和chepai2的字符串大小(顺序)
			//目的是替换对chepai的遍历查找
			//sql中对查找已经asc正序排序
			if (temp.compareTo(chepai2[j]) == 0)  {
				//debug
				//WindowsDemo.textArea.append(temp);
				this.chepai[k]=temp;
				//debug
				//WindowsDemo.textArea.append("匹配的车辆"+chepai[k]);
				k++;
				liuliang++;
				j++;
			}
			
			else if (temp.compareTo(chepai2[j]) > 0)  {
				//debug
				//WindowsDemo.textArea.append(temp+"    "+chepai2[j]+" "+i+" "+j);
				j++;
				i--;
			}

		}
		//WindowsDemo.textArea.append(liuliang);
		this.liuliang=liuliang;
	}
	
		
	//输入要查找到车牌，要查找的数组，数组有效长度输出查找结果的数组
	
	//根据chepai数组查找rs1和rs2的位置
	//返回值为chepai在rs1和rs2中的数组下标
	//返回值为数组
	//数组第一个值是元素个数
	//第一个值下标为1
	public String[][] find1(String chepai,String[][] rsx,int rsxi)  {
		
		//定义查询返回数组 数组大小和find1(存储下标)对应
		String [][] singleche = new String [maxitem][4];
		//设置循环变量
		//i为rs1的循环变量
		//j为find1的循环变量
		int i=0,j=0;
		//每一个车在卡口的记录最多有51-1条 (第一个值记录个数)
		//int [] find1 = new int[51];
		//debug
		//WindowsDemo.textArea.append("rs1i  "+rs1i);
		//按升序排序的数组
		//chepai肯定在rs1中间，所以比较的结果是正的
		//for 循环寻找rs1的下标存在find1里
		
		//j<=maxitem-1保证存放每辆车的数组不越界
		for(;i<=rsxi-1&&j<=maxitem-1;i++)  {
			if (chepai.compareTo(rsx[i][0]) == 0)  {
				//find1[j]=i;
				//debug
				//WindowsDemo.textArea.append("寻找的下标\t"+i+"\t"+Arrays.toString(rsx[i]));
				//将记录写入singleche 
				singleche[j][0]=rsx[i][0];
				singleche[j][1]=rsx[i][1];
				singleche[j][2]=rsx[i][2];
				singleche[j][3]=rsx[i][3];
				//
				j++;
			}
			else if (chepai.compareTo(rsx[i][0]) > 0)  {
				//debug
				//WindowsDemo.textArea.append("执行>0"+i+"  "+Arrays.toString(this.rs1[i]));
				continue;
			}
			else if (chepai.compareTo(rsx[i][0]) < 0)  {
				//debug
				//WindowsDemo.textArea.append("执行<0"+i+"  "+Arrays.toString(this.rs1[i]));
				break;
			}
		}
		//find1[0]=j-1;
		//debug
		//WindowsDemo.textArea.append(i+"  "+j);
		
		//debug 对应输入卡口号输出每个车的卡口信息
		//WindowsDemo.textArea.append("singleche "+Arrays.deepToString(singleche));
		return singleche;
		
		//考虑函数的思想，不直接调用类变量，采用参数传递
		
		/*
		
		//同理 
		//寻找rs2
		//重置循环变量赋值
		i=0;
		j=1;
		int [] find2 = new int[50];
		for(;i<=rs2i-1;i++)  {
			if (chepai.compareTo(this.rs2[i][0]) == 0)  {
				find2[j]=i;
				//debug
				WindowsDemo.textArea.append("寻找的下标\t"+i+"\t"+Arrays.toString(this.rs2[i]));
				j++;
			}
			else if (chepai.compareTo(this.rs2[i][0]) > 0)  {
				//debug
				//WindowsDemo.textArea.append("执行>0"+i+"  "+Arrays.toString(this.rs1[i]));
				continue;
			}
			else if (chepai.compareTo(this.rs2[i][0]) < 0)  {
				//debug
				//WindowsDemo.textArea.append("执行<0"+i+"  "+Arrays.toString(this.rs1[i]));
				break;
			}
		}
		find2[0]=j-1;
		
		*/
		
		//考虑取出寻找数组
		
	}
			

	
	//计算时间差
	//返回数值单位为秒
	//前减后
	public int diffstrtime(String time1,String time2)  {
		//定义时间差
		int time;
		//调用函数将字符串类型转换成日期类型
		Date date1=StrToDate(time1);
		Date date2=StrToDate(time2);
		//getTime获取1970-01-01 00:00:00到本时间的时间毫秒数
		//强制转换
		time=(int)(date1.getTime()-date2.getTime());
		//转化为秒
		//time=time/1000;
		return time;
		
	}
	
	//计算速度
	public double qujianspeed(double time,double distance)  {
		//定义速度
		double speed;
		speed=distance/time;
		return speed;
	}
	

	
	//************************
	//工具函数
	
	//字符串转日期函数  不用static
	public static Date StrToDate(String str)  {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	Date date = null;
	try {
		date = format.parse(str);
		} 
	catch (ParseException e) {
		e.printStackTrace();
		}
	return date;
	}
	
	
	//将日期转成字符串
	public static String DateToStr(Date date)  {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		String time=format.format(date);
		return time;
	}


	
	
	
	//*******************************
	//从sql中获取信息;
	//从其他类获取sql 函数
	public void getchepai1(String[] chepai1)  {
		this.chepai1=chepai1;
		//debug
		//WindowsDemo.textArea.append(chepai1[100]);
	}
	
	public void getchepai2(String[] chepai2)  {
		this.chepai2=chepai2;
		//debug
		//WindowsDemo.textArea.append(chepai2[100]);
	}
	
	public void getrs1(String[][] rs1)  {
		this.rs1=rs1;
		//debug
		//WindowsDemo.textArea.append(rs1[100][2]);
	}
	public void getrs2(String[][] rs2)  {
		this.rs2=rs2;
		//debug
		//WindowsDemo.textArea.append(rs2[100][2]);
	}
	
	//获取数量函数
	public void getchepai1i(int chepai1i)  {
		this.chepai1i=chepai1i;
	}
	public void getchepai2i(int chepai2i)  {
		this.chepai2i=chepai2i;
	}
	
	public void getrs1i(int rs1i)  {
		this.rs1i=rs1i;
	}
	public void getrs2i(int rs2i)  {
		this.rs2i=rs2i;
	}
	
	//类结束
	//************************
}
	
	
	/*
	//sql查询函数和数组存储函数
	//查询卡口
	public void query(String time1 , String time2 , String kakou1 , String kakou2 )  {
		
		//初始化循环变量
		//int fxi=0;
		//记录数组存放的条数
		int rs1i=0;
		int rs2i=0;
		//记录不重复车辆的条数
		int chepai1i=0;
		int chepai2i=0;
		//执行查询操作
		PreparedStatement pstmt1=conn.prepareStatement(sql1);
		pstmt1.setString(1, kakou1);
		pstmt1.setString(2, time1);
		pstmt1.setString(3, time2);
		//pstmt.setInt(4, fx[fxi]);
		
		//获取结果
		ResultSet result1 = pstmt1.executeQuery();
		//逐行(条)读取数据
		while(result1.next())
		{
			//赋值车牌
			this.rs1[rs1i][0] = result1.getString(1);
			//赋值时间
			this.rs1[rs1i][1] = result1.getString(3);
			//赋值方向
			this.rs1[rs1i][2] = result1.getString(4);
			//赋值车道号
			this.rs1[rs1i][3] = result1.getString(5);
			rs1i++;
			
		}
		result1.close();
		
		//执行查询不重复车牌操作
		PreparedStatement pstmt1x=conn.prepareStatement(sql2);
		pstmt1x.setString(1, kakou1);
		pstmt1x.setString(2, time1);
		pstmt1x.setString(3, time2);
		
		
		ResultSet rschepai = pstmt1x.executeQuery();
		while(rschepai.next())  {
			//赋值不重复车牌
			this.chepai[chepai1i] = rschepai.getString(1);
			chepai1i++;
		}
		rschepai.close();
		
		WindowsDemo.textArea.append("卡口  "+kakou2+"  数量为  "+rs1i);
		
		//
		//**************************************************
		//****************************************************
		//**************************************************
		//
		
		int rs2i=0;
		//定义sql语句  distinct
		String sq2 = "SELECT a,b,c,d FROM " + sheet +" where b = ?  and c between ? and ? ";
		PreparedStatement pstmt2=conn.prepareStatement(sql1);
		pstmt2.setString(1, kakou2);
		pstmt2.setString(2, time1);
		pstmt2.setString(3, time2);
		//pstmt.setInt(4, fx[fxi]);
		ResultSet result2 = pstmt2.executeQuery();
		while(result2.next())
		{
			//赋值车牌
			this.rs2[i][0] = result2.getString(1);
			//赋值时间
			this.rs2[i][1] = result2.getString(3);
			//赋值方向
			this.rs2[i][2] = result2.getString(4);
			//赋值车道号
			this.rs1[i][3] = result1.getString(5);
			rs2i++; 
			
		}
		result2.close();
		
		//执行查询不重复车牌操作
		PreparedStatement pstmt2x=conn.prepareStatement(sql2);
		pstmt2x.setString(1, kakou2);
		pstmt2x.setString(2, time1);
		pstmt2x.setString(3, time2);
		
		
		ResultSet rschepai2 = pstmt2x.executeQuery();
		while(rschepai2.next())  {
			//赋值不重复车牌
			this.rschepai2[chepai2i] = rschepai2.getString(1);
			rschepai2i++;
		}
		rschepai2.close();
		
		WindowsDemo.textArea.append("卡口  "+kakou2+"  数量为  "+rs2i);
	}
	
	*/
	
	
	
	
	/*
	//尝试将此函数加入到find1中
	//计算一个车的时间差和速度
	public void function()  {
		
		int time,speed;
		
		String tempchepai;
		int[] tempfind1=new int[20];
		int i=0;
		jisuan ins=new jisuan();
		//先取出一个车牌
		tempchepai=ins.chepai[1];
		//debug 输出车牌编号和数据有几条
		//WindowsDemo.textArea.append(tempchepai);
		tempfind1=ins.find1(tempchepai);
		//debug
		//WindowsDemo.textArea.append("find1 执行完毕");
		//WindowsDemo.textArea.append(tempfind1[0]);
		int tempi=0;
		//取出rs1的相关数据
		for (i=1;i<=tempfind1[0];i++)  {
			//tempfind1里存的是chepai在rs1的数组下标
			tempi=tempfind1[i];
			//debug
			//WindowsDemo.textArea.append("tempchepai"+tempchepai);
			//WindowsDemo.textArea.append("rs1[tempi][0]"+rs1[tempi][0]);
			if (tempchepai.equals(this.rs1[tempi][0]))  {
				WindowsDemo.textArea.append("车牌为 "+this.rs1[tempi][0]);
				WindowsDemo.textArea.append("时间为 "+this.rs1[tempi][1]);
				WindowsDemo.textArea.append("方向为 "+this.rs1[tempi][2]);
				WindowsDemo.textArea.append("车道为 "+this.rs1[tempi][3]);
			}
			else {
				WindowsDemo.textArea.append("数据异常");
			}
		}
		
	}
	*/
	
