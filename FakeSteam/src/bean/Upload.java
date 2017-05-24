package bean;

import static dao.DaoTools.fermeturesSilencieuses;

import java.io.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;



public class Upload {
		
	private UploadedFile uploadedFile;
	private int id_image;
	private String fileName;
	private Blob dbImage;
	
	
	public int getId_image() {
		return id_image;
	}
	public void setId_image(int id_image) {
		this.id_image = id_image;
	}
	
	
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
	
	//check if the file is unique (return new name if the name is already taken)
	public String uniqueFile (String path, String filename)    throws IOException
		  {
		
			System.out.print("nom du fichier :"+filename+"\n");
			//le chemin ne doit contenir que des slash
			path = path.replace("\\", "/");
			System.out.print("chemin :"+path+"\n");
			Pattern p = Pattern.compile("([a-zA-Z0-9_]+)\\.([a-zA-Z0-9_]+)");
			Matcher m = p.matcher(filename);
			m.find();
			
			String prefix = m.group(1);
			System.out.print("prefix :"+prefix+"\n");
			
			String extension = m.group(2);
			System.out.print("extension :"+extension+"\n");
			
			
			File file = new File(path+"/"+ prefix+"."+extension);
			System.out.print("chemin absolu :"+path+"/"+ prefix+"."+extension+"\n");
			
			boolean wasCreated = false;
			int i=1;
			while(!wasCreated)
			{
				if(file.exists())
				{
					System.out.print("le fichier existe !");
					i++;
					//le fichier existe deja
					file = new File(path+"/"+ prefix+i+"."+extension);
					
				}
				else
				{
					//le fichier n'existe pas, on le renvoie
					if(i==1)
					{
						return prefix+"."+extension;
					}
					else
					{
						return prefix+i+"."+extension;
					}
					
				}
			}
			
			
			
		    return null;
		    
	}

		
	
	public void submit(){
		
		System.out.print("test *****************************************\n");
		
		if(uploadedFile != null) {
			String fileName = uploadedFile.getFileName();
			
			
			System.out.print("nom de l'image :"+fileName+"\n");
			String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
						
			
			try {
				//verifie si le nom du fichier n'existe pas deja. Sinon, incremente le nom du fichier
				String uniqueNameFile = uniqueFile(path+"/images", fileName);
				
				System.out.print("nom du fichier final :"+uniqueNameFile+"\n");
				InputStream in = uploadedFile.getInputstream();
				OutputStream out = new FileOutputStream(new File(path+"/images",uniqueNameFile) );
				int read = 0;
	            byte[] bytes = new byte[1024];
	          
	            while ((read = in.read(bytes)) != -1) {
	                out.write(bytes, 0, read);
	            }
	            in.close();
                out.flush();
                out.close();
                
                System.out.println("New file created!");
                
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
					
			
        }
		else
		{
			System.out.print("image null\n");
		}
		     
	     
	}	
	
}