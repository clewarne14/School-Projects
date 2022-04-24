package dbutil;
/**
 * This class provides some basic methods for accessing a MariaDB database.
 * It uses Java JDBC and the MariaDB JDBC driver, mariadb-java-client-2.5.4.jar
 * to open and modify the DB. 
 */

//import the java.sql package to use JDBC methods and classes
import java.sql.*;

/**
 * @author Dr. Blaha
 * 
 */
public class Utilities {

	// Connection object
	private Connection conn = null; 
	 
	/**
	 * @return the conn
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * Open a MariaDB DB connection where user name and password are predefined
	 * (hardwired).
	 */
	public void openDB() {

		// Connect to the database using your team user name and team password	
		String url = "jdbc:mariadb://localhost:2000/company367_2022";
		//String url = "jdbc:mariadb://mal.cs.plu.edu:3306/company367_2022";
		String user = "dn367";
		String password = "dn367";
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("using url:"+url + " user:"+user + " password:*****");
			System.out.println("problem connecting to MariaDB: "+ e.getMessage());
			e.printStackTrace();
		}

	}// openDB

	/**
	 * Close the connection to the DB
	 */
	public void closeDB() {
		try {
			conn.close();
			conn = null;
		} catch (SQLException e) {
			System.err.println("Failed to close database connection: " + e);
		}
	}// closeDB

	// EXAMPLE OF USING A Statement -  A Statement object should only used when we are 
	// sure that any user input is safe from SQL injection attacks
	/**
	 * This method creates an SQL statement to list fname, lname, salary of all
	 * employees that work in the department with dname='Research'
	 * 
	 * @return ResultSet that contains three columns lname, fname, salary of all
	 *         employees that work in the research department
	 */
	
	public ResultSet researchDeptEmpInfo() {
		ResultSet rset = null;
		String sql = null;

		try {
			// create a Statement and an SQL string for the statement
			Statement stmt = conn.createStatement();
			sql = "SELECT lname, fname, salary FROM employee, department " + 
			      "WHERE dno=dnumber and dname='Research' " +
				  "ORDER BY lname, fname";
			rset = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("createStatement " + e.getMessage() + sql);
		}

		return rset;
	}// researchDeptEmpInfo

	// EXAMPLE OF USING A PreparedStatement AND SETTING Parameters
	/**
	 * This method creates an SQL PreparedStatement to list fname, lname, and department
	 * number of all employees that have a last name that starts with the String
	 * target
	 * 
	 * @param target the string used to match beginning of employee's last name
	 * @return ResultSet that contains lname, fname, and department number of
	 *         all employees that have a first name that starts with target.
	 */
	public ResultSet matchLastName(String target) {
		ResultSet rset = null;
		String sql = null;

		try {
			// create a Statement and an SQL string for the statement
			sql = "SELECT dno, lname, fname FROM employee WHERE lname like ? ORDER BY dno";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.clearParameters();
			pstmt.setString(1, target + "%"); // set the 1 parameter
			rset = pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("sql:" + sql);
			System.out.println("e.getMessage:" + e.getMessage());
		}

		return rset;
	}// matchLastName

	
	//EXAMPLE OF USING A PreparedStatement AND SETTING Parameters
	/**
	 * This method creates an SQL PreparedStatement to list fname, lname, and department
	 * number of all employees that work in the department with number dno and make
	 * more than amt
	 * 
	 * @param dno the department number
	 * @param amt salary amount
	 * @return ResultSet that contains dno, lname, fname, and salary of
	 *         all employees that work in department number dno and make more than amt
	 */	
	public ResultSet employeeByDNO_Salary(int dno, double amt) {
		ResultSet rset = null;
		String sql = null;
	 

		try {
			// create a Statement and an SQL string for the statement
			sql = "SELECT dno, lname, fname, salary FROM employee " + 
			      "WHERE dno = ? and salary > ? ORDER BY salary";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.clearParameters();
			pstmt.setInt(1, dno); // set the 1 parameter
			pstmt.setDouble(2, amt); // set the 1 parameter

			rset = pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("sql:" + sql);
			System.out.println("e.getMessage:" + e.getMessage());
		}

		return rset;
	}// employeeByDNO_Salary

	/**  
	 * 1 Write and Test
	 * Overload the open method that opens a MariaDB DB with the user name 
	 * and password given as input.
	 * 
	 * @param username is a String that is the DB account username
	 * @param password is a String that is the password the account
	 */
	public void open(String username, String password) {
		String url = "jdbc:mariadb://localhost:2000/company367_2022";
		try {
			conn=DriverManager.getConnection(url, username, password);
			System.out.println("Command Succeeded");
		}
		catch(Exception e) {
			System.out.println("Command failed");
			System.out.println("e.getMessage:" + e.getMessage());
		}
		
	}

	
	/**
	 * 2 Write and Test 
	 * Write a method that returns lname, fname, project number and hours of all 
	 * employees that work on a project controlled by department, deptNum. Here 
	 * deptNum is given as input from the client
	 * 
	 * @param deptNum is the controlling department number
	 * @return ResultSet with lname, fname, project number and hours of all
	 *         employees that work on a project controlled by department dno
	 */
	public ResultSet employeeOnControlledProject(int deptNum) {
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select lname, fname, pno, hours from employee, works_on, project where ssn=essn and pno=pnumber and dnum=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.clearParameters();
			ps.setInt(1, deptNum);
			rs = ps.executeQuery();
			System.out.println("Command Succeeded");
		}
		catch(Exception e) {
			System.out.println("Command Failed");
			System.out.println("e.getMessage:" + e.getMessage());
		}
		return rs;
	}

	/**
	 * 3 Write and Test
	 * Write a method that returns for each project the number of employees 
	 * that work on the project, the total number of hours they have all worked 
	 * on the project, and the average number of hours each employee has worked 
	 * on the project.
	 * 
	 * @return ResultSet that has for each project the number of employees that
	 *         work on the project, the total number of hours they have all
	 *         worked on the project, and the average number of hours each
	 *         employee has worked on project
	 */
	public ResultSet employeesOnProjects() {
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select pno, count(*), sum(hours), avg(hours) from works_on group by pno";
			PreparedStatement ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			System.out.println("Command Succeeded");
		}
		catch(Exception e) {
			System.out.println("Command Failed");
			System.out.println("e.getMessage:" + e.getMessage());
		}
		return rs;
	}
	
	/**
	 * 4 Write and Test
	 * Write a method that returns fname, lname, salary, and dno for each employee 
	 * that works on a project with the employee specified by input values empFname, empLname
	 * 
	 * @param empFname is the first name of the employee
	 * @param empLname is the last name of the employee
	 * @return ResultSet that has fname, lname, salary, and dno for each
	 *         employee that works on a project with the employee empFname,
	 *         empLname
	 */
	public ResultSet worksOnWithEmployee(String empFname, String empLname) {
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select distinct fname, lname, salary, dnum from employee, project, works_on where ssn=essn and pno=pnumber and pno in (select pno from employee, works_on where ssn=essn and fname=? and lname=?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.clearParameters();
			ps.setString(1, empFname);
			ps.setString(2, empLname);
			rs = ps.executeQuery();
			System.out.println("Command Succeeded");
		}
		catch(Exception e) {
			System.out.println("Command Failed");
			System.out.println("e.getMessage:" + e.getMessage());
		}
		return rs;
	}
	
	/**
	 * 5 Write and Test
	 * Retrieve the names of employees who do not work on any project and their 
	 * salary Names must be in the format "lname, fname" i.e., the last name and 
	 * first name must be concatenated.
	 * 
	 * @return ResultSet that has employee name and salary of all employees that
	 *         do not work on any project.
	 */
	public ResultSet noWorkOnProject() {
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select concat(lname, \", \", fname), salary from employee where ssn not in (select essn from works_on)";
			PreparedStatement ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			System.out.println("Command Succeeded");
		}
		catch(Exception e) {
			System.out.println("Command Failed");
			System.out.println("e.getMessage:" + e.getMessage());
		}
		return rs;
	}

	/**
	 * 6 Write and Test   
	 * This method has a 2-dim array, data, as input. Each row of the 2-dim
	 * array, data, contains the 3 attributes for one tuple in the works_on
	 * table. The 2-dim array is a nx3 array and the column format is 
	 * (essn, pno, hours) The method returns the number of tuples successfully
	 * inserted.
	 * 
	 * @param data is a nx3 table of Strings where each row has the format 
	 *        (essn, pno, hours)
	 * @return number of tuples successfully inserted into works_on
	 */
	public int worksOnInsert(String[][] data) {
		int output = 0;
		String sql = "";
		try {
			for(int i = 0; i < data.length; i++) {
				String[] currData = data[i];
				
				sql = "INSERT INTO works_on VALUES (?,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.clearParameters();
				ps.setString(1, currData[0]);
				ps.setString(2, currData[1]);
				ps.setString(3, currData[2]);
				ps.executeUpdate();
				output++;
			}
			System.out.println("Command Succeeded");
			
		}
		catch(Exception e) {
			System.out.println("Command Failed");
			System.out.println("e.getMessage:" + e.getMessage());
		}
		return output;
	}
	/**
	 * This method updates the works_on table with a row based on an input provided by the user.
	 * 
	 * @param essn is the employee ssn of the row that will be updated by this command (Primary key)
	 * @param pno is the project number of the row that will be updated by this command (Primary key)
	 * @param h is the new number of hours that is specified by this command
	 */
	public void updateWorksOn(String essn, int pno, double h) {
		try {
			String sql = "update works_on set hours = ? where essn = ? and pno = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.clearParameters();
			ps.setDouble(1, h);
			ps.setString(2, essn);
			ps.setInt(3, pno);
			ps.executeUpdate();
			System.out.println("Command Succeeded");
			
		}
		catch(Exception e) {
			System.out.println("Command Failed");
			System.out.println("e.getMessage:" + e.getMessage());
		}
	}
	
	/**
	 * This method deletes a specified row from the works_on table based on the input of the user.
	 * 
	 * @param essn is the employee ssn of the row that will be deleted by this command (Primary key)
	 * @param pno is the project number of the row that will be updated by this command (Primary key)
	 */
	public void deleteWorksOn(String essn, int pno) {
		try {
			String sql = "delete from works_on where essn=? and pno=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.clearParameters();
			ps.setString(1, essn);
			ps.setInt(2, pno);
			ps.executeUpdate();
			System.out.println("Command Succeeded!");
		}
		catch(Exception e) {
			System.out.println("Command Failed");
			System.out.println("e.getMessage:" + e.getMessage());
		}
	}
	

	/**
	 * This command adds a new row in the department locations table based on the department number and location provided by the user
	 * 
	 * @param dnumber is the department number that will be added to the table (Primary key)
	 * @param dlocation is the location that will be added to the table (Primary key)
	 * @return
	 */
	public void addDeptLoc(int dnumber, String dlocation) {
		try {
			String sql = "INSERT INTO dept_locations VALUES(?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.clearParameters();
			ps.setInt(1,dnumber);
			ps.setString(2, dlocation);
			ps.executeUpdate();
			System.out.println("Command Succeeded");
		}
		catch(Exception e) {
			System.out.println("Command Failed");
			System.out.println("e.getMessage:" + e.getMessage());
		}
	}

}// Utilities class





















