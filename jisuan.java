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
//��ӡ����ֵ����
import java.util.Arrays;

//
/*

�ҳ��������ڵ����������rs1��rs2
�ҳ��������ڵĲ��ظ�����chepai1��chepai2
����chepai1��chepai2Ѱ��rs1��rs2�Ľ����
*/
//




//����ģ��
public class jisuan  {
	//
	//����������ڹ�������
	//
	//
	//
	
	//������޸�����
	/***********************************************/
	//�������ݿ�����
	final String dbname="002";
	//�������ݿ��û��� ��sa�� Ϊ���ݿ����Ա
	final String dbuser="sa";
	//�������ݿ�����
	final String dbpassword="SQLServer";
	//�����
	final String sheet="[dbo].[01]";
	//����ʱ��
	//ѭ����ѯ(ÿ�μ�ʱ��)�������¶��� ����final����
	static String time1="2016-05-01 10:00:00 000";
	static String time2="2016-05-01 18:00:00 000";
	//����ÿ��ѭ�����ӵ�ʱ��
	/******
	time1 �� time2�����ӣ����Զ����ݴ�ʱ��
	���� time1��time2��ʱ����Ϊ20���ӣ�
	ÿ��ѭ������ʱ��Ϊ15���ӣ�
	03:00:00 03:20:00
	03:15:00 03:35:00
	03:30:00 03:50:00
	03:45:00 04:05:00 
	���������ݴ�ʱ����Ա�������������
	03:18:00 ���뿨��1
	03:22:00 ���뿨��2
	******/
	//15Ϊ15���ӣ��Դ�����
	final static int timex=15;
	//���忨�ڱ��
	private final static String kakou1="3701022049";
	private final static String kakou2="3701022116";
	//���忨��֮��ľ���
	private final int distance=222;
	//�������ν���ʻ�뽻��ڵķ���
	//1 ���� 2 ������ 3 ������ 4 ����
	private final static int downfx=1;
	
	//��������ٶ�����
	private final double maxspeed=120/3.6;
	private final double minspeed=0.001/3.6;
	
	//����ÿ��������ܱ����ڼ�¼����(ÿ�����ܾ�������)
	private final int maxitem=10;

	
	
	
	//���峵����
	//��һά�ǳ������룬�ڶ�ά������
	//private final int[][]
	
	
	/*********************************************/
	

	//private String sheet;
	//�������ڸ�ʽ
	//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	//Calendar calendar = Calendar.getInstance();
	//δ֪����
	//Date myDate1;
	//Date myDate2;
	

	

	//�����������
	//��������
	
	//����ͬʱ�ܹ�����1�Ϳ���2�ĳ�����(���ظ�)
	private static int liuliang;
	//���忨��1������2�����Ĳ��ظ��ĳ��ƺ�
	private static String[] chepai1 = new String [100*1000];
	private static String[] chepai2 = new String [100*1000];
	//����sqlֱ�ӵ����Ŀ���1�Ϳ���2�����ݣ�
	//��ά���飬��һ�б�ʾ���ƣ��ڶ��б�ʾʱ�䣬�����б�ʾ�г����򣬵����б�ʾ������
	private static String rs1[][]=new String [100*100][4];
	private static String rs2[][]=new String [100*100][4];
	//����sql��¼����1�Ϳ���2������������1�Ϳ���2���ظ�����������
	private static int rs1i,rs2i,chepai1i,chepai2i;
	//�����������ڶ��еĳ���
	private static String chepai[] = new String [100*100];
	//private static int time[] = new int [5000][5000];
	
	//����������ƽ���г�ʱ���ƽ���г̳���
	
	
	//���㿨�ڵķ���ͳ�������
	

	
	
	//���ݿ��ڽ��ڵ�������೵��
	//�ڶ�ά 1 ���� 2 ������ 3 ������ 4 ����
	String [][] fxchepai = new String[100*100][4];
	
	//ÿ�����ڽ��ڵ�����ͳ������೵�ƺ�����
	//1 ���� 2 ������ 3 ������ 4 ����
	String [][] fx1chedaochepai = new String [100*100][9];
	String [][] fx2chedaochepai = new String [100*100][9];
	String [][] fx3chedaochepai = new String [100*100][9];
	String [][] fx4chedaochepai = new String [100*100][9];
	//���ݿ��ڽ��ڵ�������ɳ���������
	//��һά��ʾ���򣬵ڶ�ά��ʾ����
	//��ǰ��һ�� 0 ���� 1 ������ 2 ������ 3 ����
	int [][] chedaoliuliang = new int [4][9];
	
	//temp3��Ϊƥ�䳵��������
	//90d48643ef3807461a2095f14be68e11	 2016-03-02 07:10:22 970	4	2	
	//90d48643ef3807461a2095f14be68e11	 2016-03-02 07:12:43 240	4	3

