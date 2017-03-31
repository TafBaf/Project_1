package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lib.LibPostgreSQL;
import model.Book;


public class WS_Library {

	public List<Book> GetBooks() {
		List<Book> bookList = new ArrayList<Book>();
		ResultSet resultSet;
		
		String query = "SELECT _id, name, author FROM book;";
		resultSet = LibPostgreSQL.GetQueryResultSet(query, null);
		
		try {
	    	int count = 0;
			while (resultSet.next()) {
				Book book = new Book();
				book.setId(resultSet.getInt("_id"));
				book.setName(resultSet.getString("name"));
				book.setAuthor(resultSet.getString("author"));
				bookList.add(count, book);
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	

		return bookList;
	}	
	
}
