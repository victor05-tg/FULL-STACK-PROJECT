package com.higradius;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddInvoice
 */
@WebServlet("/AddInvoice")
public class AddInvoice extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddInvoice() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String invoice = null;
		
		try {
			BufferedReader reader = request.getReader();
			invoice = reader.readLine();
			System.out.println(invoice);
//			System.out.println(invoice.getClass().getName());
			
//			System.out.println(invoice.split(","));
//			System.out.println(invoice.split("\"\""));
//			System.out.println(invoice.split(","));
			
			invoice =  invoice.substring(1, invoice.length() - 1);
			String final_values[] = invoice.split(",");
			
			for(int i = 0; i < final_values.length; ++i) {
				final_values[i] = final_values[i].split(":")[1];
				final_values[i] = final_values[i].substring(1, final_values[i].length() - 1);
				System.out.println(final_values[i]);
			}
			
			 String business_code = final_values[0];
		      int cust_number = Integer.parseInt(final_values[1]);
		      Date clear_date = Date.valueOf(final_values[2]);
		      String business_year = final_values[3];
		      String doc_id = final_values[4];
		      Date posting_date = Date.valueOf(final_values[5]);
		      Date document_create_date = Date.valueOf(final_values[6]);
		      Date due_in_date = Date.valueOf(final_values[7]);
		      String invoice_currency = final_values[8];
		      String document_type = final_values[9];
		      int posting_id = Integer.parseInt(final_values[10]);
		      double total_open_amount = Double.parseDouble(final_values[11]);
		      Date baseline_create_date = Date.valueOf(final_values[12]);
		      String cust_payment_terms = final_values[14];
		      int invoice_id = Integer.parseInt(final_values[13]);
			
			Connection conn = GetConnection.connectToDB();
			 String sql_statement = "INSERT INTO winter_internship (business_code, cust_number,clear_date,buisness_year, doc_id,posting_date,document_create_date,due_in_date,invoice_currency,document_type,posting_id, total_open_amount, baseline_create_date,cust_payment_terms,invoice_id,sl_no) values (?,?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?)";
			 String sql_statement1 = "INSERT INTO business (business_code) values (?)";
		     String sql_statement2 = "INSERT INTO customer (cust_number) values (?)";
			
		      PreparedStatement st1 = conn.prepareStatement(sql_statement1);
		      st1.setString(1, business_code);
		      PreparedStatement st2 = conn.prepareStatement(sql_statement2);
		      st2.setInt(1, cust_number);
		      PreparedStatement st = conn.prepareStatement(sql_statement);
		      st.setString(1, business_code);
		      st.setInt(2, cust_number);
		      st.setDate(3, clear_date);
		      st.setString(4, business_year);
		      st.setString(5,  doc_id);
		      st.setDate(6, posting_date);
		      st.setDate(7,  document_create_date);
		      st.setDate(8,  due_in_date);
		      st.setString(9,  invoice_currency);
		      st.setString(10,  document_type);
		      st.setInt(11,  posting_id);
		      st.setDouble(12,  total_open_amount);
		      st.setDate(13,  baseline_create_date);
		      st.setString(14,  cust_payment_terms);
		      st.setInt(15,  invoice_id);
			
		      st.setInt(16, 48585);
		      st1.executeUpdate();
		      st2.executeUpdate();
		      st.executeUpdate();
		      conn.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
