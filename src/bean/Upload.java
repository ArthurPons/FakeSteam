package bean;

import java.io.*;
import java.sql.*;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import dao.DaoException;
import dao.DaoFactory;
import dao.UploadDao;

public class Upload {
		
	private int id_image;
	public int getId_image() {
		return id_image;
	}
	public void setId_image(int id_image) {
		this.id_image = id_image;
	}
	private UploadedFile uploadedFile;
	
	public UploadedFile getUploadedFile() {
	return uploadedFile;
	}
	public void setUploadedFile(UploadedFile uploadedFile) {
	this.uploadedFile = uploadedFile;
	}
	public String getFileName() {
	return fileName;
	}
	public void setFileName(String fileName) {
	this.fileName = fileName;
	}
	public Blob getDbImage() {
	return dbImage;
	}
	public void setDbImage(Blob dbImage) {
	this.dbImage = dbImage;
	}
	private String fileName;
	private Blob dbImage;
	
	
	public void submit(){
		
		
		 DaoFactory fact = DaoFactory.getInstance();
	     UploadDao uploadDao = fact.getUploadDao();
		
	     try {          
	    	 	uploadDao.create( this );            
	        
		    } catch ( DaoException e ) {	        
		        e.printStackTrace();
		    }
	}
	
	/*
	public String upload() {
	
		String result="";
		Connection con = null;
		try {
		File imgfile = new File(uploadedFile.getName());
		System.out.println("Name: " + imgfile.getName());
		FileInputStream fin = new FileInputStream(imgfile);
		
		//connexion a la base
		Labcon lc = new Labcon();
		con = lc.getLocalConnection();
		PreparedStatement pre = con.prepareStatement(“insert into upload_image (IMAGE,Image_name,image_length) values(?,?,?)”);
		pre.setBinaryStream(1,fin,(int)imgfile.length());
		pre.setString(2, imgfile.getName());
		pre.setLong(3, uploadedFile.getSize());
		pre.executeUpdate();
		System.out.println(“Inserting Successfully!”);
		pre.close();
		
		} catch (Exception ioe) {
		System.out.println(“Exception-File Upload.”+ioe.getMessage());
		
		}
		return result;
	}
	*/

}