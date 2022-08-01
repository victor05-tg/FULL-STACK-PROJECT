package com.higradius;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.mysql.*;

/**
 * Servlet implementation class AdvanceSearch
 */
@WebServlet("/AdvanceSearch")
public class AdvanceSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdvanceSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		// TODO Auto-generated method stub
		
		response.setContentType("application/json");
		  response.setCharacterEncoding("UTF-8");
		  
		  PrintWriter out = response.getWriter();  
		  try {
		   
		   String query = null;

		   query = request.getParameter("query");
		   System.out.println(query);
		   String final_values[] = query.split(",");
		   for(int i = 0; i < final_values.length; ++i) {
		    System.out.println(final_values[i]);
		   }
		   String doc_id  = final_values[0];
		   int invoice_id = Integer.parseInt(final_values[1]);
		   int cust_number = Integer.parseInt(final_values[2]);
		   String business_year = final_values[3];
		   ArrayList<InvoiceDetails> data = new ArrayList<>();
		   System.out.println("value:"+doc_id+":");
		   Connection conn = GetConnection.connectToDB();
		   Statement st = conn.createStatement();
		   String sql_statement = "SELECT * FROM winter_internship WHERE doc_id ="+doc_id+" and  invoice_id =" + invoice_id + " and cust_number =" + cust_number+" and buisness_year LIKE '" + business_year + "'"; 
		   System.out.println(sql_statement);
		   ResultSet rs = st.executeQuery(sql_statement);
		   
		   while(rs.next()) {
				InvoiceDetails inv = new InvoiceDetails();
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
				
				System.out.println(inv);
				data.add(inv);
			}
		//   }
		   
		   System.out.println(data.toString());
		   Gson gson = new Gson();
		   String invoices = gson.toJson(data);
		   out.print(invoices);
		   response.setStatus(200);
		   out.flush();
		   
		  }catch(

		 ClassNotFoundException e)
		 {
		  e.printStackTrace();
		 }catch(
		 SQLException e)
		 {
		  e.printStackTrace();
		 }catch(
		 Exception e)
		 {
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
