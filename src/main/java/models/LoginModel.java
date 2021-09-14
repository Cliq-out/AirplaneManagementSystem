package models;



import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginModel{
	
    private static Connection conn = null;
	private final static String url = "jdbc:postgresql://salt.db.elephantsql.com:5432/qvqtgufm";
	private final static String user = "qvqtgufm";
	private final static String password = "A8NzNTcLxh63-1UeYbQMpUFkmeS-qpW6";

	public LoginModel (){
		connect();
	}
	private static Connection connect() {
           try {
           conn = DriverManager.getConnection(url, user, password);
           System.out.println("Connected to the PostgreSQL server successfully.");
           } catch (SQLException e) {
           System.out.println(e.getMessage());
           }
       return conn;
   }
   public void createNewTable() {
        String sql ="CREATE TABLE IF NOT EXISTS LogTabelle ( id integer PRIMARY KEY , username text NOT NULL , passwort text NOT NULL , salt text NOT NULL , status text NOT NULL );";
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

    public void insert(String Username, String Passwort , String Status) throws NoSuchAlgorithmException, NoSuchProviderException  {
   	     int Id = 0 ;
      	 String sqlid = "SELECT id  FROM LogTabelle ";
      	 try (Statement stmt = conn.createStatement(); 
			 ResultSet rs = stmt.executeQuery(sqlid)) {
			 while (rs.next()) { 
				  if(Id <= rs.getInt("id")) {
					  Id = rs.getInt("id");
					  }
			  }
		  }catch (SQLException e) {
			  System.out.println(e.getMessage());
		  }
   		  String sql = "INSERT INTO LogTabelle (id , username , passwort ,salt , status) VALUES(?,?,?,?,?)";		
		  SaltedMD5 mm = new SaltedMD5();
		  if(getLogin(Username, Passwort)==false) {
		    String[]Codesalt = mm.insertSecureCode(Passwort);
	    	try {
			   PreparedStatement pstmt = conn.prepareStatement(sql);
			   pstmt.setInt(1, ++Id);
			   pstmt.setString(2, Username);
		   	   pstmt.setString(3,Codesalt[0]);
			   pstmt.setString(4,Codesalt[1]);
			   pstmt.setString(5,Status);
			   pstmt.executeUpdate();
			   
		    }catch (SQLException e) {
			   System.out.println(e.getMessage());
		    }
	    }
	}
    public static void selectAll() {
		  String sql = "SELECT id,username,passwort,salt,status FROM LogTabelle";
		  try (Statement stmt = conn.createStatement(); 
			  ResultSet rs = stmt.executeQuery(sql)) {
			  while (rs.next()) {
			     System.out.println(rs.getInt("id") +"    "+ rs.getString("username") +"    "+ rs.getString("passwort") +"    "+rs.getString("salt")+"    "+ rs.getString("status"));
			  }
		  }catch (SQLException e) {
			  System.out.println(e.getMessage());
		  }
	 }

    public static String getSalt(String Username,String Passwort) {
		String sql = "SELECT salt  FROM LogTabelle WHERE username =?  ";
		String sql2 = "SELECT salt  FROM LogTabelle WHERE username =? and passwort=? ";
		SaltedMD5 mmv = new SaltedMD5(); 
		try {
			        PreparedStatement pstmt = conn.prepareStatement(sql);
			        pstmt.setString(1, Username);
			   	    PreparedStatement pstmt2 = conn.prepareStatement(sql2);
		        	ResultSet rs = pstmt.executeQuery();
			        while(rs.next()) {
			        	pstmt2.setString(1, Username);
			        	pstmt2.setString(2, mmv.getSaltCode(Passwort, rs.getString("salt")));
			        	ResultSet rss = pstmt2.executeQuery();
			        	if( rss.next()) {
			        		System.out.println(rss.getString("salt"));
				            return rss.getString("salt") ;		
		         	   }
			        }
	      }catch (SQLException e) {
			      System.out.println(e.getMessage());	
		  }
		 return "";
	}
    public static int getId (String Username  , String Passwort ) {
		String sql = "SELECT id  FROM LogTabelle WHERE username=? and passwort = ? ";
		SaltedMD5 mv = new SaltedMD5();
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, Username);
			pstmt.setString(2, mv.getSaltCode(Passwort, getSalt(Username,Passwort)));
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getInt("id"));
				return rs.getInt("id") ;
			} else {
				return 0 ;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}
    
    public static String getStatus (String Username  , String Passwort ) {
		String sql = "SELECT status  FROM LogTabelle WHERE username=? and passwort = ? ";
		SaltedMD5 mv = new SaltedMD5();
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, Username);
			pstmt.setString(2, mv.getSaltCode(Passwort, getSalt(Username,Passwort)));
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
			//	System.out.println(rs.getInt("id"));
				return rs.getString("status") ;
			} else {
				return "" ;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return "";
	}
    public static  boolean getLogin(String Username, String Passwort)  {
		String sql = "SELECT  username , passwort , salt  FROM LogTabelle WHERE username =? and passwort = ?";
		SaltedMD5 mm = new SaltedMD5();
		String Code = mm.getSaltCode(Passwort,getSalt(Username,Passwort)); 		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, Username);
		    pstmt.setString(2,Code);
			ResultSet rs = pstmt.executeQuery();
		    if (rs.next()) {
				   return true;
			   } else {
				return false;
		       }
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
   public static void update (String Username, String Passwort , String Neupasswort) throws NoSuchAlgorithmException, NoSuchProviderException  {
		String sql =  "UPDATE LogTabelle SET passwort = ? , salt =  ?  WHERE id = ? and username = ?  and passwort = ?";
		SaltedMD5 mm = new SaltedMD5();
		if(getLogin( Username,Passwort)) {
		    String[ ]Codesalt = mm.insertSecureCode(Neupasswort);
			String Code = mm.getSaltCode(Passwort,getSalt(Username,Passwort));
		    try {
		    	PreparedStatement pstmt = conn.prepareStatement(sql);
		    	pstmt.setString(1, Codesalt[0]);
		    	pstmt.setString(2, Codesalt[1]);
		    	pstmt.setInt(3,getId (Username , Passwort ) );
		     	pstmt.setString(4,Username );
		     	pstmt.setString(5,Code );
		    	pstmt.executeUpdate();
		        } catch (SQLException e) {
		     System.out.println(e.getMessage());
		    }
	   }
	}
  // public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException{
   //    LoginModel app = new LoginModel();
  //  app.createNewTable();
  // app.insert( "88", "9","in");
       //  getStatus ("88", "9")
       //    app.getId ("me888", "ss99");    
       //  System.out.println(getLogin("88", "9"));
   //  app.update("name", "pass","neupass");
   //  app.selectAll();
   //  }
   }
