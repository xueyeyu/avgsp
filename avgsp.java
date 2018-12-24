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
//import java.util.Date;


public class avgsp  {
	
	//��������
	//
	//
	//����SQL server���ݿ�����
	final String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	//����SQL server����
	final String dburl="jdbc:sqlserver://localhost:1433;";
	private final String sheet;
	private final String dbname;
	private final String dbuser;
	private final String dbpassword;

	
	//��������
	private final int []fx = {1,2,3,4};
	//����洢���
	//0���ƣ�1ʱ�䣬3���� 4������ �ɴ洢 1 0000 ������
	private String rs1[][]=new String [100*100][4];
	private String rs2[][]=new String [100*100][4];
	//���岻�ظ����� (distinct)
	private String []chepai1 = new String[100*100];
	private String []chepai2 = new String[100*100];
	
	//��¼�����ŵ�����
	int rs1i=0;
	int rs2i=0;
	//��¼���ظ�����������
	int chepai1i=0;
	int chepai2i=0;
	
	String sql1,sql2;

	
	//������ ���ݲ���
	public avgsp
	(String dbname,String dbuser,String dbpassword,String sheet)  {
		this.dbname=dbname;
		this.dbuser=dbuser;
		this.dbpassword=dbpassword;
		this.sheet=sheet;
		//���Ǽ���e�ֶΣ����ڱ�ţ�
		this.sql1=
		"SELECT a,f,i,h FROM " + sheet +" where e = ? and a <> 'f941c96603e51c3c3f6297dc385d4cdc' and a <> '39d6d1ed9e4cb2571d8a3dd937827396' and f between ? and ? order by a,f asc";
		this.sql2=
		"SELECT distinct a FROM " + sheet +" where e = ? and f between ? and ? order by a asc";
		
	}
	
	
	
	public void query
	(String time1,String time2,String kakou1,String kakou2)  {
		//
		//
		//private String time1;
		//private String time2;
		//private String kakou1;
		//private String kakou2;
		
		//��������
		try  {
			Class.forName(driver);    
			System.out.println("�������ݿ������ɹ�");
		}
		
		//�׳��쳣 
		catch(ClassNotFoundException e)  {    
			System.out.println("�������ݿ�����ʧ��");  
		}
		
		//�����������ݿ�

		//��������
		try  {
			Connection conn =
			DriverManager.getConnection(dburl+
			"databasename="+dbname,dbuser,dbpassword);  
			System.out.println("���ݿ����ӳɹ�");
			//System.out.println("��ʼ��ȡ���ݿ�");
			
						//

			
			
			//
			//��ʼ��ѭ������
			//int fxi=0;

			//ִ�в�ѯ����
			
			//�����ѯ���
			//String sql1 = "SELECT a,f,j,i FROM " + sheet +" where e = ? and f between ? and ? order by a asc";
			//�������ظ�����
			//String sql2 = "SELECT distinct a FROM " + sheet +" where e = ? and f between ? and ? order by a asc";
			
			
			PreparedStatement pstmt1=conn.prepareStatement(sql1);
			pstmt1.setString(1, kakou1);
			pstmt1.setString(2, time1);
			pstmt1.setString(3, time2);
			//pstmt.setInt(4, fx[fxi]);
			
			

			//��ȡ���
			ResultSet result1 = pstmt1.executeQuery();
			//����(��)��ȡ����
			while(result1.next())  {
				//��ֵ����
				this.rs1[rs1i][0] = result1.getString(1);
				//��ֵʱ��
				this.rs1[rs1i][1] = result1.getString(2);
				//��ֵ����
				this.rs1[rs1i][2] = result1.getString(3);
				//��ֵ������
				this.rs1[rs1i][3] = result1.getString(4);
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
				this.chepai1[chepai1i] = rschepai.getString(1);
				chepai1i++;
				}
			rschepai.close();
			
			//System.out.println("����  "+kakou1+"  ��¼����Ϊ  "+rs1i);
			//System.out.println("����  "+kakou1+"  ���ظ���������Ϊ  "+chepai1i);
			
			//
			//**************************************************
			//****************************************************
			//**************************************************
			//
			
			
			PreparedStatement pstmt2=conn.prepareStatement(sql1);
			pstmt2.setString(1, kakou2);
			pstmt2.setString(2, time1);
			pstmt2.setString(3, time2);
			//pstmt.setInt(4, fx[fxi]);
			ResultSet result2 = pstmt2.executeQuery();
			while(result2.next())
			{
				//��ֵ����
				this.rs2[rs2i][0] = result2.getString(1);
				//��ֵʱ��
				this.rs2[rs2i][1] = result2.getString(2);
				//��ֵ����
				this.rs2[rs2i][2] = result2.getString(3);
				//��ֵ������
				this.rs2[rs2i][3] = result2.getString(4);
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
				this.chepai2[chepai2i] = rschepai2.getString(1);
				chepai2i++;
				}
			rschepai2.close();
			
			
			//System.out.println("����  "+kakou2+"  ��¼����Ϊ  "+rs2i);
			//System.out.println("����  "+kakou2+"  ���ظ���������Ϊ  "+chepai2i);
			
			}
		
		//�׳��쳣
		//catch(SQLException | ParseException e)  
		catch(SQLException e)  {
			e.printStackTrace();
		}
	}
	
