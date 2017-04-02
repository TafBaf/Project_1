package controller;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

import lib.LibSQL;
import model.Book;


/* 
 * 
 *  TODO : Use  Spring - JDBC Framework instead!!!!!!!!!
 * 
 */

@WebService(targetNamespace = "http://controller/", portName = "WS_LibraryPort", serviceName = "WS_LibraryService")
public class WS_Library {

	@WebMethod(operationName = "GetBooks", action = "urn:GetBooks")
	public List<Book> GetBooks() {
		List<Book> bookList = new ArrayList<Book>();
		List<String[]> table = null;
		
		String query = "SELECT _id, name, author FROM book;";
		table = LibSQL.GetQueryRows(query, null);
		
		int count = 0;
		for(String[] row: table){
			Book book = new Book();
			book.setId(Integer.parseInt(row[0]));
			book.setName(row[1]);
			book.setAuthor(row[2]);
			bookList.add(count, book);
			count++;			
		}

		return bookList;
	}	
	
	
	@WebMethod(operationName = "AddBook", action = "urn:AddBook")
	public void AddBook(Book book) {
		String query = "INSERT INTO book (name, author) VALUES (?, ?);";
		String[] prepParams = {book.getName(), book.getAuthor()};
		LibSQL.Execute(query, prepParams);		
	}
	

	public void UpdateBook(Book book) {
		String query = "UPDATE book SET name = ?, author = ? WHERE _id = ?;";
		String[] prepParams = {book.getName(), book.getAuthor(), String.valueOf(book.getId())};
		LibSQL.Execute(query, prepParams);			
	}	
	
	
	public void DeleteBook(String id) {
		String query = "DELETE FROM book WHERE _id = ?;";
		String[] prepParams = {"id"};
		LibSQL.Execute(query, prepParams);			
	}
	
}