	String [][] pipei1 = new String [5000][8]; 
	//pipei1Ϊ��һ������
	String [][] pipei2 = new String [5000][8]; 
	String [][] pipei3 = new String [5000][8]; 
	String [][] pipei4 = new String [5000][8]; 
	String [][] pipei5 = new String [5000][8]; 
	String [][] pipei6 = new String [5000][8]; 
	String [][] pipei7 = new String [5000][8]; 
	String [][] pipei8 = new String [5000][8]; 
	//����ƥ���������ƥ�䳵������x���Զ�
	//����8��������ÿ������ƥ������Ϊ200
	int x1=0;
	int x2=0;
	int x3=0;
	int x4=0;
	int x5=0;
	int x6=0;
	int x7=0;
	int x8=0;
	
	
	public static void main (String args[])  {
		
		jisuan A = new jisuan();
		Calendar calendar = Calendar.getInstance();
		Date myDate1;
		int i=0;
		for(;i<=0;i++)  {
			
			
			avgsp link=new avgsp(A.dbname,A.dbuser,A.dbpassword,A.sheet);
			//time1ת�������ڸ�ʽ

			
			//ִ�в�ѯ
			link.query(A.time1,A.time2,A.kakou1,A.kakou2);
			
			
			//��ȡ��� ������ı�����ֵ
			A.getchepai1(link.returnchepai1());
			A.getchepai2(link.returnchepai2());
			A.getrs1(link.returnrs1());
			A.getrs2(link.returnrs2());
			//
			A.getrs1i(link.returnrs1i());
			A.getrs2i(link.returnrs2i());
			A.getchepai1i(link.returnchepai1i());
			A.getchepai2i(link.returnchepai2i());
			
			
			//System.out.println("chepai1i "+chepai1i);
			//System.out.println("rs1i "+rs1i);
			System.out.println("time1 "+time1);
			System.out.println("time2 "+time2);
			
			//��������
			A.liuliang();
			//
			//A.find1(chepai[0]);
			//����ƽ���г�ʱ���ƽ���г��ٶ�
			A.function();
			
			A.chedaoliuliang(A.rs1,A.rs1i,1);
			A.chedaoliuliang(A.rs1,A.rs1i,2);
			A.chedaoliuliang(A.rs1,A.rs1i,3);
			A.chedaoliuliang(A.rs1,A.rs1i,4);
			
			A.chedaoliuliang(A.rs2,A.rs2i,1);
			A.chedaoliuliang(A.rs2,A.rs2i,2);
			A.chedaoliuliang(A.rs2,A.rs2i,3);
			A.chedaoliuliang(A.rs2,A.rs2i,4);
			
			
			//ʱ��仯
			myDate1=StrToDate(A.time1);
			calendar.setTime(myDate1);
			//����timexʱ��
			calendar.add(Calendar.MINUTE,timex);
			myDate1 = calendar.getTime();
			A.time1=DateToStr(myDate1);
			
			
			myDate1=StrToDate(A.time2);
			calendar.setTime(myDate1);
			//����timexʱ��
			calendar.add(Calendar.MINUTE,timex);
			myDate1 = calendar.getTime();
			A.time2=DateToStr(myDate1);
			
			A.chetime();
			
		}
		
		//********************
		//����������
	}
	
	
	
	//���㳵ͷʱ��
	public void chetime()  {
		//System.out.println(Arrays.deepToString(rs1));
	
	}
	
		
		
		
		
	
	
