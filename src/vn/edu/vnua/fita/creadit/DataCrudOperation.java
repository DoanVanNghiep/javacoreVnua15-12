package vn.edu.vnua.fita.creadit;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DataCrudOperation {
	
    private static String jdbcURL = "jdbc:ucanaccess://lib/accessJavaCore.accdb";
    private static String jdbcUsername = "";
    private static String jdbcPassword = "";
    
    
    
	public DataCrudOperation() {
	}

    public static Connection getConnection() throws SQLException {
        Connection con = null;

        try {
            con = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }
    
    // thêm mới sinh viên
    public static void insertStudent(Scanner sc) {
    	
    	// nhập thông tin sinh viên thêm mới
    	System.out.println("--------------------");
    	System.out.println("Thông tin sinh viên cần thêm mới:");
    	
    	System.out.print("Mã sinh viên: ");
    	String maSV = sc.nextLine();
    	
    	System.out.print("Họ đệm: ");
    	String hoDem = sc.nextLine();
    	
    	System.out.print("Tên: ");
    	String ten = sc.nextLine();
    	
    	System.out.println("Ngày sinh:");
    	System.out.print("	Ngày: ");
    	int ngay = Integer.parseInt(sc.nextLine());
    	
    	System.out.print("	Tháng: ");
    	int thang = Integer.parseInt(sc.nextLine());
    	
    	System.out.print("	Năm: ");
    	int nam = Integer.parseInt(sc.nextLine());
    	
    	System.out.print("Giới tính: ");
    	String gioiTinh = sc.nextLine();
    	
    	System.out.print("Mã lớp: ");
    	String maLop = sc.nextLine();
    	
    	
    	
    	String INSERT_STUDENT_SQL = "INSERT INTO SINHVIEN (MaSV,Hodem,Ten,NgaySinh,Gioitinh,MaLop) VALUES(?,?,?,?,?,?)";
    	
    	try (Connection connection = getConnection();
    			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT_SQL)){
    		preparedStatement.setString(1, maSV );
    		preparedStatement.setString(2, hoDem);
    		preparedStatement.setString(3, ten);
    		preparedStatement.setDate(4, new Date(thang, nam, ngay) );
    		preparedStatement.setString(5, gioiTinh);
    		preparedStatement.setString(6, maLop);
    		System.out.println(preparedStatement);
    		
    		int rows = preparedStatement.executeUpdate();
    		if(rows > 0) {
    			System.out.println("Thêm mới thành công");
    		}else {
    			System.out.println("Thêm mới không thành công");
    		}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    	
    }
    
    
    // thêm mới môn học
    public static void insertSubject(Scanner sc) {
    	
    	// nhập thông tin môn học cần thêm mới
    	System.out.println("--------------------");
    	System.out.println("Thông tin môn học cần thêm mới:");
    	
    	System.out.print("Mã môn học: ");
    	String maMH = sc.nextLine();
    	
    	System.out.print("Tên môn học: ");
    	String tenMH = sc.nextLine();
    	
    	System.out.print("Số tín chỉ: ");
    	int soTC = Integer.parseInt(sc.nextLine());
    	
    	System.out.print("Diều kiện: ");
    	String dieuKien = sc.nextLine();
    	
    	
    	String INSERT_SUBJECT_SQL = "INSERT INTO MONHOC (MaMH,TenMH,SoTC,Dieukien) VALUES (?,?,?,?)";
    	try (Connection connection = getConnection();
    			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SUBJECT_SQL)){
    		preparedStatement.setString(1 , maMH);
    		preparedStatement.setString(2 , tenMH);
    		preparedStatement.setInt(3 , soTC);
    		preparedStatement.setString(4 , dieuKien);
    		
    		
    		int rows = preparedStatement.executeUpdate();
    		if(rows > 0) {
    			System.out.println("Thêm môn học thành công");
    		}else {
    			System.out.println("Thêm môn học không thành công");
    		}
    		
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    	
    }

    
    // lấy tất cả sinh viên trong bảng
    public static void selectAllSinhVien() {
    	
    	/*
    	 * SELECT * FROM SINHVIEN s 
    	 * 			INNER JOIN KETQUA kq ON kq.MaSV = s.MaSV
    	 * 			INNER JOIN MONHOC mh ON mh.MaMH = kq.MaMH 
    	 * */
    	
    	String SELECT_ALL_SINHVIEN = "SELECT *FROM SINHVIEN "
							+ "INNER JOIN KETQUA ON SINHVIEN.MaSV = KETQUA.MaSV "
							+ "INNER JOIN MONHOC ON MONHOC.MaMH = KETQUA.MaMH";
    	try (Connection connection = getConnection();
    			Statement statement = connection.createStatement()){
    		ResultSet resultSet = statement.executeQuery(SELECT_ALL_SINHVIEN);
    		
			System.out.println("---------------------");
			System.out.println("Danh sách sinh viên:");
    		//Duyệt danh sách bản ghi khi trả về
    		while(resultSet.next()) {
    			String maSV = resultSet.getString("MaSV");
    			String hoDem = resultSet.getString("Hodem");
    			String ten = resultSet.getString("Ten");
    			Date ngaySinh = resultSet.getDate("NgaySinh");
    			String gioiTinh = resultSet.getString("Gioitinh");
    			String maLop = resultSet.getString("MaLop");
    			String tenMH = resultSet.getString("TenMH");
    			float diem = resultSet.getFloat("Diem");
    			System.out.println(maSV + " - " + hoDem +" "+ ten + " - " + ngaySinh + " - " + gioiTinh + " - " + maLop + " - " + tenMH + " - " + diem);
    		
    		}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    }
    
    // lấy tất cả môn học trong bảng
    public static void selectAllSubject() {
    	String SELECT_ALL_SUBJECT = "SELECT * FROM MONHOC";
    	try (Connection connection = getConnection();
    			Statement statement = connection.createStatement()){
    		ResultSet resultSet = statement.executeQuery(SELECT_ALL_SUBJECT);
    		
			System.out.println("---------------------");
			System.out.println("Danh sách sinh viên:");
    		//Duyệt danh sách bản ghi khi trả về
    		while(resultSet.next()) {
    			String maMH = resultSet.getString("MaMH");
    			String tenMH = resultSet.getString("TenMH");
    			int soTC = resultSet.getInt("SoTC");
    			String dieuKien = resultSet.getString("Dieukien");
    			
    			
    		System.out.println(maMH + " - " + tenMH + " - " + soTC + " - " + dieuKien);
    		}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    }
    
    /*
     * Bài tập 2: Viết chương trình cho phép SV nhập mã sinh viên,
     * mã môn học, điểm để cập nhật vào CSDL, nếu chưa tồn tại thì báo lỗi
     * */
    
    // cập nhật vào bảng kết quả
    public static void insertPoint() {
    	
    	// truy vấn để kiểm tra có sinh viên hay không
    	String CHECK_STUDENT = "SELECT * FROM SINHVIEN WHERE MaSV = ?";
    	
    	// truy vấn để kiểm tra có môn học hay không
    	String CHECK_SUBJECT = "SELECT * FROM MONHOC WHERE MaMH = ?";
    	
    	// truy vấn để kiểm tra sinh viên với môn học có trong bảng KETQUA hay không
    	
    	String CHECK_ST_SB = "SELECT * FROM KETQUA WHERE MaSV = ? AND MaMH = ?";
    	// cập nhật lại điểm
    	String UPDATE_POINT = "UPDATE KETQUA SET Diem = ? WHERE MaSV = ? AND MaMH = ?";
    	
    	
    	// thêm điểm vào bảng KETQUA
    	String INSERT_POINT = "INSERT INTO KETQUA (MaSV, MaMH, Diem) VALUES (?, ?, ?)";
    	
    	try (Connection connection = getConnection();
    			// kiểm tra sự tồn tại của sinh viên
    			PreparedStatement checkStudent = connection.prepareStatement(CHECK_STUDENT);
    			
    			// kiểm tra sự tồn tại của môn học
    			PreparedStatement checkSubject = connection.prepareStatement(CHECK_SUBJECT);
    			
    			// kiểm tra sự tồn tại của môn học và sinh viên trong bảng KETQUA
    			PreparedStatement checkSTSB = connection.prepareStatement(CHECK_ST_SB);
    			
    			// cập nhật lại điểm
    			PreparedStatement updatePoint = connection.prepareStatement(UPDATE_POINT);
    			
    			// thêm điểm
    			PreparedStatement insertPoint = connection.prepareStatement(INSERT_POINT);
    			
    			// nhập thông tin từ bàn phím 
    			Scanner scanner = new Scanner(System.in);
    			){
    		
    		//nhập mã sinh viên
    		System.out.print("Nhập mã sinh viên: ");
    		String maSV = scanner.nextLine();
    		
    		// kiểm tra sự tồn tại của sinh viên
    		checkStudent.setString(1, maSV);
    		ResultSet resultStudent = checkStudent.executeQuery();
    		
            if (!resultStudent.next()) {
                System.out.println("Lỗi: Sinh viên không tồn tại.");
                return;
            }
    		
            // 
            
    		// nhập mã môn học
    		System.out.print("Nhập mã môn học: ");
    		String maMH = scanner.nextLine();
    		
    		//Kiểm tra sự tồn tại của môn học 
    		checkSubject.setString(1, maMH);
    		ResultSet resultSubject = checkSubject.executeQuery();
    		
    		if(!resultSubject.next()) {
    			System.out.println("Lỗi: Môn học không tồn tại.");
    			return;
    		}
    		
    		
    		// kiểm tra sự tồn tại của môn học và sinh viên trong bảng KETQUA
    		checkSTSB.setString(1, maSV);
    		checkSTSB.setString(2, maMH);
    		
    		ResultSet result = checkSTSB.executeQuery();
    		
    		if(result.next()) {
    			// Nếu có điểm, hỏi người dùng có muốn thay đổi không
                System.out.println("Sinh viên đã có điểm môn học này.");
                System.out.print("Bạn có muốn thay đổi điểm không? (Y/N): ");
                String choice = scanner.next();

                if (choice.equalsIgnoreCase("Y")) {

                    // Cập nhật điểm
            		System.out.print("Nhập điểm mới: ");
            		float point = Float.parseFloat(scanner.nextLine());
                    
                    updatePoint.setString(1, maSV);
                    updatePoint.setString(2, maMH);
                    updatePoint.setFloat(3, point);

                    int rowsUpdated = updatePoint.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("Cập nhật điểm thành công.");
                    } else {
                        System.out.println("Lỗi: Không thể cập nhật điểm.");
                    }
                }
                else {
                	System.out.println("Đã dừng chương trình.");
                	return;
                }
    		}    
    		// nhập điểm
    		System.out.print("Nhập điểm: ");
    		float point = Float.parseFloat(scanner.nextLine());
    		
    		// thêm điểm vào database
    		insertPoint.setString(1, maSV);
    		insertPoint.setString(2, maMH);
    		insertPoint.setFloat(3, point);
    		
    		int rowsInserted = insertPoint.executeUpdate();
    		
    		if(rowsInserted > 0) {
    			System.out.println("Thêm điểm thành công.");
    		}else {
    			System.out.println("Thêm điểm không thành công.");
    		}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	
    }
    
    
    // Bài tập 3: tìm kiếm sinh viên
    public static void searchStudent() {
    	
    	Scanner sc = new Scanner(System.in);
    	System.out.print("Nhập vào mã sinh viên: ");
    	String MSV = sc.nextLine();
    	
		String SEARCH_SV = "SELECT * FROM SINHVIEN "
				+ "INNER JOIN KETQUA ON SINHVIEN.MaSV = KETQUA.MaSV "
				+ "INNER JOIN MONHOC ON KETQUA.MaMH = MONHOC.MaMH "
				+ "WHERE MaSV = '" + MSV+"'";
		try (Connection connection = getConnection();
				Statement statement = connection.createStatement();){
			ResultSet resultSet = statement.executeQuery(SEARCH_SV);
			while (resultSet.next()) {
				String ten = resultSet.getString("Ten");
				String maSV = resultSet.getString("MaSV");
				String lop = resultSet.getString("MaLop");
				String maMH = resultSet.getString("MaMH");
				String tenMH = resultSet.getString("TenMH");
				Float diem = resultSet.getFloat("Diem");
				System.out.println(maSV + " - " + ten +" - "+lop+"\n[ "+maMH+" - "+tenMH+" - "+diem+" ]");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
    }
    
    
    /*
     * Bài tập 4: Viết phương thức với tham số là MSV, trả về đối tượng SV gồm các thông tin của sinh viên và
     *  danh sách tất cả các môn học mà sinh viên này đã học qua
     * */
    
    public static void selectAll() {
    	String SELECT_ALL_SINHVIEN = "SELECT *FROM SINHVIEN "
				+ "INNER JOIN KETQUA ON SINHVIEN.MaSV = KETQUA.MaSV "
				+ "INNER JOIN MONHOC ON MONHOC.MaMH = KETQUA.MaMH";
    	try (Connection connection = getConnection();
    			Statement statement = connection.createStatement()){
    			ResultSet resultSet = statement.executeQuery(SELECT_ALL_SINHVIEN);

    			System.out.println("---------------------");
    			System.out.println("Danh sách sinh viên:");
    			//Duyệt danh sách bản ghi khi trả về
    			while(resultSet.next()) {
    				int term = resultSet.getInt("KyHoc");
    				String maSV = resultSet.getString("MaSV");
    				String hoDem = resultSet.getString("Hodem");
    				String ten = resultSet.getString("Ten");
    				Date ngaySinh = resultSet.getDate("NgaySinh");
    				String gioiTinh = resultSet.getString("Gioitinh");
    				String maLop = resultSet.getString("MaLop");
    				System.out.println("Kỳ học thứ: " + term);
    				System.out.println(maSV + " - " + hoDem +" "+ ten + " - " + ngaySinh + " - " + gioiTinh + " - " + maLop);

    			}

    	} catch (SQLException e) {
    		// TODO: handle exception
    			e.printStackTrace();
    	}    	
    }
    
    /*
     * Bài 5: Viết phương thức trả về danh sách tất cả các đối tượng sinh viên chứa thông tin sinh viên,
     * các môn học qua các kỳ của SV đó
     * */
    
    public static void allObject() {
    	String SELECT_ALL_SINHVIEN = "SELECT *FROM SINHVIEN "
				+ "INNER JOIN KETQUA ON SINHVIEN.MaSV = KETQUA.MaSV "
				+ "INNER JOIN MONHOC ON MONHOC.MaMH = KETQUA.MaMH";
    	try (Connection connection = getConnection();
    			Statement statement = connection.createStatement()){
    			ResultSet resultSet = statement.executeQuery(SELECT_ALL_SINHVIEN);

    			System.out.println("---------------------");
    			System.out.println("Danh sách sinh viên:");
    			//Duyệt danh sách bản ghi khi trả về
    			while(resultSet.next()) {
    				while (resultSet.next()) {
    					String ten = resultSet.getString("Ten");
    					String maSV = resultSet.getString("MaSV");
    					String lop = resultSet.getString("MaLop");
    					String maMH = resultSet.getString("MaMH");
    					String tenMH = resultSet.getString("TenMH");
    					String maKyHoc = resultSet.getString("KyHoc");
    					System.out.println(maSV + " - " + ten +" - "+lop+"\nKỳ học: "+maKyHoc+" [ "+maMH+" - "+tenMH+" ]\n----------------------------");
    			}
			}	
    	} catch (SQLException e) {
    		// TODO: handle exception
    			e.printStackTrace();
    	}  
    }
    
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int x;
        do {
        	System.out.println("\n-----------------------");
            System.out.println("1. Danh sách sinh viên và kết quả");
            System.out.println("2. Danh sách môn học");
            System.out.println("3. Thêm sinh viên");
            System.out.println("4. Thêm môn học");
            System.out.println("5. Thêm kết quả");
            System.out.println("6. Tìm kiếm sinh viên");
            System.out.println("7. Danh sách sinh viên có kỳ học");
            System.out.println("8. Tất cả thông tin sinh viên, các môn học qua các kì");
            System.out.println("9. Thoát");
            System.out.print("Your choice:");
            x = sc.nextInt();
            switch (x){
                case 1:
                	selectAllSinhVien();
                    break;
                case 2:
                	selectAllSubject();
                    break;
                case 3:
                	insertStudent(sc);
                    break;
                case 4:
                	insertSubject(sc);
                    break;
                case 5:
                	insertPoint();
                    break;
                case 6:
                	searchStudent();
                    break;
                case 7:
                	selectAll();
                    break;
                case 8:
                	allObject();
                    break;
                default:
                    System.out.println(" Exit successfully!");
                    sc.close();
                    break;
            }
        }while (x!=10);System.out.println("Không tìm thấy chức năng trên. xin nhập lại!");
		
	}
}
