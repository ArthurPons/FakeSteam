package bean;

import java.sql.*;
import java.util.*;

public class Image {
		
	private String imageID;
	private String imageName;
	public String getImageName() {
	return imageName;
	}
	
	public void setImageName(String imageName) {
	this.imageName = imageName;
	}
	
	public String getImageLength() {
	return imageLength;
	}
	
	public void setImageLength(String imageLength) {
	this.imageLength = imageLength;
	}
	private String imageLength;
	
	public String getImageID() {
	return imageID;
	}
	
	public void setImageID(String imageID) {
	this.imageID = imageID;
	}
	Connection MySQLcon = null;
	Statement stmt = null;
	PreparedStatement ps;
	ResultSet rs;
	
	/*
	public List<Image> getAllImage()
	{
		List<Image> imageInfo = new ArrayList<Image>();
		
		//connexion database
		Labcon lc = new Labcon();
		MySQLcon = lc.getLocalConnection();
		try {
		stmt=MySQLcon.createStatement();
		String strSql=”select image_id,Image_name,image_length from upload_image order by image_id “;
		System.err.println(“*select all***”+strSql);
		rs=stmt.executeQuery(strSql);
		while(rs.next()){
		TableBean tbl = new TableBean();
		tbl.setImageID(rs.getString(“image_id”));
		tbl.setImageName(rs.getString(“Image_name”));
		tbl.setImageLength(rs.getString(“image_length”));
		imageInfo.add(tbl);
		}
		} catch (SQLException e) {
		System.out.println(“Exception in getAllImage::”+e.getMessage());
		}
		return imageInfo;
	}
	*/

}