	//���������ɵ����κκ���
	public void function()  {
		int i=0;
		
		//������ʱ��������chepai�������ֵ
		String tempchepai;
		//ѭ������1
		//System.out.println("���� "+this.kakou1+"\n����\tʱ��\t����\t����\t");
		
		//�����ά���飬��Ϊÿ����������
		//0 ���� 1 ʱ�� 2 ���� 3 ����
		String [][] temp1 = new String [maxitem][4];
		String [][] temp2 = new String [maxitem][4];
		//��ͬ���洢ʱ��
		int [] sumtime = new int [9];
		//����qujiantime�ķ���ֵ�����������ݸ�temp3
		int [][] temp3 = new int [maxitem][9];
		//chedaoliuliang���ڼ���ƥ��ĳ������������ڼ���ƽ���г̳��٣��������ٶȹ�����ٶȹ�С������
		int [] matchliuliang = new int [9];
		//chedaoliuliang2����ȫ���м�¼�ĳ����������ٶȹ�����ٶȹ�С�������Ǹ�
		int [] matchliuliang2=new int [9];
		double [] avgtime=new double [9];
		double [] avgspeed = new double [9];
		
		
		
		//debug
		//temp1=this.find1("f941c96603e51c3c3f6297dc385d4cdc",this.rs1,this.rs1i);
		//temp2=this.find1("f941c96603e51c3c3f6297dc385d4cdc",this.rs2,this.rs2i);
		//System.out.println("����\tʱ��\t����\t����\t");
		//temp1=Arrays.sort(temp1);
		//temp1=Arrays.sort(temp2);
		
		
		//debug
		//for(;i<=temp1.length;i++)
		//System.out.println(Arrays.toString(temp1[i])+Arrays.toString(temp2[i]));
		//select * from (select a,f,j,i from k0302 where e='3701033112' and f between '2016-03-02 03:00:00' and '2016-03-02 04:00:00' and a='f941c96603e51c3c3f6297dc385d4cdc') a join (select a,f,j,i from k0302 where e='3701033109' and f between '2016-03-02 03:00:00' and '2016-03-02 04:00:00' and a='f941c96603e51c3c3f6297dc385d4cdc') b on a.a=b.a 
		
		
		//ע�� find1�Ѹ���Ϊ�з���ֵ
	
	
		//System.out.println("\n����\tʱ��\t����\t����\t");
		//kakou1��kakou2ͬʱ����
		//ѭ��ÿ����
		for (;i<=this.liuliang-1;i++)  {
			tempchepai=this.chepai[i];
			//ȡ��һ�����������ڵ�����
			temp1=this.find1(tempchepai,this.rs1,this.rs1i);
			temp2=this.find1(tempchepai,this.rs2,this.rs2i);
			//debug
			//System.out.println("temp1 "+Arrays.deepToString(temp1));
			//System.out.println("temp2 "+Arrays.deepToString(temp2));
			
			//���ýӿ�
			//���ô������ӿ�
			//int timei=0;
			//50-1Ϊ��������ĳ���
			//for (;temp1[timei][1]!=null&&timei<=50-1;timei++)  
				/////////////////////////////////////////////////
			temp3=this.qujiantime(temp1,temp2,downfx);
			//System.out.println("temp3 "+Arrays.deepToString(temp3));
			int j=0;
			int k=0;
			//����Ϊ10
			for (;j<=temp3.length-1;j++)  {
				//����Ϊ8
				for (;k<=temp3[0].length-1;k++)  {
					if (temp3[j][k]!=0)  {
						matchliuliang2[k]++;
						if ((distance/maxspeed)*1000<temp3[j][k] && (distance/minspeed)*1000>temp3[j][k])  {
							//�������
							sumtime[k]=temp3[j][k]+sumtime[k];
							matchliuliang[k]++;
						}
						//debug
						//System.out.println("temp3 "+temp3[j][k]);
						//System.out.println("sumtime "+Arrays.toString(sumtime));
					}
				}
			}
		}
		
		//System.out.println("sumtime "+Arrays.toString(sumtime));
		//System.out.println("matchliuliang  \t"+Arrays.toString(matchliuliang));
		//System.out.println("matchliuliang2  \t"+Arrays.toString(matchliuliang2));
		int l=0;
		for(;l<=sumtime.length-1;l++)  {
			if (matchliuliang[l]!=0)  {
				avgtime[l]=sumtime[l]/(matchliuliang[l]);
				avgtime[l]=avgtime[l]/1000;
				avgspeed[l]=3.6*distance/avgtime[l];
			} 
		}
		//System.out.println("avgtime\t"+Arrays.toString(avgtime));
		System.out.println("avgspeed\t"+Arrays.toString(avgspeed));
		
		
		
		//this.chedaoliuliang=chedaoliuliang;
		//this.avgtime=avgtime;
		
		//������ƥ������ݴ���������ݿ���
		//System.out.println("this.pipei"+Arrays.deepToString(pipei2));
		
		
		final String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		//����SQL server����
		final String dburl="jdbc:sqlserver://localhost:1433;";
		
		
		try  {
			Class.forName(driver);    
			System.out.println("�������ݿ������ɹ�");
		}
		
		//�׳��쳣 
		catch(ClassNotFoundException e)  {    
			System.out.println("�������ݿ�����ʧ��");  
		}
		try  {
			Connection conn =
			DriverManager.getConnection(dburl+
			"databasename=master","sa","SQLServer");  
			System.out.println("���ݿ����ӳɹ�");

			PreparedStatement pstmt1=conn.prepareStatement("SELECT a,e,f,i,h into table1_all FROM [002].[dbo].[01] where e in (?,?) and f between ? and ?");
			pstmt1.setString(1,jisuan.kakou1);
			pstmt1.setString(2,jisuan.kakou2);
			pstmt1.setString(3,jisuan.time1);
			pstmt1.setString(4,jisuan.time2);
			
			int result1 = pstmt1.executeUpdate();
			if (result1==0) {
				System.out.println("ִ��ʧ��1\n");
			}
			pstmt1=conn.prepareStatement("create table temp1(q int,time1 char(25),time2 char(25),upfx char(25),chepai char(50))");
			result1 = pstmt1.executeUpdate();
			if (result1==0) {
				System.out.println("ִ��ʧ��2\n");
			}
			
			int ii=0;
			
			//����pipei
			for (;pipei1[ii][0]!=null;ii++) {
				//����pipei
					String ttime1=pipei1[ii][1];
					String ttime2=pipei1[ii][5];
					String tempfx=pipei1[ii][2];
					String temp=pipei1[ii][0];
					//����h
					
					//ͳ�������ο���time1��time2���������������h�ǳ���
					//temp��ƥ��ɹ��ĳ��ĳ��ƺ�
					pstmt1=conn.prepareStatement("insert into temp1 values ((select count(*) from table1_all where i=? and h=1 and e = ? and f between ? and ?),?,?,?,?)");
					pstmt1.setInt(1, jisuan.downfx);
					pstmt1.setString(2, jisuan.kakou2);
					pstmt1.setString(3, ttime1);
					pstmt1.setString(4, ttime2);
					pstmt1.setString(5, ttime1);
					pstmt1.setString(6, ttime2);
					
					//���ν��뷽��
					pstmt1.setString(7, tempfx);
					pstmt1.setString(8, temp);
					result1 = pstmt1.executeUpdate();
					if (result1==0) {
						System.out.println("ִ��ʧ��3\n");
					}
			}
			
		}
		
		catch(SQLException e)  {
			e.printStackTrace();
		}
	}
	

	
	
	
	//���뿨�ڵļ�¼��Ϣ,��¼������Ҫ��Ľ��ڵ����򣬰�����ͳ�������ֵ
	//���ڵ����� 1 �� 2 �� 3 �� 4 ��
	public void chedaoliuliang(String rsx[][],int rsxi,int fx)  {
		int i=0;
		int j=0;
		
		//int j1,j2,j3,j4,j5,j6,j7,j8,j0;
		String tempchedao;
		int [] temp= new int [9];
		//�߸�����
		//һά����泵��
		String [][] chedaochepai = new String [100*100][9];
		//��������
		for (;i<=rsxi-1;i++)  {
			if (rsx[i][2].equals(Integer.toString(fx)))  {
				//��ֵ������
				tempchedao=rsx[i][3];
				if (tempchedao.equals("1"))  {
					//��ֵ����
					chedaochepai[temp[1]][1]=rsx[i][0];
					temp[1]++;
				}
				else if (tempchedao.equals("2"))  {
					//��ֵ����
					chedaochepai[temp[2]][2]=rsx[i][0];
					temp[2]++;
				}
				else if (tempchedao.equals("3"))  {
					//��ֵ����
					chedaochepai[temp[3]][3]=rsx[i][0];
					temp[3]++;
				}
				else if (tempchedao.equals("4"))  {
					//��ֵ����
					chedaochepai[temp[4]][4]=rsx[i][0];
					temp[4]++;
				}
				else if (tempchedao.equals("5"))  {
					//��ֵ����
					chedaochepai[temp[5]][5]=rsx[i][0];
					temp[5]++;
				}
				else if (tempchedao.equals("6"))  {
					//��ֵ����
					chedaochepai[temp[6]][6]=rsx[i][0];
					temp[6]++;
				}
				else if (tempchedao.equals("7"))  {
					//��ֵ����
					chedaochepai[temp[7]][7]=rsx[i][0];
					temp[7]++;
				}
				else if (tempchedao.equals("8"))  {
					//��ֵ����
					chedaochepai[temp[8]][8]=rsx[i][0];
					temp[8]++;
				}
				else  {
					//��ֵ����
					chedaochepai[temp[0]][0]=rsx[i][0];
					temp[0]++;
				}
			}
			else  {
				continue;
			}
		}
		//�����chedaoliuliang��0123 ��һ
		//�� fx���±�����ʱȫ����һ
		for(;j<=this.chedaoliuliang[fx-1].length-1;j++)  {
			this.chedaoliuliang[fx-1][j]=temp[j];
		}
		//System.out.println("chedaochepai"+Arrays.deepToString(chedaochepai));
		//System.out.println("chedaochepai \t "+Arrays.deepToString(chedaochepai));
		System.out.println("chedaoliuliang "+fx+" \t"+Arrays.toString(this.chedaoliuliang[fx-1]));
	}
		
	
	//�������ɳ��������䳵��
	//�������ν���ڷ���
	//fxΪ���ν���ڽ��ڵ��ķ���
	//����Ϊÿ�����ڿ��ڵĲ���������2 temp2 Ϊ���ο��ڣ�fxΪ���ε�ͷ���������ο��ڷ���
	public int [][] qujiantime(String[][] temp1 ,String[][] temp2,int fx)  {
		//
		//�����һ������Ϊ���ν����
		//�趨��Ŀ�� �ں������õ�ʱ�� ����ִ������������ڷ��ڴ˺����ڣ�����һ��
		
		
		//debug
		//static int [] sumtime = new int [8]={0,0,0,0,0,0,0,0};
		//�����±� i temp1 j temp2
		int i=0,j=0;
		//������ֱ�ҵ�ʱ������
		//����7������ ��Ӧ����ڶ�ά��1-7 ��ά�ĵ�0����ʾ1-7֮��ĳ���(����7)
		//��һά��¼ÿ����ͨ��kakou1��kakou2��ʱ��
		int  chedaotime[][]=new int[maxitem][9]; 
		//����������
		//�������γ�������
		for (;j<=temp2.length-1&&temp2[j][0]!=null&&temp2[j][2].equals(Integer.toString(fx));j++)  {
			//ѭ��kakou1(temp1)
			for (;i<=temp1.length-1&&temp1[i][0]!=null /*&&!temp1[i][3].equals(fx)*/;i++)  {
				//Ѱ�ҵ���ƥ�䳵��
				//System.out.println("shijian "+Arrays.toString(temp2[0])+"\nshijian"+Arrays.toString(temp1[i]));
				//����ƥ��ʱ��
				//temp1[j][1]				temp2[i][1];
				//07:00:00					07:05:00
				//07:03:00
				//��ʱ��7:03:00Ҫ��07:05:00ƥ��
				//���temp1��һ��ʱ��С��temp1�ĸ�ֵ
				//���temp1�ڶ���ʱ��С��temp1�ٴθ�ֵ��
				//���temp1������ʱ�����temp1���ڵڶ���
				if (temp2[j][0].compareTo(temp1[i][0]) == 0 && /*(temp1[i+1][1]==null) || (*/(temp2[j][1].compareTo(temp1[i][1]) > 0)) {
					//һ�ų���

					
					if (temp2[j][3].equals("1"))  {
						//debug
						//System.out.println("gettemp"+Arrays.toString(getpipei(temp1[i],temp2[j])));
						this.pipei1[this.x1]=getpipei(temp1[i],temp2[j]);
						this.x1++;
						//System.out.println("pipei"+Arrays.toString(pipei[j]));
						
						
						chedaotime[j][1]=diffstrtime(temp2[j][1],temp1[i][1]);
						//System.out.println("xxxxxxxxxxxxxxxxx "+chedaotime[j][3]);
						//System.out.println("xxxxxxxxxxxxxxxxxtemp1 "+Arrays.deepToString(temp1));
						//System.out.println("xxxxxxxxxxxxxxxxxtemp2 "+Arrays.deepToString(temp2));
						//��¼��sumtime��sumtime��static������ÿ��ִ�ж�����ԭ����ֵ
						//sumtime[1]=chedaotime[j][1]+sumtime[1];
						//System.out.println("ִ����1");
						//j++;
					}
					//���ų���
					else if (temp2[j][3].equals("2"))  {
						this.pipei2[this.x2]=getpipei(temp1[i],temp2[j]);
						this.x2++;
						
						chedaotime[j][2]=diffstrtime(temp2[j][1],temp1[i][1]);
						//sumtime[2]=chedaotime[j][2]+sumtime[2];
						//System.out.println("ִ����2");
						//j++;
					}
					//���ų���
					else if (temp2[j][3].equals("3"))  {
						this.pipei3[this.x3]=getpipei(temp1[i],temp2[j]);
						this.x3++;
						
						chedaotime[j][3]=diffstrtime(temp2[j][1],temp1[i][1]);
						//sumtime[3]=chedaotime[j][3]+sumtime[3];
						//j++;
					}
					//�ĺų���
					else if (temp2[j][3].equals("4"))  {
						this.pipei4[this.x4]=getpipei(temp1[i],temp2[j]);
						this.x4++;
						
						chedaotime[j][4]=diffstrtime(temp2[j][1],temp1[i][1]);
						//sumtime[4]=chedaotime[j][4]+sumtime[4];
						//j++;
					}
					//��ų���
					else if (temp2[j][3].equals("5"))  {
						this.pipei5[this.x5]=getpipei(temp1[i],temp2[j]);
						this.x5++;
						
						chedaotime[j][5]=diffstrtime(temp2[j][1],temp1[i][1]);
						//sumtime[5]=chedaotime[j][5]+sumtime[5];
						//j++;
					}
					//���ų���
					else if (temp2[j][3].equals("6"))  {
						this.pipei6[this.x6]=getpipei(temp1[i],temp2[j]);
						this.x6++;
						
						chedaotime[j][6]=diffstrtime(temp2[j][1],temp1[i][1]);
						//sumtime[6]=chedaotime[j][6]+sumtime[6];
						//j++;
					}
					//�ߺų���
					else if (temp2[j][3].equals("7"))  {
						this.pipei7[this.x7]=getpipei(temp1[i],temp2[j]);
						this.x7++;
						
						chedaotime[j][7]=diffstrtime(temp2[j][1],temp1[i][1]);
						//sumtime[7]=chedaotime[j][7]+sumtime[7];
						//j++;
					}
					//�˺ų���
					else if (temp2[j][3].equals("8"))  {
						this.pipei8[this.x8]=getpipei(temp1[i],temp2[j]);
						this.x8++;
						
						
						chedaotime[j][8]=diffstrtime(temp2[j][1],temp1[i][1]);
						//sumtime[7]=chedaotime[j][7]+sumtime[7];
						//j++;
					}

					//����,��8��֮��
					else  {
						chedaotime[j][0]=diffstrtime(temp2[j][1],temp1[i][1]);
						//sumtime[0]=chedaotime[j][0]+sumtime[0];
						//System.out.println("ִ����else");
						//j++;
					}
					
				}
				else if (temp2[j][0].compareTo(temp1[i][0]) == 0 &&temp2[j][1].compareTo(temp1[i][1]) < 0)  {
					break;
					//�Ⱦ�������2�ھ�������1��ɸȥ
				}
				//δ�ҵ�(Ӧ�����е����ִ����һ����������)
				else if (temp2[j][0].compareTo(temp1[i][0]) > 0)  {
					System.out.println("���Ʋ�ƥ�䣡\n");
					break;
				}
				//δѰ�ҵ�(���ƺ��ǰ����������)
				else if (temp2[j][0].compareTo(temp1[i][0]) < 0)  {
					System.out.println("δ�ҵ�(Ӧ�����е����ִ����һ����������)");
					break;
				}
				else {
					//System.out.println("δ�ҵ�(Ӧ�����е����ִ����һ����������)");
				}
			}
		}
		//ÿ�����������γ�����¼����¼���
		

		//System.out.println(Arrays.deepToString(chedaotime));
		
		
		//���н������
		//[[0, 0, 70860, 0, 0, 0, 0, 0], 
		//[0, 0, 0, 0, 0, 0, 0, 0], 
		//[0, 0, 0, 0, 0, 0, 0, 0], 
		//����������������
		//[0, 0, 0, 0, 0, 0, 0, 0]]
		
		return chedaotime;
		

	}
		
	
	
	
	
	
	//��chepai1��chepai2��Ѱ����ͬ�ĳ��������������chepai��ֵ,liuliang��chepai��Ч���ݵĳ���

