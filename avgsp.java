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
	
	//变量声明
	//
	//
	//定义SQL server数据库驱动
	final String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	//定义SQL server链接
	final String dburl="jdbc:sqlserver://localhost:1433;";
	private final String sheet;
	private final String dbname;
	private final String dbuser;
	private final String dbpassword;

	
	//定义数据
	private final int []fx = {1,2,3,4};
	//定义存储结果
	//0车牌，1时间，3方向 4车道号 可存储 1 0000 条数据
	private String rs1[][]=new String [100*100][4];
	private String rs2[][]=new String [100*100][4];
	//定义不重复车牌 (distinct)
	private String []chepai1 = new String[100*100];
	private String []chepai2 = new String[100*100];
	
	//记录数组存放的条数
	int rs1i=0;
	int rs2i=0;
	//记录不重复车辆的条数
	int chepai1i=0;
	int chepai2i=0;
	
	String sql1,sql2;

	
	//构造器 传递参数
	public avgsp
	(String dbname,String dbuser,String dbpassword,String sheet)  {
		this.dbname=dbname;
		this.dbuser=dbuser;
		this.dbpassword=dbpassword;
		this.sheet=sheet;
		//考虑加入e字段（卡口编号）
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
		
		//加载驱动
		try  {
			Class.forName(driver);    
			System.out.println("加载数据库驱动成功");
		}
		
		//抛出异常 
		catch(ClassNotFoundException e)  {    
			System.out.println("加载数据库驱动失败");  
		}
		
		//尝试链接数据库

		//建立链接
		try  {
			Connection conn =
			DriverManager.getConnection(dburl+
			"databasename="+dbname,dbuser,dbpassword);  
			System.out.println("数据库连接成功");
			//System.out.println("开始读取数据库");
			
						//

			
			
			//
			//初始化循环变量
			//int fxi=0;

			//执行查询操作
			
			//定义查询语句
			//String sql1 = "SELECT a,f,j,i FROM " + sheet +" where e = ? and f between ? and ? order by a asc";
			//搜索不重复车牌
			//String sql2 = "SELECT distinct a FROM " + sheet +" where e = ? and f between ? and ? order by a asc";
			
			
			PreparedStatement pstmt1=conn.prepareStatement(sql1);
			pstmt1.setString(1, kakou1);
			pstmt1.setString(2, time1);
			pstmt1.setString(3, time2);
			//pstmt.setInt(4, fx[fxi]);
			
			

			//获取结果
			ResultSet result1 = pstmt1.executeQuery();
			//逐行(条)读取数据
			while(result1.next())  {
				//赋值车牌
				this.rs1[rs1i][0] = result1.getString(1);
				//赋值时间
				this.rs1[rs1i][1] = result1.getString(2);
				//赋值方向
				this.rs1[rs1i][2] = result1.getString(3);
				//赋值车道号
				this.rs1[rs1i][3] = result1.getString(4);
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
				this.chepai1[chepai1i] = rschepai.getString(1);
				chepai1i++;
				}
			rschepai.close();
			
			//System.out.println("卡口  "+kakou1+"  记录数量为  "+rs1i);
			//System.out.println("卡口  "+kakou1+"  不重复车辆数量为  "+chepai1i);
			
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
				//赋值车牌
				this.rs2[rs2i][0] = result2.getString(1);
				//赋值时间
				this.rs2[rs2i][1] = result2.getString(2);
				//赋值方向
				this.rs2[rs2i][2] = result2.getString(3);
				//赋值车道号
				this.rs2[rs2i][3] = result2.getString(4);
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
				this.chepai2[chepai2i] = rschepai2.getString(1);
				chepai2i++;
				}
			rschepai2.close();
			
			
			//System.out.println("卡口  "+kakou2+"  记录数量为  "+rs2i);
			//System.out.println("卡口  "+kakou2+"  不重复车辆数量为  "+chepai2i);
			
			}
		
		//抛出异常
		//catch(SQLException | ParseException e)  
		catch(SQLException e)  {
			e.printStackTrace();
		}
	}
	
	//传递记录的数组数据
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
	
	
	//传递记录的条数
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
		
		System.out.println("卡口  "+kakou2+"  数量为  "+rs1i);
		
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
		
		System.out.println("卡口  "+kakou2+"  数量为  "+rs2i);
	}
}
	
	*/
	
	
	
	
	
	
		/*
			try  {
			Class.forName(driver);    //加载驱动
			System.out.println("加载数据库驱动成功");
			//创建新实例
			//jisuan ins = new jisuan(time1,time2,kakou1,kakou2,sheet);
			
		}
		
		//抛出异常 
		catch(ClassNotFoundException e)  {    
			System.out.println("加载数据库驱动失败");  
		}
		
		//尝试链接数据库

		//建立链接
		try  {
			Connection conn=DriverManager.getConnection(dburl+"databasename="+dbname,dbuser,dbpassword);  
			System.out.println("数据库连接成功！");
			//System.out.println("开始读取数据库");
			jisuan A = new jisuan(sheet);
			A.query(time1,time2,kakou1,kakou2);
		}
		
		//抛出异常
		//catch(SQLException | ParseException e)  
		catch(SQLException e)  {
			e.printStackTrace();
		}
}
		
		
		
		
		
		
		
		
		
		
		//变量声明
		//定义SQL server数据库驱动
		final String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		//定义SQL server服务
		//final String conn="jdbc:sqlserver://localhost:1433;databasename=kakou";
		final String dburl="jdbc:sqlserver://localhost:1433;";
		//定义数据库名称
		final String dbname="kakou";
		//定义数据库用户名 ‘sa’ 为数据库管理员
		final String dbuser="sa";
		//定义数据库密码
		final String dbpassword="password";
		//定义表
		final String sheet="d028";
		//定义时间
		final String time1="2016-03-01 03:00:00";
		final String time2="2016-03-01 04:00:00";
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
		
		//15为15分钟，以此类推
		final int timex=15;
		//定义卡口编号
		final String kakou1="3701033112";
		final String kakou2="3701033109";
		//定义卡口之间的距离
		final int distance=1000;
		
		//尝试加载数据库驱动
		try  {
			Class.forName(driver);    //加载驱动
			System.out.println("加载数据库驱动成功");
			//创建新实例
			//jisuan ins = new jisuan(time1,time2,kakou1,kakou2,sheet);
			
		}
		
		//抛出异常 
		catch(ClassNotFoundException e)  {    
			System.out.println("加载数据库驱动失败");  
		}
		
		//尝试链接数据库

		//建立链接
		try  {
			Connection conn=DriverManager.getConnection(dburl+"databasename="+dbname,dbuser,dbpassword);  
			System.out.println("数据库连接成功！");
			//System.out.println("开始读取数据库");
			jisuan A = new jisuan(sheet);
			A.query(time1,time2,kakou1,kakou2);
		}
		
		//抛出异常
		//catch(SQLException | ParseException e)  
		catch(SQLException e)  {
			e.printStackTrace();
		}
	}
}
*/