package com.higradius;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
/**
 * Servlet implementation class Analytics
 */
@WebServlet("/Analytics")
public class Analytics extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Analytics() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		//int NO_OF_ROWS_TO_GET = 10;
		String invoice =null;
		try {
			Connection conn = GetConnection.connectToDB();
			invoice = request.getParameter("data");
			System.out.println(invoice);
			String final_values[] = invoice.split(",");
			String clear_date = final_values[0];
			String clear_date1 = final_values[1];
			String due_in_date = final_values[2];
			String due_in_date1 = final_values[3];
			String baseline_create_date = final_values[4];
			String baseline_create_date1= final_values[5];
			String invoice_currency = final_values[6];
			
//			
			Statement st = conn.createStatement();
			String sql_statement = "select * from winter_internship w join customer c on w.cust_number = c.cust_number where due_in_date between '"+ due_in_date + "' and '"+ due_in_date1 +"' and baseline_create_date between '"+ baseline_create_date + "' and '"+ baseline_create_date1 + "' and invoice_currency like '"+invoice_currency+"' order by sl_no LIMIT 0,100";
		    //String sql_statement1 = "SELECT count(*) from winter_internship";
			System.out.println(sql_statement);
			ResultSet rs = st.executeQuery(sql_statement);
			//ResultSet rs1 = st.executeQuery(sql_statement);
			
			ArrayList<InvoiceDetails> data = new ArrayList<>();
			while(rs.next()) {
				InvoiceDetails inv = new InvoiceDetails();
				//InvoiceDetails inv = new InvoiceDetails();
				inv.setSl_no(rs.getString("sl_no"));
				inv.setBusinessCode(rs.getString("business_code"));
				inv.setCustNumber(rs.getString("cust_number"));
				inv.setClearDate(rs.getString("clear_date") == null ? "" : rs.getString("clear_date").substring(0, 10));
				inv.setBusinessYear(rs.getString("buisness_year"));
				inv.setDocID(rs.getLong("doc_id"));
				inv.setPostingDate(rs.getString("posting_date"));
				inv.setDocumentCreateDate(rs.getString("document_create_date"));
				inv.setDueInDate(rs.getString("due_in_date"));
				inv.setInvoiceCurrency(rs.getString("invoice_currency"));
				inv.setDocumentType(rs.getString("document_type"));
				inv.setPostingID(rs.getInt("posting_id"));
				inv.setTotalOpenAmount(rs.getFloat("total_open_amount"));
				inv.setBaselineCreateDate(rs.getString("baseline_create_date"));
				inv.setCustPaymentTerms(rs.getString("cust_payment_terms"));
				inv.setInvoiceID(rs.getLong("invoice_id"));
				inv.setIsOpen(rs.getInt("isOpen"));
				//inv.setNotes(rs.getString("notes"));
				
				
				data.add(inv);
			}
			conn.close();
			System.out.println(data);
			Gson gson = new Gson();
			String responseData = gson.toJson(data);
			
//			System.out.println(invoices);
			response.getWriter().print(responseData);
			response.setStatus(200);
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