	//���ݼ�¼����������
	public String[][] returnrs1()  {
		return rs1;
	}
	public String[][] returnrs2()  {
		return rs2;
	}
	public String[] returnchepai1()  {
		return chepai1;
	}
	public String[] returnchepai2()  {
		return chepai2;
	}
	
	
	//���ݼ�¼������
	public int returnrs1i()  {
		return rs1i;
	}
	public int returnrs2i()  {
		return rs2i;
	}
	public int returnchepai1i()  {
		return chepai1i;
	}
	public int returnchepai2i()  {
		return chepai2i;
	}
}
		
		
		
		/*
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
}
	
	*/
	
	
	
	
	
	
		/*
			try  {
			Class.forName(driver);    //��������
			System.out.println("�������ݿ������ɹ�");
			//������ʵ��
			//jisuan ins = new jisuan(time1,time2,kakou1,kakou2,sheet);
			
		}
		
		//�׳��쳣 
		catch(ClassNotFoundException e)  {    
			System.out.println("�������ݿ�����ʧ��");  
		}
		
		//�����������ݿ�

		//��������
		try  {
			Connection conn=DriverManager.getConnection(dburl+"databasename="+dbname,dbuser,dbpassword);  
			System.out.println("���ݿ����ӳɹ���");
			//System.out.println("��ʼ��ȡ���ݿ�");
			jisuan A = new jisuan(sheet);
			A.query(time1,time2,kakou1,kakou2);
		}
		
		//�׳��쳣
		//catch(SQLException | ParseException e)  
		catch(SQLException e)  {
			e.printStackTrace();
		}
}
		
		
		
		
		
		
		
		
		
		
		//��������
		//����SQL server���ݿ�����
		final String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		//����SQL server����
		//final String conn="jdbc:sqlserver://localhost:1433;databasename=kakou";
		final String dburl="jdbc:sqlserver://localhost:1433;";
		//�������ݿ�����
		final String dbname="kakou";
		//�������ݿ��û��� ��sa�� Ϊ���ݿ����Ա
		final String dbuser="sa";
		//�������ݿ�����
		final String dbpassword="password";
		//�����
		final String sheet="d028";
		//����ʱ��
		final String time1="2016-03-01 03:00:00";
		final String time2="2016-03-01 04:00:00";
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
		
		//15Ϊ15���ӣ��Դ�����
		final int timex=15;
		//���忨�ڱ��
		final String kakou1="3701033112";
		final String kakou2="3701033109";
		//���忨��֮��ľ���
		final int distance=1000;
		
		//���Լ������ݿ�����
		try  {
			Class.forName(driver);    //��������
			System.out.println("�������ݿ������ɹ�");
			//������ʵ��
			//jisuan ins = new jisuan(time1,time2,kakou1,kakou2,sheet);
			
		}
		
		//�׳��쳣 
		catch(ClassNotFoundException e)  {    
			System.out.println("�������ݿ�����ʧ��");  
		}
		
		//�����������ݿ�

		//��������
		try  {
			Connection conn=DriverManager.getConnection(dburl+"databasename="+dbname,dbuser,dbpassword);  
			System.out.println("���ݿ����ӳɹ���");
			//System.out.println("��ʼ��ȡ���ݿ�");
			jisuan A = new jisuan(sheet);
			A.query(time1,time2,kakou1,kakou2);
		}
		
		//�׳��쳣
		//catch(SQLException | ParseException e)  
		catch(SQLException e)  {
			e.printStackTrace();
		}
	}
}
*/