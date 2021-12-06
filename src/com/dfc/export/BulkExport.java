package com.dfc.export;

import java.io.File;

import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfId;
import com.documentum.operations.IDfExportNode;
import com.documentum.operations.IDfExportOperation;
import com.documentum.operations.IDfFile;

public class BulkExport {
	
	public static void doExportMultipleObjects(String strFolderPath, String destDir, IDfSession session )
			 throws DfException {
			 IDfClientX clientx = new DfClientX();
			 IDfFolder folder = null;
			 folder = session.getFolderByPath(strFolderPath);
			 if( folder == null ) {
			 System.out.println("Folder or cabinet " + strFolderPath + " does not exist in the Docbase!");
			 return;
			 }
			 IDfExportOperation operation = clientx.getExportOperation();

			 operation.setDestinationDirectory(destDir);

			 File f = new File(destDir);
			 f.mkdir();

			 String qualification = "select * from dm_document where FOLDER(ID('" + folder.getObjectId() + "'))";

			 IDfCollection col = null; 
			 try {

			 IDfQuery q = clientx.getQuery();
			 
			 q.setDQL(qualification);
			 col = q.execute((IDfSession) session, IDfQuery.DF_READ_QUERY);

			 while (col.next()) {
			 String name = col.getString("object_name");
			 IDfFile destFile = clientx.getFile( destDir + name );
			 if (! destFile.exists()){
			 String id = col.getString("r_object_id");
			 IDfId idObj = clientx.getId(id);
			 IDfSysObject myObj = (IDfSysObject) ((IDfSession) session).getObject(idObj);
			 IDfExportNode node = (IDfExportNode)operation.add(myObj);
			 }
			 }

			 operation.execute();

			 System.out.println("The objects in " + strFolderPath + " have been exported to " + destDir);
			 }
			 finally {
			 if (col!= null)
			 col.close();
			 }
			 }

	
}