	//��������
	//���ǵ������Ǹ���������������������ﲻ�ú�������
	public void liuliang()  {
		int liuliang=0;
		//���岻�ظ�����1�ͳ���2�������±�
		//i chepai1 j chepai2 k chepai
		int i=0,j=0,k=0;
		String temp;
		//debug
		//System.out.println(chepai1i+"  "+chepai2i);
		//������������1���ظ��ĳ���
		//��¼���������������һ
		for(;(i<=chepai1i-1)&&(j<=chepai2i-1);i++)  {
			//��ֵ��ʱ����
			temp=chepai1[i];
			
			//�Ƚ�chepai1��chepai2���ַ�����С(˳��)
			//Ŀ�����滻��chepai�ı�������
			//sql�жԲ����Ѿ�asc��������
			if (temp.compareTo(chepai2[j]) == 0)  {
				//debug
				//System.out.println(temp);
				this.chepai[k]=temp;
				//debug
				//System.out.println("ƥ��ĳ���"+chepai[k]);
				k++;
				liuliang++;
				j++;
			}
			
			else if (temp.compareTo(chepai2[j]) > 0)  {
				//debug
				//System.out.println(temp+"    "+chepai2[j]+" "+i+" "+j);
				j++;
				i--;
			}

		}
		//System.out.println(liuliang);
		this.liuliang=liuliang;
	}


