package com.dfc.export;

import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfList;
import com.documentum.operations.IDfImportOperation;
import com.documentum.operations.IDfFile;
import com.documentum.operations.IDfImportNode;

public class BulkImport {
	
	public static void doImport( String srcFileOrDir, String destFolderPath, IDfSession session)
			 throws DfException {
			 IDfClientX clientx = new DfClientX();
			 IDfImportOperation operation = clientx.getImportOperation();
			 operation.setSession( session );

			 IDfFolder folder = null;
			 folder = session.getFolderByPath( destFolderPath );
			 if( folder == null ) {
			 System.out.println("Folder or cabinet " + destFolderPath + " does not exist in the Docbase!");
			 return; 
}
	
	 operation.setDestinationFolderId(folder.getObjectId());


	 IDfFile myFile = clientx.getFile(srcFileOrDir);
	 if( myFile.exists() == false ) {
	 System.out.println("File or directory " + srcFileOrDir + " does not exist in the file system!");
	 return;
	 }
	 IDfImportNode node = (IDfImportNode) operation.add( srcFileOrDir );

	 operation.execute();

	 IDfList myNodes = operation.getNodes();
	 int iCount = myNodes.getCount();
	 System.out.println("Number of nodes after operation: " + iCount );
	 for( int i = 0; i < iCount; ++i ) {
	 IDfImportNode aNode = (IDfImportNode) myNodes.get(i);
	 System.out.print("Object ID " + i + ": " + aNode.getNewObjectId().toString() + " " );
	 System.out.println("Object name: " + aNode.getNewObjectName() );
	 }
	 } 
}
