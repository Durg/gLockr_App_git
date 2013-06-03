/*package com.glockr.utils;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class UploadImage 
{
	public  String mediaString;

	public String imageProcessing()
	{
	BitmapFactory.Options o2 = new BitmapFactory.Options();
    o2.inSampleSize = scale;
    bitmap = BitmapFactory.decodeFile(filePath, o2);
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
    byte[] data = bos.toByteArray();
    mediaString = Base64.encodeBytes(data);	
    
    return mediaString;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] imageBytes = baos.toByteArray();
		String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
		
	}
}
*/