	//����Ҫ���ҵ����ƣ�Ҫ���ҵ����飬������Ч����������ҽ��������
	
	//����chepai�������rs1��rs2��λ��
	//����ֵΪchepai��rs1��rs2�е������±�
	//����ֵΪ����
	//�����һ��ֵ��Ԫ�ظ���
	//��һ��ֵ�±�Ϊ1
	public String[][] find1(String chepai,String[][] rsx,int rsxi)  {
		
		//�����ѯ�������� �����С��find1(�洢�±�)��Ӧ
		String [][] singleche = new String [maxitem][4];
		//����ѭ������
		//iΪrs1��ѭ������
		//jΪfind1��ѭ������
		int i=0,j=0;
		//ÿһ�����ڿ��ڵļ�¼�����51-1�� (��һ��ֵ��¼����)
		//int [] find1 = new int[51];
		//debug
		//System.out.println("rs1i  "+rs1i);
		//���������������
		//chepai�϶���rs1�м䣬���ԱȽϵĽ��������
		//for ѭ��Ѱ��rs1���±����find1��
		
		//j<=maxitem-1��֤���ÿ���������鲻Խ��
		for(;i<=rsxi-1&&j<=maxitem-1;i++)  {
			if (chepai.compareTo(rsx[i][0]) == 0)  {
				//find1[j]=i;
				//debug
				//System.out.println("Ѱ�ҵ��±�\t"+i+"\t"+Arrays.toString(rsx[i]));
				//����¼д��singleche 
				singleche[j][0]=rsx[i][0];
				singleche[j][1]=rsx[i][1];
				singleche[j][2]=rsx[i][2];
				singleche[j][3]=rsx[i][3];
				//
				j++;
			}
			else if (chepai.compareTo(rsx[i][0]) > 0)  {
				//debug
				//System.out.println("ִ��>0"+i+"  "+Arrays.toString(this.rs1[i]));
				continue;
			}
			else if (chepai.compareTo(rsx[i][0]) < 0)  {
				//debug
				//System.out.println("ִ��<0"+i+"  "+Arrays.toString(this.rs1[i]));
				break;
			}
		}
		//find1[0]=j-1;
		//debug
		//System.out.println(i+"  "+j);
		
		//debug ��Ӧ���뿨�ں����ÿ�����Ŀ�����Ϣ
		System.out.println("singleche "+Arrays.deepToString(singleche));
		return singleche;
		
		//���Ǻ�����˼�룬��ֱ�ӵ�������������ò�������
		
		/*
		
		//ͬ�� 
		//Ѱ��rs2
		//����ѭ��������ֵ
		i=0;
		j=1;
		int [] find2 = new int[50];
		for(;i<=rs2i-1;i++)  {
			if (chepai.compareTo(this.rs2[i][0]) == 0)  {
				find2[j]=i;
				//debug
				System.out.println("Ѱ�ҵ��±�\t"+i+"\t"+Arrays.toString(this.rs2[i]));
				j++;
			}
			else if (chepai.compareTo(this.rs2[i][0]) > 0)  {
				//debug
				//System.out.println("ִ��>0"+i+"  "+Arrays.toString(this.rs1[i]));
				continue;
			}
			else if (chepai.compareTo(this.rs2[i][0]) < 0)  {
				//debug
				//System.out.println("ִ��<0"+i+"  "+Arrays.toString(this.rs1[i]));
				break;
			}
		}
		find2[0]=j-1;
		
		*/
		
		//����ȡ��Ѱ������
		
	}
			

	
	//����ʱ���
	//������ֵ��λΪ��
	//ǰ����
	public int diffstrtime(String time1,String time2)  {
		//����ʱ���
		int time;
		//���ú������ַ�������ת������������
		Date date1=StrToDate(time1);
		Date date2=StrToDate(time2);
		//getTime��ȡ1970-01-01 00:00:00����ʱ���ʱ�������
		//ǿ��ת��
		
		//!!!
		//int 4 �ֽ� �з������͵����ֵΪ 2147483647 λ
		//��������ʱ���Ϊ 2147483647/��3600*1000��=396Сʱ
		time=(int)(date1.getTime()-date2.getTime());
		//ת��Ϊ��
		//time=time/1000;
		return time;
		
	}
	
