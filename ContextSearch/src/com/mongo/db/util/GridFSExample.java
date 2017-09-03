package com.mongo.db.util;

import static com.mongodb.client.model.Filters.eq;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import org.bson.Document;
import org.bson.types.ObjectId;
 
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
 
public class GridFSExample {
    public static void main(String[] args) {
  Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
  mongoLogger.setLevel(Level.SEVERE); 
  
  GridFSExample gridFs = new GridFSExample();
  //ObjectId babyBoyObjectId = gridFs.upload("D:/Pictures/22 Train Journeys In India That Are An Experience In Themselves!/8. The Dooars Voyage (Siliguri - New Mal - Hasimara –Alipurduar).jpg","Manabrata.jpg");
  //ObjectId babyGirlObjectId = gridFs.upload("C:/Users/Manabrata/Desktop/Manabrata/New Code/central.sql","manabrata-maity");
  
  ObjectId babyGirlObjectId = gridFs.upload("F:/Songs/Arijit Singh Mon Majhi Re Full HD Video Song _ Boss Bengali Movie _ Jeet & Subhasree - YouTube (1080p).mp4","manabrata-maity-video");

  
  gridFs.findAll();
  //gridFs.find(babyBoyObjectId);
  gridFs.download("manabrata-maity-video");
  //gridFs.rename(babyBoyObjectId,"new-baby-image");
  //gridFs.delete(babyBoyObjectId);
  gridFs.findAll();
    }
 
    // Upload File
    public ObjectId upload(String filePath,String fileName) {
  System.out.println("Calling upload...");
  MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
  DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
  ObjectId fileId = null;
  try {
   MongoDatabase database = mongoClient.getDatabase("technicalkeeda");
   GridFSBucket gridBucket = GridFSBuckets.create(database);
   InputStream inputStream = new FileInputStream(new File(filePath));
   // Create some custom options
   GridFSUploadOptions uploadOptions = new GridFSUploadOptions().chunkSizeBytes(1024).metadata(new Document("type", "image").append("upload_date", format.parse("2016-09-01T00:00:00Z")).append("content_type", "image/jpg"));
   fileId = gridBucket.uploadFromStream(fileName, inputStream, uploadOptions);
 
  } catch (Exception e) {
   e.printStackTrace();
  } finally {
   mongoClient.close();
  }
 
  return fileId;
    }
 
    
    public ObjectId uploadMultipleFiles(String filePath,String fileName) {
    	  System.out.println("Calling upload...");
    	  MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
    	  DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
    	  ObjectId fileId = null;
    	  try {
    	   MongoDatabase database = mongoClient.getDatabase("technicalkeeda");
    	   GridFSBucket gridBucket = GridFSBuckets.create(database);
    	   InputStream inputStream = new FileInputStream(new File(filePath));
    	   // Create some custom options
    	   GridFSUploadOptions uploadOptions = new GridFSUploadOptions().chunkSizeBytes(1024).metadata(new Document("type", "image").append("upload_date", format.parse("2016-09-01T00:00:00Z")).append("content_type", "image/jpg"));
    	   fileId = gridBucket.uploadFromStream(fileName, inputStream, uploadOptions);
    	 
    	  } catch (Exception e) {
    	   e.printStackTrace();
    	  } finally {
    	   mongoClient.close();
    	  }
    	 
    	  return fileId;
    	    }
    	 

    
    // Find All
    public void findAll() {
  System.out.println("Calling findAll...");
  MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
 
  try {
   MongoDatabase database = mongoClient.getDatabase("technicalkeeda");
   GridFSBucket gridBucket = GridFSBuckets.create(database);
 
   gridBucket.find().forEach(new Block<GridFSFile>() {
   @Override
   public void apply(final GridFSFile gridFSFile) {
    System.out.println("File Name:- " + gridFSFile.getFilename());
    System.out.println("Meta Data:- " + gridFSFile.getMetadata());
   }
   });
 
  } catch (Exception e) {
   e.printStackTrace();
  } finally {
   mongoClient.close();
  }
    }
    
    // Find by Id
    public void find(ObjectId objectId) {
  System.out.println("Calling find...");
  MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
 
  try {
   MongoDatabase database = mongoClient.getDatabase("technicalkeeda");
   GridFSBucket gridBucket = GridFSBuckets.create(database);
 
   GridFSFile gridFSFile = gridBucket.find(eq("_id",objectId)).first();
   System.out.println("File Name:- " + gridFSFile.getFilename());
   System.out.println("Meta Data:- " + gridFSFile.getMetadata());
 
  } catch (Exception e) {
   e.printStackTrace();
  } finally {
   mongoClient.close();
  }
    }
    
    // Download File
    public void download(String fileName) {
  System.out.println("Calling download...");
  MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
 
  try {
   MongoDatabase database = mongoClient.getDatabase("technicalkeeda");
   GridFSBucket gridBucket = GridFSBuckets.create(database);
 
   FileOutputStream fileOutputStream = new FileOutputStream("C:/Users/Manabrata/Desktop/Manabrata/Manabrata");
   gridBucket.downloadToStream(fileName, fileOutputStream);
   fileOutputStream.close();
 
  } catch (Exception e) {
   e.printStackTrace();
  } finally {
   mongoClient.close();
  }
    }
    
    //Rename File 
    public void rename(ObjectId objectId,String newFileName) {
  System.out.println("Calling rename...");
  MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
  try {
   MongoDatabase database = mongoClient.getDatabase("technicalkeeda");
   GridFSBucket gridBucket = GridFSBuckets.create(database);
   gridBucket.rename(objectId, newFileName);
  } catch (Exception e) {
   e.printStackTrace();
  } finally {
   mongoClient.close();
  }
    }
 
    //Delete File
    public void delete(ObjectId objectId) {
  System.out.println("Calling delete...");
  MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
  try {
   MongoDatabase database = mongoClient.getDatabase("technicalkeeda");
   GridFSBucket gridBucket = GridFSBuckets.create(database);
   gridBucket.delete(objectId);
  } catch (Exception e) {
   e.printStackTrace();
  } finally {
   mongoClient.close();
  }
    }
}