	//�����ٶ�
	public double qujianspeed(double time,double distance)  {
		//�����ٶ�
		double speed;
		speed=distance/time;
		return speed;
	}
	

	
	//************************
	//���ߺ���
	
	//�ַ���ת���ں���  ����static
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
	
	
	//������ת���ַ���
	public static String DateToStr(Date date)  {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		String time=format.format(date);
		return time;
	}


	
	
	
	//*******************************
	//��sql�л�ȡ��Ϣ;
	//���������ȡsql ����
	public void getchepai1(String[] chepai1)  {
		this.chepai1=chepai1;
		//debug
		//System.out.println(chepai1[100]);
	}
	
	public void getchepai2(String[] chepai2)  {
		this.chepai2=chepai2;
		//debug
		//System.out.println(chepai2[100]);
	}
	
	public void getrs1(String[][] rs1)  {
		this.rs1=rs1;
		//debug
		//System.out.println(rs1[100][2]);
	}
	public void getrs2(String[][] rs2)  {
		this.rs2=rs2;
		//debug
		//System.out.println(rs2[100][2]);
	}
	
	//��ȡ��������
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
	
	//��temp1��temp2�ϳ�һ��
	public String[] getpipei(String[] temp1,String[] temp2) {
		//int i=4;
		String [] temp3 = new String [8];
		for (int i=0;i<4;i++) {
			temp3[i]=temp1[i];
		}
		for (int i=0;i<4;i++) {
			temp3[i+4]=temp2[i];
		}
		return temp3;
	}
	
	
	//�����
	//************************
}
	
	
	/*
	//sql��ѯ����������洢����
	//��ѯ����
	public void query(String time1 , String time2 , String kakou1 , String kakou2 )  {
		
		//��ʼ��ѭ������
		//int fxi=0;
		//��¼�����ŵ�����
		int rs1i=0;
		int rs2i=0;
		//��¼���ظ�����������
		int chepai1i=0;
		int chepai2i=0;
		//ִ�в�ѯ����
		PreparedStatement pstmt1=conn.prepareStatement(sql1);
		pstmt1.setString(1, kakou1);
		pstmt1.setString(2, time1);
		pstmt1.setString(3, time2);
		//pstmt.setInt(4, fx[fxi]);
		
		//��ȡ���
		ResultSet result1 = pstmt1.executeQuery();
		//����(��)��ȡ����
		while(result1.next())
		{
			//��ֵ����
			this.rs1[rs1i][0] = result1.getString(1);
			//��ֵʱ��
			this.rs1[rs1i][1] = result1.getString(3);
			//��ֵ����
			this.rs1[rs1i][2] = result1.getString(4);
			//��ֵ������
			this.rs1[rs1i][3] = result1.getString(5);
			rs1i++;
			
		}
		result1.close();
		
		//ִ�в�ѯ���ظ����Ʋ���
		PreparedStatement pstmt1x=conn.prepareStatement(sql2);
		pstmt1x.setString(1, kakou1);
		pstmt1x.setString(2, time1);
		pstmt1x.setString(3, time2);
		
		
		ResultSet rschepai = pstmt1x.executeQuery();
		while(rschepai.next())  {
			//��ֵ���ظ�����
			this.chepai[chepai1i] = rschepai.getString(1);
			chepai1i++;
		}
		rschepai.close();
		
		System.out.println("����  "+kakou2+"  ����Ϊ  "+rs1i);
		
		//
		//**************************************************
		//****************************************************
		//**************************************************
		//
		
		int rs2i=0;
		//����sql���  distinct
		String sq2 = "SELECT a,b,c,d FROM " + sheet +" where b = ?  and c between ? and ? ";
		PreparedStatement pstmt2=conn.prepareStatement(sql1);
		pstmt2.setString(1, kakou2);
		pstmt2.setString(2, time1);
		pstmt2.setString(3, time2);
		//pstmt.setInt(4, fx[fxi]);
		ResultSet result2 = pstmt2.executeQuery();
		while(result2.next())
		{
			//��ֵ����
			this.rs2[i][0] = result2.getString(1);
			//��ֵʱ��
			this.rs2[i][1] = result2.getString(3);
			//��ֵ����
			this.rs2[i][2] = result2.getString(4);
			//��ֵ������
			this.rs1[i][3] = result1.getString(5);
			rs2i++; 
			
		}
		result2.close();
		
		//ִ�в�ѯ���ظ����Ʋ���
		PreparedStatement pstmt2x=conn.prepareStatement(sql2);
		pstmt2x.setString(1, kakou2);
		pstmt2x.setString(2, time1);
		pstmt2x.setString(3, time2);
		
		
		ResultSet rschepai2 = pstmt2x.executeQuery();
		while(rschepai2.next())  {
			//��ֵ���ظ�����
			this.rschepai2[chepai2i] = rschepai2.getString(1);
			rschepai2i++;
		}
		rschepai2.close();
		
		System.out.println("����  "+kakou2+"  ����Ϊ  "+rs2i);
	}
	
	*/
	
	
	
	
	/*
	//���Խ��˺������뵽find1��
	//����һ������ʱ�����ٶ�
	public void function()  {
		
		int time,speed;
		
		String tempchepai;
		int[] tempfind1=new int[20];
		int i=0;
		jisuan ins=new jisuan();
		//��ȡ��һ������
		tempchepai=ins.chepai[1];
		//debug ������Ʊ�ź������м���
		//System.out.println(tempchepai);
		tempfind1=ins.find1(tempchepai);
		//debug
		//System.out.println("find1 ִ�����");
		//System.out.println(tempfind1[0]);
		int tempi=0;
		//ȡ��rs1���������
		for (i=1;i<=tempfind1[0];i++)  {
			//tempfind1������chepai��rs1�������±�
			tempi=tempfind1[i];
			//debug
			//System.out.println("tempchepai"+tempchepai);
			//System.out.println("rs1[tempi][0]"+rs1[tempi][0]);
			if (tempchepai.equals(this.rs1[tempi][0]))  {
				System.out.println("����Ϊ "+this.rs1[tempi][0]);
				System.out.println("ʱ��Ϊ "+this.rs1[tempi][1]);
				System.out.println("����Ϊ "+this.rs1[tempi][2]);
				System.out.println("����Ϊ "+this.rs1[tempi][3]);
			}
			else {
				System.out.println("�����쳣");
			}
		}
		
	}
	*/
	